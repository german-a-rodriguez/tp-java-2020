<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" 
		 import="entities.Usuario, entities.Producto, entities.Categoria, entities.ProductoEnCompra, java.util.LinkedList, data.DbConnector"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="description" content="Venta de productos informáticos en la ciudad de Rosario">
	<title>GR Informática - Productos</title>
	
	<!-- CSS -->
	<link href="css/estilos-general.css" rel="stylesheet" type="text/css">
	<link href="css/index.css" rel="stylesheet" type="text/css">
	<!-- FUENTE -->
	<link rel="preconnect" href="https://fonts.gstatic.com">
	<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
	<!-- JAVASCRIPT - Al final del scriptlet -->
	
	<%
	Usuario usuario = (Usuario)session.getAttribute("usuario");
	LinkedList<Producto> productos = null;
	LinkedList<Categoria> categorias = null;
	LinkedList<ProductoEnCompra> carritoProductos = null;
	String hayValuacionesPendientes = null;
	String filtroCategoria = null;
	String primerIngreso = null;
	String error = null;
	
	if(usuario!=null && usuario.getRol().equals("admin")) {
		
		request.getRequestDispatcher("WEB-INF/panelAdmin.jsp").forward(request,response);
		
	} else {
		
		//----------------------------------------------------
		//Traigo del servlet: listados de productos, error, cantidadDeValuacionesPendientes y listado de categorias.
		
		productos = (LinkedList<Producto>)request.getAttribute("productos");
		error = (String)request.getAttribute("error");
		
		if(usuario!=null) { //Busco valuaciones pendientes solo si inició sesión
			hayValuacionesPendientes = (String)request.getAttribute("hayValuacionesPendientes");
		}
		
		categorias = (LinkedList<Categoria>)request.getAttribute("categorias");
		
		//----------------------------------------------------
		//Traigo de sesion: carrito, filtro listado productos.
		
		carritoProductos = (LinkedList<ProductoEnCompra>)session.getAttribute("carritoProductos");
		
		filtroCategoria = (String)session.getAttribute("filtroCategoria");
		
 		//----------------------------------------------------
		//Cargo js.
		
 		if(error==null) {/*Si hay error no se carga para que se vea el mensaje de error*/
 	 		%> <script src="js/posicionarEnRecarga.js"></script> <%
 		}
 		
	}
	%>
</head>
<body>
	
	<header class="header">
		<h1>GR Informática</h1>
		
		<div class="header-right">
			<%if(usuario!=null) {%>
				<p>Bienvenid@ <%=usuario.getNombre()%>!</p>
			<%}%>
		</div>
	</header>
	
	<nav class="nav color-pagina">
		<ul class="nav-menu-left">
			<li><a class="nav-a" href="consultaProductosServlet">Explorar productos</a></li>
			<%if(usuario!=null) {%>
				<li><a class="nav-a" href="redireccionServlet?destino=carrito.jsp">Carrito</a></li>
				<li>
					<div <%if(hayValuacionesPendientes!=null && hayValuacionesPendientes.equals("true")) {%>id="div-btn-mis-compras"<%}%>>
						<a class="nav-a" href="consultaComprasServlet">Mis compras</a>
					</div>
				</li>
			<%}%>
			<li><a class="nav-a" href="redireccionServlet?destino=contacto.jsp">Contacto</a></li>
		</ul>
		
		<ul class="nav-menu-right">
			<%if(usuario==null) {%>
				<li><a class="nav-a" href="redireccionServlet?destino=iniciarSesion.jsp">Iniciar Sesión</a></li>
				<li><a class="nav-a" href="redireccionServlet?destino=registrarUsuario.jsp">Registrarse</a></li>
			<%} else {%>
				<li><a class="nav-a" href="cerrarSesionServlet">Cerrar Sesión</a></li>
			<%}%>
		</ul>
	</nav>
	
	<div class="main">
		
		<div class="main-titulo-pagina">
			<h2>Listado de productos</h2>
		</div>
		
		<%if(error!=null) {%>
			<div class="main-error">
				<div class="main-error-texto">
					<%if(error.equals("formularioIncompleto")) {
						%> <p class="error-texto-color">Formulario incompleto.</p> <%
						
					} else if(error.equals("inicieSesionParaComprar")) {
						%> <p class="error-texto-color">Inicie sesión para añadir productos al carrito!</p> <%
						
					} else if(error.equals("errorEnBD")) {
						%> <p class="error-texto-color-rojo">Hubo un error al conectar con la base de datos. Intentelo más tarde.</p> <%
					}
					request.removeAttribute("error");%>
				</div>
			</div>
		<%}%>
		
		<div class="main-contenido-principal">
			
			<%if(categorias!=null) {%>
				<div class="main-div-filtro-categoria">
					<form name="formFiltrarPorCategoria" method="post" action="consultaProductosServlet">
						
						<div class="filtro-titulo">
							<label>Categorias</label>
						</div>
						
						<div class="filtro-opciones">
							<ul>
								<li><input type="radio" name="filtroCategoria" value="sinFiltro" <%if(filtroCategoria.equals("sinFiltro")) {%>checked<%}%>><label>Sin filtro</label></li>
								<%for(Categoria unaCategoria: categorias) {%>
									<li><input type="radio" name="filtroCategoria" value="<%=unaCategoria.getId()%>" <%if(String.valueOf(unaCategoria.getId()).equals(filtroCategoria)) {%>checked<%}%>><label><%=unaCategoria.getNombre()%></label></li>
								<%}%>
							</ul>
						</div>
						
						<div class="filtro-boton">
							<input class="boton" type="submit" value="Filtrar">
						</div>
						
					</form>
				</div>
			<%}%>
			
			<%if(productos!=null) {/*1*/%>
				<div class="main-div-listado-productos">
					
					<%for(Producto p: productos) {/*2*/%>
						<div class="main-div-div-un-producto">
							
							<div class="main-div-div-div-producto-img">
								<img src="<%if(p.getLinkFoto().equals("sinImagen.jpg")) {%>
											<%="images/app/sinImagen.jpg"%>
										  <%} else {%>
										  	<%="images/productos/"+p.getLinkFoto()%>
										  <%}%>"
									 width="100" height="100" alt="producto informático">
							</div>
							
							<div class="main-div-div-div-producto-info">
								<p><%=p.getDescripcion()%></p>
								<p><%=p.getCategoria().getNombre()%></p>
								<p>$ <%=p.getPrecio()%> ARS</p>
								
								<%if(p.getStock() == 0) {%>
									<p>Por el momento no hay stock</p>
								<%} else if(p.getStock() <= 3) {%>
									<p>Poco stock: <%=p.getStock()%> unidades</p>
								<%} else if(p.getStock() > 3) {%>
									<p>Hay stock: <%=p.getStock()%> unidades</p>
								<%}%>
							</div>
							
							<div class="main-div-div-div-producto-agregar-eliminar">
								<%if(p.getStock()!=0) {/*3*/%>
									<%if(usuario==null) {/*4*/%>
										
										<a class="boton" href="carritoServlet?hiddenOperacion=agregar&hiddenId=<%=p.getId()%>">Agregar al carrito</a>
										
									<%} else {/*4*/
										
										boolean productoEstaEnElCarrito = false;
										for(ProductoEnCompra pec : carritoProductos) {
											if( p.getId() == pec.getProducto().getId() ) {
												productoEstaEnElCarrito = true;
											}
										}
										
										if(productoEstaEnElCarrito == false) {%>
											
											<a class="boton" href="carritoServlet?hiddenOperacion=agregar&hiddenId=<%=p.getId()%>">Agregar al carrito</a>
											
										<%} else {%>
											
											<a class="boton boton-eliminar-color" href="carritoServlet?hiddenOperacion=eliminar&hiddenId=<%=p.getId()%>">Eliminar del carrito</a>
											
										<%}%>
										
									<%}/*4*/%>
								<%} else {/*3*/%>
									<br>
								<%}/*3*/%>
							</div>
							
						</div>
					<%}/*2*/%>
					
				</div>
			<%}/*1*/%>
			
		</div>
		
		<!-- carrito boton derecho -->
		<%if(usuario!=null && usuario.getRol().equals("cliente")) {%>
			<div id="carritoImg-div">
				<p id="carritoImg-p"><%=carritoProductos.size()%></p>
				<a href="redireccionServlet?destino=carrito.jsp">
					<img id="carritoImg-img" src="images/app/carrito.png" width="60" height="" alt="">
				</a>
			</div>
		<%}%>
		
	</div>
	
	<div class="footer-espacio">
		<br>
	</div>
	
	<footer class="footer color-pagina">
		<p>GR Informática</p>
	</footer>

</body>
</html>