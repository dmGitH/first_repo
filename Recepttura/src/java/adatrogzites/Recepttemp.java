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

//<editor-fold defaultstate="collapsed" desc="Recept fejléc rögzítése alapanyagfelvétel html kiküldése receptkészítés 2. lépés">
public class Recepttemp extends HttpServlet {

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
//<editor-fold defaultstate="collapsed" desc="token állapotát és a bejövő paramétereket ellenörzi tokent nem törli ha érvényes">
                int logtempid = 0;
                int id = 0;
                boolean nincshiba = true;
                try {
                    logtempid = Integer.parseInt(request.getParameter("logtempid"));
                    id = Integer.parseInt(request.getParameter("idr"));
                } catch (NumberFormatException e) {
                    nincshiba = false;
                    regoldal = oldalak.oldal_kerese(1);
                    regoldal = regoldal.replaceAll(":##:", "Hiba van a visszaküldött adatokban");
                    regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                    out.println(regoldal);
                }
                if (!nincshiba) {
                    // a fenti hibauzenet fut csak le
                } else {
                    String atok = request.getParameter("atok");
                    if (log.atokolvas_nemtorol_ellenoriz(atok, c.getTimeInMillis())) {
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
//<editor-fold defaultstate="collapsed" desc="A recept nevének  az ellenörzése. Ha van már recept valakinek ilyen néven akkor visszadobom">
                            String usernev = kereses.lekerdezes(kell.sql(42) + id + "\";", "felhasznalo nevenek lekerese");
                            String etelneve = vansql.anti_xxs(request.getParameter("etelneve"));
//<editor-fold defaultstate="collapsed" desc="SIP">
                            if (vansql.vanbenne(etelneve)) {
                                regoldal = kereses.lekerdezes(kell.sql(29), " ip: " + request.getLocalAddr() + ""
                                        + "---warning-- Figyelmezteto oldal betoltese a stringbe--");
                                regoldal = regoldal.replaceAll(":##:", "Az étel nevében meg nem engedett kifejezések szerepelnek (SqlInjektionProtektor)");
                                regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                                out.println(regoldal);
                            } //</editor-fold>
                            else {
                                int talalat = 0;
                                try {
                                    talalat = Integer.parseInt(kereses.lekerdezes(kell.sql(83) + etelneve + "';", "Ellenorizni, hogy ven e mar pont ilyen nevu recept"));
                                } catch (NumberFormatException e) {
                                    talalat = 0;
                                }
                                if (0 < talalat) {
                                    regoldal = oldalak.oldal_kerese(1);
                                    regoldal = regoldal.replaceAll(":##:", "Ilyen néven már szerepel étel az adatbázisban. Kérem tegyen valamilyen megkülönböztetést. PL: "
                                            + etelneve + ": " + usernev + " módra");
                                    regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                                    out.println(regoldal);
                                    ReceptRogzites vissza=new ReceptRogzites();
                                    
                                    vissza.doGet(request, response);
//</editor-fold>
                                } else {

//<editor-fold defaultstate="collapsed" desc="receptfejléc paraméterek beolvasása">
                                    int etelkategoria = 0;
                                    int szemelyre = 0;
                                    int elkeszitesiido = 0;

                                    try {
                                        etelkategoria = Integer.parseInt(request.getParameter("etelkategoria"));
                                        szemelyre = Integer.parseInt(request.getParameter("szemelyre"));
                                        elkeszitesiido = Integer.parseInt(request.getParameter("elkeszitesiido"));

                                    } catch (NumberFormatException e) {

                                    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Ha nincs ilyen néven receptje akkor felveszi ">
//                            int talalat2 = 0;
//                            try {
//                                talalat2 = Integer.parseInt(kereses.lekerdezes(kell.sql(83) + etelneve + "';", "Ellenorizni, hogy ven e mar pont ilyen nevu recept"));
//                            } catch (NumberFormatException e) {
//                                talalat2 = 0;
//                            }
//                            if (0 < talalat2) {
//                                
//                                kereses.adatbevitel("UPDATE etelek_tmp SET etel_kategoria_ID=" + etelkategoria + ",elnevezese='" + etelneve + "',bekuldo_iD=" + id + ",letrehozva="
//                                        + c.getTimeInMillis() + ",statusz=0,szemelyre=" + szemelyre + ",elkeszitesperc=" + elkeszitesiido + " WHERE elnevezese='" + etelneve + "'", "Update etel");
//                                
//                            } else {
                                    // a etel_tmp táblába rögzíteni a meglévő adatokat
                                    kereses.adatbevitel("INSERT INTO etelek_tmp(etel_kategoria_ID, elnevezese, bekuldo_iD, letrehozva, statusz, szemelyre, elkeszitesperc,elkeszitese) "
                                            + "VALUES (" + etelkategoria + ",'" + etelneve + "'," + id + "," + c.getTimeInMillis() + ",0," + szemelyre + "," + elkeszitesiido + ",'');", "recept fejlec rogzites");

//</editor-fold>
                                    // }
//itt kezdődik az alapanyagok hozzáadása oldal feltöltése megnyitása
//<editor-fold defaultstate="collapsed" desc="Az legördülő listák feltöltése adattal az adatbázisból, tokenek azonosítók elhelyezése">
                                    regoldal = oldalak.oldal_kerese(8);

                                    regoldal = regoldal.replaceAll(":#K#:KEPET", kep.kepet());
                                    regoldal = regoldal.replaceAll(":#LOGID#:", String.valueOf(logtempid));
                                    regoldal = regoldal.replaceAll(":#ATOK#:", atok);
                                    regoldal = regoldal.replaceAll(":#AALISTA#:", "Még nincsen hozzáadva alapanyag");
                                    regoldal = regoldal.replaceAll(":#EMODJA#:", "Még nincsen leírás készítve");
                                    regoldal = regoldal.replaceAll(":#ENEVE#:", etelneve);
                                    regoldal = regoldal.replaceAll(":#EKAT#:", kereses.lekerdezes("SELECT etel_kategoria_tmp.kategoria FROM etel_kategoria_tmp "
                                            + "WHERE etel_kategoria_tmp.ID=" + etelkategoria + ";", "etelkategoria lekerdezese"));
                                    //hidden mezőbe is beírni az etel nevet

                                    String alapanyaglista = kereses.tobberedmenyes_lekerdezes(kell.sql(31) + id + " ORDER BY alapanyag_neve ASC;", " ip: " + request.getLocalAddr() + ""
                                            + "--Alapanyaglegordulo ista feltolteshez lekerdezes--");

                                    regoldal = regoldal.replaceAll(":#ID#:", String.valueOf(id));
                                    regoldal = regoldal.replaceAll(":#VA#:", formazas.listaba(alapanyaglista));

                                    String mennyisegiegyseg = kereses.tobberedmenyes_lekerdezes(kell.sql(32) + id + " AND menyisegiegyseg_tmp.hasznalata='r';", " ip: " + request.getLocalAddr() + ""
                                            + "--Mennyisegiegyseg ista feltolteshez lekerdezes r recepturanal--");
                                    regoldal = regoldal.replaceAll(":#VME#:", formazas.listaba(mennyisegiegyseg));

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

    }
//</editor-fold>

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
