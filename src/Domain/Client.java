package Domain;

import java.time.LocalDate;
import java.util.Objects;

public class Client extends Entity {

    private String firstName;
    private String lastName;
    private String cnp;
    private LocalDate birthdayDate;
    private LocalDate registrationDate;
    private int points;

    public Client(String id, String firstName, String lastName, String cnp, LocalDate birthdayDate, LocalDate registrationDate, int points) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.cnp = cnp;
        this.birthdayDate = birthdayDate;
        this.registrationDate = registrationDate;
        this.points = points;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id='" + getId() + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", cnp='" + cnp + '\'' +
                ", birthdayDate='" + birthdayDate + '\'' +
                ", registrationDate='" + registrationDate + '\'' +
                ", points=" + points +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Client client = (Client) o;
        return points == client.points &&
                firstName.equals(client.firstName) &&
                lastName.equals(client.lastName) &&
                cnp.equals(client.cnp) &&
                birthdayDate.equals(client.birthdayDate) &&
                registrationDate.equals(client.registrationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, cnp, birthdayDate, registrationDate, points);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public LocalDate getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(LocalDate birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void addPoints(int points) {
        this.points = this.points + points;
    }
}