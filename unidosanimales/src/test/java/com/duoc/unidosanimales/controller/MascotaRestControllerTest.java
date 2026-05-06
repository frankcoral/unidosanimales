package com.duoc.unidosanimales.controller;

import com.duoc.unidosanimales.model.EstadoMascota;
import com.duoc.unidosanimales.model.Mascota;
import com.duoc.unidosanimales.service.MascotaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MascotaRestControllerTest {

    @Mock
    private MascotaService mascotaService;

    @InjectMocks
    private MascotaRestController mascotaRestController;

    @Test
    void listarDebeRetornarListaDeMascotas() {
        List<Mascota> mascotas = List.of(
                new Mascota(1L, "Luna", "Perro", "Mestizo", 2, "desc", "img", EstadoMascota.DISPONIBLE),
                new Mascota(2L, "Milo", "Gato", "Comun", 1, "desc", "img", EstadoMascota.DISPONIBLE)
        );

        when(mascotaService.listarTodas()).thenReturn(mascotas);

        List<Mascota> resultado = mascotaRestController.listar();

        assertEquals(2, resultado.size());
        verify(mascotaService).listarTodas();
    }
}