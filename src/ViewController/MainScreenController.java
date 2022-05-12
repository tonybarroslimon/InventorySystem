package ViewController;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import Model.Part;
import Model.Product;
import Model.Inventory;
import static Model.Inventory.deletePart;
import static Model.Inventory.getAllParts;
import static Model.Inventory.getAllProducts;
import static Model.Inventory.deleteProduct;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author antonio
 */

public class MainScreenController implements Initializable {

    // Initializing Scene Elements
    @FXML private Button addPartButton;
    @FXML private Button modifyPartButton;
    @FXML private Button deletePartButton;
    @FXML private Button addProductButton;
    @FXML private Button modifyProductButton;
    @FXML private Button deleteProductButton;
    @FXML private Button exitButton;
    @FXML private Button partSearchButton;
    @FXML private Button productSearchButton;
    @FXML private TextField partSearchTextField;
    @FXML private TextField productSearchTextField;
    @FXML private TableView<Part> partTableView;
    @FXML private TableView<Product> productTableView;
    @FXML private TableColumn<Part, Integer> partIDTableColumn;
    @FXML private TableColumn<Part, String> partNameTableColumn;
    @FXML private TableColumn<Part, Integer> partInventoryTableColumn;
    @FXML private TableColumn<Part, Double> partPriceTableColumn;
    @FXML private TableColumn<Product, Integer> productIDTableColumn;
    @FXML private TableColumn<Product, String> productNameTableColumn;
    @FXML private TableColumn<Product, Integer> productInventoryTableColumn;
    @FXML private TableColumn<Product, Double> productPriceTableColumn;
    
    // Initializing Variables
    private static Part selectedPart;
    private static Product selectedProduct;
    private static int selectedPartIndexNumber;
    private static int selectedProductIndexNumber;
    public static ObservableList<Part> selectedAssociatedPart = FXCollections.observableArrayList();


    /**
     * Loads the add part screen
     * @param buttonClicked action that triggers the new scene to load
     * @throws Exception 
     */
    @FXML public void addPartButtonClicked(ActionEvent buttonClicked) throws Exception {
        loadNewScreen("AddPartScreen.fxml", buttonClicked, "Add Part");
    }
    
    /**
     * Loads the modify part screen
     * @param buttonClicked action that triggers the new scene to load
     * @throws Exception 
     */
    @FXML public void modifyPartButtonClicked(ActionEvent buttonClicked) throws Exception {
        selectedPart = partTableView.getSelectionModel().getSelectedItem();
        selectedPartIndexNumber = getAllParts().indexOf(selectedPart);
        if (selectedPart != null) {
            loadNewScreen("ModifyPartScreen.fxml", buttonClicked, "Modify Part");
        }
        else {
            Alert blankAlert = new Alert(Alert.AlertType.ERROR);
            blankAlert.setTitle("Part Modification Error");
            blankAlert.setHeaderText("The part cannot be modified!");
            blankAlert.setContentText("There was no part selected!");
            blankAlert.showAndWait();
        }
    }
    
    /**
     * This method deletes the selected part when the Delete Part Button is clicked
     * @param buttonClicked the delete part button is clicked to kick off the method
     * @throws IOException 
     */
    @FXML public void deletePartButtonClicked(ActionEvent buttonClicked) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Part");
        alert.setHeaderText("Are you sure you want to delete this part?");
        alert.setContentText("Press OK to delete the part. \nPress Cancel to cancel the deletion.");
        alert.showAndWait();
        
        if (alert.getResult() == ButtonType.OK) {
            try {
                Part partToDelete = partTableView.getSelectionModel().getSelectedItem();
                deletePart(partToDelete);
            }
            catch (NullPointerException e) {
                Alert nullalert = new Alert(Alert.AlertType.ERROR);
                nullalert.setTitle("Part Deletion Error");
                nullalert.setHeaderText("The part was NOT deleted!");
                nullalert.setContentText("There was no part selected!");
                nullalert.showAndWait();
            }
        }
        else {
            alert.close();
        }
    }
    
    /**
     * This method handles the search part button being pressed
     * @param buttonClicked action that kicks off the part search
     */
    @FXML public void searchPartButtonClicked(ActionEvent buttonClicked) {
        String searchPartQuery = partSearchTextField.getText();
        if (searchPartQuery.equals("")){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Part Search Warning");
                alert.setHeaderText("No parts found!");
                alert.setContentText("Enter a part ID or name to search for!");
                alert.showAndWait();
                
                loadParts();
                
        }
        else {
        boolean partFound = false;
            try {
                Part searchedPartId = Inventory.lookupPart(Integer.parseInt(searchPartQuery));
                if (searchedPartId != null) {
                    ObservableList<Part> returnedPartsList = FXCollections.observableArrayList();
                    returnedPartsList.add(searchedPartId);
                    partTableView.setItems(returnedPartsList);
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Part Search Warning");
                    alert.setHeaderText("There were no parts found!");
                    alert.setContentText("The search term entered does not match any part ID!");
                    alert.showAndWait();
                }
            }
            catch (NumberFormatException e) {
                ObservableList<Part> searchedPartList = FXCollections.observableArrayList();
                searchedPartList = Inventory.lookupPart(searchPartQuery);
                if (searchedPartList != null) {
                    partTableView.setItems(searchedPartList);
                    partFound = true;
                }
                if (partFound == false) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Part Search Warning");
                    alert.setHeaderText("No parts found!");
                    alert.setContentText("The searched term does not match a part name!");
                    alert.showAndWait();
                }
            }    
        }
    }
    
    /**
     * This method loads the Add Product Screen when Add Product Button is clicked
     * @param buttonClicked action that kicks off the loading of the add product scene
     * @throws Exception 
     */
    @FXML public void addProductButtonClicked(ActionEvent buttonClicked) throws Exception {
        loadNewScreen("AddProductScreen.fxml", buttonClicked, "Add Product");
    }
        
    /**
     * This method loads the Modify Product Screen when Modify Product Button is clicked
     * @param buttonClicked action that kicks off the loading of the modify product scene
     * @throws Exception 
     */
    @FXML public void modifyProductButtonClicked(ActionEvent buttonClicked) throws Exception {
        selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        selectedProductIndexNumber = getAllProducts().indexOf(selectedProduct);
        if(selectedProduct != null){
            loadNewScreen("ModifyProductScreen.fxml", buttonClicked, "Modify Product");  
        }
        else{
            Alert blankAlert = new Alert(Alert.AlertType.ERROR);
            blankAlert.setTitle("Product Modification Error");
            blankAlert.setHeaderText("The product cannot be modified!");
            blankAlert.setContentText("There was no part selected!");
            blankAlert.showAndWait();
        }
    }
    
    /**
     * This method deletes the selected product when the Delete Product Button is clicked
     * @param buttonClicked the delete product button is pressed to kick off this method
     * @throws IOException 
     */
    @FXML public void deleteProductButtonClicked(ActionEvent buttonClicked) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Product");
        alert.setHeaderText("Are you sure you want to delete this product?");
        alert.setContentText("Press OK to delete the product. \nPress Cancel to cancel the deletion.");
        alert.showAndWait();
        
        if (alert.getResult() == ButtonType.OK) {
            try {
                Product productToDelete = productTableView.getSelectionModel().getSelectedItem();
                if (productToDelete.getAllAssociatedParts() == null) {
                    deleteProduct(productToDelete);
                }
                else {
                    Alert associatedPartAlert = new Alert(Alert.AlertType.ERROR);
                    associatedPartAlert.setTitle("Product Deletion Error");
                    associatedPartAlert.setHeaderText("The product was NOT deleted!");
                    associatedPartAlert.setContentText("Selected product has associated parts!");
                    associatedPartAlert.showAndWait();
                }
            }
            catch (NullPointerException e) {
                Alert nullalert = new Alert(Alert.AlertType.ERROR);
                nullalert.setTitle("Product Deletion Error");
                nullalert.setHeaderText("The product was NOT deleted!");
                nullalert.setContentText("There was no product selected!");
                nullalert.showAndWait();
            }
        }
        else {
            alert.close();
        }
    }
    
    // 
    /**
     * This method searches for the product in the search box
     * @param buttonClicked action that kicks off the product search
     * @throws IOException 
     */
    @FXML public void searchProductButtonClicked(ActionEvent buttonClicked) throws IOException {
        String searchProductQuery = productSearchTextField.getText();
        if (searchProductQuery.equals("")){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Produt Search Warning");
                alert.setHeaderText("No products found!");
                alert.setContentText("Enter a product ID or name to search for!");
                alert.showAndWait();
                
                loadProducts();
        }
        else {
        boolean productFound = false;
            try {
                Product searchedProduct = Inventory.lookupProduct(Integer.parseInt(searchProductQuery));
                if (searchedProduct != null) {
                    ObservableList<Product> returnedProductsList = FXCollections.observableArrayList();
                    returnedProductsList.add(searchedProduct);
                    productTableView.setItems(returnedProductsList);
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Product Search Warning");
                    alert.setHeaderText("There were no products found!");
                    alert.setContentText("The search term entered does not match any product ID!");
                    alert.showAndWait();
                }
            }
            catch (NumberFormatException e) {
                ObservableList<Product> searchedProductList = FXCollections.observableArrayList();
                searchedProductList = Inventory.lookupProduct(searchProductQuery);
                if (searchedProductList != null) {
                    productTableView.setItems(searchedProductList);
                    productFound = true;
                }
                if (productFound == false) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Product Search Warning");
                    alert.setHeaderText("No products found!");
                    alert.setContentText("The searched term does not match a product name!");
                    alert.showAndWait();
                }
            }
        }
    }
    
    /**
     * This method exits the application when Exit button is clicked
     * @param buttonClicked kicks off the exit button method
     */
    @FXML public void exitButtonClicked (ActionEvent buttonClicked) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Inventory Management System");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Press OK to exit the program. \nPress Cancel to stay on this screen.");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            Stage mainScreen = (Stage)((Node)buttonClicked.getSource()).getScene().getWindow();
            mainScreen.close();
        }
        else {
            alert.close();
        }
    }
    
    /**
     * Used for accessing part information on the modify parts screen
     * @return the selected part
     */
    public static Part getSelectedPart() {
        return selectedPart;
    }
    
    /**
     * Used for accessing part index number information on the modify parts screen
     * @return the selected part index number
     */
    public static int getSelectedPartIndexNumber() {
        return selectedPartIndexNumber;
    }

    /**
     * Used for accessing product information on the modify products screen
     * @return the selected product
     */
    public static Product getSelectedProduct() {
        return selectedProduct;
    }
    
    /**
     * Used for accessing product index number information on the modify products screen
     * @return the selected product index number
     */
    public static int getSelectedProductIndexNumber() {
        return selectedProductIndexNumber;
    }  

    /**
     * Loads all parts into the part table view
     */
    @FXML public void loadParts(){
        partTableView.setItems(getAllParts());
    }
    
    /**
     * Loads all products into the product table view
     */
    @FXML public void loadProducts(){
        productTableView.setItems(getAllProducts());
    }
    
    /**
     * @param fxmlScreen the name of the next FXML screen to load
     * @param actionEvent the event that triggers the new scene to load
     * @param title the title of the new screen
     * @throws Exception 
     */
    @FXML public void loadNewScreen(String fxmlScreen, ActionEvent actionEvent, String title) throws Exception{
        Parent newScreen = FXMLLoader.load(getClass().getResource(fxmlScreen));
        Scene newScene = new Scene(newScreen);
        Stage newStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        newStage.setTitle(title);
        newStage.setScene(newScene);
        newStage.show(); 
    }
    
    /**
     * Checks for invalid input in the text fields on part creation
     * @param stock stock to validate
     * @param price price to validate
     * @param max max to validate
     * @param min min to validate
     * @param errorValidator list of errors to return
     * @return the list of errors
     */
    public static String textFieldValidator(int stock, double price, int max, int min, String errorValidator){
        
        if(stock < 1){
            errorValidator += "\nInventory must be greater than 0.";
        }
        if(price <= 0){
            errorValidator += "\nPrice must be more than $0.";
        }
        if(max < min){
            errorValidator += "\nMax must be greater than min.";
        }
        if(stock > max){
            errorValidator += "\nInventory cannot exceed the max.";
        }
        if(stock < min){
            errorValidator += "\nInventory cannot be less than min.";
        }
        return errorValidator;
    }
    
    /**
     * Checks for empty fields on part creation
     * @param name name to validate
     * @param stock stock to validate
     * @param price stock to validate
     * @param max max to validate
     * @param min min to validate
     * @param emptyValidator list of errors to return
     * @return the list of errors
     */
    public static String emptyTextFieldValidator(String name, String stock, String price, String max, String min, String emptyValidator){
        
        if(name.equals("")){
            emptyValidator += "\nThe name field cannot be empty!";
        }
        if(stock.equals("")){
            emptyValidator += "\nThe inventory field cannot be empty!";
        }
        if(price.equals("")){
            emptyValidator += "\nThe price field cannot be empty!";
        }
        if(max.equals("")){
            emptyValidator += "\nThe max field cannot be empty!";
        }
        if(min.equals("")){
            emptyValidator += "\nThe min field cannot be empty!";
        }
        return emptyValidator;
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Loads the parts and products into the tables
        partIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryTableColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        loadParts();
        productIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryTableColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        loadProducts();
    }    
    
}
