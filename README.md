# RappiTest
### 1. Las capas de la aplicación (por ejemplo capa de persistencia, vistas, red, negocio, etc) y qué clases pertenecen a cual.

Este proyecto utilizo MVVM para su desarrollo y se siguieron las recomendaciones de android para la arquitectura

|─ app\

 	|──── data\

  		|──── repository\

  			|──── db
  			Capa de comunicacion con el API

  			|──── memory
  			Capa de control de percistencia

  		|──── service
 	 	Capa de red para el acceso al API
  
  	|──── models\
  	contiene todos los modelos necesarios para conectarse a la localdb y API
    
  	|──── ui\
  	Capa de ui esta parte tiene todos los elementos necesarios para el control y diseño de la vista 

  		|──── adapters
		Adaptadores para las vistas recyclerview y para el binding con sus respectivos Holders

  		|──── bindings
  		Aquí se encuentran los data bindings para vincular los datos a la UI y así mejorar el uso de MVVM

  		|──── viewmodels
		Se encuentran todos los viewmodels para implementar la lógica de negocio

  	|──── utils\ 
  	Se muestral clases genericas de uso global o que ayudan a lo largo del proyecto y evitan repeticiones

### 2. La responsabilidad de cada clase creada.
La aplicación se compone de 3 pantallas principales
- HomeActivity
Permite que el usuario vea las películas, las filtre por popular, top rated y upcoming además contiene un buscador
	- MoviesFragment
- MovieDetailsActivity
Es la vista que muestra el detale de cada una de las películas
La aplicación cuenta con modo offline y online para lo cuál se desarrollo lo siguiente

**Offiline**
Su lógica se encuentra en memory
- MovieDatabase
Se utilizo una base de datos ROOM y se implemento un singleton para esta clase almacena la película,trailes,cast y reviews gracias a sus 
respectivos Daos
- MovieLocalDataSource
Permite la conexión y manejo con la base de datos insertar, salvar y obtener las películas, trailes,cast y reviews

**Online**
Su lógica se encuentra en db
- PageKeyedMovieDataSoure 
Se utilizó Paging parte del Android Jatpack que nos permite cargar y mostrar los datos obtenidos de manera parcial
- NetworkBoundResource 
Es una clase generica que da un recurso para la base de datos local y la remota
- MovieRepository 
Retorna los datos paginados y los obtiene directamente de la conexión a la API
- MovieDataSourceFactory
Es un data source factory que permite observar la creacion de un data source, es parte del paginado
- DataSource
Es la interfaz del data source 
-> MovieRemoteDataSource
Es quien nos permite conectarnos al servicio y manejar las interacciones con el API

Finalmente para el correcto desarrollo del proyecto hay clases en utils importantes como
- LiveDataCallAdapter
Es un adaptador para el call de retrofit, nos permite convertir de Call a un LiveData del API
- SingleLiveEvent
Es una clase que permirte observar el ciclo de vida y así enterarse de los cambios y evita el problema que se tiene con los eventos a lo largo de la aplicación


### 1. En qué consiste el principio de responsabilidad única? Cuál es su propósito?
Su proposito es que cada componente tenga una sola resposabilidad para que el código sea más modular y tenga mejor control de cambios. En este proyecto se realizo la arquitectura MVVM donde cada modulo esta aislado y puede ser reciclado o afectado sin modificar todo el proyecto

## 2. Qué características tiene, según su opinión, un “buen” código o código limpio?
Para mi un "buen" código tiene muchos detalles, en principal creo que hacer más con menos no repetir código, usar métodos, hacer modular el programa para que este sea mantenible, ser claros en el nombre de clases,variables, etc.
Ademas Creo que es importante tener claro patrones de diseño y arquitecturas que nos ayudan a estar en un mismo canal y seguir buenas prácticas.

## DEMO
![](prueba.gif)



 




