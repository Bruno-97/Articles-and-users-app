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
import hr.java.vjezbe.entitet.Entitet;
import hr.java.vjezbe.entitet.Stan;
import hr.java.vjezbe.entitet.Stanje;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;

public class UnosStanaController 
{
	@FXML
	private TextField naslovStana;

	@FXML
	private ComboBox<Stanje> stanjeStanaBox;

	@FXML
	private TextField opisStana;

	@FXML
	private TextField kvadraturaStana;

	@FXML
	private TextField cijenaStana;
	    
	@FXML
	private Button spremiButton;
	
	private static Stan stan = null;
	
	private static final Logger logger = LoggerFactory.getLogger(BazaPodataka.class);
	
	@FXML
	public void initialize() 
	{	
		Platform.runLater(new Runnable() 
		{
			@Override
		    public void run() 
		    {
		        naslovStana.requestFocus();
		    }
		});
		 
	    stanjeStanaBox.getItems().clear();
	    stanjeStanaBox.setItems(FXCollections.observableArrayList( Stanje.values()));
	    stanjeStanaBox.getSelectionModel().selectFirst();
	    	
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
				String kvadratura = kvadraturaStana.getText();
					
				// pomocna varijabla za spremanje kvadrature stana
				Integer kvStan = Integer.parseInt(kvadratura);
				
				List<Stan> listItems = BazaPodataka.dohvatiStanovePremaKriterijima(stan); 
				OptionalLong maxId = listItems.stream().mapToLong(Entitet::getId).max();
					
				Stan stan = new Stan(maxId.getAsLong() + 1, naslovStana.getText(), 
				opisStana.getText(), new BigDecimal(cijenaStana.getText()),
				stanjeStanaBox.getValue(), kvStan);
					
				BazaPodataka.pohraniNoviStan(stan);
				StanController.listItems.add(stan);
					
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Uspješno spremanje podataka");
				alert.setHeaderText("Spremanje podataka o novom stanu");
				alert.setContentText("Podaci o novom stanu su uspješno pohranjeni");
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
			
		if(naslovStana.getText().equals("")) 
		{
			errorText += "Naslov je obavezan podatak!";
			naslovStana.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
		}
		
		else
		{
			naslovStana.setStyle("-fx-border-color: white;");
		}
			
		if(opisStana.getText().equals("")) 
		{
			errorText += "\nOpis je obavezan podatak!";
			opisStana.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
		}
		
		else
		{
			opisStana.setStyle("-fx-border-color: white;");
		}
			
		if(kvadraturaStana.getText().equals("")) 
		{
			errorText += "\nSnaga je obavezan podatak!";
			kvadraturaStana.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
		}
		
		else
		{
			kvadraturaStana.setStyle("-fx-border-color: white;");
		}
			
		if(cijenaStana.getText().equals("")) 
		{
			errorText += "\nCijena je obavezan podatak!";
			cijenaStana.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
		}
		
		else
		{
			cijenaStana.setStyle("-fx-border-color: white;");
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
