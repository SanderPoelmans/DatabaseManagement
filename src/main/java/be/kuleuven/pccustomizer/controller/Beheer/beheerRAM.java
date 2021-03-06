package be.kuleuven.pccustomizer.controller.Beheer;
import be.kuleuven.pccustomizer.controller.Objects.PSU;
import be.kuleuven.pccustomizer.controller.Objects.RAM;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class beheerRAM extends _BeheerCommon {
    private RAM modifiedRAM;
    private final List<RAM> rams = new ArrayList<RAM>();

    @FXML
    private TableView<RAM> tableView;
    @FXML
    private TextField addName;
    @FXML
    private TextField addType;
    @FXML
    private TextField addPrice;
    @FXML
    private TextField addSize;
    @FXML
    private TextField addNRofSticks;
    @FXML
    private TextField addAantal;
    @FXML
    private TableColumn<RAM, String> nameColumn;
    @FXML
    private TableColumn<RAM, String> typeColumn;
    @FXML
    private TableColumn<RAM, Integer> priceColumn;
    @FXML
    private TableColumn<RAM, Integer> sizeColumn;
    @FXML
    private TableColumn<RAM, Integer> NRofSticksColumn;
    @FXML
    private TableColumn<RAM, Integer> aantalColumn;

    public void initialize() {
        init(tableView);
    }

    public void ReadFromDB(){
        List<String> names = readDBstring("RAM","Name");
        List<String> types =  readDBstring("RAM","Type");
        List<Integer> prices =  readDBint("RAM","Price");
        List<Integer> sizes =  readDBint("RAM","Size");
        List<Integer> NRofSticks =  readDBint("RAM","Size");
        List<Integer> aantallen =  readDBint("RAM","Aantal");
        for(int i = 0; i < names.size(); i++){
            rams.add(new RAM(names.get(i), types.get(i), prices.get(i), sizes.get(i), NRofSticks.get(i), aantallen.get(i)));
        }
    }

    public void initTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<RAM, String>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<RAM, String>("type"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<RAM, Integer>("price"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<RAM, Integer>("size"));
        NRofSticksColumn.setCellValueFactory(new PropertyValueFactory<RAM, Integer>("NRofSticks"));
        aantalColumn.setCellValueFactory(new PropertyValueFactory<RAM, Integer>("aantal"));
        ObservableList<RAM> RAMList = tableView.getItems();
        RAMList.addAll(rams);
        tableView.setItems(RAMList);

    }

    public void deleteCurrentRow() {
        selectedRow = tableView.getSelectionModel().getSelectedIndex();
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            RAM ram = tableView.getSelectionModel().getSelectedItem();
            jdbi.useHandle(handle -> {
                handle.execute("DELETE FROM RAM WHERE Name = ?", ram.getName());
            });
            tableView.getItems().remove(selectedRow);
        }
    }

    public void addNewRow() {
        if(!doubles("RAM", "Name", addName.getText())) {
            RAM ram = new RAM(addName.getText(), addType.getText(), Integer.parseInt(addPrice.getText()), Integer.parseInt(addSize.getText()), Integer.parseInt(addNRofSticks.getText()), Integer.parseInt(addAantal.getText()));
            jdbi.useHandle(handle -> {
                handle.execute("insert into RAM (Name, Type ,Price, Size, Number_of_sticks, aantal) values (?, ?, ?, ?, ?, ?)",
                        ram.getName(), ram.getType(), ram.getPrice(), ram.getSize(), ram.getNRofSticks(), ram.getAantal());
            });

            ObservableList<RAM> RAMList = tableView.getItems();
            RAMList.add(ram);
            tableView.setItems(RAMList);
        }
        else{
            RAM ram = new RAM(addName.getText(), addType.getText(), Integer.parseInt(addPrice.getText()), Integer.parseInt(addSize.getText()), Integer.parseInt(addNRofSticks.getText()), Integer.parseInt(addAantal.getText()));
            jdbi.useHandle(handle -> {
                handle.execute("UPDATE RAM SET Aantal = ? WHERE Name = ?, Type = ?, Price = ?, Size = ?, Number_of_sticks = ?, aantal = ?",
                        ram.getAantal(), ram.getName(), ram.getType(), ram.getPrice(), ram.getSize(), ram.getNRofSticks());
            });
        }
    }

    public void LoadCurrentRow() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            RAM ram = tableView.getSelectionModel().getSelectedItem();
            addName.setText(ram.getName());
            addType.setText(ram.getType());
            addPrice.setText(String.valueOf(ram.getPrice()));
            addSize.setText(String.valueOf(ram.getSize()));
            addNRofSticks.setText(String.valueOf(ram.getNRofSticks()));
            addAantal.setText(String.valueOf(ram.getAantal()));

            modifiedRAM = new RAM(ram.getName(), ram.getType(),ram.getPrice(),ram.getSize(), ram.getNRofSticks(), ram.getAantal());
        }
    }

    public void modifyCurrentRow(){
        selectedRow = tableView.getSelectionModel().getSelectedIndex();

        modifiedRAM.setName(addName.getText());
        modifiedRAM.setType(addType.getText());
        modifiedRAM.setPrice(Integer.parseInt(addPrice.getText()));
        modifiedRAM.setSize(Integer.parseInt(addSize.getText()));
        modifiedRAM.setNRofSticks(Integer.parseInt(addNRofSticks.getText()));
        modifiedRAM.setAantal(Integer.parseInt(addAantal.getText()));

        jdbi.useHandle(handle -> {
            handle.execute("UPDATE RAM SET Name = ?, Type = ? , Price = ?, Size = ?, Number_of_sticks=?, Aantal = ?  WHERE Name = ?",
                    modifiedRAM.getName(), modifiedRAM.getType(), modifiedRAM.getPrice(), modifiedRAM.getSize(), modifiedRAM.getNRofSticks(), modifiedRAM.getAantal(), modifiedRAM.getName() );
        });

        ObservableList<RAM> RAMList = tableView.getItems();
        RAMList.set(selectedRow,modifiedRAM);
        tableView.setItems(RAMList);
    }
}
