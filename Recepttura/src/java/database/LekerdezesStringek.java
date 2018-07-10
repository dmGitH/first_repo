package database;

/**
 * Az SQL Stringek itt vannak "tárolva"
 */
public class LekerdezesStringek {

    /**
     * Visszaadja az adott azonosítószámon lévő SQL-t
     *
     *
     * @param mikell (SQL String azonosítószáma)
     * @return
     *
     * @see "SELECT COUNT(bekuldo.ID) FROM bekuldo";" --1
     *
     * @see "SELECT COUNT(etelek.ID) FROM etelek";" --2
     *
     * @see "SELECT COUNT(etelek.ID) FROM etelek WHERE etelek.aktivalva=3"; --3
     *
     * @see "SELECT COUNT(etelek.ID) FROM etelek WHERE etelek.aktivalva=1"; --4
     *
     * @see "SELECT COUNT(alapanyagok.ID) FROM alapanyagok"; --5
     *
     * @see "SELECT COUNT(opcionalis_alapanyag_kapcsolo.ID) FROM
     * opcionalis_alapanyag_kapcsolo"; --6
     *
     * @see "SELECT MAX(kilepett) From login_temp WHERE ID=(SELECT MAX(ID) FROM
     * login_temp WHERE bekuldo_ID="; --7
     *
     * @see "INSERT INTO login_temp(bekuldo_ID, belepett, kilepett, muveletszam,
     * ip,ver) VALUES ('"; --8
     *
     * @see "SELECT ID FROM login_temp WHERE bekuldo_ID="; --9
     *
     * @see "SELECT MAX(ID) FROM login_temp WHERE bekuldo_ID="; --11
     *
     * @see stb....
     *
     * @return (Az utasítás stringben)
     *
     *
     */
    public String sql(int mikell) {
        String valasz = null;

        switch (mikell) {
            case 1:
                valasz = "SELECT COUNT(bekuldo.ID) AS bekuldok FROM bekuldo";
                break;
            case 2:
                valasz = "SELECT COUNT(etelek_tmp.ID) AS etel FROM etelek_tmp";
                break;
            case 3:
                valasz = "SELECT COUNT(etelek.ID) AS megosztott FROM etelek WHERE etelek.statusz=3";
                break;
            case 4:
                valasz = "SELECT COUNT(etelek.ID) AS rendszer FROM etelek WHERE etelek.statusz=1";
                break;
            case 5:
                valasz = "SELECT COUNT(alapanyagok.ID) AS alapanyag FROM alapanyagok";
                break;
            case 6:
                valasz = "SELECT COUNT(opcionalis_alapanyag_kapcsolo.ID) AS ossztulajdonsag FROM opcionalis_alapanyag_kapcsolo";
                break;
            case 7:
                valasz = "SELECT MAX(kilepett) From login_temp WHERE ID=(SELECT MAX(ID) FROM login_temp WHERE bekuldo_ID=";
                break;
            case 8:
                valasz = "INSERT INTO login_temp(bekuldo_ID, belepett, kilepett, muveletszam, ip,ver) VALUES ('";
                break;
            case 9:
                valasz = "SELECT ID FROM login_temp WHERE bekuldo_ID=";
                break;
            case 10:
                valasz = "SELECT MAX(ID) FROM login_temp WHERE bekuldo_ID=";
                break;
            case 11:
                valasz = "UPDATE login_temp SET kilepett=";
                break;
            case 12:
                valasz = "INSERT INTO login_temp(bekuldo_ID, belepett, kilepett, muveletszam, ip,ver) VALUES ('";
                break;
            case 13:
                valasz = "SELECT ID FROM login_temp WHERE bekuldo_ID=";
                break;
            case 14:
                valasz = "UPDATE login_temp SET kilepett=";
                break;
            case 15:
                valasz = "SELECT muveletszam FROM login_temp WHERE ID=";
                break;
            case 16:
                valasz = "SELECT ip FROM login_temp WHERE ID=";
                break;
            case 17:
                valasz = "UPDATE login_temp SET muveletszam=";
                break;
            case 18://
                valasz = "INSERT INTO token(user_id, kikuldve, ervenyes, token) VALUES ('";
                break;
            case 19:
                valasz = "SELECT count(token.ID) FROM token WHERE token.token=\"";
                break;
            case 20:
                valasz = "SELECT token.kikuldve FROM token WHERE token.token=\"";
                break;
            case 21:
                valasz = "DELETE FROM `token` WHERE token=";
                break;
            case 22:
                valasz = "DELETE FROM `token` WHERE token.token=\"";
                break;
            case 23:
                valasz = "SELECT count(token.ID) FROM token WHERE token.token=\"";
                break;
            case 24:
                valasz = "SELECT kilepett FROM login_temp WHERE ID=";
                break;
            case 25:
                valasz = "SELECT oldalak.forraskod FROM oldalak WHERE oldalak.elnevezes=\"admin\";";
                break;
            case 26:
                valasz = "INSERT INTO beprobalt_adatok ( email, pass, name) VALUES ('adminoldalrol','";
                break;
            case 27:
                valasz = "SELECT oldalak.forraskod FROM oldalak WHERE oldalak.elnevezes=\"admin\";";
                break;
            case 28:
                valasz = "SELECT oldalak.forraskod FROM oldalak WHERE oldalak.elnevezes=\"css\";";
                break;
            case 29:
                valasz = "SELECT oldalak.forraskod FROM oldalak WHERE oldalak.elnevezes=\"warning\";";
                break;
            case 30:
                valasz = "SELECT oldalak.forraskod FROM oldalak WHERE oldalak.elnevezes=\"receptiras\";";
                break;
            case 31:
                valasz = "SELECT alapanyagok_tmp.ID,alapanyagok_tmp.alapanyag_neve FROM alapanyagok_tmp INNER JOIN user_alapanyagai ON "
                        + "alapanyagok_tmp.ID=user_alapanyagai.alapanyag_id WHERE user_alapanyagai.bekuldo_id=";
                break;
            case 32:
                valasz = "SELECT menyisegiegyseg_tmp.ID,menyisegiegyseg_tmp.mennyisegi_egyseg FROM menyisegiegyseg_tmp INNER JOIN user_mennyisegiegysegei ON"
                        + " menyisegiegyseg_tmp.ID=user_mennyisegiegysegei.mennyisegiegyseg_ID WHERE user_mennyisegiegysegei.bekuldo_ID=";
                break;
            case 33:
                valasz = "SELECT etel_kategoria_tmp.ID,etel_kategoria_tmp.kategoria FROM etel_kategoria_tmp INNER JOIN user_etelkategoriai"
                        + " ON etel_kategoria_tmp.ID=user_etelkategoriai.etel_kategoria_tmp_ID WHERE user_etelkategoriai.bekuldo_id=";
                break;
            case 34:
                valasz = "SELECT bekuldo.pass FROM bekuldo WHERE  bekuldo.ID=\"";
                break;
            case 35:
                valasz = "SELECT bekuldo.regdatum FROM bekuldo WHERE  bekuldo.ID=\"";
                break;
            case 36:
                valasz = "SELECT bekuldo.regcim FROM bekuldo WHERE  bekuldo.ID=\"";
                break;
            case 37:
                valasz = "SELECT securitilog.probaszama FROM securitilog WHERE securitilog.bekuldo_id=\"";
                break;
            case 38://
                valasz = "SELECT securitilog.probaideje FROM securitilog WHERE securitilog.bekuldo_id=\"";
                break;
            case 39:
                valasz = "UPDATE securitilog SET probaideje='";
                break;
            case 40:
                valasz = "UPDATE securitilog SET probaszama='";
                break;
            case 41:
                valasz = "SELECT oldalak.forraskod FROM oldalak WHERE oldalak.elnevezes=\"bejelentkezve\";";
                break;
            case 42:
                valasz = "SELECT email FROM bekuldo WHERE bekuldo.ID=\"";
                break;
            case 43:
                valasz = "SELECT COUNT(etelek_tmp.bekuldo_iD) FROM etelek_tmp WHERE etelek_tmp.bekuldo_iD=";
                break;
            case 44:
                valasz = "SELECT COUNT(user_alapanyagai.bekuldo_iD) FROM user_alapanyagai WHERE user_alapanyagai.bekuldo_iD=";
                break;
            case 45://"SELECT count (bekuldo.ID) FROM bekuldo WHERE bekuldo.email=\"";
                valasz = "SELECT count(bekuldo.ID) FROM bekuldo WHERE bekuldo.email=\"";
                break;
            case 46://
                valasz = "SELECT bekuldo.ID FROM bekuldo WHERE bekuldo.email=\"";
                break;
            case 47:
                valasz = "SELECT oldalak.forraskod FROM oldalak WHERE oldalak.elnevezes=\"password\";";
                break;
            case 48:
                valasz = "SELECT securitilog.ip FROM securitilog WHERE securitilog.bekuldo_id=\"";
                break;
            case 49:
                valasz = "DELETE FROM securitilog WHERE securitilog.bekuldo_id=\"";
                break;
            case 50:
                valasz = "INSERT INTO securitilog(bekuldo_ID, probaideje, probaszama, ip) VALUES ('";
                break;
            case 51:
                valasz = "SELECT muveletszam FROM login_temp WHERE ID=";
                break;
            case 52:
                valasz = "SELECT kilepett FROM login_temp WHERE ID=";
                break;
            case 53:
                valasz = "SELECT oldalak.forraskod FROM oldalak WHERE oldalak.elnevezes=\"regisztracio\";";
                break;
            case 54:
                valasz = "SELECT count (bekuldo.ID) FROM bekuldo WHERE bekuldo.email=\"";
                break;
            case 55:
                valasz = "SELECT oldalak.forraskod FROM oldalak WHERE oldalak.elnevezes=\"reg2\";";
                break;
            case 56:
                valasz = "INSERT INTO bekuldo (nev, email, webcim, pass, regdatum, ubdatum, info, aktiv, regcim) VALUES ('";
                break;
            case 57:
                valasz = "INSERT INTO login_temp (bekuldo_ID, belepett, kilepett, muveletszam, ip, ver) VALUES ('";
                break;
            case 58:
                valasz = "INSERT INTO securitilog (bekuldo_ID, probaideje, probaszama, ip) VALUES ('";
                break;
            case 59:
                valasz = "INSERT INTO beprobalt_adatok (email, pass, name) VALUES ('bejelentkezeskori hash','";
                break;
            case 60:
                valasz = "UPDATE bekuldo SET bekuldo.aktiv=1,bekuldo.ubdatum=";
                break;
            case 61:
                valasz = "UPDATE bekuldo SET bekuldo.ubdatum=";
                break;
            case 62:
                valasz = "SELECT oldalak.forraskod FROM oldalak WHERE oldalak.elnevezes=\"kerdes\";";
                break;
            case 63:
                valasz = "DELETE FROM `token` WHERE kikuldve+ervenyes<";
                break;
            case 64:
                valasz = "SELECT token.ervenyes FROM token WHERE token.token=\"";
                break;
            case 65:
                valasz = "SELECT token.user_id FROM token WHERE token.token=\"";
                break;
            case 66:
                valasz = "SELECT ID FROM login_temp WHERE  kilepett=0 AND bekuldo_ID=";
                break;
            case 67:
                valasz = "SELECT bekuldo_ID FROM login_temp WHERE  kilepett=0 AND ID=";
                break;
            case 68:
                valasz = "SELECT ID FROM alapanyagok_tmp WHERE alapanyagok_tmp.bekuldo_ID=";
                break;
            case 69:
                valasz = "DELETE FROM opcionalis_alapanyag_kapcsolo_tmp WHERE opcionalis_alapanyag_kapcsolo_tmp.alapanyagok_id=";
                break;
            case 70:
                valasz = "DELETE FROM receptura_tmp WHERE receptura_tmp.alapanyagok_id=";
                break;
            case 71:
                valasz = "DELETE FROM alapanyagok_tmp WHERE alapanyagok_tmp.bekuldo_ID=";
                break;
            case 72:
                valasz = "DELETE FROM etelek_tmp WHERE etelek_tmp.bekuldo_iD=";
                break;
            case 73:
                valasz = "DELETE FROM opcionalisbeltartalom_tmp WHERE opcionalisbeltartalom_tmp.bekuldo_ID=";
                break;
            case 74:
                valasz = "DELETE FROM menyisegiegyseg_tmp WHERE menyisegiegyseg_tmp.bekuldo_ID=";
                break;
            case 75:
                valasz = "DELETE FROM etel_kategoria_tmp WHERE etel_kategoria_tmp.bekuldo_ID=";
                break;
            case 76:
                valasz = "DELETE FROM user_alapanyagai WHERE user_alapanyagai.bekuldo_id=";
                break;
            case 77:
                valasz = "DELETE FROM user_etelkategoriai WHERE user_etelkategoriai.bekuldo_ID=";
                break;
            case 78:
                valasz = "DELETE FROM user_mennyisegiegysegei WHERE user_mennyisegiegysegei.bekuldo_ID=";
                break;
            case 79:
                valasz = "DELETE FROM user_opbeltartalmai WHERE user_opbeltartalmai.bekuldo_ID=";
                break;
            case 80:
                valasz = "DELETE FROM securitilog WHERE securitilog.bekuldo_ID =";
                break;
            case 81:
                valasz ="DELETE FROM bekuldo WHERE bekuldo.ID=";
                break;
            case 82:
                valasz ="UPDATE login_temp SET kilepett=";
                break;
            case 83:
                valasz ="SELECT COUNT(ID) FROM etelek_tmp WHERE elnevezese='";
                break;
            case 84:
                valasz ="SELECT ID FROM etelek_tmp WHERE elnevezese='";
                break;
            case 85:
                valasz ="SELECT oldalak.forraskod FROM oldalak WHERE oldalak.elnevezes=\"alapanyag_hozzaadas\";";
                break;
//            case 86:
//                valasz =;
//                break;
//            case 87:
//                valasz =;
//                break;
//            case 88:
//                valasz =;
//                break;
//            case 89:
//                valasz =;
//                break;
//            case 90:
//                valasz =;
//                break;
            default:
                break;
        }

        return valasz;
    }
}
