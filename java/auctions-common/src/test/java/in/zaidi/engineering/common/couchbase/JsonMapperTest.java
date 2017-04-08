package in.zaidi.engineering.common.couchbase;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;

import com.couchbase.client.deps.com.fasterxml.jackson.core.JsonParseException;
import com.couchbase.client.deps.com.fasterxml.jackson.core.JsonProcessingException;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.JsonMappingException;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.transcoder.JsonTranscoder;

import in.zaidi.engineering.common.couchbase.JsonMapper;

public class JsonMapperTest implements JsonMapper {

	JsonMapper subject;
	ComplexTestClass testObject;
	ObjectMapper objectMapper;
	JsonTranscoder jsonTranscoder;
	JsonObject testJsonObject;
	JsonDocument jsonDocument;

	@Before
	public void setUp() throws Exception {
		testJsonObject = mock(JsonObject.class);
		objectMapper = mock(ObjectMapper.class);
		jsonTranscoder = mock(JsonTranscoder.class);
		jsonDocument = mock(JsonDocument.class);
		subject = this;
		testObject = new ComplexTestClass();
		testObject.setId("id");
		testObject.setName("name");
		Map<Long, String> map = new HashMap<>();
		map.put(Long.valueOf(1), "1");
		map.put(Long.valueOf(2), "2");
		testObject.setMap(map);
		List<String> list = new ArrayList<>();
		list.add("1");
		list.add("2");
		testObject.setList(list);
		ComplexTestClass child = new ComplexTestClass();
		child.setName("child");
		child.setId("childId");
		child.setMap(map);
		testObject.setChild(child);
		child.setList(list);
	}

	@Test
	public final void testToJsonObject() throws Exception {
		when(objectMapper.writeValueAsString(testObject)).thenReturn("intermediate");
		when(jsonTranscoder.stringToJsonObject("intermediate")).thenReturn(testJsonObject);
		when(testJsonObject.get(TYPE_IDENTIFIER_CLASS)).thenReturn(testObject.getClass().getCanonicalName());
		
		
		subject.toJsonObject(testObject);
				
		Mockito.verify(jsonTranscoder, new Times(1)).stringToJsonObject("intermediate");
		Mockito.verify(testJsonObject, new Times(0)).get(TYPE_IDENTIFIER_CLASS);
	}

	@Test
	public final void testToJsonObjectSetClass() throws Exception {
		when(objectMapper.writeValueAsString(testObject)).thenReturn("intermediate");
		when(jsonTranscoder.stringToJsonObject("intermediate")).thenReturn(testJsonObject);
		when(testJsonObject.get(TYPE_IDENTIFIER_CLASS)).thenReturn(null);
		when(testJsonObject.put(TYPE_IDENTIFIER_CLASS, testObject.getClass().getCanonicalName()))
				.thenReturn(testJsonObject);
		subject.toJsonObject(testObject);
		Mockito.verify(testJsonObject).put(TYPE_IDENTIFIER_CLASS, testObject.getClass().getCanonicalName());
	}

	@Test(expected = RuntimeException.class)
	public final void testToJsonObjectException() throws Exception {
		when(objectMapper.writeValueAsString(testObject)).thenReturn("intermediate");
		when(jsonTranscoder.stringToJsonObject("intermediate")).thenThrow(Exception.class);
		subject.toJsonObject(testObject);
		fail("No exception thrown");
	}

	@Test(expected = RuntimeException.class)
	public final void testToJsonObjectJsonException() throws Exception {
		when(objectMapper.writeValueAsString(testObject)).thenThrow(JsonProcessingException.class);
		subject.toJsonObject(testObject);
		fail("No exception thrown");
	}

	@Test
	public final void testFromJsonDocumentJsonDocumentClassOfT()
			throws JsonParseException, JsonMappingException, IOException {
		when(jsonDocument.content()).thenReturn(testJsonObject);
		when(testJsonObject.toString()).thenReturn("content");
		when(objectMapper.readValue("content", ComplexTestClass.class)).thenReturn(testObject);
		subject.fromJsonDocument(jsonDocument, jsonDocument.getClass());
		Mockito.verify(jsonDocument, new Times(1)).content();
	}

	@Test(expected = RuntimeException.class)
	public final void testFromJsonDocumentJsonDocumentClassOfTThrows()
			throws JsonParseException, JsonMappingException, IOException {
		when(jsonDocument.content()).thenReturn(testJsonObject);
		when(testJsonObject.toString()).thenReturn("content");
		when(objectMapper.readValue("content", jsonDocument.getClass())).thenThrow(JsonMappingException.class);
		subject.fromJsonDocument(jsonDocument, jsonDocument.getClass());
		fail("No exception thrown");
	}

	@Test
	public final void testFromJsonDocumentJsonDocument() throws JsonParseException, JsonMappingException, IOException {
		when(jsonDocument.content()).thenReturn(testJsonObject);
		when(testJsonObject.getString(TYPE_IDENTIFIER_CLASS)).thenReturn(String.class.getCanonicalName());
		when(objectMapper.readValue("content", String.class)).thenReturn("test");
		subject.fromJsonDocument(jsonDocument);
		Mockito.verify(jsonDocument, new Times(2)).content();
	}

	@Override
	public JsonTranscoder getJsonTranscoder() {
		return jsonTranscoder;
	}

	@Override
	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	public class ComplexTestClass {
		private String id;
		private String name;
		private Map map;
		private List list;
		private ComplexTestClass child;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Map getMap() {
			return map;
		}

		public void setMap(Map map) {
			this.map = map;
		}

		public List getList() {
			return list;
		}

		public void setList(List list) {
			this.list = list;
		}

		public ComplexTestClass getChild() {
			return child;
		}

		public void setChild(ComplexTestClass child) {
			this.child = child;
		}
	}

}
