package be.kuleuven.pccustomizer.controller.Beheer;
import be.kuleuven.pccustomizer.controller.Objects.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class beheerCPU extends _BeheerCommon {
    private CPU modifiedCPU;
    private final List<CPU> cpus = new ArrayList<CPU>();
    //table
    @FXML
    private TableView<CPU> tableView;
    //input text fields
    @FXML
    private TextField addName;
    @FXML
    private TextField addPrice;
    @FXML
    private TextField addThreads;
    @FXML
    private TextField addCores;
    @FXML
    private TextField addClockSpeed;
    @FXML
    private TextField addPowerUsage;
    @FXML
    private TextField addAantal;
    @FXML
    private TableColumn<CPU, String> nameColumn;
    @FXML
    private TableColumn<CPU, Integer> priceColumn;
    @FXML
    private TableColumn<CPU, Integer> threadsColumn;
    @FXML
    private TableColumn<CPU, Integer> coresColumn;
    @FXML
    private TableColumn<CPU, Integer> clockSpeedColumn;
    @FXML
    private TableColumn<CPU, Integer> powerUsageColumn;
    @FXML
    private TableColumn<CPU, Integer> aantalColumn;

    public void initialize() {
        init(tableView);
    }

    public void ReadFromDB(){
        List<String> names = readDBstring("CPU","Name");
        List<Integer> prices =  readDBint("CPU","Price");
        List<Integer> threads =  readDBint("CPU","Threads");
        List<Integer> cores =  readDBint("CPU","Cores");
        List<Integer> clockSpeeds =  readDBint("CPU","Clock_speed");
        List<Integer> powerUsages =  readDBint("CPU","Power_usage");
        List<Integer> aantallen =  readDBint("CPU","Aantal");

        for(int i = 0; i < names.size(); i++){
            cpus.add(new CPU(names.get(i), prices.get(i), threads.get(i), cores.get(i), clockSpeeds.get(i), powerUsages.get(i), aantallen.get(i)));
        }
    }

    public void initTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<CPU, String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<CPU, Integer>("price"));
        threadsColumn.setCellValueFactory(new PropertyValueFactory<CPU, Integer>("threads"));
        coresColumn.setCellValueFactory(new PropertyValueFactory<CPU, Integer>("cores"));
        clockSpeedColumn.setCellValueFactory(new PropertyValueFactory<CPU, Integer>("clockSpeed"));
        powerUsageColumn.setCellValueFactory(new PropertyValueFactory<CPU, Integer>("powerUsage"));
        aantalColumn.setCellValueFactory(new PropertyValueFactory<CPU, Integer>("aantal"));

        ObservableList<CPU> CPUList = tableView.getItems();
        CPUList.addAll(cpus);
        tableView.setItems(CPUList);
    }

    public void deleteCurrentRow() {
        selectedRow = tableView.getSelectionModel().getSelectedIndex();
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            CPU cpu = tableView.getSelectionModel().getSelectedItem();
            jdbi.useHandle(handle -> {
                handle.execute("DELETE FROM CPU WHERE Name = ?", cpu.getName());
            });
            tableView.getItems().remove(selectedRow);
        }
    }

    public void addNewRow() {
        if(!doubles("CPU", "Name", addName.getText())) {
            CPU cpu = new CPU(addName.getText(), Integer.parseInt(addPrice.getText()), Integer.parseInt(addThreads.getText()),
                    Integer.parseInt(addCores.getText()), Integer.parseInt(addClockSpeed.getText()), Integer.parseInt(addPowerUsage.getText()),
                    Integer.parseInt(addAantal.getText()));
            jdbi.useHandle(handle -> {
                handle.execute("insert into CPU (Name, Price, Threads, Cores, Clock_speed, Power_usage) values (?, ?, ?, ?, ?, ?)",
                        cpu.getName(), cpu.getPrice(), cpu.getThreads(), cpu.getCores(), cpu.getClockSpeed(), cpu.getPowerUsage());
            });
            ObservableList<CPU> CPUList = tableView.getItems();
            CPUList.add(cpu);
            tableView.setItems(CPUList);
        }
        else{
            CPU cpu = new CPU(addName.getText(), Integer.parseInt(addPrice.getText()), Integer.parseInt(addThreads.getText()),
                    Integer.parseInt(addCores.getText()), Integer.parseInt(addClockSpeed.getText()), Integer.parseInt(addPowerUsage.getText()),
                    Integer.parseInt(addAantal.getText()));
            jdbi.useHandle(handle -> {
                handle.execute("UPDATE CPU SET Aantal = ? WHERE Name= ?, Price = ?, Threads = ?, Cores = ?, Clock_speed = ?, Power_usage = ?",
                        cpu.getAantal(), cpu.getName(), cpu.getPrice(), cpu.getThreads(), cpu.getCores(), cpu.getClockSpeed(), cpu.getPowerUsage());
            });
        }
    }

    public void LoadCurrentRow() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            CPU cpu = tableView.getSelectionModel().getSelectedItem();
            addName.setText(cpu.getName());
            addPrice.setText(String.valueOf(cpu.getPrice()));
            addThreads.setText(String.valueOf(cpu.getThreads()));
            addCores.setText(String.valueOf(cpu.getCores()));
            addClockSpeed.setText(String.valueOf(cpu.getClockSpeed()));
            addPowerUsage.setText(String.valueOf(cpu.getPowerUsage()));
            addAantal.setText(String.valueOf(cpu.getAantal()));
            modifiedCPU = new CPU(cpu.getName(), cpu.getPrice(), cpu.getThreads(), cpu.getCores(), cpu.getClockSpeed(), cpu.getPowerUsage(), cpu.getAantal());
        }
    }

    public void modifyCurrentRow(){
        selectedRow = tableView.getSelectionModel().getSelectedIndex();

        modifiedCPU.setName(addName.getText());
        modifiedCPU.setPrice(Integer.parseInt(addPrice.getText()));
        modifiedCPU.setThreads(Integer.parseInt(addThreads.getText()));
        modifiedCPU.setCores(Integer.parseInt(addCores.getText()));
        modifiedCPU.setClockSpeed(Integer.parseInt(addClockSpeed.getText()));
        modifiedCPU.setPowerUsage(Integer.parseInt(addPowerUsage.getText()));

        jdbi.useHandle(handle -> {
            handle.execute("UPDATE CPU SET Name = ? ,Price = ?, Threads = ? , Cores = ? , Clock_speed = ? , Power_usage = ?, Aantal = ? WHERE Name = ?",
                    modifiedCPU.getName(), modifiedCPU.getPrice(), modifiedCPU.getThreads(),
                    modifiedCPU.getCores(), modifiedCPU.getClockSpeed() , modifiedCPU.getPowerUsage(), modifiedCPU.getAantal(), modifiedCPU.getName());
        });

        ObservableList<CPU> CPUList = tableView.getItems();
        CPUList.set(selectedRow,modifiedCPU);
        tableView.setItems(CPUList);
    }
}
