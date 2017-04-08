package in.zaidi.auctions.repo;

import java.util.HashMap;
import java.util.Map;

import in.zaidi.engineering.auctions.api.Coordinates;


public interface CoordinatesResource {

	default Coordinates getCoordinates(String city){
		return indiaOfficesCoordinates.get(city);
	}
	
	
	final Map<String,Coordinates> indiaOfficesCoordinates = buildCoordinatesMap();
	static Map<String,Coordinates> buildCoordinatesMap()
	{
		Map<String,Coordinates> cityCoordinates = new HashMap<>();
		cityCoordinates.put("Bengaluru", new Coordinates(12.98f,77.69f));
		cityCoordinates.put("Gurgaon", new Coordinates(25.45f,77.02f));
		cityCoordinates.put("Noida", new Coordinates(25.52f,77.39f));
		return cityCoordinates;
	}
}
