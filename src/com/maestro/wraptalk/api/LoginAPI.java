package com.maestro.wraptalk.api;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;

import com.maestro.wraptalk.dao.WrapDAO;
import com.maestro.wraptalk.utils.Util;


public class LoginAPI extends BaseAPI{

	public LoginAPI(){ }
    
	@Override
	public void execute(HttpServerRequest request){
		init(request);
		
		if(params.isEmpty() || !checkValidation(params)){
		
			JsonObject rs = new JsonObject();
			rs.put("result_code", 0);
			rs.put("result_msg", "params error");
			request.response().end(rs.toString());
		}
		wrapDAO.getUser(this, params.getString("user_id"), params.getString("user_pw"), params.getString("gcm_id"), params.getString("device_id"));
		
	}

	@Override
	public Boolean checkValidation(JsonObject params){
		
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
		if(resultJO.containsKey("result") && "fail".equals(resultJO.getString("result"))){
			request.response().end(resultJO.toString());
			return;
		}
		
		JsonObject rs = new JsonObject();

		switch (what) {
		case WrapDAO.getUser:
			if(resultJO.getJsonArray("results").size()>0){
				String token = Util.getToken(params.getString("user_id"));
				wrapDAO.setSession(this, token);
				
				rs.put("result", "success");
				rs.put("result_text", "로그인 되었습니다.");
				rs.put("token", token);
				request.response().end(rs.toString());

				
			}else{
				rs.put("result", "fail");
				rs.put("result_text", "회원정보가 일치하지 않습니다.");
				request.response().end(rs.toString());
			}
			break;

		default:
			break;
		}
		
	}
	
	
}