package application;

import java.util.ArrayList;

public class Customer implements Comparable<Customer> {
	private String name;
	private String address;
	private String plan;
	private String ID;
	private String mobileNum;
	private ArrayList<String> received = new ArrayList<>();
	private ArrayList<String> cart = new ArrayList<>(); // i asked DR. murad and he say we can assume that interested
														// arrayList
	// is as cart
	int value = Integer.MAX_VALUE; // the value of UNLIMITED is 2147483647 this value is as unlimited (the array
									// list size can't be larger than this number)

	public Customer(String ID, String name, String address, String plan, String mobileNum) {
		super();
		setName(name);
		setAddress(address);
		setPlan(plan.toUpperCase());
		if (plan.equalsIgnoreCase("LIMITED"))
			setValue(2); // and if the customer is limited set the value to 2
		this.ID = ID;
		setMobileNum(mobileNum);
	}

	public String getID() {
		return ID;
	}

	public int getValue() {
		return value;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public void setValue(int value) throws IllegalArgumentException {
		if (value >= received.size() && value >= 0)
			this.value = value;
		else if (value < 0)
			throw new IllegalArgumentException("The value can't be negative!!");
		else
			throw new IllegalArgumentException(
					name + " value can't be " + value + " bcz he has more than this number in his received list.");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) throws IllegalArgumentException {
		if (plan.equalsIgnoreCase("LIMITED") || plan.equalsIgnoreCase("UNLIMITED"))
			this.plan = plan;
		else
			throw new IllegalArgumentException(
					name + "'s plan can't be " + plan + " it should be LIMITED or UNLIMITED.");
	}

	public ArrayList<String> getReceived() {
		return received;
	}

	public void setReceived(ArrayList<String> received) {
		if (received != null)
			this.received = received;
	}

	public ArrayList<String> getCart() {
		return cart;
	}

	public void setCart(ArrayList<String> cart) {
		if (cart != null)
			this.cart = cart;
	}

	@Override
	public String toString() {// (String ID, String name, String address, String plan, String mobileNum)
		if (plan.equalsIgnoreCase("LIMITED"))
			return String.format("%-10s|%-20s|%-30s|%-20s|%-20s|%-20s|%-35s|%-35s|%s\n", "Customer", "ID: " + getID(),
					"Name: " + name, "Address: " + address, "Plan: " + plan, "MobileNum: " + mobileNum, "Cart: " + cart,
					"Received: " + received, "Value: " + value);
		else
			return String.format("%-10s|%-20s|%-30s|%-20s|%-20s|%-20s|%-35s|%-35s|%s\n", "Customer", "ID: " + getID(),
					"Name: " + name, "Address: " + address, "Plan: " + plan, "MobileNum: " + mobileNum, "Cart: " + cart,
					"Received: " + received, "UNLIMITED");
	}

	public boolean addToCart(String mediacode) {
		for (int i = 0; i < cart.size(); i++)
			if (cart.get(i).equalsIgnoreCase(mediacode))
				return false;

		cart.add(mediacode);
		return true;

	}

	public boolean addToReceived(String mediaTitle) { // this method is to add from file
		for (int i = 0; i < received.size(); i++)
			if (received.get(i).equalsIgnoreCase(mediaTitle))
				return false;

		if (received.size() < value) {
			received.add(mediaTitle);
			return true;
		}
		return false;
	}

	public boolean removeFromCart(String mediaTitle) {
		for (int i = 0; i < cart.size(); i++)
			if (cart.get(i).equalsIgnoreCase(mediaTitle)) {
				cart.remove(i);
				return true;
			}

		return false;
	}

	public boolean returnMedia(String mediaTitle) {
		for (int i = 0; i < received.size(); i++)
			if (received.get(i).equalsIgnoreCase(mediaTitle)) {
				received.remove(i);
				return true;
			}
		return false;

	}

	@Override
	public int compareTo(Customer o) {
		return ID.compareToIgnoreCase(o.getID());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Customer)
			return ID.equalsIgnoreCase(((Customer) obj).getID());
		else
			return super.equals(obj);
	}

}
