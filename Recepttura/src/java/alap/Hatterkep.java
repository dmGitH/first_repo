/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alap;

/**
 * Képet tölt be randomra egy mappából
 *
 * return "background-image: url(img/valamilyenkep.jpeg);"
 */
public class Hatterkep {

    int KEPEKSZAMA = 55;

    /**
     * Képet tölt be randomra egy mappából
     *
     * @return "background-image: url(img/valamilyenkep.jpeg);"
     */
    public String kepet() {
        int sors = (int) (Math.random() * KEPEKSZAMA) + 1;
        return "background-image: url(img/" + sors + ".jpeg);";
    }
    
    /**Képet tölt be randomra egy mappábó
     * @param width (szelessege pixel)
     * @param height (magassaga pixel)
     * @param border (keret 0 nincsen)
     * @return (return " + sors + ".jpeg width=\""+width+"\" height=\""+height+"\" border=\""+border+"\">" )*/
    public String kepet_IMGSRC(int width,int height,int border) {
        int sors = (int) (Math.random() * KEPEKSZAMA) + 1;
        return "img/" + sors + ".jpeg width=\""+width+"\" height=\""+height+"\" border=\""+border+"\">";
    }
}
