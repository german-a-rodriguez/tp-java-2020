<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" 
         import="entities.Categoria, java.util.LinkedList"%>
<!DOCTYPE html>
<html>
<head>
	
	<!-- CSS -->
	<link href="css/tabla-cuatro-columnas.css" rel="stylesheet" type="text/css">
	<!-- JAVASCRIPT - Al final del scriptlet -->
	
	<%
	LinkedList<Categoria> categorias = (LinkedList<Categoria>)request.getAttribute("categorias");
    String error = (String)request.getAttribute("error");
    
    if(error==null) {
 		%> <script src="js/posicionarEnRecarga.js"></script> <% /*Si hay error no se carga para que se vea el mensaje de error*/
	}
	%>
</head>
<body>
	
	<div class="main">
	
		<div class="main-titulo-pagina">
			<h2>Listado de categorias</h2>
		</div>
		
		<%if(error!=null) {%>
			<div class="main-error">
				<div class="main-error-texto">
					<%if(error.equals("existenProductosConEstaCategoria")) {
						%> <p>No se puede eliminar. Existen productos dentro de esta categoría.</p>
						<br> <%
						
					} else if(error.equals("errorEnBD")) {
						%> <p>Hubo un error al conectar con la base de datos. Contactese con el programador.</p> 
						<br> <%
					}
					request.removeAttribute("error");%>
				</div>
			</div>
		<%}%>
		
		<div class="main-contenido-principal">
			
			<%if(categorias!=null) {//Viene en null cuando la db no esta conectada%>
				
				<div class="boton-agregar">
					<form name="formAgregarCategoria" method="post" action="redireccionServlet?destino=agregarCategoria.jsp">     
						<input class="boton" type="submit" size="25" value="Agregar categoria">
					</form>
				</div>
			
				<div class="table">
					<div class="tr">
						<div class="td"><p>Id</p></div>
						<div class="td"><p>Nombre</p></div>
						<div class="td"><p>Editar</p></div>
						<div class="td"><p>Eliminar</p></div>
					</div>
					<%for(Categoria c: categorias) {%>
						<div class="tr">
							<div class="td"><p><%=c.getId()%></p></div>
							<div class="td"><p><%=c.getNombre()%></p></div>
							<div class="td">
								<form name="formEditarCategoria" method="post" action="redireccionServlet?destino=editarCategoria.jsp">
									<input type="hidden" name="hiddenId" 	 size="25" value="<%=c.getId()%>">
									<input type="hidden" name="hiddenNombre" size="25" value="<%=c.getNombre()%>">
									<input class="boton" type="submit" size="25" value="Editar">
								</form>
							</div>
							<div class="td">
								<form name="formBajaCategoria" method="post" action="abmcCategoriaServlet">
									<input type="hidden" name="hiddenOperacion" value="baja">
									<input type="hidden" name="hiddenId" size="25" value="<%=c.getId()%>">
									<input class="boton" type="submit" size="25" value="Eliminar">
								</form>
							</div>
						</div>
					<%}%>
				</div>
				
			<%}%>
			
		</div>
	
	</div>
	
</body>
</html>