package free;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dm
 */
public class kalk extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Konverter</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
            out.print(" <style type=\"text/css\">\n"
                    + "        -->  \n"
                    + "input[type=\"text\"]{\n"
                    + "    background-color: lightgreen;\n"
                    + " width: 500px;"
                    + "    border-radius:5px;\n"
                    + "    font-weight: bold; color: black;  \n"
                    + "}"
                    + "        <--\n"
                    + "    </style>");
            out.println("</head>");

            out.println("<body>");
            String body = "<div align=\"center\">\n"
                    + "<label>\n"
                    + "<span class=\"dingbat\">Kodolandó érték</span> \n"
                    + "<form id=\"form1\" name=\"form1\" method=\"post\" action=\"kalk\" onsubmit=\"return validateForm()\" align=\"center\">\n"
                    + "<input id='bm' name='textfield' type='text' tabindex='1' value='' autofocus>"
                    + "<div align=\"center\">\n"
                    + "<input name=\"Submit\" type=\"submit\" class=\"title\" tabindex=\"8\" value=\"Konvertál\">\n"
                    + "</form><br><br></div>";

            out.println("<h1 align='center'>Konverter</h1>");
            out.println(body);
            String ertek = request.getParameter("textfield");
            String kodolt = "";
            String hexhtml = "";
            String url = "";
            out.println("<div>ASCII Decimal htmlkód: ");
            for (int i = 0; i < ertek.length(); i++) {
                if(ertek.charAt(i)=='Đ')kodolt=kodolt + "&#272;" ;
                else kodolt = kodolt + "&#" + ertek.codePointAt(i) + ";";

                out.println("&#");
                out.print(ertek.codePointAt(i) + ";");
            }
            //out.println("</div><input típe=\"text\" value=\"" + kodolt + "\"><br><br><div>");
            out.println("</div><br><br><div>");

            out.println("<div>URL kód: ");
            for (int i = 0; i < ertek.length(); i++) {
                url = url + "%" + Integer.toHexString(ertek.codePointAt(i)) + ";";

                out.print("%");
                out.print(Integer.toHexString(ertek.codePointAt(i)));
            }
            out.println("</div><br><br><div>");

            out.println("ASCII Hexa htmlkód: ");
            for (int i = 0; i < ertek.length(); i++) {

                hexhtml = hexhtml + "&#x" + Integer.toHexString(ertek.codePointAt(i)) + ";";
                out.println("&#x");
                out.print(Integer.toHexString(ertek.codePointAt(i)) + ";");
            }
            out.println("<div><br><br><div>ASCII HTML decod :  " + kodolt + "<br><br><div>");
            // out.println("<div><br><br><div>URL decod :  " + url + "<br><br><div>");
            out.println("<div><br>HEX HTML decod:  " + hexhtml + "<br><br><div>");

            out.println("<br>Eredeti: " + ertek);

            String base;
            base = Base64.getEncoder().encodeToString(ertek.getBytes());
            out.println("<br><br>Base64 encode : " + base + "<br>");
            try {
                base = new String(Base64.getDecoder().decode(ertek));
                out.println("<br><br>Base64 decode : " + base + "<br>");

            } catch (Exception e) {
                out.println("<br><br>Nem Base64 érték<br>");
            }
            out.println("<br><P align=\"center\" class=\"style20\"><A href=\"javascript:window.location.replace('/Recepttura');\" >"
                    + "Vissza a főoldalra</A></P></body>");
            out.println("</html>");
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
