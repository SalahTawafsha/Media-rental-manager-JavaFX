/*
package application;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Driver {
	static MediaRentalManager myLibrary = new MediaRentalManager(new ArrayList<>(), new ArrayList<>());
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		try {
			readFromFile();
		} catch (IOException e) {
			System.out.println("you don't have database and should great one!");
		}

		while (true)
			try {
				System.out.print("\nSelect:\n" + "1. add Customer.\n" + "2. add Media.\n" + "3. add to cart.\n"
						+ "4. remove from cart.\n" + "5. set limited plan limitn\n"
						+ "6. received the media in customers carts.\n" + "7. return media\n"
						+ "8. search about media.\n" + "9. save all and exit.\n0 .EXIT\n" + "Please enter (1-9): ");
				int choise = scan.nextInt();
				switch (choise) {
				case 1:
					testAddingCustomers();
					break;
				case 2:
					testAddingMedia();
					break;
				case 3:
					testingAddingToCart();
					break;
				case 4:
					testingRemovingFromCart();
					break;
				case 5:
					testingSetLimitedPlanLimit();
					break;
				case 6:
					testProcessingRequests();
					break;
				case 7:
					testReturnMedia();
					break;
				case 8:
					testSearchMedia();
					break;
				case 9:
					print();
					break;
				case 0:
					System.out.println("just smile and have a nice day.\nBye! ");
					System.exit(0); // don't need break because i will exit system
				default:
					System.out.println("Unexpected value, you should enter (1-9). ");
				}
			} catch (InputMismatchException e) {
				System.out.println("Unexpected value.");
				scan.nextLine();
			} catch (IOException e) {
				System.out.println("There are an error in print on file.");
			}

	}

	public static void readFromFile() throws IOException {
		File mediaFile = new File("Media.txt");
		File customersFile = new File("Customers.txt");
		Scanner scanMediums = new Scanner(mediaFile);
		Scanner scanCustomers = new Scanner(customersFile);
		ArrayList<Customer> customers = new ArrayList<>(); // array list to add all customers and them properties then
															// add it to media Rental by constructor
		ArrayList<Media> media = new ArrayList<>(); // array list to add all media and them properties then add it to
													// media Rental by constructor
		if (!scanMediums.hasNext() || !mediaFile.exists() || !scanCustomers.hasNext() || !customersFile.exists()) {
			scanMediums.close();
			scanCustomers.close();
			throw new IOException();
		}
		while (scanMediums.hasNext())
			try {
				String s = scanMediums.nextLine();
				String[] arr = s.split("[:|]");
				// in the first split with '|' and ':' that i add it in to string to know every
				// field easy
				standardForm(arr);
				// call method to remove all spaces in first and end of each string in array

				if (arr[0].equalsIgnoreCase("Movie"))
					media.add(new Movie(standardForm(arr[2]), Integer.parseInt(arr[4]), arr[6]));
				// standardForm is make first char capital and other small
				else if (arr[0].equalsIgnoreCase("Game"))
					media.add(new Game(standardForm(arr[2]), Integer.parseInt(arr[4]), Double.parseDouble(arr[6])));
				else if (arr[0].equalsIgnoreCase("Album"))
					media.add(new Album(standardForm(arr[2]), Integer.parseInt(arr[4]), standardForm(arr[6]), arr[8]));
			} catch (RuntimeException e) {
				System.out.println("There are an error in the file.");
				// if there are value can't accepted (like in rating the Movie)
			}
		int j = 0;
		while (scanCustomers.hasNext())
			try {
				String s = scanCustomers.nextLine();
				String[] arr = s.split("[:|]");
				// in the first split with '|' and ':' that i add it in to string to know every
				// field easy
				standardForm(arr);
				customers.add(new Customer(standardForm(arr[2]), standardForm(arr[4]), arr[6]));
				if (customers.get(j).getPlan().equalsIgnoreCase("LIMITED")) // if the customer LIMITED read and set the
																			// value
					customers.get(j).setValue(Integer.parseInt(arr[12]));

				String[] cart = arr[8].split("[\\[\\],]"); // in customer we have cart and received as arrayList so i
															// split by ] [ ,
				for (int k = 1; k < cart.length; k++) // start with 1 because 0 is ""
					customers.get(j).addToCart(standardForm(cart[k].trim()));
				String[] received = arr[10].split("[\\[\\],]");
				for (int k = 1; k < received.length; k++)
					customers.get(j).addToReceived(standardForm(received[k].trim()));
				j++; // this value for know number of customer to change his properties easy
			} catch (RuntimeException e) {
				System.out.println("There are an error in the file.");
				// if there are value can't accepted (like in rating the Movie)
			}

		Collections.sort(customers);
		Collections.sort(media);
		myLibrary = new MediaRentalManager(customers, media);
		scanMediums.close();
		scanCustomers.close();
	}

	public static void testAddingCustomers() { // read the values from user and add to database
		scan.nextLine();
		System.out.printf("Please enter the name of customer: ");
		String name = scan.nextLine();
		System.out.printf("Please enter the address of customer: ");
		String address = scan.nextLine();
		System.out.printf("Please Enter the plan of customer (LIMITED or UNLIMITED): ");
		String plan = scan.next();
		while (!plan.equalsIgnoreCase("LIMITED") && !plan.equalsIgnoreCase("UNLIMITED")) {
			System.out.printf("you should enter LIMITED or UNLIMITED, please try again: ");
			plan = scan.next();
		}
		myLibrary.addCustomer(standardForm(name), standardForm(address), plan);
	}

	public static void testAddingMedia() { // read the values from user and add to database
		System.out.print("What is the kind of media you want to add?\n" + "1. Album.\n" + "2. Game.\n" + "3. Movie.\n"
				+ "Please enter (1-3): ");
		int num, choise = scan.nextInt();
		scan.nextLine();
		String title;
		switch (choise) {
		case 1:
			System.out.printf("Please enter title of Album: ");
			title = scan.nextLine();
			System.out.printf("Please enter number of copies: ");
			num = scan.nextInt();
			scan.nextLine();
			System.out.printf("Please enter artist name: ");
			String artist = scan.nextLine();
			System.out.printf("Please enter songs names in this album (with commas betwwen them): ");
			String song = scan.nextLine();
			try {
				myLibrary.addAlbum(standardForm(title), num, standardForm(artist), song);
			} catch (IllegalArgumentException e) { // because the number of copy maybe throw exception
				System.out.println(e.getMessage());
			}
			break;
		case 2:
			System.out.printf("Please enter title of Game: ");
			title = scan.nextLine();
			System.out.printf("Please enter number of copies: ");
			num = scan.nextInt();
			System.out.printf("Please enter weight of game: ");
			double weight = scan.nextDouble();
			try {
				myLibrary.addGame(standardForm(title), num, weight);
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
			break;
		case 3:
			System.out.printf("Please enter title of Movie: ");
			title = scan.nextLine();
			System.out.printf("Please enter number of copies: ");
			num = scan.nextInt();
			scan.nextLine();
			System.out.printf("Please enter rating (AC or HR of DR): ");
			String rating = scan.next();
			while (!rating.equalsIgnoreCase("HR") && !rating.equalsIgnoreCase("DR") && !rating.equalsIgnoreCase("AC")) {
				System.out.print("You should enter (HR or DR of AC), please try again: ");
				rating = scan.next();
			}
			try {
				myLibrary.addMovie(standardForm(title), num, rating);
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + choise);
		}
	}

	public static void testingSetLimitedPlanLimit() { // read the value from user and change it for all limited
														// customers
		System.out.printf("Please enter value to set all LIMITED value customer to it: ");
		myLibrary.setLimitedPlanLimit(scan.nextInt());
	}

	public static void testingAddingToCart() { // read the values from user and add to cart of an user
		scan.nextLine();
		System.out.printf("Please Enter name of customer you want to add to his cart: ");
		String name = scan.nextLine();
		System.out.printf("please enter the media title that you want to add it: ");
		String title = scan.nextLine();
		if (myLibrary.addToCart(standardForm(name), standardForm(title)))
			System.out.println("Added!");
		else
			System.out.println(
					"Can't add, maybe the customer is already have this media in his cart, or the media or customer is NOT in database in database.");
	}

	public static void testingRemovingFromCart() { // read the values from user and remove from cart of an user
		scan.nextLine();
		System.out.printf("Please Enter name of customer you want to remove from his cart: ");
		String name = scan.nextLine();
		System.out.printf("please enter the media title that you want to remove it: ");
		String title = scan.nextLine();
		if (myLibrary.removeFromCart(standardForm(name), standardForm(title)))
			System.out.println("Removed!");
		else
			System.out.println(
					"Can't remove, maybe the media is NOT in the user cart or this customer or media is NOT exist in the database.");
	}

	public static void testProcessingRequests() { // it is clear
		System.out.println(myLibrary.processRequests());
	}

	public static void testReturnMedia() { // read the values from user and return media from an user
		scan.nextLine();
		System.out.printf("Please Enter name of customer who want to retutn media: ");
		String name = scan.nextLine();
		System.out.printf("please enter the media title: ");
		String title = scan.nextLine();

		if (myLibrary.returnMedia(name, title))
			System.out.println("Returned!");
		else
			System.out.println(
					"Can't returned, maybe the customer already don't have it or this customer or media is NOT exist in the database.");
	}

	public static void testSearchMedia() {
		scan.nextLine();
		System.out.println("NOTE: if you don't want search by any of fields (YOU CAN WRITE null).");
		System.out.printf("Please enter title of media to check if it in the library: ");
		String title = scan.nextLine();
		if (title.equals("null")) // if add null to do the search as project need If null is specified for a
									// parameter, then that parameter should be ignore in the search
			title = null;
		System.out.printf("Please enter ratting to search about film has it: ");
		String ratting = scan.nextLine();
		if (ratting.equals("null"))
			ratting = null;
		System.out.printf("Please enter name of an artist to search about his albums: ");
		String artist = scan.nextLine();
		if (artist.equals("null"))
			artist = null;
		System.out.printf("Please enter name of song to search name of it album: ");
		String song = scan.nextLine();
		if (song.equals("null"))
			song = null;
		System.out.println(myLibrary.searchMedia(title, ratting, artist, song)); // call the method and print
	}

	public static void print() throws IOException { // print on screen and file
		System.out.println(myLibrary.getAllCustomersInfo());
		System.out.println();
		System.out.println(myLibrary.getAllMediaInfo());

		try (PrintWriter customers = new PrintWriter("Customers.txt");
				PrintWriter media = new PrintWriter("Media.txt");) {
			customers.println(myLibrary.getAllCustomersInfo());
			media.println(myLibrary.getAllMediaInfo());
		}
	}

	public static String standardForm(String str) { // remove spaces and set first char capital
		str = (str.trim()).toLowerCase();
		String[] arr = str.split("[ ]");
		String ret = new String();
		for (int i = 0; i < arr.length; i++) {
			char[] chars = arr[i].toCharArray();
			chars[0] = Character.toUpperCase(chars[0]);
			String world = new String(chars);
			ret += " " + world;
		}
		return ret.trim();
	}

	public static void standardForm(String[] arr) { // remove spaces
		for (int i = 0; i < arr.length; i++)
			arr[i] = arr[i].trim();
	}
}
*/