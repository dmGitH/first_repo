package database;

import alap.Hatterkep;

/**
 * SQL lekérdezések eredményeinek a htmlkóddá formázásait végző osztály
 */
public class LekerdezesFormazasa {

    String egesz;
    String[] eredmeny_sorokban;

    /**
     * Az EgyEredmeny osztály által visszakapott lekérdezési eredmények
     * táblázatba történő formázása
     *
     * @param nyersanyag (Az EgyEredmeny osztály által visszakapott lekérdezési
     * eredmény)
     * @return (táblázati formában HTML-ben visszaadott lekérdezési eredmény az
     * első sora a fejléc)
     */
    public String tablazatban(String nyersanyag) {
        String[] sorok;
        sorok = nyersanyag.split("¤");
        if (sorok.length == 1) {
            return "Nincsen eredmény";
        } else {
            egesz = "<table>";
            for (int i = 0; i < sorok.length; i++) {

                egesz = egesz + "<tr>";
                String[] rekordok = sorok[i].split("#");
                for (int x = 1; x < rekordok.length; x++) {
                    if (x % 2 == 0) {
                        egesz = egesz + "<td bgcolor=\"#FFFF00\"><div align=\"center\">" + rekordok[x]
                                + "</div></td>\n";
                    } else if (x % 3 == 0) {
                        egesz = egesz + "<td bgcolor=\"#33CCFF\"><div align=\"center\" >" + rekordok[x]
                                + "</div></td>\n";
                    } else if (x % 4 == 0) {
                        egesz = egesz + "<td bgcolor=\"#9900CC\"><div align=\"center\" >" + rekordok[x]
                                + "</div></td>\n";
                    } else {
                        egesz = egesz + "<td bgcolor=\"#FF9900\"><div align=\"center\" >" + rekordok[x]
                                + "</div></td>\n";
                    }

                }
                egesz = egesz + "</tr>\n";
            }

            egesz = egesz + "</table>";
            return egesz;
        }
    }

    /**
     * Az EgyEredmeny osztály által visszakapott lekérdezési eredmények
     * táblázatba történő formázása, utolsó oszlop törlés gomb
     *
     * @param nyersanyag (Az EgyEredmeny osztály által visszakapott lekérdezési
     * eredmény)
     * @param miboltorol ( a törléshez szükséges azonosítást szolgáló paraméter)
     * @param atok
     * @return (táblázati formában HTML-ben visszaadott lekérdezési eredmény
     * fejléc nélkül törlés gombbal)
     */
    public String tablazatban_torlo_gombbal(String nyersanyag, String miboltorol, String atok) {
        String[] sorok;
        sorok = nyersanyag.split("¤");
        if (sorok.length == 1) {
            return "Nincsen eredmény";
        } else {
            egesz = "<table>";
            for (int i = 1; i < sorok.length; i++) {
                egesz = egesz + "<tr>";
                if (i > 0) {
                    egesz = egesz + "<td bgcolor=\"#33CCFF\"><div align=\"center\" > - " + i
                            + " -</div></td>\n";
                }
                String mittorol = null;
                String[] rekordok = sorok[i].split("#");
                for (int x = 1; x < rekordok.length; x++) {

                    if (x % 2 == 0) {
                        egesz = egesz + "<td bgcolor=\"#FFFF00\"><div align=\"center\">" + rekordok[x]
                                + "</div></td>\n";
                    } else if (x % 3 == 0) {
                        egesz = egesz + "<td bgcolor=\"#33CCFF\"><div align=\"center\" >" + rekordok[x]
                                + "</div></td>\n";
                    } else if (x % 4 == 0) {
                        egesz = egesz + "<td bgcolor=\"#9900CC\"><div align=\"center\" >" + rekordok[x]
                                + "</div></td>\n";
                    } else {
                        egesz = egesz + "<td bgcolor=\"#FF9900\"><div align=\"center\" >" + rekordok[x]
                                + "</div></td>\n";
                        mittorol = rekordok[x];
                    }

                }
                if (i > 0) {
                    egesz = egesz + "<td><form name=\"sorform" + i + "\" method=\"post\" action=\"teteltorol\">\n"
                            + "              <label>\n"
                            + "              <input  name=\"Submit\" type=\"submit\" class=\"sidebarHeader\" value=\"Eltávolit\">\n"
                            + "              <span class=\"subtitle\">\n"
                            + "              <input name=\"torol\" type=\"hidden\" value=\"" + mittorol + "\">\n"
                            + "              <input name=\"honnan\" type=\"hidden\" value=\"" + miboltorol + "\">\n"
                            + "              <input name=\"atok\" type=\"hidden\" value=\"" + atok + "\">\n"
                            + "              </span> </label>\n"
                            + "            </form></td>\n";
                }
                egesz = egesz + "</tr>\n";
            }

            egesz = egesz + "</table>";
            return egesz;
        }
    }

    /**
     * Az EgyEredmeny osztály által visszakapott lekérdezési eredmények
     * legördülő listába történő formázása
     *
     * @param nyersanyag (Az EgyEredmeny osztály által visszakapott lekérdezési
     * eredmény)
     * @return (option value="ID">rekordok...
     */
    public String listaba(String nyersanyag) {

        egesz = "";
        eredmeny_sorokban = nyersanyag.split("¤");
        if (eredmeny_sorokban.length == 1) {
            return "Nincsen eredmény";
        } else {
            egesz = "";
            for (int i = 1; i < eredmeny_sorokban.length; i++) {

                String[] rekordok = eredmeny_sorokban[i].split("#");
                for (int x = 1; x < rekordok.length; x++) {
                    if (x < rekordok.length - 1) {
                        egesz = egesz + "<option value=\"" + rekordok[x] + "\">" + rekordok[x + 1] + "</option>\n";
                    }

                }

            }

            return egesz;
        }

    }

    /**
     * Egy integer tombbe formázott lekérdezési eredmény. PL id lista
     * visszakérése valamilyen feltétel pl külsőkulcs id szerin
     *
     * @param lekerdezes (Az adatbázis kapcsolati szerver által visszaadott
     * lekérdezés eredménysora nyersen)
     * @return (Integer tömbe formázott lekérdezési eredmény) ha nincsen
     * eredmény null
     */
    public int[] egyoszlopos_szamlista_fejlec_nelkul(String lekerdezes) {

        String[] prevint_string = lekerdezes.split("¤#");
        int[] alapanyagid = new int[prevint_string.length - 1];
        for (int i = 1; i < prevint_string.length; i++) {
            if (i == prevint_string.length - 1) {
                prevint_string[i] = prevint_string[i].substring(0, prevint_string[i].length() - 1);
            }
            try {
                if (i > 0) {
                    alapanyagid[i - 1] = Integer.parseInt(prevint_string[i].trim());
                }
            } catch (NumberFormatException e) {
                return null;
            }

        }
        return alapanyagid;
    }

    /**
     * Receptek táblázatba formázása, alapanyagokkal, leírással specifikusan
     * adott lekérdezéshez!!!
     *
     * @param nyersanyag (Az sql lekérdezés eredménye elválasztó jelekkel
     * ellátva)
     * @param atok
     * @param logtempid
     * @return (a htmllé formázott lekérdzési eredmény egy Stringben)
     */
    public String tablazatba_kiemelt_mezokkel(String nyersanyag, String atok, int logtempid) {
        Hatterkep kep = new Hatterkep();

        String[] sorok;
        sorok = nyersanyag.split("¤");
        if (sorok.length == 1) {
            return "Nincsen eredmény";
        } else {
            egesz = "<table>";
            String receptvege = null;
            boolean vegevan;
            for (int i = 0; i < sorok.length; i++) {
                egesz = egesz + "<tr>";
                String mittorol = null;
                String[] rekordok = sorok[i].split("#");

                if (!rekordok[1].equals(receptvege)) {
                    receptvege = rekordok[1];
                    vegevan = true;
                } else {
                    vegevan = false;
                }
                if (vegevan) {
//<editor-fold defaultstate="collapsed" desc="távtartó">
                    egesz = egesz + "</tr>"
                            + "<td align=\"center\">*</td>"
                            + "<tr>\n";
                    egesz = egesz + "</tr>"
                            + "<td align=\"center\">*</td>"
                            + "<tr>\n";
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="beltartalmi értékek post elhelyezése a lekérdezés válaszban">  
                    if (i > 0) {
                        String foto = kep.kepet_IMGSRC(250, 200, 3);
                        egesz = egesz + "</tr><td class=\"lightgreen\"><div align=\"center\" >" + "Recept neve: " + rekordok[1]
                                + "<form name=\"torles" + i + "\" method=\"post\" action=\"rtorolk\" class=\"style8\">\n"
                                + "              <input name=\"Submit\" type=\"submit\" class=\"style12\" align=\"center\" value=\"Recept törlése vagy módosítása (Módosításhoz ne a törlést válassza!)\">\n"
                                + "              <span class=\"subtitle\">\n"
                                + "              <input name=\"logtempid\" type=\"hidden\" value=\"" + logtempid + "\">\n"
                                + "              <input name=\"recepttorlese\" type=\"hidden\" value=\"" + receptvege + "\">\n"
                                + "              <input name=\"atok\" type=\"hidden\" value=\"" + atok + "\">\n"
                                + "              </span> </label>\n"
                                + "            </form><p></p><form name=\"etellistaform" + i + "\" method=\"post\" action=\"beltartalom\" class=\"style8\">\n"
                                + "              <label> <select name=\"alapanyagid\" class=\"style12\">\n"
                                + "				:#OPCBE#:\n"
                                + "                </select>\n"
                                + "              <input  name=\"Submit\" type=\"submit\" class=\"style12\" align=\"center\" value=\"Beltartalom egy adag ételben\">\n"
                                + "              <span class=\"subtitle\">\n"
                                + "              <input name=\"logtempid\" type=\"hidden\" value=\"" + logtempid + "\">\n"
                                + "              <input name=\"etelneve\" id=\"etelneve\" type=\"hidden\" value=\"" + receptvege + "\">\n"
                                + "              <input name=\"atok\" type=\"hidden\" value=\"" + atok + "\">\n"
                                + "              </span> </label>\n"
                                + "            </form><p></p></div></td><td><div></div></td><td><div><a href=" + foto + "><img src=" + foto + "</a></div></td><tr>\n";
                        String ezt = "<a href=" + foto + ">";
                        //link létrehozása a képből és a paramétereiből, paraméterek eltávolításával
                        egesz = egesz.replaceAll(ezt, ezt.substring(0, ezt.indexOf(".") + 5) + ">");
//<editor-fold defaultstate="collapsed" desc="Jelmagyarázat">
                    } else {
                        egesz = egesz + "</tr><td class=\"lightgreen\"><div align=\"center\" >" + rekordok[1]
                                + "</div></td><tr>\n";
                    }
//</editor-fold>
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="elkészítés leírás módosítás form beszúrása">
                    try {
                        egesz = egesz + "</tr><td bgcolor=\"cyan\"><div align=\"center\" ><strong>" + rekordok[7]
                                + "</strong></div></td><tr>\n";

                    } catch (Exception e) {
                        egesz = egesz + "</tr><td bgcolor=\"cyan\"><div align=\"center\" ><strong>" + "Nincsen megadva az étel elkészítésének a leírása"
                                + "</strong></div></td><tr>\n";
                    }
//</editor-fold>

                }
                for (int x = 1; x < rekordok.length; x++) {

                    if (x % 2 == 0 && x != 7 && x != 1) {
                        egesz = egesz + "<td bgcolor=\"#FFFF00\"><div align=\"center\"><strong>" + rekordok[x]
                                + "</strong></div></td>\n";
                    } else if (x % 3 == 0 && x != 7 && x != 1) {
                        egesz = egesz + "<td class=\"style18\"><div align=\"center\" ><strong>" + rekordok[x]
                                + "</strong></div></td>\n";
                    } else if (x % 4 == 0 && x != 7 && x != 1) {
                        egesz = egesz + "<td bgcolor=\"#9900CC\"><div align=\"center\" ><strong>" + rekordok[x]
                                + "</strong></div></td>\n";
                    } else if (x != 7 && x != 1) {
                        egesz = egesz + "<td bgcolor=\"khaki\"><div align=\"center\" ><strong>" + rekordok[x]
                                + "</strong></div></td>\n";
                    }

                }

                egesz = egesz + "</tr>\n";

            }

            egesz = egesz + "</table>";
//<editor-fold defaultstate="collapsed" desc="mezőnevek lecserélése ">
            egesz = egesz.replaceAll("elnevezese", "Recept neve");
            egesz = egesz.replaceAll("elkeszitese", "Elkészítésének a leírása");
            egesz = egesz.replaceAll("alapanyag_neve", "Recept alapanyagai");
            egesz = egesz.replaceAll("mennyiseg", "Mennyiség");
            egesz = egesz.replaceAll("Mennyiségi_egyseg", "Mértékegység");
            egesz = egesz.replaceAll("elkeszitesperc", "Elkészithető(perc)");
            egesz = egesz.replaceAll("szemelyre", "Főre");
//</editor-fold>

            return egesz;
        }
    }

}
