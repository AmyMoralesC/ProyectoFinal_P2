/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cci.data;

import com.cci.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author maule
 */
public class ServicioUsuario extends Servicio {

    public void buscarTodosSinLista() {

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conectar();
            String sql = "SELECT idUsuario, carnet, nombre, correo, contraseña, facultad, carrera, sede, biografia, telefono, estado FROM usuario";
            stmt = getConexion().prepareStatement(sql);
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("idUsuario");
                String carnet = rs.getString("carnet");
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                String clave = rs.getString("contraseña");
                String facultad = rs.getString("facultad");
                String carrera = rs.getString("carrera");
                String sede = rs.getString("sede");
                String biografia = rs.getString("biografia");
                String telefono = rs.getString("telefono");
                String estado = rs.getString("estado");
                System.out.println("id: " + id + " carnet: " + carnet + " nombre: " + nombre + " correo: " + correo + "clave: "
                        + clave + "Facultad: " + facultad + "Carrera: " + carrera + "Sede: " + sede + "Biografia: "
                        + biografia + "Telefono: " + telefono + "Estado: " + estado);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarResultSet(rs);
            cerrarStatement(stmt);
            desconectar();
        }
    }

    public List<Usuario> buscarTodos() {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Usuario> listaRetorno = new ArrayList<Usuario>();

        try {
            conectar();
            String sql = "SELECT * FROM usuario";
            stmt = getConexion().prepareStatement(sql);
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("idUsuario");
                String carnet = rs.getString("carnet");
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                String clave = rs.getString("contraseña");
                String facultad = rs.getString("facultad");
                String carrera = rs.getString("carrera");
                String sede = rs.getString("sede");
                String biografia = rs.getString("biografia");
                String telefono = rs.getString("telefono");
                String estado = rs.getString("estado");
                Usuario objUsuario = new Usuario(id, carnet, nombre, correo, clave, facultad, carrera, sede, biografia, telefono, estado);
                listaRetorno.add(objUsuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarResultSet(rs);
            cerrarStatement(stmt);
            desconectar();
        }

        return listaRetorno;
    }

    public Usuario Validar(String correo, String contraseña) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Usuario usuario = null;

        try {
            conectar();
            String sql = "SELECT * FROM usuario WHERE correo = ? AND contraseña = ?";
            stmt = getConexion().prepareStatement(sql);
            stmt.setString(1, correo);
            stmt.setString(2, contraseña);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("idUsuario");
                String carnet = rs.getString("carnet");
                String nombre = rs.getString("nombre");
                String corre = rs.getString("correo");
                String clave = rs.getString("contraseña");
                String facultad = rs.getString("facultad");
                String carrera = rs.getString("carrera");
                String sede = rs.getString("sede");
                String biografia = rs.getString("biografia");
                String telefono = rs.getString("telefono");
                String estado = rs.getString("estado");
                usuario = new Usuario(id, carnet, nombre, corre, clave, facultad, carrera, sede, biografia, telefono, estado);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarResultSet(rs);
            cerrarStatement(stmt);
            desconectar();
        }

        return usuario;
    }

    public boolean insertar(Usuario usuario) {

        try {
            conectar();
            String sql = "INSERT INTO usuario (idUsuario, carnet, nombre, correo, contraseña, facultad, carrera, sede, biografia, telefono, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = getConexion().prepareStatement(sql);
            stmt.setInt(1, usuario.getId());
            stmt.setString(2, usuario.getCarnet());
            stmt.setString(3, usuario.getNombre());
            stmt.setString(4, usuario.getCorreo());
            stmt.setString(5, usuario.getClave());
            stmt.setString(6, usuario.getFacultad());
            stmt.setString(7, usuario.getCarrera());
            stmt.setString(8, usuario.getSede());
            stmt.setString(9, usuario.getBiografia());
            stmt.setString(10, usuario.getTelefono());
            stmt.setString(11, usuario.getEstado());
            int cantidad = stmt.executeUpdate();

            if (cantidad == 0) {
                throw new SQLException("No se logró insertar la persona");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            desconectar();
        }
        return true;
    }

    public boolean actualizar(Usuario usuario) {

        try {
            conectar();
            String sql = "UPDATE usuario SET carnet = ?, nombre = ?, correo = ?, contraseña = ?, facultad = ?,"
                    + " carrera = ?, sede = ?, biografia = ?, telefono = ?, estado = ? WHERE idUsuario = ?";
            PreparedStatement stmt = getConexion().prepareStatement(sql);
            stmt.setString(1, usuario.getCarnet());
            stmt.setString(2, usuario.getNombre());
            stmt.setString(3, usuario.getCorreo());
            stmt.setString(4, usuario.getClave());
            stmt.setString(5, usuario.getFacultad());
            stmt.setString(6, usuario.getCarrera());
            stmt.setString(7, usuario.getSede());
            stmt.setString(8, usuario.getBiografia());
            stmt.setString(9, usuario.getTelefono());
            stmt.setString(10, usuario.getEstado());
            stmt.setInt(11, usuario.getId());
            int cantidad = stmt.executeUpdate();

            if (cantidad == 0) {
                throw new SQLException("No se logró actualizar el usuario " + usuario.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            desconectar();
        }
        return true;
    }

    public boolean eliminar(int id) {

        try {
            conectar();
            String sql = "DELETE FROM usuario WHERE idUsuario = ?";
            PreparedStatement stmt = getConexion().prepareStatement(sql);
            stmt.setInt(1, id);
            int cantidad = stmt.executeUpdate();

            if (cantidad == 0) {
                throw new SQLException("No se logró eliminar el usuario con ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            desconectar();
        }
        return true;

    }

    public boolean existeCorreo(String correo) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE correo = ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean existe = false;

        try {
            conectar(); // Asegúrate de que la conexión está establecida
            Connection conn = getConexion();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, correo);
            rs = stmt.executeQuery();

            if (rs.next()) {
                existe = rs.getInt(1) > 0;
}
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            cerrarResultSet(rs);
            cerrarStatement(stmt);
            desconectar();
        }

        return existe;
    }

}
