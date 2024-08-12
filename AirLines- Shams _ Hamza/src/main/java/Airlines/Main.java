/////////////////HEADER/////////////////
///Presentation Date: Jan 17 2023
///Partners: Shams, Hamza
///ISU | Air Hub Booking Agency Application

package Airlines;

/////////////IMPORTS///////////////

// import for sound/music function
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

//import for email
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import javax.mail.MessagingException;

//imports for try catch, scanner, decimal format
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.Pattern;

//import for our classes in the Booking package
import Booking.*;

// import for Login UI
import LoginUI.Login;

@SpringBootApplication
public class Main {
	@Autowired
	private EmailSenderService emailSenderService;

	public static void main(String[] args) {

		///////////// VARIABLES DECLARED////////////

		int passCount = -1;
		char choice = 'n', sendEmailConfirm;
		String emailAcc = "", initials = "";
		String flyTo, flyFrom, firstName, lastName, passport, ticketNum = "";
		double bagWeight, flightPrice = 0, totalPrice = 0;
		Ticket ticket;
		Duration duration;
		boolean passportCheck;

		// Scanner, DecimalFormat and Login declare and initialized
		Scanner scan = new Scanner(System.in);
		DecimalFormat df = new DecimalFormat("0.00");
		Login loginGui = new Login();
		loginGui.createGUi();

		// keep track of info for the list of passengers registered using ArrayList
		ArrayList<Passenger> passengers = new ArrayList<Passenger>();

		// calls on method which begins the music
		PlayMusic();

		// company name/slogan & current date & song+author
		printInfo();
		emailAcc = getEmailLogin(emailAcc);

		///////////// CODE//////////////

		// print flyList Locations

		System.out.println("Fly List:\n");
		Duration.printFlyList();
		System.out.println("-".repeat(15));

		// user input for where they wanna fly and where they are flying from
		System.out.print("Where are you flying from?: ");
		flyFrom = validateLocations(scan);

		System.out.print("Where would you like to fly to?: ");
		do {
			flyTo = validateLocations(scan);
			if (flyTo.equals(flyFrom)) {
				System.out.print("Input a new location to fly to: ");
			}
		} while (flyTo.equals(flyFrom));

		// instantiate duration and ticket
		duration = new Duration(flyFrom, flyTo);
		ticket = new Ticket(duration);
		ticket.setDepartLoc(flyFrom);
		ticket.setArriveLoc(flyTo);

		// do-while if the user wants to keep adding more passengers
		do {

			// keep track of passengers added
			passCount++;

			System.out.println(("-".repeat(15)) + "\nPassenger #" + (passCount + 1) + " Info:");

			// do-while to check if first name and last name are the same (might not need
			// this ex. john john)
			do {
				System.out.print("Enter first name: ");
				firstName = validateName(scan);
				System.out.print("Enter last Name: ");
				lastName = validateName(scan);
				if (firstName.equalsIgnoreCase(lastName)) {
					System.out.println("Names must be different");
				}
			} while (firstName.equalsIgnoreCase(lastName));
			initials = firstName.charAt(0) + "" + lastName.charAt(0);

			// validates passport format so all passports follow a certain pattern
			System.out.print("Enter passport ID (Initials Followed by 6 Digits): "); // format should be AA###### or AA(any
																																								// other characters)?
			do {
				passport = scan.nextLine();
				if ((Pattern.matches("[a-zA-Z]+", passport.substring(0, 2))) && (passport.length() == 8)
						&& (passport.substring(0, 2).equalsIgnoreCase(initials))) { // update pattern so it includes initials
					passportCheck = true;
				} else {
					System.out.print("Enter a valid passport: ");
					passportCheck = false;
				}
			} while (!passportCheck);

			// once all information is given by the user we can add them to the passengers
			// ArrayList and instantiate on the same line
			passengers.add(new Passenger(firstName, lastName, passport));

			// do-while to see if the user wants to add more bags
			do {
				try {
					System.out.print("Enter Weight of Bag (KG): ");
					bagWeight = scan.nextDouble();

					// adds bag for the passenger at index (passCount)
					passengers.get(passCount).addBag(bagWeight);
					System.out.print(("-".repeat(25)) + "\nWould you like to add another bag? (y/n): ");
					do {
						choice = scan.next().charAt(0);
						if (!(choice == 'y' || choice == 'n')) {
							System.out.print("Input y or n: ");
						}
					}while (!(choice == 'y' || choice == 'n'));
				} catch (IllegalArgumentException e) {
					System.out.println(e);
					choice = 'y';
				} catch (InputMismatchException e) {
					System.out.print("Invalid data type\n");
					choice = 'y';
					scan.next();
				}
			} while (choice != 'n');

			// adds the total price of all the bags for all passengers added
			totalPrice += passengers.get(passCount).costOfBags();

			// adds ticket numbers on a list for receipt
			ticketNum += (passengers.get(passCount).getTicket() + ", ");

			// end of do-while for the user adding more users
			System.out.print("Would you like to add another passenger? (y/n): ");
			
			do {
				choice = scan.next().charAt(0);
				if (!(choice == 'y' || choice == 'n')) {
					System.out.print("Input y or n: ");
				}
			}while (!(choice == 'y' || choice == 'n'));
			scan.nextLine();
		} while (choice != 'n');

		// prints passenger information && adds seat numbers to ticket
		for (int i = 0; i < passengers.size(); i++) {
			System.out.println("-".repeat(30));
			System.out.println("Passenger #" + (i + 1) + " Info:\n" + passengers.get(i));
			System.out.println("-".repeat(30));
			ticket.addSeatNum();
		}

		// print ticket info
		System.out.println("\nTicket Info\n" + ("-".repeat(15)) + "\nTicket(s): " + ticketNum + "\n" + ticket); // add
																																																						// ticket
																																																						// info to
																																																						// email
																																																						// also add
																																																						// headers
																																																						// like on
																																																						// top is
																																																						// the
																																																						// flight
																																																						// details
																																																						// and under
																																																						// is the
																																																						// passenger
																																																						// details

		// store the cost of everything for the flight
		flightPrice = (((duration.getDistance() * passengers.size()) + totalPrice) * 0.25) * 1.13; 

		// print total cost of flight for user to see
		System.out.println(("-".repeat(30)) + "\ntotal flight price (w/ tax): $" + df.format(flightPrice));

		// Invoice Writer
		try (FileWriter fw = new FileWriter("AirLines- Shams _ Hamza/src/main/java/Airlines/Invoice.txt")) {
			PrintWriter pw = new PrintWriter(fw);
			pw.println("Date of ticket creation: " + getDate());
			pw.println("Duration of flight " + duration);
			pw.println(ticket);
			for (Passenger p : passengers) {
				pw.println("\n" + p);
				pw.println("-".repeat(15));
			}
			pw.println("\n\ntotal flight price (w/ tax): $" + df.format(flightPrice));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// ask the user if they want an email confirmation text file sent to their email
		// this will send to the email they registered with
		// if user doesn't want an email confirmation the program will say Enjoy your
		// trip
		System.out.print("\nWould you like to be sent an confirmation text file to your email? (y/n): ");
		do {
			sendEmailConfirm = scan.next().charAt(0);
			if (!(choice == 'y' || choice == 'n')) {
				System.out.print("Input y or n: ");
			}
		}while (!(choice == 'y' || choice == 'n'));
		if (sendEmailConfirm == 'y') {
			SpringApplication.run(Main.class, args);
		}
		System.out.println("\n\n\t\tEnjoy your trip!");
		printInfo();

	}

	/////////////// METHODS//////////////

	// prints info for company name, slogan, current date, music
	public static void printInfo() {
		System.out
				.println("\n\t" + ("-".repeat(14)) + "AirHub" + ("-".repeat(14)) + "\n\tBooking your passage to the future");
		System.out.println("\n\t Song: onion prod. by lukrembo");
		System.out.println("\t Todays Date: " + getDate() + "\n");
	}

	// validates name a-z
	public static String validateName(Scanner object) {
		String temp;
		do {
			temp = object.nextLine();
			if (!(Pattern.matches("[a-zA-Z]+", temp))) {
				System.out.print("Enter letters only: ");
			}

		} while (!(Pattern.matches("[a-zA-Z]+", temp)));
		return temp;
	}

	public static String getEmailLogin(String emailAcc) {
		try {
			FileWriter fw = new FileWriter("AirLines- Shams _ Hamza/src/main/java/LoginUI/email.txt");
			PrintWriter pw = new PrintWriter(fw);
			pw.close();

			FileReader fr = new FileReader("AirLines- Shams _ Hamza/src/main/java/LoginUI/email.txt");
			BufferedReader br = new BufferedReader(fr);
			emailAcc = null;
			while (emailAcc == null) {
				emailAcc = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return emailAcc;
	}

	// Validate Location
	public static String validateLocations(Scanner scan) {
		String tempString;
		do {
			// Input Place
			tempString = scan.nextLine();
			// Set the first letter to UpperCase
			tempString = tempString.substring(0, 1).toUpperCase() + tempString.substring(1);

			// Check if the location exists in the arrayList
			if (!(Duration.getFlyList().contains(tempString))) {
				System.out.print("Input valid location: ");
			}
		} while (!(Duration.getFlyList().contains(tempString)));
		return tempString;
	}

	public static String getDate() {
		String date = "";
		Date currentTime = new Date();
		// format the date to said format
		SimpleDateFormat df = new SimpleDateFormat("kk:mm   MM/dd/yyy");
		// set time zone to EST to print the right date
		df.setTimeZone(TimeZone.getTimeZone("EST"));
		// set string to the date
		date = df.format(currentTime);
		return date;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void triggerMail() throws MessagingException {
		String emailAcc = "";
		try {
			// File Read to get email
			FileReader fr = new FileReader("AirLines- Shams _ Hamza/src/main/java/LoginUI/email.txt");
			BufferedReader br = new BufferedReader(fr);
			// get Email
			emailAcc = br.readLine();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Call method and set the parameters to the email components
		emailSenderService.sendMailWithAttachment(emailAcc,
				"Your flight confirmation invoice has been sent. Please take a look.",
				"Airlines confirmation",
				"AirLines- Shams _ Hamza/src/main/java/Airlines/Invoice.txt");

	}

	// plays music in a continuous loop
	public static void PlayMusic() {
		try {
			File musicPath = new File("onion prod. by lukrembo.wav");
			if (musicPath.exists()) {

				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				Clip clip = AudioSystem.getClip();
				clip.open(audioInput);
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			} else {
				System.out.println("Can't Find File");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
