
package workshop_jafx_jdbc;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import workshop.entites.Department;
import workshop.services.DepartmentService;

public class DepartmentListControler implements Initializable {
    private DepartmentService service;
    @FXML
    private TableView<Department> tableViewDepartment;
    @FXML
    private TableColumn<Department, Integer> tableColumnId;
    @FXML
    private TableColumn<Department, String> tableColumnName;
    @FXML
    private Button btNew;
    
    private ObservableList<Department> obsList;
    
    @FXML
    public void onBtNewAction(){
        System.out.println("onBtNewAction");
    }
    public void setDepartmentService (DepartmentService service){
        this.service=service;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeNodes();
    }

    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory("name"));
        
        Stage stage = (Stage) Workshop_jafx_jdbc.getScene().getWindow();
        tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
        //TableView acompanha altura da janela
    }
    
    public void updateTableView(){
        if (service == null) {
            throw new IllegalStateException("Service was null");
        }
        List<Department> list = service.findall();
        obsList = FXCollections.observableArrayList(list);
        tableViewDepartment.setItems(obsList);
    }
}
