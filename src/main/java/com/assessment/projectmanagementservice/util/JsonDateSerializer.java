/**
 * 
 */
package com.assessment.projectmanagementservice.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author Admin
 *
 */
public class JsonDateSerializer extends JsonSerializer<Date> {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
		if (date != null) {
			String formattedDate = dateFormat.format(date);
			jsonGenerator.writeString(formattedDate);
		}
	}

}
