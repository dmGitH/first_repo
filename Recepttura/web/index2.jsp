<%@page import="database.LekerdezesStringek"%>
<%@page import="database.EgyEredmeny"%>
<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//HU">
<html>
    <head>
        <title>Kezdőlap</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <link rel="stylesheet" href="css/alap.css">    


        <style type="text/css">
           
            body{
                <% int eleje = 1;
                    int vege = 55;
                    int sors = (int) (Math.random() * vege) + 1;
                    out.print("background-image: url(img/" + sors + ".jpeg);"); %>
            }
            
        </style>

        <script type="text/javascript" src="scr/alap.js"></script>  
    </head>
    <body onload="elozmenyclear();" onunload="torol();">
        <div align="center">
            <p>&nbsp;</p>
            <p><span class="style6">Ételreceptek, alapanyagok, beltartalmi értékekek, online kalkulációk </span><br>
                <br>
                <span class="style8">Regisztált felhasználóknak személyes receptadatbázis, saját alapanyagadatbázis kialakításának, megosztásának lehetőségével </span><br>
            </p>
        </div>
        <table width="1091" height="144" border="3" align="center" bordercolor="#CCCC99" bgcolor="#AA9F55">
            <tr>
                <td width="1077" height="134"><table width="1059" height="101" border="2" align="center" bordercolor="#2A0000">
                        <tr>
                            <td width="174" height="97" class="navLink"><div align="center" class="style4">
                                    <p class="style4"> Információk</p>
                                    <form name="form5" method="post" action="info">
                                        <label>
                                            <input name="info" type="submit" class="sidebarHeader" id="info" tabindex="5" value="Tovább">
                                        </label>
                                    </form>
                                </div></td>
                            <td width="198" class="navLink"><div align="center" class="style4">
                                    <p> Online kalkulátorok</p>
                                    <form name="form4" method="post" action="kalk">
                                        <label>
                                            <input name="kalk" type="submit" class="sidebarHeader" id="kalk" tabindex="4" value="Tovább">
                                        </label>
                                    </form>
                                </div></td>
                            <td width="199" class="navLink"><div align="center" class="style4">
                                    <p> Segédanyagok </p>
                                    <form name="form3" method="post" action="segedlet">
                                        <label>
                                            <input name="alkalmazasok" type="submit" class="sidebarHeader" id="alkalmazasok" tabindex="3" value="Tovább">
                                        </label>
                                    </form>
                                </div></td>
                            <td width="230" class="navLink"><div align="center" class="style4">
                                    <p> Keresés az adatbázisban </p>
                                    <form name="form2" method="post" action="keresesek">
                                        <label>
                                            <input name="kereses" type="submit" class="sidebarHeader" id="kereses" tabindex="2" value="Tovább">
                                        </label>
                                    </form>
                                </div></td>
                            <td width="222" class="navLink"><div align="center" class="style4">
                                    <p> Regisztráció</p>
                                    <form name="form1" method="post" action="Login">
                                        <label>
                                            <input name="login" type="submit" class="sidebarHeader" id="regisztralas" tabindex="1" value="Tovább">
                                        </label>
                                    </form>
                                </div></td>
                        </tr>
                    </table></td>
            </tr>
        </table>
        <br>

        <table width="700" height="300" border="5" align="center" bordercolor="#AA9F55" >
            <tr bgcolor="#F0F0F0">

            <tr>
                <td width="725" height="32"><div align="center" class="dingbat">Nem jelentkezett ki vagy lejárt az előző oldal érvényessége. Biztonsági okokból, kérem ismételje meg a bejelentkezést </div></td>
            </tr>
            <tr>
                <td height="279" align="center" nowrap bordercolor="#F0F0F0" background="img/IMG_4108.JPG"><br>
                    <table width="325" height="187" border="5" align="center" cellpadding="1" cellspacing="1" bordercolor="#C0DCC0" class="footer">
                        <tr>
                            <td width="319" height="183" bgcolor="#FFFF99"><p align="center">
                                <p align="center">
                                <div align="center">
                                    <label>
                                        <span class="dingbat">Login név</span> 
                                        <form name="belep" method="post" action="belepes" onsubmit="return validateForm()" align="center">
                                    </label>
                                </div>
                                <p align="center">
                                    <label>
                                        <input id="bm" name="textfield" type="text" tabindex="6" value="" autofocus>
                                    </label>
                                </p>
                                <p align="center">
                                    <label></label>
                                </p>

                                <div align="center">
                                    <input name="Submit" type="submit" class="title" tabindex="8" value="Belépés">
                                </div>
                                </p>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>

            <br>
            <tr width="700" height="250" border="5" align="center" bordercolor="#C0DCC0">

                <td width="706"><p align="center" class="style9">Az adatbázisban jelenleg található</p>
                            <p align="left" class="sidebarHeader">Regisztrált felhasználó:
                                <%EgyEredmeny kereses = new EgyEredmeny();
                                    LekerdezesStringek kell = new LekerdezesStringek();
                                    String a = kereses.lekerdezes(kell.sql(1), "IP: " + request.getLocalAddr() + " Index oldal-1--");
                                    String b = kereses.lekerdezes(kell.sql(2), " Index oldal-2--");
                                    String c = kereses.lekerdezes(kell.sql(3), " Index oldal-3--");
                                    String d = kereses.lekerdezes(kell.sql(4), " Index oldal-4--");
                                    String e = kereses.lekerdezes(kell.sql(5), " Index oldal-5--");
                                    String f = kereses.lekerdezes(kell.sql(6), " Index oldal-6--");
                                    out.print(" " + a + "</p><p align=\"left\" class=\"sidebarHeader\">Felhasználói ételreceptek: " + b
                                            + "</p><p align=\"left\" class=\"sidebarHeader\">Megosztott ételreceptek: " + c
                                            + "</p><p align=\"left\" class=\"sidebarHeader\">Törzsadat ételreceptek: " + d
                                            + "</p><p align=\"left\" class=\"sidebarHeader\">Alapanyag: " + e
                                            + "</p><p align=\"left\" class=\"sidebarHeader\">Alapanyag tulajdonság"
                                            + ": " + f + "</p></td>"); %>
            </tr>
        </table>
        <br>    
        <table width="1093" height="41" border="1" align="center" bgcolor="#55DFFF">
            <tr>
                <td width="1091" height="35" bgcolor="#FFFFFF"><div align="center" class="footer">

                        <div align="center"><% 
                           // x = Calendar.getInstance();
                            out.print("Az oldalt megnyitotta: ");%>  
                           <%= new java.util.Date().toLocaleString() %>
                            </div>
                    </div></td>
            </tr>
        </table>
    </body>
    <% response.addHeader("Server message", String.valueOf(response.hashCode()));%>
</html>

