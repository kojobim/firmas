package com.bim.seguridad.service;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.function.Consumer;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bim.seguridad.domain.Llaves;
import com.bim.seguridad.domain.Usuario;
import com.bim.seguridad.repository.LlavesRepository;
import com.bim.seguridad.repository.UsuarioRepository;
import com.bim.seguridad.security.AuthoritiesConstants;

@Service
public class LlavesService {
	
	@Autowired
	private UsuarioRepository userRepo;
	
	@Autowired 
	private LlavesRepository llavesRepo;
	

	public Llaves fillLlaves(Llaves llave) {
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();		
		
        Usuario usuario=userRepo.findByNombre(auth.getName());
//        Encoder encoder=Base64.getEncoder();
        KeyPair kp=this.generateKeys();
        llave.setPrivada(kp.getPrivate().getEncoded());
        llave.setPublica(kp.getPublic().getEncoded());
        llave.setUsuario(usuario);
		return llave;
	}
	
	public List<Llaves> createLlaves() {
		List<Usuario>usuarios=userRepo.findAll();
		List<Llaves> llaves=new ArrayList<Llaves>();
		usuarios.forEach(new Consumer<Usuario>() {

			@Override
			public void accept(Usuario t) {
				if(!t.getRol().equals(AuthoritiesConstants.ADMIN)) {
					KeyPair kp=generateKeys();
					Llaves llave=new Llaves();
					llave.setPrivada(kp.getPrivate().getEncoded());
					llave.setPublica(kp.getPublic().getEncoded());
					llave.setUsuario(t);
					llaves.add(llave);				
				}
				
			}
		});
		//llavesRepo.saveAll(llaves);
		return llaves;
	}
	private KeyPair generateKeys() {
		KeyPair keyPair=null;
		try {
			KeyPairGenerator kpg=KeyPairGenerator.getInstance("RSA");			
			kpg.initialize(2048,new SecureRandom());
			keyPair=kpg.generateKeyPair();			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return keyPair;
	}
	
	
}
