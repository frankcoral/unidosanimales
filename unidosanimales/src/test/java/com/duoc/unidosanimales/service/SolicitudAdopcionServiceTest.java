package com.duoc.unidosanimales.service;

import com.duoc.unidosanimales.model.*;
import com.duoc.unidosanimales.repository.SolicitudAdopcionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SolicitudAdopcionServiceTest {

    @Mock
    private SolicitudAdopcionRepository solicitudAdopcionRepository;

    @InjectMocks
    private SolicitudAdopcionService solicitudAdopcionService;

    @Test
    void listarTodasDebeRetornarLista() {
        Mascota mascota = new Mascota(1L, "Luna", "Perro", "Mestizo", 2, "desc", "img", EstadoMascota.DISPONIBLE);
        List<SolicitudAdopcion> solicitudes = List.of(
                new SolicitudAdopcion(1L, "Juan", "juan@mail.com", "999999999", "Quiero adoptar", mascota, EstadoSolicitud.PENDIENTE)
        );

        when(solicitudAdopcionRepository.findAll()).thenReturn(solicitudes);

        List<SolicitudAdopcion> resultado = solicitudAdopcionService.listarTodas();

        assertEquals(1, resultado.size());
        verify(solicitudAdopcionRepository).findAll();
    }

    @Test
    void buscarPorIdDebeRetornarSolicitud() {
        Mascota mascota = new Mascota(1L, "Luna", "Perro", "Mestizo", 2, "desc", "img", EstadoMascota.DISPONIBLE);
        SolicitudAdopcion solicitud = new SolicitudAdopcion(1L, "Juan", "juan@mail.com", "999999999", "Quiero adoptar", mascota, EstadoSolicitud.PENDIENTE);

        when(solicitudAdopcionRepository.findById(1L)).thenReturn(Optional.of(solicitud));

        Optional<SolicitudAdopcion> resultado = solicitudAdopcionService.buscarPorId(1L);

        assertEquals(true, resultado.isPresent());
        verify(solicitudAdopcionRepository).findById(1L);
    }

    @Test
    void guardarDebeAsignarPendienteSiEstadoEsNull() {
        Mascota mascota = new Mascota(1L, "Luna", "Perro", "Mestizo", 2, "desc", "img", EstadoMascota.DISPONIBLE);
        SolicitudAdopcion solicitud = new SolicitudAdopcion();
        solicitud.setNombreSolicitante("Juan");
        solicitud.setCorreo("juan@mail.com");
        solicitud.setTelefono("999999999");
        solicitud.setMotivo("Quiero adoptar");
        solicitud.setMascota(mascota);

        when(solicitudAdopcionRepository.save(any(SolicitudAdopcion.class))).thenAnswer(invocation -> invocation.getArgument(0));

        SolicitudAdopcion resultado = solicitudAdopcionService.guardar(solicitud);

        assertEquals(EstadoSolicitud.PENDIENTE, resultado.getEstado());
        verify(solicitudAdopcionRepository).save(solicitud);
    }

    @Test
    void eliminarDebeLlamarDeleteById() {
        solicitudAdopcionService.eliminar(1L);

        verify(solicitudAdopcionRepository).deleteById(1L);
    }
}