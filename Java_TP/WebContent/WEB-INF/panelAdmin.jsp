<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" 
         import="entities.Usuario"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Panel Administrador</title>
	
	<!-- CSS -->
	<link href="css/estilos-general.css" rel="stylesheet" type="text/css">
	<!-- FUENTE -->
	<link rel="preconnect" href="https://fonts.gstatic.com">
	<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
	
	<%
	Usuario usuario = (Usuario)session.getAttribute("usuario");
	
	//Para <jsp:include>
	String recarga = (String)request.getAttribute("recarga");
	if(recarga==null) {recarga = request.getParameter("recarga");}
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
	
	<%if(recarga!=null) {
		if(recarga.equals("recargaProductos")) {
			
			%> <jsp:include page="panelAdminProductos.jsp"></jsp:include> <% //Como panelAdmin.jsp esta en WEB-INF aqui no tengo que agregarlo en la ruta
			
		} else if(recarga.equals("recargaCategorias")) {
			
			%> <jsp:include page="panelAdminCategorias.jsp"></jsp:include> <%
			
		}
	}
	%>
	
	<div class="footer-espacio">
		<br>
	</div>
	
	<footer class="footer color-pagina">
		<p>GR Informática</p>
	</footer>
	
</body>
</html>