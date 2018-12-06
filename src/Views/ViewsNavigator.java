package Views;

import javafx.fxml.FXMLLoader;

public class ViewsNavigator {
	public static final String AGENDA = "/Views/AgendaView.fxml";
	public static final String DAILY = "/Views/DailyView.fxml";
	public static final String WEEKLY = "/Views/WeeklyView.fxml";
	public static final String FINISHED = "/Views/FinishedView.fxml";
	public static final String UPCOMING = "/Views/UpcomingView.fxml";
	static MainUI main;
	
	public static void setMainUI(MainUI main)
	{
		ViewsNavigator.main = main;
	}
	
	public static void loadView(String fxml)
	{
		try {
			main.setView(FXMLLoader.load(ViewsNavigator.class.getResource(fxml)));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
