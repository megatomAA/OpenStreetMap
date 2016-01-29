package fr.aareon.openstreetmap;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;

/**
 * Generate a map (PNG image) from an address or coordinates.
 * @author tlassauniere
 *
 */
public class StaticMap {
	
	/**
	 * Address
	 */
	private String address = "";

	/**
	 * Get the address
	 * @return String
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Set the address
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * Maximum width
	 */
	private int maxWidth = 1024;
	/**
	 * Maximum height
	 */
	private int maxHeight = 1024;
	/**
	 * Size for each tile
	 */
	private int tileSize = 256;
	/**
	 * Urls for getting tiles
	 */
	private HashMap<String, String> tileSrcUrl = new HashMap<String, String>();
	/**
	 * Markers prototypes
	 */
	private ArrayList<HashMap<String, String>> markerPrototypes = new ArrayList<HashMap<String, String>>();
	
	public static String MAP_TYPE_MAPNICK = "mapnik";
	public static String MAP_TYPE_OSM = "osmarenderer";
	public static String MAP_TYPE_CYCLE = "cycle";
	
	/**
	 * Default map type
	 */
	public static String tileDefaultSrc = "mapnik";
	
    protected int zoom = 16, width = 512, height = 512;
    protected double centerX, centerY, offsetX, offsetY;
    protected String maptype = tileDefaultSrc;
    protected String icon;
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    protected BufferedImage image;
    
    /**
     * Get the generated image after generation
     * @return
     */
    public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
    protected Coords coords;
    
    /**
     * Output path
     */
    private String fileOutputPath = "";
    
    private boolean useMapCache=true;

	public boolean isUseMapCache() {
		return useMapCache;
	}
	
	/**
	 * Specify to use or not cache
	 * @param useMapCache
	 */
	public void setUseMapCache(boolean useMapCache) {
		this.useMapCache = useMapCache;
	}

    private boolean useTileCache=true;
	public boolean isUseTileCache() {
		return useTileCache;
	}

	public void setUseTileCache(boolean useTileCache) {
		this.useTileCache = useTileCache;
	}

	private String mapCacheBaseDir="osm/cache/maps";
    private String tileCacheBaseDir="osm/cache/tiles";

    private String mapCacheID = "";
    private String mapCacheFile;
    private String mapCacheExtension = "png";
    
    /**
     * Get output file path
     * @return
     */
    public String getFileOutputPath() {
		return fileOutputPath;
	}
    
    /**
     * Path for writing image to output file
     * @param fileOutputPath
     */
	public void setFileOutputPath(String fileOutputPath) {
		this.fileOutputPath = fileOutputPath;
	}
	
	/**
	 * Get coordoinates corresponding to the address
	 * @return
	 */
	public Coords getCoord() {
		return coords;
	}

	/**
	 * Set the coordinates
	 * @param coords
	 */
	public void setCoord(Coords coords) {
		this.coords = coords;
	}

	private ArrayList<Marker> markers = new ArrayList<Marker>();
    
	/**
	 * Get maximum width size
	 * @return
	 */
	public int getMaxWidth() {
		return maxWidth;
	}

	/**
	 * Set maximum width size
	 * @return
	 */
	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}

	/**
	 * Get maximum width height
	 * @return
	 */
	public int getMaxHeight() {
		return maxHeight;
	}

	public int getZoom() {
		return zoom;
	}

	/**
	 * Set map zoom. From 1 (earth view) to 16 (street view)
	 * @param zoom
	 */
	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		if (width > maxWidth) {
			this.width = maxWidth;
		} else {
			this.width = width;
		}
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		if (height > maxHeight) {
			this.height = maxHeight;
		} else {
			this.height = height;
		}
	}

	public double getCenterX() {
		return centerX;
	}

	public void setCenterX(double centerX) {
		this.centerX = centerX;
	}

	public double getCenterY() {
		return centerY;
	}

	public void setCenterY(double centerY) {
		this.centerY = centerY;
	}

	public double getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(double offsetX) {
		this.offsetX = offsetX;
	}

	public double getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(double offsetY) {
		this.offsetY = offsetY;
	}

	public String getMaptype() {
		return maptype;
	}
	
	/**
	 * Set map type.
	 * @param maptype
	 */
	public void setMaptype(String maptype) {
		this.maptype = maptype;
	}

	/**
	 * Set maximum height size
	 * @return
	 */
	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}

	public int getTileSize() {
		return tileSize;
	}

	public void setTileSize(int tileSize) {
		this.tileSize = tileSize;
	}
	
	/**
	 * Add a marker to map
	 * @param marker
	 */
	public void addMarker(Marker marker) {
		markers.add(marker);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String address = "";
		String zoom = "16";
		String maptype = tileDefaultSrc;
		String size="512x512";
		String markers="";
		String output="";
        String icon="";
		String coord="";
		String cache="true";
		
		for (int i=0;i<args.length;i++) {
			if (args[i].equals("-q")) {
				address = args[++i];
			}
			if (args[i].equals("-zoom")) {
				zoom = args[++i];
			}
			if (args[i].equals("-maptype")) {
				maptype = args[++i];
			}
            if (args[i].equals("-icon")) {
                icon = args[++i];
            }
			if (args[i].equals("-size")) {
				size = args[++i];
			}
			if (args[i].equals("-markers")) {
				markers = args[++i];
			}
			if (args[i].equals("-o")  || args[i].equals("-output")) {
				output = args[++i];
			}
			if (args[i].equals("-coord")) {
				coord = args[++i];
			}
			if (args[i].equals("-cache")) {
				cache = args[++i];
			}
		}
		
		if (address.equals("") && coord.equals("")) {
			printUsage();
			return;
		}
		
		StaticMap map = new StaticMap();
		map.setAddress(address);
		map.setZoom(Integer.parseInt(zoom));
		map.setMaptype(maptype);
		map.setFileOutputPath(output);
		map.setUseMapCache(Boolean.parseBoolean(cache));
		map.setIcon(icon);
		
		if (!coord.equals("")) {
			String[] coords = coord.split(",");
			map.setCoord(new Coords(Float.parseFloat(coords[0]), Float.parseFloat(coords[1])));
		}
		
		String[] dimensions = size.split("x");
		map.setWidth(Integer.parseInt(dimensions[0]));
		map.setHeight(Integer.parseInt(dimensions[1]));
		
		if (!markers.equals("")) {
			String[] aMarkers = markers.split("\\|");
			for (int i=0;i<aMarkers.length;i++){
				String[] aMarker = aMarkers[i].split(",");
				
				Marker marker = new Marker();
				marker.setCoord(new Coords(Float.parseFloat(aMarker[0]), Float.parseFloat(aMarker[1])));
				marker.setType(aMarker[2]);
				marker.setLabel(aMarker[3]);
				map.addMarker(marker);
			}
		}
		map.generate();
	}
	
	public StaticMap() {
		initTileUrls();
		initMarkerPrototypes();
	}
	
	/**
	 * Prints usage in console
	 */
	public static void printUsage() {
		System.out.println("java -cp OpenStreeMap-{version}-jar-dependencies.jar fr.aareon.openstreetmap.StaticMap [-q address | -coord lat,lon] [-o outputpath] [-cache true/false] [-size 512x512] [-zoom 0-18] [-markers] [-maptype [mapnik,osmarenderer,cycle]]");
		System.out.println("    -q : address");
		System.out.println("    -coord : coordinates latitude,longitude");
		System.out.println("    -o : write map to path");
		System.out.println("    -cache : Use file cache. True or false");
		System.out.println("    -size : Map size. Ex : 512x1024");
		System.out.println("    -zoom : Map zoom. From 0 to 18");
		System.out.println("    -markers : Add markers. lat,lon,type,label|lat,lon,type,label|lat,lon,type,label");
		System.out.println("    -maptype : Map type. mapnik (default), cycle, osmarenderer");
	}
	
	/**
	 * Initializes tile's urls
	 */
	private void initTileUrls() {
		tileSrcUrl.put("mapnik", "http://tile.openstreetmap.org/{Z}/{X}/{Y}.png");
		tileSrcUrl.put("osmarenderer", "http://otile1.mqcdn.com/tiles/1.0.0/osm/{Z}/{X}/{Y}.png");
		tileSrcUrl.put("cycle", "http://a.tile.opencyclemap.org/cycle/{Z}/{X}/{Y}.png");
	}
	
	/**
	 * Initializes marker prototypes
	 */
	private void initMarkerPrototypes() {
		HashMap<String, String> prototype;
		
		prototype = new HashMap<String, String>();
		prototype.put("regex", "^lightblue([0-9]+)$");
		prototype.put("extension", ".png");
		prototype.put("shadow", "false");
		prototype.put("offsetImage", "0,-19");
		prototype.put("offsetShadow", "false");
		markerPrototypes.add(prototype);

		prototype = new HashMap<String, String>();
		prototype.put("regex", "^ol-marker(|-blue|-gold|-green)+$");
		prototype.put("extension", ".png");
		prototype.put("shadow", "marker_shadow.png");
		prototype.put("offsetImage", "-10,-25");
		prototype.put("offsetShadow", "-1,-13");
		markerPrototypes.add(prototype);

		prototype = new HashMap<String, String>();
		prototype.put("regex", "^(pink|purple|red|ltblu|ylw)-pushpin$");
		prototype.put("extension", ".png");
		prototype.put("shadow", "marker_shadow.png");
		prototype.put("offsetImage", "-10,-32");
		prototype.put("offsetShadow", "-1,-13");
		markerPrototypes.add(prototype);
		
		prototype = new HashMap<String, String>();
		prototype.put("regex", "^bullseye$");
		prototype.put("extension", ".png");
		prototype.put("shadow", "false");
		prototype.put("offsetImage", "-20,-20");
		prototype.put("offsetShadow", "false");
		markerPrototypes.add(prototype);
	}

	/**
	 * Generates Map with cache configuration
	 * @return Generated map
	 */
	public BufferedImage generate() {
		initCoords();
		if (!isUseMapCache()) {
			generateMap();
		} else {
			if (!checkMapCache()) {
				generateMap();
				cacheMap();
			} else {
				System.out.println("Retrieve map from cache");
				try {
	                image = ImageIO.read(new File(mapCacheIDToFilename()));
                } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
                }
			}
		}
		if(!fileOutputPath.equals("")) {
			writeToDisk();
		}
		return image;
	}
	
	/**
	 * Generates Map without cache
	 * @return BufferedImage PNG image
	 */
	public BufferedImage generateMap() {
		if (coords != null) {
			Marker marker = new Marker();
			marker.setCoord(coords);
			marker.setLabel(address);
			if (getIcon() != null) {
			    marker.setType(getIcon());
			} else {
	            marker.setType(Marker.CONST_TYPE_PURPLEPUSHPIN);
			}
			addMarker(marker);
			createBaseMap();
			placeMarkers();
			return image;
		} else {
			System.out.println("Adresse introuvable");
			return null;
		}
	}
	
	/**
	 * Writes image to disk
	 */
	private void writeToDisk() {
        try {
	        ImageIO.write(image, "png", new File(getFileOutputPath()));
        } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	}
	
	/**
	 * Initializes coordinates. IF none, try to get coordinates from address using Geocoding.
	 */
	private void initCoords() {
		if (getCoord() == null) {
			Geocoding geocoding = new Geocoding();
			geocoding.setAddress(address);
			
			Coords[] coords = geocoding.request();
			if (coords != null) {
	            setCoord(coords[0]);
		        centerX = lonToTile(getCoord().getLongitude(), zoom);
		        centerY = latToTile(getCoord().getLatitude(), zoom);
		        offsetX = Math.floor((Math.floor(centerX) - centerX) * tileSize);
		        offsetY = Math.floor((Math.floor(centerY) - centerY) * tileSize);
			}
		} else {
		    // calc position from coords
            centerX = lonToTile(getCoord().getLongitude(), zoom);
            centerY = latToTile(getCoord().getLatitude(), zoom);
            offsetX = Math.floor((Math.floor(centerX) - centerX) * tileSize);
            offsetY = Math.floor((Math.floor(centerY) - centerY) * tileSize);
		}
	}
	
    private double lonToTile(double lon, int zoom)
    {
        return ((lon + 180) / 360) * Math.pow(2, zoom);
    }

    private double latToTile(double lat, int zoom)
    {
        return (1 - Math.log(Math.tan(lat * Math.PI / 180) + 1 / Math.cos(lat * Math.PI / 180)) / Math.PI) / 2 * Math.pow(2, zoom);
    }
	
    /**
     * Fetches a tile from http's url
     * @param url
     * @return Image
     */
	private BufferedImage fetchTile(String url) {
		File f = null;				
		if (useTileCache) {
			f = checkTileCache(url);
		}
        try {
			if (f != null) {
				System.out.println("Retrieve tile from cache");
				return ImageIO.read(f);
			} else {
				URL obj = new URL(url);
		        BufferedImage image = ImageIO.read(obj);
		        if (useTileCache) {
		        	writeTileToCache(url, image);
		        }
		        return image;
			}
        } catch (IOException e) {
	        // TODO Auto-generated catch block
        	System.out.println("Error for url "+url);
	        e.printStackTrace();
        }
        return null;
	}

	/**
	 * Creates base map Image and concatenates all tiles in one Image.
	 */
    private void createBaseMap()
    {
    	image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    	Graphics gdr = image.getGraphics();
        double startX = Math.floor(centerX - (width / tileSize) / 2);
        double startY = Math.floor(centerY - (height / tileSize) / 2);
        double endX = Math.ceil(centerX + (width / tileSize) / 2);
        double endY = Math.ceil(centerY + (height / tileSize) / 2);
        offsetX = -Math.floor((centerX - Math.floor(centerX)) * tileSize);
        offsetY = -Math.floor((centerY - Math.floor(centerY)) * tileSize);
        offsetX += Math.floor(width / 2);
        offsetY += Math.floor(height / 2);
        offsetX += Math.floor(startX - Math.floor(centerX)) * tileSize;
        offsetY += Math.floor(startY - Math.floor(centerY)) * tileSize;
        BufferedImage tileData;
        for (double x = startX; x <= endX; x++) {
            for (double y = startY; y <= endY; y++) {
            	String url = tileSrcUrl.get(maptype);
            	
                url = url.replace("{Z}", Integer.toString(zoom));
                url = url.replace("{X}", String.format("%s",(int)x));
                url = url.replace("{Y}", String.format("%s",(int)y));
                
                tileData = fetchTile(url);
                // TODO check errors
                double destX = (x - startX) * tileSize + offsetX;
                double destY = (y - startY) * tileSize + offsetY;
                //place tile in map
                gdr.drawImage(tileData, (int)destX, (int)destY, null);
            }
        }
    }
    
    /**
     * Place markers in map
     */
    private void placeMarkers()
    {
    	Graphics gdr = image.getGraphics();
    	Iterator<Marker> it = markers.iterator();
    	String[] offsetMarker = {"0", "-19"};
    	String[] offsetMarkerShadow = {"0", "0"};
    	String shadow = "false", extension = ".png";
    	Boolean isResource = false;
    	while(it.hasNext()) {
    	    isResource = false;
    		Marker marker = it.next();
    		String type = marker.getType();
    		
    		Iterator<HashMap<String, String>> imp = markerPrototypes.iterator();
    		while(imp.hasNext()) {
    			HashMap<String, String> markerPrototype = imp.next();    			
    			if (type.matches(markerPrototype.get("regex"))) {
    				offsetMarker = markerPrototype.get("offsetImage").split(",");
    				shadow = markerPrototype.get("shadow");
    				extension = markerPrototype.get("extension");
    				if (!shadow.equals("false")) {
    					offsetMarkerShadow = markerPrototype.get("offsetShadow").split(",");
    				}
    				isResource = true;
    			}
    		}
    		if (!isResource) {
    		    // not an internal resource (not matched by regexp), try to load as URL
    		    offsetMarker = getImageSize(type);
    		    shadow = "false";
    		    isResource = false;
    		}
    		
    		//check if resource image exists
    		InputStream is;
    		if (isResource) {
    		    is = StaticMap.class.getResourceAsStream("resources/images/markers/"+type+extension);
    		} else {
    		    try {
                    is = new URL(type).openStream();
                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    continue;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    continue;
                }
    		}
    		try {
	            BufferedImage imgmarker = ImageIO.read(is);
	            
	            // calc position
	            double destX = Math.floor((width / 2) - tileSize * (centerX - lonToTile(marker.getCoord().getLongitude(), zoom)));
	            double destY = Math.floor((height / 2) - tileSize * (centerY - latToTile(marker.getCoord().getLatitude(), zoom)));
	            
	            gdr.drawImage(imgmarker, (int)destX + Integer.parseInt(offsetMarker[0]), (int)destY + Integer.parseInt(offsetMarker[1]), null);
	            //Graphics2D gO = image.createGraphics();
	            gdr.setColor(Color.blue);
	            gdr.setFont(new Font( "Arial", Font.BOLD, 10 ));
	            gdr.drawString(marker.getLabel(), (int)destX + 20, (int)destY - 10);
	    		
	    		if (!shadow.equals("false")) {
	    			is = StaticMap.class.getResourceAsStream("resources/images/"+shadow);
	    			try {
	    	            BufferedImage imgshadow = ImageIO.read(is);
		    			gdr.drawImage(imgshadow, (int)destX + Integer.parseInt(offsetMarkerShadow[0]), (int)destY + Integer.parseInt(offsetMarkerShadow[1]), null);
	    			} catch (IOException e) {
	    	            // TODO Auto-generated catch block
	    	            e.printStackTrace();
	                }
	    		}
            } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
            }
    	}
    }
    
    /**
     * Checks if map is stored on disk
     * @return true if exists
     */
    private boolean checkMapCache() {
        String mapCacheIdSerialize = serializeParams();
    	MessageDigest md;
        try {
	        md = MessageDigest.getInstance("MD5");
	        BigInteger bi = new BigInteger(1, md.digest(mapCacheIdSerialize.getBytes()));
	        mapCacheID = bi.toString(16);
        } catch (NoSuchAlgorithmException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	        return false;
        }
    	
        String filename = mapCacheIDToFilename();
        File f = new File(filename);
        return f.exists();
    }

    private String serializeParams()
    {
    	String[] params = {
    			String.valueOf(zoom), String.valueOf(coords.getLatitude()), String.valueOf(coords.getLongitude()), String.valueOf(width), String.valueOf(height), markers.toString(), maptype
    	};
    	return StringUtils.join(params, "&");
    }

    private String mapCacheIDToFilename()
    {
        if (mapCacheFile == null) {
            mapCacheFile = System.getProperty("java.io.tmpdir") + "/" + mapCacheBaseDir + "/" + maptype + "/" + 
            		String.valueOf(zoom) + "/cache_" + mapCacheID.substring(0, 2) + "/" + 
            		mapCacheID.substring(2, 4) + "/" + mapCacheID.substring(4) + "." + mapCacheExtension;
        }
        return mapCacheFile;
    }
    
    /**
     * Copy map in cache.
     */
    private void cacheMap() {
    	String cacheFileName = mapCacheIDToFilename();
    	File f = new File(cacheFileName);
    	//File dir = new File(cacheFileName.substring(0, cacheFileName.lastIndexOf("/")));
    	f.getParentFile().mkdirs();
    	try {
	        ImageIO.write(image, "png", f);
        } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
    }


    private String tileUrlToFilename(String url) {
        return System.getProperty("java.io.tmpdir") + "/" + tileCacheBaseDir + "/" + url.replace("http://", "");
    }

    private File checkTileCache(String url) {
        String filename = tileUrlToFilename(url);
        File f = new File(filename);
        if (f.exists()) {
            return f;
        }
        return null;
    }
    
    private void writeTileToCache(String url, BufferedImage tile) {
        String filename = tileUrlToFilename(url);
        
        File f = new File(filename);
        f.getParentFile().mkdirs();
        try {
	        ImageIO.write(tile, "png", f);
        } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
    }
    
    private String[] getImageSize(String path) {
        URL url;
        try {
            url = new URL(path);
            final BufferedImage bi = ImageIO.read(url);
            return new String[]{"-"+bi.getWidth(), "-"+bi.getHeight()};
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        return null;
    }
}
