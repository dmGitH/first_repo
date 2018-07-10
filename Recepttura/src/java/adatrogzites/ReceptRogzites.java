package adatrogzites;

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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//<editor-fold defaultstate="collapsed" desc="A recept fejlécének: neve kategóriája stb megadó html kiküldése, receptfelvétel 1. lépés">
public class ReceptRogzites extends HttpServlet {

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
        UserLog userlog = new UserLog();
        LekerdezesStringek kell = new LekerdezesStringek();
        Calendar c = Calendar.getInstance();
        Map<String, String[]> list = request.getParameterMap();
        HTMLoldalak oldalak = new HTMLoldalak();
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
                int id;
                try {
                    id = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException e) {
                    id=userlog.User_ID_temp_id_ert(logtempid);
                }
                if (!log.bentvagyok(logtempid)) {
                    response.sendRedirect("index.htm");

//</editor-fold>
                } else {

//<editor-fold defaultstate="collapsed" desc="Az legördülő listák feltöltése adattal az adatbázisból, tokenek azonosítók elhelyezése">
                    regoldal = oldalak.oldal_kerese(5);

                    regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                    regoldal = regoldal.replaceAll(":#LOGID#:", String.valueOf(logtempid));
                    regoldal = regoldal.replaceAll(":#ATOK#:", userlog.atokir(id, c.getTimeInMillis(), 1200000, "Recept"));
                    regoldal = regoldal.replaceAll(":#HL#:", "Még nincsen hozzáadva alapanyag");
                    regoldal = regoldal.replaceAll(":#EMODJA#:", "");

                    regoldal = regoldal.replaceAll(":#ID#:", String.valueOf(id));

                    String etelkategoria = kereses.tobberedmenyes_lekerdezes(kell.sql(33) + id + " ORDER BY kategoria ASC;", " ip: " + request.getLocalAddr() + ""
                            + "--Etelkategoria ista feltolteshez lekerdezes--");
                    regoldal = regoldal.replaceAll(":#VEK#:", formazas.listaba(etelkategoria));
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Biztonsági ellenörzés, műveletszám rögzítés">
                    if (log.muveletvolt(logtempid, request.getLocalAddr())); else {
                        regoldal = oldalak.oldal_kerese(1);
                        regoldal = regoldal.replaceAll(":##:", "Az IP címe a bejelentkezése óta megváltozott. A rendszer biztonsági okokból kilépteti. Kérem jelentkezzen be újra.");
//</editor-fold>

                    }
                    out.println(regoldal);
                }

            }
        }
    }
//</editor-fold>

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
