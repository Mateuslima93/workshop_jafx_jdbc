/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workshop_jafx_jdbc;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

/**
 *
 * @author MASTER
 */
public class FXMLDocumentController implements Initializable {
    @FXML
    private MenuItem menuItemSeller;
    @FXML
    private MenuItem menuItemDepartment;
    @FXML
    private MenuItem menuItemAbout;
    
    @FXML 
    public void onMenuItemSellerAction(){
        System.out.println("onMenuItemSellerAction");
    }
    @FXML
    public void onMenuItemDepartmentAction(){
        System.out.println("onMenuItemDepartmentAction");
    }
    @FXML
    public void onMenuItemAboutAction(){
        System.out.println("onMenuItemAboutAction");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
