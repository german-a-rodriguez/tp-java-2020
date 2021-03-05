<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" 
		 import="entities.Usuario, entities.Producto, entities.Categoria, java.util.LinkedList"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Alta de producto</title>
	
	<!-- CSS -->
	<link href="css/estilos-general.css" rel="stylesheet" type="text/css">
	<link href="css/formulario.css" rel="stylesheet" type="text/css">
	<link href="css/formulario-agregar-editar-producto.css" rel="stylesheet" type="text/css">
	<!-- FUENTE -->
	<link rel="preconnect" href="https://fonts.gstatic.com">
	<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
	<!-- JAVASCRIPT - Al final del scriptlet -->
	<script src="js/handleFiles.js"></script>
	
	<%
	Usuario usuario = (Usuario)session.getAttribute("usuario");
	
	String textDescripcion = "";
	int    selectIdCategoria = 0;
	String textPrecio = "";
	String textStock = "";
	String textLinkFoto = "sinImagen.jpg";
	
	//Si se producen errores.
	String error = (String)request.getAttribute("error");
	if(error!=null) {
		textDescripcion	  = (String)request.getAttribute("textDescripcion");
		selectIdCategoria = Integer.parseInt((String)request.getAttribute("selectIdCategoria"));
		textPrecio		  = (String)request.getAttribute("textPrecio");
		textStock		  = (String)request.getAttribute("textStock");
		textLinkFoto	  = (String)request.getAttribute("textLinkFoto");
		
		request.removeAttribute("error");
		request.removeAttribute("textDescripcion");
		request.removeAttribute("selectCategoria");
		request.removeAttribute("textPrecio");
		request.removeAttribute("textStock");
		request.removeAttribute("textLinkFoto");
	}
	
	//Esta condicion se cumple solo cuando se cambia la imagen.
	String linkFoto = (String)request.getAttribute("linkFoto");
	if(linkFoto != null) { //Si viene en null es xq es el primer ingreso a la pagina
		textDescripcion   = (String)request.getAttribute("descripcion");
		selectIdCategoria = Integer.parseInt((String)request.getAttribute("idCategoria"));
		textPrecio	      = (String)request.getAttribute("precio");
		textStock	      = (String)request.getAttribute("stock");
		textLinkFoto	  = linkFoto;
		
		request.removeAttribute("descripcion");
		request.removeAttribute("idCategoria");
		request.removeAttribute("precio");
		request.removeAttribute("stock");
		request.removeAttribute("linkFoto");
	}
	
	//Para cuando se cambia la imagen.
	if(error==null) {/*Si hay error no se carga para que se vea el mensaje de error*/
 		%> <script src="js/posicionarEnRecarga.js"></script> <%
	}
	
	//Siempre
	LinkedList<Categoria> categorias = (LinkedList<Categoria>)request.getAttribute("categorias");
	
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
			<h2>Alta de productos</h2>
		</div>
		
		<%if(error!=null) {%>
			<div class="main-error">
				<div class="main-error-texto">	
					<%if(error.equals("NumberFormatExceptionPrecio")) {
						%> <p>Precio debe ser un número decimal.</p> <%
						
					} else if(error.equals("NumberFormatExceptionStock")) {
						%> <p>Stock debe ser un número entero.</p> <%
						
					} else if(error.equals("stockNegativo")){
						%> <p>Stock debe ser un número positivo.</p> <%
						
					}else if(error.equals("categoriaNoSeleccionada")) {
						%> <p>Selecione una categoria e intente nuevamente.</p> <%
						
					} else if(error.equals("descripcionYaExiste")) {
						%> <p>Ya existe producto con la descripción ingresada.</p> <%
						
					} else if(error.equals("errorEnBD")){
						%> <p>Hubo un error al conectar con la base de datos. Contactese con el programador.</p> <%
					}
					%>
				</div>
			</div>
		<%}%>
		
		<div class="main-contenido-principal">
		
			<%if(categorias!=null) {//Viene en null cuando la db no esta conectada%>
			
				<div class="formulario">
					
					<form name="formAltaProducto" method="post" action="abmcProductoServlet" autocomplete="off">
						<input type="hidden" name="hiddenOperacion" value="alta">
						
						<label>Descripción</label>
						<input type="text" id="textDescripcion" name="textDescripcion" value="<%=textDescripcion%>" size="25" maxlength="100" required autofocus>
						
						<label>Categoria</label>
						<select id="selectIdCategoria" name="selectIdCategoria">
							<option value="0" hidden="true">Seleccione una opción</option>
    						<%for(Categoria c: categorias) {%>
    							<option value="<%=c.getId()%>" <%if(c.getId() == selectIdCategoria) {%> selected <%} %> ><%=c.getNombre()%></option>
    						<%}%>
  						</select>
						
						<label>Precio</label>
						<input type="text" id="textPrecio" name="textPrecio"   value="<%=textPrecio%>"  size="25" maxlength="20"  required>
						
						<label>Stock</label>
						<input type="text" id="textStock"  name="textStock"    value="<%=textStock%>"   size="25" maxlength="10"  required>
						
						<label>Foto</label>
						<div class="img-container">
							
							<div class="input-file-container">
								<p>Seleccionar archivo</p>
								<input type="file" id="inputFile" name="inputFileImg" accept=".jpg,.png" onchange="handleFilesAgregar(this.files)">
							</div>
							
							<img src="<%if(textLinkFoto.equals("sinImagen.jpg")) {%>
											<%="images/app/sinImagen.jpg"%>
										  <%} else {%>
										  	<%="images/productos/"+textLinkFoto%>
										  <%}%>"
								 width="100" height="100" alt="producto informático">
							
							<%if(!textLinkFoto.equals("sinImagen.jpg")) {%>
								<label><%=textLinkFoto%></label>
							<%}%>
							
						</div>
						<input type="hidden" name="hiddenLinkFoto" value="<%=textLinkFoto%>">
						
						<div class="formulario-submit">
							<input class="boton" type="submit" size="25" value="Agregar">
						</div>
						
					</form>
				
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