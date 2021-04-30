package hr.java.vjezbe.javafx;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.OptionalLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Automobil;
import hr.java.vjezbe.entitet.Entitet;
import hr.java.vjezbe.entitet.Stanje;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class UnosAutomobilaController 
{
    @FXML
    private TextField naslovAutomobila;

    @FXML
    private ComboBox<Stanje> stanjeAutomobilaBox;

    @FXML
    private TextField opisAutomobila;

    @FXML
    private TextField snagaAutomobila;

    @FXML
    private TextField cijenaAutomobila;
    
    @FXML
	private Button spremiButton;
    
    private static Automobil automobil = null;
    
    private static final Logger logger = LoggerFactory.getLogger(BazaPodataka.class);
    
    @FXML
	public void initialize() 
	{
    	Platform.runLater(new Runnable() 
		{
	        @Override
	        public void run() 
	        {
	        	naslovAutomobila.requestFocus();
	        }
	    });
    	
    	stanjeAutomobilaBox.getItems().clear();
    	stanjeAutomobilaBox.setItems(FXCollections.observableArrayList( Stanje.values()));
    	stanjeAutomobilaBox.getSelectionModel().selectFirst();
    	
		spremiButton.setOnAction(e -> 
		{
			try 
			{
				SpremiPodatke();
			} 
			
			catch (BazaPodatakaException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			
			catch (Exception e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
    
    private void SpremiPodatke() 
    {
    	try (Connection connection = BazaPodataka.spajanjeNaBazu()) 
		{
    		if(ValidacijaPodataka()) 
    		{
    			List<Automobil> listItems = BazaPodataka.dohvatiAutomobilePremaKriterijima(automobil); 
    			OptionalLong maxId = listItems.stream().mapToLong(Entitet::getId).max();
    			
    			Automobil auto = new Automobil(maxId.getAsLong() + 1, naslovAutomobila.getText(), 
    			opisAutomobila.getText(), new BigDecimal(cijenaAutomobila.getText()),
    			stanjeAutomobilaBox.getValue(), new BigDecimal(snagaAutomobila.getText()));
    			
    			BazaPodataka.pohraniNoviAutomobil(auto);
    			AutomobilController.listItems.add(auto);
    			
    			Alert alert = new Alert(AlertType.INFORMATION);
    			alert.setTitle("Uspješno spremanje podataka");
    			alert.setHeaderText("Spremanje podataka o novom automobilu");
    			alert.setContentText("Podaci o novom automobilu su uspješno pohranjeni");
    			alert.show();				
    		}
		}
    	
    	catch (SQLException | IOException e) 
		{
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}
	}
    
    private boolean ValidacijaPodataka() 
    {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Pogrešan unos podataka");
		alert.setHeaderText("Molimo ispravite sljedeće pogreške!");
		String errorText = "";
		
		if(naslovAutomobila.getText().equals("")) 
		{
			errorText += "Naslov je obavezan podatak!";
			naslovAutomobila.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
		}
		
		else
		{
			naslovAutomobila.setStyle("-fx-border-color: white;");
		}
		
		if(opisAutomobila.getText().equals("")) 
		{
			errorText += "\nOpis je obavezan podatak!";	
			opisAutomobila.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
		}
		
		else
		{
			opisAutomobila.setStyle("-fx-border-color: white;");
		}
		
		if(snagaAutomobila.getText().equals("")) 
		{
			errorText += "\nSnaga je obavezan podatak!";
			snagaAutomobila.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
		}
		
		else
		{
			snagaAutomobila.setStyle("-fx-border-color: white;");
		}
		
		if(cijenaAutomobila.getText().equals("")) 
		{
			errorText += "\nCijena je obavezan podatak!";	
			cijenaAutomobila.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
		}
		
		else
		{
			cijenaAutomobila.setStyle("-fx-border-color: white;");
		}
		
		alert.setContentText(errorText);
		
		if(errorText.equals("")) 
		{
			return true;	
		}
		
		alert.show();
		return false;
	}
    
    public void prikaziPretraguAutomobila()
	{
		try 
		{
			BorderPane automobiliPane = (BorderPane) FXMLLoader.load(Main.class.getResource("/hr/java/vjezbe/javafx/Automobili.fxml"));
			Main.setMainPage(automobiliPane);
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void prikaziUnosAutomobila()
	{
		try 
		{
			BorderPane noviAutomobiliPane = (BorderPane) FXMLLoader.load(Main.class.getResource("/hr/java/vjezbe/javafx/UnosAutomobila.fxml"));
			Main.setMainPage(noviAutomobiliPane);
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void prikaziPretraguStanova()
	{
		try 
		{
			BorderPane stanoviPane = (BorderPane) FXMLLoader.load(Main.class.getResource("/hr/java/vjezbe/javafx/Stanovi.fxml"));
			Main.setMainPage(stanoviPane);
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void prikaziUnosStanova()
	{
		try 
		{
			BorderPane noviStanoviPane = (BorderPane) FXMLLoader.load(Main.class.getResource("/hr/java/vjezbe/javafx/UnosStana.fxml"));
			Main.setMainPage(noviStanoviPane);
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void prikaziPretraguUsluga()
	{
		try 
		{
			BorderPane uslugePane = (BorderPane) FXMLLoader.load(Main.class.getResource("/hr/java/vjezbe/javafx/Usluge.fxml"));
			Main.setMainPage(uslugePane);
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void prikaziUnosUsluga()
	{
		try 
		{
			BorderPane noveUslugePane = (BorderPane) FXMLLoader.load(Main.class.getResource("/hr/java/vjezbe/javafx/UnosUsluge.fxml"));
			Main.setMainPage(noveUslugePane);
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void prikaziPretraguPrivatnihKorisnika()
	{
		try 
		{
			BorderPane privatniKorisniciPane = (BorderPane) FXMLLoader.load(Main.class.getResource("/hr/java/vjezbe/javafx/PrivatniKorisnici.fxml"));
			Main.setMainPage(privatniKorisniciPane);
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void prikaziUnosPrivatnihKorisnika()
	{
		try 
		{
			BorderPane noviPrivatniKorisniciPane = (BorderPane) FXMLLoader.load(Main.class.getResource("/hr/java/vjezbe/javafx/UnosPrivatnogKorisnika.fxml"));
			Main.setMainPage(noviPrivatniKorisniciPane);
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void prikaziPretraguPoslovnihKorisnika()
	{
		try 
		{
			BorderPane poslovniKorisniciPane = (BorderPane) FXMLLoader.load(Main.class.getResource("/hr/java/vjezbe/javafx/PoslovniKorisnici.fxml"));
			Main.setMainPage(poslovniKorisniciPane);
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void prikaziUnosPoslovnihKorisnika()
	{
		try 
		{
			BorderPane noviPoslovniKorisniciPane = (BorderPane) FXMLLoader.load(Main.class.getResource("/hr/java/vjezbe/javafx/UnosPoslovnogKorisnika.fxml"));
			Main.setMainPage(noviPoslovniKorisniciPane);
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
