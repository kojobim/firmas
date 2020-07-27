package com.bim.seguridad.service;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import org.mapstruct.ap.internal.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bim.seguridad.domain.Llaves;
import com.bim.seguridad.domain.Orden;
import com.bim.seguridad.domain.Usuario;
import com.bim.seguridad.repository.LlavesRepository;
import com.bim.seguridad.repository.UsuarioRepository;

@Service
public class FirmaService {

	@Autowired
	private UsuarioRepository userRepo;
	
	@Autowired
	private LlavesRepository llavesRepo;
	
	@Autowired
	private Signature signature;
	
	
	public Orden firmarService(Orden orden) {
		
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();		
        Usuario usuario=userRepo.findByNombre(auth.getName());
        Llaves llaves=llavesRepo.findByUsuario(usuario);        
        byte[] privada=llaves.getPrivada();
        orden.setPrimaryKey(orden.getFirma());
        String firmado=firmarOrden(orden.getFirma(),privada);
        orden.setFirma(firmado);  
        orden.addLlaves(llaves);
        orden.setLlaves(Collections.asSet(llaves));
        
		return orden;
	}
	
	private String firmarOrden(String firma, byte[] privada) {		
		try {
			
			 KeyFactory kf=KeyFactory.getInstance("RSA");
			 PrivateKey pk=kf.generatePrivate(new PKCS8EncodedKeySpec(privada));
			signature.initSign(pk);
			signature.update(firma.getBytes());
			byte[] sign=signature.sign();
			firma=Base64.getEncoder().encodeToString(sign);			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return firma;
	}
	
	
}
