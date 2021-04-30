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
import hr.java.vjezbe.entitet.Automobil;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

public class AutomobilController implements Initializable
{
	@FXML
	private TextField nazivAutomobila;
	
	@FXML
	private TextField opisAutomobila;
	
	@FXML
	private TextField snagaAutomobila;
	
	@FXML
	private TextField cijenaAutomobila;
	
	@FXML
	private TableColumn<Automobil, String> naslovAutomobilaColumn;
	
	@FXML
	private TableColumn<Automobil, String> opisAutomobilaColumn;
	
	@FXML
	private TableColumn<Automobil, String> snagaAutomobilaColumn;
	
	@FXML
	private TableColumn<Automobil, String> cijenaAutomobilaColumn;
	
	@FXML
	private TableColumn<Automobil, String> stanjeAutomobilaColumn;
	
	@FXML
	private TableView<Automobil> tableAuto;
	
	private static Automobil automobil = null;
	
	private static final Logger logger = LoggerFactory.getLogger(BazaPodataka.class);
	
	public static List<Automobil> listItems = BazaPodataka.dohvatiAutomobilePremaKriterijima(automobil);
	
	ObservableList<Automobil> listaAutomobila = FXCollections.observableArrayList(listItems);

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		Platform.runLater(new Runnable() 
		{
	        @Override
	        public void run() 
	        {
	        	nazivAutomobila.requestFocus();
	        }
	    });
		
		tableAuto.setItems(listaAutomobila);
		
		naslovAutomobilaColumn.setCellValueFactory(new PropertyValueFactory<Automobil, String>("naslov"));
		opisAutomobilaColumn.setCellValueFactory(new PropertyValueFactory<Automobil, String>("opis"));
		snagaAutomobilaColumn.setCellValueFactory(new PropertyValueFactory<Automobil, String>("snagaKs"));
		cijenaAutomobilaColumn.setCellValueFactory(new PropertyValueFactory<Automobil, String>("cijena"));	
		stanjeAutomobilaColumn.setCellValueFactory(new PropertyValueFactory<Automobil, String>("stanje"));
		
		tableAuto.setRowFactory(new Callback<TableView<Automobil>, TableRow<Automobil>>() 
		{  
            public TableRow<Automobil> call(TableView<Automobil> tableView) 
            {  
                final TableRow<Automobil> row = new TableRow<>();  
                final ContextMenu contextMenu = new ContextMenu();  
                final MenuItem removeMenuItem = new MenuItem("Obrisi iz tablice");  
                
                removeMenuItem.setOnAction(new EventHandler<ActionEvent>() 
                {  
                    @Override  
                    public void handle(ActionEvent event) 
                    {  
                    	//usoJe = 1;
                        tableAuto.getItems().remove(row.getItem());
                        //listaAutomobila = tableAuto.getItems();
                        //tempLista = new ArrayList<Automobil>(listaAutomobila);
                    }  
                });  
                
                contextMenu.getItems().add(removeMenuItem);   
                row.contextMenuProperty().bind(  
                        Bindings.when(row.emptyProperty())  
                        .then((ContextMenu)null)  
                        .otherwise(contextMenu)  
                );
                
                return row;  
            }  
        }); 
	}
	
	public void pretraziSveAutomobile() 
	{
		try (Connection connection = BazaPodataka.spajanjeNaBazu()) 
		{
			List<Automobil> filtriraniAutomobili = new ArrayList<>();
			
			if(nazivAutomobila.getText().isEmpty() == false || snagaAutomobila.getText().isEmpty() == false 
			|| opisAutomobila.getText().isEmpty() == false || cijenaAutomobila.getText().isEmpty() == false) 
			{
				filtriraniAutomobili = listItems.stream().filter(Automobil -> Automobil.getNaslov().toUpperCase().startsWith(nazivAutomobila.getText().toUpperCase()))
			    .filter(Automobil -> Automobil.getOpis().toUpperCase().contains(opisAutomobila.getText().toUpperCase()))
				.filter(Automobil -> Automobil.getSnagaKs().toString().startsWith(snagaAutomobila.getText()))
				.filter(Automobil -> Automobil.getCijena().toString().startsWith(cijenaAutomobila.getText()))
				.collect(Collectors.toList());
			}
			
			if(nazivAutomobila.getText().isEmpty() == true && snagaAutomobila.getText().isEmpty() == true 
			&& opisAutomobila.getText().isEmpty() == true && cijenaAutomobila.getText().isEmpty() == true) 
			{
				filtriraniAutomobili = listItems;
			}
			
			ObservableList<Automobil> listaAutomobila = FXCollections.observableArrayList(filtriraniAutomobili);
			tableAuto.setItems(listaAutomobila);
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
