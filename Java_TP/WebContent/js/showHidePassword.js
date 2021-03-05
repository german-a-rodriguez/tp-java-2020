
/** iniciarSesion.jsp, registrarUsuario.jsp */

/** Este codigo muestra y oculta el contenido del input password al presionar el boton (img) */

function showHide(){
	var input = document.getElementById("inputPassword");
	var boton = document.getElementById("btn-show-hide");
	if(input.type === "password"){
		input.type = "text";
		boton.src="images/app/ocultarPassword.png";
	} else {
		input.type = "password";
		boton.src="images/app/mostrarPassword.png";
	}
}