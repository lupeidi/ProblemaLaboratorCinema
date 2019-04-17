package Domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation extends Entity{
    private String id_movie;
    private String id_client;
    private LocalDate date;
    private LocalTime hour;

    public Reservation(String id, String id_movie, String id_client, LocalDate date, LocalTime hour) {
        super(id);
        this.id_movie = id_movie;
        this.id_client = id_client;
        this.date = date;
        this.hour = hour;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id='" + getId()+ '\'' +
                ", id_movie='" + id_movie + '\'' +
                ", id_client='" + id_client + '\'' +
                ", date='" + date + '\'' +
                ", hour=" + hour +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Reservation that = (Reservation) o;
        return id_movie.equals(that.id_movie) &&
                id_client.equals(that.id_client) &&
                date.equals(that.date) &&
                hour.equals(that.hour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id_movie, id_client, date, hour);
    }

    public String getId_movie() {
        return id_movie;
    }

    public void setId_movie(String id_movie) {
        this.id_movie = id_movie;
    }

    public String getId_client() {
        return id_client;
    }

    public void setId_client(String id_client) {
        this.id_client = id_client;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) {
        this.hour = hour;
    }
}
