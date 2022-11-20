package ar.edu.unju.escmi.poo;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;

import ar.edu.unju.escmi.poo.config.EmfSingleton;
import ar.edu.unju.escmi.poo.dao.IDetalleDao;
import ar.edu.unju.escmi.poo.dao.IFacturaDao;
import ar.edu.unju.escmi.poo.dao.IProductoDao;
import ar.edu.unju.escmi.poo.dao.IStockDao;
import ar.edu.unju.escmi.poo.dao.IUsuarioDao;
import ar.edu.unju.escmi.poo.dao.imp.DetalleDaoImp;
import ar.edu.unju.escmi.poo.dao.imp.FacturaDaoImp;
import ar.edu.unju.escmi.poo.dao.imp.ProductoDaoImp;
import ar.edu.unju.escmi.poo.dao.imp.StockDaoImp;
import ar.edu.unju.escmi.poo.dao.imp.UsuarioDaoImp;
import ar.edu.unju.escmi.poo.models.Detalle;
import ar.edu.unju.escmi.poo.models.Factura;
import ar.edu.unju.escmi.poo.models.Producto;
import ar.edu.unju.escmi.poo.models.Rol;
import ar.edu.unju.escmi.poo.models.Stock;
import ar.edu.unju.escmi.poo.models.Usuario;

public class Principal {

	private static EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		int option = -1;
		boolean credencial = false;

		String email = "", password = "";

		IUsuarioDao usuarioDao = new UsuarioDaoImp();
		IDetalleDao detalleDao = new DetalleDaoImp();
		IFacturaDao facturaDao = new FacturaDaoImp();
		IProductoDao productoDao = new ProductoDaoImp();
		IStockDao stockDao = new StockDaoImp();

		List<Usuario> usuariosList = usuarioDao.obtenerUsuarios(); // Get all users
		Usuario usuarioRecovered = null; // Get user to initiate the session

		// Muestra los detalles del nroFactura que ingreses (mirar imp)
		// System.out.println(detalleDao.obtenerDetalles((long) 1).toString());

		// Factura de test (crear usuario y detalles)

		// Factura bill = new Factura(LocalDate.now(), 20,
		// usuarioDao.obtenerUsuario((long) 123),
		// detalleDao.obtenerDetalles((long) 1));

		// facturaDao.guardarFactura(bill);

		// Test de que los detalles se cargaron bien
		// System.out.println(facturaDao.obtenerFactura((long) 2).toString());

		// Rol rol = new Rol("newrol");
		// manager.getTransaction().begin();
		// manager.persist(rol);
		// manager.getTransaction().commit();

		// Usuario usuario = new Usuario((long) 45327608, "martin", "cruz", "c",
		// "mcruz", "test",
		// LocalDate.now(), new Rol(6));
		// Al ingresar el id del rol, tiene que ser una id que exista ya en la bd

		// MANERA 1
		// usuarioDao.guardarUsuario(usuario);

		// MANERA2

		// manager.getTransaction().begin();
		// manager.persist(usuario);
		// manager.getTransaction().commit();

		do {

			if (credencial == false) { // User is not logged in
				do {
					try {
						System.out.println("\nDigite el email del usuario: ");
						email = scanner.next();
						System.out.println("Digite la contraseña del usuario: ");
						password = scanner.next();
					} catch (Exception e) {
						scanner.next();
						System.out.println("\nINGRESE Los datos");
					}

					for (Usuario usuario : usuariosList) {
						if (usuario.getEmail().equals(email) && usuario.getPassword().equals(password)) {
							usuarioRecovered = usuario;
							break;
						}
					}
					if (usuarioRecovered == null) {
						System.out.println("\n\n Credenciales incorrectas \n\n");
					} else {
						credencial = true;
						break;
					}

				} while (1 == 1);
			}

			if (credencial) { // User is logged in Correctly

				option = -1;
				long billNumber = 6;

				System.out.println("Logged in: " + usuarioRecovered.getRol().getTipo());

				if (usuarioRecovered.getRol().getTipo().equals("Vendedor")) { // Menu de vendedor
					System.out.println("\nMenu Principal");
					System.out.println("1- Alta de cliente");
					System.out.println("2- Venta (se genera venta)");
					System.out.println("3- Listado de clientes");
					System.out.println("4- Listado de facturas");
					System.out.println("5- Buscar Factura por numero de factura");
					System.out.println("6- Salir");

					System.out.println("Ingrese su opcion: ");

					try {
						option = scanner.nextInt();
					} catch (Exception e) {
						scanner.next();
						System.out.println("\nINGRESE UN NUMERO");
					}

					if (option == 1) {

						int rolId = 1;
						int dia = 0, mes = 0, anio = 0;
						String nombreDeAlta = "", apellidoDeAlta = "", domicilioDeAlta = "";
						String emailAlta = "", passwordAlta = "";
						Long dniDeAlta = (long) 0;
						boolean band = true;
						LocalDate fechaNacimientoDeAlta = LocalDate.now();

						try {
							System.out.println("Ingrese el Nombre del usuario: ");
							nombreDeAlta = scanner.next();
							try {
								System.out.println("Ingrese el Apellido del usuario: ");
								apellidoDeAlta = scanner.next();
								try {
									System.out.println("Ingrese el DNI del usuario: ");
									dniDeAlta = scanner.nextLong();
									try {
										System.out.println("Ingrese el Domicilio del usuario: ");
										domicilioDeAlta = scanner.next();
										try {
											System.out.println("Ingrese la Fecha de Nacimiento del usuario : ");
											// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
											String dateString = scanner.next();
											fechaNacimientoDeAlta = LocalDate.parse(dateString);

										} catch (Exception e) {
											scanner.nextLine();
											System.out.println("\nIngrese una Fecha de Nacimiento posible");
											band = false;
										}
									} catch (Exception e) {
										scanner.nextLine();
										System.out.println("\nIngrese un Domicilio posible");
										band = false;
									}
								} catch (Exception e) {
									scanner.nextLine();
									System.out.println("\nIngrese un DNI posible");
									band = false;
								}
							} catch (Exception e) {
								scanner.nextLine();
								System.out.println("\nIngrese un Apellido posible");
								band = false;
							}
						} catch (Exception e) {
							scanner.nextLine();
							System.out.println("\nIngrese un Nombre posible");
							band = false;
						}

						try {
							System.out.println("\nDigite el email del cliente a ingresar: ");
							emailAlta = scanner.next();
							System.out.println("Digite la contraseña del cliente a ingresar: ");
							passwordAlta = scanner.next();
						} catch (Exception e) {
							scanner.next();
							band = false;
							System.out.println("\nINGRESE Los datos");
						}

						if (band) {
							try {
								Usuario newUser = new Usuario(dniDeAlta, nombreDeAlta, apellidoDeAlta, domicilioDeAlta,
										emailAlta, passwordAlta, fechaNacimientoDeAlta, new Rol(rolId));
								usuarioDao.guardarUsuario(newUser);
							} catch (Exception e) {
								System.out.println("\n\nDni o Email ya registrado en la base de datos \n\n");
								continue;
							}

							System.out.println("\nCarga de cliente CORRECTA\n\n");

						} else
							System.out.println("\nCarga de cliente INCORRECTA\n\n");

						System.out.println("\nOpcion de Alta de Cliente Finalizada");
					} else if (option == 2) {

						System.out.println("\nIngrese DNI del cliente: ");
						long idCard = -1;

						try {
							idCard = scanner.nextLong();
						} catch (Exception e) {
							scanner.next();
							System.out.println("\nINGRESE UN NUMERO");
						}

						Usuario client = usuarioDao.obtenerUsuario(idCard);

						if (client != null) {

							System.out.println("\nCliente: " + client.getApellido() + ", " + client.getNombre());

							String op;
							Factura bill = new Factura();
							boolean generateBill = false;
							Producto p = null;

							do {

								System.out.println("\nIngrese codigo del producto: ");

								try {
									int code = scanner.nextInt();
									p = productoDao.obtenerProducto(code);
								} catch (Exception e) {
									scanner.next();
									System.out.println("\nINGRESE UN NUMERO");
								}

								if (p != null) {

									System.out.println(
											"\nProducto: " + p.getDescripcion() + "\nPrecio: $"
													+ p.getPrecioUnitario());

									if (stockDao.obtenerStock(p) != null) {

										int amount = 0;

										System.out.println(
												"Stock Disponible: " + stockDao.obtenerStock(p).getCantidad());
										System.out.println("\nIngrese cantidad: ");

										try {
											amount = scanner.nextInt();
										} catch (Exception e) {
											scanner.next();
											System.out.println("\nINGRESE UN NUMERO");
										}

										if (amount != 0 && stockDao.obtenerStock(p).getCantidad() >= amount) {

											// ToDo - No carga factura_id (si se crea la factura antes y se lo manda
											// como parametro sigue dando error por el constructor)
											// Detalle details = new Detalle(amount, 0, p, null);
											// details.setImporte(details.calcularImporte());

											// detalleDao.guardarDetalle(details);

											// bill.getDetalles().add(details);
											generateBill = true;

											Stock stock = stockDao.obtenerStock(p);
											stock.setCantidad(stock.getCantidad() - amount);
											stockDao.modificarStock(stock);

										} else {

											System.out.println("\nNo hay stock disponible");

										}

									} else {

										System.out.println("\nNo hay stock");

									}

								} else {

									System.out.println("\nNo se encontro el producto");

								}

								System.out.println("\nAgregar mas productos? si/no: ");
								op = scanner.next();

							} while (op.equalsIgnoreCase("si") || op.equalsIgnoreCase("s"));

							if (generateBill) {

								// ToDo - Mismo error que en detalles, falla el constructor al cargar cliente o
								// detalles
								// billNumber++;
								// bill.setNroFactura(billNumber);
								// bill.setFechaGeneracion(LocalDate.now());
								// bill.setTotal(bill.calcularTotal());

								// facturaDao.guardarFactura(bill);
								// System.out.println(bill);
							} else {
								System.out.println("\nOcurrio un error al generar la factura");
							}

						} else {
							System.out.println("\nNo se encontro el cliente");
						}

						System.out.println("\nOpcion de Venta Finalizada");

					} else if (option == 3) {

						System.out.println("\nLISTADO DE CLIENTES: ");
						usuarioDao.obtenerUsuarios().stream().filter(u -> u.getRol().getTipo().equals("Cliente"))
								.forEach(user -> {
									System.out.println("\n" + user.toString());
								});

						// System.out.println(usuariosList.size());

						// List<Usuario> filteredUser = usuariosList.stream()
						// .filter(usuario -> usuario.getRol().getTipo().equals("Cliente")).;
						// System.out.println(usuariosList.size());

						// for (Usuario usuario : filteredUser) {
						// System.out.println(
						// "Nombre: " + usuario.getNombre() + " Apellido : " + usuario.getApellido() +
						// "\n");
						// System.out.println("Domicilio: " + usuario.getDireccion() + " fechaNacimiento
						// : "
						// + usuario.getFechaNacimiento().toString() + "\n");
						// System.out.println("Dni: " + usuario.getDni() + " Email: " +
						// usuario.getEmail() + "\n\n");
						// }

						System.out.println("\nOpcion de Listado de Clientes Finalizada");

					} else if (option == 4) {

						System.out.println("\nLISTADO DE FACTURAS: ");
						System.out.println("\n" + facturaDao.obtenerFacturas().toString());

						System.out.println("\nOpcion de Listado de Facturas Finalizada");

					} else if (option == 5) {

						System.out.println("\nIngrese el numero de Factura que desea buscar: ");
						Long nroFactura = scanner.nextLong();

						System.out.println(facturaDao.obtenerFactura(nroFactura).toString());

						System.out.println("\nOpcion de Buscar Factura por numero Finalizada");

					} else if (option == 6)
						break;
					else
						System.out.println("\nOpcion incorrecta");

				} else if (usuarioRecovered.getRol().getTipo().equals("Cliente")) { // Menu de Cliente
					System.out.println("1- Buscar Factura por numero de factura");
					System.out.println("2- Listar todas sus facturas");
					System.out.println("3- Salir");

					System.out.println("Ingrese su opcion: ");

					try {
						option = scanner.nextInt();
					} catch (Exception e) {
						scanner.next();
						System.out.println("\nINGRESE UN NUMERO");
					}

					if (option == 1) {

						System.out.println("\nIngrese el numero de Factura que desea buscar: ");
						Long nroFactura = scanner.nextLong();

						System.out.println(facturaDao.obtenerFactura(nroFactura).toString());

						System.out.println("\nOpcion de Buscar Factura por numero Finalizada");

					} else if (option == 2) {

						long checkUserID = usuarioRecovered.getDni();

						facturaDao.obtenerFacturas().stream().filter(b -> b.getUsuario().getDni() == checkUserID)
								.forEach(bill -> {

									System.out.println("\n" + bill.toString());

								});

						System.out.println("\nOpcion de Listado de todas las Facturas Finalizada");
					} else if (option == 3) {

						System.out.println("\nHa salido correctamente del programa");
						break;

					} else
						System.out.println("\nOpcion incorrecta");

				}

				System.out.println("\n\n\n\n");
			}

		} while (1 == 1);

		scanner.close();
	}
}