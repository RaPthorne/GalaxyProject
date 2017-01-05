package progetto.jdbc.util;

import java.util.List;

public class operazioniStatistiche {
	
	public static Double calcoloMedia(List<Double> listaValori){
		
		Double somma = 0.0;
		int N = listaValori.size();
		
		if (N == 0) return null;
		
		for (int i =0;i < N; i++){
			
			somma = somma + listaValori.get(i);
		}
		
		return (somma/N);
		
	}
	
	public static List<Double> ordinaValori(List<Double> listaValori){
		
		int N = listaValori.size();
		Double d1, d2;
		for(int i=0; i<(N-1); i++){
			int min = i;
			for (int j=(i+1); j<N; j++){
				if (listaValori.get(min) >listaValori.get(j))min = j;
			}
			if (min != i){
				d1 = listaValori.get(i);
				d2 = listaValori.get(min);
				listaValori.set(min, d1);
				listaValori.set(i, d2);
			}
		}
		
		return listaValori;
		
	}
	
	public static Double calcoloMediana(List<Double> listaValori){
		
		List<Double> listaOrdinata = ordinaValori(listaValori);
		int N = listaOrdinata.size();
		
		if (N==0) return null;
		if (N==1) return listaOrdinata.get(0);
		if (((N % 2)==0)) return ((listaOrdinata.get(N/2)+listaOrdinata.get((N/2)-1))/2);
		else return listaOrdinata.get(((N-1)/2));
		
	}
	
	public static Double calcoloDeviazioneStandard(List<Double> listaValori){
		
		int N = listaValori.size();
		if (N==0) return null;
		
		Double media = calcoloMedia(listaValori);
		Double somma = 0.0;
		
		for (int i=0;i<N;i++){
			
			somma = somma + Math.pow((listaValori.get(i)-media), 2);
		}
		
		return Math.pow((somma/N), 0.5);
		
	}

	public static Double calcoloDeviazioneMediaAssoluta(List<Double> listaValori){
		
		if (listaValori.size()==0) return null;
		
		return (calcoloDeviazioneStandard(listaValori)*0.6745);
	}
}
