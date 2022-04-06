package org.generation.blogpessoal.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.generation.blogpessoal.model.usuario;
import org.generation.blogpessoal.model.usuarioLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class UsuarioService {
	
	@Autowired
	private org.generation.blogpessoal.repository.usuarioRepository usuarioRepository;

	public Optional<usuario> cadastrarUsuario(usuario usuario){
		if (usuarioRepository.findAllByUsuarioContainingIgnoreCase(usuario.getUsuario()).isPresent())
			return Optional.empty();
		
		usuario.setSenha(criptografarSenha(usuario.getSenha()));

		return Optional.of(usuarioRepository.save(usuario));
	}
	
	
	public Optional<usuarioLogin> autenticarUsuario(Optional<usuarioLogin> usuarioLogin){
		
		Optional<usuario> usuario = usuarioRepository.findAllByUsuarioContainingIgnoreCase(usuarioLogin.get().getUsuario());
		
		if(usuario.isPresent()) {
			
			usuarioLogin.get().setId(usuario.get().getId());
			usuarioLogin.get().setNome(usuario.get().getNome());
			usuarioLogin.get().setFoto(usuario.get().getFoto());
			usuarioLogin.get().setToken(gerarBasicToken(usuarioLogin.get().getUsuario(), usuarioLogin.get().getSenhas()));
			usuarioLogin.get().setSenhas(usuario.get().getSenha());
			
			return usuarioLogin;
		}
		
		return Optional.empty();
		
	}
	
	public Optional<usuario> atualizarUsuario(usuario usuario) {

		if (usuarioRepository.findById(usuario.getId()).isPresent()) {
			Optional<usuario> buscaUsuario = usuarioRepository.findAllByUsuarioContainingIgnoreCase(usuario.getUsuario());

			if (buscaUsuario.isPresent()) {				
				if (buscaUsuario.get().getId() != usuario.getId())
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);
			}
			
			usuario.setSenha(criptografarSenha(usuario.getSenha()));

			return Optional.of(usuarioRepository.save(usuario));
		} 
			
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!", null);		
	}	
	
	
	
	
	private String criptografarSenha(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);
	}
	
	private Boolean compararSenhas(String senhaDigitada, String senhaBanco) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(senhaDigitada, senhaBanco);
	}
	
	private String gerarBasicToken(String usuario, String senha) {

		String token = usuario + ":" + senha;
		byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
		return "Basic " + new String(tokenBase64);

	}
	
}