package com.reto.literalura.principal;

import com.reto.literalura.model.*;
import com.reto.literalura.repository.LibroRepository;
import com.reto.literalura.service.ConsumoAPI;
import com.reto.literalura.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private static final String URL_BASE = "https://gutendex.com/books/";
    private static final String URL_SEARCH = "?search=";
    private final LibroRepository repositorio;

    public Principal(LibroRepository repository){this.repositorio = repository;}

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ***************************
                    Elija la opcion deseada:
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores en un determinado año
                    5 - Listar libros por idioma
                    6 - Buscar autor por nombre 
                    
                    0 - Salir
                    ***************************
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    listarLibros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLibrosIdioma();
                    break;
                case 6:
                    buscarAutorPorNombre();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void buscarLibroWeb() {
        System.out.println("Escribe el titulo del libro que deseas buscar: ");
        var tituloLibro = teclado.nextLine().replace(" ", "%20");
        var json = consumoApi.obtenerDatos(URL_BASE + URL_SEARCH + tituloLibro);
        var baseDatos = conversor.obtenerDatos(json, Datos.class);

        Optional<DatosLibro> busquedalibro = baseDatos.resultados().stream()
                .findFirst();

        if (busquedalibro.isPresent()) {
            System.out.println(
                    "\n----- LIBRO -----\n" +
                            "Titulo: " + busquedalibro.get().titulo() +
                            "\n Autor: " + busquedalibro.get().autor().stream().map(a -> a.nombre()).limit(1).collect(Collectors.joining()) +
                            "\n Idioma: " + busquedalibro.get().idioma().stream().collect(Collectors.joining()) +
                            "\n Numero descargas: " + busquedalibro.get().numeroDescargas() + "\n" +
                            "\n-----------------");

            try {
                List<Libro> libros = busquedalibro.stream().map(Libro::new).collect(Collectors.toList());
                Autor autorApi = busquedalibro.stream()
                        .flatMap(b -> b.autor().stream().map(Autor::new))
                        .findFirst()
                        .orElseThrow(() -> new Exception("Autor no encontrado"));

                Optional<Autor> autorBd = repositorio.findAutorByNombreContaining(
                        busquedalibro.get().autor().stream()
                                .map(a -> a.nombre())
                                .collect(Collectors.joining())
                );

                Optional<Libro> optionalLibro = repositorio.getLibroContainsEqualsIgnoreCaseTitulo(tituloLibro);

                if (optionalLibro.isPresent()) {
                    System.out.println("El libro ya está guardado en la base de datos.");
                } else {
                    Autor autor;
                    if (autorBd.isPresent()) {
                        autor = autorBd.get();
                        System.out.println("El autor ya existe en la base de datos.");
                    } else {
                        autor = autorApi;
                        repositorio.save(autor); // Guarda el nuevo autor primero
                    }

                    // Asocia los libros con el autor y guarda
                    for (Libro libro : libros) {
                        libro.setAutor(autor); // Asegura que cada libro tenga un autor
                    }
                    autor.setLibros(libros);
                    repositorio.save(autor); // Guarda el autor con los nuevos libros
                }

            } catch (Exception e) {
                System.out.println("Warning! " + e.getMessage());
            }

        } else {
            System.out.println("Libro no encontrado");
        }
    }

    private void listarLibros(){
        List<Libro> libros = repositorio.findLibrosByAutor();
        libros.forEach(l -> System.out.println(
                        "------ LIBRO ------" +
                        "\nTitulo: " + l.getTitulo() +
                        "\nAutor: " + l.getAutor().getNombre() +
                        "\nIdioma: " + l.getLenguaje().getIdiom() +
                        "\nNumero de descargas: " + l.getNumeroDescargas() +
                        "\n------------------"));
    }

    private void listarAutores(){
        List<Autor> autores = repositorio.findAll();
        autores.forEach(l -> System.out.println(
                        "\n----- AUTOR -----" +
                        "\nAutor: " + l.getNombre() +
                        "\nFecha nacimiento: " + l.getFechaNacimiento() +
                        "\nFecha muerte: " + l.getFechaMuerte() +
                        "\n Libros: " + l.getLibros().stream()
                        .map(t -> t.getTitulo()).collect(Collectors.toList()) + "\n" +
                        "\n------------------------\n"
        ));
    }

    private void listarAutoresVivos(){
        out.println("Escribe el año que quieres buscar la vida de un autor:");
        try {
            var fecha = Integer.valueOf(teclado.nextLine());
            List<Autor> autores = repositorio.getAutorbyFechaMuerte(fecha);
            if(!autores.isEmpty()){
                autores.forEach(l -> out.println(
                        "\n----- AUTHOR -----" +
                                "\nAutor: " + l.getNombre() +
                                "\nFecha de nacimiento: " + l.getFechaNacimiento() +
                                "\nFecha de muerte: " + l.getFechaMuerte() +
                                "\n Libros: " + l.getLibros().stream()
                                .map(t -> t.getTitulo()).collect(Collectors.toList()) + "\n" +
                                "\n------------------------\n"
                ));
            }else {
                out.println("Lo sentimos no hay ningun autor vivo en esta fecha: "+fecha);
            }
        } catch (NumberFormatException e) {
            out.println("Por favor digita solo fecha validas usando solo numeros ej:2020"+e.getMessage());
        }
    }

    private void mostrarIdiomas(){
        var idiomas = """
                    ***************************
                    Idiomas soportados:
                    en - INGLES
                    es - ESPAÑOL
                    fr - FRANCES
                    it - ITALIANO
                    pt - PORTUGUES
                    ***************************
                    """;
        System.out.println(idiomas);
    }

    private void listarLibrosIdioma(){
        mostrarIdiomas();
        System.out.println("Ingrese el idioma para buscar: ");
        var idiomaEntrada = teclado.nextLine().toLowerCase();

        if (idiomaEntrada.equalsIgnoreCase("es")
                || idiomaEntrada.equalsIgnoreCase("en")
                || idiomaEntrada.equalsIgnoreCase("it")
                || idiomaEntrada.equalsIgnoreCase("fr")
                || idiomaEntrada.equalsIgnoreCase("pt")) {
            Language lenguaje= Language.fromString(idiomaEntrada);
            List<Libro> libros = repositorio.findLibroByLenguaje(lenguaje);

            if (libros.isEmpty()){
                out.println("Lo sentimos no contamos con ningun libro para este lenguaje.");
            }else {
                libros.forEach(t-> out.println(
                        "------ BOOK ------" +
                                "\nTitle: " + t.getTitulo() +
                                "\nAuthor: " + t.getAutor().getNombre() +
                                "\nLanguage: " + t.getLenguaje().getIdiom() +
                                "\nNumber of downloads: " + t.getNumeroDescargas() +
                                "\n------------------"));
            }
        }else{
            out.println("Por favor digita un formato de lenguaje valido");
        }
    }

    public void buscarAutorPorNombre(){
        if (repositorio == null) {
            System.out.println("Repositorio no inicializado");
            return;
        }
        out.println("Escribe el nombre del autor: ");
        var nombre = teclado.nextLine();
        Optional<Autor> autor = repositorio.findAutorByNombreContaining(nombre);


        if (autor.isPresent()){
            System.out.println(
                    "\n----- AUTOR -----" +
                            "\nAuthor: " + autor.get().getNombre() +
                            "\nFecha nacimiento: " + autor.get().getFechaNacimiento() +
                            "\nFecha muerte: " + autor.get().getFechaMuerte() +
                            "\nLibros: " + autor.get().getLibros().stream()
                            .map(l -> l.getTitulo()).collect(Collectors.toList()) + "\n"+
                            "\n--------------------\n" );
        }else {
            out.println("Este autor no esta registrado en nuestra base de datos.");
        }
    }

}
