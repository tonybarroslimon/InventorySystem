package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author antonio
 */
public class InventorySystem extends Application {
    
    /**
     * Loads the initial screen of the application
     * @param stage the stage to load
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        
        
        Parent root = FXMLLoader.load(getClass().getResource("/ViewController/MainScreen.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
