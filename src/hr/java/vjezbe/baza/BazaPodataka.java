package hr.java.vjezbe.baza;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.entitet.Automobil;
import hr.java.vjezbe.entitet.PoslovniKorisnik;
import hr.java.vjezbe.entitet.PrivatniKorisnik;
import hr.java.vjezbe.entitet.Stan;
import hr.java.vjezbe.entitet.Stanje;
import hr.java.vjezbe.entitet.Usluga;
import hr.java.vjezbe.iznimke.BazaPodatakaException;

public class BazaPodataka 
{
	private static final String DATABASE_FILE = "baza.properties";

	private static final Logger logger = LoggerFactory.getLogger(BazaPodataka.class);

	public static Connection spajanjeNaBazu() throws SQLException, IOException, BazaPodatakaException 
	{
		Properties svojstva = new Properties();

		svojstva.load(new FileReader(DATABASE_FILE));

		String urlBazePodataka = svojstva.getProperty("bazaPodatakaUrl");
		String korisnickoIme = svojstva.getProperty("korisnickoIme");
		String lozinka = svojstva.getProperty("lozinka");

		try 
		{
			Class.forName("org.h2.Driver");
		} 
		
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}

		Connection veza = DriverManager.getConnection(urlBazePodataka, korisnickoIme, lozinka);

		return veza;
	}
	
	/******************************************* AUTOMOBIL *******************************************/
	public static List<Automobil> dohvatiAutomobilePremaKriterijima(Automobil automobil) throws BazaPodatakaException 
	{
		List<Automobil> listaAutomobila = new ArrayList<>();
		try (Connection connection = spajanjeNaBazu()) 
		{ 
			StringBuilder sqlUpit = new StringBuilder("SELECT distinct artikl.id, naslov, opis, cijena, snaga, stanje.naziv "
			+ "FROM artikl inner join stanje on stanje.id = artikl.idStanje "
			+ "inner join tipArtikla on tipArtikla.id = artikl.idTipArtikla WHERE tipArtikla.naziv = 'Automobil'");
			
			if (Optional.ofNullable(automobil).isEmpty() == false) 
			{ 
				if (Optional.ofNullable(automobil).map(Automobil::getId).isPresent())
				{
					sqlUpit.append(" AND artikl.id = " + automobil.getId());
				}
				
				if (Optional.ofNullable(automobil.getNaslov()).map(String::isBlank).orElse(true) == false)
				{
					sqlUpit.append(" AND artikl.naslov LIKE '%" + automobil.getNaslov() + "%'");
				}
				
				if (Optional.ofNullable(automobil.getOpis()).map(String::isBlank).orElse(true) == false)
				{
					sqlUpit.append(" AND artikl.opis LIKE '%" + automobil.getOpis() + "%'");
				}
				
				if (Optional.ofNullable(automobil).map(Automobil::getCijena).isPresent())
				{
					sqlUpit.append(" AND artikl.cijena = " + automobil.getCijena());
				}
				
				if (Optional.ofNullable(automobil).map(Automobil::getSnagaKs).isPresent())
				{
					sqlUpit.append(" AND artikl.snaga = " + automobil.getSnagaKs());
				}
			}
			
			Statement query = connection.createStatement();
			ResultSet resultSet = query.executeQuery(sqlUpit.toString());
			
			while (resultSet.next()) 
			{
				Long id = resultSet.getLong("id");
				String naslov = resultSet.getString("naslov");
				String opis = resultSet.getString("opis");
				BigDecimal cijena = resultSet.getBigDecimal("cijena");
				String stanje = resultSet.getString("naziv");
				BigDecimal snaga = resultSet.getBigDecimal("snaga");
				Automobil newAutomobil = new Automobil(id, naslov, opis, cijena, Stanje.valueOf(stanje), snaga);
				listaAutomobila.add(newAutomobil);
			}
		}
			
		catch (SQLException | IOException e) 
		{
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}
	
		return listaAutomobila;
	}
	
	public static void pohraniNoviAutomobil(Automobil automobil) throws BazaPodatakaException 
	{
		try (Connection veza = spajanjeNaBazu()) 
		{
			PreparedStatement preparedStatement = veza
			.prepareStatement(
			"insert into artikl(Naslov, Opis, Cijena, Snaga, idStanje, idTipArtikla) " +
			"values (?, ?, ?, ?, ?, 1);");
			preparedStatement.setString(1, automobil.getNaslov());
			preparedStatement.setString(2, automobil.getOpis());
			preparedStatement.setBigDecimal(3, automobil.getCijena());
			preparedStatement.setBigDecimal(4, automobil.getSnagaKs());
			preparedStatement.setLong(5, (automobil.getStanje().ordinal() + 1));
			preparedStatement.executeUpdate();
		}
		
		catch (SQLException | IOException ex) 
		{
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, ex);
			throw new BazaPodatakaException(poruka, ex);
		}
	}
	
	/******************************************* STAN *******************************************/
	public static List<Stan> dohvatiStanovePremaKriterijima(Stan stan) throws BazaPodatakaException 
	{
		List<Stan> listaStanova = new ArrayList<>();
		try (Connection connection = spajanjeNaBazu()) 
		{ 
			StringBuilder sqlUpit = new StringBuilder("SELECT distinct artikl.id, naslov, opis, cijena, kvadratura, stanje.naziv "
			+ "FROM artikl inner join stanje on stanje.id = artikl.idStanje "
			+ "inner join tipArtikla on tipArtikla.id = artikl.idTipArtikla WHERE tipArtikla.naziv = 'Stan'");
			
			if (Optional.ofNullable(stan).isEmpty() == false) 
			{ 
				if (Optional.ofNullable(stan).map(Stan::getId).isPresent())
				{
					sqlUpit.append(" AND artikl.id = " + stan.getId());
				}
				
				if (Optional.ofNullable(stan.getNaslov()).map(String::isBlank).orElse(true) == false)
				{
					sqlUpit.append(" AND artikl.naslov LIKE '%" + stan.getNaslov() + "%'");
				}
				
				if (Optional.ofNullable(stan.getOpis()).map(String::isBlank).orElse(true) == false)
				{
					sqlUpit.append(" AND artikl.opis LIKE '%" + stan.getOpis() + "%'");
				}
				
				if (Optional.ofNullable(stan).map(Stan::getCijena).isPresent())
				{
					sqlUpit.append(" AND artikl.cijena = " + stan.getCijena());
				}
				
				if (Optional.ofNullable(stan).map(Stan::getKvadratura).isPresent())
				{
					sqlUpit.append(" AND artikl.kvadratura = " + stan.getKvadratura());
				}
			}
			
			Statement query = connection.createStatement();
			ResultSet resultSet = query.executeQuery(sqlUpit.toString());
			
			while (resultSet.next()) 
			{
				Long id = resultSet.getLong("id");
				String naslov = resultSet.getString("naslov");
				String opis = resultSet.getString("opis");
				BigDecimal cijena = resultSet.getBigDecimal("cijena");
				String stanje = resultSet.getString("naziv");
				Integer kvadratura = resultSet.getInt("kvadratura");
				Stan newStan = new Stan(id, naslov, opis, cijena, Stanje.valueOf(stanje), kvadratura);
				listaStanova.add(newStan);
			}
		}
			
		catch (SQLException | IOException e) 
		{
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}
		
		return listaStanova;
	}
	
	public static void pohraniNoviStan(Stan stan) throws BazaPodatakaException 
	{
		try (Connection veza = spajanjeNaBazu()) 
		{
			PreparedStatement preparedStatement = veza
			.prepareStatement(
			"insert into artikl(Naslov, Opis, Cijena, Kvadratura, idStanje, idTipArtikla) " +
			"values (?, ?, ?, ?, ?, 3);");
			preparedStatement.setString(1, stan.getNaslov());
			preparedStatement.setString(2, stan.getOpis());
			preparedStatement.setBigDecimal(3, stan.getCijena());
			preparedStatement.setInt(4, stan.getKvadratura());
			preparedStatement.setLong(5, (stan.getStanje().ordinal() + 1));
			preparedStatement.executeUpdate();
		}
		
		catch (SQLException | IOException ex) 
		{
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, ex);
			throw new BazaPodatakaException(poruka, ex);
		}
	}
	
	/******************************************* USLUGA *******************************************/
	public static List<Usluga> dohvatiUslugePremaKriterijima(Usluga usluga) throws BazaPodatakaException 
	{
		List<Usluga> listaUsluga = new ArrayList<>();
		try (Connection connection = spajanjeNaBazu()) 
		{ 
			StringBuilder sqlUpit = new StringBuilder("SELECT distinct artikl.id, naslov, opis, cijena, stanje.naziv "
			+ "FROM artikl inner join stanje on stanje.id = artikl.idStanje "
			+ "inner join tipArtikla on tipArtikla.id = artikl.idTipArtikla WHERE tipArtikla.naziv = 'Usluga'");
			
			if (Optional.ofNullable(usluga).isEmpty() == false) 
			{ 
				if (Optional.ofNullable(usluga).map(Usluga::getId).isPresent())
				{
					sqlUpit.append(" AND artikl.id = " + usluga.getId());
				}
				
				if (Optional.ofNullable(usluga.getNaslov()).map(String::isBlank).orElse(true) == false)
				{
					sqlUpit.append(" AND artikl.naslov LIKE '%" + usluga.getNaslov() + "%'");
				}
				
				if (Optional.ofNullable(usluga.getOpis()).map(String::isBlank).orElse(true) == false)
				{
					sqlUpit.append(" AND artikl.opis LIKE '%" + usluga.getOpis() + "%'");
				}
				
				if (Optional.ofNullable(usluga).map(Usluga::getCijena).isPresent())
				{
					sqlUpit.append(" AND artikl.cijena = " + usluga.getCijena());
				}
			}
			
			Statement query = connection.createStatement();
			ResultSet resultSet = query.executeQuery(sqlUpit.toString());
			
			while (resultSet.next()) 
			{
				Long id = resultSet.getLong("id");
				String naslov = resultSet.getString("naslov");
				String opis = resultSet.getString("opis");
				BigDecimal cijena = resultSet.getBigDecimal("cijena");
				String stanje = resultSet.getString("naziv");
				Usluga newUsluga = new Usluga(id, naslov, opis, cijena, Stanje.valueOf(stanje));
				listaUsluga.add(newUsluga);
			}
		}
			
		catch (SQLException | IOException e) 
		{
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}
		
		return listaUsluga;
	}
	
	public static void pohraniNovuUslugu(Usluga usluga) throws BazaPodatakaException 
	{
		try (Connection veza = spajanjeNaBazu()) 
		{
			PreparedStatement preparedStatement = veza
			.prepareStatement(
			"insert into artikl(Naslov, Opis, Cijena, idStanje, idTipArtikla) " +
			"values (?, ?, ?, ?, 2);");
			preparedStatement.setString(1, usluga.getNaslov());
			preparedStatement.setString(2, usluga.getOpis());
			preparedStatement.setBigDecimal(3, usluga.getCijena());
			preparedStatement.setLong(4, (usluga.getStanje().ordinal() + 1));
			preparedStatement.executeUpdate();
		}
		
		catch (SQLException | IOException ex) 
		{
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, ex);
			throw new BazaPodatakaException(poruka, ex);
		}
	}
	
	/******************************************* PRIVATNI KORISNIK *******************************************/
	public static List<PrivatniKorisnik> dohvatiPrivatneKorisnikePremaKriterijima(PrivatniKorisnik privatni) throws BazaPodatakaException 
	{
		List<PrivatniKorisnik> listaPrivatnihKorisnika = new ArrayList<>();
		try (Connection connection = spajanjeNaBazu()) 
		{ 
			StringBuilder sqlUpit = new StringBuilder("SELECT distinct korisnik.id, ime, prezime, email, telefon "
			+ "FROM korisnik inner join tipKorisnika on tipKorisnika.id = korisnik.idTipKorisnika "
			+ "where tipKorisnika.naziv = 'PrivatniKorisnik'");
			
			if (Optional.ofNullable(privatni).isEmpty() == false) 
			{ 
				if (Optional.ofNullable(privatni).map(PrivatniKorisnik::getId).isPresent())
				{
					sqlUpit.append(" AND korisnik.id = " + privatni.getId());
				}
				
				if (Optional.ofNullable(privatni.getIme()).map(String::isBlank).orElse(true) == false)
				{
					sqlUpit.append(" AND korisnik.ime LIKE '%" + privatni.getIme() + "%'");
				}
				
				if (Optional.ofNullable(privatni.getPrezime()).map(String::isBlank).orElse(true) == false)
				{
					sqlUpit.append(" AND korisnik.prezime LIKE '%" + privatni.getPrezime() + "%'");
				}
				
				if (Optional.ofNullable(privatni.getEmail()).map(String::isBlank).orElse(true) == false)
				{
					sqlUpit.append(" AND korisnik.email LIKE '%" + privatni.getEmail() + "%'");
				}
				
				if (Optional.ofNullable(privatni.getTelefon()).map(String::isBlank).orElse(true) == false)
				{
					sqlUpit.append(" AND korisnik.telefon LIKE '%" + privatni.getTelefon() + "%'");
				}
			}
			
			Statement query = connection.createStatement();
			ResultSet resultSet = query.executeQuery(sqlUpit.toString());
			
			while (resultSet.next()) 
			{
				Long id = resultSet.getLong("id");
				String ime = resultSet.getString("ime");
				String prezime = resultSet.getString("prezime");
				String email = resultSet.getString("email");
				String telefon = resultSet.getString("telefon");
				PrivatniKorisnik newPrivatniKorisnik = new PrivatniKorisnik(id, ime, prezime, email, telefon);
				listaPrivatnihKorisnika.add(newPrivatniKorisnik);
			}
		}
			
		catch (SQLException | IOException e) 
		{
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}
		
		return listaPrivatnihKorisnika;
	}
	
	public static void pohraniNovogPrivatnogKorisnika(PrivatniKorisnik privatni) throws BazaPodatakaException 
	{
		try (Connection veza = spajanjeNaBazu()) 
		{
			PreparedStatement preparedStatement = veza
			.prepareStatement(
			"insert into korisnik(Ime, Prezime, Email, Telefon, idTipKorisnika) " +
			"values (?, ?, ?, ?, 1);");
			preparedStatement.setString(1, privatni.getIme());
			preparedStatement.setString(2, privatni.getPrezime());
			preparedStatement.setString(3, privatni.getEmail());
			preparedStatement.setString(4, privatni.getTelefon());
			preparedStatement.executeUpdate();
		}
		
		catch (SQLException | IOException ex) 
		{
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, ex);
			throw new BazaPodatakaException(poruka, ex);
		}
	}
	
	/******************************************* POSLOVNI KORISNIK *******************************************/
	public static List<PoslovniKorisnik> dohvatiPoslovneKorisnikePremaKriterijima(PoslovniKorisnik poslovni) throws BazaPodatakaException 
	{
		List<PoslovniKorisnik> listaPoslovnihKorisnika = new ArrayList<>();
		try (Connection connection = spajanjeNaBazu()) 
		{ 
			StringBuilder sqlUpit = new StringBuilder("SELECT distinct korisnik.id, korisnik.naziv, web, email, telefon "
			+ "FROM korisnik inner join tipKorisnika on tipKorisnika.id = korisnik.idTipKorisnika "
			+ "where tipKorisnika.naziv = 'PoslovniKorisnik'");
			
			if (Optional.ofNullable(poslovni).isEmpty() == false) 
			{ 
				if (Optional.ofNullable(poslovni).map(PoslovniKorisnik::getId).isPresent())
				{
					sqlUpit.append(" AND korisnik.id = " + poslovni.getId());
				}
				
				if (Optional.ofNullable(poslovni.getNaziv()).map(String::isBlank).orElse(true) == false)
				{
					sqlUpit.append(" AND korisnik.naziv LIKE '%" + poslovni.getNaziv() + "%'");
				}
				
				if (Optional.ofNullable(poslovni.getWeb()).map(String::isBlank).orElse(true) == false)
				{
					sqlUpit.append(" AND korisnik.web LIKE '%" + poslovni.getWeb() + "%'");
				}
				
				if (Optional.ofNullable(poslovni.getEmail()).map(String::isBlank).orElse(true) == false)
				{
					sqlUpit.append(" AND korisnik.email LIKE '%" + poslovni.getEmail() + "%'");
				}
				
				if (Optional.ofNullable(poslovni.getTelefon()).map(String::isBlank).orElse(true) == false)
				{
					sqlUpit.append(" AND korisnik.telefon LIKE '%" + poslovni.getTelefon() + "%'");
				}
			}
			
			Statement query = connection.createStatement();
			ResultSet resultSet = query.executeQuery(sqlUpit.toString());
			
			while (resultSet.next()) 
			{
				Long id = resultSet.getLong("id");
				String naziv = resultSet.getString("naziv");
				String web = resultSet.getString("web");
				String email = resultSet.getString("email");
				String telefon = resultSet.getString("telefon");
				PoslovniKorisnik newPoslovniKorisnik = new PoslovniKorisnik(id, naziv, web, email, telefon);
				listaPoslovnihKorisnika.add(newPoslovniKorisnik);
			}
		}
			
		catch (SQLException | IOException e) 
		{
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}
		
		return listaPoslovnihKorisnika;
	}
	
	public static void pohraniNovogPoslovnogKorisnika(PoslovniKorisnik poslovni) throws BazaPodatakaException 
	{
		try (Connection veza = spajanjeNaBazu()) 
		{
			PreparedStatement preparedStatement = veza
			.prepareStatement(
			"insert into korisnik(Naziv, Web, Email, Telefon, idTipKorisnika) " +
			"values (?, ?, ?, ?, 2);");
			preparedStatement.setString(1, poslovni.getNaziv());
			preparedStatement.setString(2, poslovni.getWeb());
			preparedStatement.setString(3, poslovni.getEmail());
			preparedStatement.setString(4, poslovni.getTelefon());
			preparedStatement.executeUpdate();
		}
		
		catch (SQLException | IOException ex) 
		{
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, ex);
			throw new BazaPodatakaException(poruka, ex);
		}
	}
}
