package fr.aareon.openstreetmap;

/**
 * GPS coordinates : latitude and longitude.
 * @author tlassauniere
 *
 */
public class Coords {
	
	private float latitude;
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	private float longitude;
	
	public Coords(float latitude, float longitude) {
		super();
		setLatitude(latitude);
		setLongitude(longitude);
	}
	
	public Coords() {
	}
	
	public String toString() {
		return getLatitude() + "-" + getLongitude();
	}

}
