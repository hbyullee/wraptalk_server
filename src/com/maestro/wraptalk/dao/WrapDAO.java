package com.maestro.wraptalk.dao;

import com.maestro.wraptalk.api.BaseAPI;
import com.maestro.wraptalk.utils.Util;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.SQLConnection;



public class WrapDAO {

	AbstractQuery abstractQuery;
	Redis redis;
	
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
	public static final int changeChannelSet = 16;
	
	
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
	
	public void changeChannelSet(BaseAPI api, String channel_id, String user_id, String alarm_onoff ){
		System.out.println("changeChannelSet sql execute");
		String query =  String.format("UPDATE channel_user_list SET alarm_onoff='%s' WHERE user_id='%s' and channel_id='%s'", alarm_onoff, user_id, channel_id);
		
		abstractQuery.execute(query, new QueryCallback() {
			
			@Override
			public void success(SQLConnection con, JsonObject rs) {
				System.out.println("changeChannelSet sql success");
				api.onExecute(changeChannelSet, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				System.out.println("changeChannelSet sql fail");
				JsonObject rs = new JsonObject();
				rs.put("result", "fail");
				rs.put("result_text", "DB query fail");
				api.onExecute(changeChannelSet, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
	}
	
	public void setGlobalConfig(BaseAPI api, String user_id, String alarm_onoff ){
		System.out.println("setGlobalConfig sql execute");
		String query =  String.format("UPDATE setting SET alarm_onoff='%s' WHERE user_id='%s'", alarm_onoff, user_id);
		
		abstractQuery.execute(query, new QueryCallback() {
			
			@Override
			public void success(SQLConnection con, JsonObject rs) {
				System.out.println("setGlobalConfig sql success");
				api.onExecute(setGlobalConfig, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				System.out.println("setGlobalConfig sql fail");
				JsonObject rs = new JsonObject();
				rs.put("result", "fail");
				rs.put("result_text", "DB query fail");
				api.onExecute(setGlobalConfig, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
	}	
	
	public void getGlobalConfig(BaseAPI api, String user_id){
		System.out.println("getGlobalConfig sql execute");
		String query =  String.format("SELECT * FROM  setting WHERE user_id='%s'", user_id);
		
		abstractQuery.execute(query, new QueryCallback() {
			
			@Override
			public void success(SQLConnection con, JsonObject rs) {
				System.out.println("getGlobalConfig sql success");
				api.onExecute(getGlobalConfig, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				System.out.println("getGlobalConfig sql fail");
				JsonObject rs = new JsonObject();
				rs.put("result", "fail");
				rs.put("result_text", "DB query fail");
				api.onExecute(getGlobalConfig, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
	}
	
	public void delUserChannel(BaseAPI api, String channel_no, String user_id){
		System.out.println("delUserChannel sql execute");
		
		String query = String.format("DELETE channel_user_list WHERE channel_no='%s' and user_id='%s'", 
				channel_no, user_id);
		abstractQuery.execute(query, new QueryCallback() {
			
			@Override
		public void success(SQLConnection con, JsonObject rs) {
				System.out.println("delUserChannel sql success");
				api.onExecute(delUserChannel, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				System.out.println("delUserChannel sql fail");
				JsonObject rs = new JsonObject();
				rs.put("result", "fail");
				rs.put("result_text", "DB query fail");
				api.onExecute(delUserChannel, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
	}
	
	public void setUserChannel(BaseAPI api, String channel_no, String nick){
		System.out.println("setUserChannel sql execute");
		
		String query = String.format("INSERT INTO channel_user_list SET nick='%s', channel_no='%s'",
				nick, channel_no);
		abstractQuery.execute(query, new QueryCallback() {
			
			@Override
		public void success(SQLConnection con, JsonObject rs) {
				System.out.println("setUserChannel sql success");
				api.onExecute(setUserChannel, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				System.out.println("setUserChannel sql fail");
				JsonObject rs = new JsonObject();
				rs.put("result", "fail");
				rs.put("result_text", "DB query fail");
				api.onExecute(setUserChannel, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
	}
	
	public void setUserChannel(BaseAPI api, String channel_no, String nick, String channel_pw){
		System.out.println("setUserChannel sql execute");
		
		String query = String.format("INSERT INTO channel_user_list SET nick='%s', channel_no='%s', channel_pw='%s'",
				nick, channel_no, channel_pw);
		abstractQuery.execute(query, new QueryCallback() {
			
			@Override
		public void success(SQLConnection con, JsonObject rs) {
				System.out.println("setUserChannel sql success");
				api.onExecute(setUserChannel, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				System.out.println("setUserChannel sql fail");
				JsonObject rs = new JsonObject();
				rs.put("result", "fail");
				rs.put("result_text", "DB query fail");
				api.onExecute(setUserChannel, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
	}
	
	public void setNickApp(BaseAPI api, String user_id, String app_id, String nick){
		System.out.println("setNickApp sql execute");
		
		String query = String.format("UPDATE app_user_list SET nick='%s' WHERE user_id='%s' and app_id='%s'",
				nick, user_id, app_id);
		abstractQuery.execute(query, new QueryCallback() {
			
			@Override
		public void success(SQLConnection con, JsonObject rs) {
				System.out.println("setNickApp sql success");
				api.onExecute(setNickApp, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				System.out.println("setNickApp sql fail");
				JsonObject rs = new JsonObject();
				rs.put("result", "fail");
				rs.put("result_text", "DB query fail");
				api.onExecute(setNickApp, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
	}
	
	public void setChannel(BaseAPI api, String user_id, String app_id, String channel_name, String channel_limit, String public_onoff, String channel_pw ){
		System.out.println("setChannel sql execute");
		
		String query = String.format("INSERT INTO channel SET app_id='%s', channel_name='%s', public_onoff='%s', channel_pw='%s', channel_limit='%s', datetime=now()" 
				,app_id, channel_name, public_onoff, channel_pw, channel_limit);
		abstractQuery.execute(query, new QueryCallback() {
			
			@Override
			public void success(SQLConnection con, JsonObject rs) {
				System.out.println("setChannel sql success");
				api.onExecute(setChannel, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				System.out.println("setChannel sql fail");
				JsonObject rs = new JsonObject();
				rs.put("result", "fail");
				rs.put("result_text", "DB query fail");
				api.onExecute(setChannel, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
		
	}
	
	public void setChannel(BaseAPI api, String user_id, String app_id, String channel_name, String channel_limit, String public_onoff){
		System.out.println("setChannel sql execute");
		
		String query = String.format("INSERT INTO channel SET app_id='%s', channel_name='%s', public_onoff='%s', channel_limit='%s', datetime=now()" 
				,app_id, channel_name, public_onoff, channel_limit);
		abstractQuery.execute(query, new QueryCallback() {
			
			@Override
			public void success(SQLConnection con, JsonObject rs) {
				System.out.println("setChannel sql success");
				api.onExecute(setChannel, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				System.out.println("setChannel sql fail");
				JsonObject rs = new JsonObject();
				rs.put("result", "fail");
				rs.put("result_text", "DB query fail");
				api.onExecute(setChannel, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
		
	}
	
	
	public void getChannel2(BaseAPI api, String channel_name){
		System.out.println("getChannel2 sql execute");
		String query =  String.format("SELECT * FROM  app_user_list WHERE channel_name='%%s%'", channel_name);
		
		abstractQuery.execute(query, new QueryCallback() {
			
			@Override
			public void success(SQLConnection con, JsonObject rs) {
				System.out.println("getChannel2 sql success");
				api.onExecute(getChannel, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				System.out.println("getChannel2 sql fail");
				JsonObject rs = new JsonObject();
				rs.put("result", "fail");
				rs.put("result_text", "DB query fail");
				api.onExecute(getChannel, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
		
	}
	
	public void getChannel2(BaseAPI api, String channel_name, String app_id){
		System.out.println("getChannel2 sql execute");
		String query =  String.format("SELECT * FROM  app_user_list WHERE channel_name='%%s%' and app_id='%s'", channel_name, app_id);
		
		abstractQuery.execute(query, new QueryCallback() {
			
			@Override
			public void success(SQLConnection con, JsonObject rs) {
				System.out.println("getChannel2 sql success");
				api.onExecute(getChannel, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				System.out.println("getChannel2 sql fail");
				JsonObject rs = new JsonObject();
				rs.put("result", "fail");
				rs.put("result_text", "DB query fail");
				api.onExecute(getChannel, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
		
	}
	
	public void getChannel(BaseAPI api, String user_id){
		System.out.println("getChannel sql execute");
		String query =  String.format("SELECT * FROM  app_user_list WHERE user_id='%s'", user_id);
		
		abstractQuery.execute(query, new QueryCallback() {
			
			@Override
			public void success(SQLConnection con, JsonObject rs) {
				System.out.println("getChannel sql success");
				api.onExecute(getChannel, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				System.out.println("getChannel sql fail");
				JsonObject rs = new JsonObject();
				rs.put("result", "fail");
				rs.put("result_text", "DB query fail");
				api.onExecute(getChannel, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
		
	}
	
	public void getChannel(BaseAPI api, String user_id, String app_id){
		System.out.println("getChannel sql execute");
		String query =  String.format("SELECT * FROM  app_user_list WHERE user_id='%s' and app_id='%s'", user_id, app_id);
		
		abstractQuery.execute(query, new QueryCallback() {
			
			@Override
			public void success(SQLConnection con, JsonObject rs) {
				System.out.println("getChannel sql success");
				api.onExecute(getChannel, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				System.out.println("getChannel sql fail");
				JsonObject rs = new JsonObject();
				rs.put("result", "fail");
				rs.put("result_text", "DB query fail");
				api.onExecute(getChannel, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
		
	}
	
	public void getChannel(BaseAPI api, String channel_name, String user_id, String app_id){
		System.out.println("getChannel sql execute");
		String query =  String.format("SELECT * FROM  app_user_list WHERE user_id='%s' and app_id='%s'", user_id, app_id);
		
		abstractQuery.execute(query, new QueryCallback() {
			
			@Override
			public void success(SQLConnection con, JsonObject rs) {
				System.out.println("getChannel sql success");
				api.onExecute(getChannel, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				System.out.println("getChannel sql fail");
				JsonObject rs = new JsonObject();
				rs.put("result", "fail");
				rs.put("result_text", "DB query fail");
				api.onExecute(getChannel, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
		
	}
	
	public void getSession(BaseAPI api, String token){
		System.out.println("getSession sql execute");
		
		redis.get(token, new RedisCallback() {
			
			@Override
			public void success(JsonObject rs) {
				api.onExecute(getSession, rs);
			}
			
			@Override
			public void fail() {
				JsonObject rs = new JsonObject();
				rs.put("result", "fail");
				rs.put("result_text", "DB query fail");
				api.onExecute(getSession, rs);
			}
		});
		
//		String query = String.format("SELECT * FROM session WHERE token ='%s'", token);
//		abstractQuery.execute(query, new QueryCallback() {
//			
//			@Override
//			public void success(SQLConnection con, JsonObject rs) {
//				System.out.println("getSession sql success");
//				api.onExecute(getSession, rs);
//				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
//			}
//			
//			@Override
//			public void fail() {
//				System.out.println("getSession sql fail");
//				JsonObject rs = new JsonObject();
//				rs.put("result", "fail");
//				rs.put("result_text", "DB query fail");
//				api.onExecute(getSession, rs);
//				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
//			}
//		});
		
	}
	
	public void getUserId(BaseAPI api, String user_id){
		System.out.println("getUserId sql execute");
		String query = String.format("SELECT * FROM user WHERE user_id='%s'", user_id);
		abstractQuery.execute(query, new QueryCallback() {
			
			@Override
			public void success(SQLConnection con, JsonObject rs) {
				System.out.println("getUserId sql success");
				api.onExecute(getUserId, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				System.out.println("getUserId sql fail");
				JsonObject rs = new JsonObject();
				rs.put("result", "fail");
				rs.put("result_text", "DB query fail");
				api.onExecute(getUserId, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
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
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				JsonObject rs = new JsonObject();
				rs.put("result", "fail");
				rs.put("result_text", "DB query fail");
				api.onExecute(setUser, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
		});
	}
	
	public void getUser(BaseAPI api, String user_id, String user_pw, String gcm_id, String device_id){
		String query = String.format("SELECT * FROM user WHERE user_id='%s' and user_pw='%s'" ,user_id , user_pw);
		
		abstractQuery.execute(query, new QueryCallback(){
			
			@Override
			public void success(SQLConnection con, JsonObject rs) {
				api.onExecute(getUser, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}

			@Override
			public void fail() {
				JsonObject rs = new JsonObject();
				rs.put("result", "fail");
				rs.put("result_text", "DB query fail");
				api.onExecute(getUser, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
					
			}
		});
		
	}
	
	public void setSession(BaseAPI api, String token ){
		redis.set(token, Util.getUserId(token), new RedisCallback() {
			
			@Override
			public void success(JsonObject rs) {
				api.onExecute(getUser, rs);
				System.out.println(api.getClass().getName() + " callbackRedis : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				JsonObject rs = new JsonObject();
				rs.put("result", "fail");
				rs.put("result_text", "DB query fail");
				api.onExecute(getUser, rs);
				System.out.println(api.getClass().getName() + " callbackRedis : " + rs.toString() );				
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
	
	public void setApp(BaseAPI api, String app_id, String user_id){
		String query =  String.format("INSERT INTO app_user_list SET app_id='%s', user_id='%s'", app_id, user_id);
		abstractQuery.execute(query, new QueryCallback() {
		
			@Override
			public void success(SQLConnection con, JsonObject rs) {
				api.onExecute(setApp, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				JsonObject rs = new JsonObject();
				rs.put("result", "fail");
				rs.put("result_text", "DB query fail");
				api.onExecute(setApp, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
	}
	
	public void getApp(BaseAPI api, String user_id){
		String query =  String.format("SELECT * FROM  app_user_list WHERE user_id='%s'", user_id);
		abstractQuery.execute(query, new QueryCallback() {
		
			@Override
			public void success(SQLConnection con, JsonObject rs) {
				api.onExecute(getApp, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
			
			@Override
			public void fail() {
				JsonObject rs = new JsonObject();
				rs.put("result", "fail");
				rs.put("result_text", "DB query fail");
				api.onExecute(getApp, rs);
				System.out.println(api.getClass().getName() + " onExecute : " + rs.toString() );
			}
		});
	}
}
