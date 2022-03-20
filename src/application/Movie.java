package application;

public class Movie extends Media {
	private String rating;

	public Movie(String code, String title, int numOfCopies, String rating) {
		super(title, numOfCopies, code);
		setRating(rating.toUpperCase());
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) throws RuntimeException {
		if (rating.equalsIgnoreCase("HR") || rating.equalsIgnoreCase("DR") || rating.equalsIgnoreCase("AC"))
			this.rating = rating;
		else
			throw new RuntimeException();
	}

	@Override
	public String toString() {
		return String.format("%-10s|%-20s|%-30s|%-20s|%-20s\n", "Movie", "Code: " + getCode(), "Title: " + getTitle(),
				"Num Of Copeis: " + getNumOfCopies(), "Rating: " + rating);
	}

}
