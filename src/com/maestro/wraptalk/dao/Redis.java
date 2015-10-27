package com.maestro.wraptalk.dao;


import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.RedisClient;

public class Redis {

	RedisClient redisClient;
	
	public Redis(Vertx vertx){
		
		JsonObject config = new JsonObject();
		config.put("host", "127.0.0.1");
		redisClient = RedisClient.create(vertx, config);

	}
	
	
//	public void append(String key, String appendedValue, redisCallback queryCallback){
//		
//		redisClient.append(key, appendedValue, new Handler<AsyncResult<Long>>() {
//
//			@Override
//			public void handle(AsyncResult<Long> res) {
//				
//				if(res.succeeded()){
//					queryCallback.endEvent(res.result());
//				}else{
//					queryCallback.endEvent(res.result());
//				}
//				
//			}
//			
//		});
//	}
	
	public void get(String key, RedisCallback redisCallback){
		
		redisClient.get(key, new Handler<AsyncResult<String>>() {
			
			@Override
			public void handle(AsyncResult<String> res) {
				
				if(res.succeeded()){
					redisCallback.endSuccess(res.result());
				}else{
					redisCallback.endFail(res.result());
				}
				
			}
		});
	}

	public void set(String key, String value, RedisCallback redisCallback){
		
		redisClient.set(key, value, new Handler<AsyncResult<Void>>() {

			@Override
			public void handle(AsyncResult<Void> res) {

				if(res.succeeded()){
					redisCallback.endSuccess();
				}else{
					redisCallback.endFail();
				}
				
			}
		});
	}
	
}
