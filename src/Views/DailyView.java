package Views;

import java.net.URL;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Models.Activities;
import Models.Events;
import Models.MainModels;
import Models.Tasks;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

public class DailyView implements Initializable{
	private LocalDate date;
	private ArrayList<Label> labelList; 
	private MainModels model;
	private MainUI view;
	private int filter;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private GridPane gpnList;

    @FXML
    private Button btnPrev;

    @FXML
    private Button btnNext;

    @FXML
    private Label lblDate;
    
    @FXML
    private GridPane gpnBoard;
      
    
    
    
    
    @FXML
    public void nextDay() {
    	date = date.plusDays(1);    	
    	lblDate.setText("" + date);
    	refresh();
    	view.setDate(date);
    }
    
    @FXML
    public void prevDay() {
    	date = date.minusDays(1);    	
    	lblDate.setText("" + date.toString());
    	refresh();
    	view.setDate(date);
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert gpnList != null : "fx:id=\"gpnList\" was not injected: check your FXML file 'DailyView.fxml'.";
        assert btnPrev != null : "fx:id=\"btnPrev\" was not injected: check your FXML file 'DailyView.fxml'.";
        assert btnNext != null : "fx:id=\"btnNext\" was not injected: check your FXML file 'DailyView.fxml'.";
        assert lblDate != null : "fx:id=\"lblDate\" was not injected: check your FXML file 'DailyView.fxml'.";
        
        lblDate.setText("");
       
        labelList = new ArrayList<Label>();
       
        for(int i = 0; i < 48; i++)
        {
        	labelList.add(new Label());
        }
        
        for(int i = 0; i < 48; i++) {
        	Node node = labelList.get(i);
        	gpnBoard.add(node, 0, i);
        	labelList.get(i).setPrefSize(550, 23);
        	labelList.get(i).setAlignment(Pos.BOTTOM_CENTER);
        	labelList.get(i).setText("");
        	labelList.get(i).setStyle("-fx-background-color: #ffffff;");
        	labelList.get(i).setTextAlignment(TextAlignment.CENTER);
        }
        
    }
    
    public void setDate(LocalDate date)
    {
    	this.date = date;
    	lblDate.setText(this.date.toString());
    	refresh();
    }
    
    public String getDate() {
    	return lblDate.getText();
    }
    
    public void refresh()
    {
    	ArrayList<Activities> temp = model.getActivities();
    	int hour;
    	int min;
    	int start = 0;
    	int end = 0;
    	boolean load;
    	
    	clearLabels();
    	
    	for(int i = 0; i < temp.size(); i++)
    	{
    		if(temp.get(i).getDate().equals(date))
    		{
    			min = temp.get(i).getTimeStart().getMinute();
    			hour = temp.get(i).getTimeStart().getHour();
    			
    			if(hour == 0 && min == 30)
    				start = hour + 1;
    			else if(hour == 0)
    				start = hour;
    			else if(min == 0)
    				start = hour * 2;
    			else if(min == 30)
    				start = hour * 2 + 1;
    			
    			min = temp.get(i).getTimeEnd().getMinute();
    			hour = temp.get(i).getTimeEnd().getHour();
    			
    			if(hour == 0 && min == 30)
    				end = hour + 1;
    			else if(hour == 0)
    				end = hour;
    			else if(min == 0)
    				end = hour * 2;
    			else if(min == 30)
    				end = hour * 2 + 1;
    			
    			if(temp.get(i).getClass() == Tasks.class && start == 47 && end == 0)
    				end = start + 1;
    			else if(temp.get(i).getClass() == Events.class)
    				end++;
    			
    			for(int j = start; j < end; j++)
    			{
    				if(j == start)
    					if(filter == 1 && temp.get(i).getClass() == Tasks.class) {
    						labelList.get(j).setText(temp.get(i).getName());
                    	}
                    	else if(filter == 2 && temp.get(i).getClass() == Events.class) {
                    		labelList.get(j).setText(temp.get(i).getName());
                    	}
                        else if(filter == 0) {
                            	labelList.get(j).setText(temp.get(i).getName());
                    	}
    				
    				load = false;
    					
    				if(filter == 1 && temp.get(i).getClass() == Tasks.class) {
						load = true;
                	}
                	else if(filter == 2 && temp.get(i).getClass() == Events.class) {
                		load = true;
                	}
                    else if(filter == 0) {
                        load = true;
                	}
    				
    				if(load)
    				{
	    				switch(temp.get(i).getColor().toString())
	    				{
	    					case "0x008000ff":
	    						labelList.get(j).setStyle("-fx-background-color: #2ecc71;");
	    						break;
	    					case "0x0000ffff":
	    						labelList.get(j).setStyle("-fx-background-color: #3493db;");
	    						break;
	    					case "0x808080ff":
	    						labelList.get(j).setStyle("-fx-background-color: #808080;");
	    						break;
	    				}
    				}
    			}
    		}
    	}
    }
    
    private void clearLabels()
    {
    	for(int i = 0; i < labelList.size(); i++)
    	{
    		Label temp = labelList.get(i);
    		
    		temp.setText("");
    		temp.setStyle("-fx-background-color: #ffffff;");
    	}
    }
    
    public void attachModels(MainModels model)
    {
    	this.model = model;
    }
    
    public void attachMainUI(MainUI view)
    {
    	this.view = view;
    }

	public void setFilter(int filter) 
	{
		this.filter = filter;		
	}
}
