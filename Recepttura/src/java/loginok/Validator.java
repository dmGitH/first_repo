
package loginok;

/**
 * Primitív, robot regisztátor elleni védelem
 */
public class Validator {

    private String muvelet;

    private String eredmeny;

    static final String[] szamok = {"egy", "kettő", "három", "négy", "öt", "hat", "hét", "nyolc", "kilenc", "tíz", "tizenegy", "tizenkettő", "tizenhárom", "tizennégy", "tizenöt", "tizenhat", "tizenhét", "tizennyol"
        + "c", "tizenkilenc", "húsz", "huszonegy", "huszonkettő", "huszonhárom", "huszonnégy", "huszonöt", "huszonhat", "huszonhét", "huszonnyolc", "huszonkilenc", "harmin"
        + "c", "harmincegy", "harminckettő", "harminchárom", "harmincnégy", "harmincöt", "harminchat", "harminchét", "harmincnyolc", "harminckilenc", "negyven", "negyven"
        + "egy", "negyvenkettő", "negyvenhárom", "negyvennégy", "negyvenöt", "negyvenhat", "negyvenhét", "negyvennyolc", "negyvenkilenc", "ötven", "ötvenegy", "ötvenkettő", ""
        + "ötvenhárom", "ötvennégy", "ötvenöt", "ötvenhat", "ötvenhét", "ötvennyolc", "ötvenkilenc", "hatvan", "hatvanegy", "hatvankettő", "hatvanhárom", "hatvannégy", "hatvan"
        + "öt", "hatvanhat", "hatvanhét", "hatvannyolc", "hatvankilenc", "hetven", "hetvenegy", "hetvenkettő", "hetvenhárom", "hetvennégy", "hetvenöt", "hetvenhat", "hetvenhét", "hetven"
        + "nyolc", "hetvenkilenc", "nyolcvan", "nyolcvanegy", "nyolcvankettő", "nyolcvanhárom", "nyolcvannégy", "nyolcvanöt", "nyolcvanhat", "nyolcvanhét", "nyolcvannyolc", "nyolcvan"
        + "kilenc", "kilencven", "kilencvenegy", "kilencvenkettő", "kilencvenhárom", "kilencvennégy", "kilencvenöt", "kilencvenhat", "kilencvenhét", "kilencvennyolc", "kilencvenkilenc", "száz"};

    /**
     * Egy szöveges szorzást generál
     */
    public void general() {

        int egyik = (int) ((Math.random() * 10) + 1);
        int masik = (int) ((Math.random() * 10) + 1);

        int reeredmeny = egyik * masik;
        muvelet = szamok[egyik - 1] + "*" + szamok[masik - 1];
        eredmeny = szamok[reeredmeny - 1];

    }

    public String getMuvelet() {
        return muvelet;
    }

    /**
     * visszaadja a szorzást sztringben
     * @return 
     */
    public String getEredmeny() {
        return eredmeny;
    }

}
