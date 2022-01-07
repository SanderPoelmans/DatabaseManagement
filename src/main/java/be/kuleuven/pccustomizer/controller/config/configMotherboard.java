package be.kuleuven.pccustomizer.controller.config;

import be.kuleuven.pccustomizer.controller.Objects.MotherBoard;
import be.kuleuven.pccustomizer.ProjectMain;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class configMotherboard extends _beheerConfig {
    List<MotherBoard> motherBoards = new ArrayList<MotherBoard>();
    //table
    @FXML
    private TableView<MotherBoard> tableView;

    @FXML
    private TableColumn<MotherBoard, String> nameColumn;
    @FXML
    private TableColumn<MotherBoard, Boolean> hasWifiColumn;
    @FXML
    private TableColumn<MotherBoard, Integer> priceColumn;
    @FXML
    private TableColumn<MotherBoard, String> caseSizeColumn;
    @FXML
    private TableColumn<MotherBoard, Integer> RAMSlotsColumn;
    @FXML
    private TableColumn<MotherBoard, Integer> PCIESlotsColumn;

    public void initialize() {
        ReadFromDB();
        initTable();
        btnAdd.setOnAction(e -> showBeheerScherm("Cooling"));
        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
    }

    private void showBeheerScherm(String id) {
        var resourceName = "config" + id + ".fxml";
        try {
            var stageCur = (Stage) btnClose.getScene().getWindow();
            stageCur.close();
            var stage = new Stage();
            var root = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource(resourceName));
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Admin " + id);
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();

        } catch (Exception e) {
            throw new RuntimeException("Kan beheerscherm " + resourceName + " niet vinden", e);
        }
    }

    public void ReadFromDB(){
        List<String> names = readDBstring("MotherBord","Name");
        List<Boolean> hasWifis =  readDBbool("MotherBord","Wifi");
        List<Integer> prices =  readDBint("MotherBord","Price");
        List<String> caseSizes =  readDBstring("MotherBord","Required_case_size");
        List<Integer> ramSlots =  readDBint("MotherBord","RAM_slots");
        List<Integer> pcieSlots =  readDBint("MotherBord","PCI_express_slots");

        for(int i = 0; i < names.size(); i++){
            motherBoards.add(new MotherBoard(names.get(i), hasWifis.get(i), prices.get(i), caseSizes.get(i),
                    ramSlots.get(i), pcieSlots.get(i)));
        }
    }

    public void initTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<MotherBoard, String>("name"));
        hasWifiColumn.setCellValueFactory(new PropertyValueFactory<MotherBoard, Boolean>("hasWifi"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<MotherBoard, Integer>("price"));
        caseSizeColumn.setCellValueFactory(new PropertyValueFactory<MotherBoard, String>("caseSize"));
        RAMSlotsColumn.setCellValueFactory(new PropertyValueFactory<MotherBoard, Integer>("RAMSlots"));
        PCIESlotsColumn.setCellValueFactory(new PropertyValueFactory<MotherBoard, Integer>("PCIESlots"));

        ObservableList<MotherBoard> MotherBoardList = tableView.getItems();
        MotherBoardList.addAll(motherBoards);
        tableView.setItems(MotherBoardList);
    }
}
