
package workshop_jafx_jdbc;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import workshop.entites.Department;
import workshop.entites.Seller;
import workshop.services.SellerService;
import workshop.util.Utils;

public class SellerListController implements Initializable {
    private SellerService service;
    @FXML
    private TableView<Seller> tableViewSeller;
    @FXML
    private TableColumn<Seller, Integer> tableColumnId;
    @FXML
    private TableColumn<Seller, String> tableColumnName;
    @FXML
    private TableColumn<Seller, String> tableColumnEmail;
    @FXML
    private TableColumn<Seller, Date> tableColumnBirthDate;
    @FXML
    private TableColumn<Seller, Double> tableColumnBaseSalary;
    @FXML
    private Button btNew;
    
    private ObservableList<Seller> obsList;
    
    @FXML
    public void onBtNewAction(ActionEvent event){
        
    }
    
    public void setSellerService(SellerService service){
        this.service = service;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeNodes();
    }
    private void initializeNodes(){
        tableColumnId.setCellValueFactory(new PropertyValueFactory("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory("name"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory("email"));
        tableColumnBirthDate.setCellValueFactory(new PropertyValueFactory("birthDate"));
        Utils.formatTableColumnDate(tableColumnBirthDate, "dd/MM/yyyy");
        tableColumnBaseSalary.setCellValueFactory(new PropertyValueFactory("baseSalary"));
        Utils.formatTableColumnDouble(tableColumnBaseSalary, 2);
        
        Stage stage = (Stage) Workshop_jafx_jdbc.getScene().getWindow();
        tableViewSeller.prefHeightProperty().bind(stage.heightProperty());
        
    }
    public void updateTableView(){
        if (service == null) {
            throw new IllegalStateException("Service was null"); 
        }
        List<Seller> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);
        tableViewSeller.setItems(obsList);
    }
    
}
