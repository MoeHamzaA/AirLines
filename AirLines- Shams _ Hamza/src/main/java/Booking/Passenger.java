/////////////////HEADER/////////////////
///Presentation Date: Jan 17 2023
///Partners: Shams, Hamza
///ISU | Air Hub Booking Agency Application

package Booking;

import java.util.ArrayList; // ArrayList import
import java.text.DecimalFormat; // decimal format import

public class Passenger {

DecimalFormat df = new DecimalFormat("0.00");

  // attributes
  private String passport;
  private String ticket;
  private String firstName;
  private String lastName;

  // ArrayList for bags
  private ArrayList<Luggage> bags;


  // accessors

  public String getPassport() {
    
    return passport;
  }

  public String getTicket() {
    return ticket;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public ArrayList<Luggage> getBags() {
    return bags;
  }

  public double getBagWeight(int index) {
    return bags.get(index).getWeight();
  }


  // mutators
  public void setPassport(String passport) { // type passport
    this.passport = passport;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setBagWeight(int index, double w) {
    bags.get(index).setWeight(w);
  }

  // constuctors
  public Passenger() {
    passport = "unknown";
    ticket = ((char) ((int) (Math.random() * (90 - 65 + 1)) + 65) + ""
        + (char) ((int) (Math.random() * (90 - 65 + 1)) + 65) + "-"
        + ((int) (Math.random() * (99999 - 10000 + 1)) + 10000));
    firstName = "unknown";
    lastName = "unknown";
    bags = new ArrayList<Luggage>();
  }

  public Passenger(String firstName, String lastName, String passport) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.passport = passport;
    bags = new ArrayList<Luggage>();
    ticket = ((char) ((int) (Math.random() * (90 - 65 + 1)) + 65) + ""
            + (char) ((int) (Math.random() * (90 - 65 + 1)) + 65) + "-"
            + ((int) (Math.random() * (99999 - 10000 + 1)) + 10000));
  }

  // instance

  public void addBag(double weight){
    bags.add(new Luggage(weight));
  }

  public int amountOfBags(){ // keep track of the amount of bags made
    return bags.size();
  }

  public String toString() {
    return ("First Name: " + firstName + "\nLast Name: " + lastName + "\nPassport: " + passport + "\nTicket: "
        + ticket + "\nLuggage: " + amountOfBags() + "\nCost of Bag(s): $" + df.format(costOfBags()));
  }

  public double costOfBags(){ // find the total cost of all the bags | will be adding a method for cost of one bag aswell

    double totalCost = 0;
    for(int i = 0; i < amountOfBags(); i++){
      bags.get(i).setBagType();
    }

    for(int q = 0; q < amountOfBags(); q++){
      totalCost+=bags.get(q).bagCost();
    }
    return totalCost;
  }
  
}