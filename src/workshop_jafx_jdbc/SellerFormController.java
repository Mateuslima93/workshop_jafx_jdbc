
package workshop_jafx_jdbc;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import workshop.entites.Department;
import workshop.entites.Seller;
import workshop.exceptions.ValidationException;
import workshop.services.SellerService;
import workshop.util.Constraints;
import workshop.util.Utils;

public class SellerFormController implements Initializable {
private Seller entity;
private SellerService service;

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

    public void setSeller(Seller entity){
        this.entity = entity;
    }
    public void setSellerService(SellerService service){
        this.service = service;
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
            
        }
        catch(){
            
        }
        catch(){
            
        }
    }
    @FXML
    public void onBtCancelAction(ActionEvent event){
        Utils.currentStage(event);
    }
    
}
