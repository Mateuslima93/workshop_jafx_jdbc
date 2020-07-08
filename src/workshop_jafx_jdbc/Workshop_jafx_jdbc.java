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
    private static Scene scene;
   
    @Override
    public void start(Stage stage) throws Exception {
        try{
        ScrollPane root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        //Ajustar Vbox ao ScrollPane
        root.setFitToHeight(true);
        root.setFitToWidth(true);
        
        scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Sample JavaFx Application");
        stage.show();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public static Scene getScene(){
        return scene;
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
