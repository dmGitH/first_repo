/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */





public class User {
    
    static String name;
    static String adress;
    static String email;
    static String telefon_;
    
    User(){
    
    }
 /** A User objektum user példányának a nevét és a címét, email és telefon változót  állítom be*/
    User(String nev, String cim,String mail, String telefon ) {
        name=nev;
        adress=cim;
        email=mail;
        telefon_=telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String mail) {
        User.email = mail;
    }

    public String getTelefon() {
        return telefon_;
    }

    public void setTelefon(String telefon) {
        User.telefon_ = telefon;
    }

    public String getName() {
        return name;
    }

    public void setName(String nev) {
        this.name = nev;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String cim) {
        this.adress = cim;
    }
    
    
}
