package fr.aareon.openstreetmap;

/**
 * A marker to be added on the map.
 * @author tlassauniere
 *
 */
public class Marker {

	private Coords coords;
	private String type = "lightblue1";
	private String label = "";	

	public static String CONST_TYPE_BULLSEYE = "bullseye";
	
	public static String CONST_TYPE_LIGHTBLUE1 = "lightblue1";
	public static String CONST_TYPE_LIGHTBLUE2 = "lightblue2";
	public static String CONST_TYPE_LIGHTBLUE3 = "lightblue3";
	public static String CONST_TYPE_LIGHTBLUE4 = "lightblue4";
	public static String CONST_TYPE_LIGHTBLUE5 = "lightblue5";

	public static String CONST_TYPE_BLUEPUSHPIN = "ltblu-pushpin";
	public static String CONST_TYPE_PINKPUSHPIN = "pink-pushpin";
	public static String CONST_TYPE_PURPLEPUSHPIN = "purple-pushpin";
	public static String CONST_TYPE_REDPUSHPIN = "red-pushpin";
	public static String CONST_TYPE_YELLOWPUSHPIN = "ylw-pushpin";
	
	public static String CONST_TYPE_OLBLUE = "ol-marker-blue";
	public static String CONST_TYPE_OLGOLD = "ol-marker-gold";
	public static String CONST_TYPE_OLGREEN = "ol-marker-green";
	public static String CONST_TYPE_OL = "ol-marker";
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Coords getCoord() {
		return coords;
	}
	public void setCoord(Coords coords) {
		this.coords = coords;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
