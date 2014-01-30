package com.aridhu.gwt.pricing.server.mongo.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class MongoDateDeserializer extends JsonDeserializer<Date>{

	public MongoDateDeserializer() {
		
	}

	@Override
	public Date deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonNode tree = jp.readValueAsTree();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		try{
			return dateFormat.parse(tree.get("$date").textValue());
		}
		catch(Throwable t){ 
			throw new IOException(t.getMessage(),t);
		}
	}

}
