/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import static Model.Inventory.addPart;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import static ViewController.MainScreenController.emptyTextFieldValidator;
import static ViewController.MainScreenController.textFieldValidator;


/**
 * FXML Controller class
 *
 * @author antonio
 */
public class AddPartScreenController implements Initializable {

    @FXML private RadioButton inHouseRadioButton;
    @FXML private RadioButton outsourcedRadioButton;
    @FXML private TextField idTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField invTextField;
    @FXML private TextField priceCostTextField;
    @FXML private TextField maxTextField;
    @FXML private TextField minTextField;
    @FXML private TextField companyNameMachineIDTextField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    private ToggleGroup radioButtonSelected;
    @FXML private Label companyNameMachineIDLabel;
    @FXML private int partIdCount;
    @FXML private String emptyPartField = new String();
    @FXML private String partValid = new String();
    @FXML private String partParsable = new String();
    @FXML private boolean isInHouse;
    
    /**
     * updates the radio button label whenever a new button is pressed
     * @param event that is kicked off when a radio button gets changed
     * @throws Exception 
     */
    @FXML public void radioButtonChanged(ActionEvent event) throws Exception{
         if (this.radioButtonSelected.getSelectedToggle().equals(this.inHouseRadioButton)){
             companyNameMachineIDTextField.setPromptText("Machine ID");
             companyNameMachineIDLabel.setText("Machine ID");
             isInHouse = true;
         }
         if (this.radioButtonSelected.getSelectedToggle().equals(this.outsourcedRadioButton)){
             companyNameMachineIDTextField.setPromptText("Company Name");
             companyNameMachineIDLabel.setText("Company Name");
             isInHouse = false;
         }
    }
    
    /**
     * handles the Cancel button being pressed
     * @param event event that kicks off when the cancel button is pressed
     * @throws IOException 
     */
    @FXML void cancelButtonPressed(ActionEvent event) throws IOException {
        try {
            // Alert to confirm cancel action
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit to Main Screen?");
            alert.setHeaderText("Are you sure you want to cancel?");
            alert.setContentText("978-0-13-590991-1");
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
     * handles the Save button being pressed
     * @param event event that kicks off when the save button is pressed
     * @throws IOException 
     */
    @FXML void saveButtonPressed(ActionEvent event) throws IOException {
        String nameField = nameTextField.getText();
        String stockField = invTextField.getText();
        String priceField = priceCostTextField.getText();
        String maxField = maxTextField.getText();
        String minField = minTextField.getText();
        String companyMachineIdField = companyNameMachineIDTextField.getText();

        emptyPartField = emptyTextFieldValidator(
            nameField,
            stockField,
            priceField,
            maxField,
            minField,
            emptyPartField
            );
        
        try {
            partValid = textFieldValidator(
                Integer.parseInt(stockField), 
                Double.parseDouble(priceField), 
                Integer.parseInt(maxField), 
                Integer.parseInt(minField), 
                partValid
                );
            
            if (partValid.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Part Addition Warning");
                alert.setHeaderText("The part was NOT added!");
                alert.setContentText("Invalid data input in one or more fields!" + partValid);
                alert.showAndWait();
                partValid = "";
            }
            else {
            if (isInHouse == true) {
                InHouse inHousePart = new InHouse(
                    partIdCount,
                    nameField,
                    Double.parseDouble(priceField),
                    Integer.parseInt(stockField),
                    Integer.parseInt(minField),
                    Integer.parseInt(maxField),
                    Integer.parseInt(companyMachineIdField)
                    );
                addPart(inHousePart);
                    
                loadMainScreen(event);
                }
            else {
                Outsourced outsourcedPart = new Outsourced(
                    partIdCount,
                    nameField,
                    Double.parseDouble(priceField),
                    Integer.parseInt(stockField),
                    Integer.parseInt(minField),
                    Integer.parseInt(maxField),
                    companyMachineIdField
                    );
                addPart(outsourcedPart);
                    
                loadMainScreen(event);
                }
            }
            }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Part Addition Warning");
            alert.setHeaderText("The part was NOT added!");
            alert.setContentText("Invalid data input in one or more fields!" + partValid + emptyPartField);
            alert.showAndWait();
            partValid = "";
            emptyPartField = "";
            }
        }
    
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
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // This is to intitalize the label and the textfield
        companyNameMachineIDLabel.setText("Machine ID");
        companyNameMachineIDTextField.setPromptText("Machine ID");
        isInHouse = true;
        
        // Initialize Toggle Group
        radioButtonSelected = new ToggleGroup();
        this.inHouseRadioButton.setToggleGroup(radioButtonSelected);
        this.outsourcedRadioButton.setToggleGroup(radioButtonSelected);
        
        // Set the default radio button
        inHouseRadioButton.setSelected(true);
        
        // Set the prompt text for the ID text field
        partIdCount = Inventory.increasePartID();
        idTextField.setEditable(false);
        idTextField.setText("Generated ID: " + partIdCount);
    }    
    
}
