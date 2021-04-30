package hr.java.vjezbe.javafx;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application 
{
	private static Stage stage;
	
	@Override
	public void start(Stage primaryStage) 
	{
		stage = primaryStage;
		try 
		{
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/hr/java/vjezbe/javafx/Login.fxml"));
			BackgroundImage myBI= new BackgroundImage(new Image("file:616x510.jpg", 590, 640, false,true),
			BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
			root.setBackground(new Background(myBI));
			Scene scene = new Scene(root, 590, 640);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());	
			primaryStage.setScene(scene);
			primaryStage.setTitle("PRO app");		
			primaryStage.show();
			Image ikona = new Image("file:slikica.png");
			primaryStage.getIcons().add(ikona);
		} 
		
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void setMainPage(BorderPane root) 
	{
		Scene scene = new Scene(root, 590, 640);	
		stage.setScene(scene);
		stage.show();
	} 
	
	public static void main(String[] args) 
	{	
		launch(args);
	}
}
