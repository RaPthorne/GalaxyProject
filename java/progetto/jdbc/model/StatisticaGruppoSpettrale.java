package progetto.jdbc.model;

public class StatisticaGruppoSpettrale {

	String gruppoSpettrale;
	Double valoreMedio;
	Double deviazioneStandard;
	Double mediana;
	Double deviazioneMediaAssoluta;
	
	String atomo1;
	String atomo2;
	
	public String getGruppoSpettrale() {
		return gruppoSpettrale;
	}
	public Double getValoreMedio() {
		return valoreMedio;
	}
	public Double getDeviazioneStandard() {
		return deviazioneStandard;
	}
	public Double getMediana() {
		return mediana;
	}
	public Double getDeviazioneMediaAssoluta() {
		return deviazioneMediaAssoluta;
	}
	public String getAtomo1() {
		return atomo1;
	}
	public String getAtomo2() {
		return atomo2;
	}
	public void setGruppoSpettrale(String gruppoSpettrale) {
		this.gruppoSpettrale = gruppoSpettrale;
	}
	public void setValoreMedio(Double valoreMedio) {
		this.valoreMedio = valoreMedio;
	}
	public void setDeviazioneStandard(Double deviazioneStandard) {
		this.deviazioneStandard = deviazioneStandard;
	}
	public void setMediana(Double mediana) {
		this.mediana = mediana;
	}
	public void setDeviazioneMediaAssoluta(Double deviazioneMediaAssoluta) {
		this.deviazioneMediaAssoluta = deviazioneMediaAssoluta;
	}
	public void setAtomo1(String atomo1) {
		this.atomo1 = atomo1;
	}
	public void setAtomo2(String atomo2) {
		this.atomo2 = atomo2;
	}		
}
