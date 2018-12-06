package Views;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import Controllers.MainController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddActivityUI implements Initializable{
	private ObservableList<String> time	= FXCollections.observableArrayList("0000", "0030", "0100", "0130", "0200", "0230", "0300", "0330", "0400",
			"0430", "0500", "0530", "0600", "0630", "0700", "0730", "0800", "0830", "0900", "0930", "1000", "1030", "1100", "1130", "1200",
			"1230", "1300", "1330", "1400", "1430", "1500", "1530", "1600", "1630", "1700", "1730", "1800", "1830", "1900", "1930", "2000",
			"2030", "2100", "2130", "2200", "2230", "2300", "2330");
	private ObservableList<String> type	= FXCollections.observableArrayList("Task", "Event");
	private MainController control;

    @FXML
    private Button btnCancel;

    @FXML
    private ComboBox<String> cmbStart;

    @FXML
    private TextField txfName;

    @FXML
    private ChoiceBox<String> chbType;

    @FXML
    private DatePicker datepicker;

    @FXML
    private ComboBox<String> cmbEnd;

    @FXML
    private Button btnConfirm;
    
    @FXML
    private Label lblErrorMsg;
    
    @FXML
    public void cancel() {
    	Stage stage = (Stage)btnCancel.getScene().getWindow();
    	stage.close();   	
    	
    }
    
    @FXML
    public void confirmAdd() {
    	String name;
    	String type;
    	LocalTime timeStart;
    	LocalTime timeEnd;
    	LocalDate date;
    	int hour, min;
    	
    	name = txfName.getText();
    	date = datepicker.getValue();
    	type = chbType.getValue();
    	
    	hour = Integer.parseInt(cmbStart.getValue().toString()) / 100;
    	min = Integer.parseInt(cmbStart.getValue().toString()) - hour * 100;
    	timeStart = LocalTime.of(hour, min);
    	
    	if(type.toString().equals("Event"))
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
    	
    	control.addActivity(name, date, timeStart, timeEnd, type);
    }
    	

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'AddActivityUI.fxml'.";
        assert cmbStart != null : "fx:id=\"cmbStart\" was not injected: check your FXML file 'AddActivityUI.fxml'.";
        assert txfName != null : "fx:id=\"txfName\" was not injected: check your FXML file 'AddActivityUI.fxml'.";
        assert chbType != null : "fx:id=\"chbType\" was not injected: check your FXML file 'AddActivityUI.fxml'.";
        assert datepicker != null : "fx:id=\"datepicker\" was not injected: check your FXML file 'AddActivityUI.fxml'.";
        assert cmbEnd != null : "fx:id=\"cmbEnd\" was not injected: check your FXML file 'AddActivityUI.fxml'.";
        assert btnConfirm != null : "fx:id=\"btnConfirm\" was not injected: check your FXML file 'AddActivityUI.fxml'.";

        cmbStart.setItems(time);
        cmbStart.getSelectionModel().selectFirst();
        
        cmbEnd.setItems(time);
        cmbEnd.getSelectionModel().selectFirst();
        
        chbType.setItems(type);
        chbType.getSelectionModel().selectFirst();
        cmbEnd.setDisable(true);
        
        datepicker.setValue(LocalDate.now());
        
        lblErrorMsg.setText("");
        txfName.setText("");
        
        chbType.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
              chbType.setValue((chbType.getItems().get((Integer) number2)));
              
              if(chbType.getValue().equals("Event"))
              {
            	  cmbEnd.setDisable(false);
              }
              else
              {
            	  cmbEnd.setDisable(true);
              }
              
            }
          });
    }
    
    public void attachController(MainController control)
    {
    	this.control = control; 
    }
    
    public void setErrorMsg(String message)
    {
    	lblErrorMsg.setText(message);
    }
}
