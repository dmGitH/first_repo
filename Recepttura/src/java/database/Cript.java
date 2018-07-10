/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

public class Cript {

    String encript;

//<editor-fold defaultstate="collapsed" desc="A jelszót kodolja az adatbázisba és vissza a megadott kulccsal">
    /**
     * A jelszót kodolja az adatbázisba és vissza a megadott kulccsal
     *
     * @param kodolando
     * @param kulcs
     * @return
     */
    public String kodol(String kodolando, String kulcs) {
//---------------------------
        int hossza = kodolando.length();
        char[] beolvasbyte = new char[hossza];
        char[] key = new char[kulcs.length()];
        int kkh = 0;

        for (int i = 0; i < hossza; i++) {
            beolvasbyte[i] = kodolando.charAt(i);
            key[kkh] = kulcs.charAt(kkh);
            if (kkh > kulcs.length()) {
                kkh = 0;
            }
            beolvasbyte[i] = (char) ((char) beolvasbyte[i] ^ key[kkh]);
            kkh++;
        }
        encript = String.valueOf(beolvasbyte);
        return encript;
    }
//</editor-fold>

    /**
     * A felhasználó belépését hitelesítő függvén
     *
     * @param datapass az adatbázisban tárolt jelszó visszakódolva
     * @param hdid = long token a válasszal jön
     * (request.getParameter("HDTOKEN").toString)
     * @param atok = request.getParameter("atok");
     * @return a számolt kulcs a belépéshez
     */
    public String belepes(String datapass, String hdid, String atok) {
        int a = 0;
        String err = "";
        //out.println(atok + "<p></p>" + pass2);
        String x = datapass;
        x = x.toUpperCase() + x + x.toLowerCase();
        //out.println("<p></p>" + x);
        if (hdid.length() > x.length()) {
            for (int i = 0; i < hdid.length(); i++) {
                if (a > x.length() - 1) {
                    a = 0;
                }
                if (hdid.codePointAt(i) < x.codePointAt(a)) {
                    err = err + hdid.codePointAt(i) + x.codePointAt(a);
                } else {
                    err = err + x.codePointAt(a) + hdid.codePointAt(i);
                }
                a++;
            }
        } else {
            for (int i = 0; i < x.length(); i++) {
                if (a > hdid.length() - 1) {
                    a = 0;
                }
                if (hdid.codePointAt(a) < x.codePointAt(i)) {
                    err = err + hdid.codePointAt(a) + x.codePointAt(i);
                } else {
                    err = err + x.codePointAt(i) + hdid.codePointAt(a);
                }
                a++;
            }
        }

        for (int k = 0; k < 20; k++) {
            err = err + err.charAt(k) + atok.charAt((atok.length() - 2) - k);
        }

        //out.println("param: " + err);
        String tmp = "";
        int valto = 0;
        for (int i = 0; i < err.length(); i++) {
            if (atok.length() - 1 < valto) {
                valto = 0;
            }
            tmp = tmp + (err.codePointAt(i) ^ atok.codePointAt(valto));
            tmp = tmp.replace(" ", "-");
            valto++;
        }

        return tmp;
    }

    /**
     * A regisztracios adatok olvashatóvá tétele
     *
     * @param kodolt
     * @param kulcs
     * @return dekodolt String
     */
    public String reg_dekodol(String kodolt, String kulcs) {
        String temp = "";
        String[] szamok;
        szamok = kodolt.split(",");
        int kulcsindex = 0;
        int cc;
        int key;
        for (int i = 1; i < szamok.length; i++) {
            if (kulcsindex > kulcs.length() - 1) {
                kulcsindex = 0;
            }

            cc = Integer.parseInt(szamok[i]);
            key = kulcs.codePointAt(kulcsindex);

            temp = temp + (char) (cc ^ key);
            kulcsindex++;

        }

        return temp;
    }
}
