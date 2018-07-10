/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servletsqlkontakt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Properties;

/**
 *
 * @author dm
 */
public class MutiFunkcio extends javax.swing.JFrame implements Runnable {

    private static final String CONFIG = ".\\config.cfg";
    String configip;
    String JDBC_DRIVER;
    RandomAccessFile log;
    String URL;
//    String LOG_URL;
    String USERNAME;
    String PASSWORD;
//    String LOG_USERNAME;
//    String LOG_PASSWORD;
    String database;
    Connection conn = null;
//    Connection log_conn = null;
    Statement createStatement = null;
//    Statement Log_createStatement = null;
    DatabaseMetaData md = null;
    ResultSet rs1 = null;
    int kiszolgalt = 0;
    int columCount; //oszlop száma
    String[] fejlec;
    String Sql;
    String logba;
    String hiba = "Nem volt";
    int portv;
    int port;
    long uzenet = 0;
    ServerSocket serverSocket = null;
    Socket clientSocket = null;
    public DataOutputStream kimenet;
    public DataInputStream bemenet;
    String inputLine = null;
    String egesz; // a szervletnek visszakuldott kód válasz

    boolean mode = true;
    long data = 0;
    int kuldo = 0;

    int porttmp;
    int keresazon;

    char[] szerverkulcs;
    boolean fut = true;
    boolean indul = true;
    int szama;
    boolean siker = true;
    int logolni = 1;

//    MutiFunkcio(int portonfigyelj) {
//        port = portonfigyelj;
//        initComponents();
//        
//    }
    MutiFunkcio(int szp1) {
        port = szp1;
//        initComponents();
    }

//    @SuppressWarnings("unchecked")
//    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
//    private void initComponents() {
//
////        textArea1 = new java.awt.TextArea();
////        jButton1 = new javax.swing.JButton();
//
////        jButton1.setText("Ablak elrejtése");
////        jButton1.addActionListener(new java.awt.event.ActionListener() {
////            public void actionPerformed(java.awt.event.ActionEvent evt) {
////                jButton1ActionPerformed(evt);
////            }
////        });
////
////        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
////        getContentPane().setLayout(layout);
////        layout.setHorizontalGroup(
////                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
////                        .addGroup(layout.createSequentialGroup()
////                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
////                                        .addGroup(layout.createSequentialGroup()
////                                                .addGap(26, 26, 26)
////                                                .addComponent(textArea1, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE))
////                                        .addGroup(layout.createSequentialGroup()
////                                                .addGap(146, 146, 146)
////                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
////                                .addContainerGap(24, Short.MAX_VALUE))
////        );
////        layout.setVerticalGroup(
////                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
////                        .addGroup(layout.createSequentialGroup()
////                                .addContainerGap()
////                                .addComponent(textArea1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
////                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
////                                .addComponent(jButton1)
////                                .addContainerGap(22, Short.MAX_VALUE))
////        );
////
////        pack();
////    }// </editor-fold>                        
////
////    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
////        MutiFunkcio.this.setVisible(false);
////
//   }
    @Override
    public void run() {

//        MutiFunkcio.this.setVisible(true);
        //--------------------szal kezd
        Properties pr = new Properties();
        File conf = new File(CONFIG);
        if (conf.exists() && conf.canRead()) {
//            textArea1.append("\nConfigfájl beolvasása");
            try {
                pr.load(new FileInputStream(conf));
            } catch (FileNotFoundException ex) {

            } catch (IOException ex) {

            }
            String kelllogolni = pr.getProperty("logolni");
            if (null != kelllogolni) {
                switch (kelllogolni) {
                    case "yes":
                        logolni = 1;
                        break;
                    case "no":
                        logolni = 2;
                        break;
                    case "error":
                        logolni = 3;
                        break;
                    default:
                        break;
                }
            }
            USERNAME = pr.getProperty("dbusername");
            PASSWORD = pr.getProperty("dbpassword");
            String ports = pr.getProperty("dbport");

            JDBC_DRIVER = pr.getProperty("dbdriver");
            URL = pr.getProperty("dburl");
////            LOG_URL = pr.getProperty("logdburl");
////            LOG_USERNAME = pr.getProperty("logdbusername");
//            LOG_PASSWORD = pr.getProperty("logdbpass");
            database = pr.getProperty("dbname");
//            try {
//                log = new RandomAccessFile(database, "rw");
////                textArea1.append("\nLog fájl megnyitva: " + database);
//            } catch (FileNotFoundException ex) {
////                textArea1.append("\nLog fájl nyitási hiba: " + database);
//
//                return;
//            }

        } else {
//            textArea1.append("\nNincs meg a config fájl");

        }

        mode = true;
//        textArea1.setBackground(Color.green);
//        textArea1.setForeground(Color.black);

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
//            textArea1.append("\nA port nem elérhető: " + String.valueOf(port));
            return;
        }
        try {
            serverSocket.setSoTimeout(3000);
        } catch (SocketException ex) {
            try {
                logol("\n Hiba időtullépés, port " + port);
            } catch (IOException ex1) {

            }

        }
        try {
            kiszolgalasindul();
        } catch (IOException ex) {
//            textArea1.append(hiba = hiba+ "\nKiszolgálás indítási hiba: " + ex);
            hiba = hiba + "\nKiszolgálás indítási hiba: " + ex;
        }
        if (siker) {
            try {
                conn.close();
//                textArea1.append("\nAdatbázis lezárárva");
            } catch (SQLException ex) {
//                textArea1.append(hiba=hiba+ " Adatbázis lezárási hiba "+ex);
            }
        }
        try {
            //---------------------szal vegez---strimek fájlok lezárása
            bemenet.close();
        } catch (IOException ex) {

        }
        try {
            kimenet.close();
        } catch (IOException ex) {

        }
        try {
            clientSocket.close();
        } catch (IOException ex) {

        }
        try {
            serverSocket.close();
        } catch (IOException ex) {

        }
//        MutiFunkcio.this.setVisible(false);
        Thread.currentThread().isDaemon();

    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private java.awt.TextArea textArea1;
    // End of variables declaration         

    public void kiszolgalasindul() throws IOException {
//        textArea1.append("\nKérésre várakozik");

        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
//            textArea1.append(hiba=hiba+" Kapcsolódás hiba. "+e);
            logol(" Kapcsolodasi hiba " + e);
            log.close();
            serverSocket.close();
            MutiFunkcio.this.setVisible(false);
            Thread.currentThread().isDaemon();

        }
//        textArea1.append("\nKliens kapcsolódott");
        try {
            kimenet = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException ex) {
//            textArea1.append(hiba=hiba+" Kimenet nyitás hiba "+ex);
            hiba = hiba + " Kimenet nyitás hiba " + ex;

        }
//        textArea1.append("\nKimenet nyitva");
        try {
            bemenet = new DataInputStream(clientSocket.getInputStream());
        } catch (IOException ex) {
//            textArea1.append(hiba=hiba+" Bemenet nyitás hiba. "+ex);
            hiba = hiba + " Bemenet nyitás hiba. " + ex;

        }

        clientSocket.setSoTimeout(10000); //eddig vár befulladt kérésre és lezárja a kapcsolatokat
//
//        textArea1.append("\nBemenet nyitva");
//        textArea1.setBackground(Color.black);
//        textArea1.setForeground(Color.yellow);

        DB();//itt kapcsolódik az adatbázishoz

        //szál indítás módja
        //porttmp=clientSocket.getLocalPort();
        keresazon = 0;
        keresazon = bemenet.readInt();
//        textArea1.append("\nKérés azon:" + keresazon);
        switch (keresazon) {
            case 0:

            case 1:
                fo_select(); //általános lekérdezések
                break;
            case 2:
                adatbevitel(); //módosítás, törlés,átengedett sql
                break;
            case 3:
                special(); //speciális lekérdezések
                break;
            case 4:
                kesselheto(); //speciális lekérdezések
                break;
            default:
                break;
        }
        //  createStatement.close();
        if (logolni == 1) {
            logol(logba + "Port: " + port + "Keres azonosito: " + keresazon + " hiba: " + hiba);
        } else if (logolni == 3) {
            if (!"Nem volt".equals(hiba)) {
                logol(logba + "Port: " + port + "Keres azonosito: " + keresazon + " hiba: " + hiba);
            }
        }

        //log.close();
    }

//<editor-fold defaultstate="collapsed" desc="Log fájl írása">
    public void logol(String logolni) throws IOException {
        //LOG_DB();

        Calendar c = Calendar.getInstance();
        long keszult;
        keszult = c.getTimeInMillis();
        //log.seek(log.length());
        // log.writeUTF("\nTime: " + keszult + "--" + logolni);
        String logsql = null;
        logsql = "INSERT INTO log(alkalmazas, idopont, log) VALUES ('" + database + "','" + keszult + "','" + logolni + "')";
        try {
            createStatement.execute(logsql);
            // Log_createStatement.close();
        } catch (SQLException ex) {
            System.out.print(ex);

        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="SQL lekérdezések átengedése és a válasz visszaküldése módosítás nélkül 3-as kód">
    private void special() {
        //String adatok = "";
        try {

            Sql = bemenet.readUTF(); //átengedem az sql-t az adatbázis felé

//            logba = " host: " + bemenet.readUTF(); //kliens ip adatai
//            textArea1.append(Sql);
        } catch (IOException ex) {
            try {
                kimenet.writeUTF(hiba = "Hiba:Lekérdezés beolvasási, --" + ex + "¤");
            } catch (IOException ex1) {

            }
        }

        try {
            createStatement.execute(Sql);
            try {
                kimenet.writeUTF("Az utasítás lefutott.");
            } catch (IOException ex) {

            }

        } catch (SQLException ex) {
//            textArea1.append(hiba = "Hiba:Adatbeviteli *" + ex + "¤");
            hiba = "Hiba:Adatbeviteli *" + ex + "¤";
//            textArea1.append("" + ex);
            try {
                kimenet.writeUTF(hiba = "Hiba:Adatbeviteli *" + ex + "¤");
            } catch (IOException ex1) {

            }
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Adatbázis kapcsolat felépítése">
    public void DB() {

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

//            textArea1.append("\nA híd létrejött");
        } catch (SQLException ex) {
//            textArea1.append(hiba = "\nA híd nem jött létre" + ex + "¤");

            try {
                kimenet.writeUTF(hiba = "Kapcsolati hiba:\n" + ex + "¤");
            } catch (IOException ex1) {

            }

            return;
        }

        if (conn != null) {

            try {
                createStatement = conn.createStatement();
            } catch (SQLException ex) {
//                textArea1.append(hiba = "\nHiba: createstatement" + ex + "¤");
                hiba = "\nHiba: createstatement" + ex + "¤";
//                textArea1.append("" + ex);
                try {
                    kimenet.writeUTF(hiba = "Kapcsolati hiba:\n" + ex + "¤");
                } catch (IOException ex1) {

                }
            }
        }
        //------edddig az adatdb innen a log db

    }
//</editor-fold>
//
//    //<editor-fold defaultstate="collapsed" desc="Log Adatbázis kapcsolat felépítése">
//    public void LOG_DB() {
//
//        try {
//            log_conn = DriverManager.getConnection(LOG_URL, LOG_USERNAME, LOG_PASSWORD);
//
//            System.out.print("\nA híd létrejött");
//        } catch (SQLException ex) {
////            textArea1.append(hiba = "\nA híd nem jött létre" + ex + "¤");
//            System.out.print(hiba = "Lod database :\n" + ex);
//
//            return;
//        }
//
//        if (log_conn != null) {
//
//            try {
//                createStatement = log_conn.createStatement();
//                 System.out.print("\nConn_ok");
//            } catch (SQLException ex) {
////                textArea1.append(hiba = "\nHiba: createstatement" + ex + "¤");
//                hiba = "\nHiba: createstatement" + ex + "¤";
////                textArea1.append("" + ex);
//                System.out.print(hiba = "Lod database :\n" + ex);
//            }
//        }
//        //------edddig az adatdb innen a log db
//
//    }
////</editor-fold>

//<editor-fold defaultstate="collapsed" desc="általános adatbevitelhez, módosításhoz, törléshez használt átengedett sql utasítás 2-es kód">
    public void adatbevitel() {

        //String adatok = "";
        try {

            Sql = bemenet.readUTF(); //átengedem az sql-t az adatbázis felé

//           logba = " host: " + bemenet.readUTF(); //kliens ip adatai
//            textArea1.append(Sql);
        } catch (IOException ex) {
            try {
                kimenet.writeUTF(hiba = "Hiba:Lekérdezés beolvasási, --" + ex + "¤");
            } catch (IOException ex1) {

            }
        }
        //ResultSet rs = null;
        try {
            createStatement.execute(Sql);
            try {
                kimenet.writeUTF(hiba = Sql);
            } catch (IOException ex) {

            }

        } catch (SQLException ex) {
//            textArea1.append(hiba = "Hiba:Adatbeviteli *" + ex + "¤");
            hiba = "Hiba:Adatbeviteli *" + ex + "¤";
//            textArea1.append("" + ex);
            try {
                kimenet.writeUTF(hiba = "Hiba:Adatbeviteli *" + ex + "¤");
            } catch (IOException ex1) {

            }
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Select sql utasítást átengedő és a válaszokat ,elválasztó jelekkel ellátva visszaadó függvény">
    public void fo_select() {
//        textArea1.append("\nLekérdezés");
        kiszolgalt++;
        egesz = "";
        try {
            Sql = bemenet.readUTF();
            logba = " host: " + bemenet.readUTF();
            logba = logba + Sql;
//            textArea1.append(Sql);
        } catch (IOException ex) {
            try {
                kimenet.writeUTF(hiba = "Hiba:Lekérdezés beolvasási, --" + ex + "¤");
            } catch (IOException ex1) {

            }
        }
        showAllMetaData(); //ugyanezzel a lekérdezéssel a fejlécet alakítom ki
        String sql = Sql;
        ResultSet rs = null;
        try {

            rs = createStatement.executeQuery(sql);

            while (rs.next()) {

                for (int i = 0; i < columCount; i++) {

                    egesz = egesz + "#" + rs.getString(fejlec[i]);//showAllMetaDatabol kiolvasva

                }

                // egesz = egesz + "," + id+"," + name + "," + adress + "," + email + "," + telefon + "¤";
                egesz = egesz + "¤";
            }

            try {
                kimenet.writeUTF(egesz);
                kimenet.flush();
            } catch (IOException ex) {
//                textArea1.append(hiba = "\n" + ex);
                hiba = "" + ex;
            }
        } catch (SQLException ex) {
            try {
//                textArea1.append("\nHiba:Lekérdezési all user\n");
//                textArea1.append("" + ex);
                kimenet.writeUTF(hiba = "Hiba:Lekérdezési:" + ex + "¤");
            } catch (IOException ex1) {
                hiba = "" + ex1;
            }

        }
//        textArea1.append("\n Kiszolgálás szám: " + kiszolgalt);

    }
//</editor-fold>
    
      //<editor-fold defaultstate="collapsed" desc="Select sql utasítást átengedő és a válaszokat ,elválasztó jelekkel ellátva visszaadó függvény, kesselhető válaszok">
    public void kesselheto() {
//        textArea1.append("\nLekérdezés");
        kiszolgalt++;
        egesz = "";
        try {
            Sql = bemenet.readUTF();
            logba = " host: " + bemenet.readUTF();
            logba = logba + Sql;
//            textArea1.append(Sql);
        } catch (IOException ex) {
            try {
                kimenet.writeUTF(hiba = "Hiba:Lekérdezés beolvasási,kessnél --" + ex + "¤");
            } catch (IOException ex1) {

            }
        }
        showAllMetaData(); //ugyanezzel a lekérdezéssel a fejlécet alakítom ki
        String sql = Sql;
        ResultSet rs = null;
        try {

            rs = createStatement.executeQuery(sql);

            while (rs.next()) {

                for (int i = 0; i < columCount; i++) {

                    egesz = egesz + "#" + rs.getString(fejlec[i]);//showAllMetaDatabol kiolvasva

                }

                // egesz = egesz + "," + id+"," + name + "," + adress + "," + email + "," + telefon + "¤";
                egesz = egesz + "¤";
            }

            try {
                kimenet.writeUTF(egesz);
                kimenet.flush();
            } catch (IOException ex) {
//                textArea1.append(hiba = "\n" + ex);
                hiba = "" + ex;
            }
        } catch (SQLException ex) {
            try {
//                textArea1.append("\nHiba:Lekérdezési all user\n");
//                textArea1.append("" + ex);
                kimenet.writeUTF(hiba = "Hiba:Lekérdezési:" + sql + ex + "¤");
            } catch (IOException ex1) {
                hiba = "" + ex1;
            }

        }
//        textArea1.append("\n Kiszolgálás szám: " + kiszolgalt);

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metadatokat visszaadó függvény ">
    public void showAllMetaData() {
        String sql = Sql;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;

        try {
            rs = createStatement.executeQuery(sql);
            rsmd = rs.getMetaData();
            columCount = rsmd.getColumnCount();
            fejlec = new String[columCount];
            for (int i = 1; i <= columCount; i++) {
                //System.out.print(rsmd.getColumnName(i) + "  " + i);
                //textArea1.append("\n " + rsmd.getColumnName(i));
                egesz = egesz + "#" + rsmd.getColumnName(i);
                fejlec[i - 1] = rsmd.getColumnName(i);
            }
            egesz += "¤";

        } catch (SQLException ex) {
//            textArea1.append(hiba = "\nHiba:meteadata Lekérdezési " + ex + "¤");
//            textArea1.append("" + ex);
            hiba = "\nHiba:meteadata Lekérdezési " + ex + "¤";
        }

    }
//</editor-fold>

}
