package entities;

import java.sql.Date;
import java.sql.Time;
import java.util.LinkedList;

public class Compra {
	
	private int id;
	private Usuario usuario;
	private Direccion direccion;
	private Tarjeta tarjeta;
	private Valuacion valuacion;
	private LinkedList<ProductoEnCompra> carritoComprado;
	private Date fecha;
	private Time hora;
	private double importe;
	
	public Compra() {}
	
	public Compra(int id, Valuacion valuacion) {
		this.id = id;
		this.valuacion = valuacion;
	}
	
	public Compra(Usuario usuario, Direccion direccion, Tarjeta tarjeta, Valuacion valuacion, double importe) {
		this.usuario = usuario;
		this.direccion = direccion;
		this.tarjeta = tarjeta;
		this.valuacion = valuacion;
		this.importe = importe;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Direccion getDireccion() {
		return direccion;
	}
	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}
	
	public Tarjeta getTarjeta() {
		return tarjeta;
	}
	public void setTarjeta(Tarjeta tarjeta) {
		this.tarjeta = tarjeta;
	}
	
	public Valuacion getValuacion() {
		return valuacion;
	}
	public void setValuacion(Valuacion valuacion) {
		this.valuacion = valuacion;
	}
	
	public LinkedList<ProductoEnCompra> getCarritoComprado() {
		return carritoComprado;
	}
	public void setCarritoComprado(LinkedList<ProductoEnCompra> carritoComprado) {
		this.carritoComprado = carritoComprado;
	}
	
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public Time getHora() {
		return hora;
	}
	public void setHora(Time hora) {
		this.hora = hora;
	}
	
	public double getImporte() {
		return importe;
	}
	public void setImporte(double importe) {
		this.importe = importe;
	}
	
}
