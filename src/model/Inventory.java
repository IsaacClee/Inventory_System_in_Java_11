package model;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static model.Inventory.allParts.addPartToList;


/**
 * Main Application
 * */

public class Inventory extends Application {

    /** Start
     * This method is used to set the Application scene and stage.
     * UI Main Form is launched
     * @param stage
     * */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }


    /** Main Function
     *
     * This method launches args as program is executed
     * Test Data is generated within method
     * Objects are declared in runtime memory, this application has no Database
     *
     * @param args
     */
    public static void main(String[] args){

        /**
         * Three parts are generated for application using Part Abstract with subtypes Inhouse or Outsourced
         */

        InHouse part1 = new InHouse(1,"Brakes", 15.00, 10, 2, 20, 45);
        InHouse part2 = new InHouse(2,"Wheel", 11.00, 16, 8, 60, 65);
        Outsourced part3 = new Outsourced(3, "Seat", 15.00, 10, 1, 15, "Sears");

        /**
         *  Parts are added to Parts List
         **/
        addPartToList(part1);
        addPartToList(part2);
        addPartToList(part3);

        Product product1 = new Product(1,"Giant Bike", 299.99, 5, 2, 5);
        Product product2 = new Product(2,"Tricycle", 99.99, 3, 2, 5);

        product1.addAssociatedPart(part1);
        product1.addAssociatedPart(part2);

        product2.addAssociatedPart(part2);
        product2.addAssociatedPart(part3);

        /**
         *  Products are added to Products List
         *
         * */

        allProducts.addProductToList(product1);
        allProducts.addProductToList(product2);

        launch(args);
    }

    /** allParts Class
    *   Used to store a list of all created in a list
     *   ObservableList
    * */
    public static class allParts {


        /** listOfParts Method
         * This method creates an ObservableList for Parts
         * This method will be used to populate tables in the UI
         */
        private static ObservableList<Part> listOfParts = FXCollections.observableArrayList();

        /** filteredListOfParts Method
         * This method creates an ObservableList for Part
         *  This method will be used to store filtered Parts from listOfParts
         */
        private static ObservableList<Part> filteredListOfParts = FXCollections.observableArrayList();

        /** addPartToList
         * This method uses add a part to the existing list
         * static to allow other controllers to add to the existing part inventory
         * @param part
         */
        public static void addPartToList(Part part) {
            listOfParts.add(part);
        }


        /** getAllParts
         *  This getter enables the part list to be pulled data across the application
         * @return listOfParts
         */
        public static ObservableList<Part> getAllParts() {
            return listOfParts;
        }

        /** getAllFilteredListOfParts
         *  This getter enables a filtered list to be pulled data across the application
         * @return filteredListOfParts
         */
        public static ObservableList<Part> getAllFilteredListOfParts() {
            return filteredListOfParts;
        }

    }

    /** allParts Class
     *   Used to store a list of all created in a list
     *   ObservableList
     * */
    public static class allProducts {
        /** ListOfProducts Method
         * This method creates an ObservableList for Products
         * This method will be used to populate tables in the UI
         */
        private static ObservableList<Product> ListOfProducts = FXCollections.observableArrayList();

        /** filteredProducts Method
         * This method creates an ObservableList for Products
         *  This method will be used to store filtered Parts from listOfParts
         */
        private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

        /** addProductToList
         * This method uses add a product to the existing list
         * static to allow other controllers to add to the existing part inventory
         * @param product
         */
        public static void addProductToList(Product product) {
            ListOfProducts.add(product);
        }

        /** getAllProducts
         *  This getter enables the part list to be pulled data across the application
         * @return ListOfProducts
         */
        public static ObservableList<Product> getAllProducts() {
            return ListOfProducts;
        }

        /** getAllFilteredProducts
         *  This getter enables a filtered list to be pulled data across the application
         * @return filteredProducts
         */
        public static ObservableList<Product> getAllFilteredProducts() {
             return filteredProducts;
        }

    }

    /** addPart
     *  This method accesses the Observable list of Parts and then appends a part to the existing static list
     * @param part
     */
    public static void addPart(Part part) {
        Inventory.allParts.addPartToList(part);
    }
    /** addProduct
     *  This method accesses the Observable list of Products and then appends a product to the existing static list
     * @param product
     */
    public static void addProduct(Product product) {
        Inventory.allProducts.addProductToList(product);
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


    /**
     * FUTURE ENHANCEMENT - Store deleted parts to an Observable<Part> List to allow recovery of deleted parts over a set period of days
     * This improvement would provide a short-term back-up without resorting to a database to recover
     * Allows user to gain confidence is software and prevents user frustration and workflow delays
     */

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

    /**
     * FUTURE ENHANCEMENT - Store deleted products to an Observable<Product> List to allow recovery of deleted products over a set period of days
     * This improvement would provide a short-term back-up without resorting to a database to recover
     * Allows user to gain confidence is software and prevents user frustration and workflow delays
     */

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

    /** lookupPart
     *  This method uses a part id to pull a part instance from the allParts list
     * @param id
     * @return part
     */
    public static Part lookupPart(int id) {
        for(Part part : Inventory.allParts.getAllParts()) {
            if(part.getId() == id)
                return part;
        }
        return null;
    }

    /** lookupProduct
     *  This method uses a product id to pull a product instance from the allProducts list
     * @param id
     * @return product
     */
    public static Product lookupProduct(int id) {
        for(Product product : Inventory.allProducts.getAllProducts()) {
            if(product.getId() == id)
                return product;
        }
        return null;
    }


}
