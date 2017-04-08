package in.zaidi.engineering.auctions.client;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import in.zaidi.engineering.auctions.api.Sale;

public class ItemDeserializer extends StdDeserializer<SalesSearchResponse> {

	private static final long serialVersionUID = 1L;

	public ItemDeserializer() {
		this(null);
	}

	public ItemDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public SalesSearchResponse deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonNode node = jp.getCodec().readTree(jp);
		JsonNode hits = node.get("hits");
		int count = hits.get("total").asInt();
		SalesSearchResponse response = new SalesSearchResponse();
		if (count > 0) {
			JsonNode innerHits = hits.get("hits");
			Iterator<JsonNode> iterator = innerHits.elements();
			while (iterator.hasNext()) {
				JsonNode element = iterator.next();
				String id = element.get("_id").asText();
				JsonNode source = element.get("_source");
				JsonNode doc = source.get("doc");
				Sale sale = new Sale();
				sale.setId(id);
				sale.setThumbnail(doc.get("thumbnail").asText());
				sale.setImageURL(doc.get("imageURL").asText());
				sale.setName(doc.get("name").asText());
				sale.setEndTime(new Date(doc.get("endTime").asLong()));
				response.addSale(sale);
			}
		}
		return response;
	}
}