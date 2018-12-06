package Views;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Models.MainModels;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.GridPane;

public class WeeklyView implements Initializable{
	private DoubleProperty vPos = new SimpleDoubleProperty();
	private DoubleProperty hPos = new SimpleDoubleProperty();
	private MainModels model;
	private MainUI view;
	private LocalDate date;
	private int filter;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lblWed;

    @FXML
    private Label lblFri;

    @FXML
    private Button btnPrev;

    @FXML
    private Button btnNext;

    @FXML
    private Label lblThu;

    @FXML
    private Label lblTue;

    @FXML
    private GridPane gpnList;

    @FXML
    private ScrollPane spnDays;

    @FXML
    private Label lblSat;

    @FXML
    private Label lblMon;

    @FXML
    private Label lblSun;

    @FXML
    private ScrollPane spnBoard;

    @FXML
    private Label lblWeek;

    @FXML
    private ScrollPane spnTime;

    @FXML
    private GridPane gpnBoard;

    @FXML
    void prevDay(ActionEvent event) {

    }

    @FXML
    void nextDay(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert lblWed != null : "fx:id=\"lblWed\" was not injected: check your FXML file 'WeeklyView.fxml'.";
        assert lblFri != null : "fx:id=\"lblFri\" was not injected: check your FXML file 'WeeklyView.fxml'.";
        assert btnPrev != null : "fx:id=\"btnPrev\" was not injected: check your FXML file 'WeeklyView.fxml'.";
        assert btnNext != null : "fx:id=\"btnNext\" was not injected: check your FXML file 'WeeklyView.fxml'.";
        assert lblThu != null : "fx:id=\"lblThu\" was not injected: check your FXML file 'WeeklyView.fxml'.";
        assert lblTue != null : "fx:id=\"lblTue\" was not injected: check your FXML file 'WeeklyView.fxml'.";
        assert gpnList != null : "fx:id=\"gpnList\" was not injected: check your FXML file 'WeeklyView.fxml'.";
        assert spnDays != null : "fx:id=\"spnDays\" was not injected: check your FXML file 'WeeklyView.fxml'.";
        assert lblSat != null : "fx:id=\"lblSat\" was not injected: check your FXML file 'WeeklyView.fxml'.";
        assert lblMon != null : "fx:id=\"lblMon\" was not injected: check your FXML file 'WeeklyView.fxml'.";
        assert lblSun != null : "fx:id=\"lblSun\" was not injected: check your FXML file 'WeeklyView.fxml'.";
        assert spnBoard != null : "fx:id=\"spnBoard\" was not injected: check your FXML file 'WeeklyView.fxml'.";
        assert lblWeek != null : "fx:id=\"lblWeek\" was not injected: check your FXML file 'WeeklyView.fxml'.";
        assert spnTime != null : "fx:id=\"spnTime\" was not injected: check your FXML file 'WeeklyView.fxml'.";
        assert gpnBoard != null : "fx:id=\"gpnBoard\" was not injected: check your FXML file 'WeeklyView.fxml'.";

        spnTime.setVbarPolicy(ScrollBarPolicy.NEVER);
        spnDays.setHbarPolicy(ScrollBarPolicy.NEVER);
        
        vPos.bind(spnBoard.vvalueProperty());        
        vPos.addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> arg0, Object arg1, Object arg2) {
                 spnTime.setVvalue((double) arg2);

            }
        });
        
        hPos.bind(spnBoard.hvalueProperty());
        hPos.addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> arg0, Object arg1, Object arg2) {
                 spnDays.setHvalue((double) arg2);

            }
        });
    }
    
    public void attachModels(MainModels model)
    {
    	this.model = model;
    }
    
    public void attachView(MainUI view)
    {
    	this.view = view;
    }
    
    public void setDate(LocalDate date)
    {
    	this.date = date;
    }
    
    public void setFilter(int filter)
    {
    	this.filter = filter;
    }
}
