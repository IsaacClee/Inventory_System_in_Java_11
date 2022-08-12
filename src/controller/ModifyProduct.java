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
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** ModifyProduct Initialize
 *  This method is used to start the controller
 */
public class ModifyProduct implements Initializable {

    private Product productToBeUpdated = null;
    private Product productUpdated = null;
    private ObservableList<Part> productAssociatedParts = FXCollections.observableArrayList();

    Stage stage;
    Parent scene;

    @javafx.fxml.FXML
    private TableColumn allPartInvCol;
    @javafx.fxml.FXML
    private TextField idField;
    @javafx.fxml.FXML
    private Button removeAssociatedPart;
    @javafx.fxml.FXML
    private TextField searchProduct;
    @javafx.fxml.FXML
    private TableView AssociatedPartsTable;
    @javafx.fxml.FXML
    private Button addProduct;
    @javafx.fxml.FXML
    private TextField minField;
    @javafx.fxml.FXML
    private TableColumn assNameCol;
    @javafx.fxml.FXML
    private TableColumn assInvCol;
    @javafx.fxml.FXML
    private TextField nameField;
    @javafx.fxml.FXML
    private Button saveProduct;
    @javafx.fxml.FXML
    private TableColumn allPartNameCol;
    @javafx.fxml.FXML
    private TableColumn assPriceCol;
    @javafx.fxml.FXML
    private TextField priceField;
    @javafx.fxml.FXML
    private TableColumn allPartIdCol;
    @javafx.fxml.FXML
    private TableView AllPartsTable;
    @javafx.fxml.FXML
    private TextField maxField;
    @javafx.fxml.FXML
    private TableColumn allPartPriceCol;
    @javafx.fxml.FXML
    private TableColumn assPartIdCol;
    @javafx.fxml.FXML
    private Button cancelProduct;
    @javafx.fxml.FXML
    private TextField inventoryField;
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
        System.out.println("Modify Product");

        AllPartsTable.setItems(Inventory.allParts.getAllParts());

        allPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productToBeUpdated = MainForm.getProductToBeUpdated();

        idField.setText(String.valueOf(productToBeUpdated.getId()));
        nameField.setText(productToBeUpdated.getName());
        inventoryField.setText(String.valueOf(productToBeUpdated.getStock()));
        priceField.setText(String.valueOf(productToBeUpdated.getPrice()));
        maxField.setText(String.valueOf(productToBeUpdated.getMax()));
        minField.setText(String.valueOf(productToBeUpdated.getMin()));

        productAssociatedParts = productToBeUpdated.getAllAssociatedParts();

        AssociatedPartsTable.setItems(productAssociatedParts);

        assPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        for(Part part: productAssociatedParts){
            associatedPartsTableList.add(part);
        }

    }

    /**
     * This action is triggered by a user clicking a button
     * This action triggers validation to update the product instance
     * Test Min vs Max entries
     * Test Inventory level to Min + Max entries
     * If the validation passes, the updated product will be added to the products list and the user is redirected to the main form
     * @param actionEvent
     * @throws IOException
     */
    @javafx.fxml.FXML
    public void OnActionSavePart(ActionEvent actionEvent) throws IOException {
        if (Integer.parseInt(maxField.getText()) > Integer.parseInt(minField.getText())) {
            if(Integer.parseInt(inventoryField.getText()) <= Integer.parseInt(maxField.getText()) && Integer.parseInt(inventoryField.getText()) >= Integer.parseInt(minField.getText()) ) {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                int stock = Integer.parseInt(inventoryField.getText());
                Double price = Double.parseDouble(priceField.getText());
                int max = Integer.parseInt(maxField.getText());
                int min = Integer.parseInt(minField.getText());

                productUpdated = new Product(id,name,price,stock,min,max);
                for(Part part : associatedPartsTableList){
                    productUpdated.addAssociatedPart(part);
                }
                MainForm.updateProduct(id,productUpdated);

                associatedPartsTableList.clear();

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
        associatedPartsTableList.add((Part) AllPartsTable.getSelectionModel().getSelectedItem());

        AssociatedPartsTable.setItems(associatedPartsTableList);

        assPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


    }

    /**
     * This action event can be used to remove an associated part from the Product instance
     * Tests by prompting user for confirmation
     * @param actionEvent
     * @throws IOException
     */
    @javafx.fxml.FXML
    public void OnActionRemove(ActionEvent actionEvent) throws IOException  {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will remove the selected part from this product. Do you want to proceed?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            associatedPartsTableList.remove((Part) AssociatedPartsTable.getSelectionModel().getSelectedItem());

            AssociatedPartsTable.setItems(associatedPartsTableList);

            assPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            assNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            assInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            assPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        }

    }

    /**
     * This action event can be used to cancel the new Part instance and return to the Main From
     * @param actionEvent
     * @throws IOException
     */
    @javafx.fxml.FXML
    public void OnActionCancel(ActionEvent actionEvent) throws IOException {
        associatedPartsTableList.clear();
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This action event can be used to search table for a part by ID or Name
     * @param actionEvent
     * @throws IOException
     */
    @javafx.fxml.FXML
    public void OnActionSearch(ActionEvent actionEvent) throws IOException  {
        String txt = searchProduct.getText();
        AllPartsTable.setItems(MainForm.lookupPart(txt));
        searchProduct.setText("");
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
        if(inventoryField.getText().matches("\\d+")){
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
