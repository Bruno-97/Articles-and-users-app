package hr.java.vjezbe.javafx;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.PoslovniKorisnik;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class PoslovniKorisnikController implements Initializable
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
	private TableColumn<PoslovniKorisnik, String> nazivPoslovnogKorisnikaColumn;
	
	@FXML
	private TableColumn<PoslovniKorisnik, String> webPoslovnogKorisnikaColumn;
	
	@FXML
	private TableColumn<PoslovniKorisnik, String> emailPoslovnogKorisnikaColumn;
	
	@FXML
	private TableColumn<PoslovniKorisnik, String> telefonPoslovnogKorisnikaColumn;
	
	@FXML
	private TableView<PoslovniKorisnik> tablePoslovniKorisnik;
	
	public static PoslovniKorisnik poslovni = null;
	
	private static final Logger logger = LoggerFactory.getLogger(BazaPodataka.class);
	
	public static List<PoslovniKorisnik> listItems = BazaPodataka.dohvatiPoslovneKorisnikePremaKriterijima(poslovni);

	ObservableList<PoslovniKorisnik> listaPoslovnihKorisnika = FXCollections.observableArrayList(listItems);
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		Platform.runLater(new Runnable() 
		{
	        @Override
	        public void run() 
	        {
	        	nazivPoslovnogKorisnika.requestFocus();
	        }
	    });
		
		tablePoslovniKorisnik.setItems(listaPoslovnihKorisnika);
		
		nazivPoslovnogKorisnikaColumn.setCellValueFactory(new PropertyValueFactory<PoslovniKorisnik, String>("naziv"));
		webPoslovnogKorisnikaColumn.setCellValueFactory(new PropertyValueFactory<PoslovniKorisnik, String>("web"));
		emailPoslovnogKorisnikaColumn.setCellValueFactory(new PropertyValueFactory<PoslovniKorisnik, String>("email"));
		telefonPoslovnogKorisnikaColumn.setCellValueFactory(new PropertyValueFactory<PoslovniKorisnik, String>("telefon"));	
	}
	
	public void pretraziSvePoslovneKorisnike() 
	{
		try (Connection connection = BazaPodataka.spajanjeNaBazu()) 
		{
			List<PoslovniKorisnik> filtriraniPoslovniKorisnici = new ArrayList<>();
			
			if(nazivPoslovnogKorisnika.getText().isEmpty() == false || webPoslovnogKorisnika.getText().isEmpty() == false 
			|| emailPoslovnogKorisnika.getText().isEmpty() == false || telefonPoslovnogKorisnika.getText().isEmpty() == false) 
			{
				filtriraniPoslovniKorisnici=listItems.stream().filter(PoslovniKorisnik -> PoslovniKorisnik.getNaziv().toUpperCase().startsWith(nazivPoslovnogKorisnika.getText().toUpperCase()))
			    .filter(PoslovniKorisnik -> PoslovniKorisnik.getWeb().toUpperCase().startsWith(webPoslovnogKorisnika.getText().toUpperCase()))
				.filter(PoslovniKorisnik -> PoslovniKorisnik.getEmail().toUpperCase().startsWith(emailPoslovnogKorisnika.getText().toUpperCase()))
				.filter(PoslovniKorisnik -> PoslovniKorisnik.getTelefon().startsWith(telefonPoslovnogKorisnika.getText()))
				.collect(Collectors.toList());
			}
			
			if(nazivPoslovnogKorisnika.getText().isEmpty() == true && webPoslovnogKorisnika.getText().isEmpty() == true 
			&& emailPoslovnogKorisnika.getText().isEmpty() == true && telefonPoslovnogKorisnika.getText().isEmpty() == true) 
			{
				filtriraniPoslovniKorisnici = BazaPodataka.dohvatiPoslovneKorisnikePremaKriterijima(poslovni);
			}
			
			ObservableList<PoslovniKorisnik> listaPoslovnihKorisnika = FXCollections.observableArrayList(filtriraniPoslovniKorisnici);
			tablePoslovniKorisnik.setItems(listaPoslovnihKorisnika);
		}
		
		catch (SQLException | IOException e) 
		{
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}	
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
