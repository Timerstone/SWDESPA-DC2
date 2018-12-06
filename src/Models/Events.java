package Models;

import java.time.LocalDate;
import java.time.LocalTime;

import javafx.scene.paint.Color;

public class Events extends Activities{
	
	public Events(String name, LocalDate date, LocalTime timeStart, LocalTime timeEnd)
	{
		this.name = name;
		this.date = date;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.color = Color.BLUE;
		this.done = false;
	}
	
	public void toggleStatus()
	{
		done = !done;
		if(done)
			color = Color.GRAY;
		else
			color = Color.BLUE;
	}
}
