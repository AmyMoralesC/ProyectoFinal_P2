/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cci.data;

import com.cci.model.Notificacion;
import com.cci.model.Post;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author jared
 */
@ManagedBean(name = "servicioNotificacion")
@ApplicationScoped
public class ServicioNotificacion extends Servicio {

    public boolean crearNotif(Notificacion notificacion) {
        boolean exito = false;
        PreparedStatement stmt = null;

        try {
            conectar();
            String sql = "INSERT INTO notificaciones (tipo, donde) VALUES (?, ?)";
            stmt = getConexion().prepareStatement(sql);
            stmt.setString(1, notificacion.getTipo());
            stmt.setString(2, notificacion.getDonde());
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

    public List<Notificacion> buscarTodasLasNotificaciones() {
        List<Notificacion> listaNotificacion = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conectar();
            String sql = "SELECT id, tipo, donde FROM notificaciones";
            stmt = getConexion().prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String tipo = rs.getString("tipo");
                String donde = rs.getString("donde");
                Notificacion notificacion = new Notificacion();
                notificacion.setId(id);
                notificacion.setTipo(tipo);
                notificacion.setDonde(donde);
                listaNotificacion.add(notificacion);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            cerrarResultSet(rs);
            cerrarStatement(stmt);
            desconectar();
        }

        return listaNotificacion;
    }

}
