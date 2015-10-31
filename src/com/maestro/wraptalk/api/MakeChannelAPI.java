package com.maestro.wraptalk.api;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;

import com.maestro.wraptalk.dao.WrapDAO;
import com.maestro.wraptalk.utils.Util;

public class MakeChannelAPI extends BaseAPI{
	
	public MakeChannelAPI() { }
	
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
		String channel_id="";
		switch (what) {
		case WrapDAO.getSession:
			if(resultJO.getString("results").length()<1){
				rs.put("result_code", -1);
				rs.put("result_msg", "login please");
				request.response().end(rs.toString());
				break;
			}
			channel_id = Util.getChannelId(params.getString("channel_name"));
			if("on".equals(params.getString("public_onoff")))
				wrapDAO.setChannel(this, Util.getUserId(params.getString("token")), params.getString("app_id"),
									channel_id, params.getString("channel_name"), 
									params.getString("channel_limit"), params.getString("public_onoff"));
			else
				wrapDAO.setChannel(this, Util.getUserId(params.getString("token")), params.getString("app_id"), 
									channel_id, params.getString("channel_name"), 
									params.getString("channel_limit"), params.getString("public_onoff"), params.getString("channel_pw"));
				break;

		case WrapDAO.setChannel:
			rs.put("result_code", 0);
			rs.put("result_msg", "success to make channel");
			request.response().end(rs.toString());
		}
		
	}
}
