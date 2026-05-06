package com.duoc.unidosanimales.service;

import com.duoc.unidosanimales.model.EstadoSolicitud;
import com.duoc.unidosanimales.model.SolicitudAdopcion;
import com.duoc.unidosanimales.repository.SolicitudAdopcionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SolicitudAdopcionService {

    private final SolicitudAdopcionRepository solicitudAdopcionRepository;

    public SolicitudAdopcionService(SolicitudAdopcionRepository solicitudAdopcionRepository) {
        this.solicitudAdopcionRepository = solicitudAdopcionRepository;
    }

    public List<SolicitudAdopcion> listarTodas() {
        return solicitudAdopcionRepository.findAll();
    }

    public Optional<SolicitudAdopcion> buscarPorId(Long id) {
        return solicitudAdopcionRepository.findById(id);
    }

    public SolicitudAdopcion guardar(SolicitudAdopcion solicitudAdopcion) {
        if (solicitudAdopcion.getEstado() == null) {
            solicitudAdopcion.setEstado(EstadoSolicitud.PENDIENTE);
        }
        return solicitudAdopcionRepository.save(solicitudAdopcion);
    }

    public void eliminar(Long id) {
        solicitudAdopcionRepository.deleteById(id);
    }
}