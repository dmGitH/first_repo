<%-- 
    Document   : publikus alapanyagokközötti keresés
    Created on : 2018.05.28., 19:10:00
    Author     : dm
--%>
<html>
    <head>
        <%@page import="database.LekerdezesStringek"%>
        <%@page import="database.EgyEredmeny"%>
        <%@page import="database.LekerdezesFormazasa"%>
        <%@page contentType="text/html" pageEncoding="UTF-8"%>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <link rel="stylesheet" href="../css/alap.css"> 
        <title>Alapanyagok</title>

        <style type="text/css">

           
            body{
                <% int eleje = 1;
                    int vege = 55;
                    int sors = (int) (Math.random() * vege) + 1;
                    out.print("background-image: url(../img/" + sors + ".jpeg);");%>
            }
          
        </style>
        <script type="text/javascript">
            function fooldalra() {
                window.location.replace("/Recepttura");

            }
        </script>
    </head>
    <body>
        <br>
        <h1 align="center" class="style6">Alapanyagok beltartalmi értékei/100g</h1>

        <form name="form1" method="get" action="publikus_alapanyagok.jsp" onsubmit="">
            <div align="center" class="style6"><br>
                Alapanyag nevében szerpelhet<br><br>
                <input  name="textfield"  type="text"  tabindex="1" autofocus>

                <input name="kuldve" type="submit"  class="title" tabindex="2" value="Keresés"><p></p>     
            </div>
        </form>
        <%
            String keresem = request.getParameter("textfield");
            if (keresem == null || "".equals(keresem)) {
                keresem = "¤";
            }
            if ("*".equals(keresem)) {
                keresem = "";
            }
            EgyEredmeny kereses = new EgyEredmeny();
            LekerdezesFormazasa kimenet = new LekerdezesFormazasa();
            String eredmeny="";
            try {

                eredmeny = kereses.tobberedmenyes_lekerdezes("CALL Publikus_alapanyagok_parameterei(\"%" + keresem + "%\")", "");

            } catch (Exception e) {
                out.print("<h1 align='center' class='style20'> >>>>>> HIBA KÓD: 500. SQL injektion alarm. Your IP: " + request.getLocalAddr() + " posted admin <<<<<< </h1>");
            }
            
            out.print("<div align='center'>"+kimenet.tablazatban(eredmeny)+"</div>");
        %>  

<!--        <table border="3" align="center" >
            <tr class="style20">
                <td><br/><div align="center">Alapanyag neve:</div><br/></td>
                <td><br/><div align="center">összetevő:</div><br/></td>
                <td><br/><div align="center">mennyiség/100g-ban :</div><br/></td>
                <td><br/><div align="center">mennyiségi egység: </div><br/></td></tr>
            <c:forEach var="row" items="${rs.rows}">
                ${row.egyadagban}
                <tr class="footer"><td bgcolor="#FFFF00"><div align="center"> ${row.alapanyag_neve}</div></td>
                    <td><div align="center">${row.osszetevo}</div></td>
                    <td bgcolor="lightgreen"><div align="center"> ${row.mennyiseg}</div></td>
                    <td><div align="center">${row.mennyisegi_egyseg}</div></td></tr>
            </c:forEach>
        </table>-->
        <P align="center" class="style20"><A href="javascript:fooldalra();" >
                Vissza a főoldalra</A></P>
    </body>
</html>
