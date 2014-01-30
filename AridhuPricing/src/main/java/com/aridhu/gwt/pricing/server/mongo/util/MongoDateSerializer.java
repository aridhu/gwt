package com.aridhu.gwt.pricing.server.mongo.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class MongoDateSerializer extends StdSerializer<Date> {

	public MongoDateSerializer() {
		super(Date.class);
	}

	@Override
	public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonGenerationException {
		jgen.writeStartObject();
		serializeContents(value,jgen,provider);
		jgen.writeEndObject();
		
	}
	
	private void serializeContents(Date value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonGenerationException {
		jgen.writeFieldName("$date");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		jgen.writeString(dateFormat.format(value));
	}

}
