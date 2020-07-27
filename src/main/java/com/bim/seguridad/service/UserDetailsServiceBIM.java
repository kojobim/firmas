package com.bim.seguridad.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bim.seguridad.domain.Usuario;
import com.bim.seguridad.repository.UsuarioRepository;

@Service
public class UserDetailsServiceBIM implements UserDetailsService{

	@Autowired
	private UsuarioRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usu=null;
		List<GrantedAuthority> roles=new ArrayList<>();
		UserDetails userDetails=null;
		try {
			usu=repo.findByNombre(username);
			roles.add(new SimpleGrantedAuthority(usu.getRol()));
			userDetails=new User(usu.getNombre(), usu.getContrasenia(), roles);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		return userDetails;
	}

	public static void main(String [] args) { 
			//throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		BCryptPasswordEncoder b = new BCryptPasswordEncoder();
		System.out.println(b.encode("Enared01"));
		//$2a$10$rr2wIsSK/FGd5BOwfDWnE.ZQuyGRn7fK5kMXSdFLessNfiUeOjCmG - Enared01
		//$2a$10$bQgCPfA648hgD/VX1.bzE.GhwG7FukLdKUNAl7DuHMMgAP6aIi/Oy - Enared01!
//		 //Creating KeyPair generator object
//	      KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DSA");
//		      
//	      //Initializing the key pair generator
//	      keyPairGen.initialize(2048);
//		      
//	      //Generate the pair of keys
//	      KeyPair pair = keyPairGen.generateKeyPair();
//	      
//	      //Getting the privatekey from the key pair
//	      PrivateKey privKey = pair.getPrivate();
//
//	      //Creating a Signature object
//	      Signature sign = Signature.getInstance("SHA256withDSA");
//
//	      //Initializing the signature
//	      int i=0;
//	      while(i<10) {
//	      sign.initSign(privKey);
//	      byte[] bytes = "Hello how are you".getBytes();
//	      
//	      //Adding data to the signature
//	      sign.update(bytes);
//	      
//	      //Calculating the signature
//	      byte[] signature = sign.sign();
//	      
//	      String firstSignature=Base64.encodeBase64String(signature);
//	      System.out.println(firstSignature);
//	      
//	      //Initializing the signature
//	      sign.initVerify(pair.getPublic());
//	      sign.update(bytes);
//	      
//	      //Verifying the signature
//	      boolean bool = sign.verify(signature);
//	      
//	      if(bool) {
//	         System.out.println("Signature verified");   
//	      } else {
//	         System.out.println("Signature failed");
//	      }
//	      i++;
//	      }
	}
	
}
	