package in.zaidi.engineering.common.couchbase;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.couchbase.client.deps.com.fasterxml.jackson.core.JsonParseException;
import com.couchbase.client.deps.com.fasterxml.jackson.core.JsonProcessingException;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.JsonMappingException;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.view.ViewQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Interface CouchbaseRepository.
 */
@SuppressWarnings("unchecked")
public interface CouchbaseRepository<T> {

	String ID_GENERATION_DOCUMENT = "idGeneration";
	String FIELD_ID = "id";

	static final Logger LOGGER = LoggerFactory.getLogger(CouchbaseRepository.class);

	/**
	 * Gets the bucket.
	 *
	 * @return the bucket
	 */
	Bucket getBucket();

	JsonMapper getJsonMapper();

	default int getMaxRetry() {
		return 0;
	}

	/**
	 * Save.
	 *
	 * @param <T>
	 *            the generic type
	 * @param obj
	 *            the obj
	 * @return the t
	 * @throws JsonProcessingException
	 *             the json processing exception
	 * @throws Exception
	 *             the exception
	 */
	default T save(T obj) {
		final JsonObject jsonObject = getJsonMapper().toJsonObject(obj);

		boolean idGenerated = false;
		if (jsonObject.getString(FIELD_ID) == null || jsonObject.getString(FIELD_ID).length() == 0) {
			jsonObject.put(FIELD_ID, generateNextId(jsonObject));
			idGenerated = true;
		}

		final JsonDocument docSaved = insertRetryOnClash(jsonObject, idGenerated, getMaxRetry());

		return getJsonMapper().fromJsonDocument(docSaved, (Class<T>) obj.getClass());
	}

	default String generateNextId(JsonObject jsonObject) {
		String typeId = jsonObject.getString(JsonMapper.TYPE_IDENTIFIER_CLASS);
		if (typeId != null && !typeId.trim().isEmpty()) {
			// Get id prefix from type
			if (typeId.contains(".")) {
				typeId = typeId.substring(typeId.lastIndexOf('.')+1);
			}
		}
		return String.valueOf(typeId+getBucket().counter(ID_GENERATION_DOCUMENT, 1, 0).content());
	}

	default JsonDocument insertRetryOnClash(final JsonObject jsonObject, boolean idGenerated, int maxRetry) {
		JsonDocument docSaved;
		while (true) {
			try {
				JsonDocument docToSave = JsonDocument.create(jsonObject.getString(FIELD_ID), jsonObject);
				docSaved = getBucket().insert(docToSave);
				break;
			} catch (com.couchbase.client.java.error.DocumentAlreadyExistsException idClash) {
				if (idGenerated && maxRetry > 0) {
					// regenerate Id to avoid clash
					maxRetry--;
					jsonObject.put(FIELD_ID, generateNextId(jsonObject));
				} else {
					if (idGenerated) {
						throw new RuntimeException("Failed to generate a unique Id for the document", idClash);
					} else {
						throw idClash;
					}
				}
			}
		}
		return docSaved;
	}

	/**
	 * Find by id.
	 *
	 * @param <T>
	 *            the generic type
	 * @param id
	 *            the id
	 * @return the t
	 * @throws JsonParseException
	 *             the json parse exception
	 * @throws JsonMappingException
	 *             the json mapping exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	default T findById(String id) {
		return getJsonMapper().fromJsonDocument(getBucket().get(id));
	}

	/**
	 * Gets the using view.
	 *
	 * @param <T>
	 *            the generic type
	 * @param designDoc
	 *            the design doc
	 * @param viewname
	 *            the viewname
	 * @param param
	 *            the param
	 * @param reduce
	 *            the reduce
	 * @param sort
	 *            the sort
	 * @return the using view
	 * @throws JsonParseException
	 *             the json parse exception
	 * @throws JsonMappingException
	 *             the json mapping exception
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	default List<T> getUsingView(String designDoc, String viewname, String param, boolean reduce, boolean sort) {
		final ViewQuery query = ViewQuery.from(designDoc, viewname);
		if (param != null && param.length() != 0) {
			query.key(param);
		}
		query.reduce(reduce);
		List<JsonDocument> documentResults = getBucket().query(query.debug()).allRows().stream()
				.map(view -> view.document()).collect(Collectors.toList());
		if (documentResults.isEmpty()) {
			return Collections.emptyList();
		} else {
			Stream<T> stream = documentResults.stream().map(document -> getJsonMapper().fromJsonDocument(document));
			if (sort) {
				stream = stream.sorted();
			}
			return stream.collect(Collectors.toList());
		}
	}

	default N1qlQueryResult getUsingN1ql(String query) {

		N1qlQueryResult queryResult = this.getBucket().query(N1qlQuery.simple(query));

		if (!queryResult.finalSuccess()) {
			LOGGER.info("Query returned with errors: " + queryResult.errors());
			// throw new DataRetrievalFailureException("Query error: " +
			// queryResult.errors());
		}
		return queryResult;

	}
	
	default N1qlQueryResult getMaxBidUsingN1ql(String queryString, List<String> saleIds) {
		
		JsonObject placeholderValues  = JsonObject.create().put("saleIds", saleIds);
		
		N1qlQuery query = N1qlQuery.parameterized(queryString, placeholderValues);
		
		LOGGER.info("N1ql query: " + query.n1ql());
		
		LOGGER.info("N1ql query params: " + query.params().toString());

		N1qlQueryResult queryResult = this.getBucket().query(query);
		
		LOGGER.info("N1ql query result: " + queryResult.finalSuccess());
		
		if (!queryResult.finalSuccess()) {
			LOGGER.info("Query returned with errors: " + queryResult.errors());
			// throw new DataRetrievalFailureException("Query error: " +
			// queryResult.errors());
		}
		return queryResult;

	}
}