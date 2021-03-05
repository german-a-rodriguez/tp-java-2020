<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" 
		 import="entities.Usuario, entities.Compra, entities.ProductoEnCompra, java.util.LinkedList, java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Compras realizadas</title>
	
	<!-- CSS -->
	<link href="css/estilos-general.css" rel="stylesheet" type="text/css">
	<link href="css/comprasCliente.css" rel="stylesheet" type="text/css">
	<!-- FUENTE -->
	<link rel="preconnect" href="https://fonts.gstatic.com">
	<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
	
	<%
	Usuario usuario = (Usuario)session.getAttribute("usuario");
	LinkedList<ProductoEnCompra> carritoProductos = (LinkedList<ProductoEnCompra>)session.getAttribute("carritoProductos");
	
	int cantidadValuacionesPendientes = 0;
	String cvp = (String)request.getAttribute("cantidadValuacionesPendientes");
	if(cvp != null) {cantidadValuacionesPendientes = Integer.parseInt(cvp);}
	
	LinkedList<Compra> comprasUnUsuario = (LinkedList<Compra>)request.getAttribute("comprasUnUsuario");
	String error = (String)request.getAttribute("error");
	%>
</head>
<body>
	
	<header class="header">
		<h1>GR Informática</h1>
		
		<div class="header-right">
			<%if(usuario!=null) {%>
				<p>Usuario: <%=usuario.getNombre()%> <%=usuario.getApellido()%></p>
			<%}%>
		</div>
	</header>
	
	<nav class="nav color-pagina">
		<ul class="nav-menu-left">
			<li><a class="nav-a" href="consultaProductosServlet">Explorar productos</a></li>
			<%if(usuario!=null) {%>
				<li><a class="nav-a" href="redireccionServlet?destino=carrito.jsp">Carrito</a></li>
				<li><a class="nav-a" href="consultaComprasServlet">Mis compras</a></li>
			<%}%>
			<li><a class="nav-a" href="redireccionServlet?destino=contacto.jsp">Contacto</a></li>
		</ul>
		
		<ul class="nav-menu-right">
			<li><a class="nav-a" href="cerrarSesionServlet">Cerrar Sesión</a></li>
		</ul>
	</nav>
	
	<div class="main">
		
		<div class="main-titulo-pagina">
			<h2>Compras realizadas</h2>
		</div>
		
		<%if(error!=null) {%>
			<div class="main-error">
				<div class="main-error-texto">
					<%if(error.equals("errorEnBD")){
						%> <p>Hubo un error al conectar con la base de datos. Intentelo más tarde.</p> <%
					}
					request.removeAttribute("error");%>
				</div>
			</div>
		<%}%>
		
		<div class="main-contenido-principal">
			
			<p class="msg-valuaciones">
				<%if(cantidadValuacionesPendientes == 1) {%>
					Usted tiene <%=cantidadValuacionesPendientes%> valuación pendiente.
				<%} else if(cantidadValuacionesPendientes > 1){%>
					Usted tiene <%=cantidadValuacionesPendientes%> valuaciones pendientes.
				<%}%>
			</p>
			
			<%if(error == null) {
				if(comprasUnUsuario.size() != 0) {
					SimpleDateFormat sdfFecha = new SimpleDateFormat("dd/MM/yyyy");
					SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");
					for(Compra unaCompra : comprasUnUsuario) {;%>
						
						<div class="container-una-compra">
							
							<div class="container-tabla">
								
								<div class="table-1">
									<div class="tr-1">
										<div class="td-1"><p>Fecha:</p></div>
										<div class="td-1"><p>Hora:</p></div>
										<div class="td-1"><p>Tarjeta usada:</p></div>
										<div class="td-1"><p>DNI titular tarjeta:</p></div>
										<div class="td-1"><p>Importe total compra:</p></div>
									</div>
									<div class="tr-1">
										<div class="td-1"><p><%=sdfFecha.format(unaCompra.getFecha())%></p></div>
										<div class="td-1"><p><%=sdfHora.format(unaCompra.getHora())%></p></div>
										<div class="td-1"><p><%=unaCompra.getTarjeta().getNumero()%></p></div>
										<div class="td-1"><p><%=unaCompra.getTarjeta().getDniTitular()%></p></div>
										<div class="td-1"><p>$ <%=String.format("%.2f", unaCompra.getImporte())%> ARS</p></div>
									</div>
								</div>
								
								<div class="table-2">
									<div class="tr-2">
										<div class="td-2"><p>Descripcion</p></div>
										<div class="td-2"><p>Precio unitario</p></div>
										<div class="td-2"><p>Unidades</p></div>
									</div>
									<%for(ProductoEnCompra pec : unaCompra.getCarritoComprado()) {%>
										<div class="tr-2">
											<div class="td-2"><p><%=pec.getProducto().getDescripcion()%></p></div>
											<div class="td-2"><p>$ <%=pec.getPrecio()%> ARS</p></div>
											<div class="td-2"><p><%=pec.getCantidad()%></p></div>
										</div>
									<%}%>
								</div>
								
							</div>
							
							<div class="container-valuacion">
								<%if(unaCompra.getValuacion().getEstado().equals("Pendiente")) {%>
											
									<form method="post" action="redireccionServlet?destino=realizarValuacion.jsp">
										<input type="hidden" name="hiddenIdCompra" value="<%=unaCompra.getId()%>"></input>
										<input class="boton" type="submit" value="Valuar compra"></input>
									</form>
									
								<%} else {
									%> <img src="images/app/checkVerde.png" width="30px" height="25px" alt="check verde"> <p>Valuada</p> <%
								}%>
							</div>
							
						</div>
						
					<%}
					
				} else {%>
					
					<div class="main-error">
						<div class="main-error-texto">
							<p>Usted no tiene compras realizadas hasta el momento. Compre algo y vuelva!</p>
						</div>
					</div>
					
				<%}
			}%>
		
		</div>
	
	</div>
	
	<div class="footer-espacio">
		<br>
	</div>
	
	<footer class="footer color-pagina">
		<p>GR Informática</p>
	</footer>
	
</body>
</html>