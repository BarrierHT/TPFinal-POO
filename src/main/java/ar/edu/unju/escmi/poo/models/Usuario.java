package ar.edu.unju.escmi.poo.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ar.edu.unju.escmi.poo.dao.IFacturaDao;
import ar.edu.unju.escmi.poo.dao.imp.FacturaDaoImp;

@Entity
@Table(name = "usuarios")
@NamedQuery(name = "listarUsuarios", query = "SELECT c FROM Usuario c")
public class Usuario implements Serializable {
	@Id
	// @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long dni;

	private String nombre;
	private String apellido;
	private String direccion;
	private String email;
	private String password;
	private LocalDate fechaNacimiento;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rol_id")
	private Rol rol;

	public Usuario() {

	}

	public Usuario(Long dni, String nombre, String apellido, String direccion, String email, String password,
			LocalDate fechaNacimiento, Rol rol) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccion = direccion;
		this.email = email;
		this.password = password;
		this.fechaNacimiento = fechaNacimiento;
		this.rol = rol;
	}

	public Long getDni() {
		return dni;
	}

	public void setDni(Long dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public List<Factura> consultarCompras() {

		IFacturaDao facturaDao = new FacturaDaoImp();

		List<Factura> facturas = facturaDao.obtenerFacturas();

		System.out.println("Facturas size: " + facturas.size());
		List<Factura> shoppingList = new ArrayList<Factura>();

		for (Factura factura : facturas) {
			if (factura.getUsuario().getDni() == this.dni)
				shoppingList.add(factura);
		}

		return shoppingList;
	}

	@Override
	public String toString() {
		return "Usuario [dni=" + dni + ", nombre=" + nombre + ", apellido=" + apellido + ", direccion=" + direccion
				+ ", email=" + email + ", password=" + password + ", fechaNacimiento=" + fechaNacimiento + ", rol="
				+ rol.getTipo() + "]";
	}

}
