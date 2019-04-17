package UI;

import Domain.Reservation;
import Service.ReservationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReservationSearchController {

    public TableView tableViewReservations;
    public TableColumn tableColumnIdReservation;
    public TableColumn tableColumnMovieReservation;
    public TableColumn tableColumnClientReservation;
    public TableColumn tableColumnReservationDate;
    public TableColumn tableColumnReservationTime;

    public Button reservationSearchButton;
    public Button cancelButton;

    public TextField startHour;
    public TextField startMinutes;
    public TextField endHour;
    public TextField endMinutes;

    private ReservationService reservationService;
    private ObservableList<Reservation> reservations = FXCollections.observableArrayList();

    public void setService(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public void btnSearchClick(ActionEvent actionEvent) {
        try {
            reservations.clear();
            LocalTime start = LocalTime.of(Integer.parseInt(startHour.getText()), Integer.parseInt(startMinutes.getText()));
            LocalTime end = LocalTime.of(Integer.parseInt(endHour.getText()), Integer.parseInt(endMinutes.getText()));
            reservations.addAll(reservationService.periodSearchReservations(start, end));
            tableViewReservations.setItems(reservations);
        }
        catch (RuntimeException ex) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window: Reservation search.", ex);
            Common.showValidationError(ex.getMessage());
        }
    }
    public void btnCancelClick(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
