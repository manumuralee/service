package com.assessment.projectmanagementservice.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class JsonDateDeSerializer extends JsonDeserializer<Date> {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public Date deserialize(JsonParser parser, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		String str = parser.getText().trim();
		try {
			if (str != null && !str.trim().equalsIgnoreCase("")) {
				return dateFormat.parse(str.trim());
			}
		} catch (Exception e) {
		}
		return null;
	}

}
