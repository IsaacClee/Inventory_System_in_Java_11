package controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Inventory;
import model.InHouse;
import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** AddPart Class Initialize
 *  This method is used to initialize the controller
 */
public class AddPart implements Initializable {

    Stage stage;
    Parent scene;

    @javafx.fxml.FXML
    private TextField idField;
    @javafx.fxml.FXML
    private Button savePart;
    @javafx.fxml.FXML
    private RadioButton inHouse;
    @javafx.fxml.FXML
    private TextField priceField;
    @javafx.fxml.FXML
    private TextField minField;
    @javafx.fxml.FXML
    private TextField nameField;
    @javafx.fxml.FXML
    private TextField invField;
    @javafx.fxml.FXML
    private ToggleGroup source;
    @javafx.fxml.FXML
    private TextField maxField;
    @javafx.fxml.FXML
    private Button cancelPart;
    @javafx.fxml.FXML
    private TextField toggleField;
    @javafx.fxml.FXML
    private RadioButton outsourced;
    @javafx.fxml.FXML
    private Label PartToggle;
    @javafx.fxml.FXML
    private Label nameError;
    @javafx.fxml.FXML
    private Label priceError;
    @javafx.fxml.FXML
    private Label maxError;
    @javafx.fxml.FXML
    private Label invError;
    @javafx.fxml.FXML
    private Label minError;

    /** initialize
     *  This method is used to start the controller
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("Add Part");

        nameError.setText("Name Field is blank");
        invError.setText("Inventory Field is blank");
        priceError.setText("Price Field is blank");
        minError.setText("Min Field is blank");
        maxError.setText("Max Field is blank");
    }

    /**
     * This action event can be used to cancel the new Part instance and return to the Main From
     * @param actionEvent
     * @throws IOException
     */
    public void OnActionCancelPart(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Radio button action used to toggle Part between InHouse and Outsourced Classes
     * See onActionInHouse
     * @param actionEvent
     * @throws IOException
     */
    public void OnActionOutsourced(ActionEvent actionEvent) throws IOException {
        PartToggle.setText("Company Name");
    }

    /**
     * Radio button action used to toggle Part between InHouse and Outsourced Classes
     * See OnActionOutsourced
     * @param actionEvent
     * @throws IOException
     */
    @javafx.fxml.FXML
    public void onActionInHouse(ActionEvent actionEvent) throws IOException {
        toggleField.setText("Machine ID");
    }

    /**
     * This action is triggered by a user clicking a button
     * This action triggers validation to add a new part instance
     * If the validation passes, the new part will be added to the parts list and the user is redirected to the main form
     * @param actionEvent
     * @throws IOException
     */
    public void onActionSavePart(ActionEvent actionEvent) throws IOException {
        if (Integer.parseInt(maxField.getText()) > Integer.parseInt(minField.getText())) {
            if(Integer.parseInt(invField.getText()) <= Integer.parseInt(maxField.getText()) && Integer.parseInt(invField.getText()) >= Integer.parseInt(minField.getText()) ) {
                int numOfParts = Inventory.allParts.getAllParts().size();
                while(MainForm.lookupPart(numOfParts)){
                    numOfParts++;
                }
                int id = numOfParts;
                String name = nameField.getText();
                int stock = Integer.parseInt(invField.getText());
                Double price = Double.parseDouble(priceField.getText());
                int max = Integer.parseInt(maxField.getText());
                int min = Integer.parseInt(minField.getText());
                if(inHouse.isSelected()) {
                    int toggleInteger = Integer.parseInt(toggleField.getText());
                    Inventory.allParts.addPartToList(new InHouse(id, name,price,stock,min,max,toggleInteger));
                } else {
                    String toggleString = toggleField.getText();
                    Inventory.allParts.addPartToList(new Outsourced(id, name,price,stock,min,max,toggleString));
                };

                stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            } else {
                Alert invMatchAlert = new Alert(Alert.AlertType.CONFIRMATION, "Inventory must be between Max and Min, please correct to continue.");
                invMatchAlert.showAndWait();
            }
        } else {
            Alert minMaxMatchAlert = new Alert(Alert.AlertType.CONFIRMATION, "The Min should be less than the Max, please correct to continue.");
            minMaxMatchAlert.showAndWait();
        }


    }

    /**
     * Used to valid data type for field entry - double
     * @param event
     */
    @javafx.fxml.FXML
    public void priceFieldChange(Event event) {
        if(priceField.getText().matches("\\d*[.]\\d+")){
            priceError.setText("");
        } else {
            priceError.setText("Price is not a Double");
        }

    }

    /**
     * Used to valid data type for field entry - integer
     * @param event
     */
    @javafx.fxml.FXML
    public void invFieldChange(Event event) {
        if(invField.getText().matches("\\d+")){
            invError.setText("");
        } else {
            invError.setText("Inventory is not a Integer");
        }
    }

    /**
     * Used to valid data type for field entry - integer
     * @param event
     */
    @javafx.fxml.FXML
    public void minFieldChange(Event event) {
        if(minField.getText().matches("\\d+")){
            minError.setText("");
        } else {
            minError.setText("Min is not a Integer");
        }
    }

    /**
     * Used to valid data type for field entry - string
     * @param event
     */
    @javafx.fxml.FXML
    public void nameFieldChange(Event event) {
        if(nameField.getText().matches("\\w+")){
            nameError.setText("");
        } else {
            nameError.setText("Name field is blank");
        }
    }


    /**
     * Used to valid data type for field entry - integer
     * @param event
     */
    @javafx.fxml.FXML
    public void maxFieldChange(Event event) {
        if(maxField.getText().matches("\\d+")){
            maxError.setText("");
        } else {
            maxError.setText("Max is not a Integer");
        }
    }


}
