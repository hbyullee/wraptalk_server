package com.maestro.wraptalk.dao;


import com.maestro.wraptalk.dao.QueryCallback;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.sql.UpdateResult;

/**
 * Created by cwell on 2015-10-12.
 */
 public class AbstractQuery {
    JDBCClient client;
    Message<String> retMessage;
    boolean autoConClose = true;

	
	public void dbInit(Vertx vertx){
    	
    	JsonObject config = new JsonObject()
        .put("url", "jdbc:mysql://localhost:3306/wraptalk?useUnicode=true&amp;characterEncoding=utf-8&amp;autoReconnect=true&amp;zeroDateTimeBehavior=convertToNull")
        .put("user", "root")
        .put("password", "1234567890")
        .put("max_pool_size", 30);
		client = JDBCClient.createShared(vertx, config);
		   
	}
	
    public AbstractQuery(Vertx vertx, Message<String> retMessage) {
    	
        this.retMessage = retMessage;
        dbInit(vertx);
    }

    public AbstractQuery(Vertx vertx, Message<String> retMessage, boolean autoConClose) {
        
    	this.retMessage = retMessage;
        this.autoConClose = autoConClose;
        dbInit(vertx);
    }

    void fail(SQLConnection con, String msg){
        System.out.println("Query Failed: "+msg);
    };

    void reply(String msg) {
        if(retMessage == null) return;
        retMessage.reply(msg);
    }

    private void close(SQLConnection con) {
        if(con == null) return;

        con.close(done -> {
            if (done.failed()) {
                throw new RuntimeException(done.cause());
            }
        });
    }

    public void execute(String query, QueryCallback queryCallback ) {
       
    	client.getConnection(res -> {
            if (res.succeeded()) {
            	System.out.println("db connection success");
                SQLConnection con = res.result();
                if(query.indexOf("INSERT")>-1||query.indexOf("UPDATE")>-1||query.indexOf("DELETE")>-1){
                	con.update(query, res2 -> {
	                    if (res2.succeeded()) {
	                    	System.out.println("db query success");
	                        UpdateResult rs = res2.result();
	                        
//	                        if(retMessage != null) 
//	                        if(queryCallback != null)
	                        	queryCallback.endEvent(con, rs);
	                    }
	                    else {
	                        fail(con, "Failed to get ResultSet "+res2.cause().getMessage());
//	                        if(queryCallback != null)
	                        	queryCallback.endEvent();
	                           }
	                    if(autoConClose)
	                        close(con);
	                });
                }else{
                	con.query(query, res2 -> {
	                    if (res2.succeeded()) {
	                    	System.out.println("db query success");
	                        ResultSet rs = res2.result();
	                        
//	                        if(retMessage != null) 
//	                        if(queryCallback != null)
	                        	queryCallback.endEvent(con, rs);
	                    }
	                    else {
	                    	
	                        fail(con, "Failed to get ResultSet "+res2.cause().getMessage());
//	                        if(queryCallback != null)
	                        	queryCallback.endEvent();
	                           }
	                    if(autoConClose)
	                        close(con);
	                });
                }
            } else {
                // Failed to get connection - deal with it
                fail(null, "Failed to get Connection "+res.cause().getMessage());
//                if(queryCallback != null)
                queryCallback.endEvent();
            }
        });
    }
    
    public void execute2(String query, QueryCallback queryCallback ) {
        
    	client.getConnection(res -> {
            if (res.succeeded()) {
            	System.out.println("db connection success");
                SQLConnection con = res.result();
                if(query.indexOf("SELECT")>-1||query.indexOf("select")>-1){
                	con.query(query, res2 -> {
	                    if (res2.succeeded()) {
	                    	System.out.println("db query success");
	                        ResultSet rs = res2.result();
	                        
//	                        if(retMessage != null) 
//	                        if(queryCallback != null)
	                        	queryCallback.customEvent(con, rs);
	                    }
	                    else {
	                    	
	                        fail(con, "Failed to get ResultSet "+res2.cause().getMessage());
//	                        if(queryCallback != null)
	                        	queryCallback.endEvent();
	                           }
	                    if(autoConClose)
	                        close(con);
	                });
                }else{
                	con.update(query, res2 -> {
	                    if (res2.succeeded()) {
	                    	System.out.println("db query success");
	                        UpdateResult rs = res2.result();
	                        
//	                        if(retMessage != null) 
//	                        if(queryCallback != null)
	                        	queryCallback.customEvent(con, rs);
	                    }
	                    else {
	                        fail(con, "Failed to get ResultSet "+res2.cause().getMessage());
//	                        if(queryCallback != null)
	                        	queryCallback.endEvent();
	                           }
	                    if(autoConClose)
	                        close(con);
	                });
                	
                }
            } else {
                // Failed to get connection - deal with it
                fail(null, "Failed to get Connection "+res.cause().getMessage());
//                if(queryCallback != null)
                queryCallback.endEvent();
            }
        });
    }
   
}
