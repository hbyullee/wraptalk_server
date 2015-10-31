package com.maestro.wraptalk.dao;

import com.maestro.wraptalk.api.BaseAPI;
import com.maestro.wraptalk.utils.Config;
import com.maestro.wraptalk.utils.Util;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;



public class WrapDAO {

	AbstractQuery abstractQuery;
	Redis redis;
	
	public static final int customQuery = 0;
	public static final int setUser = 1;
	public static final int getUser = 2;
	public static final int getUserId = 3;
	public static final int getSession = 4;
	public static final int setSession = 5;
	public static final int setApp = 6;
	public static final int getApp = 7;
	public static final int setChannel = 8;
	public static final int getChannel = 9;
	public static final int getChannel2 = 10; // searchChannel api를 위한
	public static final int setNickApp = 11;
	public static final int setUserChannel = 12;
	public static final int delUserChannel = 13;
	public static final int getGlobalConfig = 14;
	public static final int setGlobalConfig = 15;
	public static final int getAppConfig = 16;
	public static final int setAppConfig = 17;
	public static final int getNoticeList = 18;
	public static final int changeChannelSet = 19;
	
	
	static WrapDAO m_this = null ;
	
	private WrapDAO( ) { } ;
	
	public static WrapDAO getInstance( ) 
	{ 
		if( m_this == null )
		{
			m_this = new WrapDAO();
		}
		return m_this ;
	} ;
	
	public void init(AbstractQuery abstractQuery, Redis redis )
	{
		this.abstractQuery = abstractQuery;
		this.redis = redis;
	}
	
	public void customQuery(BaseAPI api, String query){
		Util.logDebug("customQuery sql execute");
		
		abstractQuery.execute2(query, new QueryCallback() {
			
			@Override
			public void success(SQLConnection con, JsonObject rs) {
				Util.logDebug("customQuery sql success");
				api.onExecute(customQuery, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				Util.logDebug("customQuery sql fail");
				JsonObject rs = new JsonObject();
				rs.put("result_code", -1);
				rs.put("result_msg", "DB query fail");
				api.onExecute(customQuery, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
	}
	
	public void getNoticeList(BaseAPI api, String user_id){
		Util.logDebug("getNoticeList sql execute");
		String query = String.format("SELECT * FROM notice");
		
		abstractQuery.execute(query, new QueryCallback() {
			
			@Override
			public void success(SQLConnection con, JsonObject rs) {
				Util.logDebug("getnoticeList sql success");
				api.onExecute(getNoticeList, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				Util.logDebug("getnoticeList sql fail");
				JsonObject rs = new JsonObject();
				rs.put("result_code", -1);
				rs.put("result_msg", "DB query fail");
				api.onExecute(getNoticeList, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
		
	}

	public void changeChannelSet(BaseAPI api, String channel_id, String user_id, String alarm_onoff ){
		Util.logDebug("changeChannelSet sql execute");
		String query =  String.format("UPDATE channel_user_list SET alarm_onoff='%s' WHERE user_id='%s' and channel_id='%s'", alarm_onoff, user_id, channel_id);
		
		abstractQuery.execute(query, new QueryCallback() {
			
			@Override
			public void success(SQLConnection con, JsonObject rs) {
				Util.logDebug("changeChannelSet sql success");
				api.onExecute(changeChannelSet, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				Util.logDebug("changeChannelSet sql fail");
				JsonObject rs = new JsonObject();
				rs.put("result_code", -1);
				rs.put("result_msg", "DB query fail");
				api.onExecute(changeChannelSet, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
	}
	
	public void getAppConfig(BaseAPI api, String user_id, String app_id){
		Util.logDebug("getAppConfig sql execute");
		String query =  String.format("SELECT * FROM  app_user_list WHERE user_id='%s' and app_id'%s'", user_id, app_id);
		
		abstractQuery.execute(query, new QueryCallback() {
			
			@Override
			public void success(SQLConnection con, JsonObject rs) {
				Util.logDebug("getAppConfig sql success");
				api.onExecute(getGlobalConfig, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				Util.logDebug("getAppConfig sql fail");
				JsonObject rs = new JsonObject();
				rs.put("result_code", -1);
				rs.put("result_msg", "DB query fail");
				api.onExecute(getGlobalConfig, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
	}
	
	public void setAppConfig(BaseAPI api, String app_id, String user_id, String alarm_onoff ){
		Util.logDebug("setAppConfig sql execute");
		String query =  String.format("UPDATE app_user_list SET alarm_onoff='%s' WHERE user_id='%s' and app_id='%s'", alarm_onoff, user_id, app_id);
		
		abstractQuery.execute(query, new QueryCallback() {
			
			@Override
			public void success(SQLConnection con, JsonObject rs) {
				Util.logDebug("setAppConfig sql success");
				api.onExecute(setGlobalConfig, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				Util.logDebug("setAppConfig sql fail");
				JsonObject rs = new JsonObject();
				rs.put("result_code", -1);
				rs.put("result_msg", "DB query fail");
				api.onExecute(setGlobalConfig, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
	}
	
	public void setGlobalConfig(BaseAPI api, String user_id, String alarm_onoff ){
		Util.logDebug("setGlobalConfig sql execute");
		String query =  String.format("UPDATE setting SET alarm_onoff='%s' WHERE user_id='%s'", alarm_onoff, user_id);
		
		abstractQuery.execute(query, new QueryCallback() {
			
			@Override
			public void success(SQLConnection con, JsonObject rs) {
				Util.logDebug("setGlobalConfig sql success");
				api.onExecute(setGlobalConfig, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				Util.logDebug("setGlobalConfig sql fail");
				JsonObject rs = new JsonObject();
				rs.put("result_code", -1);
				rs.put("result_msg", "DB query fail");
				api.onExecute(setGlobalConfig, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
	}	
	
	public void getGlobalConfig(BaseAPI api, String user_id){
		Util.logDebug("getGlobalConfig sql execute");
		String query =  String.format("SELECT * FROM  setting WHERE user_id='%s'", user_id);
		
		abstractQuery.execute(query, new QueryCallback() {
			
			@Override
			public void success(SQLConnection con, JsonObject rs) {
				Util.logDebug("getGlobalConfig sql success");
				api.onExecute(getGlobalConfig, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				Util.logDebug("getGlobalConfig sql fail");
				JsonObject rs = new JsonObject();
				rs.put("result_code", -1);
				rs.put("result_msg", "DB query fail");
				api.onExecute(getGlobalConfig, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
	}
	
	public void delUserChannel(BaseAPI api, String channel_no, String user_id){
		Util.logDebug("delUserChannel sql execute");
		
		String query = String.format("DELETE channel_user_list WHERE channel_no='%s' and user_id='%s'", 
				channel_no, user_id);
		abstractQuery.execute(query, new QueryCallback() {
			
			@Override
		public void success(SQLConnection con, JsonObject rs) {
				Util.logDebug("delUserChannel sql success");
				api.onExecute(delUserChannel, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				Util.logDebug("delUserChannel sql fail");
				JsonObject rs = new JsonObject();
				rs.put("result_code", -1);
				rs.put("result_msg", "DB query fail");
				api.onExecute(delUserChannel, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
	}
	
	public void setUserChannel(BaseAPI api, String user_id, String app_id, String channel_id, String nick, String alarm_onoff){
		Util.logDebug("setUserChannel sql execute");
		
		String query = String.format("INSERT INTO channel_user_list SET user_id='%s', app_id='%s', channel_id='%s', user_nick='%s', alarm_onoff='%s'",
				user_id, app_id, channel_id, nick, alarm_onoff);
		abstractQuery.execute(query, new QueryCallback() {
			
			@Override
			public void success(SQLConnection con, JsonObject rs) {
				Util.logDebug("setUserChannel sql success");
				api.onExecute(setUserChannel, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				Util.logDebug("setUserChannel sql fail");
				JsonObject rs = new JsonObject();
				rs.put("result_code", -1);
				rs.put("result_msg", "DB query fail");
				api.onExecute(setUserChannel, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
	}
	
	
	
	public void setNickApp(BaseAPI api, String user_id, String app_id, String nick){
		Util.logDebug("setNickApp sql execute");
		
		String query = String.format("UPDATE app_user_list SET user_nick='%s' WHERE user_id='%s' and app_id='%s'",
				nick, user_id, app_id);
		abstractQuery.execute(query, new QueryCallback() {
			
			@Override
		public void success(SQLConnection con, JsonObject rs) {
				Util.logDebug("setNickApp sql success");
				api.onExecute(setNickApp, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				Util.logDebug("setNickApp sql fail");
				JsonObject rs = new JsonObject();
				rs.put("result_code", -1);
				rs.put("result_msg", "DB query fail");
				api.onExecute(setNickApp, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
	}
	
	public void setChannel(BaseAPI api, String chief_id, String app_id, String channel_id, String channel_name, String channel_limit, String public_onoff, String channel_pw ){
		Util.logDebug("setChannel sql execute");
		Config.bridgeOptions.addOutboundPermitted(new PermittedOptions().setAddress("to.channel."+channel_id));

		String query = String.format("INSERT INTO channel SET channel_id='s%', chief_id='%s', app_id='%s', channel_name='%s', public_onoff='%s', channel_pw='%s', channel_limit='%s', datetime=now()" 
									,channel_id , chief_id ,app_id, channel_name, public_onoff, channel_pw, channel_limit);
		abstractQuery.execute(query, new QueryCallback() {
			
			@Override
			public void success(SQLConnection con, JsonObject rs) {
				Util.logDebug("setChannel sql success");
				api.onExecute(setChannel, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				Util.logDebug("setChannel sql fail");
				JsonObject rs = new JsonObject();
				rs.put("result_code", -1);
				rs.put("result_msg", "DB query fail");
				api.onExecute(setChannel, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
		
	}
	
	public void setChannel(BaseAPI api, String chief_id, String app_id, String channel_id, String channel_name, String channel_limit, String public_onoff){
		Util.logDebug("setChannel sql execute");
		Config.bridgeOptions.addOutboundPermitted(new PermittedOptions().setAddress("to.channel."+channel_id));

		String query = String.format("INSERT INTO channel SET channel_id='%s', chief_id='%s', app_id='%s', channel_name='%s', public_onoff='%s', channel_limit='%s', datetime=now()" 
				,channel_id, chief_id, app_id, channel_name, public_onoff, channel_limit);
		abstractQuery.execute(query, new QueryCallback() {
			
			@Override
			public void success(SQLConnection con, JsonObject rs) {
				Util.logDebug("setChannel sql success");
				api.onExecute(setChannel, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				Util.logDebug("setChannel sql fail");
				JsonObject rs = new JsonObject();
				rs.put("result_code", -1);
				rs.put("result_msg", "DB query fail");
				api.onExecute(setChannel, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
		
	}
	
	
	public void getChannel2(BaseAPI api, String channel_name){
		Util.logDebug("getChannel2 sql execute");
		String query =  String.format("SELECT * FROM  channel WHERE channel_name='%%s%'", channel_name);
		
		abstractQuery.execute(query, new QueryCallback() {
			
			@Override
			public void success(SQLConnection con, JsonObject rs) {
				Util.logDebug("getChannel2 sql success");
				api.onExecute(getChannel, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				Util.logDebug("getChannel2 sql fail");
				JsonObject rs = new JsonObject();
				rs.put("result_code", -1);
				rs.put("result_msg", "DB query fail");
				api.onExecute(getChannel, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
		
	}
	
	public void getChannel2(BaseAPI api, String channel_name, String app_id){
		Util.logDebug("getChannel2 sql execute");
		String query =  String.format("SELECT * FROM  channel WHERE channel_name='%%s%' and app_id='%s'", channel_name, app_id);
		
		abstractQuery.execute(query, new QueryCallback() {
			
			@Override
			public void success(SQLConnection con, JsonObject rs) {
				Util.logDebug("getChannel2 sql success");
				api.onExecute(getChannel, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				Util.logDebug("getChannel2 sql fail");
				JsonObject rs = new JsonObject();
				rs.put("result_code", -1);
				rs.put("result_msg", "DB query fail");
				api.onExecute(getChannel, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
		
	}
	
	public void getChannel(BaseAPI api, String user_id){
		Util.logDebug("getChannel sql execute");
		String query =  String.format("SELECT * FROM  channel_user_list WHERE user_id='%s'", user_id);
		
		abstractQuery.execute(query, new QueryCallback() {
			
			@Override
			public void success(SQLConnection con, JsonObject rs) {
				Util.logDebug("getChannel sql success");
				api.onExecute(getChannel, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				Util.logDebug("getChannel sql fail");
				JsonObject rs = new JsonObject();
				rs.put("result_code", -1);
				rs.put("result_msg", "DB query fail");
				api.onExecute(getChannel, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
		
	}
	
	public void getChannel(BaseAPI api, String user_id, String app_id){
		Util.logDebug("getChannel sql execute");
		String query =  String.format("SELECT * FROM channel WHERE user_id='%s' and app_id='%s'", user_id, app_id);
		
		abstractQuery.execute(query, new QueryCallback() {
			
			@Override
			public void success(SQLConnection con, JsonObject rs) {
				Util.logDebug("getChannel sql success");
				api.onExecute(getChannel, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				Util.logDebug("getChannel sql fail");
				JsonObject rs = new JsonObject();
				rs.put("result_code", -1);
				rs.put("result_msg", "DB query fail");
				api.onExecute(getChannel, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
		
	}
	
	
	public void getSession(BaseAPI api, String token){
		Util.logDebug("getSession sql execute");
		
		redis.get(token, new RedisCallback() {
			
			@Override
			public void success(JsonObject rs) {
				api.onExecute(getSession, rs);
			}
			
			@Override
			public void fail() {
				JsonObject rs = new JsonObject();
				rs.put("result_code", -1);
				rs.put("result_msg", "DB query fail");
				rs.put("results", "");
				api.onExecute(getSession, rs);
			}
		});
		
//		String query = String.format("SELECT * FROM session WHERE token ='%s'", token);
//		abstractQuery.execute(query, new QueryCallback() {
//			
//			@Override
//			public void success(SQLConnection con, JsonObject rs) {
//				Util.logDebug("getSession sql success");
//				api.onExecute(getSession, rs);
//				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
//			}
//			
//			@Override
//			public void fail() {
//				Util.logDebug("getSession sql fail");
//				JsonObject rs = new JsonObject();
//				rs.put("result_code", -1);
//				rs.put("result_msg", "DB query fail");
//				api.onExecute(getSession, rs);
//				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
//			}
//		});
		
	}
	
	public void getUserId(BaseAPI api, String user_id){
		Util.logDebug("getUserId sql execute");
		String query = String.format("SELECT * FROM user WHERE user_id='%s'", user_id);
		abstractQuery.execute(query, new QueryCallback() {
			
			@Override
			public void success(SQLConnection con, JsonObject rs) {
				Util.logDebug("getUserId sql success");
				api.onExecute(getUserId, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				Util.logDebug("getUserId sql fail");
				JsonObject rs = new JsonObject();
				rs.put("result_code", -1);
				rs.put("result_msg", "DB query fail");
				api.onExecute(getUserId, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
	}
	
	public void setUser(BaseAPI api, String user_id, String user_pw, String gcm_id, String device_id){
		String query = String.format("INSERT INTO user SET user_id='%s', user_pw='%s', gcm_id='%s', device_id='%s', join_date=now()" 
									,user_id, user_pw, gcm_id, device_id);
		
		abstractQuery.execute(query,  new QueryCallback(){
			
			@Override
			public void success(SQLConnection con, JsonObject rs) {
				api.onExecute(setUser, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				JsonObject rs = new JsonObject();
				rs.put("result_code", -1);
				rs.put("result_msg", "DB query fail");
				api.onExecute(setUser, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
		});
	}
	
	public void getUser(BaseAPI api, String user_id, String user_pw, String gcm_id, String device_id){
		String query = String.format("SELECT * FROM user WHERE user_id='%s' and user_pw='%s'" ,user_id , user_pw);
		
		abstractQuery.execute(query, new QueryCallback(){
			
			@Override
			public void success(SQLConnection con, JsonObject rs) {
				api.onExecute(getUser, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}

			@Override
			public void fail() {
				JsonObject rs = new JsonObject();
				rs.put("result_code", -1);
				rs.put("result_msg", "DB query fail");
				api.onExecute(getUser, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
					
			}
		});
		
	}
	
	public void setSession(BaseAPI api, String token ){
		redis.set(token, Util.getUserId(token), new RedisCallback() {
			
			@Override
			public void success(JsonObject rs) {
				api.onExecute(getUser, rs);
				Util.logDebug(api.getClass().getName() + " callbackRedis : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				JsonObject rs = new JsonObject();
				rs.put("result_code", -1);
				rs.put("result_msg", "DB query fail");
				api.onExecute(getUser, rs);
				Util.logDebug(api.getClass().getName() + " callbackRedis : " + rs.toString() );				
			}
		});
//		String query =  String.format("INSERT INTO session SET token = '%s', datetime = now()", token);
//		abstractQuery.execute(query , new QueryCallback() {
//			
//			@Override
//			public void success(SQLConnection con, JsonObject rs) {
//			}
//			
//			@Override
//			public void fail() {
//			}
//		});
		
	}
	
	public void setApp(BaseAPI api, String app_id, String user_id, String nick){
		String query =  String.format("INSERT INTO app_user_list SET app_id='%s', user_id='%s', user_nick='%s'", app_id, user_id, nick);
		abstractQuery.execute(query, new QueryCallback() {
		
			@Override
			public void success(SQLConnection con, JsonObject rs) {
				api.onExecute(setApp, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				JsonObject rs = new JsonObject();
				rs.put("result_code", -1);
				rs.put("result_msg", "DB query fail");
				api.onExecute(setApp, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
	}
	
	public void setApp(BaseAPI api, String app_id, String user_id){
		String query =  String.format("INSERT INTO app_user_list SET app_id='%s', user_id='%s'", app_id, user_id);
		abstractQuery.execute(query, new QueryCallback() {
		
			@Override
			public void success(SQLConnection con, JsonObject rs) {
				api.onExecute(setApp, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				JsonObject rs = new JsonObject();
				rs.put("result_code", -1);
				rs.put("result_msg", "DB query fail");
				api.onExecute(setApp, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
	}
	
	public void getApp(BaseAPI api, String user_id){
		String query =  String.format("SELECT * FROM  app_user_list WHERE user_id='%s'", user_id);
		abstractQuery.execute(query, new QueryCallback() {
		
			@Override
			public void success(SQLConnection con, JsonObject rs) {
				api.onExecute(getApp, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				JsonObject rs = new JsonObject();
				rs.put("result_code", -1);
				rs.put("result_msg", "DB query fail");
				api.onExecute(getApp, rs);
				Util.logDebug(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
	}
}
