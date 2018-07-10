package loginok;

import adatrogzites.UserLog;
import alap.Hatterkep;
import database.EgyEredmeny;
import database.HTMLoldalak;
import database.LekerdezesStringek;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Logout extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
//<editor-fold defaultstate="collapsed" desc="kiirom a belépett userek közül és visszaküldöm a főoldalra">
        try (PrintWriter out = response.getWriter()) {
            EgyEredmeny kereses = new EgyEredmeny();
            UserLog userlog = new UserLog();
            LekerdezesStringek kell = new LekerdezesStringek();
            Hatterkep kep = new Hatterkep();
            HTMLoldalak oldalak = new HTMLoldalak();
            int login_temp_id = Integer.parseInt(request.getParameter("logtempid"));
            if (userlog.bentvagyok(login_temp_id)) {
                userlog.kilepett(login_temp_id);
                response.sendRedirect("index.htm");
            } else {
                String regoldal;
                regoldal = oldalak.oldal_kerese(1);
                regoldal = regoldal.replaceAll(":##:", "Ön már ki van jelentkezve.");
                regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                out.println(regoldal);
            }
        }
//</editor-fold>
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
