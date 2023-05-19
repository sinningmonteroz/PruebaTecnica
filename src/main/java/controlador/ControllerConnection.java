
package controlador;

import java.sql.Connection;

public class ControllerConnection {
    public static Connection getConnectionController(){
        return modelo.ModelConnection.getConnection();
    }
}
