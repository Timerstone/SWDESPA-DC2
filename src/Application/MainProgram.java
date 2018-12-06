package Application;

import Views.MainUI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainProgram extends Application {	
	@Override
    public void start(Stage primaryStage) throws Exception {
		MainUI main;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MainUI.fxml"));
        Scene scene = new Scene(loader.load());
        
        main = loader.getController();
        main.refresh();
        
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
