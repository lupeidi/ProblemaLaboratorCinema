package UI;

import Domain.Movie;
import Service.MovieService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MoviesByReservationsController {

    public TableView tableViewMovies;
    public TableColumn tableColumnNameMovie;
    public TableColumn tableColumnBookingsNumber;
    public TableColumn tableColumnIdMovie;

    private MovieService service;

    private ObservableList<Movie> movies = FXCollections.observableArrayList();

    public void setService(MovieService service) {
        this.service = service;
    }

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            try {
                List<Movie> orderedMovies = service.sortByReservations();
                movies.addAll(orderedMovies);
                tableViewMovies.setItems(movies);
            } catch (RuntimeException e) {
                Logger logger = Logger.getLogger(getClass().getName());
                logger.log(Level.SEVERE, "Failed to create new Window: ", e);
            }
        });
    }
}
