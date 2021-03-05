# tp-java-2020
## Trabajo práctico Java - Germán Rodríguez - Comisión 308


### Descripción del sistema

• El trabajo práctico consistirá en una aplicación web cliente-servidor desarrollada en el lenguaje de programación java. La aplicación será desarrollada en capas según la arquitectura aprendida durante el año en la materia. Los datos persistirán en una base de datos.

• La aplicación se desarrolla con el objetivo de vender productos informáticos al público. Para ello contará con distintos niveles de acceso. 

El nivel de invitado estará habilitado solo para explorar los productos que se ofrecen a través de un listado de los mismos. 

El nivel de cliente estará habilitado para seleccionar los productos que desee y realizar compras. Además, tendrá acceso al listado de todas sus compras y podrá realizar una valuación de cada una de ellas. 

Por otro lado, el nivel de administrador estará habilitado para consultar, agregar, editar y eliminar los productos que se ofrecen, así como también las categorías a las que pertenecen los productos. También podrá acceder al listado de los usuarios de la aplicación y al listado de todas las ventas realizadas y su valuación correspondiente.

• El listado de productos al cual pueden acceder el invitado y el cliente contará con un filtro por categoría. 

Se utilizará la sesión para guardar el usuario y su carrito de compras. También se la utilizará para guardar el filtro del listado de productos.

Durante la sesión se le mostrará al cliente la cantidad de productos en el carrito y la cantidad de valuaciones pendientes.

La aplicación contará con validaciones básicas del lado del cliente y manejo de errores en todas sus capas.

