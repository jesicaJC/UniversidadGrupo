
package universidadgrupo.accesoAdatos;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import universidadgrupo.entidades.Materia;

public class MateriaData {

    //Atributo:
    private Connection con;

    //Constructor
    public MateriaData() {
        con = Conexion.getConexion();
    }

    //Métodos:
    
         public void guardarMateria(Materia materia) {
        String sql = "INSERT INTO materia (nombre, año,estado) VALUES (?, ?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, materia.getNombre());
            ps.setInt(2, materia.getAnioMateria()); // Get codigo es para pedir el "idMateria" en la base de datos
            ps.setBoolean(3, materia.isEstado());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();//Obtiene la clave,recibimos un resultset consulta
            if (rs.next()) {
                materia.setIdMateria(rs.getInt("idMateria"));
                JOptionPane.showMessageDialog(null, "Materia añadido con éxito.");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al ingresar a Materia "+ ex.getMessage());
        }
    }

     public Materia buscarMateria(int id) {
        Materia materia = null;
        String sql = "SELECT nombre, año FROM materia WHERE idMateria = ?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                materia = new Materia();
                materia.setIdMateria(id);
                materia.setNombre(rs.getString("nombre"));
                materia.setAnioMateria(rs.getInt("Año"));
//                materia.setIdMateria(rs.getInt("codigo"));
            }else{
             JOptionPane.showMessageDialog(null, "No existe la Materia");
        }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a Materias"+ ex.getMessage());
            // Manejo de excepciones en caso de error.
//            ex.printStackTrace();
        }
        return materia;
    }

    public void modificarMateria(Materia materia) {
        String sql = "UPDATE materia SET nombre = ?, codigo = ? WHERE idMateria = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, materia.getNombre());
            ps.setInt(2, materia.getAnioMateria());
            ps.setInt(3, materia.getIdMateria());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            // Manejo de excepciones en caso de error.
            ex.printStackTrace();
        }
    }

    public void eliminarMateria(int id) {
        String sql = "DELETE FROM materia WHERE idMateria = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            // Manejo de excepciones en caso de error.
            ex.printStackTrace();
        }
    }

    public List<Materia> listarMaterias() {
        List<Materia> materias = new ArrayList<>();
        String sql = "SELECT idMateria, nombre, año FROM materia";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Materia materia = new Materia();
                materia.setIdMateria(rs.getInt("idMateria"));
                materia.setNombre(rs.getString("nombre"));
                materia.setAnioMateria(rs.getInt("año"));
                materias.add(materia);
            }
            ps.close();
        } catch (SQLException ex) {
            // Manejo de excepciones en caso de error.
           JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Materias: " + ex.getMessage());
        }
        return materias;
    }
}