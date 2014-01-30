package com.aridhu.gwt.pricing.server.mongo.util;

import java.io.IOException;
import org.bson.types.ObjectId;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class MongoObjectIDSerializer extends JsonSerializer<ObjectId>{
	
	public MongoObjectIDSerializer() {
		super();
	}

	@Override
	public void serialize(ObjectId value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		jgen.writeStartObject();
		serializeContents(value,jgen,provider);
		jgen.writeEndObject();
	}
	
	private void serializeContents(ObjectId value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonGenerationException {
		jgen.writeFieldName("$oid");
		jgen.writeString(value.toString());
	}

}
