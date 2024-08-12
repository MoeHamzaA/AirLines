/////////////////HEADER/////////////////
///Presentation Date: Jan 17 2023
///Partners: Shams, Hamza
///ISU | Air Hub Booking Agency Application

package Booking;
import java.util.*;


public class Duration {
  private String flyFrom;
  private String flyTo;
  private static ArrayList<String> locations = new ArrayList<>(Arrays.asList("Toronto", "Quebec", "Manitoba", "Saskatchewan", "Alberta")); // province and city
  private final int AVG_FLIGHT_SPEED = 540; //km/h
  private final double PI180 = Math.PI / 180.0;
  HashMap<String, Integer> places = new HashMap<String, Integer>();

  double[][] airports = {{1, 43.6777, 79.6248},  //toronto
                         {2, 46.7907, 71.3886}, //quebec
                         {3, 49.9098, 97.2365}, //manitoba
                         {4, 52.1746, 106.7005}, //saskatchewan
                         {5, 53.3062, 113.5828} //alberta 
                        };    //lat     lon
  //  Constructor
  public Duration() {
    flyFrom = "unknown";
    flyTo = "unknown";
  }  

  //OverLoader
  public Duration(String flyFrom, String flyTo) {
    if (locations.contains(flyFrom) && locations.contains(flyTo))  {
      this.flyFrom = flyFrom;
      this.flyTo = flyTo;
    }else {
      throw new IllegalArgumentException("Set available places to fly from/to...");    
    }
  }

  // Setters
  public void setFlyFrom(String flyFrom) {
    if (locations.contains(flyFrom))  {
      this.flyFrom = flyFrom;
    }else {
      throw new IllegalArgumentException("Set available places to fly from...");    
    }
  }
  public void setFlyTo(String flyTo) {
    if (locations.contains(flyTo))  {
      this.flyTo = flyTo;
    }else {
      throw new IllegalArgumentException("Set available places to fly to...");    
    }
  }
  
  // Getters
  public String getFlyFrom() {
    return flyFrom;
  }

  public String getFlyTo() {
    return flyTo;
  }

  // Instances 
  public static void printFlyList() {
    for(String s: locations) {
      System.out.println("-"+s);
    }
  }
  public static ArrayList<String> getFlyList() {
    return locations;
  }

  //set values for the hashmap
  private HashMap<String, Integer> getHashMap() {
    places.put("Toronto", 0);
    places.put("Quebec", 1);
    places.put("Manitoba", 2);
    places.put("Saskatchewan", 3);
    places.put("Alberta", 4);
    return places;
  }

  //get distance using the 2d array
  public double getDistance() {
    return (calcDistance(airports[getHashMap().get(flyFrom)][1], 
                         airports[getHashMap().get(flyFrom)][2], 
                         airports[getHashMap().get(flyTo)][1], 
                         airports[getHashMap().get(flyTo)][2]
                        )
           );
  }
  //calculate the distance using lat and long equation
  private double calcDistance(double lat1, double lon1, double lat2, double lon2) {
    double a, b, c;
    a = Math.cos(lat1 * PI180) * Math.cos(lat2 *PI180) * Math.cos(lon1 * PI180) * Math.cos(lon2 * PI180);   
    b =  Math.cos(lat1 * PI180) * Math.sin(lon1 *PI180) * Math.cos(lat2 * PI180) * Math.sin(lon2 * PI180);
    c = Math.sin(lat1 * PI180) * Math.sin(lat2 * PI180);
    return ( Math.acos(a + b+ c) * 6370.0);
  }
  //get the duration using distance / speed
  public double getDuration() {
    return (getDistance() / AVG_FLIGHT_SPEED);
  }
  //format the duration
  public String formatDuration() { 
    return ((int)(getDuration()) + ":" + (int)((getDuration() - ((int)(getDuration()))) *100 * 0.60)); // use this for the arrival time
  }


  // toString
  public String toString() {
    return (formatDuration() +"");
  }
  
}
