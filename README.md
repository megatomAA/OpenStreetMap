# OpenStreetMap
Geocoding + Generate Map from OpenStreetMap with JAVA

## Maven Build
mvn package

## Examples of use : JAVA

### Geocoding

```java
Geocoding geocoding = new Geocoding();
geocoding.setAddress("9 rue Jeanne Braconnier 92360 MEUDON FRANCE");
Coords[] coords = geocoding.request();
```

### StaticMap 

```java
StaticMap map = new StaticMap(); 
map.setWidth(800);
map.setHeight(600);
map.setZoom(16);
map.setCoord(new Coords(Float.parseFloat("48.7897645"), Float.parseFloat("2.2117242")));
map.setAddress("9 rue Jeanne Braconnier 92360 MEUDON");
map.setMaptype(StaticMap.MAP_TYPE_CYCLE);
map.setUseMapCache(false);
map.setUseTileCache(true);
map.setFileOutputPath("/tmp/map.png");
map.setApiKey("{ThunderForestApiKey}");
map.generate();
```

## Command Line Arguments

### StaticMap
<code>java -cp OpenStreeMap-{version}-jar-dependencies.jar fr.aareon.openstreetmap.StaticMap [-q address | -coord lat,lon] [-o outputpath] [-cache true/false] [-size 512x512] [-zoom 0-18] [-markers] [-maptype [cycle (default), transport, landscape, outdoors, transport-dark, spinal-map, pioneer, mobile-atlas, neighbourhood]] [-cleanCache]
</code>


