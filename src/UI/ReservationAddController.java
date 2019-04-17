package UI;

import Service.ClientService;
import Service.ReservationService;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationAddController {
    public TextField txtIdMovie;
    public TextField txtIdClient;
    public TextField txtDateYear;
    public TextField txtDateMonth;
    public TextField txtDateDay;
    public TextField txtTimeHour;
    public TextField txtTimeMin;
    public Button btnAdd;
    public Button btnCancel;
    public TextField spnId;

    private ReservationService reservationService;

    public void setService(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public void btnCancelClick(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public void btnReservationInsertClick(ActionEvent actionEvent) {

        try {
            String id = spnId.getText();
            String idMovie = txtIdMovie.getText();
            String idCard = txtIdClient.getText();
            LocalDate date = LocalDate.of(Integer.parseInt(txtDateYear.getText()),
                    Integer.parseInt(txtDateMonth.getText()), Integer.parseInt(txtDateDay.getText()));
            LocalTime time = LocalTime.of(Integer.parseInt(txtTimeHour.getText()),
                    Integer.parseInt(txtTimeMin.getText()));

            reservationService.addOrUpdate(id, idMovie, idCard, date, time);
            btnCancelClick(actionEvent);
        } catch (RuntimeException rex) {
            Common.showValidationError(rex.getMessage());
        }
    }
}
