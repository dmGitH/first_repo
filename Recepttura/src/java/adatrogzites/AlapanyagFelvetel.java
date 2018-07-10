package adatrogzites;

import alap.Hatterkep;
import database.AntiSqlInjection;
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

public class AlapanyagFelvetel extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

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
        AntiSqlInjection vansql = new AntiSqlInjection();
        HTMLoldalak oldalak = new HTMLoldalak();
//</editor-fold>
        try (PrintWriter out = response.getWriter()) {

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
                int id = Integer.parseInt(request.getParameter("idr"));
                if (!log.bentvagyok(logtempid)) {
                    response.sendRedirect("index.htm");

//</editor-fold>
                } else {
//<editor-fold defaultstate="collapsed" desc="token állapotát ellenörzi és hosszabítja ha érvényes">

                    String atok = request.getParameter("atok");
                    if (log.atokolvas_nemtorol_ellenoriz(atok, c.getTimeInMillis())) {
                        regoldal = oldalak.oldal_kerese(1);
                        regoldal = regoldal.replaceAll(":##:", "A válaszul küldött oldalnak lejárt az érvényessége. A receptek listában van lehetősége módosítani a recepten ha nem tudta befejezni.");
                        regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                        out.println(regoldal);

//</editor-fold>
                    } else {
//<editor-fold defaultstate="collapsed" desc="alapanyag rögzítése a receptura_tmp táblába">
                        boolean nincshiba = true;
                        String etelneve = request.getParameter("etelneve");
                        String elkeszitese = request.getParameter("parameter");
                        if ("".equals(elkeszitese) || elkeszitese == null) {
                            elkeszitese = kereses.lekerdezes("SELECT etelek_tmp.elkeszitese FROM etelek_tmp WHERE etelek_tmp.elnevezese='" + etelneve + "';", "recept leiras betoltese ha van");

                        }
//<editor-fold defaultstate="collapsed" desc="SIP">
                        if (vansql.vanbenne(etelneve)) {
                            regoldal = kereses.lekerdezes(kell.sql(29), " ip: " + request.getLocalAddr() + ""
                                    + "---warning-- Figyelmezteto oldal betoltese a stringbe--");
                            regoldal = regoldal.replaceAll(":##:", "Az étel nevében meg nem engedett kifejezések szerepelnek (SqlInjektionProtektor)");
                            regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                            out.println(regoldal);
//</editor-fold>
                        } else {
//<editor-fold defaultstate="collapsed" desc="VIsszaküldött adatok, formátumának ellenörzése">
                            int receptid = 0;
                            int alapanyagid = 0;
                            int alapanyagmennyisege = 0;
                            int mennyisegiegysege = 0;
                            try {
                                alapanyagmennyisege = Integer.parseInt(request.getParameter("mennyisege"));
                                alapanyagid = Integer.parseInt(request.getParameter("alapanyaflista"));
                                mennyisegiegysege = Integer.parseInt(request.getParameter("mennyisegiegyseg"));
                                receptid = Integer.parseInt(kereses.lekerdezes("SELECT etelek_tmp.ID FROM etelek_tmp WHERE etelek_tmp.elnevezese='" + etelneve + "'; ", ""));
                            } catch (NumberFormatException e) {
                                nincshiba = false;
                                regoldal = kereses.lekerdezes(kell.sql(29), " ip: " + request.getLocalAddr() + ""
                                        + "---warning-- Figyelmezteto oldal betoltese a stringbe--");
                                regoldal = regoldal.replaceAll(":##:", "Hiba van a visszaküldött adatokban");
                                regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                                out.println(regoldal);
                            }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="ha már van felvéve az adott recepturában uo alapanyag jelzem">
                            int vanbenne = 0;
                            try {
                                vanbenne = Integer.parseInt(kereses.lekerdezes("SELECT COUNT(receptura_tmp.alapanyagok_id) FROM receptura_tmp WHERE receptura_tmp.etelek_id="
                                        + receptid + " AND receptura_tmp.alapanyagok_id="
                                        + alapanyagid + ";", "van e már az adott alapanyag a recepturában rögzítve"));
                            } catch (NumberFormatException e) {

                            }
                            if (vanbenne > 0) {
                                regoldal = kereses.lekerdezes(kell.sql(29), " ip: " + request.getLocalAddr() + ""
                                        + "---warning-- Figyelmezteto oldal betoltese a stringbe--");
                                regoldal = regoldal.replaceAll(":##:", "Az alapanyag már egyszer szerepel a recepurában.");
                                regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                                out.println(regoldal);
                            }
                            if (vanbenne == 0) {
                                kereses.adatbevitel("INSERT INTO receptura_tmp(etelek_id, alapanyagok_id, mennyiseg, menyisegiegyseg_id) VALUES"
                                        + " (" + receptid + "," + alapanyagid + "," + alapanyagmennyisege + "," + mennyisegiegysege + ")", "alapanyag rogzites a recepturaba");

                                kereses.adatbevitel("UPDATE etelek_tmp SET elkeszitese='" + elkeszitese + "' WHERE etelek_tmp.ID=" + receptid + ";", "elkeszites leiras update");
                            }
//</editor-fold>
//</editor-fold>

                            if (!nincshiba); else {
                                log.atokhosszabbias(atok, c.getTimeInMillis());

//<editor-fold defaultstate="collapsed" desc="Az legördülő listák feltöltése adattal az adatbázisból, tokenek azonosítók elhelyezése">
                                regoldal = oldalak.oldal_kerese(8);

                                regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                                regoldal = regoldal.replaceAll(":#LOGID#:", String.valueOf(logtempid));
                                regoldal = regoldal.replaceAll(":#ATOK#:", atok);
//<editor-fold defaultstate="collapsed" desc="a már a recepturához adott alapanyagok listázása">

                                String felvettalapanyagok = kereses.tobberedmenyes_lekerdezes("SELECT alapanyagok_tmp.alapanyag_neve, receptura_tmp.mennyiseg, menyisegiegyseg_tmp.mennyisegi_egyseg"
                                        + " FROM alapanyagok_tmp INNER JOIN receptura_tmp ON receptura_tmp.alapanyagok_id=alapanyagok_tmp.ID "
                                        + "INNER JOIN menyisegiegyseg_tmp ON menyisegiegyseg_tmp.ID=receptura_tmp.menyisegiegyseg_id "
                                        + "INNER JOIN etelek_tmp ON receptura_tmp.etelek_id=etelek_tmp.ID WHERE etelek_tmp.ID=" + receptid + "; ", "alapanyaglista lekerdezes");

//<editor-fold defaultstate="collapsed" desc="Formázás fejléc feliratok lecserélése">
                                felvettalapanyagok = felvettalapanyagok.replaceAll("alapanyag_neve", "Alapanyag");
                                felvettalapanyagok = felvettalapanyagok.replaceAll("mennyiseg", "Mennyiség"); //ez lecserélte következő elejét ékezetesre
                                felvettalapanyagok = felvettalapanyagok.replaceAll("Mennyiségi_egyseg", "Mértékegység");
                                regoldal = regoldal.replaceAll(":#AALISTA#:", formazas.tablazatban_torlo_gombbal(felvettalapanyagok, etelneve, atok));
//</editor-fold>
//</editor-fold>

                                regoldal = regoldal.replaceAll(":#ENEVE#:", etelneve);
                                regoldal = regoldal.replaceAll(":#parameter#:", etelneve);
                                regoldal = regoldal.replaceAll(":#EKAT#:", request.getParameter("kategoria"));
                                // regoldal = regoldal.replaceAll(":#EMODJA#:", kereses.lekerdezes("SELECT elkeszitese FROM etelek_tmp WHERE elnevezese='"+etelneve+"' ", "elkeszites leirasa"));
//---------fel kell tölteni az alapanyaglistát névsor szerint
                                String alapanyaglista = kereses.tobberedmenyes_lekerdezes(kell.sql(31) + id + " ORDER BY alapanyag_neve ASC;", " ip: " + request.getLocalAddr() + ""
                                        + "--Alapanyaglegordulo ista feltolteshez lekerdezes--");
//
                                regoldal = regoldal.replaceAll(":#ID#:", String.valueOf(id));
                                regoldal = regoldal.replaceAll(":#VA#:", formazas.listaba(alapanyaglista));
// <option value="0">válaszzon ki egy alapanyagot</option>
                                String mennyisegiegyseg = kereses.tobberedmenyes_lekerdezes(kell.sql(32) + id + " AND menyisegiegyseg_tmp.hasznalata='r';", " ip: " + request.getLocalAddr() + ""
                                        + "--Mennyisegiegyseg lista feltolteshez lekerdezes r recepturanal--");
                                regoldal = regoldal.replaceAll(":#VME#:", formazas.listaba(mennyisegiegyseg));
//<editor-fold defaultstate="collapsed" desc="receptleiras visszatöltése 3 helyen van ilyen TetelTorolValidalas, AlapanyagFelvetel, ReceptTorlesValidalas ">
                                regoldal = regoldal.replaceAll("<textarea id=\"textarea\" name=\"textarea\" cols=\"88\" rows=\"10\"></textarea>",
                                        "<textarea id=\"textarea\" name=\"textarea\" cols=\"88\" rows=\"10\">"
                                        + vansql.anti_xxs(elkeszitese) + "</textarea>");
//</editor-fold>
                                regoldal = regoldal.replaceAll(":#BETARTALOM#:", "beltartalmi érték számítás");
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Biztonsági ellenörzés, műveletszám rögzítés">
                                if (log.muveletvolt(logtempid, request.getLocalAddr())); else {
                                    regoldal = oldalak.oldal_kerese(1);
                                    regoldal = regoldal.replaceAll(":##:", "Az IP címe a bejelentkezése óta megváltozott. A rendszer biztonsági okokból kilépteti. Kérem jelentkezzen be újra.");
//</editor-fold>

                                }
                                out.println(regoldal);
                            }
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
//<editor-fold defaultstate="collapsed" desc="A get ből post lett">
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        doGet(request, response);
    }
//</editor-fold>

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
