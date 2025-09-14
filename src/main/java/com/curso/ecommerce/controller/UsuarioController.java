package com.curso.ecommerce.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.IUsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@GetMapping("/registro")
	public String create() {
		return "usuario/registro";
	}
	
	@PostMapping("/save")
	public String save(Usuario usuario) {
		logger.info("Usuario : {}", usuario);
		usuario.setTipo("USER");
		usuarioService.save(usuario);
		
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login() {
		return "usuario/login";
	}
	
	@PostMapping("/acceder")
	public String acceder(Usuario usuario, HttpSession session) {
		logger.info("Acceso : {}", usuario);
		
		Optional<Usuario> user = usuarioService.findByEmail(usuario.getEmail());
		
		if (user.isPresent()) {
			logger.info("DB User: {}", user.get());
			session.setAttribute("idUsuario", user.get().getId());
			if (user.get().getTipo().equals("ADMIN")) {
				return "redirect:/administrador";
			}
		} else {
			logger.info("Usuario no existe");
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/compras")
	public String obtenerCompras(Model model, HttpSession session) {
		model.addAttribute("sesion", session.getAttribute("idUsuario"));
		return "usuario/compras";
	}
}
