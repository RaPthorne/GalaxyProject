package progetto.jdbc.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

import progetto.jdbc.conn.ManagerDB;
import progetto.jdbc.model.FlussoGenerico;
import progetto.jdbc.model.Galaxy;
import progetto.jdbc.model.GalaxyFull;
import progetto.jdbc.model.RapportoFlussi;
import progetto.jdbc.model.StatisticaGruppoSpettrale;
import progetto.jdbc.persistence.UploadCSV;
import progetto.jdbc.util.Util;

public class Controller {
	
	public static String importFileCommand(String command) throws ClassNotFoundException, SQLException, IOException{
			
		if (Util.countSpaces(command)!=1) return "Comando errato!";
		
		String typeImport = command.substring(0, 1);
		int report = 2;
		
		if (typeImport.equals("1"))
			report = UploadCSV.uploadCSV1(command.substring(2).trim());
		else if (typeImport.equals("2"))
			report = UploadCSV.uploadCSV2(command.substring(2).trim());
		else if (typeImport.equals("3"))
			report = UploadCSV.uploadCSV3(command.substring(2).trim());
		else if (typeImport.equals("4"))
			report = UploadCSV.uploadCSV4(command.substring(2).trim());
		else if (typeImport.equals("5"))
			report = UploadCSV.uploadCSV2(command.substring(2).trim());
		else return "comando sbagliato!";
		
		if (report ==0) return "File importato correttamente";
		else if (report == 1) return "File non trovato";
		else return "Errore nell'importazione del file";
		
	}
	
	public static String registerUserCommand(String command) throws ClassNotFoundException, SQLException{
		
		if (Util.countSpaces(command)!=5) return "Comando errato!";
		
		String nome, cognome, userid, password, email, tipology;
		String tempString = command;
		
		nome = tempString.substring(0, tempString.indexOf(" "));
		tempString = tempString.substring(tempString.indexOf(" ")+1);
		cognome = tempString.substring(0, tempString.indexOf(" "));
		
		tempString = tempString.substring(tempString.indexOf(" ")+1);
		userid = tempString.substring(0, tempString.indexOf(" "));
		
		tempString = tempString.substring(tempString.indexOf(" ")+1);
		password = tempString.substring(0, tempString.indexOf(" "));
		
		tempString = tempString.substring(tempString.indexOf(" ")+1);
		email = tempString.substring(0, tempString.indexOf(" "));
		
		tempString = tempString.substring(tempString.indexOf(" ")+1);
		tipology = tempString;
		
		ManagerDB.registerUser(nome,cognome,userid,password,email,tipology);
		return "Utente aggiunto correttamente";
		
	}
	
	public static String searchByNameCommand(String command) throws ClassNotFoundException, SQLException{
			
		if (Util.countSpaces(command)!=0) return "Comando errato!";
		
		GalaxyFull g = Util.ricercaGalassiaPerNome(command);
		
		if (g==null) return "Galassia non presente nel database";
		
		else {
			String tmp = "Nome: "+g.getGalaxyName()+ " Distanza: "+ g.getDistance()+ " Redshift: "+
					g.getNed().toString() + " Luminosità nev1: "+ g.getLuminosityNev1() + " Luminosità nev2"+
					g.getLuminosityNev2()+ " Luminosità oiv:"+  g.getLuminosityOiv();
			return tmp;
		}
		
	}
	
	public static String searchByRange(String command) throws NumberFormatException, ClassNotFoundException, SQLException{
		
		if (Util.countSpaces(command)!=3) return "Comando errato!";
		
		String tempString = command;
		
		String ra1 = tempString.substring(0, tempString.indexOf(" "));
		tempString = tempString.substring(tempString.indexOf(" ")+1);
		
		String dec1 = tempString.substring(0, tempString.indexOf(" "));
		tempString = tempString.substring(tempString.indexOf(" ")+1);
		
		String raggio = tempString.substring(0, tempString.indexOf(" "));
		tempString = tempString.substring(tempString.indexOf(" ")+1);
		
		tempString = tempString.substring(tempString.indexOf(" ")+1);
		String numeroRisultati = tempString;
		
		
		List<Galaxy> l = new ArrayList<Galaxy>();
		l = Util.ordinaGalassie(Util.ricercaGalassiePerRaggio
				(Double.parseDouble(ra1),Double.parseDouble(dec1), Double.parseDouble(raggio), Integer.parseInt(numeroRisultati)));
		
		String toPrint = "";
		while (!(l.isEmpty())){
			Galaxy g = l.remove(0);
			toPrint = toPrint +("Galassia: "+g.getGalaxyName()+ " Distanza: "+ g.getDistance()+ "<br>");
		}
		
		return toPrint;
		
	}
	
	public static String searchForRedshiftCommand(String command) throws NumberFormatException, ClassNotFoundException, SQLException{
		
		if (Util.countSpaces(command)!=2) return "Comando errato!";
		
		List<Galaxy> l = null;
		
		String tempString = command;
		
		String tipology = tempString.substring(0, tempString.indexOf(" "));
		tempString = tempString.substring(tempString.indexOf(" ")+1);
		
		String value = tempString.substring(0, tempString.indexOf(" "));
		tempString = tempString.substring(tempString.indexOf(" ")+1);
		
		tempString = tempString.substring(tempString.indexOf(" ")+1);
		String numberResult = tempString;
		
		System.out.println("tipologia:" + tipology);
		System.out.println("valore: "+value);
		System.out.println("numeror:"+numberResult);
		if (tipology.equals("<")){
			
			l = Util.ricercaGalassiePerRedshift(Double.parseDouble(value), 0, Integer.parseInt(numberResult));
		}
		
		else if (tipology.equals(">")){
			
			l = Util.ricercaGalassiePerRedshift(Double.parseDouble(value), 1, Integer.parseInt(numberResult));
		}
		
		else return "Comando errato";
		
		String toPrint = "";
		while (!(l.isEmpty())){
			Galaxy g = l.remove(0);
			toPrint = toPrint +("Galassia: "+g.getGalaxyName()+ " Redshift: "+ g.getNed()+ "<br>");
		}
		
		System.out.println(toPrint);
		
		if (toPrint.equals("")) return "Nessun elemento trovato";
		return toPrint;
		
		
	}
	
	public static String searchFluxes(String command) throws ClassNotFoundException, SQLException{
		
		if (Util.countSpaces(command)!=1) return "Comando errato!";
		
		int n = 0;
		for (int i =0;i < command.length();i++){
			if (command.substring(i,i+1).equals(" ")) n++;
		}
		
		String tempString = command;
		
		String galaxy = tempString.substring(0, tempString.indexOf(" "));
		tempString = tempString.substring(tempString.indexOf(" ")+1);
		
		List<String> l = new ArrayList<String>();
		String tmp;
		
		for(int j =0;j< (n-1) ;j++){
			
			tmp = tempString.substring(0, tempString.indexOf(" "));
			tempString = tempString.substring(tempString.indexOf(" ")+1);
			l.add(tmp);
		}
		
		tempString = tempString.substring(tempString.indexOf(" ")+1);
		tmp = tempString;
		l.add(tmp);
	
		List<FlussoGenerico> lf = Util.cercaFlussi(galaxy, l);
		
		String toPrint = "";
		
		while (!(lf.isEmpty())){
			FlussoGenerico f = lf.remove(0);
			toPrint = toPrint +("Flusso: "+ f.getAtomo()+ " ValoreFlusso: "+ f.getValoreFlusso()+
					" Limit:"+ f.getLimitFlusso()+ "<br>");
		}
		
		System.out.println(toPrint);
		
		return toPrint;
		
		
	}
	

	public static String searchRapportFluxCommand(String command) throws ClassNotFoundException, SQLException{
		
		if (Util.countSpaces(command)!=2) return "Comando errato!";
		
		String tempString = command;
		
		String galaxy = tempString.substring(0, tempString.indexOf(" "));
		tempString = tempString.substring(tempString.indexOf(" ")+1);
		
		String atomo1 = tempString.substring(0, tempString.indexOf(" "));
		tempString = tempString.substring(tempString.indexOf(" ")+1);
		
		tempString = tempString.substring(tempString.indexOf(" ")+1);
		String atomo2 = tempString;
		
		RapportoFlussi rf = Util.getRapportoFlussi(galaxy, atomo1, atomo2);
		
		if (rf==null) return "Informazione non presente";
		else {
			return "Rapporto: "+ rf.getRapporto() + " Limit: "+ rf.getLimit();
		}
		
	
	}
	
	public static String getStats(String command) throws ClassNotFoundException, SQLException{
		
		String aper3 = command.substring(0,3);
		String aper5 = command.substring(0,3);
		String aperc = command.substring(0,1);
		String aperfinal = "";
		if ((aper3.equals("3x3"))||(aper5.equals("5x5"))||(aperc.equals("c"))){
			
			if (Util.countSpaces(command)!=3) return "Comando errato!";
			
			if (aper3.equals("3x3")) aperfinal = aper3;
			else if (aper5.equals("5x5")) aperfinal = aper5;
			else if (aperc.equals("c")) aperfinal = aperc;
			else return "Comando errato";
			

			String tempString = command.substring(command.indexOf(" ")+1);
			
			String atomo1 = tempString.substring(0, tempString.indexOf(" "));
			tempString = tempString.substring(tempString.indexOf(" ")+1);
			
			String atomo2 = tempString.substring(0, tempString.indexOf(" "));
			tempString = tempString.substring(tempString.indexOf(" ")+1);
			
			tempString = tempString.substring(tempString.indexOf(" ")+1);
			String gruppospettrale = tempString;
			
			StatisticaGruppoSpettrale sgs = Util.getStatisticheRapportoFlussiAper
					(gruppospettrale, atomo1, atomo2, aperfinal);
			
			if (sgs==null) return "Informazioni non trovata";
			else {
				String toPrint = "ValoreMedio: "+ sgs.getValoreMedio()+ " DeviazioneStandard: "+
						sgs.getDeviazioneStandard()+ " Mediana: "+ sgs.getMediana()+" DeviazioneMediaAssoluta"
						+ sgs.getDeviazioneMediaAssoluta();
				return toPrint;
			}
			
			
		}
		
		else {
			
			if (Util.countSpaces(command)!=2) return "Comando errato!";
			
			String tempString = command;
			
			String atomo1 = tempString.substring(0, tempString.indexOf(" "));
			tempString = tempString.substring(tempString.indexOf(" ")+1);
			
			String atomo2 = tempString.substring(0, tempString.indexOf(" "));
			tempString = tempString.substring(tempString.indexOf(" ")+1);
			
			tempString = tempString.substring(tempString.indexOf(" ")+1);
			String gruppospettrale = tempString;
			
			StatisticaGruppoSpettrale sgs = Util.getStatisticheRapportoFlussi(gruppospettrale, atomo1, atomo2);
			if (sgs==null) return "Informazioni non trovata";
			else {
				String toPrint = "ValoreMedio: "+ sgs.getValoreMedio()+ " DeviazioneStandard: "+
						sgs.getDeviazioneStandard()+ " Mediana: "+ sgs.getMediana()+" DeviazioneMediaAssoluta"
						+ sgs.getDeviazioneMediaAssoluta();
				return toPrint;
			}
			
			
		}
		
	}
	
	public static String searchRapportFluxCont(String command) throws ClassNotFoundException, SQLException{
		
		if (Util.countSpaces(command)!=1) return "Comando errato!";
		
		String tempString = command;
		
		String galaxy = tempString.substring(0, tempString.indexOf(" "));
		tempString = tempString.substring(tempString.indexOf(" ")+1);
		
		tempString = tempString.substring(tempString.indexOf(" ")+1);
		String atomo = tempString;
		
		Double rf = Util.getRapportoTraFlussoEContinuo(galaxy, atomo);
		
		if (rf ==null) return "Informazione non presente";
		else return rf.toString();
		
		
	}


}
