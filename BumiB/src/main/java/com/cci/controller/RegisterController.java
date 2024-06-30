/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cci.controller;

import com.cci.data.ServicioUsuario;
import com.cci.model.Usuario;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author maule
 */
@ManagedBean(name = "registerController")
@SessionScoped
public class RegisterController {

    private Usuario usuario = new Usuario();
    private ServicioUsuario servicioUsuario = new ServicioUsuario();

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void registrarUsuario() {
        boolean isInserted = servicioUsuario.insertar(this.getUsuario());
        if (isInserted) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro exitoso", "El usuario ha sido registrado correctamente."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en el registro", "No se pudo registrar el usuario. Inténtalo de nuevo."));
        }
    }

    public void validatePassword(FacesContext context, UIComponent component, Object value) {
        String password = value.toString();

        // Validar que la contraseña tenga al menos una letra minúscula, una mayúscula, un número y un carácter especial
        if (!isValidPassword(password)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Contraseña no válida",
                    "La contraseña debe contener al menos una letra minúscula, una mayúscula, un número y un carácter especial.");
            throw new ValidatorException(msg);
        }
    }

    private boolean isValidPassword(String password) {
        // Implementar la lógica de validación aquí
        // Por ejemplo, usar expresiones regulares o métodos de comparación para verificar la complejidad de la contraseña
        // Ejemplo básico: al menos 8 caracteres, una letra minúscula, una mayúscula, un número y un carácter especial
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(regex);
    }

}
