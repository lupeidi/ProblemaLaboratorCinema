package UI;

import Domain.Client;
import Domain.Movie;
import Domain.Reservation;
import Service.MovieService;
import Service.ClientService;
import Service.ReservationService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Controller {

    public TableView tableViewMovies;
    public TableColumn tableColumnIdMovie;
    public TableColumn tableColumnNameMovie;
    public TableColumn tableColumnReleaseYear;
    public TableColumn tableColumnPrice;
    public TableColumn tableColumnAiring;
    public Button btnMovieAdd;
    public Button btnMovieDelete;

    public TableView tableViewClients;
    public TableColumn tableColumnIdClient;
    public TableColumn tableColumnFirstName;
    public TableColumn tableColumnLastName;
    public TableColumn tableColumnCNP;
    public TableColumn tableColumnBirthdayDate;
    public TableColumn tableColumnRegistrationDate;
    public TableColumn tableColumnPoints;
    public Button btnClientAdd;
    public Button btnClientDelete;

    public TableView tableViewReservations;
    public TableColumn tableColumnIdReservation;
    public TableColumn tableColumnMovieReservation;
    public TableColumn tableColumnClientReservation;
    public TableColumn tableColumnDate;
    public TableColumn tableColumnHour;
    public Button btnReservationAdd;
    public Button btnReservationDelete;

    private MovieService movieService;
    private ClientService clientService;
    private ReservationService reservationService;

    private ObservableList<Movie> movies = FXCollections.observableArrayList();
    private ObservableList<Client> clients = FXCollections.observableArrayList();
    private ObservableList<Reservation> reservations = FXCollections.observableArrayList();

    public void setServices(MovieService movieService, ClientService clientService, ReservationService reservationService) {
        this.movieService = movieService;
        this.clientService = clientService;
        this.reservationService = reservationService;
    }

    @FXML
    private void initialize() {

        Platform.runLater(() -> {
            movies.addAll(movieService.getAll());
            tableViewMovies.setItems(movies);
            clients.addAll(clientService.getAll());
            tableViewClients.setItems(clients);
            reservations.addAll(reservationService.getAll());
            tableViewReservations.setItems(reservations);
        });
    }


    public void upsertMovie(FXMLLoader fxmlLoader, String text) {
        try {
            Scene scene = new Scene(fxmlLoader.load(), 400, 400);
            Stage stage = new Stage();
            stage.setTitle(text);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            MovieAddController controller = fxmlLoader.getController();
            controller.setService(movieService);
            stage.showAndWait();
            movies.clear();
            movies.addAll(movieService.getAll());
        } catch (IOException e) {
        Logger logger = Logger.getLogger(getClass().getName());
        logger.log(Level.SEVERE, "Failed to create new Window", e);
    }
    }

    public void upsertClient(FXMLLoader fxmlLoader, String text) {
        try {
            Scene scene = new Scene(fxmlLoader.load(), 400, 400);
            Stage stage = new Stage();
            stage.setTitle(text);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            ClientAddController controller = fxmlLoader.getController();
            controller.setService(clientService);
            stage.showAndWait();
            clients.clear();
            clients.addAll(clientService.getAll());
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window", e);
        }
    }

    public void upsertReservation(FXMLLoader fxmlLoader, String text) {
        try {
            Scene scene = new Scene(fxmlLoader.load(), 400, 400);
            Stage stage = new Stage();
            stage.setTitle(text);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            ReservationAddController controller = fxmlLoader.getController();
            controller.setService(reservationService);
            stage.showAndWait();
            reservations.clear();
            reservations.addAll(reservationService.getAll());
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window", e);
        }
    }

    public void btnMovieAddClick(ActionEvent actionEvent) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../Resources/movieAdd.fxml"));
            upsertMovie(fxmlLoader, "Add/Update Movie");
    }

    public void btnMovieDeleteClick(ActionEvent actionEvent) {
        Movie selected = (Movie)tableViewMovies.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                movieService.remove(selected.getId());
                movies.clear();
                movies.addAll(movieService.getAll());
            } catch (RuntimeException rex) {
                Common.showValidationError(rex.getMessage());
            }
        }
    }

    public void btnClientAddClick(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../Resources/clientAdd.fxml"));
        upsertClient(fxmlLoader, "Add/Update Client");
    }

    public void btnClientDeleteClick(ActionEvent actionEvent) {
        Client selected = (Client)tableViewClients.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                clientService.remove(selected.getId());
                clients.clear();
                clients.addAll(clientService.getAll());
            } catch (RuntimeException rex) {
                Common.showValidationError(rex.getMessage());
            }
        }
    }

    public void btnReservationAddClick(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../Resources/reservationAdd.fxml"));
        upsertReservation(fxmlLoader, "Add/Update Reservation");
    }

    public void btnReservationDeleteClick(ActionEvent actionEvent) {
        Reservation selected = (Reservation) tableViewReservations.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                reservationService.remove(selected.getId());
                reservations.clear();
                reservations.addAll(reservationService.getAll());
            } catch (RuntimeException rex) {
                Common.showValidationError(rex.getMessage());
            }
        }
    }

    public void btnMovieUndoClick(ActionEvent actionEvent) {
        movieService.undo();
        movies.clear();
        movies.addAll(movieService.getAll());
    }

    public void btnMovieRedoClick(ActionEvent actionEvent) {
        movieService.redo();
        movies.clear();
        movies.addAll(movieService.getAll());
    }

    public void btnClientUndoClick(ActionEvent actionEvent) {
        clientService.undo();
        clients.clear();
        clients.addAll(clientService.getAll());
    }

    public void btnClientRedoClick(ActionEvent actionEvent) {
        clientService.redo();
        clients.clear();
        clients.addAll(clientService.getAll());
    }

    public void btnReservationUndoClick(ActionEvent actionEvent) {
        reservationService.undo();
        reservations.clear();
        reservations.addAll(reservationService.getAll());
    }

    public void btnReservationRedoClick(ActionEvent actionEvent) {
        reservationService.redo();
        reservations.clear();
        reservations.addAll(reservationService.getAll());
    }

    public void reservationSearch(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../Resources/reservationSearch.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 600, 600);
            Stage stage = new Stage();
            stage.setTitle("Reservation Search");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            ReservationSearchController controller = fxmlLoader.getController();
            controller.setService(reservationService);
            stage.showAndWait();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window:", e);
        }
    }

    public void moviesByReservations(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../Resources/moviesByReservations.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 600, 600);
            Stage stage = new Stage();
            stage.setTitle("Movies ordered by reservations");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            MoviesByReservationsController controller = fxmlLoader.getController();
            controller.setService(movieService);
            stage.showAndWait();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window: ", e);
        }
    }

    public void clientsByPoints(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../Resources/clientsByPoints.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 900, 600);
            Stage stage = new Stage();
            stage.setTitle("Clients ordered by points");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            ClientsByPointsController controller = fxmlLoader.getController();
            controller.setService(clientService);
            stage.showAndWait();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window: ", e);
        }
    }

    public void bonusPoints(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../Resources/bonusPoints.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 900, 600);
            Stage stage = new Stage();
            stage.setTitle("Bonus Points");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            BonusPointsController controller = fxmlLoader.getController();
            controller.setService(clientService);
            stage.showAndWait();
            clients.clear();
            clients.addAll(clientService.getAll());
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window: ", e);
        }
    }

    public void reservationsPeriodRemove (ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../Resources/reservationPeriodRemove.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 1000, 400);
            Stage stage = new Stage();
            stage.setTitle("Remove reservations in date range");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            ReservationPeriodRemoveController controller = fxmlLoader.getController();
            controller.setService(reservationService);
            stage.showAndWait();
            reservations.clear();
            reservations.addAll(reservationService.getAll());
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window: ", e);
        }
    }

    public void searchClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../Resources/search.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
            Stage stage = new Stage();
            stage.setTitle("Text Search");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            SearchController controller = fxmlLoader.getController();
            controller.setService(movieService, clientService, reservationService);
            stage.showAndWait();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window: ", e);
        }
    }
}
