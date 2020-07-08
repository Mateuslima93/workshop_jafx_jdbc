
package workshop.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexao {
   private static final String DRIVER = "com.mysql.jdbc.Driver"; 
   private static final String URL = "jdbc:mysql://localhost:3306/coursejdbc?useSSL=false";
   private static final String USER = "root";
   private static final String PASS = "27m12l93r";
   
   private static Connection conn = null;
   
   public static Connection getConnection(){
       if (conn == null) {
           try{
               Class.forName(DRIVER);
               conn = DriverManager.getConnection(URL, USER, PASS);
           }
           catch(SQLException ex){
               throw new DbException("Erro ao abrir conexão " + ex);
           } catch (ClassNotFoundException ex) {
               Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
       return conn;
   }
   public static void closeConnection(){
       if (conn != null) {
           try{
               conn.close();
           }
           catch(SQLException ex){
               throw new DbException("Erro ao fechar conexão " + ex.getMessage());
           }
       }
   }
   public static void closeStatement(Statement st){
       if(st != null){
           try{
               st.close();
           }
           catch(SQLException ex){
               throw new DbException("Erro ao fechar padrão de comando" + ex.getMessage());
           }
       }
   }
   public static void closeResultSet(ResultSet rs){
       if (rs != null) {
           try{
               rs.close();
           }
           catch(SQLException ex){
               throw new DbException("Erro ao fechar ponteiro " + ex.getMessage());
           }
       }
   }
}
