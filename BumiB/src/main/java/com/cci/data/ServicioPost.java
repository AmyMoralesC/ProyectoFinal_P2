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

   
    public boolean crearPost(Post post) { // Método para crear una nueva publicación
        boolean exito = false;
        PreparedStatement stmt = null;

        try {
            conectar();
            String sql = "INSERT INTO mensajes (titulo, creador, texto, fecha, notificacion, idCreador, likes_count) VALUES (?, ?, ?, ?, ?, ?, ?)";
            stmt = getConexion().prepareStatement(sql);
            stmt.setString(1, post.getTitulo());
            stmt.setString(2, post.getCreador());
            stmt.setString(3, post.getTexto());
            stmt.setTimestamp(4, new java.sql.Timestamp(post.getFecha().getTime()));
            stmt.setInt(5, post.getNotifi());
            stmt.setInt(6, post.getCreadorId());
            stmt.setInt(7, 0); // Inicializa el conteo de likes en 0
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
            String sql = "SELECT idmensajes, titulo, creador, texto, fecha, idCreador, likes_count FROM mensajes ORDER BY fecha DESC";
            stmt = getConexion().prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idmensajes");
                String titulo = rs.getString("titulo");
                String creador = rs.getString("creador");
                String texto = rs.getString("texto");
                Date fecha = rs.getTimestamp("fecha");
                int creadorId = rs.getInt("idCreador");
                int likesCount = rs.getInt("likes_count"); // Recupera el conteo de likes
                Post post = new Post();
                post.setId(id);
                post.setTitulo(titulo);
                post.setCreador(creador);
                post.setTexto(texto);
                post.setFecha(fecha);
                post.setCreadorId(creadorId);
                post.setLikesCount(likesCount); // Establece el conteo de likes
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
            String sql = "UPDATE mensajes SET titulo = ?, creador= ?, texto = ?, fecha = ?, idCreador = ?, likes_count = ? WHERE idmensajes = ?";
            stmt = getConexion().prepareStatement(sql);
            stmt.setString(1, post.getTitulo());
            stmt.setString(2, post.getCreador());
            stmt.setString(3, post.getTexto());
            stmt.setTimestamp(4, new java.sql.Timestamp(post.getFecha().getTime()));
            stmt.setInt(5, post.getCreadorId());
            stmt.setInt(6, post.getLikesCount()); // Actualiza el conteo de likes
            stmt.setInt(7, post.getId());
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

    // Método para buscar posts por usuario
    public List<Post> buscarPostsPorUsuario(int idCreador) {
        List<Post> listaPosts = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conectar();
            String sql = "SELECT idmensajes, titulo, creador, texto, fecha, idCreador, likes_count FROM mensajes WHERE idCreador = ? ORDER BY fecha DESC";
            stmt = getConexion().prepareStatement(sql);
            stmt.setInt(1, idCreador);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idmensajes");
                String titulo = rs.getString("titulo");
                String creador = rs.getString("creador");
                String texto = rs.getString("texto");
                Date fecha = rs.getTimestamp("fecha");
                int creadorId = rs.getInt("idCreador");
                int likesCount = rs.getInt("likes_count"); // Recupera el conteo de likes
                Post post = new Post();
                post.setId(id);
                post.setTitulo(titulo);
                post.setCreador(creador);
                post.setTexto(texto);
                post.setFecha(fecha);
                post.setCreadorId(creadorId);
                post.setLikesCount(likesCount); // Establece el conteo de likes
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

    // Método para actualizar el conteo de likes de una publicación
    public boolean actualizarLikes(int postId, int likesCount) {
        boolean exito = false;
        PreparedStatement stmt = null;

        try {
            conectar();
            String sql = "UPDATE mensajes SET likes_count = ? WHERE idmensajes = ?";
            stmt = getConexion().prepareStatement(sql);
            stmt.setInt(1, likesCount);
            stmt.setInt(2, postId);
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

    // Método para verificar si el usuario ya dio like a un post
    public boolean usuarioYaDioLike(int postId, int userId) {
        boolean yaDioLike = false;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conectar();
            // Implementa la lógica para verificar en la base de datos si el usuario ya dio like al post
            // Puedes tener una tabla intermedia para almacenar los likes de los usuarios
            String sql = "SELECT COUNT(*) FROM likes WHERE post_id = ? AND user_id = ?";
            stmt = getConexion().prepareStatement(sql);
            stmt.setInt(1, postId);
            stmt.setInt(2, userId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                yaDioLike = (count > 0);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            cerrarResultSet(rs);
            cerrarStatement(stmt);
            desconectar();
        }

        return yaDioLike;
    }

    // Método para agregar un like a un post
    public boolean agregarLike(int postId, int userId) {
        boolean exito = false;
        PreparedStatement stmt = null;

        try {
            conectar();
            String sql = "INSERT INTO likes (post_id, user_id) VALUES (?, ?)";
            stmt = getConexion().prepareStatement(sql);
            stmt.setInt(1, postId);
            stmt.setInt(2, userId);
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