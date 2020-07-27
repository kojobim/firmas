package com.bim.seguridad.web.rest;

import javax.sql.DataSource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class CreadorDeOrdenes {

	public static void main(String[] args) {
		int i=0;
		while(i<10) {
		long t=System.nanoTime();
		String d=String.valueOf(t);		
		System.out.println(d.substring(5));
		i++;
		}
	}
	
	
	@GetMapping("/ordenes/{cantid}")
	public String createOrdenes(@PathVariable Long cantid) {
		Orp_Client="00195171";
		Orp_Cuenta="001951710012";
		Orp_CueOrd="001951710012";
		Orp_TiCuBe="40";
		Orp_CueRec="012180015174856821";
		Orp_NomBen="DESCRI 1";
		Orp_DatBen="JIOK800610M21";
		Orp_Canid="0.20";
		Orp_ConPag="MISMO CONCEPTO";
		Orp_Fecha="20200713";
		Orp_InsRec="40012";
		Orp_Priori="1";
		Ucs_NumTra=String.valueOf(System.nanoTime()).substring(5);
		Transaccio="SPP";
		Modulo="SP";
		
		
		
		return "";
	}
	
	private static String Orp_Client;
	private static String Orp_Cuenta;
	private static String Orp_CueOrd;
	private static String Orp_TiCuBe;
	private static String Orp_CueRec;
	private static String Orp_NomBen;
	private static String Orp_DatBen;
	private static String Orp_Canid;
	private static String Orp_ConPag;
	private static String Orp_Fecha;
	private static String Orp_InsRec;
	private static String Orp_Priori;
	private static String Ucs_NumTra;
	private static String Transaccio;
	private static String Modulo;
	private static String query="call SPORDPAGALT "
			+ "'',"
			+ "'E',"
			+ "'001',"
			+ "'"+Orp_Client+"',"
			+ "'"+Orp_Cuenta+"',"
			+ "'',"
			+ "'40',"
			+ "'"+Orp_CueOrd+"',"
			+ "'YAZMIN IRAZU DURAN CHAVEZ',"
			+ "'DUCY7511056RA',"
			+ "'"+Orp_TiCuBe+"',"
			+ "'"+Orp_CueRec+"',"
			+ "'"+Orp_NomBen+"','"
			+Orp_DatBen+"',"
			+ "'',"
			+ "'',"
			+ "'',"
			+ "'',"
			+ "'',"
			+ ""+Orp_Canid+","
			+ "0.0,"
			+ "0.0,"
			+ "'"+Orp_ConPag+"',"
			+ "'',"
			+ "'',"
			+ "'',"
			+ "'01',"
			+ "'"+Orp_Fecha+"',"
			+ "'N',"
			+ "'CC',"
			+ "'',"
			+ "'',"
			+ "0.0,"
			+ "'000662',"
			+ "0,0,"
			+ "'',"
			+ "'',"
			+ "'',"
			+ "'',"
			+ "'"
			+Orp_InsRec+"',"
			+ "'"+Orp_Priori+"',"
			+ "'',"
			+ "'',"
			+ "'',"
			+ "'P',"
			+ "0,'"
			+ ""+Ucs_NumTra+"',"
			+ "'"+Transaccio+"',"
			+ "'',"
			+ "'',"
			+ "'001',"
			+ "'001',"
			+ "'"+Modulo+"'";
}
