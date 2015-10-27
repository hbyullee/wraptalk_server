package com.maestro.wraptalk.api;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;

import com.maestro.wraptalk.dao.AbstractQuery;
import com.maestro.wraptalk.dao.WrapDAO;
import com.maestro.wraptalk.utils.Util;

public class SetGlobalConfigAPI extends BaseAPI{
	
	public SetGlobalConfigAPI() { }
	
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
			
			wrapDAO.setGlobalConfig(this, Util.getUserId(params.getString("token")), params.getString("alarm_onoff"));
			break;
	
		case WrapDAO.setGlobalConfig:
			rs.put("result", "success");
			rs.put("result_text", "설정 정보를 수정하였습니다.");
			rs.put("list_app", resultJO.getJsonArray("results"));
			request.response().end(rs.toString());
		}
		
	}

}
