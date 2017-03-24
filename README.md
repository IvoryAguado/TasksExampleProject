# TasksExampleProject

La interfaz consta de 3 pantallas.

Pantalla Login
La pantalla de login tiene la función de iniciar sesión con los usuarios 
hardcodeados en la base de datos de la aplicación.

Credenciales:
	ROLE_ADMIN 
admin : password
	ROLE_TEC
user : password
	user1 : password

Navegación
Al iniciar sesion en la parte baja se podrá navegar entre las pantallas de Tareas y Web Service además de hacer logout.

Pantalla Tareas
Aquí el usuario logueado dependiendo de su role podrá añadir  una tarea si tiene el rol de Administrador (ROLE_ADMIN) con un Floating Button, si no es así simplemente se ocultara el botón.

Tambien se podra diferenciar entre una tarea terminada por el color verde en la celda de la tarea en la lista.

Pantalla  Web Service
En esta pantalla se muestran los datos previamente descargados de una API, cogiendo los datos directamente de la base de datos para una mejor UX.
(Si los datos están incompletos/descargando, se actualiza la interfaz una vez finalizado el “Job” de descargar los datos)


Librerías Utilizadas
GreenDao con cifrado (release) - Para la base de datos (persistencia)
Retrofit - Peticiones REST API parseando Json directamente utilizando Gson
EventBus - Postear Eventos para avisar de nuevos datos descargados a la interfaz
AES-Encryption - Encriptación de los datos de preferencias (release)
ButterKnife - Inyección/Bind a las vistas 
Gson - Parse de Objetos Json
JobQueue - Mejora y optimización a la hora de llevar a cabo tareas encapsulando las y haciéndolas transicionales, además de especificar el requerimiento de internet y evitar la ejecución repetida de la tarea.

