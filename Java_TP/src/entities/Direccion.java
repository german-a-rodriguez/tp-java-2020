package entities;

public class Direccion {
	
	private int id;
	private String provincia;
	private String localidad;
	private String direccion;
	private String piso;
	private String departamento;
	private String codigoAreaTelef;
	private String nroTelef;
	
	public Direccion() {}

	public Direccion(String provincia, String localidad, String direccion, String piso, String departamento, String codigoAreaTelef, String nroTelef) {
		this.provincia = provincia;
		this.localidad = localidad;
		this.direccion = direccion;
		this.piso = piso;
		this.departamento = departamento;
		this.codigoAreaTelef = codigoAreaTelef;
		this.nroTelef = nroTelef;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getCodigoAreaTelef() {
		return codigoAreaTelef;
	}

	public void setCodigoAreaTelef(String codigoAreaTelef) {
		this.codigoAreaTelef = codigoAreaTelef;
	}

	public String getNroTelef() {
		return nroTelef;
	}

	public void setNroTelef(String nroTelef) {
		this.nroTelef = nroTelef;
	}
	
}
