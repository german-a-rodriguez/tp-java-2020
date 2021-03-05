<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" 
		 import="entities.Usuario, entities.ProductoEnCompra, java.util.LinkedList"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Realizar compra</title>
	
	<!-- CSS -->
	<link href="css/estilos-general.css" rel="stylesheet" type="text/css">
	<link href="css/formulario-realizarCompra.css" rel="stylesheet" type="text/css">
	<!-- FUENTE -->
	<link rel="preconnect" href="https://fonts.gstatic.com">
	<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
	
	<%
	Usuario usuario = (Usuario)session.getAttribute("usuario");
	LinkedList<ProductoEnCompra> carritoProductos = (LinkedList<ProductoEnCompra>)session.getAttribute("carritoProductos");
	
	String importeTotal = request.getParameter("hiddenImporteTotal");
	String error = (String)request.getAttribute("error");
	
	String 	textProvincia 		= "",
			textLocalidad 		= "",
			textDireccion 		= "",
			textPiso 			= "",
			textDepartamento 	= "",
			textCodigoAreaTelef = "",
			textNroTelef 		= "",
			textDniTitularTarj 	= "",
			textNyATitularTarj 	= "",
			textNumeroTarj 		= "",
			textEntidadEmisora 	= "",
			textMesVtoTarj 		= "",
			textAnioVtoTarj 	= "",
			textCodigoSegTarj 	= "";
	int		anioActual 			= 0;	//Inicializo
	
	if(error!=null) {
		//get
		textProvincia  		= (String)request.getAttribute("textProvincia");
		textLocalidad  		= (String)request.getAttribute("textLocalidad");
		textDireccion 		= (String)request.getAttribute("textDireccion");
		textPiso 			= (String)request.getAttribute("textPiso");
		textDepartamento  	= (String)request.getAttribute("textDepartamento");
		textCodigoAreaTelef = (String)request.getAttribute("textCodigoAreaTelef");
		textNroTelef 		= (String)request.getAttribute("textNroTelef");
		
		textDniTitularTarj 	= (String)request.getAttribute("textDniTitularTarj");
		textNyATitularTarj  = (String)request.getAttribute("textNyATitularTarj");
		textNumeroTarj 		= (String)request.getAttribute("textNumeroTarj");
		textEntidadEmisora 	= (String)request.getAttribute("textEntidadEmisora");
		textMesVtoTarj  	= (String)request.getAttribute("textMesVtoTarj");
		textAnioVtoTarj 	= (String)request.getAttribute("textAnioVtoTarj");
		textCodigoSegTarj 	= (String)request.getAttribute("textCodigoSegTarj");
		
		//remove
		request.removeAttribute("textProvincia");
		request.removeAttribute("textLocalidad");
		request.removeAttribute("textDireccion");
		request.removeAttribute("textPiso");
		request.removeAttribute("textDepartamento");
		request.removeAttribute("textCodigoAreaTelef");
		request.removeAttribute("textNroTelef");
		
		request.removeAttribute("textDniTitularTarj");
		request.removeAttribute("textNyATitularTarj");
		request.removeAttribute("textNumeroTarj");
		request.removeAttribute("textEntidadEmisora");
		request.removeAttribute("textMesVtoTarj");
		request.removeAttribute("textAnioVtoTarj");
		request.removeAttribute("textCodigoSegTarj");
		
		//traigo anio actual
		if(error.equals("anioIncorrecto")) {
			anioActual = (int)request.getAttribute("anioActual");
		}
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
			<h2>Realizar compra</h2>
		</div>
	
		<%if(error!=null) {%>
			<div class="main-error">
				<div class="main-error-texto">
					<%if(error.equals("tarjetaRechazaCompra")){
						%> <p>La tarjeta ingresada rechazó la compra.</p> <%
						
					} else if(error.equals("NumberFormatExceptionMesAnio")) {
						%> <p>El mes y año ingresado debe ser un número entero.</p> <%
						
					} else if(error.equals("mesIncorrecto")){
						%> <p>El mes de vencimiento ingresado debe ser un número entre 1 y 12.</p> <%
						
					} else if(error.equals("mesDebeSerMayorAlActual")){
						%> <p>Si su tarjeta vence este año, el mes de vencimiento ingresado debe mayor al mes actual.</p> <%
						
					} else if(error.equals("anioIncorrecto")) {
						%> <p>El año de vencimiento ingresado debe ser mayor a <%=anioActual-1%> y menor a <%=anioActual+10%>.</p> <%
						
					} else if(error.equals("errorEnDB")){
						%> <p>Hubo un error al conectar con la base de datos. Intentelo más tarde.</p> <%
						
					}
					request.removeAttribute("error");%>
				</div>
			</div>
		<%}%>
		
		<div class="main-contenido-principal">
				
			<form name="formDatosCompra" method="post" action="registrarCompraServlet" autocomplete="off">
				<input type="hidden" name="hiddenImporteTotal" value="<%=importeTotal%>">
				
				<div class="form-container">
					
					<div class="data-container">
						<label>Datos de envío</label>
						<label>Provincia</label>				<input type="text" name="textProvincia"			value="<%=textProvincia%>" 		size="25" maxlength="30"  required>
						<label>Localidad</label>				<input type="text" name="textLocalidad"			value="<%=textLocalidad%>" 		size="25" maxlength="30"  required>
						<label>Dirección</label>		 		<input type="text" name="textDireccion"			value="<%=textDireccion%>" 		size="25" maxlength="30"  required>
						<label>Piso (opcional)</label>		 	<input type="text" name="textPiso" 				value="<%=textPiso%>" 			size="25" maxlength="2">
						<label>Departamento (opcional)</label>	<input type="text" name="textDepartamento"    	value="<%=textDepartamento%>" 	size="25" maxlength="2">
						<label>Tel.-Código de área</label>		<input type="text" name="textCodigoAreaTelef" 	value="<%=textCodigoAreaTelef%>"size="25" maxlength="10"  required>
						<label>Tel.-Número</label>				<input type="text" name="textNroTelef"			value="<%=textNroTelef%>" 		size="25" maxlength="10"  required>
					</div>
					
					<div class="data-container">
						<label>Datos de pago</label>
						<label>DNI (Titular tarjeta)</label>				<input type="text" name="textDniTitularTarj" 	value="<%=textDniTitularTarj%>" size="25" maxlength="8"  required>
						<label>Nombre y apellido (Titular tajeta)</label>	<input type="text" name="textNyATitularTarj" 	value="<%=textNyATitularTarj%>" size="25" maxlength="50" required>
						<label>Número de tarjeta</label>	 				<input type="text" name="textNumeroTarj"     	value="<%=textNumeroTarj%>" 	size="25" minlength="16" maxlength="16" required>
						<label>Entidad emisora</label>	 					<input type="text" name="textEntidadEmisora" 	value="<%=textEntidadEmisora%>" size="25" maxlength="50" required>
						<label>Mes de vencimiento</label>					<input type="text" name="textMesVtoTarj"     	value="<%=textMesVtoTarj%>" 	size="25" maxlength="2"  required>
						<label>Año de vencimiento</label>					<input type="text" name="textAnioVtoTarj"    	value="<%=textAnioVtoTarj%>" 	size="25" maxlength="4"  required>
						<label>Código de seguridad</label> 					<input type="text" name="textCodigoSegTarj"  	value="<%=textCodigoSegTarj%>" 	size="25" maxlength="10" required>
						
						<div class="submit-container">
							<input class="boton" type="submit"  value="Comprar"></input>
						</div>
					</div>
					
				</div>
				
			</form>
			
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