package entities;

public class Tarjeta {
	
	private String numero;
	private String entidadEmisora;
	private int mesVencimiento;
	private int anioVencimiento;
	private String dniTitular;
	private String nyaTitular;
	
	public Tarjeta() {}
	
	public Tarjeta(String numero) {
		this.numero = numero;
	}
	
	public Tarjeta(String numero, String entidadEmisora, int mesVencimiento, int anioVencimiento, String dniTitular, String nyaTitular) {
		this.numero = numero;
		this.entidadEmisora = entidadEmisora;
		this.mesVencimiento = mesVencimiento;
		this.anioVencimiento = anioVencimiento;
		this.dniTitular = dniTitular;
		this.nyaTitular = nyaTitular;
	}
	
	public String getNumero() {
		return numero;
	}
	
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public String getEntidadEmisora() {
		return entidadEmisora;
	}
	
	public void setEntidadEmisora(String entidadEmisora) {
		this.entidadEmisora = entidadEmisora;
	}
	
	public int getMesVencimiento() {
		return mesVencimiento;
	}
	
	public void setMesVencimiento(int mesVencimiento) {
		this.mesVencimiento = mesVencimiento;
	}
	
	public int getAnioVencimiento() {
		return anioVencimiento;
	}
	
	public void setAnioVencimiento(int anioVencimiento) {
		this.anioVencimiento = anioVencimiento;
	}
	
	public String getDniTitular() {
		return dniTitular;
	}
	
	public void setDniTitular(String dniTitular) {
		this.dniTitular = dniTitular;
	}
	
	public String getNyaTitular() {
		return nyaTitular;
	}
	
	public void setNyaTitular(String nyaTitular) {
		this.nyaTitular = nyaTitular;
	}
	
}
