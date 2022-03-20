package application;

import java.util.ArrayList;
import java.util.Collections;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MediaRentalManager implements MediaRentalInt {
	private ArrayList<Customer> customers = new ArrayList<>();
	private ArrayList<Media> media = new ArrayList<>();

	public MediaRentalManager(ArrayList<Customer> customers, ArrayList<Media> media) {
		super();
		if (customers != null)
			this.customers = new ArrayList<>(customers);
		if (media != null)
			this.media = new ArrayList<>(media);
		Collections.sort(this.customers);
		Collections.sort(this.media);
	}

	public ArrayList<Customer> getCustomers() {
		return customers;
	}

	public ArrayList<Media> getMedia() {
		return media;
	}

	@Override
	public void addCustomer(String name, String address, String plan, String ID, String mobileNum) {
		Customer neww = new Customer(ID, name, address, plan, mobileNum);
		int index = Collections.binarySearch(customers, neww); // to don't sort every time ... find where it should be
																// in negative
																// and add it
		if (++index <= 0)
			customers.add(-index, neww);
	}

	@Override
	public void addMovie(String code, String title, int copiesAvailable, String rating) {
		Media neww = new Movie(code, title, copiesAvailable, rating);
		int index = Collections.binarySearch(media, neww); // to don't sort every time ... find where it should be in
															// negative and
															// add it
		if (++index <= 0)
			media.add(-index, neww);

	}

	@Override
	public void addGame(String code, String title, int copiesAvailable, double weight) {
		Media neww = new Game(code, title, copiesAvailable, weight);
		int index = Collections.binarySearch(media, neww); // to don't sort every time ... find where it should be and
															// add it
		if (++index <= 0)
			media.add(-index, neww);

	}

	@Override
	public void addAlbum(String code, String title, int copiesAvailable, String artist, String songs) {
		Media neww = new Album(code, title, copiesAvailable, artist, songs);
		int index = Collections.binarySearch(media, neww); // to don't sort every time ... find where it should be in
															// negative and
															// add it
		if (++index <= 0)
			media.add(-index, neww);

	}

	@Override
	public void setLimitedPlanLimit(int value) {
		for (int i = 0; i < customers.size(); i++)
			if (customers.get(i).getPlan().equalsIgnoreCase("LIMITED")) // because we don't have name then the set of
																		// value should be for all LIMITED
				try {
					customers.get(i).setValue(value);
				} catch (IllegalArgumentException e) {
					Alert error = new Alert(AlertType.ERROR,
							customers.get(i).getName() + " have more than " + value + " media in his recived list");
					error.show();
				}

	}

	@Override
	public String getAllCustomersInfo() {
		String s = new String();
		for (int i = 0; i < customers.size(); i++)
			s += customers.get(i).toString();

		return s;
	}

	@Override
	public String getAllMediaInfo() {
		String s = new String();
		for (int i = 0; i < media.size(); i++)
			s += media.get(i).toString();

		return s;
	}

	@Override
	public boolean addToCart(String customerID, String mediaCode) {
		int i = Collections.binarySearch(media, new Album(mediaCode, "", 0, "", "")); // use binarySearch is faster to
																						// check if it exist and use new
																						// album because media is
																						// abstract
																						// (I can't compare with new
																						// Media)
																						// and it no change if compare
																						// to
																						// album or movie or game
																						// because
																						// the compare is by just the
																						// title
		if (i < 0)
			return false;

		int j = Collections.binarySearch(customers, new Customer(customerID, "", "", "limited", "")); // use
																										// binarySearch
																										// is
		// faster to check if he
		// exist
		if (j < 0)
			return false;

		if (customers.get(j).addToCart(mediaCode)) // check if there are copy
													// and it isn't in cart with
													// add
			return true;
		else
			return false;

	}

	@Override
	public boolean removeFromCart(String customerID, String mediaCode) {
		int i = Collections.binarySearch(media, new Album(mediaCode, "", 0, "", "")); // use binarySearch is faster to
																						// check if it exist and use new
																						// album because media is
																						// abstract
																						// (I can't compare with new
																						// Media)
																						// and it no change if compare
																						// to
																						// album or movie or game
																						// because
																						// the compare is by just the
																						// title
		if (i < 0)
			return false;

		int j = Collections.binarySearch(customers, new Customer(customerID, "", "", "limited", "")); // use
																										// binarySearch
																										// is
		// faster to check if he
		// exist
		if (j < 0)
			return false;

		if (customers.get(j).removeFromCart(mediaCode)) // check if it in file and remove
			return true;
		else
			return false;
	}

	@Override
	public String processRequests() {
		String s = new String();
		for (int i = 0; i < customers.size(); i++) {
			ArrayList<String> rem = new ArrayList<>(); // store the names that i should remove it from cart (because i
														// can't remove direct in loop that use cart.size())
			for (int j = 0; j < customers.get(i).getCart().size()
					&& customers.get(i).getReceived().size() < customers.get(i).getValue(); j++)
				if (delCopy(customers.get(i).getCart().get(j))) {
					customers.get(i).getReceived().add(customers.get(i).getCart().get(j));
					s += String.format("Sending [%s] to [%s]\n", customers.get(i).getCart().get(j),
							customers.get(i).getName());
					rem.add(customers.get(i).getCart().get(j));
				}

			for (int j = 0; j < rem.size(); j++)
				customers.get(i).getCart().remove(rem.get(j));

		}

		return s;
	}

	public String processRequests(String ID) {
		String s = new String();
		ArrayList<String> rem = new ArrayList<>();
		int i = Collections.binarySearch(customers, new Customer(ID, null, null, "UNLIMITED", null));
		for (int j = 0; j < customers.get(i).getCart().size()
				&& customers.get(i).getReceived().size() < customers.get(i).getValue(); j++)
			if (delCopy(customers.get(i).getCart().get(j))) {
				customers.get(i).getReceived().add(customers.get(i).getCart().get(j));
				s += String.format("Sending [%s] to [%s]\n", customers.get(i).getCart().get(j),
						customers.get(i).getName());
				rem.add(customers.get(i).getCart().get(j));
			}

		for (int j = 0; j < rem.size(); j++)
			customers.get(i).getCart().remove(rem.get(j));

		return s;
	}

	private boolean delCopy(String string) {
		int i = binarySearch(string);
		if (media.get(i).getNumOfCopies() > 0) {
			media.get(i).setNumOfCopies(media.get(i).getNumOfCopies() - 1); // change number of copy
			return true;
		} else
			return false;
	}

	private int binarySearch(String key) {
		int l = 0, h = media.size() - 1;
		while (l <= h) {
			int mid = (l + h) / 2;
			if (media.get(mid).getCode().compareToIgnoreCase(key) > 0)
				h = mid - 1;
			else if (media.get(mid).getCode().compareToIgnoreCase(key) < 0)
				l = mid + 1;
			else
				return mid;
		}

		return -1;
	}

	@Override
	public boolean returnMedia(String customerId, String mediaCode) {
		int i = Collections.binarySearch(media, new Album(mediaCode, "", 0, "", "")); // check if the media exist in
																						// database
		if (i < 0)
			return false;

		int j = Collections.binarySearch(customers, new Customer(customerId, "", "", "limited", ""));// check if the
																										// User
		// exist in database
		if (j < 0)
			return false;

		if (customers.get(j).returnMedia(mediaCode)) { // check if it in the cart and return (in the method in
														// customer)
			media.get(i).setNumOfCopies(media.get(i).getNumOfCopies() + 1); // change number of copy
			return true;
		} else
			return false;

	}

	@Override
	public ArrayList<String> searchMedia(String title, String rating, String artist, String songs) {
		ArrayList<String> s = new ArrayList<>();
		for (int i = 0; i < media.size(); i++) {

			if (title == null && rating == null && artist == null && songs == null)
				s.add(media.get(i).getTitle());
			else if (title != null && title.equalsIgnoreCase(media.get(i).getTitle()))
				s.add(media.get(i).getTitle());
			else if (media.get(i) instanceof Movie && (rating.equalsIgnoreCase("AC") || rating.equalsIgnoreCase("DR")
					|| rating.equalsIgnoreCase("HR")))
				s.add(media.get(i).getTitle());
			else if (media.get(i) instanceof Album && (((Album) (media.get(i))).getArtist().equalsIgnoreCase(artist)
					|| ((Album) (media.get(i))).getSongs().contains(songs)))
				s.add(media.get(i).getTitle());
		}

		Collections.sort(s);
		return s;
	}

}
