<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" 
         import="entities.Producto, java.util.LinkedList"%>
<!DOCTYPE html>
<html>
<head>
	
	<!-- CSS -->
	<link href="css/tabla-seis-columnas.css" rel="stylesheet" type="text/css">
	<!-- JAVASCRIPT - Al final del scriptlet -->
	
	<%
	LinkedList<Producto> productos = (LinkedList<Producto>)request.getAttribute("productos");
	String error = (String)request.getAttribute("error");
	
	if(error==null) {/*Si hay error no se carga para que se vea el mensaje*/
 		%> <script src="js/posicionarEnRecarga.js"></script> <%
	}
	%>
</head>
<body>
	
	<div class="main">
	
		<div class="main-titulo-pagina">
			<h2>Listado de productos</h2>
		</div>
		
		<%if(error!=null) {%>
			<div class="main-error">
				<div class="main-error-texto">
					<%if(error.equals("existenProductosEnCompraConEsteProducto")) {
						%> <p>Se han registrado ventas de este producto. No es posible eliminarlo.</p> 
						<br> <%
					
					} else if (error.equals("errorEnBD")) {
						%> <p>Hubo un error al conectar con la base de datos. Contactese con el programador.</p> 
						<br> <%
					}
					request.removeAttribute("error");%>
				</div>
			</div>	
		<%}%>
		
		<div class="main-contenido-principal">
			
			<%if(productos!=null) {//Viene en null cuando la db no esta conectada%>
				
				<div class="boton-agregar">
					<a class="boton" href="agregarProductoServlet">Agregar producto</a>
					<a class="boton" href="ConsultaImagenesServlet">Administrar imágenes</a>
				</div>
				
				<div class="table">
					
					<div class="tr">
						<div class="td width-m"><p>Imagen</p></div>
						<div class="td width-s"><p>Id</p></div>
						<div class="td width-l"><p>Descripción</p></div>
						<div class="td width-m"><p>Categoria</p></div>
						<div class="td width-m"><p>Precio</p></div>
						<div class="td width-m"><p>Stock</p></div>
						<div class="td width-xl"><p>Acciones</p></div>
					</div>
					
					<%for(Producto p: productos) {%>
						<div class="tr">
							<div class="td width-m">
								<img src="<%if(p.getLinkFoto().equals("sinImagen.jpg")) {%>
											<%="images/app/sinImagen.jpg"%>
										  <%} else {%>
										  	<%="images/productos/"+p.getLinkFoto()%>
										  <%}%>"
									 width="100" height="100" alt="producto informático">
							</div>
							<div class="td width-s"><p><%=p.getId()%></p></div>
							<div class="td width-l"><p><%=p.getDescripcion()%></p></div>
							<div class="td width-m"><p><%=p.getCategoria().getNombre()%></p></div>
							<div class="td width-m"><p>$ <%=p.getPrecio()%> ARS</p></div>
							
							<%if(p.getStock() == 0) {%>
								<div class="td width-m"><p class="no-hay-stock-color">No hay stock<br>(<%=p.getStock()%> unidades)</p></div>
							<%} else if(p.getStock() <= 3) {%>
								<div class="td width-m"><p class="pocas-unidades-color">Pocas unidades<br>(<%=p.getStock()%> unidades)</p></div>
							<%} else if(p.getStock() > 3) {%>
								<div class="td width-m"><p class="hay-stock-color">Hay stock<br>(<%=p.getStock()%> unidades)</p></div>
							<%}%>
							
							<div class="td width-xl">
								
								<a class="boton" href="editarProductoServlet?hiddenId=<%=p.getId()%>">Editar</a>
								
								<%if(p.getBajaLogica()==false) {%>
									<!-- mostrar boton dar de alta -->
									<a class="boton dar-de-baja-color" href="abmcProductoServlet?hiddenOperacion=darDeAlta&hiddenId=<%=p.getId()%>">Dar de alta</a>
								<%} else {%>
									<!-- mostrar boton dar de baja -->
									<a class="boton dar-de-alta-color" href="abmcProductoServlet?hiddenOperacion=darDeBaja&hiddenId=<%=p.getId()%>">Dar de baja</a>
								<%}%>
								
								<a class="boton" href="abmcProductoServlet?hiddenOperacion=baja&hiddenId=<%=p.getId()%>">Eliminar</a>
								
							</div>
						</div>
					<%}%>
				
				</div>
				
			<%}%>
			
		</div>
	
	</div>
	
</body>
</html>