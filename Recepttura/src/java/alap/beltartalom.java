/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alap;

import adatrogzites.UserLog;
import database.AntiSqlInjection;
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

/**
 *
 * @author dm
 */
public class beltartalom extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

//<editor-fold defaultstate="collapsed" desc="paraméterek, válzók">
        LekerdezesFormazasa formazas = new LekerdezesFormazasa();
        String regoldal = null;
        UserLog log = new UserLog();
        EgyEredmeny kereses = new EgyEredmeny();
        Hatterkep kep = new Hatterkep();
        HTMLoldalak oldalak = new HTMLoldalak();
        LekerdezesStringek kell = new LekerdezesStringek();
        Calendar c = Calendar.getInstance();
        Map<String, String[]> list = request.getParameterMap();
        AntiSqlInjection vansql = new AntiSqlInjection();
//</editor-fold>
        try (PrintWriter out = response.getWriter()) {

//<editor-fold defaultstate="collapsed" desc="Az oldalt közvetlen a link beírásával akarják megnyitni">
            if (list.isEmpty()) {

                regoldal = oldalak.oldal_kerese(1);
                regoldal = regoldal.replaceAll(":##:", "Az oldal nem érhető el közvetlenül.");
                regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                out.println(regoldal);
                //</editor-fold>
            } else {
//<editor-fold defaultstate="collapsed" desc="A bejelentkezettség állapotát ellenörzi">
                int logtempid = Integer.parseInt(request.getParameter("logtempid"));
                int id = log.User_ID_temp_id_ert(logtempid);
                if (!log.bentvagyok(logtempid)) {
                    response.sendRedirect("index.htm");

//</editor-fold>
                } else {

//<editor-fold defaultstate="collapsed" desc="Biztonsági ellenörzés, műveletszám rögzítés">
                    if (log.muveletvolt(logtempid, request.getLocalAddr())); else {
                        regoldal = oldalak.oldal_kerese(1);
                        regoldal = regoldal.replaceAll(":##:", "Az IP címe a bejelentkezése óta megváltozott. A rendszer biztonsági okokból kilépteti. Kérem jelentkezzen be újra.");
                        out.println(regoldal);
//</editor-fold>

                    }
//<editor-fold defaultstate="collapsed" desc="egy étel beltartalmi értékeinek a lekérdezése és megjelenítése">
//response.sendRedirect("http://127.0.0.1:80/PhpJAwaTeszt/index2.php"); //itt meg lehet adni, hogy  milyen címre menjen
String fejlec = oldalak.oldal_kerese(10);
fejlec = fejlec.replaceAll(":#K#:KEPET", kep.kepet());
out.println(fejlec);
//egyféle beltartalmi érték
String etelneve = request.getParameter("etelneve");
int etel_id = 0;
int alapanyag_beltartalom_id = 0;
try {
    alapanyag_beltartalom_id = Integer.parseInt(request.getParameter("alapanyagid"));
} catch (NumberFormatException e) {
    out.println("<h1 align='center' class='style6'>Hiba a visszaadott értékekben alapanyag id</h1> <br>" + e);
}
if (alapanyag_beltartalom_id != 0) {
    //out.println(etelneve + " alapanyag id: " + alapanyag_id);
    try {
        etel_id = Integer.parseInt(kereses.lekerdezes("CALL etelnevert_etelid ('" + etelneve + "');", ""));
    } catch (NumberFormatException e) {
        out.println("<h1 align='center' class='style6'>Hiba a visszaadott értékekben étel id</h1> <br>" + e);
    }
    //out.println("eteid "+etel_id+" alapanyagid "+alapanyag_id);
    String keresett_ertek = kereses.lekerdezes("CALL Receptura_egy_alapanyaganyak_mennyisege(" + etel_id + "," + alapanyag_beltartalom_id + ");", "");
    String beltartalom_neve = kereses.lekerdezes(" CALL `beltartalom_id_ert_a_neve`(" + alapanyag_beltartalom_id + ");", "");
    String mennyisegi_egysege = kereses.lekerdezes(" CALL `meertekegyseg_opcionalis_beltartalom_id_ert`(" + alapanyag_beltartalom_id + ");", "");
    if (keresett_ertek == null || keresett_ertek.equals("null")) {
        out.println("<h1 align='center' class='style6'>A keresett beltartalmi egység nincsen megadva valamelyik alapanyaghoz.</h1> <br>");
    } else {
        out.println("<h1 align='center' class='style6'>A(z) " + etelneve + " nevü étel 1 adagjában található: "
                + keresett_ertek + " " + mennyisegi_egysege + " " + beltartalom_neve + "</h1> <br>");
    }
} else {
    //out.println("mindent meg kell jekeníteni ág");
    int[] parameterek;
    parameterek = formazas.egyoszlopos_szamlista_fejlec_nelkul(kereses.tobberedmenyes_lekerdezes("SELECT opcionalisbeltartalom_tmp.ID FROM"
            + " `opcionalisbeltartalom_tmp` ORDER BY opcionalisbeltartalom_tmp.osszetevo ;", ""));
    //out.println("tomb hossza: " + parameterek.length);
    try {
        etel_id = Integer.parseInt(kereses.lekerdezes("CALL etelnevert_etelid ('" + etelneve + "');", ""));
    } catch (NumberFormatException e) {
        out.println("<h1 align='center' class='style6'>Hiba a visszaadott értékekben étel id</h1> <br>" + e);
    }
    out.println("<h1 align='center' class='style6'>A(z) " + etelneve + " nevü étel 1 adagjában található:</h1> <br>");
    for (int i = 0; i < parameterek.length; i++) {
        alapanyag_beltartalom_id = parameterek[i];
        String keresett_ertek = kereses.lekerdezes("CALL Receptura_egy_alapanyaganyak_mennyisege(" + etel_id + "," + alapanyag_beltartalom_id + ");", "");
        String beltartalom_neve = kereses.lekerdezes(" CALL `beltartalom_id_ert_a_neve`(" + alapanyag_beltartalom_id + ");", "");
        String mennyisegi_egysege = kereses.lekerdezes(" CALL `meertekegyseg_opcionalis_beltartalom_id_ert`(" + alapanyag_beltartalom_id + ");", "");
        if (keresett_ertek == null || keresett_ertek.equals("null")) {
            //out.println("A keresett beltartalmi egység nincsen megadva valamelyik alapanyaghoz.");
        } else {
            out.println("<h3 align='center' class='dingbat'>" + keresett_ertek + " " + mennyisegi_egysege + " " + beltartalom_neve + "</h3>");
        }
        
    }
}
out.println("</bady></html>");
//response.addHeader("fejlec", fejlec);
//</editor-fold>
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
//<editor-fold defaultstate="collapsed" desc="A get ből post lett">
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        doGet(request, response);
    }
//</editor-fold>

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
