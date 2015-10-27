package com.maestro.wraptalk.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {

	public static String getUserId(String token){
		
		String[] user_id = token.split("_user_");
		
		return user_id[1];
	}
	
	
	public static String getToken(String user_id){
		String a = user_id+ "wraptalk";
		String SHA = ""; 
		try{
			MessageDigest sh = MessageDigest.getInstance("SHA-256"); 
			sh.update(a.getBytes()); 
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer(); 
			for(int i = 0 ; i < byteData.length ; i++){
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			SHA = sb.toString();
			
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace(); 
			SHA = null; 
		}
		SHA += "_user_"+user_id;
		return SHA;
	}
}

