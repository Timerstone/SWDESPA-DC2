package Views;

import java.net.URL;
import java.util.ResourceBundle;

import Models.Activities;
import Models.Events;
import Models.MainModels;
import Models.Tasks;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class FinishedView implements Initializable{
	private MainModels model;
	private int filter;
	
    @FXML
    private ListView<Activities> list;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert list != null : "fx:id=\"list\" was not injected: check your FXML file 'FinishedView.fxml'.";
        
        list.setCellFactory(lv -> new ListCell<Activities>() {
            @Override
            protected void updateItem(Activities item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                    setTextFill(null);
                } else {               	
                	if(filter == 1 && item.getClass() == Tasks.class) {
                		setText("\t" + item.getDate() + " [" + item.getTimeStart() + " to " 
                    			+  item.getTimeEnd() + "] "
                    			+ ": \n\t\t\t\t" + item.getName() 
                    			+"\n\n\t*******************************************************************************************************************");
                    setTextFill(item.getColor());
                	}
                	else if(filter == 2 && item.getClass() == Events.class) {
                		setText("\t" + item.getDate() + " [" + item.getTimeStart() + " to " 
                    			+  item.getTimeEnd() + "] "
                    			+ ": \n\t\t\t\t" + item.getName() 
                    			+"\n\n\t*******************************************************************************************************************");
                    setTextFill(item.getColor());
                	}
                    else if(filter == 0) {
                    	setText("\t" + item.getDate() + " [" + item.getTimeStart() + " to " 
                    			+  item.getTimeEnd() + "] "
                    			+ ": \n\t\t\t\t" + item.getName() 
                    			+"\n\n\t*******************************************************************************************************************");
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
    
    public void refresh()
    {
    	list.getItems().clear();
    	
    	Activities temp;
    	
    	for(int i = 0; i < model.getActivities().size(); i++)
    	{
    		temp = model.getActivities().get(i);
    		
    		if(temp.getStatus())
    		{
    			list.getItems().add(temp);
    		}
    	}
    }

	public void setFilter(int filter) 
	{
		this.filter = filter;		
	}

}
