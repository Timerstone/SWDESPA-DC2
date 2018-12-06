package Views;

import java.net.URL;
import java.time.LocalDate;
//import java.util.Comparator;
import java.util.ResourceBundle;

import Models.Activities;
import Models.Events;
import Models.MainModels;
import Models.Tasks;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class AgendaView implements Initializable{
	private LocalDate date;
	private MainModels model;
	private MainUI view;
	private int filter;
	
    @FXML
    private Button btnPrev;

    @FXML
    private Button btnNext;

    @FXML
    private Label lblDate;

    @FXML
    private ListView<Activities> list;
        
    @FXML
    public void nextDay() {
    	date = date.plusDays(1);    	
    	lblDate.setText(date.toString());
    	refresh();
    	view.setDate(date);
    }
    
    @FXML
    public void prevDay() {
    	date = date.minusDays(1);    	
    	lblDate.setText(date.toString());
    	refresh();
    	view.setDate(date);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert btnPrev != null : "fx:id=\"btnPrev\" was not injected: check your FXML file 'AgendaView.fxml'.";
        assert btnNext != null : "fx:id=\"btnNext\" was not injected: check your FXML file 'AgendaView.fxml'.";
        assert lblDate != null : "fx:id=\"lblDate\" was not injected: check your FXML file 'AgendaView.fxml'.";
        assert list != null : "fx:id=\"list\" was not injected: check your FXML file 'AgendaView.fxml'.";
        
        list.setCellFactory(lv -> new ListCell<Activities>() {
            @Override
            protected void updateItem(Activities item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                    setTextFill(null);
                }
                else {
                	if(filter == 1 && item.getClass() == Tasks.class) {
                		setText("\t" + item.getTimeStart() + " to " +  item.getTimeEnd() + ": \n\t\t\t\t" + item.getName() +"\n\n\t*******************************************************************************************************************");
                        setTextFill(item.getColor());
                	}
                	else if(filter == 2 && item.getClass() == Events.class) {
                		setText("\t" + item.getTimeStart() + " to " +  item.getTimeEnd() + ": \n\t\t\t\t" + item.getName() +"\n\n\t*******************************************************************************************************************");
                        setTextFill(item.getColor());
                	}
                    else if(filter == 0) {
                		setText("\t" + item.getTimeStart() + " to " +  item.getTimeEnd() + ": \n\t\t\t\t" + item.getName() +"\n\n\t*******************************************************************************************************************");
                        setTextFill(item.getColor());
                	}
                	
                	
                	
                }
            }
        });
    }

    public void attachModels(MainModels model)
    {
    	this.model = model;
    }
    
    public void attachMainUI(MainUI view)
    {
    	this.view = view;
    }
    
    public void refresh()
    {
    	list.getItems().clear();
    	
    	Activities temp;
    	for(int i = 0; i < model.getActivities().size(); i++)
    	{
    		temp = model.getActivities().get(i);
    		if(temp.getDate().equals(date))
    		{
    			list.getItems().add(temp);  			
    			
    		}
    	}
    }
    
    public void setDate(LocalDate date)
    {
    	this.date = date;
    	lblDate.setText(this.date.toString());
    	refresh();
    }

	public void setFilter(int filter) 
	{
		this.filter = filter;		
	}
    
}