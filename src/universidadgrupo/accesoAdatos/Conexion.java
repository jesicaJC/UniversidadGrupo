package universidadgrupo.accesoAdatos;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class Conexion {
     private static final String URL="jdbc:mariadb://localhost/";
     private static final String BD="universidadulp";
     private static final String USUARIO="root";
     private static final String CONTRASEÑA="";
     private static Connection connection;
     
     private Conexion(){}
     
     public static Connection getConexion(){ //NO o va devolver un objeto conexion de la clase. Va a devolver un objeto tipo Connection
         
        if(connection == null){ //es la primeras vez que vamos a invocar este metodo ahi se carga los drivrr de conexion.
            
            try {
            Class.forName("org.mariasdb.jdbc.Driver");
            connection = DriverManager.getConnection(URL+BD,USUARIO,CONTRASEÑA);
            
            JOptionPane.showInternalMessageDialog(null, "Conectado");
            
            } catch (ClassNotFoundException ex) {
               JOptionPane.showInternalMessageDialog(null, "Error al cargar Driver");
            } catch (SQLException ex) {
                JOptionPane.showInternalMessageDialog(null, "Error de conexion"); 
            }
        
        }
        return connection;
     }
}
