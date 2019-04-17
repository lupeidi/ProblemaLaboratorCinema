package UI;

import Domain.Client;
import Service.ClientService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientsByPointsController {
    public TableView tableViewClients;
    public TableColumn tableColumnIdClient;
    public TableColumn tableColumnFirstName;
    public TableColumn tableColumnLastName;
    public TableColumn tableColumnCNP;
    public TableColumn tableColumnBirthdayDate;
    public TableColumn tableColumnRegistrationDate;
    public TableColumn tableColumnPoints;
    private ClientService service;

    private ObservableList<Client> clients = FXCollections.observableArrayList();

    public void setService(ClientService service) {
        this.service = service;
    }

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            try {
                List<Client> clientsOrdered = service.sortClientsByPoints();

                clients.addAll(clientsOrdered);
                tableViewClients.setItems(clients);
            } catch (RuntimeException e) {
                Logger logger = Logger.getLogger(getClass().getName());
                logger.log(Level.SEVERE, "Failed to create new Window: ", e);
            }
        });
    }

}
