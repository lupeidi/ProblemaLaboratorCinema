package UI;

import Service.MovieService;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MovieAddController extends Controller{
    public TextField txtName;
    public TextField txtReleaseYear;
    public TextField txtPrice;
    public CheckBox chkAiring;

    public Button btnAdd;
    public Button btnCancel;
    public TextField spinId;

    private MovieService movieService;

    public void setService(MovieService movieService) {
        this.movieService = movieService;
    }

    public void btnCancelClick(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public void btnMovieInsertClick(ActionEvent actionEvent) {

        try {
            String id = spinId.getText();
            String title = txtName.getText();
            int releaseYear = Integer.parseInt( txtReleaseYear.getText());
            double price = Double.parseDouble(txtPrice.getText());
            boolean airing = chkAiring.isSelected();

            movieService.addOrUpdate(id, title, releaseYear, price, airing);

            btnCancelClick(actionEvent);
        } catch (RuntimeException rex) {
            Common.showValidationError(rex.getMessage());
        }
    }
}
