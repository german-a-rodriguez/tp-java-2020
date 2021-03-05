
	/** subirImagenAlServidor.jsp */
	
	function cargarInfoEnHiddens(){
		
		/** Este codigo carga informacion de los archivos seleccionados para borrar en los input hidden del formulario */
		
		var fileInput = document.getElementById("fileInputBorrar");
		var files = fileInput.files;
		
		var cantidad = document.getElementById("inputHiddenCantidad");
		cantidad.value = files.length;
		
		var nombres = document.getElementById("inputHiddenNombres");
		nombres.value = "";
		
		var file;
		for (var i=0; i<files.length; i++) {
			
			file = files[i];
			nombres.value = nombres.value + file.name + "/";
			
		}
		
		/** Este codigo muestra la cantidad de archivos seleccionados para borrar */
		
		var parrafo = document.getElementById("cantFilesBorrar");
		
		var texto = "";
		if(files.length == 1){
			texto = " archivo seleccionado";
		} else if(files.length > 1){
			texto = " archivos seleccionados";
		}
		parrafo.textContent = files.length + texto;
		
	}
	
	function mostrarCantidadArchivosSubir(){
		
		/** Este codigo muestra la cantidad de archivos seleccionados para subir */
		
		var fileInput = document.getElementById("fileInputSubir");
		var files = fileInput.files;
		
		var parrafo = document.getElementById("cantFilesSubir");
		
		var texto = "";
		if(files.length == 1){
			texto = " archivo seleccionado";
		} else if(files.length > 1){
			texto = " archivos seleccionados";
		}
		parrafo.textContent = files.length + texto;
		
		/** Habilito boton "Subir imagenes" (No se nota por el css del boton) */
		
		var btnSubirImagenes = document.getElementById("btnSubirImgs");
		btnSubirImagenes.disabled = false;
		
	}
	
	function deseleccionarArchivosSubir(){
		
		/** Este codigo borra los elementos del array que guarda los archivos seleccionados en el input file para subir */
		
		//Borro archivos del array
		
		document.getElementById("fileInputSubir").value = "";
		
		//Cambio texto del parrafo
		
		var parrafo = document.getElementById("cantFilesSubir");
		
		parrafo.textContent = "No se eligi\u00F3 archivo"; // \u00F3 es la o con tilde
		
		/** Deshabilito boton "Subir imagenes" (No se nota por el css del boton) */
		
		var btnSubirImagenes = document.getElementById("btnSubirImgs");
		btnSubirImagenes.disabled = true;
		
	}
		
	function deseleccionarArchivosBorrar(){
		
		/** Este codigo borra los elementos del array que guarda los archivos seleccionados en el input file para borrar */
		
		//Borro archivos del array
		
		document.getElementById("fileInputBorrar").value = "";
		
		//Cambio texto del parrafo
		
		var parrafo = document.getElementById("cantFilesBorrar");
		
		parrafo.textContent = "No se eligi\u00F3 archivo"; // \u00F3 es la o con tilde
		
	}	
	