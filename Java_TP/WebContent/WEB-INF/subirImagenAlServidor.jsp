<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" 
		 import="entities.Usuario, entities.Producto, java.util.LinkedList" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Imagenes</title>
	
	<!-- CSS -->
	<link href="css/estilos-general.css" rel="stylesheet" type="text/css">
	<link href="css/subirImagenesAlServidor.css" rel="stylesheet" type="text/css">
	<!-- FUENTE -->
	<link rel="preconnect" href="https://fonts.gstatic.com">
	<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
	<!-- JAVASCRIPT -->
	<script src="js/setearInfoArchivos.js"></script>
	
	<%
	Usuario usuario = (Usuario)session.getAttribute("usuario");
	String[] listadoImagenes = (String[])request.getAttribute("listadoImagenes");
	String mensajeResultadoOperacion = (String)request.getAttribute("mensaje");
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
			<h2>Administración de imágenes</h2>
		</div>
		
		<%if(mensajeResultadoOperacion!=null) {%>
			<div class="main-error">
				<div class="main-error-texto">
					<p><%=mensajeResultadoOperacion%></p>
				</div>
			</div>
		<%request.removeAttribute("mensaje");}%>
		
		<div class="main-contenido-principal">
			<div class="div-form">
				
				<p class="form-title">Subir imágenes al servidor</p>
				
				<form name="formSubirImagen" method="post" enctype="multipart/form-data" action="SubirImagenAlServidorServlet?accion=subir">
					
					<div class="contenedor-seleccionar">
						<div class="contenedor-file-input">
							<p>Seleccionar archivo</p>
							<input id="fileInputSubir" type="file" name="foto" accept=".jpg,.png" multiple onchange="mostrarCantidadArchivosSubir()">
						</div>
						<p id="cantFilesSubir">No se eligió archivo</p>
					</div>
					
					<div class="contenedor-botones">
						<input class="boton" type="button" value="Deseleccionar archivos" onclick="deseleccionarArchivosSubir()">
						<input id="btnSubirImgs" class="boton" type="submit" value="Subir imágenes" disabled>
					</div>
					
				</form>
				
			</div>
		</div>
		
		<div class="imagenes-container">
			
			<form method="post" action="SubirImagenAlServidorServlet?accion=borrar">
				
				<div class="btn-borrar-img-serv-container">
					<div class="btn-borrar-img-serv">
						<p class="form-title">Eliminar imágenes del servidor</p>
						<input class="boton" type="submit" value="Eliminar imágenes seleccionadas">
					</div>
				</div>
				
				<%for(String li: listadoImagenes) {%>
					<div class="una-imagen">
						
						<div class="una-imagen-foto">
							<img src="<%="images/productos/"+li%>" width="100" height="100" alt="producto informático">
						</div>
						
						<div class="una-imagen-nombre">
							<p><input class="checkbox" type="checkbox" name="imagenes" value="<%=li%>"><%=li%></p>
						</div>
						
					</div>
				<%}%>
			</form>
					
		</div>
		
		<div class="div-volver">
			<a class="boton" href="AbmcProductoServlet?hiddenOperacion=consulta">Volver a productos</a>
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