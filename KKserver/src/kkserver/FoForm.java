/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkserver;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dm
 */
public class FoForm extends javax.swing.JFrame {

    private static final long serialVersionUID = 5431087325248125418L;

    //private static final long serialVersionUID = 1L;
    /**
     * Creates new form FoForm
     */
    public FoForm() {
        initComponents();
    }

    
    byte[] kriptovakey;
    byte[] dkriptovakey;
    int kkhd = 0;
    boolean kkopen = false;
    String keyfile;
    DataInputStream key;
    Socket s;
    DataInputStream in;
    DataOutputStream out;
    long hash = 0;
    long kesleltet;
    int kilepperc = 0;
    int kkh = 0;

    public void setFut(boolean fut) {
        this.fut = fut;
    }

    ServerSocket serverSocket = null;
    Socket clientSocket = null;
    DataOutputStream kout;
    DataInputStream kin;
    String inputLine = null;
     boolean fut = true;
    boolean indul = true;
    String beolvas = null;
    int buffmeret = 40000;
    byte[] buff;
    long adatcrc = 0;

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textArea1 = new java.awt.TextArea();
        jFileChooser1 = new javax.swing.JFileChooser();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField2 = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jCheckBox1 = new javax.swing.JCheckBox();
        jComboBox5 = new javax.swing.JComboBox<>();
        textArea2 = new java.awt.TextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("KKS Kriptova üzenetfogadó modul");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        textArea1.setBackground(new java.awt.Color(0, 204, 204));
        textArea1.setText("Válassza ki a beszélgető partnerével közös kulcs-állományt. Ez bármi lehet, csak  a \"beszélgető\" partnerének is legyen meg,\n mert különben nem fogják tudni olvasni egymás üzeneteit. Célszerü egy előre megbeszélt kulcsállomány cseréje egymás\n között személyesen. A kulcsállomány lehet zip, jpg .... minél változatosabb adattartalommal. Semmiképpen ne legyen csupa\n egyforma karakter vagy kisebb, mint 250 bájt. \n\n(A Kriptova titkosító programmal tud kulcsállományokat generálni.) \n\nÁllítsa be a regisztrációs szerverük címét és portját.  Írjon be egy tetszőleges login nevet, amin elérhető az ugyanazon kulcsot\n és regisztrációs szervert használó felhasználók számára. (a login nevét is ismernie kell annak aki keresi, a kulccsal együtt.) \n Majd a kiválasztott kulcs-állományom kattintson duplán. Ha a szerver regisztrálta. Az ön elérhetőségét a partnerei onnan\n kikérhetik, login név alapján, ha a kulcsuk ellenörző kódja megfelelő. A regisztráció virtuális, nem kerül lemezre a szerver \n30 perc inaktivítás után, vagy az ön kérésére, kilépéskor törli a memóriájából.\n\nHa sikeresen regisztrálta a fogadó oldalt, indítsa el az  üzenetküldő modult és kövesse az ott leírtakat.");

        jFileChooser1.setDialogType(javax.swing.JFileChooser.CUSTOM_DIALOG);
        jFileChooser1.setControlButtonsAreShown(false);
        jFileChooser1.setCurrentDirectory(new java.io.File("C:\\"));
            jFileChooser1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jFileChooser1ActionPerformed(evt);
                }
            });

            jButton1.setText("Start");
            jButton1.setEnabled(false);
            jButton1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton1ActionPerformed(evt);
                }
            });

            jTextField1.setText("127.0.0.1");
            jTextField1.setToolTipText("Regisztrációs szerver IP, host");

            jComboBox1.setToolTipText("Reg szerver port");

            jTextField2.setText("Felhasznalo01");
            jTextField2.setToolTipText("aliasnév, amin az a kapcsolódási adatok elérhetőek a másik félnek");

            jComboBox2.setToolTipText("Késleltetési idő - regszerverhez");

            jButton2.setText("Fogadás");
            jButton2.setEnabled(false);
            jButton2.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton2ActionPerformed(evt);
                }
            });

            jComboBox3.setToolTipText("Tétlen perc után automatikusan törli a regszerver bejegyzést nem fogad üzenetet");

            jComboBox4.setToolTipText("Üzenet fogadó port");

            jCheckBox1.setSelected(true);
            jCheckBox1.setText("Adatfogadást enged");

            jComboBox5.setToolTipText("Adatbuffer mérete, a küldőével egyeznie kell.");

            textArea2.setBackground(new java.awt.Color(0, 0, 0));
            textArea2.setForeground(new java.awt.Color(0, 255, 0));

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jFileChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jButton2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jCheckBox1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10))
                        .addComponent(textArea1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(textArea2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(textArea2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(2, 2, 2)
                    .addComponent(textArea1, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, 0)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton2)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jCheckBox1)
                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(20, 20, 20))
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        for (int i = 0; i < 1200; i++) {
            jComboBox1.addItem(String.valueOf(i));
        }
        jComboBox1.setSelectedIndex(77);
        for (int i = 0; i < 3000; i += 50) {
            jComboBox2.addItem(String.valueOf(i));
        }
        jComboBox2.setSelectedIndex(5);
        for (int i = 0; i < 120; i++) {
            jComboBox3.addItem(String.valueOf(i));
        }
        jComboBox3.setSelectedIndex(10);
        for (int i = 0; i < 1200; i++) {
            jComboBox4.addItem(String.valueOf(i));
        }
        jComboBox4.setSelectedIndex(78);
        for (int i = 100; i < 50000; i += 100) {
            jComboBox5.addItem(String.valueOf(i));
        }
        jComboBox5.setSelectedIndex(100);


    }//GEN-LAST:event_formWindowOpened

   

   

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here://jelszó ellenörzés regisztráció,,átrendezés

        kesleltet = jComboBox2.getSelectedIndex() * 50;

        if (null != jButton1.getText()) {
            switch (jButton1.getText()) {
                case "Start":
                    textArea1.setText("\nNyomja meg az Indít gombot a kezdéshez\n"
                            + "Ha beállította a regisztrációs szerver paramétereit és a login nevet");
                    jFileChooser1.setVisible(false);
                    textArea1.setSize(940, 600);
                    textArea1.setBackground(Color.black);
                    textArea1.setForeground(Color.yellow);
                    jButton1.setText("Indit");

                    //textArea1.resize(940, 600);
                    break;
                //lezaras();
                case "Indit":
                    // textArea1.append("\nitt kezdem a regisztrálást");
                    kapcsolodas();
                    regisztralas();
                    break;
                case "Unreg Auto":
                    kapcsolodas();
                    kilep();
                    break;
                default:
                    break;
            }
        }


    }//GEN-LAST:event_jButton1ActionPerformed


    private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser1ActionPerformed
        // TODO add your handling code here:
        keyfile = jFileChooser1.getSelectedFile().getAbsolutePath();
        textArea1.setText("\n" + keyfile + " a kiválasztott kulcsállomány\n"
                + "Ha ok akkor nyomja meg a Start gombot");

        DataInputStream beolvas;

        try {
            beolvas = new DataInputStream(new FileInputStream(keyfile));
        } catch (FileNotFoundException ex) {
            textArea1.append("\nFájl megnyitási hiba");
            Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        if (jFileChooser1.getSelectedFile().length() < 2000000) {
            kriptovakey = new byte[(int) jFileChooser1.getSelectedFile().length()];
        } else {
            kriptovakey = new byte[20000000];
        }

        try {
            beolvas.read(kriptovakey);
        } catch (IOException ex) {
            textArea1.append("\nKulcs beolvasási hiba");
            Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        hash = 0;
        kkh = kriptovakey.length - 1;
        dkriptovakey = new byte[kriptovakey.length];
        for (int i = 0; i < kriptovakey.length - 1; i++) {
            hash = hash + kriptovakey[i] + 1;
        }
        for (int i = 0; i < kriptovakey.length - 1; i++) {
            dkriptovakey[i] = (byte) (kriptovakey[i] + 1);

        }
        textArea1.append("\nKulcs beolvasva");
        jButton1.setEnabled(true);
        try {
            beolvas.close();
            textArea1.append("\nKulcsfájl lezárva");
        } catch (IOException ex) {
            textArea1.append("\nFájl lezárási hiba");
            Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jFileChooser1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:


    }//GEN-LAST:event_formWindowClosing

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here://uzenetfogadás indítása
        figyelesindul();
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FoForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FoForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FoForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FoForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new FoForm().setVisible(true);
        });
       
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private java.awt.TextArea textArea1;
    private java.awt.TextArea textArea2;
    // End of variables declaration//GEN-END:variables

    public void regisztralas() {

        try {
            out.writeInt(1);
        } catch (IOException ex) {
            textArea1.append("\nKimenet írás hiba kérés");
            Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            out.writeUTF(jTextField2.getText());
        } catch (IOException ex) {
            textArea1.append("\nKimenet írás hiba login");
            Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            out.writeLong(hash);
        } catch (IOException ex) {
            textArea1.append("\nKimenet írás hiba hashkód");
            Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            out.writeInt(jComboBox4.getSelectedIndex());
        } catch (IOException ex) {
            textArea1.append("\nKimenet írás hiba port");
            Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            sleep(kesleltet);
        } catch (InterruptedException ex) {
            Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            String szervervalasz = null;
            while (in.available() > 0) {
                szervervalasz = in.readUTF();
                textArea1.append("\n " + szervervalasz);
                if ("Regisztráció megtörtént".equals(szervervalasz)) {
                    jButton2.setEnabled(true);
                    jButton1.setText("Unreg Auto");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
        }

        lezaras();
    }

    public void lezaras() {
        //kliens lezárása

        try {
            in.close();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (IOException ex) {
            Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            s.close();
        } catch (IOException ex) {
            Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void kapcsolodas() {

        try {
            s = new Socket(jTextField1.getText(), jComboBox1.getSelectedIndex());
            textArea1.append(".");
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (IOException ex) {
            textArea1.setText("\nNem sikerült csatlakozni a szerverhez\nEllenőrizze a megadott paramétereket");
            Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        try {
            in = new DataInputStream(s.getInputStream());
            textArea1.append(".");
        } catch (IOException ex) {
            textArea1.setText("\nNem sikerült a fogadó csatorna nyitás");
            Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        try {
            out = new DataOutputStream(s.getOutputStream());
            textArea1.append(".");
        } catch (IOException ex) {
            textArea1.setText("\nNem sikerült a küldő csatorna nyitás");
            Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);

        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void kilep() {
        try {
            out.writeInt(2);
        } catch (IOException ex) {
            textArea1.append("\nKimenet írás hiba: kérés");
            Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            out.writeUTF(jTextField2.getText());
        } catch (IOException ex) {
            textArea1.append("\nKimenet írás hiba: login");
            Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            out.writeLong(hash);
        } catch (IOException ex) {
            textArea1.append("\nKimenet írás hiba: hashkód");
            Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            sleep(kesleltet);
        } catch (InterruptedException ex) {
            Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            while (in.available() > 0) {
                textArea1.append("\n " + in.readUTF());
            }
        } catch (IOException ex) {
            Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        jButton1.setText("Indit");
        lezaras();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void figyelesindul() {

        fut = true;
//   Thread AljLe=new Stop();
//    AljLe.start();
        
        if (indul) {

            if (jComboBox1.getSelectedIndex() == 0) {
                return;
            }
            int port = jComboBox4.getSelectedIndex(); //üzenet fogadó port

            Calendar c = Calendar.getInstance();

            String indult;
            indult = c.getTime().toLocaleString();
            textArea1.append("\nÜzenetfogadás aktív: " + indult + "\n\n");

            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException e) {
                textArea1.append("\nA port nem elérhető: " + String.valueOf(port));
                return;
            }
            indul = false;
        }

        while (fut) {

            kiszolgalasindul();

        }

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void kiszolgalasindul() {

        String uzenet = null;
        while (true) {

            try {
                //textArea1.append("\nÜzenet:");
                serverSocket.setSoTimeout(1000 * 120);
            } catch (SocketException ex) {
                textArea1.append("\nTime out setup error");
                Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                textArea1.append(".");

                kapcsolodas(); //a regisztracios szerverhez
                megerosites();

                kilepperc += 2;
                if (kilepperc > jComboBox3.getSelectedIndex()) {
                    kapcsolodas();
                    kilep();
                    fut = false;
                    jButton2.setEnabled(false);
                }
                return;
            }

            try {
                kout = new DataOutputStream(clientSocket.getOutputStream());
            } catch (IOException ex) {
                textArea1.append("\nKimenet nyitás hiba");
                Logger.getLogger(szerver.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                //textArea1.append("\nKimenet nyitva");
                kout.writeInt(kkh);  //kulcsszinkron jel küldése
            } catch (IOException ex) {
                Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                kin = new DataInputStream(clientSocket.getInputStream());
            } catch (IOException ex) {
                textArea1.append("\nBemenet nyitás hiba.");
                Logger.getLogger(szerver.class.getName()).log(Level.SEVERE, null, ex);
            }

            textArea1.append(clientSocket.getInetAddress().getHostAddress() + ": ");
            kilepperc = 0;//tétlenségi idő mérés nullázása
            try {
                uzenet = kin.readUTF();
            } catch (IOException ex) {
                textArea1.append("\nÜzenet olvasási hiba");
                Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            if ("UZENET_VEGE".equals(uzenet)) {
                String kilep = clientSocket.getInetAddress().getHostAddress();

                if ("127.0.0.1".equals(kilep)) {
                    fut = false;
                    kkh = 0;
                    kiszolgalaslezar();
                    kapcsolodas();
                    kilep();
                    System.exit(0);
                }

            }
            //------------bárki kiléptethető---------------
            if (("BEZAR_A_BAZAR").equals(uzenet)) {
                fut = false;
                kkh = 0;
                kiszolgalaslezar();
                kapcsolodas();
                kilep();
                System.exit(0);

            }
            if ("FAJL_KULDES_".equals(uzenet)) {
                textArea1.append("\nFájl fogadására érkezett felkérés");
                if (jCheckBox1.isSelected()) {
                    try {
                        textArea1.append("\nÖnnél a fájl fogadása engedélyezve van.\n");
                        kout.writeUTF("_OK_");
                    } catch (IOException ex) {
                        Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    textArea1.append("\nÖnnél nem engedélyezett a fájl fogadása\n");
                    try {
                        kout.writeUTF(" ");
                    } catch (IOException ex) {
                        Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                break;

            }
            if ("_FFBE_".equals(uzenet)) {
                String kilep = clientSocket.getInetAddress().getHostAddress();
                if ("127.0.0.1".equals(kilep)) {
                    jCheckBox1.setSelected(true);
                    textArea1.append("\nFájl fogadás bekapcsolva\n");
                }
                break;

            }
            if ("_FFKI_".equals(uzenet)) {
                String kilep = clientSocket.getInetAddress().getHostAddress();
                if ("127.0.0.1".equals(kilep)) {
                    jCheckBox1.setSelected(false);
                    textArea1.append("\nFájl fogadás kikapcsolva\n");
                }
                break;

            }

            if ("_KULDEMENY_".equals(uzenet)) {
                if (jCheckBox1.isSelected()) {
                    try {
                        kout.writeUTF("_JOJJON_");
                    } catch (IOException ex) {
                        Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //fajlfogadas();

                    Fajltfogad task = new Fajltfogad();
                    task.run();
                    return;
                } else {

                    try {
                        kout.writeUTF("_STOP_");
                    } catch (IOException ex) {
                        Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                break;
            }

            beolvas = uzenet;
            titkosit();

            if (("_BEZAR_A_BAZAR_").equals(beolvas)) {
                fut = false;
                kkh = 0;
                kiszolgalaslezar();
                kapcsolodas();
                kilep();
                System.exit(0);

            }

            if (beolvas != null) {
                textArea1.append(beolvas);
            } else {
                kapcsolodas(); //a regisztracios szerverhez
                megerosites();

            }

            if (beolvas != null) {
                try {
                    kout.writeUTF("fogadva...");

                    beolvas = "";
                } catch (IOException ex) {
                    textArea1.append("\nVisszajelzés hiba");
                    Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
        }
        kiszolgalaslezar();

    }

    private void megerosites() {
        try {
            out.writeInt(4);
        } catch (IOException ex) {
            textArea1.append("\nKimenet írás hiba kérés");
            Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            out.writeUTF(jTextField2.getText());
        } catch (IOException ex) {
            textArea1.append("\nKimenet írás hiba login");
            Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            out.writeLong(hash);
        } catch (IOException ex) {
            textArea1.append("\nKimenet írás hiba hashkód");
            Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            sleep(kesleltet);
        } catch (InterruptedException ex) {
            Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            while (in.available() > 0) {
                textArea1.append("\n " + in.readUTF());
            }
        } catch (IOException ex) {
            Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
        }

        lezaras();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void titkosit() {

        int hossza = beolvas.length();
        char[] beolvasbyte = new char[hossza];

        for (int i = 0; i < hossza; i++) {
            beolvasbyte[i] = beolvas.charAt(i);
            if (kkh < 0) {
                kkh = kriptovakey.length - 1;
            }
            beolvasbyte[i] = (char) ((char) beolvasbyte[i] ^ kriptovakey[kkh]);
            kkh--;
        }
        beolvas = String.valueOf(beolvasbyte);
    }

    private void kiszolgalaslezar() {
        try {
            kin.close();
        } catch (IOException ex) {
            Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            kout.close();
        } catch (IOException ex) {
            Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            clientSocket.close();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (IOException ex) {
            Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /*  private void fajlfogadas() {
       //fajlnev, hossz beolvasása c iklusban átvétel mentés könyvtár létrehozása
       DataOutputStream kiir = null;
       String fajlnev = null;
        try {
            fajlnev=kin.readUTF();
        } catch (IOException ex) {
            textArea1.append("\nFogadott file név  olvasási hiba");
            Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        File dir = new File("fogadott");
        dir.mkdir();
        try {
            kiir=new DataOutputStream(new FileOutputStream(".\\fogadott\\"+fajlnev));
        } catch (FileNotFoundException ex) {
             textArea1.append("\nFogadott file létrehozási hiba");
            Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
        }
         textArea1.append("\nFogadott file neve: "+fajlnev);
        
        long filemeret=0;
        try {
            filemeret=kin.readLong();
        } catch (IOException ex) {
             textArea1.append("\nFogadott file méret  olvasási hiba");
            Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         textArea1.append("\nFogadott file mérete: "+filemeret);
         
         //---------------van-e elegendő szabad hely a fájl fogadásához
         
         //---------bufferméret beállítás
         int fogadottbuff=0;
         
        try {
            fogadottbuff=kin.readInt();
        } catch (IOException ex) {
            textArea1.append("\nBuffer méret szinkron  olvasási hiba");
            Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        jComboBox5.setSelectedIndex(fogadottbuff);
        
         
        
        long olvasott=0;
        buffmeret=(jComboBox5.getSelectedIndex()+1)*100;
        buff = new byte[buffmeret];
        kkhd=0;
        adatcrc=0;
        int var=0;
        while(olvasott<filemeret){
        
            if(olvasott+buffmeret>filemeret){
                int ujmeret=(int) ((int)filemeret-olvasott);
                buff=new byte[ujmeret];
            }
           
           try {
               while(kin.available()<buff.length){
                   if(kin.available()>=buff.length){
                       var++;
                       break;
                   }
                   
               }
           } catch (IOException ex) {
               Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
           }
                        
           try {
               kin.read(buff);
           } catch (IOException ex) {
                try {
                    textArea1.append("\nBuffer olvasási hiba");
                    textArea1.append("\nAz átvitel nem sikerült. Talán a küldő vegyen lejebb a buffer méretből");
                    kiszolgalaslezar();
                    kiir.close();
                    fut=false;
                    Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex1) {
                    Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex1);
                    return;
                }
                return;
           }
            
           adattitkositas(); 
           
            
           try {
               kiir.write(buff);
           } catch (IOException ex) {
               textArea1.append("\nFájl írás hiba");
               Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
           }
           
           olvasott+=buffmeret;
           textArea2.setText("Adatra várt: "+var+" | "+buffmeret+" bufferméret "+fajlnev+": "+filemeret/1024/1024+" MB "+olvasott/1024/1024+" fogadott MB "
                   + "---> %: "+(double)((double)olvasott/(double)filemeret)*100);
        }
        textArea1.append("\nFájl átvitelnek vége\n ");
        
        
        try {
            kiir.close();
        } catch (IOException ex) {
            Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
        textArea1.append("\nAdatacrc: "+adatcrc+"\n");
        kiszolgalaslezar();
        fut=false;
    } */
    private void adattitkositas() {

        for (int i = 0; i < buff.length; i++) {
            if (kkhd < 0) {
                kkhd = kriptovakey.length - 1;
            }
            buff[i] = (byte) (buff[i] ^ kriptovakey[kkhd]);
            adatcrc += buff[i];
            kkhd--;
        }

    }

    class Fajltfogad implements Runnable {

        //@Override
        @Override
        public void run() {

            //fajlnev, hossz beolvasása c iklusban átvétel mentés könyvtár létrehozása
            DataOutputStream kiir = null;
            String fajlnev = null;
            try {
                fajlnev = kin.readUTF();
            } catch (IOException ex) {
                textArea1.append("\nFogadott file név  olvasási hiba");
                Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
            }

            File dir = new File("fogadott");
            dir.mkdir();
            try {
                kiir = new DataOutputStream(new FileOutputStream(".\\fogadott\\" + fajlnev));
            } catch (FileNotFoundException ex) {
                textArea1.append("\nFogadott file létrehozási hiba");
                Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            textArea1.append("\nFogadott file neve: " + fajlnev);

            long filemeret = 0;
            try {
                filemeret = kin.readLong();
            } catch (IOException ex) {
                textArea1.append("\nFogadott file méret  olvasási hiba");
                Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
            }

            textArea1.append("\nFogadott file mérete: " + filemeret);

            //---------------van-e elegendő szabad hely a fájl fogadásához
            //---------bufferméret beállítás
            int fogadottbuff = 0;

            try {
                fogadottbuff = kin.readInt();
            } catch (IOException ex) {
                textArea1.append("\nBuffer méret szinkron  olvasási hiba");
                Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            jComboBox5.setSelectedIndex(fogadottbuff);

            long olvasott = 0;
            buffmeret = (jComboBox5.getSelectedIndex() + 1) * 100;
            buff = new byte[buffmeret];
            kkhd = 0;
            adatcrc = 0;
            int var = 0;
            while (olvasott < filemeret) {

                if (olvasott + buffmeret > filemeret) {
                    int ujmeret = (int) ((int) filemeret - olvasott);
                    buff = new byte[ujmeret];
                }

                try {
                    while (kin.available() < buff.length) {
                        if (kin.available() >= buff.length) {
                            var++;

                            break;
                        }

                    }
                } catch (IOException ex) {
                    Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    kin.read(buff);
                } catch (IOException ex) {
                    try {
                        textArea1.append("\nBuffer olvasási hiba");
                        textArea1.append("\nAz átvitel nem sikerült. Talán a küldő vegyen lejebb a buffer méretből");
                        kiszolgalaslezar();
                        kiir.close();
                        fut = false;
                        Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex1) {
                        Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex1);
                        return;
                    }
                    return;
                }

                adattitkositas();

                try {
                    kiir.write(buff);
                } catch (IOException ex) {
                    textArea1.append("\nFájl írás hiba");
                    Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
                }

                olvasott += buffmeret;
                textArea2.setText("Adatra várt: " + var + " | " + buffmeret + " bufferméret " + fajlnev + ": " + filemeret / 1024 / 1024 + " MB " + olvasott / 1024 / 1024 + " fogadott MB "
                        + "    " + (int) ((double) ((double) olvasott / (double) filemeret) * 100) + " %");
            }
            textArea1.append("\nFájl átvitelnek vége\n ");

            try {
                kiir.close();
            } catch (IOException ex) {
                Logger.getLogger(FoForm.class.getName()).log(Level.SEVERE, null, ex);
            }

            textArea1.append("\nAdatacrc: " + adatcrc + "\n");
            kiszolgalaslezar();
            fut = false;
            figyelesindul();
        }

    }

}
