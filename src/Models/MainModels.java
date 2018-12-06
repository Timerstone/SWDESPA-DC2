package Models;

import javafx.scene.paint.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import Views.MainUI;

public class MainModels {
	private ArrayList<Activities> list;
	private MainUI view;
	private BufferedWriter writer;
	private BufferedReader reader;
	private String filename;
	
	public MainModels()
	{
		list = new ArrayList<Activities>();
		//list.add(new Events("Something", LocalDate.now(), LocalTime.of(2, 30), LocalTime.of(3, 30)));
		filename = "src/Models/Activities.csv";
	}
	
	public void attachView(MainUI view)
	{
		this.view = view;
	}
	
	public ArrayList<Activities> getActivities()
	{
		return list;
	}
	
	public int getActivityIndex(String name)
	{
		int i;
		
		for(i = 0; i < list.size(); i++)
		{
			if(list.get(i).getName().equals(name))
			{
				return i;
			}
		}
		
		return -1;
	}
	
	public Activities searchActivity(String name)
	{
		int index = getActivityIndex(name);
		if(index > -1)
			return list.get(index);
		
		return null;
	}
	
	public boolean hasTimeConflict(LocalDate date, LocalTime timeStart, LocalTime timeEnd)
	{
		int i;
		
		for(i = 0; i < list.size(); i++)
		{
			if(date.equals(list.get(i).getDate()))
			{
				Activities temp;
				temp = list.get(i);
				if((temp.getTimeStart().isAfter(timeStart) && temp.getTimeEnd().isAfter(timeEnd) && temp.getTimeStart().isBefore(timeEnd) && temp.getTimeEnd().isAfter(timeStart)) ||
						(temp.getTimeStart().isBefore(timeStart) && temp.getTimeEnd().isAfter(timeEnd) && temp.getTimeStart().isBefore(timeEnd) && temp.getTimeEnd().isAfter(timeStart)) ||
						(temp.getTimeStart().isBefore(timeStart) && temp.getTimeEnd().isBefore(timeEnd) && temp.getTimeStart().isBefore(timeEnd) && temp.getTimeEnd().isAfter(timeStart)) ||
						(temp.getTimeStart().isAfter(timeStart) && temp.getTimeEnd().isBefore(timeEnd) && temp.getTimeStart().isBefore(timeEnd) && temp.getTimeEnd().isAfter(timeStart)) ||
						(temp.getTimeStart().equals(timeStart) || temp.getTimeEnd().equals(timeEnd)))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean hasTimeConflict(LocalDate date, LocalTime timeStart, LocalTime timeEnd, int index)
	{
		int i;
		
		for(i = 0; i < list.size(); i++)
		{
			if(date.equals(list.get(i).getDate()))
			{
				Activities temp;
				temp = list.get(i);
				if(index == i)
					i = index;
				else if((temp.getTimeStart().isAfter(timeStart) && temp.getTimeEnd().isAfter(timeEnd) && temp.getTimeStart().isBefore(timeEnd) && temp.getTimeEnd().isAfter(timeStart)) ||
						(temp.getTimeStart().isBefore(timeStart) && temp.getTimeEnd().isAfter(timeEnd) && temp.getTimeStart().isBefore(timeEnd) && temp.getTimeEnd().isAfter(timeStart)) ||
						(temp.getTimeStart().isBefore(timeStart) && temp.getTimeEnd().isBefore(timeEnd) && temp.getTimeStart().isBefore(timeEnd) && temp.getTimeEnd().isAfter(timeStart)) ||
						(temp.getTimeStart().isAfter(timeStart) && temp.getTimeEnd().isBefore(timeEnd) && temp.getTimeStart().isBefore(timeEnd) && temp.getTimeEnd().isAfter(timeStart)) ||
						(temp.getTimeStart().equals(timeStart) || temp.getTimeEnd().equals(timeEnd)))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean hasActivity(String name)
	{
		Activities temp;
		
		temp = null;
		temp = searchActivity(name);
		
		if(temp == null)
			return false;
		
		return true;
	}
	
	public boolean hasActivity(String name, int index)
	{
		Activities temp;
		
		temp = null;
		temp = searchActivity(name);
		
		if(temp == null)
			return false;
		else if(temp.getName().equals(list.get(index).getName()))
			return false;
		
		return true;
	}
	
	public boolean hasActivity(LocalDate date)
	{
		for(int i = 0; i < list.size(); i++)
		{
			if(list.get(i).getDate().equals(date))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean exists(Activities nAct)
	{
		Activities temp;
		
		for(int i = 0; i < list.size(); i++)
		{
			temp = list.get(i);
			if(nAct.getName().equals(temp.getName()))
				return true;
			if(hasTimeConflict(nAct.getDate(), nAct.getTimeStart(), nAct.getTimeEnd()))
				return true;
		}
		
		return false;
	}
	
	public void addActivity(Activities act)
	{
		list.add(act);
	}
	
	public void removeActivity(String name)
	{
		list.remove(getActivityIndex(name));
	}
	
	public void clearList()
	{
		list.clear();
	}
	
	public void saveToFile()
	{
		Activities temp;
		
		try {
			writer = new BufferedWriter(new FileWriter(filename));
			for(int i = 0; i < list.size(); i++)
			{
				temp = list.get(i);

				writer.write("" + temp.getClass().toString().replaceFirst("class Models.", "") + "," 
								+ temp.getName() + "," 
								+  temp.getDate() + "," 
								+  temp.getTimeStart() + "," 
								+  temp.getTimeEnd() + "," 
								+  temp.getColor() + "," 
								+  temp.getStatus() + "\n");
			}
			
			writer.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void readFromFile()
	{
		String temp;
		String split[];
		String date[];
		String time[];
		Activities nAct = null;
		int year, month, day;
		int hour, min;

		try {
			reader = new BufferedReader(new FileReader(filename));
			
			while((temp = reader.readLine()) != null)
			{
				split = temp.split(",");
				temp = split[2];
				date = temp.split("-");
				
				month = Integer.parseInt(date[1]);
				day = Integer.parseInt(date[2]);
				year = Integer.parseInt(date[0]);
				
				time = (split[3]).split(":");
				hour = Integer.parseInt(time[0]);
				min = Integer.parseInt(time[1]);
				
				switch((split[0]).toLowerCase())
				{
					case "tasks":
						nAct = new Tasks(split[1], LocalDate.of(year, month, day), LocalTime.of(hour, min), LocalTime.of(hour, min));
						
						time = split[4].split(":");
						hour = Integer.parseInt(time[0]);
						min = Integer.parseInt(time[1]);
						
						nAct.setTimeEnd(LocalTime.of(hour,  min));
						nAct.setColor(Color.valueOf(split[5]));
						nAct.setStatus(Boolean.valueOf(split[6]));
						break;
					case "events":
						nAct = new Events(split[1], LocalDate.of(year, month, day), LocalTime.of(hour, min), LocalTime.of(hour, min));
						
						time = split[4].split(":");
						hour = Integer.parseInt(time[0]);
						min = Integer.parseInt(time[1]);
						
						nAct.setTimeEnd(LocalTime.of(hour,  min));
						nAct.setColor(Color.valueOf(split[5]));
						nAct.setStatus(Boolean.valueOf(split[6]));
						break;
				}
				
				if(nAct != null)
				{
					if(!exists(nAct))
						list.add(nAct);
				}
				
			}
			
			reader.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void refresh()
	{
		view.refresh();
	}
}
