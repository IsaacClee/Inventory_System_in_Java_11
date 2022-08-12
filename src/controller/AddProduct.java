package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/** AddProduct Initialize
 *  This method is used to start the controller
 */
public class AddProduct implements Initializable {

    Stage stage;
    Parent scene;

    @javafx.fxml.FXML
    private TextField idField;
    @javafx.fxml.FXML
    private TableView AssociatedPartTable;
    @javafx.fxml.FXML
    private Button removeAssociatedPart;
    @javafx.fxml.FXML
    private TextField searchProduct;
    @javafx.fxml.FXML
    private Button addProduct;
    @javafx.fxml.FXML
    private TextField minField;
    @javafx.fxml.FXML
    private TextField nameField;
    @javafx.fxml.FXML
    private Button saveProduct;
    @javafx.fxml.FXML
    private TableColumn allPartNameCol;
    @javafx.fxml.FXML
    private TableColumn allPriceCol;
    @javafx.fxml.FXML
    private TableColumn AssNameCol;
    @javafx.fxml.FXML
    private TableColumn AssPartCol;
    @javafx.fxml.FXML
    private TableColumn allInvCol;
    @javafx.fxml.FXML
    private TextField priceField;
    @javafx.fxml.FXML
    private TableColumn allPartIdCol;
    @javafx.fxml.FXML
    private TableView allPartsTable;
    @javafx.fxml.FXML
    private TableColumn AssPriceCol;
    @javafx.fxml.FXML
    private TextField invField;
    @javafx.fxml.FXML
    private TableColumn AssInvCol;
    @javafx.fxml.FXML
    private TextField maxField;
    @javafx.fxml.FXML
    private Button cancelProduct;

    private static ObservableList<Part> associatedPartsTableList = FXCollections.observableArrayList();
    @javafx.fxml.FXML
    private Label minError;
    @javafx.fxml.FXML
    private Label nameError;
    @javafx.fxml.FXML
    private Label priceError;
    @javafx.fxml.FXML
    private Label maxError;
    @javafx.fxml.FXML
    private Label invError;

    /** initialize
     *  This method is used to start the controller
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("Add Product");

        nameError.setText("Name Field is blank");
        invError.setText("Inventory Field is blank");
        priceError.setText("Price Field is blank");
        minError.setText("Min Field is blank");
        maxError.setText("Max Field is blank");

        allPartsTable.setItems(Inventory.allParts.getAllParts());

        allPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * This action event can be used to cancel the new Part instance and return to the Main From
     * @param actionEvent
     * @throws IOException
     */
    @javafx.fxml.FXML
    public void OnActionCancelPart(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This action event can be used to remove an associated part from the Product instance
     * Tests by prompting user for confirmation
     * @param actionEvent
     * @throws IOException
     */
    @javafx.fxml.FXML
    public void OnActionRemovePart(ActionEvent actionEvent) throws IOException  {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will remove the selected part from this product. Do you want to proceed?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            associatedPartsTableList.remove((Part) AssociatedPartTable.getSelectionModel().getSelectedItem());

            AssociatedPartTable.setItems(associatedPartsTableList);

            AssPartCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            AssNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            AssInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            AssPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        }

    }

    /**
     * This action event can be used to search table for a part by ID or Name
     * @param actionEvent
     * @throws IOException
     */
    @javafx.fxml.FXML
    public void OnActionSearchPart(ActionEvent actionEvent) throws IOException  {
        String txt = searchProduct.getText();
        allPartsTable.setItems(MainForm.lookupPart(txt));
        searchProduct.setText("");
    }

    /**
     * This action is triggered by a user clicking a button
     * This action triggers validation to add a new product instance
     * If the validation passes, the new product will be added to the products list and the user is redirected to the main form
     * @param actionEvent
     * @throws IOException
     */
    @javafx.fxml.FXML
    public void OnActionSaveProduct(ActionEvent actionEvent) throws IOException  {

        if (Integer.parseInt(maxField.getText()) > Integer.parseInt(minField.getText())) {
            if(Integer.parseInt(invField.getText()) <= Integer.parseInt(maxField.getText()) && Integer.parseInt(invField.getText()) >= Integer.parseInt(minField.getText()) ) {
                int numOfParts = Inventory.allProducts.getAllProducts().size();
                while(MainForm.searchForProduct(numOfParts)){
                    numOfParts++;
                }
                int id = numOfParts;
                String name = nameField.getText();
                int stock = Integer.parseInt(invField.getText());
                Double price = Double.parseDouble(priceField.getText());
                int max = Integer.parseInt(maxField.getText());
                int min = Integer.parseInt(minField.getText());

                Product productNew = new Product(id,name,price,stock,min,max);
                for(Part part : associatedPartsTableList){
                    productNew.addAssociatedPart(part);
                }
                Inventory.allProducts.addProductToList(productNew);

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
     * This action event can be used to add an associated part from the Product instance
     * User must select part from parts table
     * @param actionEvent
     * @throws IOException
     */
    @javafx.fxml.FXML
    public void OnActionAddPart(ActionEvent actionEvent) throws IOException  {

        associatedPartsTableList.add((Part) allPartsTable.getSelectionModel().getSelectedItem());

        AssociatedPartTable.setItems(associatedPartsTableList);

        AssPartCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        AssNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

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
