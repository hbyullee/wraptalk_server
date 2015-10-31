package com.maestro.wraptalk.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.lang.reflect.Method;

import com.maestro.wraptalk.dao.AbstractQuery;
import com.maestro.wraptalk.dao.Redis;
import com.maestro.wraptalk.dao.WrapDAO;
import com.maestro.wraptalk.utils.Util;

public class RestVerticle extends AbstractVerticle  {
    
	
	AbstractQuery abstractQuery;
	Redis redis;
	
    Object reqClasses[][] = new Object[][]{
    		{"/user/login", com.maestro.wraptalk.api.LoginAPI.class, "POST"},
    		{"/user/join", com.maestro.wraptalk.api.JoinAPI.class, "POST"},
    		{"/user/registApp", com.maestro.wraptalk.api.RegistAppAPI.class, "GET"},
    		{"/user/listApp", com.maestro.wraptalk.api.ListAppAPI.class, "GET"},
    		{"/user/makeChannel", com.maestro.wraptalk.api.MakeChannelAPI.class, "GET"},
    		{"/user/listChannel", com.maestro.wraptalk.api.ListChannelAPI.class, "GET"},
    		{"/user/registNick", com.maestro.wraptalk.api.RegistNickAPI.class, "GET"},
    		{"/user/joinChannel", com.maestro.wraptalk.api.JoinChannelAPI.class, "GET"},
    		{"/user/withdrawChannel", com.maestro.wraptalk.api.WithdrawChannelAPI.class, "GET"},
    		{"/user/getGlobalConfig", com.maestro.wraptalk.api.GetGlobalConfigAPI.class, "GET"},
    		{"/user/setGlobalConfig", com.maestro.wraptalk.api.SetGlobalConfigAPI.class, "GET"},
    		{"/user/searchChannel", com.maestro.wraptalk.api.SearchChannelAPI.class, "GET"},
    		{"/user/setAppConfig", com.maestro.wraptalk.api.SetAppConfigAPI.class, "GET"},
    		{"/user/getAppConfig", com.maestro.wraptalk.api.GetAppConfigAPI.class, "GET"},
    		{"/user/getNoticeList", com.maestro.wraptalk.api.GetNoticeListAPI.class, "GET"},
    		{"/user/changeChannelSet", com.maestro.wraptalk.api.ChangeChannelSetAPI.class, "GET"},
    		{"/user/customQuery", com.maestro.wraptalk.api.CustomQueryAPI.class, "GET"}
    		
    };
    
    
 
	@Override
	public void start() throws Exception {
		super.start();
		
	
		redis = new Redis(vertx);
		
		abstractQuery = new AbstractQuery(vertx, null);
		WrapDAO.getInstance().init(abstractQuery, redis);
		
		Router router = Router.router(vertx);
		Route route = router.route("/user/*");
		
		route.handler(new Handler<RoutingContext>(){

			@Override
			public void handle(RoutingContext routingContext) {
				
				HttpServerRequest request = routingContext.request();
				MultiMap params = request.params();
				
				String uri = request.uri();
				String path = request.path();
				String query = request.query();
				JsonObject param = new JsonObject();
				params.forEach(entry -> param.put(entry.getKey(), entry.getValue()));
				
				Util.logDebug(request.method().name());
				Util.logDebug("uri :" + uri);
				Util.logDebug("path :" + path);
				Util.logDebug("query :" + query);
				Util.logDebug("paramters to json :" + param.toString());
				
				request.endHandler(new Handler<Void>() {
					
					@Override
					public void handle(Void empty) {
						
						for(int i=0; i<reqClasses.length; i++) {
							if(path.equals(reqClasses[i][0])) {
//							if(path.equals(reqClasses[i][0])) {
								
								try {
									
									Class cls = (Class)reqClasses[i][1];
									Object object = cls.newInstance();
									Util.logDebug(object.getClass().getName() + " execute !!");
									Class[] paramTypes = { HttpServerRequest.class };
									Method apiMethod = cls.getDeclaredMethod("execute", paramTypes);
									request.response().putHeader("content-type", "application/json");
									request.response().putHeader("Access-Control-Allow-Origin", "*" );
									apiMethod.invoke(object, request);  
									break;
									
								} catch (Exception e) {
									request.response().end("error : "+e.getMessage());
									e.printStackTrace();
									break;
								} 
							}
							if(i==reqClasses.length -1){
								request.response().end("error : "+ "not exist api");
								break;
							}
							
						}
					}
				});
			}
			
		});
		
		HttpServerOptions httpServerOptions = new HttpServerOptions();
		httpServerOptions.setCompressionSupported(true);

		vertx.createHttpServer(httpServerOptions)
				.requestHandler(router::accept)
				.listen(7010);
		
	}

	
	
	@Override
	public void stop() throws Exception {

	}

}