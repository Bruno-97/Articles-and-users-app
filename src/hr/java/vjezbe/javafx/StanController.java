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
import hr.java.vjezbe.entitet.Stan;
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

public class StanController implements Initializable
{
	@FXML
	private TextField naslovStana;
	
	@FXML
	private TextField opisStana;
	
	@FXML
	private TextField kvadraturaStana;
	
	@FXML
	private TextField cijenaStana;
	
	@FXML
	private TableColumn<Stan, String> naslovStanaColumn;
	
	@FXML
	private TableColumn<Stan, String> opisStanaColumn;
	
	@FXML
	private TableColumn<Stan, String> kvadraturaStanaColumn;
	
	@FXML
	private TableColumn<Stan, String> cijenaStanaColumn;
	
	@FXML
	private TableColumn<Stan, String> stanjeStanaColumn;
	
	@FXML
	private TableView<Stan> tableStan;
	
	private static Stan stan = null;
	
	private static final Logger logger = LoggerFactory.getLogger(BazaPodataka.class);
	
	public static List<Stan> listItems = BazaPodataka.dohvatiStanovePremaKriterijima(stan);

	ObservableList<Stan> listaStanova = FXCollections.observableArrayList(listItems);
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		Platform.runLater(new Runnable() 
		{
	        @Override
	        public void run() 
	        {
	        	naslovStana.requestFocus();
	        }
	    });
		
		tableStan.setItems(listaStanova);
		
		naslovStanaColumn.setCellValueFactory(new PropertyValueFactory<Stan, String>("naslov"));
		opisStanaColumn.setCellValueFactory(new PropertyValueFactory<Stan, String>("opis"));
		kvadraturaStanaColumn.setCellValueFactory(new PropertyValueFactory<Stan, String>("kvadratura"));
		cijenaStanaColumn.setCellValueFactory(new PropertyValueFactory<Stan, String>("cijena"));	
		stanjeStanaColumn.setCellValueFactory(new PropertyValueFactory<Stan, String>("stanje"));
	}
	
	public void pretraziSveStanove()
	{
		try (Connection connection = BazaPodataka.spajanjeNaBazu()) 
		{
			List<Stan> filtriraniStanovi = new ArrayList<>();
			
			if(naslovStana.getText().isEmpty() == false || kvadraturaStana.getText().isEmpty() == false 
			|| opisStana.getText().isEmpty() == false || cijenaStana.getText().isEmpty() == false) 
			{
				filtriraniStanovi = listItems.stream().filter(Stan -> Stan.getNaslov().toUpperCase().startsWith(naslovStana.getText().toUpperCase()))
			    .filter(Stan -> Stan.getOpis().toUpperCase().contains(opisStana.getText().toUpperCase()))
				.filter(Stan -> Stan.getKvadratura().toString().startsWith(kvadraturaStana.getText()))
				.filter(Stan -> Stan.getCijena().toString().startsWith(cijenaStana.getText()))
				.collect(Collectors.toList());
			}
			
			if(naslovStana.getText().isEmpty() == true && kvadraturaStana.getText().isEmpty() == true 
			&& opisStana.getText().isEmpty() == true && cijenaStana.getText().isEmpty() == true) 
			{
				filtriraniStanovi = listItems;
			}
			
			ObservableList<Stan> listaStanova = FXCollections.observableArrayList(filtriraniStanovi);
			tableStan.setItems(listaStanova);
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
