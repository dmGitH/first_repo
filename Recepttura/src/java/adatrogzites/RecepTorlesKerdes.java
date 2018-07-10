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

public class RecepTorlesKerdes extends HttpServlet {

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
//<editor-fold defaultstate="collapsed" desc="A bejelentkezettség állapotát ellenörzi">
                int logtempid = Integer.parseInt(request.getParameter("logtempid"));
                //int id = Integer.parseInt(request.getParameter("id"));

                if (!log.bentvagyok(logtempid)) {
                    response.sendRedirect("index.htm");

//</editor-fold>
                } else {
//<editor-fold defaultstate="collapsed" desc="Biztonsági ellenörzés, műveletszám rögzítés">
                    if (!log.muveletvolt(logtempid, request.getLocalAddr())) {
                        regoldal = oldalak.oldal_kerese(1);
                        regoldal = regoldal.replaceAll(":##:", "Az IP címe a bejelentkezése óta megváltozott. A rendszer biztonsági okokból kilépteti. Kérem jelentkezzen be újra.");
                        out.println(regoldal);
//</editor-fold>
                    } else {
//<editor-fold defaultstate="collapsed" desc="kiküldöm a kérdést, hogy biztosan törli e a receptet (tokennel meg minden ami kell hozzá, hogy tudjam ki válaszol)">
                        regoldal = oldalak.oldal_kerese(7);
                        regoldal = regoldal.replaceAll(":##:", "Biztos benne, hogy törli? Étel neve:" + request.getParameter("recepttorlese"));
                        regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                        regoldal = regoldal.replaceAll(":#HOVAKULDI#:", "ReceptTorles");
                        regoldal = regoldal.replaceAll(":#ATOK#:", request.getParameter("atok"));
                        regoldal = regoldal.replaceAll(":#parameter#:", request.getParameter("recepttorlese"));
                        out.println(regoldal);
                    }
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
