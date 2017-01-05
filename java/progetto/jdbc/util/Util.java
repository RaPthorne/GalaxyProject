package progetto.jdbc.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import progetto.jdbc.conn.ManagerDB;
import progetto.jdbc.model.FlussoGenerico;
import progetto.jdbc.model.Galaxy;
import progetto.jdbc.model.GalaxyFull;
import progetto.jdbc.model.RapportoFlussi;
import progetto.jdbc.model.StatisticaGruppoSpettrale;

public class Util {
	
	public static GalaxyFull ricercaGalassiaPerNome(String stringGalaxy) throws ClassNotFoundException, SQLException{
		
		Connection cDB = ManagerDB.getConnect();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String querySql = "select * from \"galassia\" G full join \"coordinategalassia\" C on (G.nome = C.nomegalassia)"
				+ "where G.nome = ?";
		st = cDB.prepareStatement(querySql);
		
		st.setString(1, stringGalaxy);
		rs = st.executeQuery();
		
		if (rs.next()){
			
			GalaxyFull gf = new GalaxyFull();
			
			if (rs.getString("nome") != null) gf.setGalaxyName(rs.getString("nome"));
			else gf.setGalaxyName(rs.getString("nomegalassia"));
			if (rs.getString("rah") != null)  gf.setRah(rs.getDouble("rah"));
			else gf.setRah(null);
			if (rs.getString("ram") != null) gf.setRam(rs.getDouble("ram"));
			else gf.setRam(null);
			if (rs.getString("ras") != null) gf.setRas(rs.getDouble("ras"));
			else gf.setRas(null);
			gf.setDe(rs.getString("de"));
			if (rs.getString("ded") != null) gf.setDed(rs.getDouble("ded"));
			else gf.setDed(null);
			if (rs.getString("dem") != null) gf.setDem(rs.getDouble("dem"));
			else gf.setDem(null);
			if (rs.getString("des") != null) gf.setDes(rs.getDouble("des"));
			else gf.setDes(null);
			if (rs.getString("distance") != null) gf.setDistance(rs.getDouble("distance"));
			else gf.setDistance(null);
			gf.setLimitNev1(rs.getString("limitnev1"));
			gf.setLimitNev2(rs.getString("limitnev2"));
			gf.setLimitOiv(rs.getString("limitoiv"));
			if (rs.getString("ned") != null) gf.setNed(rs.getDouble("ned"));
			else gf.setNed(null);
			if (rs.getString("luminositynev1") != null) gf.setLuminosityNev1(rs.getDouble("luminositynev1"));
			else gf.setLuminosityNev1(null);
			if (rs.getString("luminositynev2") != null) gf.setLuminosityNev2(rs.getDouble("luminositynev2"));
			else gf.setLuminosityNev2(null);
			if (rs.getString("luminosityoiv") != null) gf.setLuminosityOiv(rs.getDouble("luminosityoiv"));
			else gf.setLuminosityOiv(null);
			if (rs.getString("metallicity") != null) gf.setMetallicity(rs.getDouble("metallicity"));
			else gf.setMetallicity(null);
			if (rs.getString("metallicityerror") != null) gf.setMetallicityError(rs.getDouble("metallicityerror"));
			else gf.setMetallicityError(null);
			
			rs.close();
			st.close();
			cDB.close();
			
			return gf;
		
		}
		
		else {
			
			rs.close();
			st.close();
			cDB.close();
			return null;
		}
			
	}
	
	public static List<Galaxy> ricercaGalassiePerRedshift(Double redshift, int tipology, int n) throws ClassNotFoundException, SQLException{
		/*typology==0 ricerca per minore o uguale, tipology==1 ricerca per maggiore o uguale */
		
		Connection cDB = ManagerDB.getConnect();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		if (tipology==0){
			 
			String querySql = "select nome, ned from \"galassia\" where ned <= ? order by ned";
			st = cDB.prepareStatement(querySql);
			st.setDouble(1, redshift);
			
			rs = st.executeQuery();
			
			List<Galaxy> listGalaxy = new ArrayList<Galaxy>();
			
			while (rs.next()){
				
				if(n > 0){
				
					Galaxy g = new Galaxy();
					g.setGalaxyName(rs.getString("nome"));
					g.setNed(rs.getDouble("ned"));
					listGalaxy.add(g);
					n--;
				
				}
				
			}
			
			rs.close();
			st.close();
			cDB.close();
			
			return listGalaxy;
		}
		
		else {
			
			String querySql = "select nome, ned from \"galassia\" where ned >= ? order by ned";
			st = cDB.prepareStatement(querySql);
			st.setDouble(1, redshift);
			
			rs = st.executeQuery();
			
			List<Galaxy> listGalaxy = new ArrayList<Galaxy>();
			
			while (rs.next()){
				
				if(n > 0){
					
					Galaxy g = new Galaxy();
					g.setGalaxyName(rs.getString("nome"));
					g.setNed(rs.getDouble("ned"));
					listGalaxy.add(g);
					n--;
				
				}
				
			}
			
			rs.close();
			st.close();
			cDB.close();
			
			return listGalaxy;
			
		}
	}
	
	public static List<Galaxy> ricercaGalassiePerRaggio(Double ra1, Double dec1, Double dist, int n) throws ClassNotFoundException, SQLException{
		
		String querySql = "select * from \"coordinategalassia\" where rah is not null and ram is not null and ras is not null"
				+ " and de is not null and ded is not null and dem is not null and des is not null";
		
		Connection cDB = ManagerDB.getConnect();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		st = cDB.prepareStatement(querySql);
		rs = st.executeQuery();
		
		List<Galaxy> listGalaxy = new ArrayList<Galaxy>();
		Double ra2, dec2, d;
		Double rah,ram,ras,ded,dem,des;
		Double calcoloSeno, calcoloCoseno;
		String de;
		
		while (rs.next()){
			
			if (n>0){
				
				rah = rs.getDouble("rah");
				ram = rs.getDouble("ram");
				ras = rs.getDouble("ras");
				ded = rs.getDouble("ded");
				dem = rs.getDouble("dem");
				des = rs.getDouble("des");
				de = rs.getString("de");
				ra2 = 15*(rah + (ram/60) + (ras/3600));
				dec2 = (ded + (dem/60) + (des/3600));
			
				if (de.equals("-")) dec2 = dec2*(-1);
			
				calcoloSeno = Math.sin(Math.toRadians(ra1))*Math.sin(Math.toRadians(ra2));
				calcoloCoseno = Math.cos(Math.toRadians(ra1))*Math.cos(Math.toRadians(ra2)*Math.cos(Math.toRadians((dec1 - dec2))));
				d = calcoloSeno + calcoloCoseno;
			
				if (d <= dist){
					
					Galaxy g = new Galaxy();
					g.setGalaxyName(rs.getString("nomegalassia"));
					g.setDistance(d);
					listGalaxy.add(g);
					n--;
				}
			}
		}
		
		rs.close();
		st.close();
		cDB.close();
		
		return listGalaxy;
	}
	
	public static List<Galaxy> ordinaGalassie(List<Galaxy> listGalaxy){
		
		int N = listGalaxy.size();
		Galaxy g1, g2;
		for(int i=0; i<(N-1); i++){
			int min = i;
			for (int j=(i+1); j<N; j++){
				if (listGalaxy.get(min).getDistance() > listGalaxy.get(j).getDistance()) min = j;
			}
			if (min != i){
				g1 = listGalaxy.get(i);
				g2 = listGalaxy.get(min);
				listGalaxy.set(min,g1);
				listGalaxy.set(i, g2);
			}
		}
		
		return listGalaxy;
		
	}
	
	public static FlussoGenerico cercaFlusso(String nomeGalassia, String nomeAtomo, int tipology) throws SQLException, ClassNotFoundException{
		/* 0 righeaper, 1 continuoaper,2 righeirsmode */
		
		String querySql = null;
		
		if (tipology ==0){
				
			querySql = "select * from \"flussorigheaper\" where nomegalassia = ? "
				+ "and atomo = ? and valoreflusso is not null";
		}
		
		else if (tipology == 1){
			
			querySql = "select * from \"flussocontinuoaper\" where nomegalassia = ? "
					+ "and atomo = ? and valoreflusso is not null";
			}
		
		else {
			
			querySql = "select * from \"flussorigheirsmode\" where nomegalassia = ? "
					+ "and atomo = ? and valoreflusso is not null";
		}
		
		Connection cDB = ManagerDB.getConnect();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		st = cDB.prepareStatement(querySql);
		st.setString(1, nomeGalassia);
		st.setString(2, nomeAtomo);
		
		rs = st.executeQuery();
		
		if (rs.next()){
			
			FlussoGenerico fg = new FlussoGenerico();
			fg.setGalaxyName(nomeGalassia);
			fg.setAtomo(nomeAtomo);
			fg.setValoreFlusso(rs.getDouble("valoreflusso"));
			fg.setLimitFlusso(rs.getString("limitflusso"));
			if (rs.getString("erroreflusso") != null) fg.setErroreFlusso(rs.getDouble("erroreflusso"));
			else fg.setErroreFlusso(null);
			
			return fg;
			
		}
		
		rs.close();
		st.close();
		cDB.close();
		
		return null;
		
	}
	
	public static  List<FlussoGenerico> cercaFlussi(String nomeGalassia, List<String> listaFlussi) throws ClassNotFoundException, SQLException{
		
		List<FlussoGenerico> lf = new ArrayList<FlussoGenerico>();
		int N = listaFlussi.size();
		FlussoGenerico f = null;
		
		for(int i=0; i<N; i++){
			
			f = cercaFlusso(nomeGalassia, listaFlussi.get(i), 0);
			if (f != null) lf.add(f);
			else {
				f = cercaFlusso(nomeGalassia, listaFlussi.get(i), 1);
				if (f != null) lf.add(f);
				else {
					f = cercaFlusso(nomeGalassia, listaFlussi.get(i), 2);
					if (f != null) lf.add(f);
				}
			}
		}
		
		return lf;
		
	}
	
	public static RapportoFlussi getRapportoFlussi(String nomeGalassia, String atomo1, String atomo2) throws ClassNotFoundException, SQLException{
		
		FlussoGenerico f = null;
		f = cercaFlusso(nomeGalassia, atomo1, 0);
		if (f == null){
			f = cercaFlusso(nomeGalassia, atomo1, 1);
			if (f == null){
				f = cercaFlusso(nomeGalassia, atomo1, 2);
			}
		}
		
		FlussoGenerico f2 = null;
		f2 = cercaFlusso(nomeGalassia, atomo2, 0);
		if (f2 == null){
			f2 = cercaFlusso(nomeGalassia, atomo2, 1);
			if (f2 == null){
				f2 = cercaFlusso(nomeGalassia, atomo2, 2);
			}
		}
		
		if ((f == null)||(f2 == null)||(f.getValoreFlusso()==null)||(f2.getValoreFlusso()==null)){
			
			return null;
		}
		
		RapportoFlussi rf = new RapportoFlussi();
		rf.setRapporto(f.getValoreFlusso()/f2.getValoreFlusso());
		
		if ((f.getLimitFlusso() != null)&&(f2.getLimitFlusso() == null)) rf.setLimit("upper-limit");
		else if ((f.getLimitFlusso() == null)&&(f2.getLimitFlusso() != null)) rf.setLimit("lower-limit");
		else rf.setLimit(null);
		
		return rf;
		
	}
	
	public static StatisticaGruppoSpettrale getStatisticheRapportoFlussi(String gruppoSpettrale, String atomo1, String atomo2) throws ClassNotFoundException, SQLException{
		/* 0 righeaper, 1 continuoaper,2 righeirsmode */
		
		String queryGruppoSpettrale = "select nome from \"galassia\" where spectralgroup = ?";
		
		List<Double> listaRapporti = new ArrayList<Double>();
		
		Connection cDB = ManagerDB.getConnect();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		st = cDB.prepareStatement(queryGruppoSpettrale);
		st.setString(1,gruppoSpettrale);
		
		rs = st.executeQuery();
		RapportoFlussi rf = null;
		
		while (rs.next()){
			//per ogni galassia, computa la richiesta!
			rf = getRapportoFlussi(rs.getString("nome"), atomo1, atomo2);
			if (rf != null) {
				listaRapporti.add(rf.getRapporto());
				System.out.println(rf.getRapporto());
			}
			
			
		}
		
		rs.close();
		st.close();
		cDB.close();
		
		return calcoloStatistiche(listaRapporti, gruppoSpettrale, atomo1, atomo2);
		
	}
	
	public static StatisticaGruppoSpettrale calcoloStatistiche(List<Double> listaValori, String gruppoSpettrale,
			String atomo1, String atomo2){
		
		StatisticaGruppoSpettrale statistica = new StatisticaGruppoSpettrale();
		statistica.setGruppoSpettrale(gruppoSpettrale);
		statistica.setAtomo1(atomo1);
		statistica.setAtomo2(atomo2);
		statistica.setValoreMedio(operazioniStatistiche.calcoloMedia(listaValori));
		statistica.setMediana(operazioniStatistiche.calcoloMediana(listaValori));
		statistica.setDeviazioneStandard(operazioniStatistiche.calcoloDeviazioneStandard(listaValori));
		statistica.setDeviazioneMediaAssoluta(operazioniStatistiche.calcoloDeviazioneMediaAssoluta(listaValori));
		
		return statistica;
		
	}
	
	public static FlussoGenerico cercaFlussoAper(String nomeGalassia, String nomeAtomo, int tipology, String aper) throws SQLException, ClassNotFoundException{
		/* 0 righeaper, 1 continuoaper*/
		
		String querySql = null;
		
		if (tipology ==0){
				
			querySql = "select * from \"flussorigheaper\" where nomegalassia = ? "
				+ "and atomo = ? and valoreflusso is not null and aperture = ?";
		}
		
		else if (tipology == 1){
			
			querySql = "select * from \"flussocontinuoaper\" where nomegalassia = ? "
					+ "and atomo = ? and valoreflusso is not null and aperture = ?";
			}
		
		
		
		Connection cDB = ManagerDB.getConnect();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		st = cDB.prepareStatement(querySql);
		st.setString(1, nomeGalassia);
		st.setString(2, nomeAtomo);
		st.setString(3, aper);
		
		rs = st.executeQuery();
		
		if (rs.next()){
			
			FlussoGenerico fg = new FlussoGenerico();
			fg.setGalaxyName(nomeGalassia);
			fg.setAtomo(nomeAtomo);
			fg.setValoreFlusso(rs.getDouble("valoreflusso"));
			fg.setLimitFlusso(rs.getString("limitflusso"));
			if (rs.getString("erroreflusso") != null) fg.setErroreFlusso(rs.getDouble("erroreflusso"));
			else fg.setErroreFlusso(null);
			
			return fg;
			
		}
		
		rs.close();
		st.close();
		cDB.close();
		
		return null;
		
	}
	

	public static RapportoFlussi getRapportoFlussiAper(String nomeGalassia, String atomo1, String atomo2, String aper) throws ClassNotFoundException, SQLException{
		
		FlussoGenerico f = null;
		f = cercaFlussoAper(nomeGalassia, atomo1, 0, aper);
		if (f == null){
			f = cercaFlussoAper(nomeGalassia, atomo1, 1, aper);
		}
		
		FlussoGenerico f2 = null;
		f2 = cercaFlussoAper(nomeGalassia, atomo2, 0, aper);
		if (f2 == null){
			f2 = cercaFlussoAper(nomeGalassia, atomo2, 1,aper);
		}
		
		if ((f == null)||(f2 == null)||(f.getValoreFlusso()==null)||(f2.getValoreFlusso()==null)){
			
			return null;
		}
		
		RapportoFlussi rf = new RapportoFlussi();
		rf.setRapporto(f.getValoreFlusso()/f2.getValoreFlusso());
		
		if ((f.getLimitFlusso() != null)&&(f2.getLimitFlusso() == null)) rf.setLimit("upper-limit");
		else if ((f.getLimitFlusso() == null)&&(f2.getLimitFlusso() != null)) rf.setLimit("lower-limit");
		else rf.setLimit(null);
		
		return rf;
		
	}
	
	public static StatisticaGruppoSpettrale getStatisticheRapportoFlussiAper(String gruppoSpettrale, String atomo1, String atomo2, String aper) throws ClassNotFoundException, SQLException{
		/* 0 righeaper, 1 continuoaper*/
		
		String queryGruppoSpettrale = "select nome from \"galassia\" where spectralgroup = ?";
		
		List<Double> listaRapporti = new ArrayList<Double>();
		
		Connection cDB = ManagerDB.getConnect();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		st = cDB.prepareStatement(queryGruppoSpettrale);
		st.setString(1,gruppoSpettrale);
		
		rs = st.executeQuery();
		RapportoFlussi rf = null;
		
		while (rs.next()){
			//per ogni galassia, computa la richiesta!
			rf = getRapportoFlussiAper(rs.getString("nome"), atomo1, atomo2, aper);
			if (rf != null) {
				listaRapporti.add(rf.getRapporto());
				System.out.println(rf.getRapporto());
			}
			
			
		}
		
		rs.close();
		st.close();
		cDB.close();
		
		return calcoloStatistiche(listaRapporti, gruppoSpettrale, atomo1, atomo2);
		
	}
	
	
	public static Double getRapportoTraFlussoEContinuo(String nomeGalassia, String atomo) throws ClassNotFoundException, SQLException{
		
		FlussoGenerico f = null;
		f = cercaFlusso(nomeGalassia, atomo, 0);
		if (f == null){
			f = cercaFlusso(nomeGalassia, atomo, 2);
		}
		
		FlussoGenerico f2 = null;
		f2 = cercaFlusso(nomeGalassia, atomo, 1);
		

		if ((f == null)||(f2 == null)||(f.getValoreFlusso()==null)||(f2.getValoreFlusso()==null)){
			
			return null;
		}
		
		
		return (f.getValoreFlusso()/f2.getValoreFlusso());
		
		
	
	}
	
	public static int countSpaces(String string) {
	    
		int spaces = 0;
	    for(int i = 0; i < string.length(); i++) {
	        spaces += (Character.isWhitespace(string.charAt(i))) ? 1 : 0;
	    }
	    
	    return spaces;
	}

}
