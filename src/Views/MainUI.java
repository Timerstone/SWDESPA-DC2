package Views;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import Controllers.MainController;
import Models.Activities;
import Models.Events;
import Models.MainModels;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainUI implements Initializable{
	//non FX attributes
	private LocalDate today;
	private LocalDate date;
	private ObservableList<String> vtype = FXCollections.observableArrayList("Weekly", "Daily", "Agenda", "Finished", "Upcoming");
	private ObservableList<String> ftype = FXCollections.observableArrayList("Show All", "Tasks", "Events");
	private AgendaView agenda;
	private DailyView daily;
	private WeeklyView weekly;
	private FinishedView finish;
	private UpcomingView upcoming;
	private MainController control;
	private MainModels model;
	private AddActivityUI addUI;
	private Stage addEvent;
	private Stage manage;
	private ManageUI manageUI;
	
	/**********************************************************/
	//FX attributes
	
    @FXML
    private AnchorPane apnBoard;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnAdd;
    
    @FXML
    private Button btnExit;

    @FXML
    private Label lblToday;

    @FXML
    private Button btnToday;

    @FXML
    private ChoiceBox<String> chbView;
    
    @FXML
    private ChoiceBox<String> chbFilter;

    @FXML
    private DatePicker datepicker;
    
    @FXML
    private TextArea txaNotif;
    
    /**********************************************************/
    //FX methods:
    @FXML
    public void refresh()
    {
    	model.readFromFile();
    	date = datepicker.getValue();
    	
    	if(!model.hasActivity(date))
    		txaNotif.setText("There are no activities today.");
    	else
    		txaNotif.setText("");
    	
    	if(agenda != null) {
    		agenda.setDate(date);
    	}
    	if(daily != null) {
    		daily.setDate(date);
    	}
    	if(weekly != null) {
    		weekly.setDate(date);
    	}
    	if(finish != null) {
    		finish.refresh();
    	}
    	if(upcoming != null) {
    		upcoming.refresh();
    	}
    }
    
    @FXML
    private void manageActivities()
    {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ManageUI.fxml"));
			Scene scene = new Scene(loader.load());
			
			manage = new Stage();
			manage.setScene(scene);
			
			manageUI = loader.getController();
			manageUI.attachController(control);
			control.attachManageUI(manageUI);
			manageUI.attachModels(model);
			manageUI.addChoices();
			manageUI.attachView(this);
			
			manage.initStyle(StageStyle.UNDECORATED);
			manage.initModality(Modality.APPLICATION_MODAL);
			manage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    private void addActivity()
    {    	
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/AddActivityUI.fxml"));
			Scene scene = new Scene(loader.load());
			
			addEvent = new Stage();
			addEvent.setScene(scene);
			
			addUI = loader.getController();
			addUI.attachController(control);
			control.attachAddUI(addUI);
			
			addEvent.initStyle(StageStyle.UNDECORATED);
			addEvent.initModality(Modality.APPLICATION_MODAL);
			addEvent.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    private void goToToday()
    {
    	datepicker.setValue(LocalDate.now());
    	refresh();
    }
    
    @FXML
    private void exit()
    {
    	model.saveToFile();
    	System.exit(0);    	
    }

    
    /**********************************************************/
    //non FX methods:
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	assert apnBoard != null : "fx:id=\"apnBoard\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert btnRefresh != null : "fx:id=\"btnRefresh\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert chbFilter != null : "fx:id=\"chbFilter\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert txaNotif != null : "fx:id=\"txaNotif\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert lblToday != null : "fx:id=\"lblToday\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert btnToday != null : "fx:id=\"btnToday\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert datepicker != null : "fx:id=\"datepicker\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert chbView != null : "fx:id=\"chbView\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'MainUI.fxml'.";
        
        loadResources();
        
        today = LocalDate.now();
        date = today;
        
        lblToday.setText(today.toString());
        datepicker.setValue(today);
        
        chbView.setItems(vtype);
        chbView.getSelectionModel().select(1);
        control.changeView(chbView.getValue());
        chbView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
              chbView.setValue(chbView.getItems().get((Integer) number2));
              control.changeView(chbView.getValue());
            }
          });
        
        chbFilter.setItems(ftype);
        chbFilter.getSelectionModel().selectFirst();
        chbFilter.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
        	@Override
        	public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
        		chbFilter.setValue(chbFilter.getItems().get((Integer) number2));
        		setFilter((Integer) number2);
        		refresh();
        	}
        });
        
        datepicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            date = newValue;
        });
        txaNotif.setEditable(false);
        
        refresh();
        initChecker();
    }
    
    public void setFilter(int filter)
    {
    	if(agenda != null) {
    		agenda.setFilter(filter);
    	}
    	if(daily != null) {
    		daily.setFilter(filter);
    	}
    	if(weekly != null) {
    		//weekly.setFilter(filter);
    	}
    	if(finish != null) {
    		finish.setFilter(filter);
    	}
    	if(upcoming != null) {
    		upcoming.setFilter(filter);
    	}
    }
    
    public void loadResources()
    {
    	model = new MainModels();
    	model.attachView(this);
    	
    	control = new MainController();
    	control.attachModels(model);
    	control.attachView(this);
    	control.attachViewToNavi();
    }
    
    public void setView(Node node)
    {    	
    	apnBoard.getChildren().setAll(node);
    	
    	if(chbView.getValue().equals("Weekly"))
    	{
    		weekly = (WeeklyView) getController(node);
    		weekly.attachModels(model);
    		weekly.attachView(this);
    	}
    	else if(chbView.getValue().equals("Daily"))
    	{
    		daily = (DailyView) getController(node);
    		daily.attachModels(model);
    		daily.attachMainUI(this);
    		daily.setDate(date);
    	}
    	else if(chbView.getValue().equals("Agenda"))
    	{
    		agenda = (AgendaView) getController(node);
    		agenda.attachModels(model);
    		agenda.attachMainUI(this);
    		agenda.setDate(date);
    	}
    	else if(chbView.getValue().equals("Finished"))
    	{
    		finish = (FinishedView) getController(node);
    		finish.attachModels(model);
    	}
    	else if(chbView.getValue().equals("Upcoming"))
    	{
    		upcoming = (UpcomingView) getController(node);
    		upcoming.attachModels(model);
    	}
    	
    	refresh();
    }
    
    public Object getController(Node node) {
        return node.getProperties().get("foo");
    }
    
    public LocalDate getDate()
    {
    	return date;
    }
    
    public void setDate(LocalDate date)
    {
    	this.date = date;
    	datepicker.setValue(date);
    	refresh();
    	
    }
    
    public void sendNotif(String text)
    {
    	txaNotif.setText(text);
    }
    
    public void initChecker()
    {
    	Thread T1 = new Thread() {
    		public void run() {
    			Activities temp;
    			while(this.isAlive())
    			{
	    			for(int i = 0; i < model.getActivities().size(); i++)
	    			{
	    				temp = model.getActivities().get(i);
	    				if(!temp.getStatus() && temp.getClass() == Events.class)
	    				{
							if(temp.getDate().isBefore(LocalDate.now())) {
								temp.toggleStatus();
							}
							else if(temp.getTimeEnd().isBefore(LocalTime.now())) {
								temp.toggleStatus();
							}
	    				}
	    			}
    			}
    		}
    	};
    	
    	T1.start();
    }
    
}
