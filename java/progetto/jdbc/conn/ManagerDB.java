package progetto.jdbc.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import progetto.jdbc.control.Controller;
import progetto.jdbc.model.FlussoGenerico;
import progetto.jdbc.model.Galaxy;
import progetto.jdbc.model.RapportoFlussi;
import progetto.jdbc.model.StatisticaGruppoSpettrale;
import progetto.jdbc.persistence.UploadCSV;
import progetto.jdbc.util.Util;
import progetto.jdbc.util.operazioniStatistiche;

public class ManagerDB {
	
	public static Connection getConnect() throws ClassNotFoundException, SQLException{
		
		Connection c = null;
		String url = "jdbc:postgresql://localhost:5432/progettodb";
		Class.forName("org.postgresql.Driver");
		c = DriverManager.getConnection(url, "postgres", "password");
		
		return c;
		
	}
	
	public static void creationTable(){
			
			String tableUtente = "CREATE TABLE utente ( nome varchar(20), cognome varchar(20), userid varchar(20),"
					+ " password varchar(30), email varchar(30), tipology varchar(10), primary key (userid), unique (email) )";
			
			String tableGalassia = "CREATE TABLE galassia ( nome varchar(30) primary key, ned double precision, "
					+ "distance double precision, spectralgroup varchar(10), limitnev1 character, "
					+ "luminositynev1 double precision, limitnev2 character, luminositynev2 double precision, "
					+ "limitoiv character, luminosityoiv double precision, metallicity double precision, "
					+ "metallicityerror double precision, alternativename varchar(30) )";


			String tableCoordinateGalassia = "CREATE TABLE coordinategalassia ( nomegalassia varchar(30),"
							+ "rah double precision, ram double precision, ras double precision, de character, ded double precision,"
							+ "dem double precision, des double precision, primary key (nomegalassia) )";


			String tableFlussoContinuoAper = "CREATE TABLE flussocontinuoaper ( nomegalassia varchar(30),"
							+ "atomo varchar(10), aperture varchar(10), satellite varchar(30), valoreflusso double precision,"
							+ "limitflusso varchar(5), erroreflusso double precision, primary key (nomegalassia, atomo, aperture) )  ";


			String tableFlussoRigheAper = "CREATE TABLE flussorigheaper ( nomegalassia varchar(30),"
							+ "atomo varchar(10), aperture varchar(10), satellite varchar(30), valoreflusso double precision,"
							+ "limitflusso varchar(5), erroreflusso double precision, primary key (nomegalassia, atomo, aperture) )  ";


			String tableFlussoIrsMode = "CREATE TABLE flussorigheirsmode ( nomegalassia varchar(30),"
							+ "atomo varchar(10), irsmode varchar(10), satellite varchar(30), valoreflusso double precision,"
							+ "limitflusso varchar(5), erroreflusso double precision, primary key (nomegalassia, atomo)  )  ";
			
			
			Connection c = null;
			String urlinit = "jdbc:postgresql://localhost:5432/";
			
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				c = DriverManager.getConnection(urlinit, "postgres", "password");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			Statement at = null;
			try {
				at = c.createStatement();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				at.executeUpdate("CREATE DATABASE progettodb");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				c.close();
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			
			String url = "jdbc:postgresql://localhost:5432/progettodb";
			
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			Connection conn = null;
			try {
				conn = DriverManager.getConnection(url, "postgres", "password");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			Statement st = null;
			try {
				st = conn.createStatement();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
				
			try {
				st.executeUpdate(tableGalassia);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			try {
				st.executeUpdate(tableCoordinateGalassia);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			try {
				st.executeUpdate(tableFlussoContinuoAper);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			try {
				st.executeUpdate(tableFlussoRigheAper);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			try {
				st.executeUpdate(tableFlussoIrsMode);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			try {
				st.executeUpdate(tableUtente);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				registerUser("Giorgio","Iannone","iangio","miapass","giorgioiannone@gmail.com","admin");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		
		Double rf = Util.getRapportoTraFlussoEContinuo("3C120", "CII158");
		if (rf != null) System.out.println(rf.toString());
	}
	
	public static String loginUser(String userid, String password) throws ClassNotFoundException, SQLException{
		
		Connection c = getConnect();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String querySql = "select * from \"utente\" where userid = ? and password = ?";
		st = c.prepareStatement(querySql);
		
		st.setString(1, userid);
		st.setString(2, password);
		rs = st.executeQuery();
		
		if (rs.next()){
			return rs.getString("tipology");
		}
		
		else return null;
		
	}
	
	public static boolean registerUser(String nome, String cognome, String userid, 
			String password, String email, String tipology ) throws ClassNotFoundException, SQLException{
		
		Connection c = getConnect();
		PreparedStatement st = null;
		
		String insertSql = "insert into \"utente\"(nome, cognome, userid, password, email, tipology)"
				+ "values (?,?,?,?,?,?) ";
		
		st = c.prepareStatement(insertSql);
		st.setString(1, nome);
		st.setString(2, cognome);
		st.setString(3, userid);
		st.setString(4, password);
		st.setString(5, email);
		st.setString(6, tipology);
		
		
		return st.execute();
		
	}
	
}
