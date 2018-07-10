package alap;

import database.EgyEredmeny;
import database.LekerdezesStringek;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class adminstat extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            EgyEredmeny kereses = new EgyEredmeny();
            LekerdezesStringek kell = new LekerdezesStringek();
            String adminname = request.getParameter("textfield");
            String adminpass = request.getParameter("pass");
            Hatterkep kep = new Hatterkep();

//<editor-fold defaultstate="collapsed" desc="Csali adminoldal jelszópróbálgatóknak">
            if (adminname == null || "".equals(adminname)); else {

                if (adminpass == null || "".equals(adminpass)); else {

                    kereses.adatbevitel(kell.sql(26) + adminpass + "','" + adminname + "')", ""
                            + "--beprobalt belepesi adatok rogzitese --");

                }

            }

            String regoldal = kereses.lekerdezes(kell.sql(27), " ip: " + request.getLocalAddr() + ""
                    + "--admin oldal megnyitas adminstatrol--");

            regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());

            out.println(regoldal);
//</editor-fold>
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
