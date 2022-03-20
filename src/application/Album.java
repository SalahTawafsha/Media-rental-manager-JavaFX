package application;

public class Album extends Media {
	private String artist;
	private String songs;

	public Album(String code, String title, int numOfCopies, String artist, String songs) {
		super(title, numOfCopies, code);
		setArtist(artist);
		setSongs(songs);
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getSongs() {
		return songs;
	}

	public void setSongs(String songs) {
		this.songs = songs;
	}

	@Override
	public String toString() {
		return String.format("%-10s|%-20s|%-30s|%-20s|%-20s|%s\n", "Album", "Code: " + getCode(),
				"Title: " + getTitle(), "Num Of Copeis: " + getNumOfCopies(), "Artist: " + artist, "Songs: " + songs);
	}
}
