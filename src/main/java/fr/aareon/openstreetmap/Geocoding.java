package fr.aareon.openstreetmap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Get coordinates for an address from HTTP.
 * @author tlassauniere
 *
 */
public class Geocoding {
	
	private String address;
	
	/**
	 * Get the address
	 * @return address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Set the address
	 * @param address the address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Get HTTP response format
	 * @return format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * Set HTTP response format
	 * @param format format
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	private String format = "json";
	
	private String url = "http://nominatim.openstreetmap.org/search?q=${adress}&format=${format}";
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Geocoding() {
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String address = "";
		String format = "json";
		
		for (int i=0;i<args.length;i++) {
			if (args[i].equals("-q")) {
				address = args[++i];
			}
			if (args[i].equals("-f")) {
				format = args[++i];
			}
		}
		
		if (address.equals("")) {
			System.out.println("Paramètre -q absent");
			return;
		}
		
		Geocoding geocoding = new Geocoding();
		geocoding.setAddress(address);
		geocoding.setFormat(format);
		Coords[] coords = geocoding.request();
		if (coords.length > 0) {
			System.out.println("Coordonnees trouvees : ");
			for (int i=0;i<coords.length;i++) {
			    System.out.println("{"+coords[i].getLatitude()+":"+coords[i].getLongitude()+"}");
			}
		}
	}
	
	/**
	 * Request coordinates for an address
	 * @return Found coordinates or null.
	 */
	public Coords[] request() {
	    Coords[] coords;
		String url = getUrl();
		try {
			url = url.replace("${adress}", URLEncoder.encode(getAddress(), "UTF-8"));
			url = url.replace("${format}", getFormat());
			
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	 
			// optional default is GET
			con.setRequestMethod("GET");
	 
			//add request header
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
	 
			int responseCode = con.getResponseCode();
			if (responseCode == 200) {
	 
				BufferedReader in = new BufferedReader(
				        new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
		 
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
		 
				//print result
				//System.out.println(response.toString());
				
				JSONParser parser = new JSONParser();
				JSONArray json = (JSONArray) parser.parse(response.toString());
				if (json.size() == 0) {
				    return null;
				}
				
				coords = new Coords[json.size()];
				for (int i=0;i<json.size();i++) {
				
    				JSONObject jsonObj = (JSONObject) json.get(0);
    				
    				Coords coord = new Coords();
    				coord.setLatitude(Float.valueOf(jsonObj.get("lat").toString()));
    				coord.setLongitude(Float.valueOf(jsonObj.get("lon").toString()));
                    coord.setAddressLabel(jsonObj.get("display_name").toString());
    				
    				coords[i] = coord;
				}
				return coords;
				
			} else {
				return null;
			}
        } catch (MalformedURLException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        } catch (ParseException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
		return null;
	}
	
	
}
