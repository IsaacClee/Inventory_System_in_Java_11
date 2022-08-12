package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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


/** MainForm Initialize
 *  This method is used to start the controller
 */
public class MainForm implements Initializable {

    Stage stage;
    Parent scene;

    @javafx.fxml.FXML
    private TextField searchProduct;
    @javafx.fxml.FXML
    private Button AddPart;
    @javafx.fxml.FXML
    private Button addProduct;
    @javafx.fxml.FXML
    private Button deletePart;
    @javafx.fxml.FXML
    private Button modifyProduct;
    @javafx.fxml.FXML
    private Button exitMenu;
    @javafx.fxml.FXML
    private Button deleteProduct;
    @javafx.fxml.FXML
    private TextField searchPart;
    @javafx.fxml.FXML
    private Button modifyPart;
    @javafx.fxml.FXML
    private TableColumn <Part, Double>partPriceCol;
    @javafx.fxml.FXML
    private TableColumn <Part, Integer> partIDCol;
    @javafx.fxml.FXML
    private TableView<Product> productTable;
    @javafx.fxml.FXML
    private TableColumn <Product, String> productNameCol;
    @javafx.fxml.FXML
    private TableColumn <Product, Integer>productInvCol;
    @javafx.fxml.FXML
    private TableView <Part> partTable;
    @javafx.fxml.FXML
    private TableColumn <Product,Integer> productIDCol;
    @javafx.fxml.FXML
    private TableColumn <Part, String> partNameCol;
    @javafx.fxml.FXML
    private TableColumn <Part, Integer> partInvCol;
    @javafx.fxml.FXML
    private TableColumn <Product, Double> productPriceCol;



    /** lookupPart
     *  This method uses a part id to pull a part instance from the allParts list
     * @param id
     * @return boolean
     */
    public static boolean lookupPart(int id) {
        for(Part part : Inventory.allParts.getAllParts()) {
            if(part.getId() == id)
                return true;
        }
     return false;
    }

    /** searchForProduct
     *  This method uses a product id to pull a product instance from the allProducts list
     * @param id
     * @return boolean
     */
    public static boolean searchForProduct(int id) {
        for(Product product : Inventory.allProducts.getAllProducts()) {
            if(product.getId() == id)
                return true;
        }
        return false;
    }

    /** updatePart
     *  This method loops from each part on the allParts list and uses the id to find an existing part to update
     * @param id
     * @param part
     */
    public static void updatePart(int id, Part part)
    {
        int indexCounter = -1;

        for(Part eachPart : Inventory.allParts.getAllParts())
        {
            indexCounter++;
            if(eachPart.getId() == id){
                Inventory.allParts.getAllParts().set(indexCounter, part);
            }
        }
    }

    /** updateProduct
     *  This method loops from each part on the allProducts list and uses the id to find an existing product to update
     * @param id
     * @param product
     */
    public static void updateProduct(int id, Product product)
    {
        int indexCounter = -1;

        for(Product eachProduct : Inventory.allProducts.getAllProducts())
        {
            indexCounter++;
            if(eachProduct.getId() == id){
                Inventory.allProducts.getAllProducts().set(indexCounter, product);
            }
        }
    }

    /** deletePart
     * Loop used to delete a Part form the allParts list by using id as an identifier
     * @param id
     * @return Inventory.allParts.getAllParts().remove(part);
     * Returns list minus deleted part
     */
    public boolean deletePart(int id){
        for(Part part : Inventory.allParts.getAllParts()) {
            if(part.getId() == id)
                return Inventory.allParts.getAllParts().remove(part);
        }
        return false;
    }

    /** deleteProduct
     * Loop used to delete a Product form the allProducts list by using id as an identifier
     * @param id
     * @return Inventory.allProducts.getAllProducts().remove(product);
     * returns list minus deleted product
     */
    public boolean deleteProduct(int id){
        for(Product product : Inventory.allProducts.getAllProducts()) {
            if(product.getId() == id)
                return Inventory.allProducts.getAllProducts().remove(product);
        }
        return false;
    }


    /** selectPart
     *  This method is used to return a part from the allParts list by id
     * @param id
     * @return part
     */
    public Part selectPart(int id){
        for(Part part : Inventory.allParts.getAllParts()) {
            if(part.getId() == id)
                return part;
        }
        return null;
    }

    /** lookupPart
     * This method uses a string input to filter the existing part list to find either the Part ID or Part name
     * Used to filter Tables in the UI by user input
     * Returns filtered list held in the allParts class
     * @param name
     * @return Inventory.allParts.getAllFilteredListOfParts()
     */
    public static ObservableList<Part> lookupPart(String name){
        if(!(Inventory.allParts.getAllFilteredListOfParts()).isEmpty()){
            Inventory.allParts.getAllFilteredListOfParts().clear();
        }

        for(Part part : Inventory.allParts.getAllParts()) {
            if(part.getName().contains(name) || Integer.toString(part.getId()).contains(name))
                Inventory.allParts.getAllFilteredListOfParts().add(part);
        }

        if(Inventory.allParts.getAllFilteredListOfParts().isEmpty()){
            return Inventory.allParts.getAllParts();
        }

        return Inventory.allParts.getAllFilteredListOfParts();
    }

    /** lookupProduct
     * This method uses a string input to filter the existing product list to find either the Product ID or Product name
     * Used to filter Tables in the UI by user input
     * Returns filtered list held in the allProducts class
     * @param name
     * @return Inventory.allProducts.getAllFilteredProducts();
     */
    public static ObservableList<Product> lookupProduct(String name){
        if(!(Inventory.allProducts.getAllFilteredProducts()).isEmpty()){
            Inventory.allProducts.getAllFilteredProducts().clear();
        }

        for(Product product : Inventory.allProducts.getAllProducts()) {
            if(product.getName().contains(name) || Integer.toString(product.getId()).contains(name))
                Inventory.allProducts.getAllFilteredProducts().add(product);
        }

        if(Inventory.allProducts.getAllFilteredProducts().isEmpty()){
            return Inventory.allProducts.getAllProducts();
        }

        return Inventory.allProducts.getAllFilteredProducts();
    }

    /** partToBeUpdated
     *  Used to store a Part to be used by another interface/controller by static class
     */
    public static Part partToBeUpdated = null;

    /** getPartToBeUpdated
     *  Getter to pull Part to another interface/controller
     * @return partToBeUpdated
     */
    public static Part getPartToBeUpdated(){
      return partToBeUpdated;
    };
    /** productToBeUpdated
     *  Used to store a Product to be used by another interface/controller by static class
     */
    public static Product productToBeUpdated = null;
    /** getProductToBeUpdated
     *  Getter to pull Part to another interface/controller
     * @return productToBeUpdated
     */
    public static Product getProductToBeUpdated(){
        return productToBeUpdated;
    };

    /** Initialize
     *  Main Form
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("Main Form");


        /**
         *  Populate Parts Table
         */

        partTable.setItems(Inventory.allParts.getAllParts());

        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTable.setItems(Inventory.allProducts.getAllProducts());

        /**
         *  Populate Products Table
         */

        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }



    /** onActionDeleteProduct
     *  User triggered action event using button
     *  Used to delete a Product that was selected from the ProductsTable in the UI
     *  Tests to ensure Product does that contain as associated part to avoid user errors
     *  Tests to ensure user intended to delete Product
     * @param actionEvent
     * @throws IOException
     */
    @javafx.fxml.FXML
    public void onActionDeleteProduct(ActionEvent actionEvent) throws IOException  {
        Product selectedItem = productTable.getSelectionModel().getSelectedItem();
        if(selectedItem.getAllAssociatedParts().size() > 0){
            Alert associatedPartAlert = new Alert(Alert.AlertType.CONFIRMATION, "This product has an associated Part and cannot be deleted until all associated parts are removed.");
            associatedPartAlert.showAndWait();
        } else {
            Alert deleteProductAlert = new Alert(Alert.AlertType.CONFIRMATION, "This will remove the selected product from inventory. Do you want to proceed?");
            Optional<ButtonType> deleteProductAlertResult  = deleteProductAlert.showAndWait();

            if(deleteProductAlertResult.isPresent() && deleteProductAlertResult.get() == ButtonType.OK){
                deleteProduct(selectedItem.getId());
        };

        }

    }


    /** onActionModifyProduct
     *  User triggered action event using button
     *  Uses productToBeUpdated to pass object
     *  Used to launch new window to update a Product that was selected from the ProductsTable in the UI
     * @param actionEvent
     * @throws IOException
     */
    @javafx.fxml.FXML
    public void onActionModifyProduct(ActionEvent actionEvent) throws IOException {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        productToBeUpdated = selectedProduct;
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ModifyProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** onActionAddProduct
     *  User triggered action event using button
     *  Used to launch new window to add a new Product instance to the Products Inventory List
     * @param actionEvent
     * @throws IOException
     */
    @javafx.fxml.FXML
    public void onActionAddProduct(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** onActionDeletePart
     *  User triggered action event using button
     *  Used to delete a Part that was selected from the PartsTable in the UI
     *  Tests to ensure user intended to delete Part
     * @param actionEvent
     * @throws IOException
     */
    @javafx.fxml.FXML
    public void onActionDeletePart(ActionEvent actionEvent) {
        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION, "This will remove the selected part from inventory. Do you want to proceed?");
        Optional<ButtonType> result = deleteAlert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            Part selectedItem = partTable.getSelectionModel().getSelectedItem();
            deletePart(selectedItem.getId());
        }
    }

    /** onActionAddPart
     *  User triggered action event using button
     *  Used to launch new window to add a new Part instance to the Parts Inventory List
     * @param actionEvent
     * @throws IOException
     */
    @javafx.fxml.FXML
    public void onActionAddPart(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** onActionModifyPart
     *  User triggered action event using button
     *  Uses partToBeUpdated to pass object
     *  Used to launch new window to update a Part that was selected from the PartsTable in the UI
     * @param actionEvent
     * @throws IOException
     */
    @javafx.fxml.FXML
    public void onActionModifyPart(ActionEvent actionEvent) throws IOException {
        Part selectedItem = partTable.getSelectionModel().getSelectedItem();
        partToBeUpdated = selectedItem;
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ModifyPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** onActionExitMenu
     * This method closes the application by a typical method
     * @param actionEvent
     */
    @javafx.fxml.FXML
    public void onActionExitMenu(ActionEvent actionEvent) {
        System.exit(0);
    }

    /** onActionSearchPart
     * This method uses a user input via search bar to search the Parts list by ID or Name
     * @param actionEvent
     */
    @FXML
    public void onActionSearchPart(ActionEvent actionEvent) {
        String txt = searchPart.getText();
        partTable.setItems(lookupPart(txt));
        searchPart.setText("");
    }

    /** onActionSearchProduct
     * This method uses a user input via search bar to search the Products list by ID or Name
     * @param actionEvent
     */
    @FXML
    public void onActionSearchProduct(ActionEvent actionEvent) {
        String txt = searchProduct.getText();
        productTable.setItems(lookupProduct(txt));
        searchProduct.setText("");
    }
}
