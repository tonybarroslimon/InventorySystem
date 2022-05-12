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
import static Model.Inventory.addPart;
import Model.Outsourced;
import Model.Part;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import static ViewController.MainScreenController.getSelectedPart;
import static ViewController.MainScreenController.getSelectedPartIndexNumber;
import static Model.Inventory.updatePart;
import static ViewController.MainScreenController.emptyTextFieldValidator;
import static ViewController.MainScreenController.textFieldValidator;
/**
 * FXML Controller class
 *
 * @author antonio
 */
public class ModifyPartScreenController implements Initializable {

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
    @FXML private boolean isInHouse;
    @FXML private int partID;
    @FXML private Part selectedPart;
    // @FXML private InHouse inHouseSelectedPart;
    // @FXML private Outsourced outsourcedSelectedPart;
    
    /**
     * updates the radio button label whenever a new button is pressed
     * @param event the event that kicks off the radio button being changed
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
                updatePart(getSelectedPartIndexNumber(), inHousePart);
                    
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
                updatePart(getSelectedPartIndexNumber(), outsourcedPart);
                    
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
     * 
     * Clicking the Modify part button from the main page was not loading.
     * As far as I could see the code was correct. The trace error from
     * console was showing that clicking the Modify part button was giving
     * a Null Pointer Exception. Originally I thought the issue was coming
     * from the Main Screen Controller. So to troubleshoot, I commented all
     * of my code out and started uncommenting pieces to get the screen to load.
     * As soon as the Modify parts screen loaded, I realized that the problem
     * was actually in the below code. I uncommented each row and tested to see
     * if the screen would load. It would break at the "if" statement. I 
     * assumed that I was using the 'instaceof' incorrectly. I started reading
     * articles about it and saw people using 'isInstance' instead of
     * 'instanceof' but that was not solving my problem. All this to say, 
     * I had updated the text fields name and assumed it would update the .fxml
     * file. It obviously did not so I went and updated the text field name and
     * everything loaded as expected.
     * 
     * This project was a lot of fun to work through. Going forward, if I were
     * going to expand the functionality, I would create a way to export all parts
     * or products to a Comma Separated File so that if someone wanted to do some
     * analysis of the data in the system they could do so easily. That is a
     * standard feature of products like this in the real world and needed in most
     * modern systems. The other thing, I would add an actual database so that the
     * data would persist. This would require creating the database and creating the
     * tables based on the Models we created here and then we would need to figure
     * out how to connect the database to the application.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize Toggle Group
        radioButtonSelected = new ToggleGroup();
        this.inHouseRadioButton.setToggleGroup(radioButtonSelected);
        this.outsourcedRadioButton.setToggleGroup(radioButtonSelected);
        
        
        // Loads the selected part to modify
        selectedPart = getSelectedPart();
        partID = selectedPart.getId();
        idTextField.setText("Generated ID: " + partID);
        nameTextField.setText(selectedPart.getName());
        invTextField.setText(Integer.toString(selectedPart.getStock()));
        priceCostTextField.setText(Double.toString(selectedPart.getPrice()));
        maxTextField.setText(Integer.toString(selectedPart.getMax()));
        minTextField.setText(Integer.toString(selectedPart.getMin()));
        if (selectedPart instanceof InHouse) {
            radioButtonSelected.selectToggle(inHouseRadioButton);
            companyNameMachineIDTextField.setText(Integer.toString(((InHouse) selectedPart).getMachineId()));
            companyNameMachineIDTextField.setPromptText("Machine ID");
            companyNameMachineIDLabel.setText("Machine ID");
            isInHouse = true;
        }
        else {
            radioButtonSelected.selectToggle(outsourcedRadioButton);
            companyNameMachineIDTextField.setText(((Outsourced) selectedPart).getCompanyName());
            companyNameMachineIDTextField.setPromptText("Company Name");
            companyNameMachineIDLabel.setText("Company Name");
            isInHouse = false;
        }
        
    }    
    
}
