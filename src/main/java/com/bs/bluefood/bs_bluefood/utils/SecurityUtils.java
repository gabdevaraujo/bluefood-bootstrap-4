package com.bs.bluefood.bs_bluefood.utils;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bs.bluefood.bs_bluefood.domain.cliente.Cliente;
import com.bs.bluefood.bs_bluefood.domain.restaurante.Restaurante;
import com.bs.bluefood.bs_bluefood.infrastructure.web.security.LoggedUser;

public class SecurityUtils {

    //Retorna um usuário logado
    public static LoggedUser loggedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication instanceof AnonymousAuthenticationToken){
            return null;
        }

        return (LoggedUser) authentication.getPrincipal();
    }
    
    
    public static Cliente loggedCliente(){
        LoggedUser loggedUser = loggedUser();

        if(loggedUser == null){
            throw new IllegalStateException("Não existe um usuário logado");
        }

        if(!(loggedUser.getUsuario() instanceof Cliente)){
            throw new IllegalStateException("Usuário logado não é um cliente");
        }

        return (Cliente) loggedUser.getUsuario();
    }

    public static Restaurante loggedRestaurante(){
        LoggedUser loggedUser = loggedUser();

        if(loggedUser == null){
            throw new IllegalStateException("Não existe um usuário logado");
        }

        if(!(loggedUser.getUsuario() instanceof Restaurante)){
            throw new IllegalStateException("Usuário logado não é um Restaurante");
        }

        return (Restaurante) loggedUser.getUsuario();
    }
}