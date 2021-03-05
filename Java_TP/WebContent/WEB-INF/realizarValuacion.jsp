<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" 
		 import="entities.Usuario, entities.ProductoEnCompra, java.util.LinkedList"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Realizar valuación</title>
	
	<!-- CSS -->
	<link href="css/estilos-general.css" rel="stylesheet" type="text/css">
	<link href="css/formulario-valuacion.css" rel="stylesheet" type="text/css">
	<!-- FUENTE -->
	<link rel="preconnect" href="https://fonts.gstatic.com">
	<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
	
	<%
	Usuario usuario = (Usuario)session.getAttribute("usuario");
	LinkedList<ProductoEnCompra> carritoProductos = (LinkedList<ProductoEnCompra>)session.getAttribute("carritoProductos");
	
	String hiddenIdCompra = request.getParameter("hiddenIdCompra");
	String valuacion      = (String)request.getAttribute("valuacion");
	String error 		  = (String)request.getAttribute("error");
	
	String textComentario = "";
	if(error!=null) {
		hiddenIdCompra = (String)request.getAttribute("hiddenIdCompra");
		textComentario = (String)request.getAttribute("textComentario");
		
		request.removeAttribute("hiddenIdCompra");
		request.removeAttribute("textComentario");
	}
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
			<h2 align="center">Valuación de compra</h2>
		</div>
		
		<%if(error!=null) {%>
			<div class="main-error">
				<div class="main-error-texto">
					<%if(error.equals("formularioIncompletoHideForm")) {
						%> <p>El formulario no fue enviado. Intentelo nuevamente.</p> <%
						
					} else if(error.equals("formularioIncompletoShowForm")) {
						%> <p>Por favor, complete el formulario.</p> <%
						
					} else if(error.equals("errorEnBD")) {
						%> <p>Hubo un error al conectar con la base de datos. Intentelo más tarde.</p> <%
					}
					request.removeAttribute("error");%>
				</div>
			</div>
		<%}%>
				
		<div class="main-contenido-principal">
			
			<%if(valuacion!=null && valuacion.equals("valuacionExitosa")) {%>
				
				<p>Valuación realizada con éxito. Muchas gracias!</p>
				
			<%} else {%>
				
				<%if(error==null || error!=null && error.equals("formularioIncompletoShowForm")) {
				//Cuando el error es de conexion a db o por que se ingreso por la url, no muestro el form%>	
					
					<div class="formulario">
						<form name="formValuar" method="post" action="valuarServlet" autocomplete="off">
							
							<input type="hidden" name="hiddenIdCompra" value="<%=hiddenIdCompra%>">
							
							<div class="puntaje">
								<label class="label-titulo">Puntaje del 1 al 5</label>
								<input type="radio" name="radioPuntaje" value="1"><label class="label-radio">1</label>
								<input type="radio" name="radioPuntaje" value="2"><label class="label-radio">2</label>
								<input type="radio" name="radioPuntaje" value="3"><label class="label-radio">3</label>
								<input type="radio" name="radioPuntaje" value="4"><label class="label-radio">4</label>
								<input type="radio" name="radioPuntaje" value="5"><label class="label-radio">5</label>
							</div>
							
							<div class="comentario">
								<label class="label-titulo">Comentario sobre la compra (opcional)</label>
								<textarea name="textComentario" rows="10" cols="40" maxlength="255" placeholder="Escribe aquí tus comentarios"><%=textComentario%></textarea>
							</div>
							
							<div class="formulario-submit">
								<input class="boton" type="submit" value="Valuar">
							</div>
							
						</form>
					</div>
					
				<%}%>
			
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