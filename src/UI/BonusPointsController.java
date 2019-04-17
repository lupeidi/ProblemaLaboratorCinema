package UI;

import Domain.Client;
import Service.ClientService;
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

public class BonusPointsController {
    public TableView tableViewClients;
    public TableColumn tableColumnIdClient;
    public TableColumn tableColumnFirstName;
    public TableColumn tableColumnLastName;
    public TableColumn tableColumnCNP;
    public TableColumn tableColumnBirthdayDate;
    public TableColumn tableColumnRegistrationDate;
    public TableColumn tableColumnPoints;

    public TextField startDay;
    public TextField startMonth;
    public TextField endDay;
    public TextField endMonth;
    public TextField bonusPoints;
    public Button extraButton;
    public Button cancelButton;
    public Button undoButton;
    public Button redoButton;

    private ClientService service;
    private ObservableList<Client> clients = FXCollections.observableArrayList();

    public void setService(ClientService service) {
        this.service = service;
    }

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            clients.addAll(service.getAll());
            tableViewClients.setItems(clients);
        });
    }

    public void bonusPointsClick(ActionEvent actionEvent) {
        try {
            LocalDate begin = LocalDate.of(2000, Integer.parseInt(startMonth.getText()), Integer.parseInt(startDay.getText()));
            LocalDate end = LocalDate.of(2000, Integer.parseInt(endMonth.getText()), Integer.parseInt(endDay.getText()));
            int bonus = Integer.parseInt(bonusPoints.getText());
            service.bonusPoints(begin, end, bonus);
            clients.clear();
            clients.addAll(service.getAll());
        } catch (RuntimeException rex) {
            Common.showValidationError(rex.getMessage());
        }
    }

    public void btnClientBonusPointsUndoClick(ActionEvent actionEvent) {
        service.undo();
        clients.clear();
        clients.addAll(service.getAll());
    }

    public void btnClientBonusPointsRedoClick(ActionEvent actionEvent) {
        service.redo();
        clients.clear();
        clients.addAll(service.getAll());
    }

    public void cancelClick(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
