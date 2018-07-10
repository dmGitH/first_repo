package database;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import static java.lang.System.out;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Az adatbázis kapcsolati szolgáltatásnak továbbítja az sql parancsokat
 */
public class EgyEredmeny {

    Socket s;
    DataInputStream in;
    DataOutputStream ki;

    int port = 77; //config port

    String configip = "127.0.0.1";
    String egesz = "";

    /**
     * egy eredményt visszaadó lekérdezés küldése az adatbázis kapcsolati
     * szolgáltatás felé
     *
     * @param sql (Az SQL parancs String)
     * @param logba (a log rekordhoz írható programozói üzenet)
     * @return (a lekérdezés eredménye Stringben egyetlen válasz, fejléc nélkül.
     * Ha nincs eredmény akkor a válasz "Nincs eredménye a lekérdezésnek")
     */
    public String lekerdezes(String sql, String logba) {

       if(regszerverhezkapcsololodas(port));
        else {
            regszerverhezkapcsololodas(port-1);
        }
//----------------------------------------------------
        try {
            ki.writeInt(1);
        } catch (IOException ex) {
           
        }
        String lekerdezes = sql;
        try {
            ki.writeUTF(lekerdezes);
        } catch (IOException ex) {
          
        }
        try {
            ki.writeUTF(logba);
        } catch (IOException ex) {
            
        }
        String eredmeny = null;
        try {
            eredmeny = in.readUTF();
        } catch (IOException ex) {
           
        }
        String[] sorok;
        sorok = eredmeny.split("¤");
//        for (int i = 0; i < sorok.length; i++) {
//
//            egesz = egesz + "<tr>";
//            String[] rekordok = sorok[i].split(",");
//            
//            
//        }
        lezaras();
        try {
            return sorok[1].substring(1);
        } catch (Exception e) {
            return "Nincs eredménye a lekérdezésnek";
        }

    }
     /**
     * egy eredményt visszaadó lekérdezés küldése az adatbázis kapcsolati
     * szolgáltatás felé NOKESS
     *
     * @param sql (Az SQL parancs String)
     * @param logba (a log rekordhoz írható programozói üzenet)
     * @return (a lekérdezés eredménye Stringben egyetlen válasz, fejléc nélkül.
     * Ha nincs eredmény akkor a válasz "Nincs eredménye a lekérdezésnek")
     */
    public String lekerdezes_nokess(String sql, String logba) {

       if(regszerverhezkapcsololodas(port));
        else {
            regszerverhezkapcsololodas(port-1);
        }
//----------------------------------------------------
        try {
            ki.writeInt(4);
        } catch (IOException ex) {
           
        }
        String lekerdezes = sql;
        try {
            ki.writeUTF(lekerdezes);
        } catch (IOException ex) {
          
        }
        try {
            ki.writeUTF(logba);
        } catch (IOException ex) {
            
        }
        String eredmeny = null;
        try {
            eredmeny = in.readUTF();
        } catch (IOException ex) {
           
        }
        String[] sorok;
        sorok = eredmeny.split("¤");

        lezaras();
        try {
            return sorok[1].substring(1);
        } catch (Exception e) {
            return "Nincs eredménye a lekérdezésnek";
        }

    }

    private boolean regszerverhezkapcsololodas(int port) {

        try {
            s = new Socket(configip, port);
            s.setSoTimeout(200);
        } catch (IOException ex) {
            // System.out.print("\nNem sikerült csatlakozni a szerverhez\nEllenőrizze a megadott paramétereket");
            // if(port>76)regszerverhezkapcsololodas(port-1);
             return false;
        }
        try {
            in = new DataInputStream(s.getInputStream());

        } catch (IOException ex) {
            // System.out.print("\nNem sikerült a fogadó csatorna nyitás");
            // if(port>76)regszerverhezkapcsololodas(port-1);
            return false;
        }
        try {
            ki = new DataOutputStream(s.getOutputStream());

        } catch (IOException ex) {
            //System.out.print("\nNem sikerült a küldő csatorna nyitás");
            // if(port>76)regszerverhezkapcsololodas(port-1);
            return false;
        }
        //System.out.print("\nKapcsolódási kísérlet OK");

        try {
            ki.writeInt(14);
        } catch (IOException ex) {
            return false;
        }
        int s_port = port;
        try {
            s_port = in.readInt();
        } catch (IOException ex) {
            return false;
        }
        if (s_port == 401) {

            lezaras();
            //  if(port>76)regszerverhezkapcsololodas(port-1);
            return false;

        }
        int port_uj = s_port;
        lezaras();
        try {
            s = new Socket(configip, port_uj);

        } catch (IOException ex) {
            //   if(port>76)regszerverhezkapcsololodas(port-1);

            return false;
        }
        try {
            in = new DataInputStream(s.getInputStream());
            
        } catch (IOException ex) {
            //  if(port>76)regszerverhezkapcsololodas(port-1);

            return false;
        }
        try {
            ki = new DataOutputStream(s.getOutputStream());

        } catch (IOException ex) {
            //  if(port>76)regszerverhezkapcsololodas(port-1);
            return false;
        }

        //System.out.print("\nKapcsolódási kísérlet OK");
        return true;
    }

    private void lezaras() {
        try {
            in.close();

        } catch (IOException ex) {
            Logger.getLogger(EgyEredmeny.class.getName()).log(Level.SEVERE, null, ex);
        }
        out.close();
    }

    /**
     * Adatamanipulációs SQL küldése az adatbázis kapcsolati szolgáltatás felé
     *
     * @param sql (Az SQL parancs String)
     * @param logba (a log rekordhoz írható programozói üzenet)
     * @return (Ha nincs hiba akkor a "hibaüzenet", hogy "Az utasítás lefutott".
     * Különben az SQL szerver hibaüzenetét küldi vissza)
     */
    public String adatbevitel(String sql, String logba) {

        if(regszerverhezkapcsololodas(port));
        else {
            regszerverhezkapcsololodas(port-1);
        }
//----------------------------------------------------
        try {
            ki.writeInt(2);
        } catch (IOException ex) {

           
        }
        String lekerdezes = sql;
        try {
            ki.writeUTF(lekerdezes);
        } catch (IOException ex) {
           
        }
        try {
            ki.writeUTF(logba);
        } catch (IOException ex) {
            
        }
        String eredmeny = null;
        try {
            eredmeny = in.readUTF();
        } catch (IOException ex) {
            
        }

        lezaras();
        return eredmeny;

    }

    /**
     * Több eredményt visszaadó lekérdezés küldése az adatbázis kapcsolati
     * szolgáltatás felé
     *
     * @param sql (Az SQL parancs String)
     * @param logba (a log rekordhoz írható programozói üzenet)
     * @return (a lekérdezés eredménye Stringben a sorok ¤-el elválasztva a
     * rekordok,-vel. Az 1. sor a fejléc)
     */
    public String tobberedmenyes_lekerdezes(String sql, String logba) {
        if(regszerverhezkapcsololodas(port));
        else {
            regszerverhezkapcsololodas(port-1);
        }
//----------------------------------------------------
        try {
            ki.writeInt(1);
        } catch (IOException ex) {
           
        }
        String lekerdezes = sql;
        try {
            ki.writeUTF(lekerdezes);
        } catch (IOException ex) {
           
        }
        try {
            ki.writeUTF(logba);
        } catch (IOException ex) {
           
        }
        String eredmeny = null;
        try {
            eredmeny = in.readUTF();
        } catch (IOException ex) {
            
        }

        lezaras();

        return eredmeny;
    }
//</editor-fold>

}
