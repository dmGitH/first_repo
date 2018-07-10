package loginok;

import adatrogzites.UserLog;
import alap.Hatterkep;
import database.EgyEredmeny;
import database.HTMLoldalak;
import database.LekerdezesFormazasa;
import database.LekerdezesStringek;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UnregValidalas", urlPatterns = {"/UnregValidalas"})
public class UnregValidalas extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
//<editor-fold defaultstate="collapsed" desc="fontos beállítani a magyar hosszú ékezetes betük miatt">
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="paraméterek, válzók">
        LekerdezesFormazasa formazas = new LekerdezesFormazasa();
        String regoldal;
        UserLog log = new UserLog();
        EgyEredmeny kereses = new EgyEredmeny();
        Hatterkep kep = new Hatterkep();
        UserLog userlog = new UserLog();
        LekerdezesStringek kell = new LekerdezesStringek();
        Calendar c = Calendar.getInstance();
        Map<String, String[]> list = request.getParameterMap();
        HTMLoldalak oldalak = new HTMLoldalak();
//</editor-fold>
        try (PrintWriter out = response.getWriter()) { //"respons nyomtató"
//<editor-fold defaultstate="collapsed" desc="Az oldalt közvetlen a link beírásával akarják megnyitni">
            if (list.isEmpty()) {

                regoldal = oldalak.oldal_kerese(1);
                regoldal = regoldal.replaceAll(":##:", "Az oldal nem érhető el közvetlenül.");
                regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                out.println(regoldal);
                //</editor-fold>
            } else {
//<editor-fold defaultstate="collapsed" desc="token állapotát ellenörzi">
                int logtempid = 0;
                logtempid = log.atokolvas_logtempidt_advissza(request.getParameter("atok"), c.getTimeInMillis());

                if (logtempid == 0) {
                    regoldal = oldalak.oldal_kerese(1);
                    regoldal = regoldal.replaceAll(":##:", "A válaszul küldött oldalnak lejárt az érvényessége.");
                    regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                    out.println(regoldal);

//</editor-fold>
                } else {
//<editor-fold defaultstate="collapsed" desc="Biztonsági ellenörzés, műveletszám rögzítés">
                    if (!log.muveletvolt(logtempid, request.getLocalAddr())) {
                        regoldal = oldalak.oldal_kerese(1);
                        regoldal = regoldal.replaceAll(":##:", "Az IP címe a bejelentkezése óta megváltozott. A rendszer biztonsági okokból kilépteti. Kérem jelentkezzen be újra.");
                        regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                        out.println(regoldal);
//</editor-fold>
                    } else {
//<editor-fold defaultstate="collapsed" desc="A felhasználónak küldött kérdésre kapott válasz kiértékelése">
                        String valasz = request.getParameter("valasz");

                        if (valasz == null || "".equals(valasz)) {
                            //a valasz nem nem kell törölni a regisztrációt
                            int id = log.User_ID_temp_id_ert(logtempid);
                            if (id == 0) {
                                regoldal = oldalak.oldal_kerese(1);
                                regoldal = regoldal.replaceAll(":##:", "A felhasználó kilépett a műveletet nem lehet végrehajtani.");
                                regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                                out.println(regoldal);
//</editor-fold>
                            } else {
//<editor-fold defaultstate="collapsed" desc="a profiloldal visszatöltése">
                                regoldal = oldalak.oldal_kerese(6);
                                regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                                regoldal = regoldal.replaceAll(":#LOGID#:", String.valueOf(id));
//személyes adatok lekérdezése betöltése az email és a neve fel van cserélve mindenütt még a táblában is bocs :)
                                String nev = kereses.lekerdezes(kell.sql(42) + id + "\";", " ip: " + request.getLocalAddr() + ""
                                        + "-- login nev lekereses--");
                                regoldal = regoldal.replaceAll(":#BFH#:", nev);

                                String sajatreceptekszama = kereses.lekerdezes(kell.sql(43) + id + ";", " ip: " + request.getLocalAddr() + ""
                                        + "--Sajat receptek szama--");
                                regoldal = regoldal.replaceAll(":#SRSZ#:", sajatreceptekszama);
                                String sajatalapanyagok = kereses.lekerdezes(kell.sql(44) + id + ";", " ip: " + request.getLocalAddr() + ""
                                        + "--Sajat alapanyagok szama--");
                                regoldal = regoldal.replaceAll(":#SASZ#:", sajatalapanyagok);
                                regoldal = regoldal.replaceAll(":#LOGOUT#:", String.valueOf(logtempid));
                                out.println(regoldal);
//</editor-fold>
                            }
                        } else {
//<editor-fold defaultstate="collapsed" desc="User minden felvitt adatának a törlése a Userrel együtt">
                            //out.println("Adatok törlése folyamatban");
                            int id = log.User_ID_temp_id_ert(logtempid);
                            String alapanyagid_string = kereses.tobberedmenyes_lekerdezes(kell.sql(68) + id + ";", "alapanyag id lista kerese user id szerint");
                            int[] alapanyagid = formazas.egyoszlopos_szamlista_fejlec_nelkul(alapanyagid_string);
                            for (int i = 0; i < alapanyagid.length; i++) {

                                kereses.adatbevitel(kell.sql(69) + alapanyagid[i] + ";", "opcionalis alapanyagkapcsolo_tmp bol torles");
                                kereses.adatbevitel(kell.sql(70) + alapanyagid[i] + ";", "receptura_tmp-bol az alapanyag torlese");

                            }

                            kereses.adatbevitel(kell.sql(71) + id + ";", "regisztraciotorlese_adattorlesek");
                            kereses.adatbevitel(kell.sql(72) + id + ";", "regisztraciotorlese_adattorlesek");
                            kereses.adatbevitel(kell.sql(73) + id + ";", "regisztraciotorlese_adattorlesek");
                            kereses.adatbevitel(kell.sql(74) + id + ";", "regisztraciotorlese_adattorlesek");
                            kereses.adatbevitel(kell.sql(75) + id + ";", "regisztraciotorlese_adattorlesek");
                            kereses.adatbevitel(kell.sql(76) + id + ";", "regisztraciotorlese_adattorlesekt");
                            kereses.adatbevitel(kell.sql(77) + id + ";", "aregisztraciotorlese_adattorlesek");
                            kereses.adatbevitel(kell.sql(78) + id + ";", "regisztraciotorlese_adattorlesekt");
                            kereses.adatbevitel(kell.sql(79) + id + ";", "regisztraciotorlese_adattorlesek");
                            kereses.adatbevitel(kell.sql(80) + id + ";", "regisztraciotorlese_adattorlesek");
                            kereses.adatbevitel(kell.sql(81) + id + ";", "Felhasznalo torolve");
                            kereses.adatbevitel("DELETE FROM receptura_tmp WHERE receptura_tmp.etelek_id NOT IN (SELECT etelek_tmp.ID FROM etelek_tmp)",
                                    "minden alapanyagot a receptura tablabol aminek nincsen receptje ");
                            //logintemp lezárás és azt kell beírni, hogy a regisztráció törölve
                            log.unregisztracio(logtempid);
                            response.sendRedirect("index.htm");
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

}
