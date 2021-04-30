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
import hr.java.vjezbe.entitet.Usluga;
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

public class UslugaController implements Initializable
{
	@FXML
	private TextField naslovUsluge;
	
	@FXML
	private TextField opisUsluge;
	
	@FXML
	private TextField cijenaUsluge;
	
	@FXML
	private TableColumn<Usluga, String> naslovUslugeColumn;
	
	@FXML
	private TableColumn<Usluga, String> opisUslugeColumn;
	
	@FXML
	private TableColumn<Usluga, String> cijenaUslugeColumn;
	
	@FXML
	private TableColumn<Usluga, String> stanjeUslugeColumn;
	
	@FXML
	private TableView<Usluga> tableUsluga;
	
	private static Usluga usluga = null;
	
	private static final Logger logger = LoggerFactory.getLogger(BazaPodataka.class);
	
	public static List<Usluga> listItems = BazaPodataka.dohvatiUslugePremaKriterijima(usluga);

	ObservableList<Usluga> listaUsluga = FXCollections.observableArrayList(listItems);
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		Platform.runLater(new Runnable() 
		{
	        @Override
	        public void run() 
	        {
	        	naslovUsluge.requestFocus();
	        }
	    });
		
		tableUsluga.setItems(listaUsluga);
		
		naslovUslugeColumn.setCellValueFactory(new PropertyValueFactory<Usluga, String>("naslov"));
		opisUslugeColumn.setCellValueFactory(new PropertyValueFactory<Usluga, String>("opis"));
		cijenaUslugeColumn.setCellValueFactory(new PropertyValueFactory<Usluga, String>("cijena"));	
		stanjeUslugeColumn.setCellValueFactory(new PropertyValueFactory<Usluga, String>("stanje"));
	}
	
	public void pretraziSveUsluge()
	{
		try (Connection connection = BazaPodataka.spajanjeNaBazu()) 
		{
			List<Usluga> filtriraneUsluge = new ArrayList<>();
			
			if(naslovUsluge.getText().isEmpty() == false || opisUsluge.getText().isEmpty() == false 
			|| cijenaUsluge.getText().isEmpty() == false) 
			{
				filtriraneUsluge = listItems.stream().filter(Usluga -> Usluga.getNaslov().toUpperCase().startsWith(naslovUsluge.getText().toUpperCase()))
			    .filter(Usluga -> Usluga.getOpis().toUpperCase().contains(opisUsluge.getText().toUpperCase()))
				.filter(Usluga -> Usluga.getCijena().toString().startsWith(cijenaUsluge.getText()))
				.collect(Collectors.toList());
			}
			
			if(naslovUsluge.getText().isEmpty() == true && opisUsluge.getText().isEmpty() == true 
			&& cijenaUsluge.getText().isEmpty() == true) 
			{
				filtriraneUsluge = listItems;
			}
			
			ObservableList<Usluga> listaUsluga = FXCollections.observableArrayList(filtriraneUsluge);
			tableUsluga.setItems(listaUsluga);
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
