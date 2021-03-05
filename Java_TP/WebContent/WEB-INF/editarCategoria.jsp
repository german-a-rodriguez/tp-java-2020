<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"
		 import="entities.Usuario" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Editar categoria</title>
	
	<!-- CSS -->
	<link href="css/estilos-general.css" rel="stylesheet" type="text/css">
	<link href="css/formulario.css" rel="stylesheet" type="text/css">
	<!-- FUENTE -->
	<link rel="preconnect" href="https://fonts.gstatic.com">
	<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
	
	<%
	Usuario usuario = (Usuario)session.getAttribute("usuario");
	
	int    textId     = Integer.parseInt(request.getParameter("hiddenId"));
	String textNombre = request.getParameter("hiddenNombre");//La primera vez el id y nombre viene de la pagina anterior por formulario(como parametro)
	
	String error  	  = (String)request.getAttribute("error");
	if(error!=null) {
		textId	   = Integer.parseInt((String)request.getAttribute("hiddenId"));
		textNombre = (String)request.getAttribute("textNombre");//La segunda por servlet(como atributo)
		
		request.removeAttribute("error");
		request.removeAttribute("hiddenId");
		request.removeAttribute("textNombre");
	}
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
			<h2>Editar categoria</h2>
		</div>
		
		<%if(error!=null) {%>
			<div class="main-error">
				<div class="main-error-texto">
					<%if(error.equals("categoriaYaExiste")){
						%> <p>La categoria ingresada ya existe.</p> <%
						
					} else if(error.equals("errorEnBD")) {
						%> <p>Hubo un error al conectar con la base de datos. Contactese con el programador.</p> <%
					
					}%>
				</div>
			</div>
		<%}%>
			
		<div class="main-contenido-principal">
			
			<div class="formulario">
			
				<form name="formModificacionCategoria" method="post" action="abmcCategoriaServlet" autocomplete="off">
					<input type="hidden" name="hiddenOperacion" value="modificacion">
					<input type="hidden" name="hiddenId" value="<%=textId%>">
					
					<label>Nombre de categoria	</label><input type="text" name="textNombre" value="<%=textNombre%>" size="50" maxlength="100" required autofocus>
					<div class="formulario-submit">
						<input class="boton" type="submit" size="25" value="Editar">
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