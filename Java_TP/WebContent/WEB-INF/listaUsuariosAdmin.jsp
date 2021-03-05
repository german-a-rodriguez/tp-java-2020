<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" 
         import="entities.Usuario, java.util.LinkedList"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Usuarios registrados</title>
	
	<!-- CSS -->
	<link href="css/estilos-general.css" rel="stylesheet" type="text/css">
	<link href="css/tabla-cuatro-columnas.css" rel="stylesheet" type="text/css">
	<!-- FUENTE -->
	<link rel="preconnect" href="https://fonts.gstatic.com">
	<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">

	<%
	Usuario usuario = (Usuario)session.getAttribute("usuario");
	
	LinkedList<Usuario> todosLosUsuarios = (LinkedList<Usuario>)request.getAttribute("todosLosUsuarios");
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
			<h2>Usuarios registrados</h2>
		</div>
		
		<%if(error!=null) {%>
			<div class="main-error">
				<div class="main-error-texto">
					<%if(error.equals("errorEnBD")) {
						%> <p>Hubo un error al conectar con la base de datos. Contactese con el programador.</p> <%
					}
					request.removeAttribute("error");%>
				</div>
			</div>
		<%}%>
			
		<div class="main-contenido-principal">
		
			<%if(error==null) {%>
				<div class="table">
					
					<div class="tr">
						<div class="td"><p>Nombre</p></div>
						<div class="td"><p>Apellido</p></div>
						<div class="td"><p>Email</p></div>
						<div class="td"><p>Rol</p></div>
					</div>
					
					<%for(Usuario unUsuario : todosLosUsuarios) {%>
						<div class="tr">
							<div class="td"><p><%=unUsuario.getNombre()%></p></div>
							<div class="td"><p><%=unUsuario.getApellido()%></p></div>
							<div class="td"><p><%=unUsuario.getEmail()%></p></div>
							<div class="td"><p><%=unUsuario.getRol()%></p></div>
						</div>
					<%}%>
					
				</div>
			<%}%>
		
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