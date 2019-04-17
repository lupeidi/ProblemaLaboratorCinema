package UI;

import Service.ClientService;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class ClientAddController {
    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtCNP;
    public TextField txtBirthdayYear;
    public TextField txtBirthdayMonth;
    public TextField txtBirthdayDay;
    public TextField txtRegistrationYear;
    public TextField txtRegistrationMonth;
    public TextField txtRegistrationDay;
    public TextField txtPoints;
    public Button btnAdd;
    public Button btnCancel;
    public TextField spnId;

    private ClientService clientService;

    public void setService(ClientService clientService) {
        this.clientService = clientService;
    }

    public void btnCancelClick(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public void btnClientInsertClick(ActionEvent actionEvent) {

        try {
            String id = spnId.getText();
            String firstName = txtFirstName.getText();
            String lastName = txtLastName.getText();
            String cnp = txtCNP.getText();
            LocalDate birthday = LocalDate.of(Integer.parseInt(txtBirthdayYear.getText()),Integer.parseInt(txtBirthdayMonth.getText()),Integer.parseInt(txtBirthdayDay.getText()));
            LocalDate registration = LocalDate.of(Integer.parseInt(txtRegistrationYear.getText()),Integer.parseInt(txtRegistrationMonth.getText()),Integer.parseInt(txtRegistrationDay.getText()));
            int points = Integer.parseInt( txtPoints.getText());


            clientService.addOrUpdate(id, firstName, lastName, cnp, birthday, registration, points);
            btnCancelClick(actionEvent);
        } catch (RuntimeException rex) {
            Common.showValidationError(rex.getMessage());
        }
    }
}
