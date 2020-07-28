package com.bs.bluefood.bs_bluefood.infrastructure.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bs.bluefood.bs_bluefood.domain.cliente.ClienteRepository;
import com.bs.bluefood.bs_bluefood.domain.restaurante.RestauranteRepository;
import com.bs.bluefood.bs_bluefood.domain.usuario.Usuario;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = clienteRepository.findByEmail(username);

        if(usuario == null){
            usuario = restauranteRepository.findByEmail(username);

            if(usuario == null){
                throw new UsernameNotFoundException(username);
            }
        }

        return new LoggedUser(usuario);
    }
    
}