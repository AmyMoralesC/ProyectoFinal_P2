package com.cci.data;

import com.cci.model.MensajePrivado;
import com.cci.model.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicioMensajePrivado extends Servicio {

    public void enviarMensaje(MensajePrivado mensaje) throws SQLException, ClassNotFoundException {
        PreparedStatement stmt = null;

        try {
            conectar();
            String sql = "INSERT INTO mensajes_privados (remitente_id, destinatario_id, contenido, fecha_envio) VALUES (?, ?, ?, NOW())";
            stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, mensaje.getRemitenteId());
            stmt.setInt(2, mensaje.getDestinatarioId());
            stmt.setString(3, mensaje.getContenido());
            stmt.executeUpdate();
        } finally {
            cerrarStatement(stmt);
            desconectar();
        }
    }

    public List<MensajePrivado> obtenerMensajes(int usuarioId) throws SQLException, ClassNotFoundException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<MensajePrivado> mensajes = new ArrayList<>();

        try {
            conectar();
            String sql = "SELECT * FROM mensajes_privados WHERE destinatario_id = ?";
            stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, usuarioId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                MensajePrivado mensaje = new MensajePrivado();
                mensaje.setId(rs.getInt("id"));
                mensaje.setRemitenteId(rs.getInt("remitente_id"));
                mensaje.setDestinatarioId(rs.getInt("destinatario_id"));
                mensaje.setContenido(rs.getString("contenido"));
                mensaje.setFechaEnvio(rs.getTimestamp("fecha_envio"));
                mensaje.setLeido(rs.getBoolean("leido"));
                mensajes.add(mensaje);
            }
        } finally {
            cerrarResultSet(rs);
            cerrarStatement(stmt);
            desconectar();
        }
        return mensajes;
    }

    public List<MensajePrivado> obtenerHistorialMensajes(int remitenteId, int destinatarioId) throws SQLException, ClassNotFoundException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<MensajePrivado> mensajes = new ArrayList<>();

        try {
            conectar();
            String sql = "SELECT * FROM mensajes_privados WHERE (remitente_id = ? AND destinatario_id = ?) OR (remitente_id = ? AND destinatario_id = ?) ORDER BY fecha_envio ASC";
            stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, remitenteId);
            stmt.setInt(2, destinatarioId);
            stmt.setInt(3, destinatarioId);
            stmt.setInt(4, remitenteId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                MensajePrivado mensaje = new MensajePrivado();
                mensaje.setRemitenteId(rs.getInt("remitente_id"));
                mensaje.setDestinatarioId(rs.getInt("destinatario_id"));
                mensaje.setContenido(rs.getString("contenido"));
                mensaje.setFechaEnvio(rs.getTimestamp("fecha_envio"));
                mensajes.add(mensaje);
            }
        } finally {
            cerrarResultSet(rs);
            cerrarStatement(stmt);
            desconectar();
        }
        return mensajes;
    }
}
