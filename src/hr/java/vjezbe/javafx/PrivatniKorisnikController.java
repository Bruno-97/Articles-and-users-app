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
import hr.java.vjezbe.entitet.PrivatniKorisnik;
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

public class PrivatniKorisnikController implements Initializable
{
	@FXML
	private TextField imePrivatnogKorisnika;
	
	@FXML
	private TextField prezimePrivatnogKorisnika;
	
	@FXML
	private TextField emailPrivatnogKorisnika;
	
	@FXML
	private TextField telefonPrivatnogKorisnika;
	
	@FXML
	private TableColumn<PrivatniKorisnik, String> imePrivatnogKorisnikaColumn;
	
	@FXML
	private TableColumn<PrivatniKorisnik, String> prezimePrivatnogKorisnikaColumn;
	
	@FXML
	private TableColumn<PrivatniKorisnik, String> emailPrivatnogKorisnikaColumn;
	
	@FXML
	private TableColumn<PrivatniKorisnik, String> telefonPrivatnogKorisnikaColumn;
	
	@FXML
	private TableView<PrivatniKorisnik> tablePrivatniKorisnik;
	
	public static PrivatniKorisnik privatni = null;
	
	private static final Logger logger = LoggerFactory.getLogger(BazaPodataka.class);
	
	public static List<PrivatniKorisnik> listItems = BazaPodataka.dohvatiPrivatneKorisnikePremaKriterijima(privatni);

	ObservableList<PrivatniKorisnik> listaPrivatnihKorisnika = FXCollections.observableArrayList(listItems);
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		Platform.runLater(new Runnable() 
		{
	        @Override
	        public void run() 
	        {
	        	imePrivatnogKorisnika.requestFocus();
	        }
	    });
		
		tablePrivatniKorisnik.setItems(listaPrivatnihKorisnika);
		
		imePrivatnogKorisnikaColumn.setCellValueFactory(new PropertyValueFactory<PrivatniKorisnik, String>("ime"));
		prezimePrivatnogKorisnikaColumn.setCellValueFactory(new PropertyValueFactory<PrivatniKorisnik, String>("prezime"));
		emailPrivatnogKorisnikaColumn.setCellValueFactory(new PropertyValueFactory<PrivatniKorisnik, String>("email"));
		telefonPrivatnogKorisnikaColumn.setCellValueFactory(new PropertyValueFactory<PrivatniKorisnik, String>("telefon"));	
	}
	
	public void pretraziSvePrivatneKorisnike() 
	{
		try (Connection connection = BazaPodataka.spajanjeNaBazu()) 
		{
			List<PrivatniKorisnik> filtriraniPrivatniKorisnici = new ArrayList<>();
			
			if(imePrivatnogKorisnika.getText().isEmpty() == false || prezimePrivatnogKorisnika.getText().isEmpty() == false 
			|| emailPrivatnogKorisnika.getText().isEmpty() == false || telefonPrivatnogKorisnika.getText().isEmpty() == false) 
			{
				filtriraniPrivatniKorisnici = listItems.stream().filter(PrivatniKorisnik -> PrivatniKorisnik.getIme().toUpperCase().startsWith(imePrivatnogKorisnika.getText().toUpperCase()))
			    .filter(PrivatniKorisnik -> PrivatniKorisnik.getPrezime().toUpperCase().startsWith(prezimePrivatnogKorisnika.getText().toUpperCase()))
				.filter(PrivatniKorisnik -> PrivatniKorisnik.getEmail().toUpperCase().startsWith(emailPrivatnogKorisnika.getText().toUpperCase()))
				.filter(PrivatniKorisnik -> PrivatniKorisnik.getTelefon().startsWith(telefonPrivatnogKorisnika.getText()))
				.collect(Collectors.toList());
			}
			
			if(imePrivatnogKorisnika.getText().isEmpty() == true && prezimePrivatnogKorisnika.getText().isEmpty() == true 
			&& emailPrivatnogKorisnika.getText().isEmpty() == true && telefonPrivatnogKorisnika.getText().isEmpty() == true) 
			{
				filtriraniPrivatniKorisnici = listItems;
			}
			
			ObservableList<PrivatniKorisnik> listaPrivatnihKorisnika = FXCollections.observableArrayList(filtriraniPrivatniKorisnici);
			tablePrivatniKorisnik.setItems(listaPrivatnihKorisnika);
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
