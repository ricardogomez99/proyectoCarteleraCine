package com.trailers.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trailers.demo.modelo.Pelicula;

public interface PeliculaRepositorio extends JpaRepository<Pelicula,Integer>{
    
}
