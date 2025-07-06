package vg.jesus.sanchez.hackathon.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vg.jesus.sanchez.hackathon.model.Fichas;
import vg.jesus.sanchez.hackathon.service.FichasService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1/api/fichas")
public class FichasRest {

    private final FichasService fichasService;

    public FichasRest(FichasService fichasService){
        this.fichasService = fichasService;
    }

    @GetMapping
    public List <Fichas> findAll() {
        return fichasService.findAll();
    }

    @GetMapping("/estado/{estado}")
    public List <Fichas> findByEstado(@PathVariable boolean estado) {
        return fichasService.findByEstado(estado);
    }

    @GetMapping("/{id}")
    public Optional <Fichas> findById(@PathVariable Integer id) {
        return fichasService.findById(id);
    }

    @PostMapping("/save")
    public Fichas save(@RequestBody Fichas fichas) { 
        return fichasService.save(fichas);
    }

    @PutMapping("/update/{id}")
    public Fichas update(@PathVariable("id") Integer id, @RequestBody Fichas fichas) {
        fichas.setId(id);
        return fichasService.update(fichas);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id){
        fichasService.delete(id);
    }

    @PutMapping("/restore/{id}")
    public void restore(@PathVariable Integer id) {
        fichasService.restore(id);
    }

     @GetMapping("/pdf")
    public ResponseEntity<byte[]> generateJasperPdfReport() {
        try {
            byte[] pdf = fichasService.generateJasperPdfReport();
            return ResponseEntity.ok()
                    //Renombrar el archivo PDF al descargar
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=fichas_report.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdf);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
        
    
}
