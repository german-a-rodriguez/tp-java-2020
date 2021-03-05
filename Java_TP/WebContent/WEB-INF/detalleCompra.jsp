<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" 
		 import="entities.Usuario, entities.ProductoEnCompra, java.util.LinkedList"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Detalle compra</title>
	
	<!-- CSS -->
	<link href="css/estilos-general.css" rel="stylesheet" type="text/css">
	<link href="css/detalleCompra.css" rel="stylesheet" type="text/css">
	<!-- FUENTE -->
	<link rel="preconnect" href="https://fonts.gstatic.com">
	<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
	
	<%
	Usuario usuario = (Usuario)session.getAttribute("usuario");
	LinkedList<ProductoEnCompra> carritoProductos = (LinkedList<ProductoEnCompra>)session.getAttribute("carritoProductos");
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
			<h2 align="center">Detalle compra</h2>
		</div>
		
		<div class="main-contenido-principal">
			
			<div class="mensaje-uno">
				<p>FELICITACIONES! La compra se ha realizado con éxito.</p>
			</div>
			<div class="mensaje-dos">
				<p>Los productos estarán llegando al domicilio indicado en la compra en las próximas 72 hs.</p>
			</div>
			
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