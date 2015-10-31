package com.maestro.wraptalk.api;

import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;

import com.maestro.wraptalk.dao.AbstractQuery;
import com.maestro.wraptalk.dao.Redis;
import com.maestro.wraptalk.dao.WrapDAO;

public abstract class BaseAPI {
	HttpServerRequest request;
	JsonObject params;
	WrapDAO wrapDAO;
	
	public void init(HttpServerRequest request){
		this.request = request;
		MultiMap param = request.params();
		params = new JsonObject();
		param.forEach(entry -> params.put(entry.getKey(), entry.getValue()));
		
		wrapDAO  = WrapDAO.getInstance();
	}
	
	public abstract Boolean checkValidation( JsonObject Params );
	
	public abstract void execute( HttpServerRequest params);
	
	public abstract void onExecute(int what, JsonObject resultJO);

}
