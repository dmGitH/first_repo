package loginok;

import adatrogzites.UserLog;
import alap.Hatterkep;
import database.AntiSqlInjection;
import database.Cript;
import database.EgyEredmeny;
import database.HTMLoldalak;
import database.LekerdezesFormazasa;
import database.LekerdezesStringek;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//<editor-fold defaultstate="collapsed" desc="Regisztrációs adatok ellenörzése servlet">
public class RegAdatEllenorzese extends HttpServlet {

    int KITILTASIIDO = 300000;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Map<String, String[]> list = request.getParameterMap();
            Hatterkep kep = new Hatterkep();
            EgyEredmeny kereses = new EgyEredmeny();
            LekerdezesStringek kell = new LekerdezesStringek();
            UserLog userlog = new UserLog();
            LekerdezesFormazasa formaz = new LekerdezesFormazasa();
            HTMLoldalak oldalak = new HTMLoldalak();
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Az oldalt közvetlenül próbálják elérni, paraméterek megadása nélkül">
            if (list.isEmpty()) {

                String regoldal = oldalak.oldal_kerese(1);

                regoldal = regoldal.replaceAll(":##:", "Az oldal nem érhető el közvetlenül. Kövesse a regisztráció lépéseit");
                regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                out.println(regoldal);
//</editor-fold>
            } else {
//<editor-fold defaultstate="collapsed" desc="lices elfogadás ellenörzése">
                String validation;

                validation = request.getParameter("elfogadom");
//validation=String.valueOf(validation.toLowerCase().hashCode());

                if (validation == null || "".equals(validation)) {

                    String regoldal = oldalak.oldal_kerese(1);

                    regoldal = regoldal.replaceAll(":##:", "A regisztrációhoz be kell jelölnie az elfogadom a feltételeket jelölő négyzetet.");
                    regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                    out.println(regoldal);
                    Login ujra = new Login();
                    ujra.doGet(request, response);
//</editor-fold>
                } else {
//<editor-fold defaultstate="collapsed" desc="robotteszt kiértékelés">

                    String validvalasz = request.getParameter("eredmeny");
//                Robotteszt ellenörzése
                    if (!String.valueOf(validvalasz.toLowerCase().hashCode()).equals(validation)) {

                        String regoldal = oldalak.oldal_kerese(1);

                        regoldal = regoldal.replaceAll(":##:", "A regisztrációhoz írja be betüvel a kérdésként feltett művelet eredményét."
                                + " Ez biztonsági okokból szükséges.");
                        regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                        out.println(regoldal);
                        Login ujra = new Login();
                        ujra.doGet(request, response);
                    } else {

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="paraméterbegyűjtés">
                        String mailcim = request.getParameter("nn1");
                        String loginnev = request.getParameter("ee1");
                        String pw1 = request.getParameter("pp1");
                        String pw2 = request.getParameter("pp2");
                        String web = request.getParameter("web");
                        String rolam = request.getParameter("rolam");
                        String atok = request.getParameter("atok");
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="a kapott paraméterek visszakódolása">
//---visszakódolni a 4 paramétert
                        Cript encript = new Cript();
                        AntiSqlInjection injektion = new AntiSqlInjection();
                        String auth = request.getParameter("email") + request.getParameter("pw1");
                        String regoldal;
//token visszakódolása
                        atok = encript.reg_dekodol(atok, auth);

                        mailcim = encript.reg_dekodol(mailcim, atok);
                        pw1 = encript.reg_dekodol(pw1, atok);
                        pw2 = encript.reg_dekodol(pw2, atok);
                        loginnev = encript.reg_dekodol(loginnev, atok);
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Megadott adatok hosszának a validálása regisztrációhoz">
                        if (mailcim.length() > 250 || loginnev.length() > 500 || pw1.length() > 79 || web.length() > 500 || rolam.length() > 3000) {
                            regoldal = oldalak.oldal_kerese(1);

                            regoldal = regoldal.replaceAll(":##:", "Elnézését kérjük de az ön által megadott adatok hossza túl nagy.\n"
                                    + "Loginnév max-99 karakter, e-mail cím max 99 karakter, jelszó maximum 79 karakter\n"
                                    + ", a webcím míximum 254 karakter és az egyéb infó max 999 karakter lehet.\n\n"
                                    + "Megértését kérjük."
                                    + " ");
                            regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                            out.println(regoldal);
                            Login ujra = new Login();
                            ujra.doGet(request, response);
//</editor-fold>
                        } else {
//<editor-fold defaultstate="collapsed" desc="nem adott meg a user minden adatot">
                            if (loginnev == null || "".equals(loginnev) || pw1 == null || "".equals(pw1)) {

                                regoldal = oldalak.oldal_kerese(1);

                                regoldal = regoldal.replaceAll(":##:", "Nem adta meg a regisztrációhoz kötelező információkat. Javascriptet engedélyeznie kell a regisztrációhoz"
                                        + " ");
                                regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                                out.println(regoldal);
                                Login ujra = new Login();
                                ujra.doGet(request, response);
//</editor-fold>
                            } else {
//<editor-fold defaultstate="collapsed" desc="Két jelszó nem egyezik">
                                if (pw1 == null ? pw2 != null : !pw1.equals(pw2) || pw1.length() < 6) {

                                    regoldal = oldalak.oldal_kerese(1);

                                    regoldal = regoldal.replaceAll(":##:", "Nem egyezik a két jelszómező tartalma, vagy a hossza nem éri el a 6 karaktert kérem ellenőrizze."
                                            + " ");
                                    regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                                    out.println(regoldal);
                                    Login ujra = new Login();
                                    ujra.doGet(request, response);
//</editor-fold>
                                } else {
//<editor-fold defaultstate="collapsed" desc="emailcím validálás">
                                    if (mailcim.length() < 7 || !mailcim.contains(".") || !mailcim.contains("@")) {

                                        regoldal = oldalak.oldal_kerese(1);

                                        regoldal = regoldal.replaceAll(":##:", "Az email cím hibásnak tűnik, kérem ellenőrizze."
                                                + " ");
                                        regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                                        out.println(regoldal);
                                        Login ujra = new Login();
                                        ujra.doGet(request, response);
//</editor-fold>
                                    } else {
//<editor-fold defaultstate="collapsed" desc="LOginnév egyedi legyen. WARNINNG az email és a logon mező fel van cserélve a programban, de itt pont nincsen felszerélve">
//email-cím létezésének ellenörzése (pontosabban login névé)

                                        String emailvalidator = kereses.lekerdezes("CALL `Felhasznaloinev_ellenorzese`('" + injektion.ascii_kod(loginnev, "dec") + "' )", " ip: " + request.getLocalAddr() + ""
                                                + "--létezo loginnev ellenorzese--");

                                        int ell;
                                        try {
                                            ell = Integer.parseInt(emailvalidator);
                                        } catch (NumberFormatException e) {
                                            ell = 0;
                                        }
                                        if (ell > 0) {

                                            regoldal = oldalak.oldal_kerese(1);

                                            regoldal = regoldal.replaceAll(":##:", "A felhasználói név már létezik az adatbázisunkban. Kérem válasszon egy másik felhasználói nevet a regisztrációhoz");
                                            regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                                            out.println(regoldal);
                                            Login ujra = new Login();
                                            ujra.doGet(request, response);
//</editor-fold>
                                        } else {
//<editor-fold defaultstate="collapsed" desc="SIP">

                                            Calendar c;
                                            c = Calendar.getInstance();

//jelszó kódolás
                                            String kulcs = String.valueOf(c.getTimeInMillis()) + request.getLocalAddr();
                                            String PW = encript.kodol(pw1, kulcs);

                                            if (injektion.vanbenne(loginnev) && injektion.vanbenne(mailcim) && injektion.vanbenne(web) && injektion.vanbenne(rolam) && injektion.vanbenne(PW)) {
                                                regoldal = oldalak.oldal_kerese(1);
                                                regoldal = regoldal.replaceAll(":##:", "A megadott adatok meg nem engedett kifejezéseket, karaktereket tartalmaznak.\n"
                                                        + "---SQL Injection Protector--- (SIP.2018)");
                                                regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                                                out.println(regoldal);
                                                Login ujra = new Login();
                                                ujra.doGet(request, response);
//</editor-fold>

                                            } else {

//<editor-fold defaultstate="collapsed" desc="regisztráció elfogadva oldal felparaméterezése">
                                                regoldal = oldalak.oldal_kerese(2);
                                                regoldal = regoldal.replaceAll(":##:RN", mailcim = injektion.ascii_kod(injektion.anti_xxs(mailcim), "hex"));
                                                regoldal = regoldal.replaceAll(":##:EC", loginnev = injektion.ascii_kod(injektion.anti_xxs(loginnev), "dec"));
                                                regoldal = regoldal.replaceAll(":##:WA", web = injektion.ascii_kod(injektion.anti_xxs(web), "hex"));
                                                regoldal = regoldal.replaceAll(":##:EI", rolam = injektion.ascii_kod(injektion.anti_xxs(rolam), "hex"));
                                                regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="regisztracios token torlese ha nincsen meg akkor vissza az egész, a regisztráció nem történik meg ">
                                                if (!userlog.atokolvas_nembelepeskor(atok, 1)) {
                                                    regoldal = oldalak.oldal_kerese(1);

                                                    regoldal = regoldal.replaceAll(":##:", "A regisztrációs oldal érvényessége lejárt vagy manipulálva lett. Töltsön ki egy új regisztrációs űrlapot."
                                                            + " ");
                                                    regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                                                    out.println(regoldal);
//</editor-fold>
                                                } else {

//<editor-fold defaultstate="collapsed" desc=" A felhasználó beregisztrálása a bekuldo tablaba -sql string">
                                                    String bekuldo = kell.sql(56)
                                                            + mailcim + "','" + loginnev + "','" + web + "','" + PW + "','" + c.getTimeInMillis() + "','" + c.getTimeInMillis() + "',"
                                                            + "'" + rolam + "','" + 0 + "','" + request.getLocalAddr() + "')";

//adatrögzítés                                  
                                                    kereses.adatbevitel(bekuldo, "-- bekuldo regisztracio --");
                                                    String kodolatlanmail = loginnev;

//---a securitihez is be kell regisztralni a felhasználó id jét és a logtempbe is ?
                                                    emailvalidator = kereses.lekerdezes(kell.sql(46) + kodolatlanmail + "\";", " ip: " + request.getLocalAddr() + ""
                                                            + "--e-mail cim validator--");
                                                    int id = 0;
                                                    try {
                                                        id = Integer.parseInt(emailvalidator);
                                                    } catch (NumberFormatException e) {

                                                    }
//legyen egy belepes rogzitve
                                                    kereses.adatbevitel(kell.sql(57) + id + "','" + c.getTimeInMillis() + "','" + c.getTimeInMillis()
                                                            + "','" + 0 + "','" + request.getLocalAddr() + "','" + "regisztráció" + "')",
                                                            "--Felhasznaloi regisztacio  adatrogzites a belepesprobak ellenorzesere--");

                                                    kereses.adatbevitel(kell.sql(58) + id + "','" + (c.getTimeInMillis() - KITILTASIIDO) + "','" + 0 + "','" + request.getLocalAddr() + "')",
                                                            "--Felhasznaloi regisztacio  adatrogzites a belepesprobak ellenorzesere--");
                                                    out.println(regoldal);
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Az új felhasználó részére alapadatbázis készítése amit tatszés szerint módosíthat majd">
                                                    kereses.adatbevitel("INSERT INTO user_alapanyagai(alapanyag_id) SELECT alapanyagok_tmp.ID FROM alapanyagok_tmp;", "User alap adatokat kap a hasznalathoz etelkategoriak");
                                                    kereses.adatbevitel("INSERT INTO user_etelkategoriai (etel_kategoria_tmp_ID) SELECT etel_kategoria_tmp.ID FROM etel_kategoria_tmp;", "User alap adatokat kap a hasznalathoz etelkategoriak");
                                                    kereses.adatbevitel("INSERT INTO user_mennyisegiegysegei (mennyisegiegyseg_ID ) SELECT menyisegiegyseg_tmp.ID FROM menyisegiegyseg_tmp;", "User alap adatokat kap a hasznalathoz etelkategoriak");
                                                    kereses.adatbevitel("INSERT INTO user_opbeltartalmai (opcionalisbeltartalom_tmp_ID) SELECT opcionalisbeltartalom_tmp.ID FROM opcionalisbeltartalom_tmp;", "User alap adatokat kap a hasznalathoz etelkategoriak");
                                                    kereses.adatbevitel("UPDATE user_alapanyagai SET bekuldo_ID=" + id + " WHERE bekuldo_ID=0", "User alap adatokat kap a hasznalathoz etelkategoriak");
                                                    kereses.adatbevitel("UPDATE user_mennyisegiegysegei SET bekuldo_ID=" + id + " WHERE bekuldo_ID=0", "User alap adatokat kap a hasznalathoz etelkategoriak");
                                                    kereses.adatbevitel("UPDATE user_etelkategoriai SET bekuldo_ID=" + id + " WHERE bekuldo_ID=0", "User alap adatokat kap a hasznalathoz etelkategoriak");
                                                    kereses.adatbevitel("UPDATE user_opbeltartalmai SET bekuldo_ID=" + id + " WHERE bekuldo_ID=0", "User alap adatokat kap a hasznalathoz etelkategoriak");

                                                }
//</editor-fold>
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
