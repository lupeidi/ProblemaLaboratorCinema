package Domain;

import java.util.Objects;

public class Movie extends Entity {

    private String title;
    private int releaseYear;
    private double price;
    private boolean airing;
    private int bookings;

    public Movie(String id, String title, int releaseYear, double price, boolean airing) {
        super(id);
        this.title = title;
        this.releaseYear = releaseYear;
        this.price = price;
        this.airing = airing;
        this.bookings = 0;
    }

    public Movie(Movie toCopy) {
        super(toCopy.getId());
        this.title = toCopy.getTitle();
        this.releaseYear = toCopy.getReleaseYear();
        this.price = toCopy.getPrice();
        this.airing = toCopy.isAiring();
        this.bookings = toCopy.getBookings();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Movie movie = (Movie) o;
        return releaseYear == movie.releaseYear &&
                Double.compare(movie.price, price) == 0 &&
                airing == movie.airing &&
                title.equals(movie.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, releaseYear, price, airing);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + getId() + '\'' +
                ", title='" + title + '\'' +
                ", releaseYear=" + releaseYear +
                ", price=" + price +
                ", airing=" + airing +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAiring() {
        return airing;
    }

    public void setAiring(boolean airing) {
        this.airing = airing;
    }

    public int getBookings() {
        return bookings;
    }

    public void setBookings(int bookings) {
        this.bookings = bookings;
    }
}
