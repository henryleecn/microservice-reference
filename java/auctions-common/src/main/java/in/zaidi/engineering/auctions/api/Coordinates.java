package in.zaidi.engineering.auctions.api;

public class Coordinates{
	public Coordinates(){
		super();
	}
	
	public Coordinates(float lat, float lon) {
		super();
		this.lat = lat;
		this.lon = lon;
	}
	private float lat;
	private float lon;
	public float getLat() {
		return lat;
	}
	public void setLat(float lat) {
		this.lat = lat;
	}
	public float getLon() {
		return lon;
	}
	public void setLon(float lon) {
		this.lon = lon;
	}
	
	
}