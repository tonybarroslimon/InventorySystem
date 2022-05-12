package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author antonio
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    
    /**
     * @param id id to assign
     * @param name name to assign
     * @param price price to assign
     * @param stock stock to assign
     * @param min min to assign
     * @param max max to assign
     */
    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * @param name the name to set
     */    
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @param price the price to set
     */    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    /**
     * @param stock the stock to set
     */    
    public void setStock(int stock) {
        this.stock = stock;
    }
    
    /**
     * @param min the min to set
     */    
    public void setMin(int min) {
        this.min = min;
    }
    
    /**
     * @param max the max to set
     */    
    public void setMax(int max) {
        this.max = max;
    }
    
    /**
     * @return the id
     */    
    public int getId() {
        return id;
    }
    
    /**
     * @return the name
     */    
    public String getName() {
        return name;
    }
    
    /**
     * @return the price
     */    
    public Double getPrice() {
        return price;
    }
    
    /**
     * @return the stock
     */    
    public int getStock() {
        return stock;
    }
    
    /**
     * @return the min
     */    
    public int getMin() {
        return min;
    }
    
    /**
     * @return the max
     */    
    public int getMax() {
        return max;
    }
    
    /**
     * @param part the part to add to the associatedParts list
     */    
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);  
    }
    
    /**
     * @param selectedAssociatedPart part to delete from associatedParts list
     * @return if selectedAssociatedPart was found
     */      
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        for (Part productPart : associatedParts) {
            if (productPart.getId() == selectedAssociatedPart.getId()) {
                associatedParts.remove(selectedAssociatedPart);
                return true;
            }
        }
        return false;
    }
    
    /**
     * @return the associatedParts list
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
    
    /*
    public static Part associatedPartSearch(int partID){
        return associatedParts.get(partID);
    }
    */
    
    /*public void setAssociatedPartsList(ObservableList<Part> parts) {
        associatedParts.addAll(parts);
    }*/
    
    
}
