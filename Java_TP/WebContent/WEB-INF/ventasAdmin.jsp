<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" 
         import="entities.Usuario, entities.Compra, entities.ProductoEnCompra, java.util.LinkedList, java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Ventas realizadas</title>
	
	<!-- CSS -->
	<link href="css/estilos-general.css" rel="stylesheet" type="text/css">
	<link href="css/tabla-ventasAdmin.css" rel="stylesheet" type="text/css">
	<!-- FUENTE -->
	<link rel="preconnect" href="https://fonts.gstatic.com">
	<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
	
	<%
	Usuario usuario = (Usuario)session.getAttribute("usuario");
	
	LinkedList<Compra> todasLasVentas = (LinkedList<Compra>)request.getAttribute("todasLasVentas");
	String error = (String)request.getAttribute("error");
	%>
</head>
<body>
	
	<header class="header">
		<h1>GR Informática - Panel de Administrador</h1>
		
		<div class="header-right">
			<%if(usuario!=null) {%>
				<p>Usuario: <%=usuario.getNombre()%> <%=usuario.getApellido()%></p>
			<%}%>
		</div>
	</header>
	
	<nav class="nav color-pagina">
		<ul class="nav-menu-left">
			<li><a class="nav-a" href="abmcProductoServlet?hiddenOperacion=consulta">ABMC Productos</a></li>
			<li><a class="nav-a" href="abmcCategoriaServlet?hiddenOperacion=consulta">ABMC Categorias</a></li>
			<li><a class="nav-a" href="consultaVentasServlet">Ventas realizadas</a></li>
			<li><a class="nav-a" href="consultaUsuariosServlet">Usuarios</a></li>
		</ul>
		
		<ul class="nav-menu-right">
			<li><a class="nav-a" href="cerrarSesionServlet">Cerrar Sesión</a></li>
		</ul>
	</nav>
	
	<div class="main">
	
		<div class="main-titulo-pagina">
			<h2>Ventas realizadas</h2>
		</div>
		
		<%if(error!=null) {%>
			<div class="main-error">
				<div class="main-error-texto">
					<%if(error.equals("errorEnBD")){
						%> <p>Hubo un error al conectar con la base de datos. Contactese con el programador.</p> <%
						
					}
					request.removeAttribute("error");%>
				</div>
			</div>
		<%}%>
			
		<div class="main-contenido-principal">
			
			<%if(todasLasVentas!=null) {
				SimpleDateFormat sdfFecha = new SimpleDateFormat("dd/MM/yyyy");
				SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");
				for(Compra unaCompra : todasLasVentas) {%>
						
						<div class="table-general">
							<div class="tr-general">
							
								<div class="th-table-1"><p>Información de la compra</p></div>
								<div class="th-table-2"><p>Información del envío</p></div>
								<div class="th-table-3"><p>Información de la valuación</p></div>
								
							</div>
							<div class="tr-general">
									
									<div class="table-1">
										<div class="tr-1">
											<div class="td-1"><p>Cliente: </p></div><div class="td-1"><p><%=unaCompra.getUsuario().getNombre()%> <%=unaCompra.getUsuario().getApellido()%></p></div>
										</div>
										<div class="tr-1">
											<div class="td-1"><p>Email: </p></div><div class="td-1"><p><%=unaCompra.getUsuario().getEmail()%></p></div>
										</div>
										<div class="tr-1">
											<div class="td-1"><p>Titular: </p></div><div class="td-1"><p><%=unaCompra.getTarjeta().getNyaTitular()%></p></div>
										</div>
										<div class="tr-1">
											<div class="td-1"><p>DNI Titular: </p></div><div class="td-1"><p><%=unaCompra.getTarjeta().getDniTitular()%></p></div>
										</div>
										<div class="tr-1">
											<div class="td-1"><p>Tarjeta: </p></div><div class="td-1"><p><%=unaCompra.getTarjeta().getNumero()%></p></div>
										</div>
										<div class="tr-1">
											<div class="td-1"><p>Emisor: </p></div><div class="td-1"><p><%=unaCompra.getTarjeta().getEntidadEmisora()%></p></div>
										</div>
										<div class="tr-1">
											<div class="td-1"><p>Fecha: </p></div><div class="td-1"><p><%=sdfFecha.format(unaCompra.getFecha())%></p></div>
										</div>
										<div class="tr-1">
											<div class="td-1"><p>Hora: </p></div><div class="td-1"><p><%=sdfHora.format(unaCompra.getHora())%></p></div>
										</div>
										<div class="tr-1">
											<div class="td-1"><p>Importe compra: </p></div><div class="td-1"><p>$ <%=String.format("%.2f", unaCompra.getImporte())%> ARS</p></div>
										</div>
										<div class="tr-1 table-lista-productos">
											
											<div class="tr-lista-productos">
												<div class="td-lista-productos"><p>Producto</p></div>
												<div class="td-lista-productos"><p>Precio</p></div>
												<div class="td-lista-productos"><p>Cantidad</p></div>
											</div>
											<%for(ProductoEnCompra pec : unaCompra.getCarritoComprado()) {%>
												<div class="tr-lista-productos">
													<div class="td-lista-productos"><p><%=pec.getProducto().getDescripcion()%></p></div>
													<div class="td-lista-productos"><p>$ <%=pec.getPrecio()%> ARS</p></div>
													<div class="td-lista-productos"><p><%=pec.getCantidad()%> unidades</p></div>
												</div>
											<%}%>
											
										</div>
									</div>
									
									<div class="table-2">
										<div class="tr-2"><div class="td-2"><p>Provincia: </p></div><div class="td-2"><p><%=unaCompra.getDireccion().getProvincia()%></p></div></div>
										<div class="tr-2"><div class="td-2"><p>Localidad: </p></div><div class="td-2"><p><%=unaCompra.getDireccion().getLocalidad()%></p></div></div>
										<div class="tr-2"><div class="td-2"><p>Direccion: </p></div><div class="td-2"><p><%=unaCompra.getDireccion().getDireccion()%></p></div></div>
										<%if(!unaCompra.getDireccion().getPiso().equals("")) {%>
											<div class="tr-2"><div class="td-2"><p>Piso: </p></div><div class="td-2"><p><%=unaCompra.getDireccion().getPiso()%> - Dto: <%=unaCompra.getDireccion().getDepartamento()%></p></div></div>
										<%}%>
										<div class="tr-2"><div class="td-2"><p>Teléfono: </p></div><div class="td-2"><p><%=unaCompra.getDireccion().getCodigoAreaTelef()%> <%=unaCompra.getDireccion().getNroTelef()%></p></div></div>
									</div>
									
									<%if(unaCompra.getValuacion().getEstado().equals("Pendiente")) {%>
										<div class="table-3">
											<p class="valuacion-pendiente-color">Valuación pendiente</p>
										</div>
									<%} else { %>
										
										<div class="table-3">
											<div class="tr-3"><div class="td-3"><p>Fecha:</p></div>		<div class="td-3"><p><%=sdfFecha.format(unaCompra.getValuacion().getFecha())%></p></div></div>
											<div class="tr-3"><div class="td-3"><p>Hora:</p></div>		<div class="td-3"><p><%=sdfHora.format(unaCompra.getValuacion().getHora())%></p></div></div>
											<div class="tr-3"><div class="td-3"><p>Puntaje:</p></div>	<div class="td-3"><p><%=unaCompra.getValuacion().getPuntaje()%></p></div></div>
											<div class="tr-3"><div class="td-3"><p>Comentario:</p></div><div class="td-3"><p><%=unaCompra.getValuacion().getComentario()%></p></div></div>
										</div>
									<%}%>
									
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