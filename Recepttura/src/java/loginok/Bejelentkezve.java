package loginok;

import adatrogzites.UserLog;
import alap.Hatterkep;
import database.AntiSqlInjection;
import database.Cript;
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
import org.apache.tomcat.util.buf.HexUtils;

public class Bejelentkezve extends HttpServlet {
    
    int KITILTASIIDO = 300000;
    
    int temp_id;
    Thread szal;
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
//<editor-fold defaultstate="collapsed" desc="fontos beállítani a magyar hosszú ékezetes betük miatt">
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
//</editor-fold>
        try (PrintWriter out = response.getWriter()) { //"respons nyomtató"

//<editor-fold defaultstate="collapsed" desc="változók, paraméterek">
            Map<String, String[]> list = request.getParameterMap();
            EgyEredmeny kereses = new EgyEredmeny();
            UserLog userlog = new UserLog();
            LekerdezesStringek kell = new LekerdezesStringek();
            HTMLoldalak oldalak= new HTMLoldalak();
            String regoldal;
            //előtöltöm a htmlt, hogy ha nincsen más hiba már be legyen töltve
            regoldal = oldalak.oldal_kerese(6);
// String pass = request.getParameter("pass"); // az eredeti input mező nem jó érték validálásra, megtévesztő adatot tartalmaz
            String pass2 = request.getParameter("return"); //ez a jó érték csak kódolva van
            String ver = request.getParameter("ver");
            String atok = request.getParameter("atok");
            String ltid = request.getParameter("logtempid");
            String honnan = request.getParameter("honnan");
            int id = 0;
            Calendar c = Calendar.getInstance();
            Hatterkep kep = new Hatterkep();
            
            long token = 0;
            boolean paswordbelepes = true;
            boolean jelszook = true;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="receptfelvétel oldalról jön vissza a felhasználó és ha az inaktivitás miatt ki lett léptetve elküldöm a főoldalra">
            if ("RFV".equals(honnan)) {
                id = Integer.parseInt(request.getParameter("idr"));
                
                if (userlog.atokolvas_nembelepeskor(atok, id)) {

                    //ha nincsen token az adatbázisban visszamegy a belépő oldalra
                    paswordbelepes = false;
                    
                } else {
                    response.sendRedirect("error.html");
                }
            }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Ha nem megfelelőek a oaraméterek visszaküldöm a főoldalra a következő else ágnál">
            if (paswordbelepes) {
                try {
                    token = Long.parseLong(request.getParameter("HDTOKEN"));
                    id = Integer.parseInt(request.getParameter("HDID"));
                } catch (NumberFormatException e) {
                    //ha nincsenek olvasható értékek visszamegy a belépő oldalra
                    //out.println("Azonosító: "+token+", Honnan: "+honnan);
                    paswordbelepes = false;
                }
            }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="felhasználói adatok előlekérdezése">
            regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
            regoldal = regoldal.replaceAll(":#LOGID#:", String.valueOf(id));
//személyes adatok lekérdezése betöltése az email és a neve fel van cserélve mindenütt még a táblában is bocs
            String nev = kereses.lekerdezes(kell.sql(42) + id + "\";", " ip: " + request.getLocalAddr() + ""
                    + "-- login nev lekereses--");
            regoldal = regoldal.replaceAll(":#BFH#:", nev);
            
            String sajatreceptekszama = kereses.lekerdezes(kell.sql(43) + id + ";", " ip: " + request.getLocalAddr() + ""
                    + "--Sajat receptek szama--");
            regoldal = regoldal.replaceAll(":#SRSZ#:", sajatreceptekszama);
            String sajatalapanyagok = kereses.lekerdezes(kell.sql(44) + id + ";", " ip: " + request.getLocalAddr() + ""
                    + "--Sajat alapanyagok szama--");
            regoldal = regoldal.replaceAll(":#SASZ#:", sajatalapanyagok);
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="semilyen paraméter nincsen a linket közvetlen próbálják elérni">
            if (list.isEmpty()) {
                
                regoldal = oldalak.oldal_kerese(1);
                
                regoldal = regoldal.replaceAll(":##:", "Az oldal nem érhető el közvetlenül.");
                regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                out.println(regoldal);
//</editor-fold>
            } else {
                
                if (paswordbelepes) {
//<editor-fold defaultstate="collapsed" desc="nem adtak meg jeszót, valahogy átment a frontend ellenörzésen">
                    if (pass2 == null || "".equals(pass2)) {
                        regoldal =oldalak.oldal_kerese(1);
                        
                        regoldal = regoldal.replaceAll(":##:", "Kérem adja meg a regisztrációhoz használt jelszavát");
                        regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                        out.println(regoldal);
//</editor-fold>
                    } else {

//<editor-fold defaultstate="collapsed" desc="a játok oldalt sikerült visszaküldenie a jusernek..., kezdje előről">
                        String hdid = String.valueOf(token);
                        if (id == -1) {
                            response.sendRedirect("index2.jsp");
                        }
//</editor-fold>

                        token = token / id;//a kiküldött token meg volt szorozva az id vel

//<editor-fold defaultstate="collapsed" desc="a jelszóbekérő oldal érvényessége lejárt, kezdje az elejéről">
                        if (((token + (1000 * 60 * 3)) < c.getTimeInMillis()) || (c.getTimeInMillis() + (1000 * 60 * 3) < token)) {
                            
                            response.sendRedirect("index2.jsp");
//</editor-fold>
                        } else {

//<editor-fold defaultstate="collapsed" desc="jelszóvalidálás">
                            String datapass = kereses.lekerdezes(kell.sql(34) + id + "\";", " ip: " + request.getLocalAddr() + ""
                                    + "--password validator1--");
                            String ctime = kereses.lekerdezes(kell.sql(35) + id + "\";", " ip: " + request.getLocalAddr() + ""
                                    + "--password validator2--");
                            String ip = kereses.lekerdezes(kell.sql(36) + id + "\";", " ip: " + request.getLocalAddr() + ""
                                    + "--password validator3--");
// a megadott jeszóból generált válasz ellenörzése a tárolt jelszóból történt generálás eredményének az összevetésével
                            Cript encript = new Cript();
                            int joapassword;
                            if (pass2.equals(encript.belepes(encript.kodol(datapass, ctime + ip), hdid, atok))) {
                                joapassword = 1;
                            } else {
                                joapassword = 0;
                            }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="hibás jelszó megadásának a kezelése">
                            int hibasbelepesszama = Integer.parseInt(kereses.lekerdezes_nokess(kell.sql(37) + id + "\";", " ip: " + request.getLocalAddr() + ""
                                    + "-----Hibasan megadott password--"));
                            
                            String mikor = kereses.lekerdezes("CALL `Belepesiprobaideje`('"+id+"');", " "
                                    + "--bejelentkezesi hiba idejenek ell--");
                            long mikorlong = Long.parseLong(mikor);
                            if (c.getTimeInMillis() < KITILTASIIDO + mikorlong) {
                                hibasbelepesszama = 6;
                            }
                            if (joapassword > 0) {
                                
                                if (hibasbelepesszama > 5) {
                                    response.sendRedirect("index.htm");
                                }
                                
                            } else {
                                regoldal = oldalak.oldal_kerese(1);

                                //-------------belépési kisérletek számolása
                                if (hibasbelepesszama > 5) {
                                    if (hibasbelepesszama > 6) {
                                        response.sendRedirect("index.htm");
                                    }
                                    regoldal = regoldal.replaceAll(":##:", "A beléptető rendszer biztonsági okból 5 percig nem fogad el érvényes jelszót sem. Megértését kérjük.");
                                    
                                    kereses.adatbevitel(kell.sql(39) + c.getTimeInMillis() + "' , probaszama='7' WHERE bekuldo_ID=" + id,
                                            "--WARNING---Tobbszori hibasan megadott jelszo--");
                                    
                                } else {
                                    hibasbelepesszama++;
                                    kereses.adatbevitel(kell.sql(40) + hibasbelepesszama + "',ip='" + request.getLocalAddr() + "' WHERE bekuldo_ID=" + id,
                                            "--WARNING---Tobbszori hibasan megadott jelszo--");
                                    kereses.adatbevitel(kell.sql(59) + pass2.hashCode() + "','" + id + "')", " hibas jelszavak rogzitese");
                                }
                                regoldal = regoldal.replaceAll(":##:", "A megadott jelszó, nem az ön által regisztrált jelszó.");
                                regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                                jelszook = false;
                                out.println(regoldal);
//</editor-fold>
                            }
                            if (jelszook) {
//<editor-fold defaultstate="collapsed" desc="nincs meg a token vagy lejár az érvényessége">
                                if (!userlog.atokolvas(atok, c.getTimeInMillis())) {
                                    
                                    regoldal = kereses.lekerdezes(kell.sql(29), " ip: " + request.getLocalAddr() + ""
                                            + "--warning-- password megadasa nelkuli belepesi kiserlet--");
                                    
                                    regoldal = regoldal.replaceAll(":##:", "Az ön által használt jelszóbekérő oldal csak egyszeri belépésre jogosít, "
                                            + "kérem frissítse, vagy jelentkezzen be a főoldalról.");
                                    regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                                    out.println(regoldal);
                                    jelszook = false;
//</editor-fold>
                                } else {

//<editor-fold defaultstate="collapsed" desc="megvan a token és érvényes beírom hogy belépett, létrehozok egy inaktivítást vizsgáló Thread -et">
                                    temp_id = userlog.belepett(id, request.getLocalAddr(), ver);
                                    ver = "";
                                    szal = new Thread(new Inaktivakkileptetese(temp_id));
                                }
//</editor-fold>
                            }
                        }
                    }
                }
//<editor-fold defaultstate="collapsed" desc="nem a játék beléptető oldalról jött és minden oknak tűnik az oldal visszaadásához">
                if (temp_id != -1 || !paswordbelepes) {
                    if (jelszook) {
                        
                        kereses.adatbevitel(kell.sql(40) + 0 + "' WHERE bekuldo_ID=" + id,
                                "---Hibas belepes nullazasa--");

//<editor-fold defaultstate="collapsed" desc="ha nem új a belépés hanem visszatér valamilyen műveletből akkor már van logtempid-je">
                        int logtemp_id;
                        try {
                            logtemp_id = Integer.parseInt(request.getParameter("logtempid"));
                            temp_id = logtemp_id;
                        } catch (NumberFormatException e) {

//<editor-fold defaultstate="collapsed" desc="ha új belépés akor már megnézzük, hogy aktiválva van-e a regisztrációja ha nincsen akkor aktiváljuk">
                            int statusz = 0;
                            try {
                                statusz = Integer.parseInt(kereses.lekerdezes("SELECT bekuldo.aktiv FROM bekuldo WHERE bekuldo.ID=" + id + ";", "Regisztracio aktivitas, specialis statuszok vizsgalata"));
                            } catch (NumberFormatException e1) {
                                //valami database gond
                            }
                            
                            if (statusz == 0) {
                                kereses.adatbevitel(kell.sql(60) + c.getTimeInMillis() + " WHERE bekuldo.ID=" + id + ";", "aktivizalom a regisztraciot");
                            } else if (statusz == 1) { //itt már a saját receptjeit láthatja de mások nem láthatják
                                kereses.adatbevitel(kell.sql(61) + c.getTimeInMillis() + " WHERE bekuldo.ID=" + id + ";", "Utolso belepesi ido setup");
                            }
//</editor-fold>
                        }
//<editor-fold defaultstate="collapsed" desc="ha nem új belépő akkor megnézzük hogy nem módosított e a receptleíráson">
                         AntiSqlInjection injektion = new AntiSqlInjection();
                        String etelneve = request.getParameter("etelnev");
                        String leirasa = request.getParameter("leiras");
                        
                        if ("".equals(etelneve) || etelneve == null || "".equals(leirasa) || leirasa == null); else {
                            kereses.adatbevitel("UPDATE etelek_tmp SET elkeszitese='" + injektion.anti_xxs(leirasa) + "' WHERE etelek_tmp.elnevezese='" + etelneve + "';", "receptleiras update");
                        }
//</editor-fold>
                        regoldal = regoldal.replaceAll(":#LOGOUT#:", String.valueOf(temp_id));
                        out.println(regoldal);
                        userlog.muveletvolt(temp_id, request.getLocalAddr());
//</editor-fold>
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="ha új belépés volt elindítom az inaktivítást ellenörző szálat">
                        if (paswordbelepes) {
                            szal.start();
                        }
//</editor-fold>
                    }
                } else {
                    // ha már be van jelentkezve vagy nem jelentkezett ki az újboli belépés előtt, mielőtt  a rendeszer automatikusan kiléptette volna.
                    userlog.kilepett(temp_id);
                    response.sendRedirect("index2.jsp");
                    
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
