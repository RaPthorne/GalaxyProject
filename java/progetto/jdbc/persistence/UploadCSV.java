package progetto.jdbc.persistence;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVReader;

import progetto.jdbc.conn.ManagerDB;
import progetto.jdbc.model.FlussoContinuoAper;
import progetto.jdbc.model.FlussoRigheAper;
import progetto.jdbc.model.FlussoRigheIrsMode;
import progetto.jdbc.model.Galaxy;
import progetto.jdbc.model.GalaxyPosition;

public class UploadCSV {
	
	public static int uploadCSV1(String percorsoCSV) throws ClassNotFoundException, SQLException{
		
		Connection cDB = ManagerDB.getConnect();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		CSVReader reader = null;
		
		try {
			reader = new CSVReader(new FileReader(percorsoCSV), ';');
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return 1; /* file non trovato */
		}
		
		String [] nextLine;
		
		String insertGalaxy = "insert into \"galassia\"(nome,ned,distance,spectralgroup,limitnev1,luminositynev1,"
				+ "limitnev2,luminositynev2,limitoiv,luminosityoiv,metallicity,metallicityerror,alternativename)"
				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String searchGalaxy = "select * from \"galassia\" G where G.nome = ?";
		String deleteGalaxy = "delete from \"galassia\" G where G.nome = ? ";
		
		String searchGalaxyPosition = "select * from \"coordinategalassia\" C where C.nomegalassia = ?";
		String insertGalaxyPosition = "insert into \"coordinategalassia\"(nomegalassia, rah, ram, ras, de, ded, dem, des) values(?,?,?,?,?,?,?,?)";
		String deleteGalaxyPosition = "delete from \"coordinategalassia\" C where C.nomegalassia = ?";
		
		try {
			while ((nextLine = reader.readNext()) != null) {
				
				st = cDB.prepareStatement(searchGalaxy);
				st.setString(1, nextLine[0].trim());
				rs = st.executeQuery();
				
				if (!(rs.next()))	{
			
					Galaxy g = new Galaxy();
				
					if (nextLine[0].trim().equals("")) g.setGalaxyName(null);
					else g.setGalaxyName(nextLine[0].trim());
					if (nextLine[8].trim().equals("")) g.setNed(null);
					else g.setNed(Double.parseDouble(nextLine[8].trim()));
					if (nextLine[9].trim().equals("")) g.setDistance(null);
					else g.setDistance(Double.parseDouble(nextLine[9].trim()));
					if (nextLine[11].trim().equals("")) g.setSpectralGroup(null);
					else g.setSpectralGroup(nextLine[11].trim());
					if (nextLine[16].trim().equals("")) g.setLimitNev1(null);
					else g.setLimitNev1(nextLine[16].trim());
					if (nextLine[17].trim().equals("")) g.setLuminosityNev1(null);
					else g.setLuminosityNev1(Double.parseDouble(nextLine[17].trim()));
					if (nextLine[18].trim().equals("")) g.setLimitNev2(null);
					else g.setLimitNev2(nextLine[18].trim());
					if (nextLine[19].trim().equals("")) g.setLuminosityNev2(null);
					else g.setLuminosityNev2(Double.parseDouble(nextLine[19].trim()));
					if (nextLine[20].trim().equals("")) g.setLimitOiv(null);
					else g.setLimitOiv(nextLine[20].trim());
					if (nextLine[21].trim().equals("")) g.setLuminosityOiv(null);
					else g.setLuminosityOiv(Double.parseDouble(nextLine[21].trim()));
					if (nextLine[22].trim().equals("")) g.setMetallicity(null);
					else g.setMetallicity(Double.parseDouble(nextLine[22].trim()));
					if (nextLine[23].trim().equals("")) g.setMetallicityError(null);
					else g.setMetallicityError(Double.parseDouble(nextLine[23].trim()));
					if (nextLine[25].trim().equals("")) g.setAlternativeName(null);
					else g.setAlternativeName(nextLine[25].trim());
				
					st = cDB.prepareStatement(insertGalaxy);
				
					st.setString(1, g.getGalaxyName());
					if ((g.getNed())!= null) st.setDouble(2, g.getNed());
					else st.setNull(2,  java.sql.Types.DOUBLE);
					if (g.getDistance() != null) st.setDouble(3, g.getDistance());
					else st.setNull(3, java.sql.Types.DOUBLE);
					st.setString(4, g.getSpectralGroup());
					st.setString(5, g.getLimitNev1());
					if (g.getLuminosityNev1() != null) st.setDouble(6, g.getLuminosityNev1());
					else st.setNull(6,java.sql.Types.DOUBLE);
					st.setString(7, g.getLimitNev2());
					if (g.getLuminosityNev2() != null) st.setDouble(8, g.getLuminosityNev2());
					else st.setNull(8, java.sql.Types.DOUBLE);
					st.setString(9, g.getLimitOiv());
					if (g.getLuminosityOiv() != null) st.setDouble(10, g.getLuminosityOiv());
					else st.setNull(10, java.sql.Types.DOUBLE);
					if (g.getMetallicity() != null) st.setDouble(11, g.getMetallicity());
					else st.setNull(11, java.sql.Types.DOUBLE );
					if (g.getMetallicityError() != null) st.setDouble(12, g.getMetallicityError());
					else st.setNull(12, java.sql.Types.DOUBLE);
					st.setString(13, g.getAlternativeName());
				
					st.executeUpdate();
				}
				
				else {
					
					Galaxy g = new Galaxy();
					
					g.setGalaxyName(rs.getString("nome"));
					if (rs.getString("ned") != null) g.setNed(rs.getDouble("ned"));
					else g.setNed(null);
					if (rs.getString("distance") != null) g.setDistance(rs.getDouble("distance"));
					else g.setDistance(null);
					g.setSpectralGroup(rs.getString("spectralgroup"));
					g.setLimitNev1(rs.getString("limitnev1"));
					if (rs.getString("luminositynev1") != null) g.setLuminosityNev1(rs.getDouble("luminositynev1"));
					else g.setLuminosityNev1(null);
					g.setLimitNev2(rs.getString("limitnev2"));
					if (rs.getString("luminositynev2") != null) g.setLuminosityNev2(rs.getDouble("luminositynev2"));
					else g.setLuminosityNev2(null);
					g.setLimitOiv(rs.getString("limitoiv"));
					if (rs.getString("luminosityoiv") != null) g.setLuminosityOiv(rs.getDouble("luminosityoiv"));
					else g.setLuminosityOiv(null);
					if (rs.getString("metallicity") != null) g.setMetallicity(rs.getDouble("metallicity"));
					else g.setMetallicity(null);
					if (rs.getString("metallicityerror") != null) g.setMetallicityError(rs.getDouble("metallicityerror"));
					else g.setMetallicityError(null);
					g.setAlternativeName(rs.getString("alternativename"));
					
					st.close();
					st = cDB.prepareStatement(deleteGalaxy);
					st.setString(1, nextLine[0].trim());
					st.executeUpdate();
					
					if (nextLine[0].trim().equals("")) ;
					else g.setGalaxyName(nextLine[0].trim());
					if (nextLine[8].trim().equals("")) ;
					else g.setNed(Double.parseDouble(nextLine[8].trim()));
					if (nextLine[9].trim().equals("")) ;
					else g.setDistance(Double.parseDouble(nextLine[9].trim()));
					if (nextLine[11].trim().equals("")) ;
					else g.setSpectralGroup(nextLine[11].trim());
					if (nextLine[16].trim().equals("")) ;
					else g.setLimitNev1(nextLine[16].trim());
					if (nextLine[17].trim().equals("")) ;
					else g.setLuminosityNev1(Double.parseDouble(nextLine[17].trim()));
					if (nextLine[18].trim().equals("")) ;
					else g.setLimitNev2(nextLine[18].trim());
					if (nextLine[19].trim().equals("")) ;
					else g.setLuminosityNev2(Double.parseDouble(nextLine[19].trim()));
					if (nextLine[20].trim().equals("")) ;
					else g.setLimitOiv(nextLine[20].trim());
					if (nextLine[21].trim().equals("")) ;
					else g.setLuminosityOiv(Double.parseDouble(nextLine[21].trim()));
					if (nextLine[22].trim().equals("")) ;
					else g.setMetallicity(Double.parseDouble(nextLine[22].trim()));
					if (nextLine[23].trim().equals("")) ;
					else g.setMetallicityError(Double.parseDouble(nextLine[23].trim()));
					if (nextLine[25].trim().equals("")) ;
					else g.setAlternativeName(nextLine[25].trim());
					
					st = cDB.prepareStatement(insertGalaxy);
					
					st.setString(1, g.getGalaxyName());
					if ((g.getNed())!= null) st.setDouble(2, g.getNed());
					else st.setNull(2,  java.sql.Types.DOUBLE);
					if (g.getDistance() != null) st.setDouble(3, g.getDistance());
					else st.setNull(3, java.sql.Types.DOUBLE);
					st.setString(4, g.getSpectralGroup());
					st.setString(5, g.getLimitNev1());
					if (g.getLuminosityNev1() != null) st.setDouble(6, g.getLuminosityNev1());
					else st.setNull(6,java.sql.Types.DOUBLE);
					st.setString(7, g.getLimitNev2());
					if (g.getLuminosityNev2() != null) st.setDouble(8, g.getLuminosityNev2());
					else st.setNull(8, java.sql.Types.DOUBLE);
					st.setString(9, g.getLimitOiv());
					if (g.getLuminosityOiv() != null) st.setDouble(10, g.getLuminosityOiv());
					else st.setNull(10, java.sql.Types.DOUBLE);
					if (g.getMetallicity() != null) st.setDouble(11, g.getMetallicity());
					else st.setNull(11, java.sql.Types.DOUBLE );
					if (g.getMetallicityError() != null) st.setDouble(12, g.getMetallicityError());
					else st.setNull(12, java.sql.Types.DOUBLE);
					st.setString(13, g.getAlternativeName());
				
					st.executeUpdate();
					
				}
				
				st = cDB.prepareStatement(searchGalaxyPosition);
				st.setString(1, nextLine[0].trim());
				rs = st.executeQuery();
				
				if (!(rs.next())){
					
					GalaxyPosition gp = new GalaxyPosition();
					
					if (nextLine[0].trim().equals("")) gp.setGalaxyName(null);
					else gp.setGalaxyName(nextLine[0].trim());
					if (nextLine[1].trim().equals("")) gp.setRah(null);
					else gp.setRah(Double.parseDouble(nextLine[1].trim()));
					if (nextLine[2].trim().equals("")) gp.setRam(null);
					else gp.setRam(Double.parseDouble(nextLine[2].trim()));
					if (nextLine[3].trim().equals("")) gp.setRas(null);
					else gp.setRas(Double.parseDouble(nextLine[3].trim()));
					if (nextLine[4].trim().equals("")) gp.setDe(null);
					else gp.setDe(nextLine[4]);
					if (nextLine[5].trim().equals("")) gp.setDed(null);
					else gp.setDed(Double.parseDouble(nextLine[5].trim()));
					if (nextLine[6].trim().equals("")) gp.setDem(null);
					else gp.setDem(Double.parseDouble(nextLine[6].trim()));
					if (nextLine[7].trim().equals("")) gp.setDes(null);
					else gp.setDes(Double.parseDouble(nextLine[7].trim()));
					
					st = cDB.prepareStatement(insertGalaxyPosition );
					
					st.setString(1, gp.getGalaxyName());
					if (gp.getRah() != null) st.setDouble(2, gp.getRah());
					else st.setNull(2, java.sql.Types.DOUBLE);
					if (gp.getRam() != null) st.setDouble(3, gp.getRam());
					else st.setNull(3, java.sql.Types.DOUBLE);
					if (gp.getRas() != null) st.setDouble(4, gp.getRas());
					else st.setNull(4, java.sql.Types.DOUBLE);
					st.setString(5, gp.getDe());
					if (gp.getDed() != null) st.setDouble(6, gp.getDed());
					else st.setNull(6, java.sql.Types.DOUBLE);
					if (gp.getDem() != null) st.setDouble(7, gp.getDem());
					else st.setNull(7, java.sql.Types.DOUBLE);
					if (gp.getDes() != null) st.setDouble(8, gp.getDes());
					else st.setNull(8, java.sql.Types.DOUBLE);
					
					st.executeUpdate();
					
				}
				
				else {
					
					GalaxyPosition gp = new GalaxyPosition();
					
					gp.setGalaxyName(rs.getString("nomegalassia"));
					if (rs.getString("rah") != null) gp.setRah(rs.getDouble("rah"));
					else gp.setRah(null);
					if (rs.getString("ram") != null) gp.setRam(rs.getDouble("ram"));
					else gp.setRam(null);
					if (rs.getString("ras") != null) gp.setRas(rs.getDouble("ras"));
					else gp.setRas(null);
					gp.setDe(rs.getString("de"));
					if (rs.getString("ded") != null) gp.setDed(rs.getDouble("ded"));
					else gp.setDed(null);
					if (rs.getString("dem") != null) gp.setDem(rs.getDouble("dem"));
					else gp.setDem(null);
					if (rs.getString("des") != null) gp.setDes(rs.getDouble("des"));
					else gp.setDes(null);
					
					st.close();
					st = cDB.prepareStatement(deleteGalaxyPosition);
					st.setString(1, nextLine[0].trim());
					st.executeUpdate();
					
					if (nextLine[0].trim().equals("")) ;
					else gp.setGalaxyName(nextLine[0].trim());
					if (nextLine[1].trim().equals(""));
					else gp.setRah(Double.parseDouble(nextLine[1].trim()));
					if (nextLine[2].trim().equals("")) ;
					else gp.setRam(Double.parseDouble(nextLine[2].trim()));
					if (nextLine[3].trim().equals("")) ;
					else gp.setRas(Double.parseDouble(nextLine[3].trim()));
					if (nextLine[4].trim().equals("")) ;
					else gp.setDe(nextLine[4]);
					if (nextLine[5].trim().equals("")) ;
					else gp.setDed(Double.parseDouble(nextLine[5].trim()));
					if (nextLine[6].trim().equals("")) ;
					else gp.setDem(Double.parseDouble(nextLine[6].trim()));
					if (nextLine[7].trim().equals("")) ;
					else gp.setDes(Double.parseDouble(nextLine[7].trim()));
					
					st = cDB.prepareStatement(insertGalaxyPosition );
					
					st.setString(1, gp.getGalaxyName());
					if (gp.getRah() != null) st.setDouble(2, gp.getRah());
					else st.setNull(2, java.sql.Types.DOUBLE);
					if (gp.getRam() != null) st.setDouble(3, gp.getRam());
					else st.setNull(3, java.sql.Types.DOUBLE);
					if (gp.getRas() != null) st.setDouble(4, gp.getRas());
					else st.setNull(4, java.sql.Types.DOUBLE);
					st.setString(5, gp.getDe());
					if (gp.getDed() != null) st.setDouble(6, gp.getDed());
					else st.setNull(6, java.sql.Types.DOUBLE);
					if (gp.getDem() != null) st.setDouble(7, gp.getDem());
					else st.setNull(7, java.sql.Types.DOUBLE);
					if (gp.getDes() != null) st.setDouble(8, gp.getDes());
					else st.setNull(8, java.sql.Types.DOUBLE);

					st.executeUpdate();
					
				}
			}
			
			rs.close();
			st.close();
			cDB.close();
			reader.close();
			return 0;
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 2;
		}
	}
	
	public static int uploadCSV2(String percorsoCSV) throws ClassNotFoundException, SQLException, IOException{
		
		Connection cDB = ManagerDB.getConnect();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		CSVReader reader = null;
		
		String searchFlussoRigheAper = "select * from \"flussorigheaper\" where nomegalassia = ? "
				+ "and atomo = ? and aperture = ?";
		String deleteFlussoRigheAper = "delete from \"flussorigheaper\" where nomegalassia = ? "
				+ "and atomo = ? and aperture = ?";
		String insertFlussoRigheAper = "insert into \"flussorigheaper\"(nomegalassia, atomo, aperture, "
				+ "satellite, valoreflusso, limitflusso, erroreflusso) values (?,?,?,?,?,?,?)";
		
		try {
			reader = new CSVReader(new FileReader(percorsoCSV), ';');
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return 1;
		}
		
		List<String> listaAtomi = Arrays.asList("OIII52","NIII57","OI63","OIII88","NII122","OI145","CII158");
		
		String [] nextLine;

		try {
			while ((nextLine = reader.readNext()) != null){
				
				for(int i = 0; i <= 6; i++){
					
					st = cDB.prepareStatement(searchFlussoRigheAper);
					st.setString(1, nextLine[0].trim());
					st.setString(2, listaAtomi.get(i));
					st.setString(3, nextLine[22].trim());
					rs = st.executeQuery();
					
					if (!(rs.next()))	{
						
						FlussoRigheAper f = new FlussoRigheAper();
						
						if (nextLine[0].trim().equals("")) f.setGalaxyName(null);
						else f.setGalaxyName(nextLine[0].trim());
						f.setAtomo(listaAtomi.get(i));
						if (nextLine[22].trim().equals("")) f.setAperture(null);
						else  f.setAperture(nextLine[22].trim());
						if (nextLine[2+(3*i)].trim().equals("")) f.setValoreFlusso(null);
						else f.setValoreFlusso(Double.parseDouble(nextLine[2+(3*i)].trim()));
						if (nextLine[1+(3*i)].trim().equals("")) f.setLimitFlusso(null);
						else f.setLimitFlusso(nextLine[1+(3*i)].trim());
						if (nextLine[3+(3*i)].trim().equals("")) f.setErroreFlusso(null);
						else f.setErroreFlusso(Double.parseDouble(nextLine[3+(3*i)].trim()));
						f.setSatellite("Hersel/PACS");
						
						st = cDB.prepareStatement(insertFlussoRigheAper);
						
						st.setString(1, f.getGalaxyName());
						st.setString(2, f.getAtomo());
						st.setString(3, f.getAperture());
						st.setString(4, f.getSatellite());
						if (f.getValoreFlusso() != null) st.setDouble(5, f.getValoreFlusso());
						else st.setNull(5,java.sql.Types.DOUBLE);
						st.setString(6, f.getLimitFlusso());
						if (f.getErroreFlusso() != null) st.setDouble(7, f.getErroreFlusso());
						else st.setNull(7,java.sql.Types.DOUBLE);
						
						st.executeUpdate();
						
					}
					
					else {
						
						FlussoRigheAper f = new FlussoRigheAper();
						
						f.setGalaxyName(rs.getString("nomegalassia"));
						f.setAtomo(rs.getString("atomo"));
						f.setAperture(rs.getString("aperture"));
						f.setSatellite(rs.getString("satellite"));
						if (rs.getString("valoreflusso") != null) f.setValoreFlusso(rs.getDouble("valoreflusso"));
						else f.setValoreFlusso(null);
						f.setLimitFlusso(rs.getString("limitflusso"));
						if (rs.getString("erroreflusso") != null) f.setErroreFlusso(rs.getDouble("erroreflusso"));
						else f.setErroreFlusso(null);
						
						st.close();
						st = cDB.prepareStatement(deleteFlussoRigheAper);
						st.setString(1, nextLine[0].trim());
						st.setString(2, listaAtomi.get(i));
						st.setString(3, nextLine[22].trim());
						st.executeUpdate();
						
						f.setSatellite("Hersel/PACS");
						if (!(nextLine[2+(3*i)].trim().equals(""))) 
							f.setValoreFlusso(Double.parseDouble(nextLine[2+(3*i)].trim()));
						if (!(nextLine[1+(3*i)].trim().equals(""))) 
							f.setLimitFlusso(nextLine[1+(3*i)].trim());
						if (!(nextLine[3+(3*i)].trim().equals(""))) 
							f.setErroreFlusso(Double.parseDouble(nextLine[3+(3*i)].trim()));
						
						st = cDB.prepareStatement(insertFlussoRigheAper);
						
						st.setString(1, f.getGalaxyName());
						st.setString(2, f.getAtomo());
						st.setString(3, f.getAperture());
						st.setString(4, f.getSatellite());
						if (f.getValoreFlusso() != null) st.setDouble(5, f.getValoreFlusso());
						else st.setNull(5,java.sql.Types.DOUBLE);
						st.setString(6, f.getLimitFlusso());
						if (f.getErroreFlusso() != null) st.setDouble(7, f.getErroreFlusso());
						else st.setNull(7,java.sql.Types.DOUBLE);
						
						st.executeUpdate();
				
					}
					
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 2;
		}
		
		rs.close();
		st.close();
		cDB.close();
		reader.close();
		return 0;
		
	}
	
	public static int uploadCSV3(String percorsoCSV) throws ClassNotFoundException, SQLException, IOException{
		
		Connection cDB = ManagerDB.getConnect();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		CSVReader reader = null;
		
		String searchFlussoContinuoAper = "select * from \"flussocontinuoaper\" where nomegalassia = ? "
				+ "and atomo = ? and aperture = ?";
		String deleteFlussoContinuoAper = "delete from \"flussocontinuoaper\" where nomegalassia = ? "
				+ "and atomo = ? and aperture = ?";
		String insertFlussoContinuoAper = "insert into \"flussocontinuoaper\"(nomegalassia, atomo, aperture, "
				+ "satellite, valoreflusso, limitflusso, erroreflusso) values (?,?,?,?,?,?,?)";
		
		try {
			reader = new CSVReader(new FileReader(percorsoCSV), ';');
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return 1;
		}
		

		List<String> listaAtomi = Arrays.asList("OIII52","NIII57");
		List<String> listaAtomiNew = Arrays.asList("OI63","OIII88","NII122","OI145","CII158");
		
		String [] nextLine;
		
		try {
			while ((nextLine = reader.readNext()) != null){
				
				for(int i = 0; i <= 1; i++){
					
					
					st = cDB.prepareStatement(searchFlussoContinuoAper);
					st.setString(1, nextLine[0].trim());
					st.setString(2, listaAtomi.get(i));
					st.setString(3, nextLine[21].trim());
					rs = st.executeQuery();
					
					if (!(rs.next())){
						
						//inserisci da zero
						
						FlussoContinuoAper f = new FlussoContinuoAper();
						
						if (nextLine[0].trim().equals("")) f.setGalaxyName(null);
						else f.setGalaxyName(nextLine[0].trim());
						f.setAtomo(listaAtomi.get(i));
						if (nextLine[21].trim().equals("")) f.setAperture(null);
						else f.setAperture(nextLine[21].trim());
						f.setSatellite("Hersel/PACS");
						if (nextLine[1+(2*i)].trim().equals("")) f.setValoreFlusso(null);
						else f.setValoreFlusso(Double.parseDouble(nextLine[1+(2*i)].trim()));
						if (nextLine[2+(2*i)].trim().equals("")) f.setErroreFlusso(null);
						else f.setErroreFlusso(Double.parseDouble(nextLine[2+(2*i)].trim()));
						f.setLimitFlusso(null);
						
						st = cDB.prepareStatement(insertFlussoContinuoAper);
						
						st.setString(1, f.getGalaxyName());
						st.setString(2, f.getAtomo());
						st.setString(3, f.getAperture());
						st.setString(4, f.getSatellite());
						if (f.getValoreFlusso() != null) st.setDouble(5, f.getValoreFlusso());
						else st.setNull(5,java.sql.Types.DOUBLE);
						st.setString(6, f.getLimitFlusso());
						if (f.getErroreFlusso() != null) st.setDouble(7, f.getErroreFlusso());
						else st.setNull(7,java.sql.Types.DOUBLE);
						
						st.executeUpdate();
					}
					
					else{
						
						FlussoContinuoAper f = new FlussoContinuoAper();
						
						f.setGalaxyName(rs.getString("nomegalassia"));
						f.setAtomo(rs.getString("atomo"));
						f.setAperture(rs.getString("aperture"));
						f.setSatellite(rs.getString("satellite"));
						if (rs.getString("valoreflusso") != null) f.setValoreFlusso(rs.getDouble("valoreflusso"));
						else f.setValoreFlusso(null);
						f.setLimitFlusso(rs.getString("limitflusso"));
						if (rs.getString("erroreflusso") != null) f.setErroreFlusso(rs.getDouble("erroreflusso"));
						else f.setErroreFlusso(null);
						
						st.close();
						st = cDB.prepareStatement(deleteFlussoContinuoAper);
						st.setString(1, nextLine[0].trim());
						st.setString(2, listaAtomi.get(i));
						st.setString(3, nextLine[21].trim());
						st.executeUpdate();
						
						f.setSatellite("Hersel/PACS");
						f.setLimitFlusso(null);
						if (!(nextLine[1+(2*i)].trim().equals(""))) 
							f.setValoreFlusso(Double.parseDouble(nextLine[1+(2*i)].trim()));
						if (!(nextLine[2+(2*i)].trim().equals(""))) 
							f.setErroreFlusso(Double.parseDouble(nextLine[2+(2*i)].trim()));
						

						st = cDB.prepareStatement(insertFlussoContinuoAper);
						
						st.setString(1, f.getGalaxyName());
						st.setString(2, f.getAtomo());
						st.setString(3, f.getAperture());
						st.setString(4, f.getSatellite());
						if (f.getValoreFlusso() != null) st.setDouble(5, f.getValoreFlusso());
						else st.setNull(5,java.sql.Types.DOUBLE);
						st.setString(6, f.getLimitFlusso());
						if (f.getErroreFlusso() != null) st.setDouble(7, f.getErroreFlusso());
						else st.setNull(7,java.sql.Types.DOUBLE);
						
						st.executeUpdate();
						
						}
				}
				
				for(int j = 0;j <= 4;j++){
					
					
					st = cDB.prepareStatement(searchFlussoContinuoAper);
					st.setString(1, nextLine[0].trim());
					st.setString(2, listaAtomiNew.get(j));
					st.setString(3, nextLine[21].trim());
					rs = st.executeQuery();
					
					if (!(rs.next()))	{
						
						FlussoContinuoAper f = new FlussoContinuoAper();
						
						if (nextLine[0].trim().equals("")) f.setGalaxyName(null);
						else f.setGalaxyName(nextLine[0].trim());
						f.setAtomo(listaAtomiNew.get(j));
						if (nextLine[21].trim().equals("")) f.setAperture(null);
						else  f.setAperture(nextLine[21].trim());
						if (nextLine[6+(3*j)].trim().equals("")) f.setValoreFlusso(null);
						else f.setValoreFlusso(Double.parseDouble(nextLine[6+(3*j)].trim()));
						if (nextLine[5+(3*j)].trim().equals("")) f.setLimitFlusso(null);
						else f.setLimitFlusso(nextLine[5+(3*j)].trim());
						if (nextLine[7+(3*j)].trim().equals("")) f.setErroreFlusso(null);
						else f.setErroreFlusso(Double.parseDouble(nextLine[7+(3*j)].trim()));
						f.setSatellite("Hersel/PACS");
						
						st = cDB.prepareStatement(insertFlussoContinuoAper);
						
						st.setString(1, f.getGalaxyName());
						st.setString(2, f.getAtomo());
						st.setString(3, f.getAperture());
						st.setString(4, f.getSatellite());
						if (f.getValoreFlusso() != null) st.setDouble(5, f.getValoreFlusso());
						else st.setNull(5,java.sql.Types.DOUBLE);
						st.setString(6, f.getLimitFlusso());
						if (f.getErroreFlusso() != null) st.setDouble(7, f.getErroreFlusso());
						else st.setNull(7,java.sql.Types.DOUBLE);
						
						st.executeUpdate();
						
					}
					
					else {
						
						FlussoContinuoAper f = new FlussoContinuoAper();
						
						f.setGalaxyName(rs.getString("nomegalassia"));
						f.setAtomo(rs.getString("atomo"));
						f.setAperture(rs.getString("aperture"));
						f.setSatellite(rs.getString("satellite"));
						if (rs.getString("valoreflusso") != null) f.setValoreFlusso(rs.getDouble("valoreflusso"));
						else f.setValoreFlusso(null);
						f.setLimitFlusso(rs.getString("limitflusso"));
						if (rs.getString("erroreflusso") != null) f.setErroreFlusso(rs.getDouble("erroreflusso"));
						else f.setErroreFlusso(null);
						
						st.close();
						st = cDB.prepareStatement(deleteFlussoContinuoAper);
						st.setString(1, nextLine[0].trim());
						st.setString(2, listaAtomiNew.get(j));
						st.setString(3, nextLine[21].trim());
						st.executeUpdate();
						
						f.setSatellite("Hersel/PACS");
						if (!(nextLine[6+(3*j)].trim().equals(""))) 
							f.setValoreFlusso(Double.parseDouble(nextLine[6+(3*j)].trim()));
						if (!(nextLine[5+(3*j)].trim().equals(""))) 
							f.setLimitFlusso(nextLine[5+(3*j)].trim());
						if (!(nextLine[7+(3*j)].trim().equals(""))) 
							f.setErroreFlusso(Double.parseDouble(nextLine[7+(3*j)].trim()));
						
						st = cDB.prepareStatement(insertFlussoContinuoAper);
						
						st.setString(1, f.getGalaxyName());
						st.setString(2, f.getAtomo());
						st.setString(3, f.getAperture());
						st.setString(4, f.getSatellite());
						if (f.getValoreFlusso() != null) st.setDouble(5, f.getValoreFlusso());
						else st.setNull(5,java.sql.Types.DOUBLE);
						st.setString(6, f.getLimitFlusso());
						if (f.getErroreFlusso() != null) st.setDouble(7, f.getErroreFlusso());
						else st.setNull(7,java.sql.Types.DOUBLE);
						
						st.executeUpdate();
				
					}
					
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 2;
		}
		
		rs.close();
		st.close();
		cDB.close();
		reader.close();
		return 0;

		
	}

	public static int uploadCSV4(String percorsoCSV) throws ClassNotFoundException, SQLException, IOException{
		
		Connection cDB = ManagerDB.getConnect();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		CSVReader reader = null;
		
		String searchFlussoRigheIrsMode = "select * from \"flussorigheirsmode\" where nomegalassia = ? "
				+ "and atomo = ?";
		String deleteFlussoRigheIrsMode = "delete from \"flussorigheirsmode\" where nomegalassia = ? "
				+ "and atomo = ?";
		String insertFlussoRigheIrsMode = "insert into \"flussorigheirsmode\"(nomegalassia, atomo, irsmode, "
				+ "satellite, valoreflusso, limitflusso, erroreflusso) values (?,?,?,?,?,?,?)";
		
		try {
			reader = new CSVReader(new FileReader(percorsoCSV), ';');
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return 1;
		}
		
		List<String> listaAtomi = Arrays.asList("SIV10.5","NeII12.8","NeV14.3","NeIII15.6","SIII18.7",
				"NeV24.3","OIV25.9","SIII33.5","SiII34.8");
		
		String [] nextLine;
		
		try {
			while ((nextLine = reader.readNext()) != null){
				
				for(int i = 0; i <= 8; i++){
					
					st = cDB.prepareStatement(searchFlussoRigheIrsMode);
					st.setString(1, nextLine[0].trim());
					st.setString(2, listaAtomi.get(i));
					rs = st.executeQuery();
					
					if (!(rs.next()))	{
						
						FlussoRigheIrsMode f = new FlussoRigheIrsMode();
						
						if (nextLine[0].trim().equals("")) f.setGalaxyName(null);
						else f.setGalaxyName(nextLine[0].trim());
						f.setAtomo(listaAtomi.get(i));
						if (nextLine[28].trim().equals("")) f.setIrsMode(null);
						else  f.setIrsMode(nextLine[28].trim());
						if (nextLine[2+(3*i)].trim().equals("")) f.setValoreFlusso(null);
						else f.setValoreFlusso(Double.parseDouble(nextLine[2+(3*i)].trim()));
						if (nextLine[1+(3*i)].trim().equals("")) f.setLimitFlusso(null);
						else f.setLimitFlusso(nextLine[1+(3*i)].trim());
						if (nextLine[3+(3*i)].trim().equals("")) f.setErroreFlusso(null);
						else f.setErroreFlusso(Double.parseDouble(nextLine[3+(3*i)].trim()));
						f.setSatellite("Spitzer");
						
						st = cDB.prepareStatement(insertFlussoRigheIrsMode);
						
						st.setString(1, f.getGalaxyName());
						st.setString(2, f.getAtomo());
						st.setString(3, f.getIrsMode());
						st.setString(4, f.getSatellite());
						if (f.getValoreFlusso() != null) st.setDouble(5, f.getValoreFlusso());
						else st.setNull(5,java.sql.Types.DOUBLE);
						st.setString(6, f.getLimitFlusso());
						if (f.getErroreFlusso() != null) st.setDouble(7, f.getErroreFlusso());
						else st.setNull(7,java.sql.Types.DOUBLE);
						
						st.executeUpdate();
						
					}
					
					else {
						
						FlussoRigheIrsMode f = new FlussoRigheIrsMode();
						
						f.setGalaxyName(rs.getString("nomegalassia"));
						f.setAtomo(rs.getString("atomo"));
						f.setIrsMode(rs.getString("irsmode"));
						f.setSatellite(rs.getString("satellite"));
						if (rs.getString("valoreflusso") != null) f.setValoreFlusso(rs.getDouble("valoreflusso"));
						else f.setValoreFlusso(null);
						f.setLimitFlusso(rs.getString("limitflusso"));
						if (rs.getString("erroreflusso") != null) f.setErroreFlusso(rs.getDouble("erroreflusso"));
						else f.setErroreFlusso(null);
						
						st.close();
						st = cDB.prepareStatement(deleteFlussoRigheIrsMode);
						st.setString(1, nextLine[0].trim());
						st.setString(2, listaAtomi.get(i));
						st.executeUpdate();
						
						if (!(nextLine[28].trim().equals("")))
							f.setIrsMode(nextLine[28].trim());
						f.setSatellite("Spitzer");
						if (!(nextLine[2+(3*i)].trim().equals(""))) 
							f.setValoreFlusso(Double.parseDouble(nextLine[2+(3*i)].trim()));
						if (!(nextLine[1+(3*i)].trim().equals(""))) 
							f.setLimitFlusso(nextLine[1+(3*i)].trim());
						if (!(nextLine[3+(3*i)].trim().equals(""))) 
							f.setErroreFlusso(Double.parseDouble(nextLine[3+(3*i)].trim()));
						
						st = cDB.prepareStatement(insertFlussoRigheIrsMode);
						
						st.setString(1, f.getGalaxyName());
						st.setString(2, f.getAtomo());
						st.setString(3, f.getIrsMode());
						st.setString(4, f.getSatellite());
						if (f.getValoreFlusso() != null) st.setDouble(5, f.getValoreFlusso());
						else st.setNull(5,java.sql.Types.DOUBLE);
						st.setString(6, f.getLimitFlusso());
						if (f.getErroreFlusso() != null) st.setDouble(7, f.getErroreFlusso());
						else st.setNull(7,java.sql.Types.DOUBLE);
						
						st.executeUpdate();
				
					}
					
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 2;
		}
		
		rs.close();
		st.close();
		cDB.close();
		reader.close();
		return 0;
		
		}
	
}
