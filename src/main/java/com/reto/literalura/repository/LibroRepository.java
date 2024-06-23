package com.reto.literalura.repository;

import com.reto.literalura.model.Autor;
import com.reto.literalura.model.Language;
import com.reto.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Autor,Long> {

    Optional<Autor> findAutorByNombreContaining(String name);

    @Query("SELECT l FROM Libro l JOIN l.autor a WHERE l.titulo LIKE %:tituloLibro%")
    Optional<Libro> getLibroContainsEqualsIgnoreCaseTitulo(String tituloLibro);

    @Query("SELECT l FROM Autor a join a.libros l")
    List<Libro> findLibrosByAutor();


    @Query("SELECT a FROM Autor a WHERE a.fechaMuerte > :fecha ")
    List<Autor> getAutorbyFechaMuerte(Integer fecha);

    @Query("SELECT l FROM Autor a join a.libros l WHERE l.lenguaje = :lenguaje")
    List<Libro> findLibroByLenguaje(Language lenguaje);

}
