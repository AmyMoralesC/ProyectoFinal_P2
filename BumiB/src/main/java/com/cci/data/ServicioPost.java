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
            String sql = "INSERT INTO mensajes (titulo, texto, fecha) VALUES (?, ?, ?)";
            stmt = getConexion().prepareStatement(sql);
            stmt.setString(1, post.getTitulo());
            stmt.setString(2, post.getTexto());
            stmt.setTimestamp(3, new java.sql.Timestamp(post.getFecha().getTime()));
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
            String sql = "SELECT idmensajes, titulo, texto, fecha FROM mensajes";
            stmt = getConexion().prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idmensajes");
                String titulo = rs.getString("titulo");
                String texto = rs.getString("texto");
                Date fecha = rs.getTimestamp("fecha");
                Post post = new Post();
                post.setId(id);
                post.setTitulo(titulo);
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
            String sql = "UPDATE mensajes SET titulo = ?, texto = ?, fecha = ? WHERE idmensajes = ?";
            stmt = getConexion().prepareStatement(sql);
            stmt.setString(1, post.getTitulo());
            stmt.setString(2, post.getTexto());
            stmt.setTimestamp(3, new java.sql.Timestamp(post.getFecha().getTime()));
            stmt.setInt(4, post.getId());
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
