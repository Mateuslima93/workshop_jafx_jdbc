/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workshop_jafx_jdbc;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

/**
 *
 * @author MASTER
 */
public class Workshop_jafx_jdbc extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        try{
        ScrollPane root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        root.setFitToHeight(true);
        root.setFitToWidth(true);
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Sample JavaFx Application");
        stage.show();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
