
package modelo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModelConnection {
    
    public static Connection getConnection(){
    Connection con;
        try {
            String url = "jdbc:sqlserver://localhost:1433;"
                    + "database=prueba_tecnica;"
                    + "user = sa;"
                    + "password = A1b2c3123;integratedSecurity=true;encrypt=false;";
            con = DriverManager.getConnection(url);
            return con;
        } catch (SQLException ex) {
            Logger.getLogger(ModelConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
