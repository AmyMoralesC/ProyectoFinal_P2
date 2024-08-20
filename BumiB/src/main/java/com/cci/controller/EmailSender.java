package com.cci.controller;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author maule
 */
public class EmailSender {

    public void enviarCorreo(String destinatario, String codigo) {
        // Configurar las propiedades del servidor SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        final String username = "mauriciolj128@gmail.com"; // correo electronico que se usara para enviar
        final String password = "phfh amgo gezt fapj"; // contraseña o clave generada para aplicacion

        // Crear una sesión de correo
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Crear el mensaje
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject("Código de Verificación");
            message.setText("Tu código de verificación es: " + codigo);

            // Enviar el mensaje
            Transport.send(message);
            System.out.println("Correo enviado exitosamente.");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Error al enviar el correo.");
        }
    }

    public void enviarCorreoConEnlace(String destinatario, String enlace) {
        // Configurar las propiedades del servidor SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        final String username = "mauriciolj128@gmail.com"; // correo electronico que se usara para enviar
        final String password = "phfh amgo gezt fapj"; // contraseña o clave generada para aplicacion
        
        // Crear una sesión de correo
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Crear el mensaje
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject("Restablecer contraseña");
            message.setText("Para restablecer tu contraseña, haz clic en el siguiente enlace: " + enlace);

            // Enviar el mensaje
            Transport.send(message);
            System.out.println("Correo de restablecimiento enviado exitosamente.");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Error al enviar el correo de restablecimiento.");
        }
    }

}
