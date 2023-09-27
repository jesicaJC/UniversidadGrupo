
package Principal;

import java.sql.Connection;
import universidadgrupo.accesoAdatos.AlumnoData;
import universidadgrupo.accesoAdatos.Conexion;
import universidadgrupo.accesoAdatos.InscripcionData;
import universidadgrupo.accesoAdatos.MateriaData;
import universidadgrupo.entidades.Alumno;
import universidadgrupo.entidades.Inscripcion;
import universidadgrupo.entidades.Materia;
import universidadgrupo.vistas.MenuPrincipal;


public class Main {
    public static void main(String[] args) {
       Connection con= Conexion.getConexion();
       MenuPrincipal mp = new MenuPrincipal();
         mp.setVisible(true);
         mp.setLocationRelativeTo(null);
 
        
        
 } 
}
   

