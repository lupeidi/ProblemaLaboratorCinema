package UI;

import Domain.Client;
import Domain.Movie;
import Domain.Reservation;
import Service.ClientService;
import Service.MovieService;
import Service.ReservationService;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SearchController {
    public Label result;
    public TextField textToSearch;
    public Button btnSearch;
    public Button btnCancel;
    private MovieService movieService;
    private ClientService clientService;
    private ReservationService reservationService;

    public void setService(MovieService movieService, ClientService clientService, ReservationService reservationService) {
        this.movieService = movieService;
        this.clientService = clientService;
        this.reservationService = reservationService;
    }

    public void btnSearchClick(ActionEvent actionEvent) {
        String txt = textToSearch.getText();
        String found = " found in :\n" + movieSearch(txt) + "\n" + clientSearch(txt) + "\n" + reservationSearch(txt);
//        if(txt.length() >= 1)
            result.setText(found);
    }

    private String reservationSearch(String text) {
        String reservationFound = "";
        for (Reservation b : reservationService.textSearchReservations(text)) {
            reservationFound += b + "\n";
        }
        return reservationFound;
    }

    private String clientSearch(String text) {
        String cardsTextFound = "";
        for (Client c : clientService.textSearchClients(text)) {
            cardsTextFound += c + "\n";
        }
        return cardsTextFound;
    }

    private String movieSearch(String text) {
        String moviesTextFound = "";
        for (Movie m : movieService.textSearchMovies(text)) {
            moviesTextFound += m + "\n";
        }
        return moviesTextFound;
    }

    public void btnCancelClick(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
