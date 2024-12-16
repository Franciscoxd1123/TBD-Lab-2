Instrucciones detalladas para configurar y desplegar la aplicación.

### Descarga
Clonar el repositorio Git del proyecto mediante `git clone` o descargar el proyecto en un archivo `.zip` y descomprimirlo donde se desee

### Frontend
. Luego ingresar a la carpeta "Frontend TBD-Lab-1" y acceder al simbolo de sistema o cmd para ejecutar los siguientes comandos:

```bash
npm install
npm install axios
npm install vue-router
```

> [!NOTE]  
> Sabemos que `npm install` deberia ser suficiente, sin embargo a veces igualmente fue necesario instalar por separado `axios` y `vue-router`, asi evitamos probelmas..

Una vez instalado todo eso, para levantar el frontend se debe en la consola ejecutar `npm run dev` lo que lo abrira donde se especifica en la consola.


## Backend

Después utilizar un IDE a gusto para poder manejar Frontend y Backend del proyecto. Con esto acceder mediante el IDE seleccionado a los archivos `application.properties` y `Sql2oConfig.java` que se encuentran en  `\src\main\resources` y `\src\main\java\com\example\Ecommerce\config" respectivamente, estos se encuentran en el Backend del proyecto y se tienen que modificar credenciales de el nombre de usuario y contraseña de acceso a la base de datos en postgreSQL (Cambiar el puerto si es necesario, pero postgreSQL trabaja por defecto en el puerto 5432).

Tener creada una base de datos en postgreSQL con el nombre `Ecommerce` y utilizar el archivo `respaldo_Ecommerce.tar` para poder "restaurar" la base de datos, esto se puede usar desde pgAdmin o con la consola ingresando el comando `pg_restore -c -U [tu usuario] -d Ecommerce -v "/ruta del archivo/respaldo_Ecommerce.tar"`.

Con esto queda listo para levantar el Backend, en nuestro caso utilizamos IntelliJ 

## Uso

Con lo anterior quedaria levantado (si no modifican ningun archivo ni puerto):

- Frontend en la url: http://localhost:5173/
- Backend en la url: http://localhost:8090/

Para ver el funcionamiento o para ver que los datos se estan modificando o guardando se pueden utilizar las rutas de los controladores del backend en consultas mediante Postman u otro equivalente, o de por si probar
directamente con las funcionaledades respectivas de la aplicación web.


### Documentación que explique cómo funciona la aplicación y cómo se realizó la implementación.
Al utilizar la ruta http://localhost:5173/ para acceder a la aplicación se le redireccionara a la página principal, donde:

Página principal:
- Registrarse: Funcionalidad que registra al usuario (Después redirecciona directamente a iniciar sesión).
- Iniciar sesión: Funcionalidad que permite loguear al usuario (Después redirecciona al menú del usuario logueado).

Pagina bienvenida:
- Aqui se muestra todo los productos disponibles para el usuario.
- Contiene un buscador y un filtro por categorias
- Mediante el boton **Agregar a la Orden** se pueden agregar los distintos productos al cartito de compras.

Lista de Ordenes:
- El listado con todas las ordenes creadas por el usuario especificando su estado y detalle.

Devolucion de productos:
- Aqui es donde se permite al usuario generar una devolucion de productos, para esto la orden tiene que estar en el estado "Enviado", sino, no se permitira.

Historial de Compras:
- Todas las compras del usuario que se encuentren enviadas o pagadas pendientes a su envio, en caso de que el pago este pendiente o haya sido devuelto no aparecen.

Ordenes cercanas (10km):
- Muestra todas las ordenes que se encuentren cerca, entendiendo que cerca es un radio de 10km.

Almacen mas cercano:
- Te muestra a partir de todos los almacenes registrados, cual es el mas cercano respecto a la direccion del cliente.

Ruta mas corta:
- Tomando en cuenta todos los almacenes, retorna la distancia de la rota mas corta de cada uno de ellos.

Cerrar sesion.

## Pruebas de queries SQL.

Para ejecutar las pruebas, se deben correr los archivos ubicados en `/Backend/src/main/resources/Datos/` donde se encuentra `procedureQueries.sql` que tiene los procedimientos solicitados, `sentenciaSQL.sql` y `triggerinventario.sql` junto a `triggerQueries.sql` que mantienen los triggers necesarios, adentro de cada archivo encontraran la documentacion pertinente.

En nuestro caso, para ejecutar estas distintas queries utilizamos PgAdmin 4 para copiarlas, pegarlas y poder correrlas, aunque es libre de usar la metodologia que mas le acomode.

## Lenguajes y tecnologias

Para la implementación del Backend se utilizo Springboot para la creación del proyecto, Itellij IDEA para manejar el Backend y Sql2o para la conexión entre Backend y base de datos.

Para la implementación del Frontend se utilizo Node.js y Vue para la creación del proyecto, Visual Studio Code para manejar el Frontend y Axios para poder conectar Backend y Frontend.

Y para la implementacion y manejo de la base de datos se utilizo postgreSQL y PGAdmin 4.
