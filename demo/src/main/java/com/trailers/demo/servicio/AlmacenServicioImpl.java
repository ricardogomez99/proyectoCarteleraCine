package com.trailers.demo.servicio;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.trailers.demo.excepciones.AlmacenExcepcion;
import com.trailers.demo.excepciones.FileNotFoundException;

@Service
public class AlmacenServicioImpl implements AlmacenServicio{

    @Value("${storage.location}")
    private String storageLocation;

    @PostConstruct //Sirve para indicar que este metodo se va a ejecutar cada vez que haya una nueva instancia de esta clase
    @Override
    public void iniciarAlmacenDeArchivos() {
        try {
           Files.createDirectories(Paths.get(storageLocation));
        } catch (IOException exception) {
            throw new AlmacenExcepcion("Error al inicializar la ubicación en el almacen de archivos");
        }
    }

    @Override
    public String almacenarArchivo(MultipartFile archivo) {
       String nombreArchivo = archivo.getOriginalFilename();
       if(archivo.isEmpty()){
            throw new AlmacenExcepcion("No se puede almacenar un archivo vacio");
       }

       try {
            InputStream inputStream = archivo.getInputStream();
            Files.copy(inputStream,Paths.get(storageLocation).resolve(nombreArchivo),StandardCopyOption.REPLACE_EXISTING);
       } catch (IOException exception) {
            throw new AlmacenExcepcion("Error al almacenar el archivo" + nombreArchivo,exception);

       }
       return nombreArchivo;
    }

    @Override
    public Path cargarArchivo(String nombreArchivo) {
        return Paths.get(storageLocation).resolve(nombreArchivo);
    }

    @Override
    public Resource cargarComoRecurso(String nombreArchivo) {
       try {
            Path archivo = cargarArchivo(nombreArchivo);
            Resource recurso = new UrlResource(archivo.toUri());

            if(recurso.exists() || recurso.isReadable()){
                return recurso;
            }else{
                throw new FileNotFoundException("No se puedo encontrar el archivo "+nombreArchivo);
            }

       } catch (MalformedURLException exception) {
            throw new FileNotFoundException("No se puedo encontrar el archivo "+exception);
       }
    }

    @Override
    public void eliminarArchivo(String nombreArchivo) {
        Path archivo = cargarArchivo(nombreArchivo);

        try {
            FileSystemUtils.deleteRecursively(archivo);
        } catch (Exception exception) {
            System.out.println(exception);
        }


    }
    
}
