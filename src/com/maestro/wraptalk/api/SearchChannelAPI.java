package com.maestro.wraptalk.api;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;

import com.maestro.wraptalk.dao.AbstractQuery;
import com.maestro.wraptalk.dao.Redis;
import com.maestro.wraptalk.dao.WrapDAO;

public class SearchChannelAPI extends BaseAPI{
	
	public SearchChannelAPI() { }
	
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
				rs.put("result_code", -1 );
				rs.put("result_msg", "login please");
				request.response().end(rs.toString());
				break;
			}
			if(!params.containsKey("app_id"))
				wrapDAO.getChannel2(this, params.getString("channel_name"));
			else
				wrapDAO.getChannel2(this, params.getString("channel_name"), params.getString("app_id"));
				
			break;
	
		case WrapDAO.getChannel2:
			rs.put("result_code", 0);
			rs.put("result_msg", "success to search channel list");
			rs.put("list_channel", resultJO.getJsonArray("results"));
			request.response().end(rs.toString());
		}
		
	}

}
