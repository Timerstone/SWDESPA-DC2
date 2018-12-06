package Controllers;

import java.time.LocalDate;
import java.time.LocalTime;

import Models.Events;
import Models.MainModels;
import Models.Tasks;
import Views.AddActivityUI;
import Views.MainUI;
import Views.ManageUI;
import Views.ViewsNavigator;

public class MainController {
	private MainUI view;
	private MainModels model;
	private AddActivityUI addUI;
	private ManageUI manage;
	
	public void attachView(MainUI view)
	{
		this.view = view;
	}
	
	public void attachModels(MainModels model)
	{
		this.model = model;
	}
	
	public void attachViewToNavi()
	{
		ViewsNavigator.setMainUI(view);
	}
	
	public void attachAddUI(AddActivityUI addUI)
	{
		this.addUI = addUI;
	}
	
	public void attachManageUI(ManageUI manage)
	{
		this.manage = manage;
	}
	
	public void refresh()
	{
		model.refresh();
	}
	
	public void addActivity(String name, LocalDate date, LocalTime timeStart, LocalTime timeEnd, String type)
	{		
    	if(type.equals("Event") && timeStart.isAfter(timeEnd)) {
    		addUI.setErrorMsg("Error: Invalid time setting");
    	}
    	else if(name.equals("")) {
    		addUI.setErrorMsg("Error: Cannot have an empty name");
    	}
    	else if(model.hasTimeConflict(date, timeStart, timeEnd)) {
    		addUI.setErrorMsg("Error: It conflicts another activity");
    	}
    	else if(model.hasActivity(name))
    	{
    		addUI.setErrorMsg("Error: Name exists");
    	}
    	else
    	{
    		switch(type)
    		{
    			case "Task": 
    				model.addActivity(new Tasks(name, date, timeStart, timeEnd));
    				break;
    			case "Event": 
    				model.addActivity(new Events(name, date, timeStart, timeEnd));
    				break;
    		}
    		model.saveToFile();
    		view.refresh();
    		addUI.cancel();
    	}
	}
	
	public void editActivity(String name, LocalDate date, LocalTime timeStart, LocalTime timeEnd, String type, int index, boolean status)
	{		
    	if(type.equals("Event") && timeStart.isAfter(timeEnd)) {
    		manage.setErrorMsg("Error: Invalid time setting");
    	}
    	else if(name.equals("")) {
    		manage.setErrorMsg("Error: Cannot have an empty name");
    	}
    	else if(model.hasTimeConflict(date, timeStart, timeEnd, index)) {
    		manage.setErrorMsg("Error: It conflicts another activity");
    	}
    	else if(model.hasActivity(name, index))
    	{
    		manage.setErrorMsg("Error: Name exists");
    	}
    	else
    	{
    		switch(type)
    		{
    			case "Tasks": 
    				model.getActivities().get(index).setName(name);
    				model.getActivities().get(index).setDate(date);
    				model.getActivities().get(index).setTimeStart(timeStart);
    				model.getActivities().get(index).setTimeEnd(timeEnd);
    				
    				if(status != model.getActivities().get(index).getStatus())
    					model.getActivities().get(index).toggleStatus();
    				
    				break;
    				
    			case "Events": 
    				model.getActivities().get(index).setName(name);
    				model.getActivities().get(index).setDate(date);
    				model.getActivities().get(index).setTimeStart(timeStart);
    				model.getActivities().get(index).setTimeEnd(timeEnd);
    				break;
    		}
    		
    		model.saveToFile();
    		view.refresh();
    		manage.cancel();
    	}
	}
	
	public void changeView(String type)
	{
		if(type.equals("Weekly"))
    	{
    		ViewsNavigator.loadView(ViewsNavigator.WEEKLY);
    	}
    	else if(type.equals("Daily"))
    	{
    		ViewsNavigator.loadView(ViewsNavigator.DAILY);
    	}
    	else if(type.equals("Agenda"))
    	{
    		ViewsNavigator.loadView(ViewsNavigator.AGENDA);
    	}
    	else if(type.equals("Finished"))
    	{
    		ViewsNavigator.loadView(ViewsNavigator.FINISHED);
    	}
    	else if(type.equals("Upcoming"))
    	{
    		ViewsNavigator.loadView(ViewsNavigator.UPCOMING);
    	}
	}
}
