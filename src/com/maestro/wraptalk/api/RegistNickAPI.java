package com.maestro.wraptalk.api;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;

import com.maestro.wraptalk.dao.AbstractQuery;
import com.maestro.wraptalk.dao.WrapDAO;
import com.maestro.wraptalk.utils.Util;

public class RegistNickAPI extends BaseAPI{
	
	public RegistNickAPI() { }
	
	@Override
	public Boolean checkValidation(JsonObject Params) {
		return true;
	}

	@Override
	public void execute(HttpServerRequest request) {
		init(request);
		
		wrapDAO.getSession(this, params.getString("token"));
		
		if(params.isEmpty() || !checkValidation(params)){
		
			JsonObject rs = new JsonObject();
			rs.put("result_code", 0);
			rs.put("result_msg", "params error");
			request.response().end(rs.toString());
		}

		
	}


	@Override
	public void onExecute(int what, JsonObject resultJO) {
		
		if(resultJO.containsKey("result") && "fail".equals(resultJO.getString("result"))){
			request.response().end(resultJO.toString());
			return;
		}
		JsonObject rs = new JsonObject();

		switch (what) {
		case WrapDAO.getSession:
			if(resultJO.getString("results").length()<1){
				rs.put("result", "fail");
				rs.put("result_text", "로그인이 필요합니다.");
				request.response().end(rs.toString());
				break;
			}
			
			wrapDAO.setNickApp(this, Util.getUserId(params.getString("token")), params.getString("app_id"), params.getString("nick"));
			break;
			
		case WrapDAO.setNickApp:
			
			rs.put("result", "success");
			rs.put("result_text", "앱에 닉네임을 등록하였습니다.");
			request.response().end(rs.toString());
			
			break;
			
		}
		
	}

}
