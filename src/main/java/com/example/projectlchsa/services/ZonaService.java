package com.example.projectlchsa.services;

import com.example.projectlchsa.dtos.zona.ZonaCorrectoDTO;
import com.example.projectlchsa.dtos.zona.ZonaRequestDTO;
import com.example.projectlchsa.entities.ZonaEntity;
import com.example.projectlchsa.mappers.ZonaMapper;
import com.example.projectlchsa.repositories.ZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ZonaService implements GenerateService<ZonaCorrectoDTO, ZonaRequestDTO> {

    @Autowired
    private ZonaRepository repository;
    @Autowired
    private ZonaMapper mapper;

    @Override
    public List<ZonaCorrectoDTO> findAll() throws Exception {
        try {
            return mapper.zonasCorrectasDTOs(repository.findAll());
        } catch (Exception e) {
            throw new Exception("No se lograron traer los registros de Zona.");
        }
    }

    @Override
    public ZonaCorrectoDTO findObject(Long idObject) throws Exception {
        try {
            Optional<ZonaEntity> zona = repository.findById(idObject);
            if (zona.isPresent()) {
                return mapper.zonaCorrectaDTO(zona.get());
            } else throw new Exception("No se encontró el id ingresado.");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public ZonaCorrectoDTO saveObject(ZonaRequestDTO object) throws Exception {
        try {
            if (object.getTotal() == 0) {
                throw new Exception("El espacio total debe ser ingresado.");
            } else return mapper.zonaCorrectaDTO(repository.save(mapper.zonaEntidadRequest(object)));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public ZonaCorrectoDTO updateObject(Long idObject, ZonaRequestDTO object) throws Exception {
        try {
            Optional<ZonaEntity> zona = repository.findById(idObject);
            if (zona.isEmpty()) {
                throw new Exception("No se encontró el id ingresado.");
            } else if (object.getTotal() == 0) {
                throw new Exception("El espacio total debe ser ingresado.");
            } else {
                ZonaEntity zonaEntity = zona.get();
                zonaEntity.setEspacioParcial(object.getParcial());
                zonaEntity.setEspacioTotal(object.getTotal());
                return mapper.zonaCorrectaDTO(repository.save(zonaEntity));
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public boolean deleteObject(Long idObject) throws Exception {
        try {
            Optional<ZonaEntity> zona = repository.findById(idObject);
            if (zona.isPresent()) {
                repository.deleteById(idObject);
                return true;
            } else throw new Exception("No se encontro el objeto con ese id.");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
