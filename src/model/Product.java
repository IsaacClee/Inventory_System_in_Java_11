package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Product Class
 * This class has a Dependency on the Part Class
 * Dependency = ObservableList<Part> associatedParts
 * @author Isaac Lee
 */
public class Product {
    public ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /** Product
     *  Constructor
     *  Does not include associatedParts as part of constructor
     *  Values must be assigned per instance of Product
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }


    /** addAssociatedPart
     *  Used to add Part to existing list of associated part for Product instance
     *  Must be assigned apart than Product Constructor
     *  Method must remain static for use
     * @param part
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /** deleteAssociatedPart
     *  The method is used to delete a Part from the existing list of associatedParts for a Product instance
     * @param SelectedAssociatedPart
     * @return boolean
     */
    public boolean deleteAssociatedPart(Part SelectedAssociatedPart) {
        for(Part part : associatedParts) {
            if(SelectedAssociatedPart.getId() == part.getId())
                associatedParts.remove(part);
                return true;
        }
        return false;
    }

    /** getAllAssociatedParts
     *  This getter is used to pull the associatedParts observable list from a Product instance
     * @return
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }

}