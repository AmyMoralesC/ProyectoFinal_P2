/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cci.data;

import com.cci.model.Post;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServicioPost extends Servicio {

    // Método para crear una nueva publicación
    public boolean crearPost(Post post) {
        boolean exito = false;
        PreparedStatement stmt = null;

        try {
            conectar();
            String sql = "INSERT INTO mensajes (titulo, creador, texto, fecha, notificacion) VALUES (?, ?, ?, ?, ?)";
            stmt = getConexion().prepareStatement(sql);
            stmt.setString(1, post.getTitulo());
            stmt.setString(2, post.getCreador());
            stmt.setString(3, post.getTexto());
            stmt.setTimestamp(4, new java.sql.Timestamp(post.getFecha().getTime()));
            stmt.setInt(5, post.getNotifi());
            int rows = stmt.executeUpdate();
            exito = (rows == 1);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            cerrarStatement(stmt);
            desconectar();
        }

        return exito;
    }

    // Método para buscar todas las publicaciones
public List<Post> buscarTodosLosPosts() {
    List<Post> listaPosts = new ArrayList<>();
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        conectar();
        // Modifica la consulta para ordenar por fecha en orden descendente
        String sql = "SELECT idmensajes, titulo, creador, texto, fecha FROM mensajes ORDER BY fecha DESC";
        stmt = getConexion().prepareStatement(sql);
        rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("idmensajes");
            String titulo = rs.getString("titulo");
            String creador = rs.getString("creador");
            String texto = rs.getString("texto");
            Date fecha = rs.getTimestamp("fecha");
            Post post = new Post();
            post.setId(id);
            post.setTitulo(titulo);
            post.setCreador(creador);
            post.setTexto(texto);
            post.setFecha(fecha);
            listaPosts.add(post);
        }
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
    } finally {
        cerrarResultSet(rs);
        cerrarStatement(stmt);
        desconectar();
    }

    return listaPosts;
}


    // Método para actualizar una publicación
    public boolean actualizarPost(Post post) {
        boolean exito = false;
        PreparedStatement stmt = null;

        try {
            conectar();
            String sql = "UPDATE mensajes SET titulo = ?, creador= ?, texto = ?, fecha = ? WHERE idmensajes = ?";
            stmt = getConexion().prepareStatement(sql);
            stmt.setString(1, post.getTitulo());
            stmt.setString(2, post.getCreador());
            stmt.setString(3, post.getTexto());
            stmt.setTimestamp(4, new java.sql.Timestamp(post.getFecha().getTime()));
            stmt.setInt(5, post.getId());
            int rows = stmt.executeUpdate();
            exito = (rows == 1);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            cerrarStatement(stmt);
            desconectar();
        }

        return exito;
    }

    // Método para eliminar una publicación
    public boolean eliminarPost(int id) {
        boolean exito = false;
        PreparedStatement stmt = null;

        try {
            conectar();
            String sql = "DELETE FROM mensajes WHERE idmensajes = ?";
            stmt = getConexion().prepareStatement(sql);
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            exito = (rows == 1);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            cerrarStatement(stmt);
            desconectar();
        }

        return exito;
    }
}
