/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cci.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author maule
 */
@FacesValidator("emailValidator")
public class EmailValidator implements Validator<String> {

    private static final String ULATINA_EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@ulatina\\.net$";

    @Override
    public void validate(FacesContext context, UIComponent component, String value) throws ValidatorException {
        if (value == null || value.isEmpty()) {
            return; // Let required="true" handle empty case.
        }

        if (!value.matches(ULATINA_EMAIL_PATTERN)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Correo no válido", "El correo debe ser de dominio @ulatina.net");
            throw new ValidatorException(msg);
        }
    }
}