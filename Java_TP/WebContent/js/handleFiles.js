
/** agregarProducto.jsp, editarProducto.jsp */

/** Al cambiar la imagen del input file se envia al servlet lo que ingreso el usuario hasta el momento para recargarlo 
 * junto a la nueva imagen seleccionada */

function handleFilesAgregar(files) {
	for (var i = 0; i < files.length; i++) {
		var file = files[i];
  		var descripcion			= document.getElementById("textDescripcion").value;
  		var selectIdCategoria	= document.getElementById("selectIdCategoria");
 		var idCategoria			= selectIdCategoria.options[selectIdCategoria.selectedIndex].value;
 		var precio				= document.getElementById("textPrecio").value;
 		var stock				= document.getElementById("textStock").value;
		var linkFoto 			= file.name;
 		location.href = "agregarProductoServlet?descripcion=" + descripcion + "&idCategoria=" + idCategoria + "&precio=" + precio + 
 						"&stock=" + stock + "&linkFoto=" + linkFoto;
	}
}

function handleFilesEditar(files) {
	for (var i = 0; i < files.length; i++) {
		var file = files[i];
		var idProducto 			= document.getElementById("hiddenId").value;
		var descripcion			= document.getElementById("textDescripcion").value;
		var selectIdCategoria	= document.getElementById("selectIdCategoria");
		var idCategoria			= selectIdCategoria.options[selectIdCategoria.selectedIndex].value;
		var precio				= document.getElementById("textPrecio").value;
 		var stock				= document.getElementById("textStock").value;
		var linkFoto 			= file.name;
		location.href = "editarProductoServlet?hiddenId=" + idProducto + "&descripcion=" + descripcion + "&idCategoria=" + idCategoria + 
						"&precio=" + precio + "&stock=" + stock + "&linkFoto=" + linkFoto;
	}
}