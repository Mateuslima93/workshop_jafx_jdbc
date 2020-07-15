
package workshop_jafx_jdbc;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import workshop.db.DbException;
import workshop.entites.Department;
import workshop.entites.Seller;
import workshop.exceptions.ValidationException;
import workshop.listeners.DataChangeListener;
import workshop.services.DepartmentService;
import workshop.services.SellerService;
import workshop.util.Alerts;
import workshop.util.Constraints;
import workshop.util.Utils;

public class SellerFormController implements Initializable {
private Seller entity;
private SellerService service;
private DepartmentService departmentService;
private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

@FXML
private TextField txtId;
@FXML
private TextField txtName;
@FXML
private TextField txtEmail;
@FXML
private DatePicker dpBirthDate;
@FXML
private TextField txtBaseSalary;
@FXML
private ComboBox<Department> comboBoxDepartment;
@FXML
private Label labelErrorName;
@FXML
private Label labelErrorEmail;
@FXML
private Label labelErrorBirthDate;
@FXML
private Label labelErrorBaseSalary;
@FXML
private Button btSave;
@FXML
private Button btCancel;

private ObservableList<Department> obsList;

    public void setSeller(Seller entity){
        this.entity = entity;
    }
    public void setServices(SellerService service, DepartmentService depService){
        this.service = service;
        this.departmentService = depService;
    }
    public void subscribeDataChangeListeners(DataChangeListener listener){
        dataChangeListeners.add(listener);
    }
    private void notifyDataChangeListeners(){
        dataChangeListeners.forEach((listener) -> {listener.onDataChanged();});
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeNodes();
    }
    private void initializeNodes(){
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 70);
        Constraints.setTextFieldMaxLength(txtEmail, 60);
        Constraints.setTextFieldDouble(txtBaseSalary);
        Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");
    }
    public void updateFormData(){
        if (entity == null) {
            throw new IllegalStateException("Entity was null");
        }
        txtId.setText(String.valueOf(entity.getId()));
        txtName.setText(entity.getName());
        txtEmail.setText(entity.getEmail());
        if (entity.getBirthDate() != null) {
            dpBirthDate.setValue(entity.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
        Locale.setDefault(Locale.US);
        txtBaseSalary.setText(String.format("%.2f",entity.getBaseSalary()));
        
                
    }
    public void loadAssociateObjects(){
        if (departmentService == null) {
            throw new IllegalStateException("DepartmentService was null");
        }
        List<Department> list = departmentService.findall();
        obsList = FXCollections.observableArrayList(list);
        comboBoxDepartment.setItems(obsList);
    }
    public Seller getFormData(){
        Seller obj = new Seller;
        
        ValidationException exception = new ValidationException("Validation Error");
        
        obj.setId(Utils.tryParseInt(txtId.getText()));
        
    } 
    @FXML
    public void onBtSaveAction(ActionEvent event){
        if (entity == null) {
            throw new IllegalStateException("Entity was null");
        }
        if (service == null){
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
            Alerts.showAlert("Error saving objects", null, e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    @FXML
    public void onBtCancelAction(ActionEvent event){
        Utils.currentStage(event);
    }
    
        private void setErrorMessage (Map<String,String> errors){
       Set<String> fields = errors.keySet();
        if (fields.contains("name")) {
            labelErrorName.setText(errors.get("name"));
        }
    
    }
    
    public void initializeComboBox(){
        Callback<ListView<Department>, ListCell<Department>> factory = lv -> new ListCell<Department>(){
            @Override
            protected void updateItem(Department item, boolean empty){
                super.updateItem(item,empty);
                setText(empty ? "" : item.getName());
        }
    };
    }
    
}
