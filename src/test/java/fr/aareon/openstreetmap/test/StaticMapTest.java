package fr.aareon.openstreetmap.test;

import java.io.File;

import fr.aareon.openstreetmap.Coords;
import fr.aareon.openstreetmap.Marker;
import fr.aareon.openstreetmap.StaticMap;
import junit.framework.TestCase;

public class StaticMapTest extends TestCase {

    public void test_createMap() {
        String mapFilePath = "src/test/java/map.png";
        File f = new File(mapFilePath);
        if (f.exists()) {
            f.delete();
        }
        
        StaticMap map = new StaticMap();
        map.setWidth(800);
        map.setHeight(600);
        map.setZoom(16);
        map.setCoord(new Coords(Float.parseFloat("48.7897645"), Float.parseFloat("2.2117242")));
        map.setAddress("9 rue Jeanne Braconnier 92360 MEUDON");
        map.setMaptype(StaticMap.MAP_TYPE_MAPNICK);
        map.setUseMapCache(false);
        map.setUseTileCache(true);
        map.setFileOutputPath(mapFilePath);
        map.generate();
        
        assertTrue(f.exists());
        assertTrue(f.delete());
    }
    
    public void test_createMapWithMarkerLocal() {
        String mapFilePath = "src/test/java/mapWithMarkerLocal.png";
        File f = new File(mapFilePath);
        if (f.exists()) {
            f.delete();
        }
        
        StaticMap map = new StaticMap();
        map.setWidth(800);
        map.setHeight(600);
        map.setZoom(16);
        map.setCoord(new Coords(Float.parseFloat("48.7897645"), Float.parseFloat("2.2117242")));
        map.setAddress("9 rue Jeanne Braconnier 92360 MEUDON");
        map.setMaptype(StaticMap.MAP_TYPE_MAPNICK);
        map.setUseMapCache(false);
        map.setUseTileCache(true);
        map.setFileOutputPath(mapFilePath);
        
        Marker marker = new Marker();
        marker.setCoord(new Coords((float) 48.7896, (float)2.212));
        marker.setType(Marker.CONST_TYPE_BLUEPUSHPIN);
        marker.setLabel("Test Marker");
        map.addMarker(marker);
        
        map.generate();
        
        assertTrue(f.exists());
        assertTrue(f.delete());
    }

    public void test_createMapWithMarkerUrl() {
        String mapFilePath = "src/test/java/mapWithMarkerUrl.png";
        File f = new File(mapFilePath);
        if (f.exists()) {
            f.delete();
        }
        
        StaticMap map = new StaticMap();
        map.setWidth(800);
        map.setHeight(600);
        map.setZoom(16);
        map.setCoord(new Coords(Float.parseFloat("48.7892521"), Float.parseFloat("2.2118679")));
        map.setAddress("Aareon France Ancienne Adresse");
        map.setMaptype(StaticMap.MAP_TYPE_MAPNICK);
        map.setUseMapCache(false);
        map.setUseTileCache(true);
        map.setFileOutputPath(mapFilePath);
        
        Marker marker = new Marker();
        marker.setCoord(new Coords((float) 48.7897645, (float)2.2117242));
        marker.setType("http://lv3im5:7780/syloimages/puce_aareon.png");
        marker.setLabel("Aareon France Nouvelle Adresse");
        map.addMarker(marker);
        
        map.generate();
        
        assertTrue(f.exists());
        assertTrue(f.delete());
    }   

    public void test_createMapWithSpecificIcon() {
        String mapFilePath = "src/test/java/mapWithSpecificIcon.png";
        File f = new File(mapFilePath);
        if (f.exists()) {
            f.delete();
        }
        
        StaticMap map = new StaticMap();
        map.setWidth(800);
        map.setHeight(600);
        map.setZoom(16);
        map.setCoord(new Coords(Float.parseFloat("48.7892521"), Float.parseFloat("2.2118679")));
        map.setAddress("Aareon France Ancienne Adresse");
        map.setIcon("http://lv3im5:7780/syloimages/puce_aareon.png");
        map.setMaptype(StaticMap.MAP_TYPE_MAPNICK);
        map.setUseMapCache(false);
        map.setUseTileCache(true);
        map.setFileOutputPath(mapFilePath);
        
        Marker marker = new Marker();
        marker.setCoord(new Coords((float) 48.7897645, (float)2.2117242));
        marker.setType("http://lv3im5:7780/syloimages/puce_aareon.png");
        marker.setLabel("Aareon France Nouvelle Adresse");
        map.addMarker(marker);
        
        map.generate();
        
        assertTrue(f.exists());
        assertTrue(f.delete());
    } 
    
}
