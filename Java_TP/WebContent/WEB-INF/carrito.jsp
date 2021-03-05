<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" 
		 import="entities.Usuario, entities.Producto, entities.ProductoEnCompra, java.util.LinkedList"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Carrito de compras</title>
	
	<!-- CSS -->
	<link href="css/estilos-general.css" rel="stylesheet" type="text/css">
	<link href="css/carrito.css" rel="stylesheet" type="text/css">
	<!-- FUENTE -->
	<link rel="preconnect" href="https://fonts.gstatic.com">
	<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
	<!-- JAVASCRIPT - Al final del scriptlet -->
	
	<%
	Usuario usuario 							  = (Usuario)session.getAttribute("usuario");
	LinkedList<ProductoEnCompra> carritoProductos = (LinkedList<ProductoEnCompra>)session.getAttribute("carritoProductos");
	
	String error 		 = (String)request.getAttribute("error");
	Producto pErrorStock = (Producto)request.getAttribute("productoErrorStock");
	
	if(error==null) {/*Si hay error no se carga para que se vea el mensaje de error*/
 		%> <script src="js/posicionarEnRecarga.js"></script> <%
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
			<h2>Carrito de compras</h2>
		</div>
		
		<%if(error!=null) {%>
			<div class="main-error">
				<div class="main-error-texto">
					<%if(error.equals("stockMaximoAlcanzado")){
						%> <p>Stock máximo alcanzado.</p> <%
						
					} else if(error.equals("stockModificadoMientrasClienteRealizabaLaCompra")){
						%> <p>El stock del producto <%=pErrorStock.getDescripcion()%> se redujo mientras usted realizaba la compra. Ingrese nueva cantidad de unidades a comprar e intente nuevamente.</p> <%
					}
					request.removeAttribute("error");%>
				</div>
			</div>
		<%}%>
		
		<div class="main-contenido-principal">
		<%if(carritoProductos!=null) {%>
			<%if(carritoProductos.size() > 0) {%>
					
				<div class="table">
					
					<div class="thead">
						<div class="tr">
							<div class="th th-foto"><p>Foto</p></div>
							<div class="th th-desc"><p>Descripción</p></div>
							<div class="th th-cate"><p>Categoria</p></div>
							<div class="th th-stoc"><p>Stock</p></div>
							<div class="th th-cant"><p>Cantidad</p></div>
							<div class="th th-prec"><p>Precio</p></div>
							<div class="th th-subt"><p>Total</p></div>
							<div class="th th-elim"></div>
						</div>
					</div>
					
					<div class="tbody">
						<%
						double importeTotal = 0; 
						for(ProductoEnCompra pec: carritoProductos) {
							double importeUnitario =  pec.getProducto().getPrecio();
							double importeSubtotal =  pec.getProducto().getPrecio() * pec.getCantidad();
							importeTotal = importeTotal + importeSubtotal;
							%>
							
							<div class="tr">
								<div class="td td-foto"><img src="<%="images/productos/"+pec.getProducto().getLinkFoto()%>" alt="" width="100" height="100"></div>
								<div class="td td-desc"><p><%=pec.getProducto().getDescripcion()%></p></div>
								<div class="td td-cate"><p><%=pec.getProducto().getCategoria().getNombre()%></p></div>
								<div class="td td-stoc"><p><%=pec.getProducto().getStock()%> unidades</p></div>
								
								<div class="td td-cant">
									<div class="table-cantidad-tr">
											<div class="table-cantidad-td">
												<form name="formCantidadMenosUno" method="get" action="cantidadServlet">
													<input type="hidden" name="hiddenOperacion" value="cantidadMenosUno">
													<input type="hidden" name="hiddenId" 		value="<%=pec.getProducto().getId()%>">
													<input class="boton boton-menos" type="submit" value="-">
												</form>
											</div>
											<div class="table-cantidad-td centrar-numero">
												<p><%=pec.getCantidad()%></p>
											</div>
											<div class="table-cantidad-td">
 												<form name="formCantidadMasUno" method="get" action="cantidadServlet">
													<input type="hidden" name="hiddenOperacion" value="cantidadMasUno">
													<input type="hidden" name="hiddenId" 		value="<%=pec.getProducto().getId()%>">
													<input class="boton boton-mas" type="submit" value="+">
												</form>
											</div>
									</div>
								</div>
								
								<div class="td td-prec"><p>$ <%=String.format("%.2f", importeUnitario)%> ARS</p></div>
								<div class="td td-subt"><p>$ <%=String.format("%.2f", importeSubtotal)%> ARS</p></div>
								
								<div class="td td-elim">
									<form name="formEliminarProductoCarrito" method="get" action="carritoServlet">
										<input type="hidden" name="volverACarrito" 	value="true">
										<input type="hidden" name="hiddenOperacion" value="eliminar">
										<input type="hidden" name="hiddenId" 		value="<%=pec.getProducto().getId()%>">
										<input class="boton boton-eliminar-color" type="submit" size="25" value="Eliminar del carrito">
									</form>
								</div>
								
							</div>
						<%}%>	
					</div>
					
					<div class="tfoot">
						<p>Total de la compra: $ <%=String.format("%.2f", importeTotal)%> ARS<p>
	 				</div>
					
				</div>
				
				<div class="table-boton">
					<form name="formIniciarCompra" method="post" action="redireccionServlet?destino=realizarCompra.jsp">
						<input type="hidden" name="hiddenImporteTotal" value="<%=importeTotal%>">
						<input class="boton" type="submit" value="Iniciar compra">
					</form>
				</div>
				
			<%} else {%>
				
				<div class="main-error">
					<div class="main-error-texto">
						<p>No hay productos en el carrito. Agregue algún producto y vuelva!</p>
					</div>
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