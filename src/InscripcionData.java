/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Manuel
 */

import java.sql.Connection;
import java.util.List;
import universidadgrupo.AccesoADatos.AlumnoData;
import universidadgrupo.accesoAdatos.Conexion;
import universidadgrupo.accesoAdatos.MateriaData;
import universidadgrupo.entidades.Alumno;
import universidadgrupo.entidades.Inscripcion;
import universidadgrupo.entidades.Materia;

public class InscripcionData {

    //Atributos:
    private Connection con;
    private MateriaData matData;
    private AlumnoData aluData;

    //Cosntructor:
    public InscripcionData() {
        con = Conexion.getConexion();
        matData = new MateriaData();
        aluData = new AlumnoData();
    }

    //Todos los Metodos:
    
    public void guardarInscripcion(Inscripcion insc) {
        // Implementa la lógica para guardar una inscripción en la base de datos.
    }

    public List<Inscripcion> obtenerInscripciones() {
        // Implementa la lógica para obtener todas las inscripciones.
        return null;
    }

    public List<Inscripcion> obtenerInscripcionesPorAlumno(int id) {
        // Implementa la lógica para obtener inscripciones por alumno.
        return null;
    }

    public List<Materia> obtenerMateriasCursadas(int id) {
        // Implementa la lógica para obtener materias cursadas por un alumno.
        return null;
    }

    public List<Materia> obtenerMateriasNOCursadas(int id) {
        // Implementa la lógica para obtener materias no cursadas por un alumno.
        return null;
    }

    public void borrarInscripcionMateriaAlumno(int idAlumno, int idMateria) {
        // Implementa la lógica para borrar una inscripción de materia por alumno.
    }

    public void actualizarNota(int idAlumno, int idMateria, double nota) {
        // Implementa la lógica para actualizar la nota de un alumno en una materia.
    }

    public List<Alumno> obtenerAlumnosXMateria(int idMateria) {
        // Implementa la lógica para obtener alumnos por materia.
        return null;
    }
}
