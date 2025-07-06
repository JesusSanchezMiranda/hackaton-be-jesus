package vg.jesus.sanchez.hackathon.service;

import java.util.List;
import java.util.Optional;

import vg.jesus.sanchez.hackathon.model.Fichas;

public interface FichasService {

    List <Fichas> findAll();

    List <Fichas> findByEstado(boolean estado);

    Optional <Fichas> findById(Integer id);

    Fichas save(Fichas fichas);

    Fichas update(Fichas fichas);

    void delete(Integer id);

    void restore(Integer id);

    byte[] generateJasperPdfReport() throws Exception;
    
}
