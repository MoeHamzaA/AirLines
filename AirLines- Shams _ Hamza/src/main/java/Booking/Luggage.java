/////////////////HEADER/////////////////
///Presentation Date: Jan 17 2023
///Partners: Shams, Hamza
///ISU | Air Hub Booking Agency Application

package Booking;

import java.text.DecimalFormat; // decimal format import

public class Luggage {

  DecimalFormat df = new DecimalFormat("0.00");

  /////////// ATTRIBUTES

  private int bagID; // airlines usually have this for bags | might make an option to ask for a bag details or a bag search system if time given
  private double weight; // cost is determined by weight
  private String bagType; // cost is determined by bagtype asweLL

  /////////// CLASS STATIC VARIABLES

  private static final double COST_WEIGHT = 4.5; // can be changed later on depending on cost
  private static final int HAND_BAG = 10; // constant for hangbag cost
  private static final int SUIT_CASE = 32; // constant for suitcase cost
  private static final String BIG_SUIT = "Suit-Case";
  private static final String SMALL_SUIT = "Hand-bag";

  /////////// ACCESSOR

  public int getBagID() {
    return bagID;
  }

  public double getWeight() {
    return weight;
  }

  public String getBagType() {
    return bagType;
  }


  /////////// MUTATOR

  public void setBagID(int bag) {
    bagID = bag;
  }

  public void setWeight(double weight) {
    if (weight > 0 && weight <=32) {
      this.weight = weight;
    }else if (weight <= 0) {
      throw new IllegalArgumentException("Cannot have a weight of 0 or less");
    }else {
      throw new IllegalArgumentException("Cannot be greater than a weight of 32");
    }
  }

  public void setBagType() {
    if (weight > 10) {
      bagType = BIG_SUIT;
    }else if (weight <=10) {
      bagType = SMALL_SUIT;
    }
    else{
      throw new IllegalArgumentException("Cannot determine bag type");
    }
  }

  /////////// CONSTRUCTOR

  public Luggage() {
    weight = 7;
    bagID = (int) (899 * Math.random() + 100);
    bagType = SMALL_SUIT;
  }

  public Luggage(double weight) { 
    if (weight > 0 && weight <= 32) {
      this.weight = weight;
      bagID = (int) (89999 * Math.random() + 10000);
      if (weight > 10) {
        bagType = BIG_SUIT;
      }else if (weight <=10) {
        bagType = SMALL_SUIT;
      }
      else{
        throw new IllegalArgumentException("Cannot determine bag type");
      }
    }else if (weight <=0) {
      throw new IllegalArgumentException("Cannot have a weight of 0 or less");
    }else {
      throw new IllegalArgumentException("Cannot be greater than a weight of 32, create new bag");
    }
  }

  // instance


  public double bagCost(){
    double cost;
    if(bagType.equals("Suit-Case")){
      cost = SUIT_CASE + (getWeight()*COST_WEIGHT);
      return cost;
    }
    else{
      cost = HAND_BAG + (getWeight()*COST_WEIGHT);
      return cost;
    }
  }

  // to String

  public String toString() {
    return ("Bag ID: " + bagID + ", Bag Weight: " + weight + ", Bag Type: " + getBagType() + ", Cost (WITHOUT TAX): $" + df.format(bagCost()));
  }

}
