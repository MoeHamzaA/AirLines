/////////////////HEADER/////////////////
///Presentation Date: Jan 17 2023
///Partners: Shams, Hamza
///ISU | Air Hub Booking Agency Application

package Booking;


import java.text.SimpleDateFormat;
import java.util.*;

public class Ticket {

    // fields

   private ArrayList <Integer> seatNum; // arraylist could be better
    private int gateNum;
    private int refNum;
    private String departLoc;
    private String arriveLoc;
    private Calendar departTime; 
    private Calendar arriveTime; 
    private Calendar boardTime;


    static SimpleDateFormat format = new SimpleDateFormat (" MM/dd/yyy | kk:mm");;

    //  accessors

    public ArrayList<Integer> getSeatNum(){ // make a for loop that runs for the amount of passengers and add N amount to seat number to differiataaesaes the seats
        return seatNum;
    }

    public int getGateNum(){
        return gateNum;
    }

    public String getDepartLoc(){
        return departLoc;
    }

    public String getArriveLoc(){
        return arriveLoc;
    }

    public int getRefNum(){
        return refNum;
    }

    public Calendar getDepartTime(){
        return departTime;
    }

    public Calendar getArriveTime(){
        return arriveTime;
    }

    public Calendar getBoardTime(){
        return boardTime;
    }

    // mutators

    public void addSeatNum(){
        seatNum.add(((int)(199 * Math.random() ) + 1));
    }

    public void setDepartLoc(String d){
        departLoc = d;
    }

    public void setArriveLoc(String a){
        arriveLoc = a;
    }

    public void setGateNum(int num){
        gateNum = num;
    }

    public void setRefNum(int num){
        refNum = num;
    }

    public void setDepartTime(Calendar time){
        departTime = time;
    }

    public void setArriveTime(Calendar time){
        arriveTime = time;
    }

    public void setBoardTime(Calendar time){
        boardTime = time;
    }

    // constructors

    public Ticket(){
        int rand = ((int)(10 * Math.random() ) + 3);
        seatNum = new ArrayList<>();
        gateNum = (int)(24 * Math.random() ) + 1; // with this method passengers going to the same place will have dif gateNums (maybe every person gets the same ticket but only dif seat num and bookin ref)
        refNum = (int)(99999 * Math.random() ) + 10000;
        format.setTimeZone(TimeZone.getTimeZone("EST"));
        Calendar.getInstance(TimeZone.getTimeZone("EST"));
        departTime.add(Calendar.HOUR, rand);
        arriveTime.add(Calendar.HOUR, rand+1); // add actual arrival time in overload
        boardTime.add(Calendar.HOUR, rand-2); // same method for overload should be fine
    }

    public Ticket(Duration currentTime){
        seatNum = new ArrayList<>();
        gateNum = (int)(24 * Math.random() ) + 1; // with this method passengers going to the same place will have dif gateNums (maybe every person gets the same ticket but only dif seat num and bookin ref)
        refNum = (int)(99999 * Math.random() ) + 10000;

        format.setTimeZone(TimeZone.getTimeZone("EST"));

        departTime = Calendar.getInstance(TimeZone.getTimeZone("EST"));
        arriveTime = Calendar.getInstance(TimeZone.getTimeZone("EST"));
        boardTime = Calendar.getInstance(TimeZone.getTimeZone("EST"));

        departTime.add(Calendar.DATE, 1);
        arriveTime.add(Calendar.DATE, 1);
        boardTime.add(Calendar.DATE, 1);

        arriveTime.add((Calendar.HOUR_OF_DAY), (int) currentTime.getDuration()); // add actual arrival time in overload
        arriveTime.add(Calendar.MINUTE, (int)(((currentTime.getDuration() - ((int)(currentTime.getDuration())))) *100 * 0.60)); // add actual arrival time in overload
        boardTime.add(Calendar.HOUR, -1); // same method for overload should be fine
    }


    // possible to add a function in here like a for loop to print all the seats instead of printing one of these for each client
    public String toString(){
        int size = seatNum.size();
        String str = "Gate Number: " + gateNum + "\nDepart Location: " + getDepartLoc() + " Major Airport\nArrival Location: " + getArriveLoc() + " Major Airport\nReference Number: " + refNum + "\nBoarding Time: " + format.format(boardTime.getTime()) + " EST\nDeparture Time: " + format.format(departTime.getTime()) + " EST\nArrival Time: " + format.format(arriveTime.getTime()) + " EST\nSeat Number(s):";

        for(int i = 0; i < size; i++){
            str = str + " | " + seatNum.get(i);
        }
        return str;
    }
}
