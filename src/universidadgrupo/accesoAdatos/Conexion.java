package universidadgrupo.accesoAdatos;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Conexion {

    //Declaración de constantes de tipo String con información que utilizaremos para la conexión
    private static final String URL = "jdbc:mariadb://localhost/";
    private static final String DB = "universidadulp";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "";
    private static Connection connection;

    private Conexion() {
    } //la constante “connection” representará a un objeto de tipo Connection a través del cual podremos enviar nuestras peticiones a la Base de Datos.

    public static Connection getConexion() { //NO o va devolver un objeto conexion de la clase. Va a devolver un objeto tipo Connection

        if (connection == null) { //es la primeras vez que vamos a invocar este metodo ahi se carga los drivrr de conexion.

            try {
                Class.forName("org.mariadb.jdbc.Driver");
                //Setup the connection with the DB
                connection = DriverManager.
                        getConnection(URL + DB + "?useLegacyDatetimeCode=false&serverTimezone=UTC"
                            + "&user=" + USUARIO + "&password=" + PASSWORD);

                JOptionPane.showInternalMessageDialog(null, "Conectado");
            
            } catch (SQLException ex) {
                JOptionPane.showInternalMessageDialog(null, "Error al conectarse a la BD "+ex.getMessage());
            } catch (ClassNotFoundException ex) {
                JOptionPane.showInternalMessageDialog(null, "Error al cargar los Drivers"+ex.getMessage());
            }       

        }
        return connection;
    }
}
