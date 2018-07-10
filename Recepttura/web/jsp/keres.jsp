<%-- 
    Document   : test
    Created on : 2018.05.28., 19:10:00
    Author     : dm
--%>
<html>
    <head>
        <%@page contentType="text/html" pageEncoding="UTF-8"%>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
       
        <link rel="stylesheet" href="css/alap.css"> 
        <title>Keresések</title>
       <style type="text/css">

           
            body{
                <% int eleje = 1;
                    int vege = 55;
                    int sors = (int) (Math.random() * vege) + 1;
                    out.print("background-image: url(img/" + sors + ".jpeg);");%>
            }
            
        </style>
        <script type="text/javascript">
            function oldal(){
                
                window.location.replace("./jsp/publikus_alapanyagok.jsp");
                
            }
        </script>
    </head>
    <body>
       

         <h1 align="center" class="style6">Listák, keresések</h1>
         <table border="3" align="center" >
             <tr class="style3">
                 <td><P><A href="javascript:oldal();">
                Publikus alapanyag lista</A></P></td>
                <td>Alapanyagok listájában név szerinti keresés, beltartalmi értékek, megjelenítésével</td>
               </tr>
                
        </table>
        
    </body>
</html>
