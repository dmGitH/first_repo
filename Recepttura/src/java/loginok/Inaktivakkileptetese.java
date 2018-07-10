package loginok;

import adatrogzites.UserLog;
import database.EgyEredmeny;
import database.LekerdezesStringek;

/**
 * A felhasználó bejelentkezettségének és aktivításának ellenörzésére egy szál
 * fut minden bejelentkezett felhasználónál
 */
public class Inaktivakkileptetese implements Runnable {
    
    int login_temp_id;
    int PERCUTAN = 20;

//<editor-fold defaultstate="collapsed" desc="nem jó semmire">
    public int getLogin_temp_id() {
        return login_temp_id;
    }
    
    public void setLogin_temp_id(int login_temp_id) {
        this.login_temp_id = login_temp_id;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="a szál konstruktora">
    public Inaktivakkileptetese(int login_temp_id) {
        this.login_temp_id = login_temp_id;
    }
//</editor-fold>
    
    @Override
    //<editor-fold defaultstate="collapsed" desc="percenként ellenörzön, hogy a felhasználó végzett e regisztrált műveletet az oldalon és ha PERCUTAN-ig nem akkor kiléptetem, ha kilépett a szál elhal">
    public void run() {
        boolean fuss = true;
        EgyEredmeny kereses = new EgyEredmeny();
        LekerdezesStringek kell = new LekerdezesStringek();
        int muveletszam = 0;
        int ellenor = 0;
        int szecund = 0;
        long kilepesiido = 0;
        while (fuss) {
            
            try {
                Thread.sleep((60000));
            } catch (InterruptedException ex) {
                Thread.interrupted();
            }
            muveletszam = Integer.parseInt(kereses.lekerdezes(kell.sql(51) + login_temp_id + "", " : muveletszam visszakerese Thread  "));
            kilepesiido = Long.parseLong(kereses.lekerdezes(kell.sql(52) + login_temp_id + "", " : kepett-e mar ellenorzese Thread  "));
            if (muveletszam == ellenor) {
                szecund++;
                if (szecund == PERCUTAN || kilepesiido != 0) {
                    UserLog kileptet = new UserLog();
                    kileptet.kilepett(login_temp_id);
                    fuss = false;
                }
                
            } else {
                ellenor = muveletszam;
                szecund = 0;
            }
            
        }
    }
//</editor-fold>

}
