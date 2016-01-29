package fr.aareon.openstreetmap;

import org.json.simple.JSONObject;

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
	
	private String addressLabel = "";
	
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
    public String getAddressLabel() {
        return addressLabel;
    }
    public void setAddressLabel(String addressLabel) {
        this.addressLabel = addressLabel;
    }
    
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("display_name", getAddressLabel());
        json.put("lat", getLatitude());
        json.put("lon", getLongitude());
        return json;
    }

}
