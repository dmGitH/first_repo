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

public class ReceptTorlesValidalas extends HttpServlet {

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
//<editor-fold defaultstate="collapsed" desc="token állapotát ellenörzi">
                int logtempid = 0;
                logtempid = log.atokolvas_logtempidt_advissza_nemtorli(request.getParameter("atok"), c.getTimeInMillis());

                if (logtempid == 0) {
                    regoldal = oldalak.oldal_kerese(1);
                    regoldal = regoldal.replaceAll(":##:", "A válaszul küldött oldalnak lejárt az érvényessége.");
                    regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                    out.println(regoldal);

//</editor-fold>
                } else {
//<editor-fold defaultstate="collapsed" desc="Biztonsági ellenörzés, műveletszám rögzítés">
                    if (!log.muveletvolt(logtempid, request.getLocalAddr())) {
                        regoldal = oldalak.oldal_kerese(1);
                        regoldal = regoldal.replaceAll(":##:", "Az IP címe a bejelentkezése óta megváltozott. A rendszer biztonsági okokból kilépteti. Kérem jelentkezzen be újra.");
                        regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                        out.println(regoldal);
//</editor-fold>
                    } else {
//<editor-fold defaultstate="collapsed" desc="A felhasználónak küldött kérdésre kapott válasz kiértékelése">
                        String valasz = request.getParameter("valasz");

                        if (valasz == null || "".equals(valasz)) {
                            //a valasz nem nem kell törölni a regisztrációt
                            int id = log.User_ID_temp_id_ert(logtempid);
                            if (id == 0) {
                                regoldal = oldalak.oldal_kerese(1);
                                regoldal = regoldal.replaceAll(":##:", "A felhasználó kilépett a műveletet nem lehet végrehajtani.");
                                regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                                out.println(regoldal);
//</editor-fold>
                            } else {
//<editor-fold defaultstate="collapsed" desc="Alapanyagok hozzadása a recepthez visszatöltése">
                                log.atokhosszabbias(request.getParameter("atok"), c.getTimeInMillis());

                                regoldal = oldalak.oldal_kerese(8);

                                regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                                regoldal = regoldal.replaceAll(":#LOGID#:", String.valueOf(logtempid));
                                regoldal = regoldal.replaceAll(":#ATOK#:", request.getParameter("atok"));
//<editor-fold defaultstate="collapsed" desc="a már a recepturához adott alapanyagok listázása">
                                boolean hiba = false;
                                int receptid = 0;
                                try {
                                    receptid = Integer.parseInt(kereses.lekerdezes("SELECT etelek_tmp.ID FROM etelek_tmp WHERE etelek_tmp.elnevezese='" + request.getParameter("parameter") + "';", "etel nevert ID-t"));
                                } catch (NumberFormatException e) {
                                    hiba = true;
                                }
                                if (hiba) {
                                    regoldal = oldalak.oldal_kerese(1);
                                    regoldal = regoldal.replaceAll(":##:", "A kapott paraméterekben hiba van.");
                                    regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                                    out.println(regoldal);
                                } else {
                                    String felvettalapanyagok = kereses.tobberedmenyes_lekerdezes("SELECT alapanyagok_tmp.alapanyag_neve, receptura_tmp.mennyiseg, menyisegiegyseg_tmp.mennyisegi_egyseg"
                                            + " FROM alapanyagok_tmp INNER JOIN receptura_tmp ON receptura_tmp.alapanyagok_id=alapanyagok_tmp.ID "
                                            + "INNER JOIN menyisegiegyseg_tmp ON menyisegiegyseg_tmp.ID=receptura_tmp.menyisegiegyseg_id "
                                            + "INNER JOIN etelek_tmp ON receptura_tmp.etelek_id=etelek_tmp.ID WHERE etelek_tmp.ID=" + receptid + "; ", "alapanyaglista lekerdezes");

//<editor-fold defaultstate="collapsed" desc="Formázás fejléc feliratok lecserélése">
                                    felvettalapanyagok = felvettalapanyagok.replaceAll("alapanyag_neve", "Alapanyag");
                                    felvettalapanyagok = felvettalapanyagok.replaceAll("mennyiseg", "Mennyiség"); //ez lecserélte következő elejét ékezetesre
                                    felvettalapanyagok = felvettalapanyagok.replaceAll("Mennyiségi_egyseg", "Mértékegység");
                                    regoldal = regoldal.replaceAll(":#AALISTA#:", formazas.tablazatban_torlo_gombbal(felvettalapanyagok, request.getParameter("parameter"), request.getParameter("atok")));
//</editor-fold>
//</editor-fold>

                                    regoldal = regoldal.replaceAll(":#ENEVE#:", request.getParameter("parameter"));
                                    regoldal = regoldal.replaceAll(":#parameter#:", request.getParameter("parameter"));
                                    regoldal = regoldal.replaceAll(":#EKAT#:", kereses.lekerdezes("SELECT etel_kategoria_tmp.kategoria FROM etel_kategoria_tmp INNER JOIN etelek_tmp ON"
                                            + " etelek_tmp.etel_kategoria_ID=etel_kategoria_tmp.ID WHERE etelek_tmp.elnevezese='" + request.getParameter("parameter") + "';", "etelnevbol kategoria"));
//---------fel kell tölteni az alapanyaglistát névsor szerint
                                    String alapanyaglista = kereses.tobberedmenyes_lekerdezes(kell.sql(31) + id + " ORDER BY alapanyag_neve ASC;", " ip: " + request.getLocalAddr() + ""
                                            + "--Alapanyaglegordulo ista feltolteshez lekerdezes--");
//
                                    regoldal = regoldal.replaceAll(":#ID#:", String.valueOf(id));
                                    regoldal = regoldal.replaceAll(":#VA#:", formazas.listaba(alapanyaglista));
// <option value="0">válaszzon ki egy alapanyagot</option>
                                    String mennyisegiegyseg = kereses.tobberedmenyes_lekerdezes(kell.sql(32) + id, " ip: " + request.getLocalAddr() + ""
                                            + "--Mennyisegiegyseg ista feltolteshez lekerdezes--");
                                    regoldal = regoldal.replaceAll(":#VME#:", formazas.listaba(mennyisegiegyseg));

//<editor-fold defaultstate="collapsed" desc="receptleírás betöltése ha módosításkor nyitom meg az oldalt receptleiras visszatöltése 3 helyen van ilyen TetelTorolValidalas, AlapanyagFelvetel, ReceptTorlesValidalas ">
//                                    String elkeszitese = kereses.lekerdezes("SELECT"
//                                            + " etelek_tmp.elkeszitese FROM etelek_tmp WHERE etelek_tmp.elnevezese='" + request.getParameter("parameter") + "';", "recept leiras betoltese ha van");
                                    regoldal = regoldal.replaceAll("<textarea id=\"textarea\" name=\"textarea\" cols=\"88\" rows=\"10\"></textarea>",
                                            "<textarea id=\"textarea\" name=\"textarea\" cols=\"88\" rows=\"10\">"
                                            + kereses.lekerdezes("SELECT etelek_tmp.elkeszitese FROM etelek_tmp WHERE etelek_tmp.elnevezese='" + request.getParameter("parameter") + "';",
                                                    "Recept leiras visszatoltese a text mezobe") + "</textarea>");
//</editor-fold>
                                    out.println(regoldal);
//</editor-fold>
                                }
                            }
                        } else {
//<editor-fold defaultstate="collapsed" desc="A recept törlése a recepturával együtt">

                            kereses.adatbevitel("DELETE FROM receptura_tmp WHERE receptura_tmp.etelek_id=(SELECT etelek_tmp.ID FROM etelek_tmp "
                                    + "WHERE etelek_tmp.elnevezese='" + request.getParameter("parameter") + "')", "az adott receptura torlese ");
                            kereses.adatbevitel("DELETE FROM etelek_tmp WHERE etelek_tmp.elnevezese='" + request.getParameter("parameter") + "';", "adott etel torlese");

                            int id = log.User_ID_temp_id_ert(logtempid);

                            regoldal = oldalak.oldal_kerese(6);
                            kereses.adatbevitel(kell.sql(40) + 0 + "' WHERE bekuldo_ID=" + id,
                                    "---Hibas belepes nullazasa--");

                            regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                            regoldal = regoldal.replaceAll(":#LOGID#:", String.valueOf(id));
                            //személyes adatok lekérdezése betöltése az email és a neve fel van cserélve mindenütt még a táblában is bocs
                            String nev = kereses.lekerdezes(kell.sql(42) + id + "\";", " ip: " + request.getLocalAddr() + ""
                                    + "-- login nev lekereses--");
                            regoldal = regoldal.replaceAll(":#BFH#:", nev);

                            String sajatreceptekszama = kereses.lekerdezes(kell.sql(43) + id + ";", " ip: " + request.getLocalAddr() + ""
                                    + "--Sajat receptek szama--");
                            regoldal = regoldal.replaceAll(":#SRSZ#:", sajatreceptekszama);
                            String sajatalapanyagok = kereses.lekerdezes(kell.sql(44) + id + ";", " ip: " + request.getLocalAddr() + ""
                                    + "--Sajat alapanyagok szama--");
                            regoldal = regoldal.replaceAll(":#SASZ#:", sajatalapanyagok);

                            log.atokolvas_nembelepeskor(request.getParameter("atok"), id);
                            regoldal = regoldal.replaceAll(":#LOGOUT#:", String.valueOf(logtempid));
                            out.println(regoldal);
                            userlog.muveletvolt(logtempid, request.getLocalAddr());
//</editor-fold>
//</editor-fold>
                        }
                    }

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
