package progetto.jdbc.model;

public class Galaxy {
	
	private String galaxyName;
	private Double ned;
	private Double distance;
	private String spectralGroup;
	private String limitNev1;
	private Double luminosityNev1;
	private String limitNev2;
	private Double luminosityNev2;
	private String limitOiv;
	private Double luminosityOiv;
	private Double metallicity;
	private Double metallicityError;
	private String alternativeName;
	
	public String getGalaxyName() {
		return galaxyName;
	}
	public void setGalaxyName(String galaxyName) {
		this.galaxyName = galaxyName;
	}
	public Double getNed() {
		return ned;
	}
	public void setNed(Double ned) {
		this.ned = ned;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public String getSpectralGroup() {
		return spectralGroup;
	}
	public void setSpectralGroup(String spectralGroup) {
		this.spectralGroup = spectralGroup;
	}
	public String getLimitNev1() {
		return limitNev1;
	}
	public void setLimitNev1(String limitNev1) {
		this.limitNev1 = limitNev1;
	}
	public Double getLuminosityNev1() {
		return luminosityNev1;
	}
	public void setLuminosityNev1(Double luminosityNev1) {
		this.luminosityNev1 = luminosityNev1;
	}
	public String getLimitNev2() {
		return limitNev2;
	}
	public void setLimitNev2(String limitNev2) {
		this.limitNev2 = limitNev2;
	}
	public Double getLuminosityNev2() {
		return luminosityNev2;
	}
	public void setLuminosityNev2(Double luminosityNev2) {
		this.luminosityNev2 = luminosityNev2;
	}
	public String getLimitOiv() {
		return limitOiv;
	}
	public void setLimitOiv(String limitOiv) {
		this.limitOiv = limitOiv;
	}
	public Double getLuminosityOiv() {
		return luminosityOiv;
	}
	public void setLuminosityOiv(Double luminosityOiv) {
		this.luminosityOiv = luminosityOiv;
	}
	public Double getMetallicity() {
		return metallicity;
	}
	public void setMetallicity(Double metallicity) {
		this.metallicity = metallicity;
	}
	public Double getMetallicityError() {
		return metallicityError;
	}
	public void setMetallicityError(Double metallicityError) {
		this.metallicityError = metallicityError;
	}
	public String getAlternativeName() {
		return alternativeName;
	}
	public void setAlternativeName(String alternativeName) {
		this.alternativeName = alternativeName;
	}
}
