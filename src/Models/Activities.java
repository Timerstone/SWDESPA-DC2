package Models;

import java.time.LocalDate;
import java.time.LocalTime;

import javafx.scene.paint.Color;

public abstract class Activities {
	protected String name;
	protected LocalDate date;
	protected LocalTime timeStart;
	protected LocalTime timeEnd;
	protected Color color;
	protected boolean done;
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public void setDate(LocalDate date) 
	{
		this.date = date;
	}
	
	public void setTimeStart(LocalTime timeStart) 
	{
		this.timeStart = timeStart;
	}
	
	public void setTimeEnd(LocalTime timeEnd) 
	{
		this.timeEnd = timeEnd;
	}
	
	public void setColor(Color color) 
	{
		this.color = color;
	}
	
	public void setStatus(boolean status)
	{
		this.done = status;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public LocalDate getDate() 
	{
		return date;
	}
	
	public LocalTime getTimeStart() 
	{
		return timeStart;
	}
	
	public LocalTime getTimeEnd() 
	{
		return timeEnd;
	}
	
	public Color getColor() 
	{
		return color;
	}
	
	public boolean getStatus()
	{
		return done;
	}
	
	public abstract void toggleStatus();

}
