/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workshop_jafx_jdbc;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import workshop.db.DbException;
import workshop.entites.Department;
import workshop.exceptions.ValidationException;
import workshop.listeners.DataChangeListener;
import workshop.services.DepartmentService;
import workshop.util.Alerts;
import workshop.util.Constraints;
import workshop.util.Utils;

/**
 *
 * @author MASTER
 */
public class DepartmentFormController implements Initializable {
    private Department entity;
    private DepartmentService service;
    private List <DataChangeListener> dataChangeListeners = new ArrayList <>();
    
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
    public void onBtSaveAction(ActionEvent event){
        if (entity == null) {
            throw new IllegalStateException("Entity was null");
        }
        if (service == null) {
            throw new IllegalStateException("Service was null");
        }
        try{
            entity = getFormData();
            service.saveOrUpdate(entity);
            notifyDataChangeListeners();
            Utils.currentStage(event).close();
        }
        catch(ValidationException e){
            setErrorMessage(e.getErrors());
        }
        catch(DbException e){
            Alerts.showAlert("Error saving object", null, e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    @FXML
    public void onBtCancelAction(ActionEvent event){
        Utils.currentStage(event).close();
    }
    public void setDepartmentForm(Department entity){
        this.entity = entity;
    }
    public void setDepartmentService(DepartmentService service){
        this.service = service;
    }
    public void subscribeDataChangeListeners(DataChangeListener listener){
        dataChangeListeners.add(listener);
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

    private Department getFormData() {
        Department obj = new Department();
       
        ValidationException exception = new ValidationException("Validation Error");
        
        obj.setId(Utils.tryParseInt(txtId.getText()));
        if (txtName.getText()==null || txtName.getText().trim().equals("")) {
            exception.addErrors("name", "Field can't be empty");
        }
        obj.setName(txtName.getText());
        if (exception.getErrors().size()> 0) {
            throw exception;
        }
        return obj;
    }

    private void notifyDataChangeListeners() {
        dataChangeListeners.forEach((listener) -> {listener.onDataChanged();});
    }
    
    private void setErrorMessage (Map<String,String> errors){
       Set<String> fields = errors.keySet();
        if (fields.contains("name")) {
            labelErrorName.setText(errors.get("name"));
        }
    
    }
}
