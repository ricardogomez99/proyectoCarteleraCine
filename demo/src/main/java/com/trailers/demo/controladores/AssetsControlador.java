package com.trailers.demo.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trailers.demo.servicio.AlmacenServicioImpl;

@RestController
@RequestMapping("/assets")
public class AssetsControlador {

    @Autowired
    private AlmacenServicioImpl servicio;

    @GetMapping("/{filename:.+}")
    public Resource obtenerRecursos(@PathVariable("filename") String filename){
        return servicio.cargarComoRecurso(filename);
    }
    
}
