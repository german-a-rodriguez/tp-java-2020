package entities;

import java.sql.Date;
import java.sql.Time;

public class Valuacion {
	
	private Date fecha;
	private Time hora;
	private int puntaje;
	private String comentario;
	private String estado;
	
	public Valuacion() {}
	
	public Valuacion(String estado) {
		this.estado = estado;
	}
	
	public Valuacion(int puntaje, String comentario) {
		this.puntaje = puntaje;
		this.comentario = comentario;
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

	public int getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
