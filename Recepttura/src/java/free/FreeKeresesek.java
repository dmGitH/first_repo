package free;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class FreeKeresesek extends HttpServlet {

     private static final long serialVersionUID = 1L;

    @Override
    public void doPost (HttpServletRequest request,
            HttpServletResponse response) {

       try {
           
           request.setAttribute ("servletName", "FreeKeresesek");
           getServletConfig().getServletContext().getRequestDispatcher(
                   "/jsp/keres.jsp").forward(request, response);
       } catch (IOException | ServletException ex) {
       }
    }
}