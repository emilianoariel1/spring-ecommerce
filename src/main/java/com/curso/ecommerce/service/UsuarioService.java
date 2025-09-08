package com.curso.ecommerce.service;

import java.util.Optional;

import com.curso.ecommerce.model.Usuario;

public interface UsuarioService {
	
	Optional<Usuario> findById(Integer id);
}
