/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universidadgrupo.accesoAdatos;

/**
 *
 * @author Manuel
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
        String sql = "INSERT INTO materia (nombre, codigo) VALUES (?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, materia.getNombre());
            ps.setString(2, materia.getCodigo()); // Get codigo es para pedir el "idMAteria" en la base de datos
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            // Manejo de excepciones en caso de error.
            ex.printStackTrace();
        }
    }

    public Materia buscarMateria(int id) {
        Materia materia = null;
        String sql = "SELECT nombre, codigo FROM materia WHERE idMateria = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                materia = new Materia();
                materia.setIdMateria(id);
                materia.setNombre(rs.getString("nombre"));
                materia.setCodigo(rs.getString("codigo"));
            }
            ps.close();
        } catch (SQLException ex) {
            // Manejo de excepciones en caso de error.
            ex.printStackTrace();
        }
        return materia;
    }

    public void modificarMateria(Materia materia) {
        String sql = "UPDATE materia SET nombre = ?, codigo = ? WHERE idMateria = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, materia.getNombre());
            ps.setString(2, materia.getCodigo());
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
        String sql = "SELECT idMateria, nombre, codigo FROM materia";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Materia materia = new Materia();
                materia.setIdMateria(rs.getInt("idMateria"));
                materia.setNombre(rs.getString("nombre"));
                materia.setCodigo(rs.getString("codigo"));
                materias.add(materia);
            }
            ps.close();
        } catch (SQLException ex) {
            // Manejo de excepciones en caso de error.
            ex.printStackTrace();
        }
        return materias;
    }
}
