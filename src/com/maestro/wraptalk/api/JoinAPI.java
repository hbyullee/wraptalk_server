package com.maestro.wraptalk.api;

import com.maestro.wraptalk.dao.WrapDAO;
import com.maestro.wraptalk.utils.Util;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class JoinAPI extends BaseAPI{

	public JoinAPI(){ }
	
	@Override
	public void execute(HttpServerRequest request){
		init(request);
		
		if(params.isEmpty() || !checkValidation(params)){
			JsonObject rs = new JsonObject();
			rs.put("result_code", -1);
			rs.put("result_msg", "params error");
			request.response().end(rs.toString());
			
		}
		
		wrapDAO.getUserId(this, params.getString("user_id"));
		
	}

	@Override
	public Boolean checkValidation(JsonObject params) {
		if (!params.containsKey("user_id") || 
			params.getString("user_id").isEmpty() || 
			params.getString("user_id").equals("")){
				
			return false;
		}
		if (!params.containsKey("user_pw") || 
			params.getString("user_pw").isEmpty() || 
			params.getString("user_pw").equals("")){
			
			return false;
		}
		if (!params.containsKey("gcm_id") || 
			params.getString("gcm_id").isEmpty() || 
			params.getString("gcm_id").equals("")){
				
			return false;
		}
		if (!params.containsKey("device_id") || 
			params.getString("device_id").isEmpty() || 
			params.getString("device_id").equals("")){
				
			return false;
		}
			return true;
	}


	@Override
	public void onExecute(int what, JsonObject resultJO) {
		if(resultJO.containsKey("result_code") && resultJO.getInteger("result_code")==-1){
			request.response().end(resultJO.toString());
			return;
		}
		
		JsonObject rs = new JsonObject();

		switch (what) {
		case WrapDAO.getUserId:
			JsonArray ja  = resultJO.getJsonArray("results");
			if(ja.size()<1)
			wrapDAO.setUser(this, params.getString("user_id"), params.getString("user_pw"), params.getString("gcm_id"), params.getString("device_id"));
			else {
				rs.put("result_code", -1);
				rs.put("result_msg", "id already exist");
				
				request.response().end(rs.toString());
			}
			
			break;
		
		case WrapDAO.setUser:
			String token = Util.getToken(params.getString("user_id"));
			wrapDAO.setSession(this, token);
			rs.put("result_code", 0);
			rs.put("result_msg", "join success");
			rs.put("token", token );
			
			request.response().end(rs.toString());
			break;
			
		default:
			break;
		}
		
	}


}
