

// Neil Jain, RUID: 178001542, NetId: nj243
//Rohan Pahwa, RUID: 179005790, NetId: rp930

package songlib.app;

import java.io.FileNotFoundException;


import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import songlib.view.SongLibController;


public class SongLib extends Application {
	@Override
	public void start(Stage primaryStage) 
	throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/songlib/view/songlib.fxml"));
		Pane root = (Pane)loader.load();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Song Library");
		primaryStage.setResizable(false);
		SongLibController slc = loader.getController();
		
		slc.listview(null, 0);
		primaryStage.show();
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		launch(args);
		
	}
	}