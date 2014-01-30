package com.aridhu.gwt.pricing.server.mongo.util;

import java.io.IOException;


import org.bson.types.ObjectId;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class MongoObjectIDDeserializer extends JsonDeserializer<ObjectId>{

	public MongoObjectIDDeserializer() {
		super();
	}

	@Override
	public ObjectId deserialize(JsonParser jp, DeserializationContext ctxt )
			throws IOException, JsonProcessingException {
		JsonNode tree = jp.readValueAsTree();
		try{
			String val=tree.get("$oid").textValue();
			ObjectId valobj= new ObjectId(val);
			return valobj;
		}
		catch(Throwable t){ 
			throw new IOException(t.getMessage(),t);
		}

	}



}
