package com.trailers.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trailers.demo.modelo.Genero;

public interface GeneroRepositorio extends JpaRepository<Genero,Integer>{
    
}
