# OpenStreetMap
Geocoding + Generate Static Map (PNG) from OpenStreetMap with JAVA

For Map Generation, you have to register to ThunderForst and obtain an API Key. See : [https://www.thunderforest.com/docs/apikeys/](https://www.thunderforest.com/docs/apikeys/)

## Maven Build
mvn package

## Examples of use : JAVA

### Geocoding

```java
Geocoding geocoding = new Geocoding();
geocoding.setAddress("9 rue Jeanne Braconnier 92360 MEUDON FRANCE");
Coords[] coords = geocoding.request();
for (Coords coord : coords) {
    float lon = coord.getLongitude();
    float lat = coord.getLatitude();
}
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
BufferedImage img = map.generate();
```

## Command Line Arguments

### StaticMap
<code>java -cp OpenStreeMap-{version}-jar-dependencies.jar fr.aareon.openstreetmap.StaticMap (-q address | -coord lat,lon) -o outputpath [-cache true/false] [-size 512x512] [-zoom 0-18] [-markers] [-maptype [cycle (default), transport, landscape, outdoors, transport-dark, spinal-map, pioneer, mobile-atlas, neighbourhood]] [-cleanCache]
</code>

### Available arguments
- **-q** - string representation of the address which is the center of the map. Geocoding will be used to get coordinates for this address. Example : 9 rue Jeanne Braconnier 92360 MEUDON FRANCE
- **-coord** - string representation of GPS coordinates (latitude,longitude) for the center of the map. Example : 2.2117242,48.7897645
- **-o** - path to write the generated image. 
- **-cache** - if true, use cache for maps and tiles. Default is false
- **-size** - string representation of image size ({widthInPixels}x{heightInPixels}). Default is 512x512
- **-zoom** - numeric value for zoom. (0 to 18). Default is 16.
- **-maptype** - the name of the map style from list : cycle (default), transport, landscape, outdoors, transport-dark, spinal-map, pioneer, mobile-atlas, neighbourhood. See [Thunderforest Maps Examples](https://www.thunderforest.com/maps/)
- **-cleanCache** - if true, clean cache folder before generating map. Default is false
- **-markers** - string representation of a list of markers, pipe separated. Format is : lat,lon,type,label|lat,lon,type,label|lat,lon,type,label.
  - **lat** - latitude, float
  - **lon** - longitude, float
  - **type** - string representation of marker icon, from list : bullseye, lightblue1, lightblue2, lightblue3, lightblue4, lightblue5, ltblu-pushpin, pink-pushpin, purple-pushpin, red-pushpin, ylw-pushpin, ol-marker-blue, ol-marker-gold, ol-marker-green, ol-marker
  - **label** - label for the marker (label, address, description...)
 
