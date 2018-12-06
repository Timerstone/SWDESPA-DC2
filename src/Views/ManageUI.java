package Views;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import Controllers.MainController;
import Models.Activities;
import Models.MainModels;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ManageUI implements Initializable{
	private ObservableList<String> time	= FXCollections.observableArrayList("0000", "0030", "0100", "0130", "0200", "0230", "0300", "0330", "0400",
			"0430", "0500", "0530", "0600", "0630", "0700", "0730", "0800", "0830", "0900", "0930", "1000", "1030", "1100", "1130", "1200",
			"1230", "1300", "1330", "1400", "1430", "1500", "1530", "1600", "1630", "1700", "1730", "1800", "1830", "1900", "1930", "2000",
			"2030", "2100", "2130", "2200", "2230", "2300", "2330");
	private MainController control;
	private MainModels model;
	private MainUI view;
	private LocalDate date;
	private String origname;

    @FXML
    private Button btnCancel;

    @FXML
    private ComboBox<String> cmbStart;

    @FXML
    private Button btnEdit;

    @FXML
    private DatePicker dtpChoose;

    @FXML
    private Label lblErrorMsg;

    @FXML
    private TextField txfName;

    @FXML
    private Button btnConfirm;

    @FXML
    private Button btnRemove;

    @FXML
    private DatePicker dtpChange;

    @FXML
    private ChoiceBox<String> chbActivities;

    @FXML
    private ComboBox<String> cmbEnd;
    
    @FXML
    private CheckBox ckbDone;

    @FXML
    void edit() 
    {
    	int index;
    	Activities temp;
    	String type;
    	
    	index = model.getActivityIndex(chbActivities.getValue());
    	temp = model.getActivities().get(index);
    	type = temp.getClass().toString().replace("class Models.", "");
    	
    	txfName.setDisable(false);
    	cmbStart.setDisable(false);
    	btnConfirm.setDisable(false);
    	dtpChange.setDisable(false);
    	
    	origname = temp.getName();
    	txfName.setText(temp.getName());
    	cmbStart.getSelectionModel().select(temp.getTimeStart().toString().replace(":", ""));
    	dtpChange.setValue(date);
    	
    	if(type.equals("Tasks"))
    	{
    		cmbEnd.setDisable(true);
    		ckbDone.setDisable(false);
    		if(temp.getStatus())
    			ckbDone.setSelected(true);
    	}
    	else
    	{
    		cmbEnd.setDisable(false);
    		ckbDone.setDisable(true);
    		cmbEnd.getSelectionModel().select(temp.getTimeEnd().toString().replace(":", ""));
    	}
    }

    @FXML
    public void cancel() 
    {
    	Stage stage = (Stage)btnCancel.getScene().getWindow();
    	stage.close();  
    	view.refresh();
    }

    @FXML
    public void remove() 
    {
    	int index;
    	index = model.getActivityIndex(chbActivities.getValue());
    	System.out.println(index + " " + chbActivities.getValue());
    	model.getActivities().remove(index);
    	
    	model.saveToFile();
    	view.refresh();
    	
    	cancel();
    }

    @FXML
    public void confirmAdd() 
    {
    	String name;
    	String type;
    	LocalTime timeStart;
    	LocalTime timeEnd;
    	LocalDate date;
    	int hour, min;
    	int index;
    	boolean status;
    	
    	index = model.getActivityIndex(origname);
    	
    	name = txfName.getText();    	
    	date = dtpChange.getValue();
    	type = model.getActivities().get(index).getClass().toString().replace("class Models.", "");
    	status = ckbDone.isSelected();
    	
    	
    	hour = Integer.parseInt(cmbStart.getValue().toString()) / 100;
    	min = Integer.parseInt(cmbStart.getValue().toString()) - hour * 100;
    	timeStart = LocalTime.of(hour, min);
    	
    	if(type.toString().equals("Events"))
    	{
    		hour = Integer.parseInt(cmbEnd.getValue().toString()) / 100;
    		min = Integer.parseInt(cmbEnd.getValue().toString()) - hour * 100;
    	}
    	else
    	{
    		if(min == 0)
    			min += 30;
    		else if(hour == 23 && min == 30)
    			hour = min = 0;
    		else
    		{
    			hour += 1;
    			min = 0;
    		}
    	}
    	
    	timeEnd = LocalTime.of(hour, min);
    	control.editActivity(name, date, timeStart, timeEnd, type, index, status);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'ManageUI.fxml'.";
        assert cmbStart != null : "fx:id=\"cmbStart\" was not injected: check your FXML file 'ManageUI.fxml'.";
        assert btnEdit != null : "fx:id=\"btnEdit\" was not injected: check your FXML file 'ManageUI.fxml'.";
        assert dtpChoose != null : "fx:id=\"dtpChoose\" was not injected: check your FXML file 'ManageUI.fxml'.";
        assert lblErrorMsg != null : "fx:id=\"lblErrorMsg\" was not injected: check your FXML file 'ManageUI.fxml'.";
        assert txfName != null : "fx:id=\"txfName\" was not injected: check your FXML file 'ManageUI.fxml'.";
        assert btnConfirm != null : "fx:id=\"btnConfrm\" was not injected: check your FXML file 'ManageUI.fxml'.";
        assert btnRemove != null : "fx:id=\"btnRemove\" was not injected: check your FXML file 'ManageUI.fxml'.";
        assert dtpChange != null : "fx:id=\"dtpChange\" was not injected: check your FXML file 'ManageUI.fxml'.";
        assert chbActivities != null : "fx:id=\"cmbActivities\" was not injected: check your FXML file 'ManageUI.fxml'.";
        assert cmbEnd != null : "fx:id=\"cmbEnd\" was not injected: check your FXML file 'ManageUI.fxml'.";
        assert ckbDone != null : "fx:id=\"ckbDone\" was not injected: check your FXML file 'ManageUI.fxml'.";
        
        cmbStart.setItems(time);
        cmbStart.getSelectionModel().selectFirst();
        
        cmbEnd.setItems(time);
        cmbEnd.getSelectionModel().selectFirst();
        
        dtpChoose.setValue(LocalDate.now());
        dtpChoose.valueProperty().addListener((ov, oldValue, newValue) -> {
            date = newValue;
            addChoices();
        });
        
        date = LocalDate.now();
        dtpChange.setValue(date);
        
        lblErrorMsg.setText("");
        txfName.setText("");
        
        ckbDone.setAllowIndeterminate(false);
        
        btnConfirm.setDisable(true);
        txfName.setDisable(true);
        cmbStart.setDisable(true);
        cmbEnd.setDisable(true);
        ckbDone.setDisable(true);
        dtpChange.setDisable(true);
    }
    
    public void attachController(MainController control)
    {
    	this.control = control;
    }
    
    public void attachModels(MainModels model)
    {
    	this.model = model;
    }
    
    public void attachView(MainUI view)
    {
    	this.view = view;
    }
    public void addChoices()
    {
    	if(!chbActivities.getItems().isEmpty())
    		chbActivities.getItems().clear();
    	
    	for(int i = 0; i < model.getActivities().size(); i++)
    	{
    		if(model.getActivities().get(i).getDate().equals(date))
    			chbActivities.getItems().add(model.getActivities().get(i).getName());
    	}
    	
    	chbActivities.getSelectionModel().selectFirst();
    }
    
    public void setErrorMsg(String text)
    {
    	lblErrorMsg.setText(text);
    }
}
