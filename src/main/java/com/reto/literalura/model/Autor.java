package com.reto.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaMuerte;
    @OneToMany(mappedBy = "autor",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor(){}

    public Autor(DatosAutor datosAutor){
        this.nombre = datosAutor.nombre();
        this.fechaNacimiento = datosAutor.fechaNacimiento();
        this.fechaMuerte = datosAutor.fechaMuerte();
    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {this.nombre = nombre;}

    public Integer getFechaNacimiento() {return fechaNacimiento;}

    public void setFechaNacimiento(Integer fechaNacimiento) {this.fechaNacimiento = fechaNacimiento;}

    public Integer getFechaMuerte() {return fechaMuerte;}

    public void setFechaMuerte(Integer fechaMuerte) {this.fechaMuerte = fechaMuerte;}

    public List<Libro> getLibros() {return libros;}

    public void setLibros(List<Libro> libros) {this.libros = libros;}

    @Override
    public String toString() {
        return   "id=" + id +'\'' +
                ", Nombre='" + nombre + '\'' +
                ", Nacimiento=" + fechaNacimiento +'\'' +
                ", Fallecimiento=" + fechaMuerte;
    }

}




