package in.zaidi.engineering.auctions.repository;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;

import in.zaidi.engineering.auctions.api.Sale;
import in.zaidi.engineering.common.couchbase.BaseCouchbaseRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import org.springframework.dao.DataRetrievalFailureException;

@Repository
public class SaleWithBidRepository extends BaseCouchbaseRepository<Sale> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SaleWithBidRepository.class);

	/**
	 * Instantiates a new bid repository.
	 *
	 * @param bucket
	 *            the bucket
	 */
	@Autowired
	public SaleWithBidRepository(@Qualifier("bucket") Bucket bucket) {
		super(bucket);
	}

	public Map<String,Double> getSaleMaxBidUsingN1QL(String userId, List<String> saleIds){
		
		
		String whereCondition = "where ";
		N1qlQueryResult queryResult;
		Map<String,Double> maxBidForSale = new HashMap<String,Double>();
		
		whereCondition += "bkt2.saleId in $saleIds ";
		
		if(userId != null)
			whereCondition += "and bkt1.createdBy = \"" + userId + "\" ";
		
		whereCondition += "group by bkt2.saleId having count(bkt2.saleId) > 0";
					
		String query = "select bkt2.saleId as id, max(bkt2.amount) as maxBidAmount  from microservices bkt2 join microservices bkt1 on keys bkt2.saleId " + whereCondition;
		
		queryResult = getMaxBidUsingN1ql(query, saleIds);	
		
		for (N1qlQueryRow row : queryResult) {
			 LOGGER.info("row value: " + row.value().toString());
			 JsonObject saleWithMaxBidKV = row.value();
			 maxBidForSale.put(saleWithMaxBidKV.getString("id"), saleWithMaxBidKV.getDouble("maxBidAmount"));
	        }
		
		return maxBidForSale;
	}
	
	public Map<String,Double> getAllSaleMaxBidUsingN1QL(String param){
		
		String whereCondition;
		N1qlQueryResult queryResult;
		Map<String,Double> maxBidForSale = new HashMap<String,Double>();
		
		if(param == null)
			whereCondition = "where bkt2.saleId = bkt1.id group by bkt2.saleId having count(bkt2.saleId) > 0";
		else
			whereCondition = "where bkt2.saleId = bkt1.id and bkt1.createdBy = \"" + param + "\"group by bkt2.saleId having count(bkt2.saleId) > 0";
			
		String query = "select bkt2.saleId as id, max(bkt2.amount) as maxBidAmount  from microservices bkt2 join microservices bkt1 on keys bkt2.saleId " + whereCondition;
		
		LOGGER.debug("query: " + query);
		
		queryResult = getUsingN1ql(query);	
		
		for (N1qlQueryRow row : queryResult) {
			 //LOGGER.info("row value: " + row.value().toString());
			 JsonObject saleWithMaxBidKV = row.value();
			 maxBidForSale.put(saleWithMaxBidKV.getString("id"), saleWithMaxBidKV.getDouble("maxBidAmount"));
	        }
		
		return maxBidForSale;
	}
	

}
