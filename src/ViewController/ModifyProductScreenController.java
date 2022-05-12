/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import Model.Inventory;
import static Model.Inventory.getAllParts;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import static ViewController.MainScreenController.getSelectedProduct;
import static ViewController.MainScreenController.getSelectedProductIndexNumber;
import static Model.Inventory.updateProduct;
import static ViewController.MainScreenController.emptyTextFieldValidator;
import static ViewController.MainScreenController.textFieldValidator;


/**
 * FXML Controller class
 *
 * @author antonio
 */
public class ModifyProductScreenController implements Initializable {

    @FXML private TextField idTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField invTextField;
    @FXML private TextField costTextField;
    @FXML private TextField maxTextField;
    @FXML private TextField minTextField;
    @FXML private Button searchButton;
    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private TextField searchTextField;
    @FXML private TableView<Part> searchResultsTableView;
    @FXML private TableView<Part> addedResultsTableView;
    @FXML private TableColumn<Part, Integer> searchResultsPartIDTableColumn;
    @FXML private TableColumn<Part, String> searchResultsPartNameTableColumn;
    @FXML private TableColumn<Part, Integer> searchResultsInventoryLevelTableColumn;
    @FXML private TableColumn<Part, Double> searchResultsPricePerUnitTableColumn;
    @FXML private TableColumn<Part, Integer> addedResultsPartIDTableColumn;
    @FXML private TableColumn<Part, String> addedResultsPartNameTableColumn;
    @FXML private TableColumn<Part, Integer> addedResultsInventoryLevelTableColumn;
    @FXML private TableColumn<Part, Double> addedResultsPricePerUnitTableColumn;
    @FXML private int productID;
    @FXML private String blankProductField = new String();
    @FXML private String validProductField = new String();
    @FXML private ObservableList<Part> returnedPartsList = FXCollections.observableArrayList();
    @FXML private ObservableList<Part> addedPartsList = FXCollections.observableArrayList();
    
    /**
     * loads main screen
     * @param event event that kicks off loading the main screen
     * @throws IOException 
     */
    @FXML void loadMainScreen(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage mainScreen = (Stage)((Node)event.getSource()).getScene().getWindow();
        mainScreen.setTitle("Inventory Management System");
        mainScreen.setScene(scene);
        mainScreen.show();
    }
    
    /**
     * This function searches for the parts in the search box
     * @param buttonClicked event that kicks off when the search button is clicked
     */
    @FXML public void searchButtonClicked(ActionEvent buttonClicked) {
        String searchPartQuery = searchTextField.getText();
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
                Part searchedPart = Inventory.lookupPart(Integer.parseInt(searchPartQuery));
                if (searchedPart != null) {
                    ObservableList<Part> returnedPartsList = FXCollections.observableArrayList();
                    returnedPartsList.add(searchedPart);
                    searchResultsTableView.setItems(returnedPartsList);
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
                    searchResultsTableView.setItems(searchedPartList);
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
     * handles the add button being pressed
     * @param event event that kicks off the add button being pressed
     */
    @FXML void addButtonPressed(ActionEvent event) {
        Part part = searchResultsTableView.getSelectionModel().getSelectedItem();
        if (part == null) {
            Alert noPartSelected = new Alert(Alert.AlertType.ERROR);
            noPartSelected.setTitle("Associated Part Addition Error");
            noPartSelected.setHeaderText("The part was not added!");
            noPartSelected.setContentText("A part was not selected!");
            noPartSelected.showAndWait();
        }
        else {
            addedPartsList = addedResultsTableView.getItems();
            addedPartsList.add(part);
            addedResultsTableView.setItems(addedPartsList);
        }
    }
    
    /**
     * Keeps track and saves added parts
     * @param part to add
     */
    @FXML public void addButtonAssociatedPart(Part part) {
        returnedPartsList.add(part);
    }
    
    /**
     * All of the associated parts that were added to table view
     * @return all of the parts list
     */
    @FXML public ObservableList<Part> getAllAssociatedPartsFromAddButton() {
        return returnedPartsList;
    }
    
    /**
     * handles the save button being pressed
     * @param event event that kicks off when the save button is pressed
     * @throws IOException 
     */
    @FXML void saveButtonPressed(ActionEvent event) throws IOException {
        String productName = nameTextField.getText();
        String productInventory = invTextField.getText();
        String productCost = costTextField.getText();
        String productMax = maxTextField.getText();
        String productMin = minTextField.getText();
        ObservableList<Part> associatedProductParts = addedResultsTableView.getItems();
        
        blankProductField = emptyTextFieldValidator(
            productName, 
            productInventory, 
            productCost, 
            productMax, 
            productMin, 
            blankProductField);
                
        try {
            validProductField = textFieldValidator(
                Integer.parseInt(productInventory), 
                Double.parseDouble(productCost), 
                Integer.parseInt(productMax), 
                Integer.parseInt(productMin), 
                validProductField);
            
            if (validProductField.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Part Addition Warning");
                alert.setHeaderText("The part was NOT added!");
                alert.setContentText("Invalid data input in one or more fields!" + validProductField);
                alert.showAndWait();
                validProductField = "";
            }
            else{
            if (associatedProductParts.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Product Save Error");
                alert.setHeaderText("The product wasn't saved!");
                alert.setContentText("An associated part was not selected!");
                alert.showAndWait();
            }
            else {
                Product newProduct = new Product(
                    productID,
                    productName,
                    Double.parseDouble(productCost),
                    Integer.parseInt(productInventory),
                    Integer.parseInt(productMin),
                    Integer.parseInt(productMax)          
                    );
                
                Inventory.updateProduct(getSelectedProductIndexNumber(), newProduct);
                
                for (Part partsInAssociatedProductParts: associatedProductParts) {
                    newProduct.addAssociatedPart(partsInAssociatedProductParts);
                }

                loadMainScreen(event);
            }
            
        }
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Product Addition Warning");
            alert.setHeaderText("The product wasn't added!");
            alert.setContentText("Invalid data input in one or more fields!" + blankProductField + validProductField);
            alert.showAndWait();
            blankProductField = "";
            validProductField = "";
        }
    }
    
    /**
     * handles the Cancel button being pressed
     * @param event event that kicks off once the cancel button is pressed
     * @throws IOException 
     */
    @FXML void cancelButtonPressed(ActionEvent event) throws IOException {
        try {
            // Alert to confirm cancel action
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit to Main Screen?");
            alert.setHeaderText("Are you sure you want to cancel?");
            alert.setContentText("Press OK to exit to Main Screen.\nPress Cancel to stay on this screen.");
            alert.showAndWait();
            
            // logic to load new screen
            if (alert.getResult() == ButtonType.OK){
                loadMainScreen(event);
            }
            else {
                alert.close();
            }
        }
        catch (IOException e) {}
    }
    
    /**
     * handles the Delete button being pressed
     * @param event event that kicks off once the delete button is pressed
     */
    @FXML void deleteButtonPressed(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Associated Part");
        alert.setHeaderText("Are you sure you want to delete the associated part?");
        alert.setContentText("Press OK to delete the associated part. \nPress Cancel to cancel the deletion.");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            try {
                Part part = addedResultsTableView.getSelectionModel().getSelectedItem();
                addedResultsTableView.getItems().removeAll(part);
            }
            catch (NullPointerException e) {
                Alert nullalert = new Alert(Alert.AlertType.ERROR);
                nullalert.setTitle("Associated Part Deletion Error");
                nullalert.setHeaderText("The part wasn't deleted!");
                nullalert.setContentText("A part wasn't selected!");
                nullalert.showAndWait();
            }
        }
        else {
            alert.close();
        }
    }
    
    /**
     * Loads all parts into the search results table view
     */
    @FXML public void loadParts(){
        searchResultsTableView.setItems(getAllParts());
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // sets the values for the search results table
        searchResultsPartIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        searchResultsPartNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        searchResultsInventoryLevelTableColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        searchResultsPricePerUnitTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        loadParts();
        
        // sets the values for the added parts table
        addedResultsPartIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addedResultsPartNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addedResultsInventoryLevelTableColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addedResultsPricePerUnitTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        // sets the product text fields with the selected Product to modify
        Product selectedProduct = getSelectedProduct();
        productID = selectedProduct.getId();
        idTextField.setText("Generated ID: " + productID);
        nameTextField.setText(selectedProduct.getName());
        invTextField.setText(Integer.toString(selectedProduct.getStock()));
        costTextField.setText(Double.toString(selectedProduct.getPrice()));
        maxTextField.setText(Integer.toString(selectedProduct.getMax()));
        minTextField.setText(Integer.toString(selectedProduct.getMin()));
        addedResultsTableView.setItems(selectedProduct.getAllAssociatedParts());  
    }    
    
}
