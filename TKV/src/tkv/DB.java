

package tkv;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DB {

    final String URL="jdbc:derby:peldaDB;create=true";
    final String USERNAME="";
    final String PASSWORD="";
    Connection  conn=null;
    Statement createStatement=null;  
    DatabaseMetaData md=null;
    ResultSet rs1=null;
     
    public DB (){ 
       
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("A híd létrejött");
        } catch (SQLException ex) {
            System.out.println("A híd nem jött létre");
            System.out.println(""+ex);
        }
         
        if(conn!=null){
        
         
            try {
                createStatement=conn.createStatement();
            } catch (SQLException ex) {
               System.out.println("Hiba: createstatement");
               System.out.println(""+ex);
            }
        }
        
        //meg nézni üres e az adatbázis
        
       
        
        try {
             md=conn.getMetaData();
        } catch (SQLException ex) {
            System.out.println("Hiba: getmetadata");
            System.out.println(""+ex);
            
        }
        
        
        try {
            rs1=md.getTables(null, "APP", "CIMLISTA", null);
            if(!rs1.next()){
                createStatement.execute("create table cimlista(id INT not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), vezeteknev varchar(20), keresztnev varchar(30),email varchar(50))");
            }
        } catch (SQLException ex) {
            System.out.println("Hiba:tábla ellenörzés, létrehozás");
            System.out.println(""+ex);
        }
        
    }
  
    /**A táblához adja a user adatai nevet és címet
     * @param vezeteknev
     * @param keresztnev
     * @param email */
    public void addUser(String vezeteknev,String keresztnev,String email){
      
        try {
//            String utasitas="insert into users values ('"+name+"','"+adress+"')";
//            createStatement.execute(utasitas);
            String sql="insert into cimlista values(?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, vezeteknev);
            preparedStatement.setString(2, keresztnev);
            preparedStatement.setString(3, email);
            preparedStatement.execute();    
        } catch (SQLException ex) {
            System.out.println("Hiba:adatbeviteli");
            System.out.println(""+ex);
        }
    }
    
    /**Minden user adatainak a listázása*/
    public void showAllUser(){
        String sql="select * from cimlista";
        ResultSet rs=null;
        try {
            rs=createStatement.executeQuery(sql);
            int i=1;
            while(rs.next()){
            
                String vezetekneve=rs.getString("vezeteknev");
                String keresztneve=rs.getString("keresztnev");
                String emilje=rs.getString("email");
                System.out.println(vezetekneve+","+keresztneve+"   "+emilje+" - "+i);
                i++;
            }
        } catch (SQLException ex) {
           System.out.println("Hiba:Lekérdezési all user");
           System.out.println(""+ex);

        }
        
        
    
    }
    
    /**A tábla metaadatainak a kiolvasása, oszlopok nevei*/
    public void showAllMetaData(){
        String sql="select * from cimlista";
        ResultSet rs=null;
        ResultSetMetaData rsmd=null;
        
        try {
            rs=createStatement.executeQuery(sql);
           rsmd=rs.getMetaData();
            int columCount=rsmd.getColumnCount();
            for(int i=1;i<=columCount;i++){
                System.out.println(rsmd.getColumnName(i)+"  "+ i);
            }
            
        } catch (SQLException ex) {
           System.out.println("Hiba:meteadata Lekérdezési ");
           System.out.println(""+ex);

        }
    
    }
    
    /**Teljes user lista betöltése ArrayList-ba*/
    public ArrayList<Ember> getAllUsers(){
        String sql="select * from cimlista";
        ArrayList<Ember> users=null;
        ResultSet rs=null;
        try {
            rs=createStatement.executeQuery(sql);
            users=new ArrayList<>();
            while(rs.next()){
            
                Ember actualEmber = new Ember(rs.getInt("id"),rs.getString("vezeteknev"),rs.getString("keresztnev"),rs.getString("email"));
                users.add(actualEmber);
                
            }
        } catch (SQLException ex) {
           System.out.println("Hiba:Lekérdezési all user ArrayList-ba");
           System.out.println(""+ex);

        }
     return users;
    }

      /**Adatbázisba töltés User objektumbó
     * @param user*/
    public void addUser(Ember user){
         String sql="insert into cimlista (vezeteknev,keresztnev,email) values(?,?,?)";
        try {
           
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user.getVezeteknev());
            preparedStatement.setString(2, user.getKeresztnev());
            preparedStatement.setString(3, user.getEmailcim());
            preparedStatement.execute();    
        } catch (SQLException ex) {
            System.out.println("Hiba:adatbeviteli");
            System.out.println(""+ex);
        }
    
    }
     public void Update(Ember user){
         String sql="update cimlista set vezeteknev=?, keresztnev=?,email=? where id =?";
        try {
           
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user.getVezeteknev());
            preparedStatement.setString(2, user.getKeresztnev());
            preparedStatement.setString(3, user.getEmailcim());
            preparedStatement.setInt(4, Integer.parseInt(user.getId())); //az elsődleges kulcsra int ként kell hivatkozni 
            preparedStatement.execute();    
        } catch (SQLException ex) {
            System.out.println("Hiba:adatbeviteli");
            System.out.println(""+ex);
        }
    
    }
     
    public void removeKontakt(Ember user){
         String sql="delete from cimlista where id =?";
        try {
           
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            
            preparedStatement.setInt(1, Integer.parseInt(user.getId())); //az elsődleges kulcsra int ként kell hivatkozni 
            preparedStatement.execute();    
        } catch (SQLException ex) {
            System.out.println("Hiba:Tölési");
            System.out.println(""+ex);
        }
    
    } 
    
    
}
