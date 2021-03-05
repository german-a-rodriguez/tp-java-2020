
/** index.jsp, agregarProducto.jsp, carrito.jsp, editarProducto.jsp, panelAdminCategorias.jsp, panelAdminProductos.jsp */

/** Al recargar la pagina se posiciona a la altura que estaba antes de la recarga */

window.onload = function(){
	var pos = window.name || 0;
	window.scrollTo(0,pos);
}
window.onunload = function(){
	window.name = self.pageYOffset || (document.documentElement.scrollTop+document.body.scrollTop);
}	