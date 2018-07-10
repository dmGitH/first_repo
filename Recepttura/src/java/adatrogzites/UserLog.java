package adatrogzites;

import database.AntiSqlInjection;
import database.EgyEredmeny;
import database.LekerdezesStringek;
import java.util.Calendar;

/**
 * A login temp tábla kezelése, egyéb paraméterek alapján keresések az
 * adatbázisban . A felhasználói műveletek nyilvántartásához, kezeléséhez,
 * vezérléséhez tartalmaz alkalmazásspec fügvényeket. Stingek SIP-al ellenőrizve
 */
public class UserLog {

    /**
     * A belépett felhasználó belépésének rögzítése és az aktuális logintemp_ID
     * visszakapása
     *
     * @param id
     * @param ip
     * @param ver
     * @return (login_temp_id)
     */
    public int belepett(int id, String ip, String ver) {

        EgyEredmeny kereses = new EgyEredmeny();
        Calendar c = Calendar.getInstance();
        LekerdezesStringek kell = new LekerdezesStringek();
        AntiSqlInjection vansql = new AntiSqlInjection();
//        if (vansql.vanbenne(ver)) {
//            return -1;
//        }
        //megnézni, hogy az adott id ki van e lépve
        long utolsokilepesiido = 1;
        try {
            utolsokilepesiido = Long.parseLong(kereses.lekerdezes(kell.sql(7) + id + ")", " : Az utolso kilepesi ido keresese "));
        } catch (NumberFormatException e) {
            kereses.adatbevitel(kell.sql(8) + id + "','" + c.getTimeInMillis() + "','0','0','" + ip + "','" + ver + "')", ": A felhasznalo belepett ");
            return Integer.parseInt(kereses.lekerdezes(kell.sql(9) + id + "  And belepett=" + c.getTimeInMillis() + "", " : A belepes azonosito visszakerese "));
        }
        if (utolsokilepesiido == 0) {
            String temp_id = kereses.lekerdezes(kell.sql(10) + id + "", " belso muvelet ");
            kereses.adatbevitel(kell.sql(11) + c.getTimeInMillis() + " WHERE iD=" + temp_id + "", " : Kileptetes mert mar be volt jelentkezve ");
            return -1;
        }
        kereses.adatbevitel(kell.sql(12) + id + "','" + c.getTimeInMillis() + "','0','0','" + ip + "','" + ver + "')", ": A felhasznalo belepett ");
        return Integer.parseInt(kereses.lekerdezes(kell.sql(13) + id + "  And belepett=" + c.getTimeInMillis() + "", " : A belepes azonosito visszakerese "));
    }

    /**
     * A felhasználó kilépési idejének a rögzítés
     *
     * @param login_temp_id
     */
    public void kilepett(int login_temp_id) {
        EgyEredmeny kereses = new EgyEredmeny();
        Calendar c = Calendar.getInstance();
        LekerdezesStringek kell = new LekerdezesStringek();
        kereses.adatbevitel(kell.sql(14) + c.getTimeInMillis() + " WHERE iD=" + login_temp_id + "", " felhasznalo kilepett ");
    }

    /**
     * A felhasználó regisztrációjának a törlési idejének a rögzítés
     *
     * @param login_temp_id
     */
    public void unregisztracio(int login_temp_id) {
        EgyEredmeny kereses = new EgyEredmeny();
        Calendar c = Calendar.getInstance();
        LekerdezesStringek kell = new LekerdezesStringek();
        kereses.adatbevitel(kell.sql(82) + c.getTimeInMillis() + ", ver='regisztráció törlése' WHERE iD=" + login_temp_id + "", " felhasznalo kilepett ");
    }

    /**
     * A felhasználó műveletszámáának a rögzítése az aktuális login temp id -t
     * kell megadni, közben ellenörzi, hogy az ip nem változik e a műveletek
     * közben
     *
     * @param login_temp_id
     * @param ip
     * @return fals ha a bejelntkezett user a műveletek végzése között
     * változtatja az ip címét
     */
    public boolean muveletvolt(int login_temp_id, String ip) {
        EgyEredmeny kereses = new EgyEredmeny();
        LekerdezesStringek kell = new LekerdezesStringek();
        int muveletszam;
        try {
            muveletszam = Integer.parseInt(kereses.lekerdezes(kell.sql(15) + login_temp_id + "", " : muveletszam visszakerese "));
        } catch (NumberFormatException e) {
            return false;
        }
        if (ip == null ? kereses.lekerdezes(kell.sql(16) + login_temp_id + "", " : ip cim muveletszamlalas kozben visszakeresese ") == null
                : ip.equals(kereses.lekerdezes(kell.sql(16) + login_temp_id + "", " : ip cim visszakeresese muveletszamlalas kozben visszakerese "))); else {
            kilepett(login_temp_id);
            return false;
        }
        muveletszam++;
        kereses.adatbevitel(kell.sql(17) + muveletszam + " WHERE iD=" + login_temp_id + "", " felhasznalo muveletet vegzett ");
        return true;
    }

    /**
     * Ellenörzi, hogy a megadott temp_id jű felhasználó kijelentkezett e már
     *
     * @param login_temp_id
     * @return (ha be van jelentkezve true)
     */
    public boolean bentvagyok(int login_temp_id) {
        EgyEredmeny kereses = new EgyEredmeny();
        LekerdezesStringek kell = new LekerdezesStringek();
        long kilepesiido = Long.parseLong(kereses.lekerdezes(kell.sql(24) + login_temp_id + "", " : belejelentkezettseg ellenorzese "));
        return kilepesiido <= 0;
    }

    /**
     * Tokent generál a kiküldött weoldalhoz és törli a lejárt tokeneket az
     * adatbázisból
     *
     * @param id (a küldő felhasználó ID -je)
     * @param kuldesi_ido
     * @param ervenyes (meddig használható fel a kiküldött token(minisec))
     * @param kiirja (milyenn célból készül a token)
     * @return (A token stringben)
     */
    public String atokir(int id, long kuldesi_ido, long ervenyes, String kiirja) {
        EgyEredmeny kereses = new EgyEredmeny();
        LekerdezesStringek kell = new LekerdezesStringek();
        String atok;
        double a = Math.random();
        atok = String.valueOf(a) + String.valueOf(kuldesi_ido).substring(4) + kiirja;

        kereses.adatbevitel("CALL `addtoken`("+id+","+kuldesi_ido+","+ervenyes+",'"+atok+"');","atokir");

        kereses.adatbevitel(kell.sql(63) + kuldesi_ido + ";", "lejart tokenek torlese");

        return atok;
    }

    /**
     * A belépési token érvényességének az ellenörzése meglétekor törlése ha
     * nincs hiba ha lejárt hiba
     *
     * @param atok
     * @param jelenido
     * @return (van e érvényes token a belépéshez)
     */
    public boolean atokolvas(String atok, long jelenido) {
        EgyEredmeny kereses = new EgyEredmeny();
        LekerdezesStringek kell = new LekerdezesStringek();
        AntiSqlInjection vansql = new AntiSqlInjection();
        if (vansql.vanbenne(atok)) {
            return false;
        }
        int eredmeny;
        long ervenyesseg;
        try {
            eredmeny = Integer.parseInt(kereses.lekerdezes(kell.sql(19) + atok + "\"", " : token ellenorzese "));
            ervenyesseg = Long.parseLong(kereses.lekerdezes(kell.sql(64) + atok + "\"", " : token ervenyessegi ideje ms "));
        } catch (NumberFormatException e) {
            return false;
        }
        if (0 < eredmeny) {
            if (jelenido + ervenyesseg < Long.parseLong(kereses.lekerdezes(kell.sql(20) + atok + "\"", " : token ellenorzese2 "))) {
                kereses.adatbevitel(kell.sql(21) + atok + "", "token torolve");
                return false;
            }
            kereses.adatbevitel(kell.sql(22) + atok + "\"", "token torolve");
            return true;
        } else {
            return false;
        }

    }

    /**
     * A nem belépési token ellenörzése meglétekor törlése, ha nincs hiba
     *
     * @param atok
     * @param id
     * @return (van e érvényes token a művelethez) ha nincsen akkor false ha van
     * true és törli
     */
    public boolean atokolvas_nembelepeskor(String atok, int id) {
        EgyEredmeny kereses = new EgyEredmeny();
        LekerdezesStringek kell = new LekerdezesStringek();
        AntiSqlInjection vansql = new AntiSqlInjection();
        if (vansql.vanbenne(atok)) {
            return false;
        }
        if (0 < Integer.parseInt(kereses.lekerdezes(kell.sql(23) + atok + "\"", " : token ellenorzese "))) {

            kereses.adatbevitel(kell.sql(22) + atok + "\"", "token torolve");
            return true;
        } else {
            return false;
        }

    }

    /**
     * A nem belépési token ellenörzése nem törlése ha érvényes , ha lejárt hiba
     * és törlés, ha nincs hiba nem törli
     *
     *
     * @param atok
     * @param jelenido (az ellenorzes idopontja)
     * @return (van e érvényes token a művelethez) ha nincsen akkor false és
     * törli, ha van akkor true és megtartja
     */
    public boolean atokolvas_nemtorol_ellenoriz(String atok, long jelenido) {
        EgyEredmeny kereses = new EgyEredmeny();
        LekerdezesStringek kell = new LekerdezesStringek();
        AntiSqlInjection vansql = new AntiSqlInjection();
        if (vansql.vanbenne(atok)) {
            return false;
        }
        if (0 < Integer.parseInt(kereses.lekerdezes(kell.sql(23) + atok + "\"", " : token ellenorzese "))) {
            long ervenyesseg = 0;
            try {
                ervenyesseg = Long.parseLong(kereses.lekerdezes(kell.sql(64) + atok + "\"", " : token ervenyessegi ideje ms "));
            } catch (NumberFormatException e) {
                kereses.adatbevitel(kell.sql(21) + atok + "", "token torolve");
                return false;
            }
            if (jelenido > Long.parseLong(kereses.lekerdezes(kell.sql(20) + atok + "\"", " : token kikuldesi ideje ")) + ervenyesseg) {

                return true;
            } else {
                kereses.adatbevitel(kell.sql(21) + atok + "", "token torolve");
                return false;
            }

        } else {
            kereses.adatbevitel(kell.sql(21) + atok + "", "token torolve");
            return false;
        }

    }

    /**
     * Token érvénysségének a hosszabbítása
     *
     *
     * @param atok
     * @param jelenido (az új érvényességi idő kezdete idopontja)
     * @return a lekérdezés vagy hibauzenet
     */
    public String atokhosszabbias(String atok, long jelenido) {
        EgyEredmeny kereses = new EgyEredmeny();
        AntiSqlInjection vansql = new AntiSqlInjection();
        if (vansql.vanbenne(atok)) {
            return "SIP";
        }
        return kereses.adatbevitel("UPDATE token SET kikuldve=" + jelenido + " WHERE token.token='" + atok + "';", "token ervenyesseg hosszabbitas");

    }

    /**
     * Az adott token érvényességének az ellenörzése meglétekor törlése ha nincs
     * hiba ha lejárt hiba ha megvan akkor a logtemp_id -t adja vissza
     *
     * @param atok
     * @param jelenido
     * @return (ha érvényes a token és be van jelentkezve aki kikuldte akkor az
     * bejelentkezett user logtemp_id-jét adja vissza hiba esetén 0-át ad vissza
     * )
     */
    public int atokolvas_logtempidt_advissza(String atok, long jelenido) {
        EgyEredmeny kereses = new EgyEredmeny();
        LekerdezesStringek kell = new LekerdezesStringek();
        AntiSqlInjection vansql = new AntiSqlInjection();
        if (vansql.vanbenne(atok)) {
            return -1;
        }
        int logtemp_id = 0;
        long eredmeny;
        long ervenyesseg;
        int id;
        try {
            eredmeny = Long.parseLong(kereses.lekerdezes(kell.sql(19) + atok + "\"", " : token megletenek ellenorzese "));
            ervenyesseg = Long.parseLong(kereses.lekerdezes(kell.sql(64) + atok + "\"", " : token ervenyessegi ideje ms "));
            id = Integer.parseInt(kereses.lekerdezes(kell.sql(65) + atok + "\"", " : token user id-je "));
        } catch (NumberFormatException e) {
            return 0;
        }
        if (0 < eredmeny) {
            if (jelenido > Long.parseLong(kereses.lekerdezes(kell.sql(20) + atok + "\"", " : token kikuldesi ideje ")) + ervenyesseg) {
                kereses.adatbevitel(kell.sql(21) + atok + "", "token torolve");
                return 0;
            }
            try {
                logtemp_id = Integer.parseInt(kereses.lekerdezes(kell.sql(66) + id + ";", "az éppen aktuális (be van jelentkezve) logtempidt keresem az id alapján "));
            } catch (NumberFormatException e) {
                kereses.adatbevitel(kell.sql(22) + atok + "\"", "token torolve");
                return 0;
            }
            kereses.adatbevitel(kell.sql(22) + atok + "\"", "token torolve");
            return logtemp_id;

        } else {
            return 0;
        }

    }

    /**
     * Ha nincsen a tokennel hiba nem törli csak a logtempid-t olvassa ki belőle
     * ha lejárt törli
     *
     * @param atok
     * @param jelenido
     * @return (ha érvényes a token és be van jelentkezve aki kikuldte akkor az
     * bejelentkezett user logtemp_id-jét adja vissza hiba esetén 0-át ad vissza
     * )
     */
    public int atokolvas_logtempidt_advissza_nemtorli(String atok, long jelenido) {
        EgyEredmeny kereses = new EgyEredmeny();
        LekerdezesStringek kell = new LekerdezesStringek();
        AntiSqlInjection vansql = new AntiSqlInjection();
        if (vansql.vanbenne(atok)) {
            return -1;
        }
        int logtemp_id = 0;
        long eredmeny;
        long ervenyesseg;
        int id;
        try {
            eredmeny = Long.parseLong(kereses.lekerdezes(kell.sql(19) + atok + "\"", " : token megletenek ellenorzese "));
            ervenyesseg = Long.parseLong(kereses.lekerdezes(kell.sql(64) + atok + "\"", " : token ervenyessegi ideje ms "));
            id = Integer.parseInt(kereses.lekerdezes(kell.sql(65) + atok + "\"", " : token user id-je "));
        } catch (NumberFormatException e) {
            return 0;
        }
        if (0 < eredmeny) {
            if (jelenido > Long.parseLong(kereses.lekerdezes(kell.sql(20) + atok + "\"", " : token kikuldesi ideje ")) + ervenyesseg) {
                kereses.adatbevitel(kell.sql(21) + atok + "", "token torolve");
                return 0;
            }
            try {
                logtemp_id = Integer.parseInt(kereses.lekerdezes(kell.sql(66) + id + ";", "az éppen aktuális (be van jelentkezve) logtempidt keresem az id alapján "));
            } catch (NumberFormatException e) {
                kereses.adatbevitel(kell.sql(22) + atok + "\"", "token torolve");
                return 0;
            }

            return logtemp_id;

        } else {
            return 0;
        }

    }

    /**
     * A login_temp_id ert visszaadja a user id-t ha a user be van jelentkezve
     * ha nincsen akkor 0 át ad vissz
     *
     * @param login_temp_id
     * @return (User id vagy 0 ha nincsen bejelentkezve)
     */
    public int User_ID_temp_id_ert(int login_temp_id) {
        EgyEredmeny kereses = new EgyEredmeny();
        LekerdezesStringek kell = new LekerdezesStringek();
        int id = 0;

        try {
            id = Integer.parseInt(kereses.lekerdezes(kell.sql(67) + login_temp_id + ";", "keresem az idt a UsersLog osztalybol"));
        } catch (NumberFormatException e) {
            return 0;
        }

        return id;
    }

    /**
     * Az étel nevért integerben az étel id adja viss
     *
     * @param etelnev (Étel elnevezése)
     * @return (etel ID)
     */
    public int etelnevert_etel_ID_t(String etelnev) {
        AntiSqlInjection vansql = new AntiSqlInjection();
        if (vansql.vanbenne(etelnev)) {
            return -1;
        }
        EgyEredmeny kereses = new EgyEredmeny();
        LekerdezesStringek kell = new LekerdezesStringek();
        int id = 0;
        try {
            id = Integer.parseInt(kereses.lekerdezes("SELECT etelek_tmp.ID FROM etelek_tmp WHERE etelek_tmp.elnevezese='" + etelnev + "';", "etel_idt keresek"));
        } catch (NumberFormatException e) {
            return 0;
        }

        return id;
    }

    /**
     * Az étel nevért az étel kategóraáját adja vissza
     *
     * @param etelnev (Étel elnevezése)
     * @return (ételkategória)
     */
    public String etelnevert_etel_kategoria(String etelnev) {
        AntiSqlInjection vansql = new AntiSqlInjection();
        if (vansql.vanbenne(etelnev)) {
            return "SIP";
        }
        EgyEredmeny kereses = new EgyEredmeny();
        LekerdezesStringek kell = new LekerdezesStringek();

        String kategoria = kereses.lekerdezes("SELECT etel_kategoria_tmp.kategoria FROM etel_kategoria_tmp "
                + "INNER JOIN etelek_tmp ON etelek_tmp.etel_kategoria_ID=etel_kategoria_tmp.ID "
                + "WHERE etelek_tmp.elnevezese='" + etelnev + "';", "etel_kategoriat keresek");

        return kategoria;
    }

    /**
     * Az étel nevért az elkészítés leírását adja vissza
     *
     * @param etelnev (Étel elnevezése)
     * @return (elkészítése)
     */
    public String etelnevert_elkeszitese(String etelnev) {
        AntiSqlInjection vansql = new AntiSqlInjection();
        if (vansql.vanbenne(etelnev)) {
            return "SIP";
        }
        EgyEredmeny kereses = new EgyEredmeny();
        LekerdezesStringek kell = new LekerdezesStringek();

        String elkeszitese = kereses.lekerdezes("SELECT elkeszitese FROM etelek_tmp WHERE elnevezese='" + etelnev + "' ", "elkeszites leirasa");

        return elkeszitese;
    }

    /**
     * A megadott ételből törli a megadott alapanyagot a receptura tbl-
     *
     * @param etelnev
     * @param alapanyagnev
     */
    public void etel_felvett_alapanyaganak_torlese(String etelnev, String alapanyagnev) {
        EgyEredmeny kereses = new EgyEredmeny();
        LekerdezesStringek kell = new LekerdezesStringek();
        kereses.adatbevitel("DELETE FROM receptura_tmp WHERE receptura_tmp.alapanyagok_id=" + alapanyagnevert_alapanyag_ID_t(alapanyagnev)
                + " AND receptura_tmp.etelek_id=" + etelnevert_etel_ID_t(etelnev) + ";", "az etelbol torol alapanyagot");
    }

    /**
     * Az étel nevért integerben az étel id adja viss
     *
     * @param alapanyagnev
     * @return (alapanyag ID)
     */
    public int alapanyagnevert_alapanyag_ID_t(String alapanyagnev) {
//        AntiSqlInjection vansql = new AntiSqlInjection();
//        if (vansql.vanbenne(alapanyagnev)) {
//            return -1;
//        }
        EgyEredmeny kereses = new EgyEredmeny();
        LekerdezesStringek kell = new LekerdezesStringek();
        int id = 0;
        try {
            id = Integer.parseInt(kereses.lekerdezes("SELECT alapanyagok_tmp.ID FROM alapanyagok_tmp WHERE alapanyagok_tmp.alapanyag_neve='" + alapanyagnev + "';", "alapanyag_idt keresek"));
        } catch (NumberFormatException e) {
            return 0;
        }

        return id;
    }

    /**
     * Egy adott ID-jű étel addott nevü beltartalmi egységének a mennyisége a
     * mértékegység a beltartalmi egység mértékegységében értelmezve
     *
     * @param etelid (Az adott átel azonosítószáma az adatbázisban
     * etelek_tmp.ID)
     * @param osszetevo (A keresett összetevő neve pl: E vitamin)
     * @return (A kiszámított beltartalmi érték str)
     */
    public String beltartalmiertek_egy_adag_etelben(int etelid, String osszetevo) {
        String eredmeny = null;
        EgyEredmeny kereses = new EgyEredmeny();
        LekerdezesStringek kell = new LekerdezesStringek();
        eredmeny=kereses.lekerdezes("SELECT "
                + "SUM((((receptura_tmp.mennyiseg*"
                + "(Select menyisegiegyseg_tmp.valto FROM menyisegiegyseg_tmp WHERE receptura_tmp.menyisegiegyseg_id=menyisegiegyseg_tmp.ID )"
                + ")/100)*opcionalis_alapanyag_kapcsolo_tmp.mennyiseg)/"
                + "(SELECT etelek_tmp.szemelyre FROM etelek_tmp WHERE etelek_tmp.ID="+etelid+")) "
                + "AS egyadagban FROM opcionalis_alapanyag_kapcsolo_tmp "
                + "INNER JOIN menyisegiegyseg_tmp ON menyisegiegyseg_tmp.ID=opcionalis_alapanyag_kapcsolo_tmp.menyisegiegyseg_id "
                + "INNER JOIN receptura_tmp ON receptura_tmp.alapanyagok_id=opcionalis_alapanyag_kapcsolo_tmp.alapanyagok_id "
                + "WHERE receptura_tmp.etelek_id="+etelid+" AND opcionalis_alapanyag_kapcsolo_tmp.opcionalisbeltartalom_id="
                + "(SELECT opcionalisbeltartalom_tmp.ID FROM opcionalisbeltartalom_tmp WHERE opcionalisbeltartalom_tmp.osszetevo='"+osszetevo+"')",
                " public String beltartalmiertek_egy_adag_etelben(int etelid, String osszetevo)");

        return eredmeny;
    }
    
    
}
