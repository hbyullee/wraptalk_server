package com.maestro.wraptalk.dao;



import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.sql.UpdateResult;


public abstract class QueryCallback {
	
	
	public void endEvent( SQLConnection con, ResultSet rs) 
	{
		JsonArray jsonArray =new JsonArray();
		
		for(int i = 0; i<rs.getNumRows(); i++){
			JsonObject jsonObject = new JsonObject();
			JsonArray ja = rs.getResults().get(i);
			
			int j = 0;
			for(String column : rs.getColumnNames()){
				if (ja.getValue(j) instanceof String) {
					jsonObject.put(column, ja.getString(j) );
				}else {
					jsonObject.put(column, ja.getLong(j) );
				}
				j++;
			}
			jsonArray.add(jsonObject);
		}
	
		JsonObject response = new JsonObject();
		response.put("results", jsonArray);
		System.out.println(rs.getResults());
		System.out.println(rs.getRows());
		success( con, response ) ;
		
	}
	
	public void endEvent( SQLConnection con, UpdateResult rs) 
	{
		
		success( con, rs.toJson() ) ;
	}
	
	public void endEvent( ) 
	{
		fail( ) ;
	}
	public abstract void success( SQLConnection con, JsonObject rs ) ;
	
	
	public abstract void fail( ) ;
}
