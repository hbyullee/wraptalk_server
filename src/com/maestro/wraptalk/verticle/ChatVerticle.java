package com.maestro.wraptalk.verticle;

import com.maestro.wraptalk.utils.Config;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.sockjs.BridgeEvent;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

public class ChatVerticle extends AbstractVerticle {

	
	EventBus eb;
	String permissions[] = {
            "to.client.BroadcastNewsfeed",
            "to.server.channel"
    };
    int count;
    @Override
    public void start() throws Exception {

        Router router = Router.router(vertx);

        addSockJS(router);
        addCustomEvent();
		eb = vertx.eventBus();
		// 서버에서 공용 이벤트버스로 채팅 메시지를 받고 이벤트 버스로 전달된 채널ID와 메시지 내용으로 해당 이벤트버스를 이용해 전달하면된다 ?
		
		eb.consumer("to.server.channel", new Handler<Message<String>>() {
	            @Override
	            public void handle(Message<String> objectMessage) {
	            	JsonObject jsonObject = new JsonObject(objectMessage.body());
	                vertx.eventBus().publish("to.channel."+jsonObject.getString("channel_id"), jsonObject.getString("msg"));
	            }
	    });
//        vertx.setPeriodic(1000, t -> vertx.eventBus().publish("to.client.BroadcastNewsfeed", "news from the server! " + (count++)));
    }

    // only for addSockJS method
    private BridgeOptions createOptions() {
    	Config.bridgeOptions = new BridgeOptions();
        for(String permission : permissions) {
            if(permission.startsWith("to.client"))
            	Config.bridgeOptions.addOutboundPermitted(new PermittedOptions().setAddress(permission));
            else if(permission.startsWith("to.server"))
            	Config.bridgeOptions.addInboundPermitted(new PermittedOptions().setAddress(permission));
            else
                System.out.println(permission+" is wrong permission!!");
        }
        return Config.bridgeOptions;
    }

    
    //이건 클라이언트와 이벤트 버스를 공유하기 위한 부분이다. 클라이언트와 서버사이의 Bridge를 만드는 것! /eventbus 로 request가 오는 것은
    //SockJS로 받아 클라이언트와의 브릿지를 두어 이벤트 버스를 공유하게끔 한다.
    //그건 이벤트버스로 메시지를 보내는 것임을 명시하고 있다. 이벤트버스의 데이터 형태로와서 해당 주소를 찾아간다!
    private void addSockJS(Router router) {
        router.route("/eventbus/*").handler(SockJSHandler.create(vertx).bridge(createOptions(), event -> {

            String uuid = event.socket().writeHandlerID();
            if (event.type() == BridgeEvent.Type.SOCKET_CREATED) {
                String host = event.socket().remoteAddress().host();
                System.out.println(host+" connected");
            }

            event.complete(true);

        }));
    }

    private void addCustomEvent()
    {
        EventBus eb = vertx.eventBus();

        // 타 버티클에서 접속자에게 실시간 뉴스피드 전송  // 다른 버티클에서 이버티클에 메시지를 보내는데 to.server.BroadcastNewsfeed 이통로를 이용해서 보내면
        // 그것을 클라이언트에게 pub해버린다.
        eb.consumer("to.server.BroadcastNewsfeed", new Handler<Message<String>>() {
            @Override
            public void handle(Message<String> objectMessage) {
                vertx.eventBus().publish("to.client.BroadcastNewsfeed", objectMessage.body());
            }
        });

        // 유저가 서버로 뉴스피드 요청
        eb.consumer("to.server.RequestNewsfeed").handler(msg -> {
            msg.reply("test");
        });
    }
	
	
	
}
