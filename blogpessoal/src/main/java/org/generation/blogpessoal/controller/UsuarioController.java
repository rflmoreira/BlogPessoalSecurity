package org.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.generation.blogpessoal.model.usuario;
import org.generation.blogpessoal.model.usuarioLogin;
import org.generation.blogpessoal.repository.usuarioRepository;
import org.generation.blogpessoal.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins="*")

public class UsuarioController {

	@Autowired
	private usuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/all")
	public ResponseEntity<List<usuario>> getAll(){
		
		return ResponseEntity.ok(usuarioRepository.findAll());
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<usuario> cadastraUsuario(@RequestBody usuario usuario){

		return usuarioService.cadastrarUsuario(usuario)
				.map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(resposta))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}
		
	@PostMapping("/logar")
	public ResponseEntity<usuarioLogin> logaUsuario(@RequestBody Optional<usuarioLogin> user){
		return usuarioService.autenticarUsuario(user)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	
}
