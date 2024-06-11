# Bancash

Aplicación demo de una fintech virtual llamada Bancash

## Build Stack

Esta aplicación fue desarrollada enteramente con lenguaje kotlin. Se usó una arquitectura limpia, esto significa que se separó en 3 paquetes principales, 
`domain`, `data` y `presentation`. Utilizando Jetpack Compose en la vista y Firebase para el backend. 
Como inyección de dependencias se utilizó `Koin` que funciona perfecto en proyectos kotlin y no requiere anotaciones.
Se incluyeron otras dependencias útiles como `Coil` para la visualizacion de imagenes del servidor y `Lottie` para animaciones.

## Backend

El backend esta realizado unicamente con Firebase: 
- Auth para el manejo de registro y login
- Storage para el almacenamiento de fotografias del usuario
- Firestore Database para la base de datos principal, que al ser un negocio tipo bancario se debería usar una base de datos transaccional como Postgres o MariaDB pero para propositos del demo se hicieron datos simples en documentos

La base de datos consta de las siguientes colecciones:
- `users` donde se almacenan datos del usuario como nombre, apellido, correo, avatar
- `accounts` una estructura muy simple que consta de una referencia al usuario y un balance general
- `movements` una coleccion de todos los movimientos bancarios de todos los usuarios usando una referencia al numero de cuenta para poder filtrarlos

## Registro

La pantalla de registro recopila datos de nombre, apellido, correo, password y una foto selfie. 
Despues de validar que todos los datos de entrada son correctos se crea el usuario con FirebaseAuth si es exitoso se sube la foto selfie a Firebase Storage y se recupera la URL la cual se guarda en la estructura del usuario

## Balance y movimientos

luego se genera la cuenta con un movimiento de una promocion de $100 por nuevo usuario. Todo se orquesta desde la app y no tiene forma de recuperarse si se llegara a cortar el flujo