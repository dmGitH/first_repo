/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loginok;

import adatrogzites.UserLog;
import alap.Hatterkep;
import database.EgyEredmeny;
import database.HTMLoldalak;
import database.LekerdezesStringek;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dm
 */
public class Login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    String css;
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        //response.addHeader("Server message", String.valueOf(response.hashCode()));
        response.setLocale(Locale.forLanguageTag("HU"));
        response.setHeader("validation", String.valueOf(response.hashCode()));
        Hatterkep kep = new Hatterkep();
        EgyEredmeny kereses = new EgyEredmeny();
        LekerdezesStringek kell = new LekerdezesStringek();
        UserLog userlog = new UserLog();
        Calendar c = Calendar.getInstance();
        HTMLoldalak oldalak = new HTMLoldalak();

//<editor-fold defaultstate="collapsed" desc="regisztrációs oldal elküldése, tokennel, robotteszttel stb....">
        try (PrintWriter out = response.getWriter()) {
            
            Validator val = new Validator();
            val.general();
            
            String regoldal = oldalak.oldal_kerese(0);
            
            regoldal = regoldal.replaceAll(":#CSS#:", css);
            regoldal = regoldal.replaceAll("eztkelllecserelni", val.getMuvelet());
            regoldal = regoldal.replaceAll(":##:", String.valueOf(val.getEredmeny().toLowerCase().hashCode()));
            regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
            regoldal = regoldal.replaceAll(":#ATOK#:", userlog.atokir(1, c.getTimeInMillis(),600000, "reg"));
            response.addHeader("Validator", String.valueOf(val.getEredmeny().toLowerCase().hashCode()));
            
            out.println(regoldal);
            
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
