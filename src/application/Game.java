package application;

public class Game extends Media {
	private double weight;

	public Game(String code, String title, int numOfCopies, double weight) {
		super(title, numOfCopies, code);
		setWeight(weight);
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) throws IllegalArgumentException {
		if (weight > 0)
			this.weight = weight;
		else
			throw new IllegalArgumentException("weight can't be less than or equal zero!!");
	}

	@Override
	public String toString() {
		return String.format("%-10s|%-20s|%-30s|%-20s|Weight: %.2f\n", "Game", "Code: " + getCode(),
				"Title: " + getTitle(), "Num Of Copeis: " + getNumOfCopies(), weight);
	}

}
