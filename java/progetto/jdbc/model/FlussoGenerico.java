package progetto.jdbc.model;

public class FlussoGenerico {
	
	String galaxyName;
	String atomo;
	Double valoreFlusso;
	String limitFlusso;
	Double erroreFlusso;
	
	public String getGalaxyName() {
		return galaxyName;
	}
	public String getAtomo() {
		return atomo;
	}
	public Double getValoreFlusso() {
		return valoreFlusso;
	}
	public String getLimitFlusso() {
		return limitFlusso;
	}
	public Double getErroreFlusso() {
		return erroreFlusso;
	}
	public void setGalaxyName(String galaxyName) {
		this.galaxyName = galaxyName;
	}
	public void setAtomo(String atomo) {
		this.atomo = atomo;
	}
	public void setValoreFlusso(Double valoreFlusso) {
		this.valoreFlusso = valoreFlusso;
	}
	public void setLimitFlusso(String limitFlusso) {
		this.limitFlusso = limitFlusso;
	}
	public void setErroreFlusso(Double erroreFlusso) {
		this.erroreFlusso = erroreFlusso;
	}
}
