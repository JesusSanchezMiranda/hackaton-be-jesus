package vg.jesus.sanchez.hackathon.service.Impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import vg.jesus.sanchez.hackathon.model.Fichas;
import vg.jesus.sanchez.hackathon.repository.FichasRepository;
import vg.jesus.sanchez.hackathon.service.FichasService;

@Slf4j
@Service
public class FichasServiceImpl implements FichasService{

    private final FichasRepository fichasRepository;
    private final DataSource dataSource;

    public FichasServiceImpl(FichasRepository fichasRepository, DataSource dataSource){
        this.fichasRepository = fichasRepository;
        this.dataSource = dataSource;
    }

    @Override
    public List <Fichas> findAll(){
        log.info("Listado de datos");
        return fichasRepository.findAll();
    }

    @Override
    public List <Fichas> findByEstado(boolean estado){
        log.info("Listado de datos por estado");
        return fichasRepository.findByEstado(estado);
    }

    @Override
    public Optional <Fichas> findById(Integer id){
        log.info("Listado de datos por ID");
        return fichasRepository.findById(id);
    }

    @Override
    public Fichas save(Fichas fichas){
        log.info("Guardado de datos" + fichas.toString());
        return fichasRepository.save(fichas);
    }

@Override
public Fichas update(Fichas nuevaFicha) {
    Fichas fichaExistente = fichasRepository.findById(nuevaFicha.getId())
        .orElseThrow(() -> new EntityNotFoundException("Ficha no encontrada"));

    fichaExistente.setTema(nuevaFicha.getTema());
    fichaExistente.setNumero_paginas(nuevaFicha.getNumero_paginas());
    fichaExistente.setNumero_edicion(nuevaFicha.getNumero_edicion());
    fichaExistente.setEditorial(nuevaFicha.getEditorial());

    log.info("Actualizado de datos: " + fichaExistente);
    return fichasRepository.save(fichaExistente);
}


    @Override
    public void delete(Integer id){
        log.info("Eliminado de datos por ID");
        Optional <Fichas> fichas = fichasRepository.findById(id);
        fichas.ifPresent(
            em ->{
                em.setEstado(false);
                fichasRepository.save(em);
            }
        );
    }


    @Override
    public void restore(Integer id){
        log.info("Restaurado de datos");
        Optional <Fichas> fichas = fichasRepository.findById(id);
        fichas.ifPresent(
            em ->{
                em.setEstado(true);
                fichasRepository.save(em);
            }
        ); 
    }

    @Override
    public byte[] generateJasperPdfReport() throws Exception {
        // Cargar archivo .jasper en src/main/resources/reports (SIN USAR IMÁGENES EN EL JASPER)
        InputStream jasperStream = new ClassPathResource("reports/fichas_report.jasper").getInputStream();
        // Sin parámetros
        HashMap<String, Object> params = new HashMap<>();
        // Llenar reporte con conexión a Oracle Cloud con application.yml | aplicación.properties
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperStream, params, dataSource.getConnection());
        // Exportar a PDF
        log.info("Reporte Jasper en PDF");
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    
}
