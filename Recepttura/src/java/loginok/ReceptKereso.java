package loginok;

import adatrogzites.UserLog;
import alap.Hatterkep;
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
 * Bejelentkezett felhasználó receptjeinek a megjelnítése
 */
public class ReceptKereso extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

//<editor-fold defaultstate="collapsed" desc="paraméterek, válzók">
        LekerdezesFormazasa formazas = new LekerdezesFormazasa();
        String regoldal;
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
                int id = Integer.parseInt(request.getParameter("id"));
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
//<editor-fold defaultstate="collapsed" desc="tokenírás oldal betöltése felparaméterezése">
                    regoldal = oldalak.oldal_kerese(9);
                    regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                    regoldal = regoldal.replaceAll(":#LOGID#:", String.valueOf(logtempid));
                    String at = request.getParameter("atok");
                    boolean lejart = false;
                    String atok = null;
                    if (at != null) {
                        if (!log.atokolvas_nembelepeskor(at, id)) {
                            lejart = true;
                        }
                    }
                    if (lejart) {
                        regoldal = oldalak.oldal_kerese(1);
                        regoldal = regoldal.replaceAll(":##:", "Az oldal érvényessége lejárt.");
                        out.println(regoldal);
                    } else {
                        atok = log.atokir(id, c.getTimeInMillis(), 600000, "receptkereso");
                        regoldal = regoldal.replaceAll(":#ATOK#:", atok);
                        regoldal = regoldal.replaceAll(":#ID#:", String.valueOf(id));
                        regoldal = regoldal.replaceAll(":#LOGOUT#:", String.valueOf(logtempid));
                        regoldal = regoldal.replaceAll(":#EKAT#:", "<option value=\"" + 0 + "\">" + "-Válasszon" + "</option>\n" + formazas.listaba(kereses.tobberedmenyes_lekerdezes("SELECT etel_kategoria_tmp.ID,etel_kategoria_tmp.kategoria "
                                + "FROM etel_kategoria_tmp INNER JOIN user_etelkategoriai ON user_etelkategoriai.etel_kategoria_tmp_ID=etel_kategoria_tmp.ID "
                                + "WHERE user_etelkategoriai.bekuldo_ID=" + id + " GROUP BY etel_kategoria_tmp.kategoria;", "etelkategoriak legordulolista feltoltese a user etelkategoriaival")));
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="keresési paraméterek kezelése ellenörzése validálása">
                        String en = request.getParameter("etelneve");
                        String etelneve;
                        if (en == null) {
                            etelneve = "";
                        } else {
                            etelneve = en;
                        }
                        String an = request.getParameter("alapanyag");
                        String alapanyagneve;
                        if (an == null) {
                            alapanyagneve = "";
                        } else {
                            alapanyagneve = an;
                        }
                        String ek = request.getParameter("leiras");
                        String elkeszitese;
                        if (ek == null) {
                            elkeszitese = "";
                        } else {
                            elkeszitese = ek;
                        }
                        int etelkategoria_ID;
                        try {
                            etelkategoria_ID = Integer.parseInt(request.getParameter("etelkategoria"));
                        } catch (NumberFormatException e) {
                            etelkategoria_ID = 0;
                        }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="A beltartalmi lista első elemének a létrehozása --összes néven">
                        String beltartalom = formazas.listaba(kereses.tobberedmenyes_lekerdezes("SELECT "
                                + "opcionalisbeltartalom_tmp.ID,opcionalisbeltartalom_tmp.osszetevo FROM opcionalisbeltartalom_tmp"
                                + " ORDER BY opcionalisbeltartalom_tmp.osszetevo;", "beltartalom_tmp tartalma"));
                        beltartalom = "<option value=\"" + 0 + "\">" + "--Összes" + "</option>\n" + beltartalom;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="A keresést és a találati lista sql, html-be formázva">
                        regoldal = regoldal.replaceAll(":#LEKE#:", formazas.tablazatba_kiemelt_mezokkel(
                                kereses.tobberedmenyes_lekerdezes(
                                        "CALL `Felhasznalo_receptjeiben_kereses`(" + etelkategoria_ID + ","
                                        + "\"%" + etelneve + "%\","
                                        + "\"%" + alapanyagneve + "%\","
                                        + "\"%" + elkeszitese + "%\","
                                        + id +");", "felhasznaloi receptekben kereses"), atok, logtempid));

//</editor-fold>
                        //a beltartalmi értékek lgördülő listájának az elhelyezése az eredményben html
                        regoldal = regoldal.replaceAll(":#OPCBE#:", beltartalom);
                        out.println(regoldal);
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
