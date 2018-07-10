
//pojo
package tkv;

import javafx.beans.property.SimpleStringProperty;//az adatatábla megjelenítő elemmel kompatibilis adathordozó


public class Ember {

    private final SimpleStringProperty vezeteknevOBJ;
    private final SimpleStringProperty keresztnevOBJ;
    private final SimpleStringProperty emailOBJ;
    private final SimpleStringProperty idOBJ;
    
    
    public Ember() {
        this.vezeteknevOBJ = new SimpleStringProperty("");
        this.keresztnevOBJ = new SimpleStringProperty("");
        this.emailOBJ = new SimpleStringProperty("");
        this.idOBJ = new SimpleStringProperty("");
    }
    
    public Ember(String id,String vezeteknevE, String keresztnevE, String emailcimE) {
        this.vezeteknevOBJ = new SimpleStringProperty(vezeteknevE);
        this.keresztnevOBJ = new SimpleStringProperty(keresztnevE);
        this.emailOBJ = new SimpleStringProperty(emailcimE);
        this.idOBJ = new SimpleStringProperty(id);
    }
    public Ember(int id,String vezeteknevE, String keresztnevE, String emailcimE) {
        this.vezeteknevOBJ = new SimpleStringProperty(vezeteknevE);
        this.keresztnevOBJ = new SimpleStringProperty(keresztnevE);
        this.emailOBJ = new SimpleStringProperty(emailcimE);
        this.idOBJ = new SimpleStringProperty(String.valueOf(id));
    }

    
    

    public String getVezeteknev() {
        return vezeteknevOBJ.get();
    }

    public void setVezeteknev(String vezeteknev) {
        this.vezeteknevOBJ.set(vezeteknev);
    }

    public String getKeresztnev() {
        return keresztnevOBJ.get();
    }

    public void setKeresztnev(String keresztnev) {
        this.keresztnevOBJ.set(keresztnev);
    }

    public String getEmailcim() {
        return emailOBJ.get();
    }

    public void setEmailcim(String emailcim) {
        this.emailOBJ.set(emailcim);
    }

    public String getId() {
        return idOBJ.get();
    }

   

    
    public void setId(String id) {
        this.idOBJ.set(id);
    }
      

}
