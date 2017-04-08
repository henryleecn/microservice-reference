package in.zaidi.engineering.common.couchbase;

import java.io.IOException;

import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.transcoder.JsonTranscoder;

public interface JsonMapper {

	String TYPE_IDENTIFIER_CLASS = "_class";

	JsonTranscoder getJsonTranscoder();

	ObjectMapper getObjectMapper();

	default <T> JsonObject toJsonObject(T obj) {
		JsonObject jsonObject;
		try {
			jsonObject = getJsonTranscoder().stringToJsonObject(getObjectMapper().writeValueAsString(obj));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		String className = jsonObject.getString(TYPE_IDENTIFIER_CLASS);
		if (className == null) {
			jsonObject.put(TYPE_IDENTIFIER_CLASS, obj.getClass().getCanonicalName());
		}
		return jsonObject;
	}

	default <T> T fromJsonDocument(JsonDocument docSaved, Class<T> clazz) {
		try {
			return getObjectMapper().readValue(docSaved.content().toString(), clazz);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	default <T> T fromJsonDocument(JsonDocument document) {
		if (document == null) {
			return null;
		}
		final String className = document.content().getString(TYPE_IDENTIFIER_CLASS);
		Class<T> clazz;
		try {
			clazz = (Class<T>) Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new UnsupportedOperationException(e);
		}
		try {
			T obj = getObjectMapper().readValue(document.content().toString(), clazz);
			return obj;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
