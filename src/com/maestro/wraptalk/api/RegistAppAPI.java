package com.maestro.wraptalk.api;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;

import com.maestro.wraptalk.dao.AbstractQuery;
import com.maestro.wraptalk.dao.WrapDAO;
import com.maestro.wraptalk.utils.Util;

public class RegistAppAPI extends BaseAPI{
	
	public RegistAppAPI() { }
	
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
			Util.getUserId(params.getString("token"));
			wrapDAO.setApp(this, params.getString("app_id"), Util.getUserId(params.getString("token")));
			break;
			
		case WrapDAO.setApp:
			wrapDAO.getApp(this, Util.getUserId(params.getString("token")));
			break;
			
		case WrapDAO.getApp:
			rs.put("result", "success");
			rs.put("result_text", "사용 앱으로 등록 되었습니다.");
			request.response().end(rs.toString());
		}
		
	}

}
