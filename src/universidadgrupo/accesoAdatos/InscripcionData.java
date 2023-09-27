
package universidadgrupo.accesoAdatos;

/**
 *
 * @author Manuel
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import universidadgrupo.accesoAdatos.*;
import universidadgrupo.entidades.Alumno;
import universidadgrupo.entidades.Inscripcion;
import universidadgrupo.entidades.Materia;

public class InscripcionData {

    //Atributos:
     private Connection con = null;
    private MateriaData matData = new MateriaData();
    private AlumnoData aluData= new AlumnoData();
    
    //Cosntructor:
    public InscripcionData() {
        con = Conexion.getConexion();
//        matData = new MateriaData();
//        aluData = new AlumnoData();
    }

    //Todos los Metodos: 
   public void guardarInscripcion(Inscripcion insc) {
        // Implementa la lógica para guardar una inscripción en la base de datos.
         String sql = "INSERT INTO inscripcion (idAlumno, idMateria, nota) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, insc.getAlumno().getIdAlumno());
            ps.setInt(2, insc.getMateria().getIdMateria());
            ps.setDouble(3, insc.getNota());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();//Obtiene la clave,recibimos un resultset consulta
//           
            if(rs.next()){
            insc.setIdInscripto(rs.getInt(1));
             JOptionPane.showMessageDialog(null, "Inscripción REGISTRADA " );
            }
             ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar inscripción: " + ex.getMessage());
        }
    }


    public List<Inscripcion> obtenerInscripciones() {
        // Implementa la lógica para obtener todas las inscripciones.
        List<Inscripcion> inscripciones = new ArrayList<Inscripcion>();
        try {
            String sql = "SELECT * FROM inscripcion";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idInscripcion = rs.getInt("idInscripcion");
                int idAlumno = rs.getInt("idAlumno");
                int idMateria = rs.getInt("idMateria");
                double nota = rs.getDouble("nota");

                Alumno alumno = aluData.buscarAlumno(idAlumno);
                Materia materia = matData.buscarMateria(idMateria);

                Inscripcion insc = new Inscripcion(idInscripcion, alumno, materia, nota);
                inscripciones.add(insc);
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener inscripciones: " + ex.getMessage());
        }
        return inscripciones;
    }

    public List<Inscripcion> obtenerInscripcionesPorAlumno(int id) {
        // Implementa la lógica para obtener inscripciones por alumno.
        List<Inscripcion> inscripciones = new ArrayList<Inscripcion>();
        try {
            String sql = "SELECT * FROM inscripcion WHERE idAlumno = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idInscripcion = rs.getInt("idInscripcion");
                int idMateria = rs.getInt("idMateria");
                double nota = rs.getDouble("nota");

                Alumno alumno = aluData.buscarAlumno(id);
                Materia materia = matData.buscarMateria(idMateria);

                Inscripcion insc = new Inscripcion(idInscripcion, alumno, materia, nota);
                inscripciones.add(insc);
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener inscripciones por alumno: " + ex.getMessage());
        }
        return inscripciones;
    }

     public List<Materia> obtenerMateriasCursadas(int id) {

       List<Materia> materias = new ArrayList<Materia>();
        try {
            String sql = "SELECT inscripcion.idMateria, nombre, año FROM inscripcion,"
                    + " materia WHERE inscripcion.idMateria = materia.idMateria\n"
                    + "AND inscripcion.idAlumno = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);//seteo el signo de pregunats
            ResultSet rs = ps.executeQuery();
            while (rs.next()) { //mientras result set tenga elemento rrecorre el SQL
                // Obtenemos el ID de la materia cursada:
//                int idMateriaCursada = rs.getInt("idMateria");

                // Agrega esta materia a la lista de materias cursadas
                Materia materia = new Materia();
                materia.setIdMateria(rs.getInt("idMateria"));
                materia.setNombre(rs.getString("nombre"));
                materia.setAnioMateria(rs.getInt("año"));

                // Agregamos la materia a la lista de materias cursadas (opcional)
                materias.add(materia);
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener materias no cursadas: " + ex.getMessage());
        }
        return materias;
    }
    

    public List<Materia> obtenerMateriasNOCursadas(int id) {
       
        List<Materia> materiasNoCursadas = new ArrayList<Materia>();
        String sql = "SELECT  * FROM materia WHERE estado = 1 AND idMateria not in"
                    + "(SELECT idMateria FROM inscripcion WHERE idAlumno= ?)";//Obtenemos idMateria de todas la materias donde esta inscripto un alumno
        try {
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
//            Materia materia;
            while (rs.next()) {
                
                Materia  materia= new Materia();
                materia.setIdMateria(rs.getInt("idMateria"));
                materia.setNombre(rs.getString("nombre"));
                materia.setAnioMateria(rs.getInt("año"));
                //Una vez que recupere de la materia(id,nombre,año) se lo agregamps al List
                materiasNoCursadas.add(materia);
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener Inscripciones." + ex.getMessage());
        }
        return materiasNoCursadas;
    }


    public void borrarInscripcionMateriaAlumno(int idAlumno, int idMateria) {
        // Implementa la lógica para borrar una inscripción de materia por alumno.
        try {
            String sql = "DELETE FROM inscripcion WHERE idAlumno = ? AND idMateria = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idAlumno);
            ps.setInt(2, idMateria);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "¡Inscripción anulada!" );
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al borrar inscripción de materia por alumno: " + ex.getMessage());
        }
    }
    public void actualizarNota(int idAlumno, int idMateria, double nota) {
        // Implementa la lógica para actualizar la nota de un alumno en una materia.
        try {
            String sql = "UPDATE inscripcion SET nota = ? WHERE idAlumno = ? AND idMateria = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, nota);
            ps.setInt(2, idAlumno);
            ps.setInt(3, idMateria);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la nota del alumno en la materia: " + ex.getMessage());
        }
    }

    public List<Alumno> obtenerAlumnosXMateria(int idMateria) {
        // Implementa la lógica para obtener alumnos por materia.
        List<Alumno> alumnos = new ArrayList<Alumno>();
        try {
            String sql = "SELECT alumno.* FROM inscripcion "
                    + "JOIN alumno ON inscripcion.idAlumno = alumno.idAlumno "
                    + "WHERE inscripcion.idMateria = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idMateria);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Alumno alumno = new Alumno(
                        rs.getInt("idAlumno"),
                        rs.getInt("dni"),
                        rs.getString("apellido"),
                        rs.getString("nombre"),
                        rs.getDate("fechaNacimiento").toLocalDate(),
                        rs.getBoolean("estado")
                );
                alumnos.add(alumno);
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener alumnos por materia: " + ex.getMessage());
        }
        return alumnos;
    }
}
