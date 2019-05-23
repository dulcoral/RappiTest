# RappiTest

**1. Las capas de la aplicación (por ejemplo capa de persistencia, vistas, red, negocio, etc) y qué clases pertenecen a cual.**
**2. La responsabilidad de cada clase creada.**

Este proyecto utilizo MVVM para su desarrollo

|─ app\

 	|──── data\

  		|──── repository\

  			|──── db
  			Capa de control de percistencia

  			|──── remoto
  			Capa de comunicacion con el api

  		|──── service
 	 	Capa de red para el acceso al api
  
  	|──── models\
  	contiene todos los modelos necesarios para conectarse a la localdb y api
    
  	|──── ui\
  	Capa de ui esta parte tiene todos los elementos necesarios para el control y diseño de la vista 

  		|──── adapters

  		|──── bindings

  		|──── viewmodels 

  	|──── utils\ 
  
  	Se muestral clases genericas de uso global o que ayudan a lo largo del proyecto y evitan repeticiones



**Responda y escriba dentro del Readme con las siguientes preguntas:**

**1. En qué consiste el principio de responsabilidad única? Cuál es su propósito?**
Su proposito es que cada componente tenga una sola resposabilidad para que el código sea más modular y tenga mejor control de cambios. En este proyecto se realizo la arquitectura MVVM donde cada modulo esta aislado y puede ser reciclado o afectado sin modificar todo el proyecto

**2. Qué características tiene, según su opinión, un “buen” código o código limpio?**
Para mi un "buen" código tiene muchos detalles, en principal creo que hacer más con menos no repetir código, usar métodos, hacer modular el programa para que este sea mantenible, ser claros en el nombre de clases,variables, etc.
Ademas Creo que es importante tener claro patrones de diseño y arquitecturas que nos ayudan a estar en un mismo canal y seguir buenas prácticas.


