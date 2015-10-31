package com.maestro.wraptalk.api;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;

import com.maestro.wraptalk.dao.AbstractQuery;
import com.maestro.wraptalk.dao.WrapDAO;
import com.maestro.wraptalk.utils.Util;

public class JoinChannelAPI extends BaseAPI{
	
	public JoinChannelAPI() { }
	
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
			rs.put("result_code", -1);
			rs.put("result_msg", "params error");
			request.response().end(rs.toString());
		}

	}

	@Override
	public void onExecute(int what, JsonObject resultJO) {

		if(resultJO.containsKey("result_code") && resultJO.getInteger("result_code")==-1){
			request.response().end(resultJO.toString());
			return;
		}
		JsonObject rs = new JsonObject();

		switch (what) {
		case WrapDAO.getSession:
			if(resultJO.getString("results").length()<1){
				rs.put("result_code", -1);
				rs.put("result_msg", "login please");
				request.response().end(rs.toString());
				break;
			}
			wrapDAO.
			
			if(params.containsKey("channel_pw"))
				wrapDAO.setUserChannel(this, Util.getUserId(params.getString("token")), params.getString("app_id"), params.getString("channel_id"), params.getString("nick"), params.getString("alarm_onoff"), params.getString("channel_pw"));
			else
				wrapDAO.setUserChannel(this, Util.getUserId(params.getString("token")), params.getString("app_id"), params.getString("channel_id"), params.getString("nick"), params.getString("alarm_onoff"));
				
			break;
			
		case WrapDAO.setUserChannel:
			
			rs.put("result_code", 0);
			rs.put("result_msg", "success to join channel");
			
			request.response().end(rs.toString());
			break;
		}
		
	}

}
