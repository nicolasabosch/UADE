========================================================================
 UADE Beats - Modulo 1: Musica y Videos
 Trabajo Practico Integrador de Paradigma Orientado a Objetos - UADE
 Equipo 7 - Comision Aula 349
 Integrantes: Ignacio Andant, Federico Sznajderhaus, Nicolas Abosch,
              Axel Estrada, Ignacio Bruno
 Docente: Alfonso Fernandez Buttera
========================================================================

CONTENIDO
---------
src/base          Clases comunes de la plataforma, interfaces y enums.
src/excepciones   Jerarquia de excepciones propias del dominio.
src/modulo        Clases del modulo Musica y Videos.
src/persistencia  Persistencia en archivos de texto (.txt) con lectura java.io
                  y escritura NIO.2.
src/gui           Interfaz grafica en Swing (VentanaPrincipal).
src/Main.java     Clase ejecutable con la prueba funcional + lanzamiento GUI.
src/datos         Archivos editables (carpeta unica src/datos).
diagrama/         Diagrama de clases Draw.io (UADE_Beats_DiagramaClases.drawio).

REQUISITOS
----------
- JDK 17 o superior (probado con JDK 21).
- No utiliza ninguna libreria externa.

COMPILAR
--------
Desde la carpeta del proyecto en PowerShell:

    if (!(Test-Path out)) { New-Item -ItemType Directory -Path out | Out-Null }
    $files = Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
    javac -encoding UTF-8 -d out $files

El proyecto compila sin errores y no utiliza librerias externas.

EJECUTAR
--------
    java -cp out Main

La prueba funcional lee los datos iniciales desde "src/datos" y se imprime por consola.

Si se ejecuta en un entorno con pantalla, ademas se abre automaticamente
la ventana Swing. Si se quiere ejecutar solo la prueba por consola:

    java "-Djava.awt.headless=true" -cp out Main

La ventana Swing muestra tres paneles: Catalogo y reproduccion, Playlists
(playlists privadas por usuario) e Interacciones. La GUI no redirige la
salida de consola; llama a metodos del modelo que devuelven String y muestra
esos textos en JTextArea.

ALINEACION CON CLASE 11/12
---------------------------
Lectura de archivos (Clase 11):
  FileReader, BufferedReader, readLine(), trim(), isEmpty().
  Las lineas de encabezado en los .txt empiezan con '#' y se omiten con
  startsWith("#").
  Los campos de cada registro se separan con split(";").

Escritura de archivos (Clase 12):
  Files.writeString para guardar catalogo exportado y comentarios.

Deteccion de carpeta de datos (Clase 11/12):
  new File(ruta).exists() para ubicar la carpeta datos al ejecutar.

ARCHIVOS DE DATOS
-----------------
src/datos/artistas.txt    id;nombre;generoPrincipal;biografia;verificado
src/datos/usuarios.txt    id;nombreUsuario;nombre;apellido;email;password;plan
src/datos/canciones.txt   id;titulo;duracion;descripcion;fecha;disponible;exclusivo;genero;artistaId;letra
src/datos/videoclips.txt  id;titulo;duracion;descripcion;fecha;disponible;exclusivo;resolucion;subtitulos;url;cancionId
src/datos/albumes.txt     id;titulo;descripcion;fecha;disponible;exclusivo;anio;discografica;cancionIds
src/datos/entrevistas.txt id;titulo;duracion;descripcion;fecha;disponible;exclusivo;entrevistador;tematica;url
src/datos/playlist.txt    id;nombre;usuarioPropietarioId;contenidoIds
src/datos/comentarios.txt salida generada al comentar desde la GUI

Para agregar un artista, usuario, cancion, videoclip, album, entrevista o
playlist, se edita el archivo correspondiente y se vuelve a ejecutar el
programa. No hace falta modificar Main.java ni recompilar por cambio de datos.
Un album o playlist guarda ids de contenido separados por coma; esas canciones
viven en canciones.txt y pueden aparecer en varios albumes o playlists.

ESTRUCTURA DE PAQUETES
----------------------
base:         PlanSuscripcion (enum); IInteractuable, Reproducible, Validable
              (interfaces); Usuario, Artista, Contenido (abstract), Playlist,
              Comentario, Reaccion.
excepciones:  UadeBeatsException (base) + ContenidoNoDisponibleException,
              PlanInsuficienteException, UsuarioInvalidoException.

Planes: FREE (contenido libre), PREMIUM (contenido exclusivo), ARTIST_PASS
        (contenido exclusivo + entrevistas grabadas).
modulo:       ContenidoAudiovisual (abstract), Cancion, VideoClip, Album,
              EntrevistaGrabada, CatalogoMusical.
persistencia: GestorPersistencia (NIO.2: Files.writeString; java.io:
              FileReader / BufferedReader).
gui:          VentanaPrincipal.
========================================================================
