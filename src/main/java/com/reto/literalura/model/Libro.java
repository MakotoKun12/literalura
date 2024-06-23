package com.reto.literalura.model;

import jakarta.persistence.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    private Long id;
    private String titulo;
    @Enumerated(EnumType.STRING)
    private Language lenguaje;
    private String copyright;
    private Double numeroDescargas;
    @ManyToOne
    private Autor autor;

    public Libro(){}

    public Libro(DatosLibro datosLibro) {
        this.id = datosLibro.id();
        this.titulo = datosLibro.titulo();
        this.lenguaje = Language.fromString(datosLibro.idioma().stream()
                .limit(1).collect(Collectors.joining()));
        this.copyright = datosLibro.copyright();
        this.numeroDescargas = datosLibro.numeroDescargas();
    }

    //Getters and Seters

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getTitulo() {return titulo;}

    public void setTitulo(String titulo) {this.titulo = titulo;}

    public Language getLenguaje() {return lenguaje;}

    public void setLenguaje(Language lenguaje) {this.lenguaje = lenguaje;}

    public String getCopyright() {return copyright;}

    public void setCopyright(String copyright) {this.copyright = copyright;}

    public Double getNumeroDescargas() {return numeroDescargas;}

    public void setNumeroDescargas(Double numeroDescargas) {this.numeroDescargas = numeroDescargas;}

    public Autor getAutor() {return autor;}

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Id=" + id + '\'' +
                ", Titulo='" + titulo + '\'' +
                ", Idioma=" + lenguaje +
                ", Copyright='" + copyright + '\'' +
                ", NumeroDescargas=" + numeroDescargas +
                ", Autor=" + autor +
                '}';
    }
}
