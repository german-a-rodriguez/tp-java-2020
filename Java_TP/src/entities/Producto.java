package entities;

public class Producto {
	
	private int id;
	private String descripcion;
	private String linkFoto;
	private Categoria categoria;
	private double precio;
	private int stock;
	private boolean bajaLogica;
	
	public Producto() {}
	
	public Producto(int id) {
		this.id = id;
	}
	
	public Producto(int id, boolean bajaLogica) {
		this.id = id;
		this.bajaLogica = bajaLogica;
	}
	
	public Producto(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public Producto(String descripcion, String linkFoto, Categoria categoria, double precio, int stock) {
		this.descripcion = descripcion;
		this.linkFoto = linkFoto;
		this.categoria = categoria;
		this.precio = precio;
		this.stock = stock;
	}
	
	public Producto(int id, String descripcion, String linkFoto, Categoria categoria, double precio, int stock) {
		this.id = id;
		this.descripcion = descripcion;
		this.linkFoto = linkFoto;
		this.categoria = categoria;
		this.precio = precio;
		this.stock = stock;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getLinkFoto() {
		return linkFoto;
	}

	public void setLinkFoto(String linkFoto) {
		this.linkFoto = linkFoto;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public boolean getBajaLogica() {
		return bajaLogica;
	}
	
	public void setBajaLogica(boolean bajaLogica) {
		this.bajaLogica = bajaLogica;
	}
	
}
