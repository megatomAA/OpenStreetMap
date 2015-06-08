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
