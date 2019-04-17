package UI;

import Domain.Client;
import Domain.Reservation;
import Service.ReservationService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReservationPeriodRemoveController {
    public TableView tableViewReservations;
    public TableColumn tableColumnIdReservation;
    public TableColumn tableColumnMovieReservation;
    public TableColumn tableColumnClientReservation;
    public TableColumn tableColumnReservationDate;
    public TableColumn tableColumnReservationTime;
    public Button removeButton;
    public Button cancelButton;
    public Button undoButton;
    public Button redoButton;

    public TextField startDay;
    public TextField startMonth;
    public TextField startYear;
    public TextField endDay;
    public TextField endMonth;
    public TextField endYear;

    private ReservationService service;
    private ObservableList<Reservation> reservations = FXCollections.observableArrayList();

    public void setService(ReservationService service) {
        this.service = service;
    }

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            try {
                reservations.addAll(service.getAll());
                tableViewReservations.setItems(reservations);
            } catch (RuntimeException e) {
                Logger logger = Logger.getLogger(getClass().getName());
                logger.log(Level.SEVERE, "Failed to create new Window: ", e);
            }
        });
    }

    public void removeReservationsClick(ActionEvent actionEvent) {
        try {
            LocalDate begin = LocalDate.of(Integer.parseInt(startYear.getText()), Integer.parseInt(startMonth.getText()), Integer.parseInt(startDay.getText()));
            LocalDate end = LocalDate.of(Integer.parseInt(endYear.getText()), Integer.parseInt(endMonth.getText()), Integer.parseInt(endDay.getText()));
            service.periodRemoveReservations(begin, end);
            reservations.clear();
            reservations.addAll(service.getAll());
        } catch (RuntimeException rex) {
            Common.showValidationError(rex.getMessage());
        }
    }

    public void btnReservationsRemoveUndoClick(ActionEvent actionEvent) {
        service.undo();
        reservations.clear();
        reservations.addAll(service.getAll());

    }

    public void btnReservationsRemoveRedoClick(ActionEvent actionEvent) {
        service.redo();
        reservations.clear();
        reservations.addAll(service.getAll());
    }


    public void cancelClick(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
