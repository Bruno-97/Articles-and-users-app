package hr.java.vjezbe.javafx;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.OptionalLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Entitet;
import hr.java.vjezbe.entitet.PoslovniKorisnik;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;

public class UnosPoslovnogKorisnikaController 
{
	@FXML
	private TextField nazivPoslovnogKorisnika;

	@FXML
	private TextField webPoslovnogKorisnika;
	 
	@FXML
	private TextField emailPoslovnogKorisnika;

	@FXML
	private TextField telefonPoslovnogKorisnika;
	    
	@FXML
	private Button spremiButton;
	
	public static PoslovniKorisnik poslovni = null;
	
	private static final Logger logger = LoggerFactory.getLogger(BazaPodataka.class);
	
	@FXML
	public void initialize() 
	{	
		Platform.runLater(new Runnable() 
		{
	        @Override
	        public void run() 
	        {
	        	nazivPoslovnogKorisnika.requestFocus();
	        }
	    });
		
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
				List<PoslovniKorisnik> listItems = BazaPodataka.dohvatiPoslovneKorisnikePremaKriterijima(poslovni); 
				OptionalLong maxId = listItems.stream().mapToLong(Entitet::getId).max();
						
				PoslovniKorisnik poslovni = new PoslovniKorisnik(maxId.getAsLong() + 1, nazivPoslovnogKorisnika.getText(), 
				webPoslovnogKorisnika.getText(), emailPoslovnogKorisnika.getText(), telefonPoslovnogKorisnika.getText());
						
				PoslovniKorisnikController.listItems.add(poslovni);
				BazaPodataka.pohraniNovogPoslovnogKorisnika(poslovni);
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Uspješno spremanje podataka");
				alert.setHeaderText("Spremanje podataka o novom poslovnom korisniku");
				alert.setContentText("Podaci o novom poslovnom korisniku su uspješno pohranjeni");
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
			
		if(nazivPoslovnogKorisnika.getText().equals("")) 
		{
			errorText += "Naziv je obavezan podatak!";
			nazivPoslovnogKorisnika.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
		}
		
		else
		{
			nazivPoslovnogKorisnika.setStyle("-fx-border-color: white;");
		}
			
		if(webPoslovnogKorisnika.getText().equals("")) 
		{
			errorText += "\nWeb je obavezan podatak!";
			webPoslovnogKorisnika.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
		}
		
		else
		{
			webPoslovnogKorisnika.setStyle("-fx-border-color: white;");
		}
			
		if(emailPoslovnogKorisnika.getText().equals("")) 
		{
			errorText += "\nE-mail je obavezan podatak!";
			emailPoslovnogKorisnika.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
		}
		
		else
		{
			emailPoslovnogKorisnika.setStyle("-fx-border-color: white;");
		}
			
		if(telefonPoslovnogKorisnika.getText().equals("")) 
		{
			errorText += "\nTelefon je obavezan podatak!";	
			telefonPoslovnogKorisnika.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
		}
		
		else
		{
			telefonPoslovnogKorisnika.setStyle("-fx-border-color: white;");
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
