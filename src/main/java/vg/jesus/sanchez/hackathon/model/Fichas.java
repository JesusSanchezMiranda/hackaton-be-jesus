package vg.jesus.sanchez.hackathon.model;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "fichas")
public class Fichas {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "autor")
    private String autor;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "anio_publicacion")
    private LocalDate anio_publicacion;

    @Column(name = "tema")
    private String tema;

    @CreationTimestamp
    @Column(name = "fecha_agregada", updatable = false)
    private LocalDate fecha_agregada;

    @Column(name = "tipo_ficha")
    private String tipo_ficha;

    @Column(name = "editorial")
    private String editorial;

    @Column(name = "numero_edicion")
    private Integer numero_edicion;

    @Column(name = "numero_paginas")
    private Integer numero_paginas;

    @Column(name = "estado")
    private boolean estado = true;
    
}
