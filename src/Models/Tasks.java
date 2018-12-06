package Models;

import java.time.LocalDate;
import java.time.LocalTime;

import javafx.scene.paint.Color;

public class Tasks extends Activities{
	
	public Tasks(String name, LocalDate date, LocalTime timeStart, LocalTime timeEnd)
	{
		this.name = name;
		this.date = date;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.color = Color.GREEN;
		this.done = false;
	}
	
	public void toggleStatus()
	{
		done = !done;
		
		if(done)
			color = Color.GRAY;
		else
			color = Color.GREEN;
	}
}
