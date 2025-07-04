package vg.jesus.sanchez.hackathon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vg.jesus.sanchez.hackathon.model.Fichas;

public interface FichasRepository extends JpaRepository<Fichas, Integer>{
    List <Fichas>findByEstado(boolean estado);
}
