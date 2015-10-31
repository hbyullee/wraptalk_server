package com.maestro.wraptalk.dao;

import io.vertx.core.json.JsonObject;

public abstract class RedisCallback {
	
	public void endSuccess(String result){
		JsonObject response = new JsonObject();
		if(result==null)
			response.put("results", "");
		else
			response.put("results", result);

		success( response ) ;
		
	}
	
	public void endFail(String result){
		JsonObject response = new JsonObject();
		response.put("results", "");
		success( response ) ;

	}

	public void endSuccess(){
		JsonObject response = new JsonObject();
		response.put("results", "");
		success( response ) ;
	}

	public void endFail(){
		fail() ;
	}

	public abstract void success( JsonObject rs ) ;
	
	public abstract void fail( ) ;
}
