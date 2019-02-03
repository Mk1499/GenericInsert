/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xodb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author MKhaled
 */
public class XODB {

    /**
     * @param args the command line arguments
     */
    Connection con ; 
    PreparedStatement pst ;
    ResultSet rs ; 
    Statement stmt ; 
    ResultSetMetaData rsmd ; 
    
     //Constructor 
    
    public XODB(){}
    
    //connect Function 
    public void connect (){
        try{
            Class.forName("com.mysql.jdbc.Driver") ; 
             con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/tictactoe","root","") ;         
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
   
    // Table Columns Names 
    String tableColsNames(String tableName){
       
        String colNames = "(" ; 
        String q2 = "select * from "+tableName ; 
       
        try{
            stmt = con.createStatement() ; 
            rs = stmt.executeQuery(q2);
            rsmd = rs.getMetaData();
            
            for (int i = 2; i <= rsmd.getColumnCount()-1; i++) {
                   colNames+=(rsmd.getColumnName(i)+",");  
            }
            colNames+=(rsmd.getColumnName(rsmd.getColumnCount())+")") ;  
            }
      catch(Exception ex){
          ex.printStackTrace();
            }
      
      return colNames ; 
    }
 
    
    //insert Function 
    public void insert( ArrayList<String> values , String table_name ){
       
        //get columns names
       String colNames = tableColsNames(table_name) ;
       
       // Calculate number of '?'
       String qParams = "(" ; 
        for (int i = 0; i < values.size()-1; i++) {
            qParams+="?," ; 
        }
        qParams+= "?)" ; 
         
        try { 
       // get table Columns names 
       
   //start insert 
       String query = "insert into " +table_name+colNames+" values"+qParams ;
         
        pst = con.prepareStatement(query) ; 
            for (int i = 0; i < values.size(); i++) {
               
                if ("VARCHAR".equals(rsmd.getColumnTypeName(i+2)))
                    pst.setString(i+1, values.get(i));
                else if ("INT".equals(rsmd.getColumnTypeName(i+2))){
                   pst.setInt(i+1, Integer.parseInt(values.get(i)) );
                }
            }
              pst.executeUpdate() ; }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        XODB obj = new XODB() ; 
        ArrayList<String> valArr = new ArrayList<>() ; 
        obj.connect();
       
        //valArr.add("5");
        valArr.add("Mohamed"); //name 
        valArr.add( "123456" ); // password
        valArr.add("online"); // online state 
        valArr.add("Mohamed@gmail.com") ; // email
        valArr.add("14"); // score 
        valArr.add("win"); // player state
        valArr.add("fb.com"); // facebook url 
        
         obj.insert(valArr, "player") ;
         
    }
    
}
