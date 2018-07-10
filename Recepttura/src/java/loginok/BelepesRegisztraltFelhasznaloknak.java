package loginok;

import adatrogzites.UserLog;
import alap.Hatterkep;
import database.AntiSqlInjection;
import database.EgyEredmeny;
import database.HTMLoldalak;
import database.LekerdezesStringek;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BelepesRegisztraltFelhasznaloknak extends HttpServlet {

    int KITILTASIIDO = 300000;

    String regoldal;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            Map<String, String[]> list = request.getParameterMap();
            EgyEredmeny kereses = new EgyEredmeny();
            LekerdezesStringek kell = new LekerdezesStringek();
            Hatterkep kep = new Hatterkep();
            HTMLoldalak oldalak = new HTMLoldalak();
            String loginnev = request.getParameter("textfield");
            AntiSqlInjection injektion = new AntiSqlInjection();
            
            //-------------------------jelszó bekérő oldal betöltése
            regoldal = oldalak.oldal_kerese(4);
//<editor-fold defaultstate="collapsed" desc="SIP">
            if (injektion.vanbenne(loginnev)) {
                regoldal = oldalak.oldal_kerese(1);

                regoldal = regoldal.replaceAll(":##:", "A felhasználói név meg nem engedett karaktereket vagy kifejezéseket tartalmaz.\n"
                        + "---SQL Injection Protector--- (SIP.2018)");
                regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                out.println(regoldal);
//</editor-fold>
            } else {

//<editor-fold defaultstate="collapsed" desc="közvetlen oldalelérési kisérlet">
                boolean talaltlogint = true;
                Calendar c = Calendar.getInstance();
                if (list.isEmpty()) {

                    regoldal = oldalak.oldal_kerese(1);

                    regoldal = regoldal.replaceAll(":##:", "Az oldal nem érhető el közvetlenül.");
                    regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                    out.println(regoldal);
//</editor-fold>
                } else {

//<editor-fold defaultstate="collapsed" desc="Nincs loginnév megadva">
                    if (loginnev == null || "".equals(loginnev)) {
                        regoldal = oldalak.oldal_kerese(1);

                        regoldal = regoldal.replaceAll(":##:", "Kérem adja meg a regisztrációhoz használt Login nevét (felhasználói nevét)");
                        regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                        out.println(regoldal);
//</editor-fold>
                    } else {
//<editor-fold defaultstate="collapsed" desc="ha talált olyan loginnevet amit beírt a juser">
                        String emailvalidator = kereses.lekerdezes( "CALL `Felhasznaloinev_ellenorzese`('" + loginnev+ "' )", " ip: " + request.getLocalAddr() + ""
                                + "--e-mail cim validator--");
                        int ell = 0;
                        try {
                            ell = Integer.parseInt(emailvalidator);
                        } catch (NumberFormatException e) {

                        }
                        if (ell > 0) {
                            String belepo_ID_string = kereses.lekerdezes( "CALL `A_felhasznalo_id_lekerdezese_nev_alapjan`('" +loginnev+ "')", " ip: " + request.getLocalAddr() + ""
                                    + "--bekuldo id lekeres--");
                            int id_int = 0;
                            if (!"Nincs eredménye a lekérdezésnek".equals(belepo_ID_string)) {
                                id_int = Integer.parseInt(belepo_ID_string);
                            }

                            regoldal = regoldal.replaceAll(":##:", " az oldalon");
                            UserLog userlog = new UserLog();
                            regoldal = regoldal.replaceAll(":#ATOK#:", userlog.atokir(id_int, c.getTimeInMillis(), 180000, "PW"));
                            regoldal = regoldal.replaceAll(":###:", String.valueOf(id_int));
                            regoldal = regoldal.replaceAll(":#HDT#:", String.valueOf(id_int * c.getTimeInMillis()));
//-----------------------------regisztralom a belépési folyamatot ha még nincsen regisztrálva :#HDT#:
                            String szamol = kereses.lekerdezes_nokess(kell.sql(37) + id_int + "\";", " ip: " + request.getLocalAddr() + ""
                                    + "--bejelentkezesi folyamat regisztracio megletenek ellenorzese--");
                            int szamolint = 0;
                            try {
                                szamolint = Integer.parseInt(szamol);
                            } catch (NumberFormatException e) {
                                out.println("A felhasználó nem található a securiti_log TBL-ben: Kérem a hibát jelezze az error@recepttura.hu címen");
                            }
                            boolean belepesikiserlet = false;

                            String ip = null;
//<editor-fold defaultstate="collapsed" desc="volt e hibás jelszó megadás ,mikor volt hányszor ha kell akkor 5 perc kitiltás">
                            if (szamolint > 5) {
                                String mikor = kereses.lekerdezes( "CALL `Belepesiprobaideje`('"+id_int+"');" , " "
                                        + "--bejelentkezesi hiba idejenek ell--");
                                long mikorlong = Long.parseLong(mikor);
                                if (c.getTimeInMillis() > KITILTASIIDO + mikorlong) {
                                    ip = kereses.lekerdezes(kell.sql(48) + id_int + "\";", "");
                                    kereses.adatbevitel(kell.sql(49) + id_int + "\";", " "
                                            + "--bejelentkezesi hiba idejenek torlese--");
                                    kereses.adatbevitel(kell.sql(50) + id_int + "','" + c.getTimeInMillis() + "','" + 0 + "','" + request.getLocalAddr() + "')",
                                            "--Felhasznaloi belepesproba ellenorzesre adatrogzites--");
                                    belepesikiserlet = true;
                                } else {
                                    response.sendRedirect("index.htm");
                                }
//</editor-fold>
                            } else {
                                regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                                out.println(regoldal);

                            }
//az oldalára több mint 6 sikertelen belépési kisérlet történt
                            if (belepesikiserlet) {
                                regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                                regoldal = regoldal.replaceAll(loginnev, loginnev + "! Az ön fiókjába több sikertelen belépési kisérlet történt, utoljára a következő IP címről: " + ip);
                                out.println(regoldal);
                            }
//</editor-fold>
                        } else {

//<editor-fold defaultstate="collapsed" desc="Nincs olyan login név amit megadott a juser kap egy password próbálgató oldalt ami a frontenden sriptelt, hogy ne terhelje a servert">
                            talaltlogint = false;
                            regoldal = kereses.lekerdezes(kell.sql(47), " ip: " + request.getLocalAddr() + ""
                                    + "--jelszo bekeres oldal lekerese--");

                            regoldal = regoldal.replaceAll(":##:", "az oldalon");

                            regoldal = regoldal.replaceAll(":###:", String.valueOf(-1));
                            regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                            regoldal = regoldal.replaceAll(":#HDT#:", String.valueOf(-1 * c.getTimeInMillis()));
                            regoldal = regoldal.replaceAll(":#ATOK#:", loginnev);
                            out.println(regoldal);
//</editor-fold>

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

    @Override
    public void init() {

    }
}
