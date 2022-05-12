package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *
 * @author antonio
 */
public class Inventory {
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static int productID = 0;
    private static int partID = 0;
    
    /**
     * @param product product to add
     */
    public static void addProduct(Product product){
        allProducts.add(product);
    }
    
    /**
     * @param part part to add
     */
    public static void addPart(Part part){
        allParts.addAll(part);
    }
    
    /**
     * @param partID part ID to search
     * @return the part that is being looked up
     */
    public static Part lookupPart(int partID){
        for(Part partToBeLookedUp: allParts){
            if(partToBeLookedUp.getId() == partID){
                return partToBeLookedUp;
            }
        }
        return null;
    }
    
    /**
     * @param partName part name to be looked up
     * @return any part that matches the part name
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> foundPartsList = FXCollections.observableArrayList();
        for(int i = 0; i < allParts.size(); i++){
            if(allParts.get(i).getName().toLowerCase().contains(partName.toLowerCase())){
                foundPartsList.add(allParts.get(i));
            }
        }
        return foundPartsList;
    }
    
    /**
     * @param productID product ID to search
     * @return the product that is being looked up
     */
    public static Product lookupProduct(int productID){
        for(Product productToBeLookedUp: allProducts){
            if(productToBeLookedUp.getId() == productID){
                return productToBeLookedUp;
            }
        }
        return null;
    }
    
    /**
     * @param productName product name to lookup
     * @return all found products that match the product name
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> foundProductsList = FXCollections.observableArrayList();
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getName().toLowerCase().contains(productName.toLowerCase())) {
                foundProductsList.add(allProducts.get(i));
            }
        }
        return foundProductsList;
    }
    
    /**
     * @param index index of the part to update
     * @param part part to update
     */
    public static void updatePart(int index, Part part) {
        allParts.set(index, part);
    }
    
    /**
     * @param index index of the product to update
     * @param product product to update
     */
    public static void updateProduct(int index, Product product){
        allProducts.set(index, product);
    }
    
    /**
     * @param selectedPart selected part to delete
     * @return if the part to delete was deleted
     */
    public static boolean deletePart(Part selectedPart){
        for(Part partToBeDeleted : allParts){
            if(partToBeDeleted.getId() == selectedPart.getId()){
                allParts.remove(partToBeDeleted);
                return true;
            }
        }
        return false;
    }
    
    /**
     * @param selectedProduct selected product to delete
     * @return if the product to delete was deleted
     */
    public static boolean deleteProduct(Product selectedProduct){
        for(Product productToBeDeleted : allProducts){
            if(productToBeDeleted.getId() == selectedProduct.getId()){
                allProducts.remove(productToBeDeleted);
                return true;
            }
        }
        return false;
    }
    
    /**
     * @return all parts
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    
    /**
     * @return all products
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
    
    /**
     * @return the updated part ID
     */
    public static int increasePartID(){
        partID += 1;
        return partID;
    }
    
    /**
     * @return the updated product ID
     */
    public static int increaseProductID(){
        productID += 1;
        return productID;
    }
}
