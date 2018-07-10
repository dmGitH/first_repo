package database;

import java.util.Base64;

/**
 * Sql Injetion securiti class vagy inkább védelmi osztály. A változók
 * értékeinek ellenörzésre sql parancsokra
 */
public class AntiSqlInjection {

    /**
     * SQL kifejezésekre ellenöri
     *
     * @param input (ellenörzendő felhasználói oldalról feltöltött változó
     * tartalma)
     * @return ha van benne SQL kifejezés true ha nincsen akkor false
     */
    public boolean vanbenne(String input) {
        if ("".equals(input) || input == null) {
            input = " ";
        }
        String keresem = "CREATE,ALTER,INDEX,DROP,TEMPORARY,TABLES,SHOW,VIEW,ROUTINE,EXECUTE,CREATE,EVENT,TRIGGER,GRANT,SUPER,PROCES"
                + "S,RELOAD,SHUTDOWN,SHOW,DATABASES,LOCK,TABLES,REFERENCES,REPLICATION,CLIENT,SLAVE,USER,SELECT,INSERT,UPDATE,D"
                + "ELETE,FILE,AND,OR,GROUP,BY,LIKE,ASC,DSC,MAX,COUNT,MIN,WHERE,INNER,JOIN,FROM,DISTINC,SET,"
                + "SYS,ASCII,SUBSTRING,UNION,TEXT,CAST,VARBINARY,SYSCOLUMNS,COLUMNS,OBJECT,XML,AUTO,SPID,BASE64,FOR,RAW,NULL,DATA,DIR,LIMIT";
        String[] lista = keresem.split(",");
        for (String lista1 : lista) {
            input = input.toUpperCase();
            if (input.compareToIgnoreCase(lista1) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * A < > sepeciális jeleket tóvolítja el az inputból
     *
     * @param input (Ellenörzendő input string)
     * @return (tisztított string)
     */
    public String anti_xxs(String input) {

        return (input.replaceAll("<", " ")).replaceAll(">", " ").replaceAll("\"", " ").replaceAll("&#60", " ").replaceAll("&#62", " ").replaceAll("#", " ")
                .replaceAll("#", " ").replaceAll("=", " ");
        

    }
    /**Az inputot kódolja hexa vagy dec html kóddá
     * @param ertek (A kódolandó string)
     * @param dec (a kódolás paramétere dec vagy hex)
     * @return (A kódolt String)*/
    public String ascii_kod(String ertek, String dec) {
        String kodolt = "";
        String hexhtml = "";
        if ("dec".equals(dec)) {
            for (int i = 0; i < ertek.length(); i++) {
                kodolt = kodolt + "&#" + ertek.codePointAt(i) + ";";

            }
            return kodolt;
        } else if ("hex".equals(dec)) {
            for (int i = 0; i < ertek.length(); i++) {

                hexhtml = hexhtml + "&#x" + Integer.toHexString(ertek.codePointAt(i)) + ";";

            }
            return hexhtml;
        }
        return "";
    }
    /**Base 64 kódol
     * @param data (kódolandó string)
     * @return (base64 kódolás)*/
    public String base_encod(String data){
        
        return Base64.getEncoder().encodeToString(data.getBytes());
    }
    /**Base 64 dekódol
     * @param data (dekódolandó string)
     * @return (base64 dekódolás)*/
    public String base_decod(String data){
        
        return new String(Base64.getDecoder().decode(data));
    }
    
    /**Az inputot kódolja url kóddá
     * @param ertek (A kódolandó string)
     * @return (A kódolt String)*/
    public String url_kod(String ertek) {
        
        String urlhtml = "";
        
            for (int i = 0; i < ertek.length(); i++) {

                urlhtml = urlhtml + "%" + Integer.toHexString(ertek.codePointAt(i));

            }
            return urlhtml;
        
    }
}
