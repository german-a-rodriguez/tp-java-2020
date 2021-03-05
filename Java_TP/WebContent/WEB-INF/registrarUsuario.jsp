<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Registro de usuario</title>
	
	<!-- CSS -->
	<link href="css/estilos-general.css" rel="stylesheet" type="text/css">
	<link href="css/formulario.css" rel="stylesheet" type="text/css">
	<!-- FUENTE -->
	<link rel="preconnect" href="https://fonts.gstatic.com">
	<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
	<!-- JAVASCRIPT -->
	<script src="js/showHidePassword.js"></script>
	
	<% 
	String textNombre = "";
	String textApellido = "";
	String textEmail = "";
	String textPassword = "";
	String error = "";
	
	error = (String)request.getAttribute("error");
	
	if(error!=null) {
		if(error.equals("formularioIncompleto")) {
			textNombre	 = "";
			textApellido = "";
			textEmail 	 = "";
			textPassword = "";
		} else {
			textNombre	 = (String)request.getAttribute("textNombre");
			textApellido = (String)request.getAttribute("textApellido");
			textEmail 	 = (String)request.getAttribute("textEmail");
			textPassword = (String)request.getAttribute("textPassword");
			
			request.removeAttribute("error");
			request.removeAttribute("textNombre");
			request.removeAttribute("textApellido");
			request.removeAttribute("textEmail");
			request.removeAttribute("textPassword");
		}
	}
	%>
</head>
<body>
	
	<header class="header">
		<h1>GR Informática</h1>
	</header>
	
	<nav class="nav color-pagina">
		<ul class="nav-menu-left">
			<li><a class="nav-a" href="consultaProductosServlet">Explorar productos</a></li>
			<li><a class="nav-a" href="redireccionServlet?destino=contacto.jsp">Contacto</a></li>
		</ul>
		
		<ul class="nav-menu-right">
			<li><a class="nav-a" href="redireccionServlet?destino=iniciarSesion.jsp">Iniciar Sesión</a></li>
			<li><a class="nav-a" href="redireccionServlet?destino=registrarUsuario.jsp">Registrarse</a></li>
		</ul>
	</nav>
	
	<div class="main">
		
		<div class="main-titulo-pagina">
			<h2>Registro de usuario</h2>
		</div>
		
		<%if(error!=null) {%>
			<div class="main-error">
				<div class="main-error-texto">
					<%if(error.equals("formularioIncompleto")) {
						%> <p>Complete el formulario e intente nuevamente.</p> <%
						
					} else if(error.equals("emailYaRegistrado")) {
						%> <p>El email ingresado ya está registrado. Ingrese otro email.</p> <%
						
					} else if(error.equals("errorEnBD")) {
						%> <p>Hubo un error al conectar con la base de datos. Intentelo más tarde.</p> <%
						
					}%>
				</div>
			</div>
		<%}%>
		
		<div class="main-contenido-principal">
			
			<div class="formulario">
				
				<form name="formAltaUsuario" method="post" action="altaUsuarioServlet" autocomplete="off">
					<input type="hidden" 	name="hiddenRol" 	value="cliente">
					
					<label>Nombre</label>
					<input type="text" 		name="textNombre"   value="<%=textNombre%>" 	maxlength="50" 	required autofocus>
					
					<label>Apellido</label>
					<input type="text" 		name="textApellido" value="<%=textApellido%>"	maxlength="50" 	required >
					
					<label>Email</label>
					<input type="email"		name="textEmail"    value="<%=textEmail%>"		maxlength="50" 	required >
					
					<label>Contraseña</label>
					<input type="password" 	name="textPassword"	value="<%=textPassword%>" 	minlength="6" 	maxlength="10" required id="inputPassword">
					<div id="btn-show-hide-container">
						<img id="btn-show-hide" onclick="showHide()" src="images/app/mostrarPassword.png" width="30" height="" alt="">
					</div>
					
					<div class="formulario-submit">
						<input class="boton" type="submit" size="25" value="Registrar">
					</div>
					
				</form>
				
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