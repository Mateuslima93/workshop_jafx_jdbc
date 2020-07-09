/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workshop_jafx_jdbc;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import workshop.entites.Department;
import workshop.util.Constraints;

/**
 *
 * @author MASTER
 */
public class DepartmentFormController implements Initializable {
    private Department entity;
    @FXML
    private TextField txtId;
    
    @FXML
    private TextField txtName;
    
    @FXML
    private Label labelErrorName;
    
    @FXML
    private Button btSave;
    
    @FXML
    private Button btCancel;
    
    @FXML
    public void onBtSaveAction(){
        System.out.println("onBtSaveAction");
    }
    @FXML
    public void onBtCancelAction(){
        System.out.println("onBtCancelAction");
    }
    public void setDepartmentForm(Department entity){
        this.entity = entity;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeNodes();
    }
 
    private void initializeNodes(){
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 30);
    }
    public void updateFormData(){
        if (entity == null) {
            throw new IllegalStateException("Entity was null");
        }
        txtId.setText(String.valueOf(entity.getId()));
        txtName.setText(entity.getName());
    }
}
