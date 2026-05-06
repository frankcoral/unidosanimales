package com.duoc.unidosanimales.service;

import com.duoc.unidosanimales.model.EstadoMascota;
import com.duoc.unidosanimales.model.Mascota;
import com.duoc.unidosanimales.repository.MascotaRepository;
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
class MascotaServiceTest {

    @Mock
    private MascotaRepository mascotaRepository;

    @InjectMocks
    private MascotaService mascotaService;

    @Test
    void listarTodasDebeRetornarLista() {
        List<Mascota> mascotas = List.of(
                new Mascota(1L, "Luna", "Perro", "Mestizo", 2, "desc", "img", EstadoMascota.DISPONIBLE)
        );

        when(mascotaRepository.findAll()).thenReturn(mascotas);

        List<Mascota> resultado = mascotaService.listarTodas();

        assertEquals(1, resultado.size());
        verify(mascotaRepository).findAll();
    }

    @Test
    void listarDisponiblesDebeRetornarDisponibles() {
        List<Mascota> mascotas = List.of(
                new Mascota(1L, "Milo", "Gato", "Comun", 1, "desc", "img", EstadoMascota.DISPONIBLE)
        );

        when(mascotaRepository.findByEstado(EstadoMascota.DISPONIBLE)).thenReturn(mascotas);

        List<Mascota> resultado = mascotaService.listarDisponibles();

        assertEquals(1, resultado.size());
        verify(mascotaRepository).findByEstado(EstadoMascota.DISPONIBLE);
    }

    @Test
    void buscarPorIdDebeRetornarMascota() {
        Mascota mascota = new Mascota(1L, "Luna", "Perro", "Mestizo", 2, "desc", "img", EstadoMascota.DISPONIBLE);

        when(mascotaRepository.findById(1L)).thenReturn(Optional.of(mascota));

        Optional<Mascota> resultado = mascotaService.buscarPorId(1L);

        assertEquals(true, resultado.isPresent());
        verify(mascotaRepository).findById(1L);
    }

    @Test
    void guardarDebePersistirMascota() {
        Mascota mascota = new Mascota(1L, "Luna", "Perro", "Mestizo", 2, "desc", "img", EstadoMascota.DISPONIBLE);

        when(mascotaRepository.save(mascota)).thenReturn(mascota);

        Mascota resultado = mascotaService.guardar(mascota);

        assertEquals("Luna", resultado.getNombre());
        verify(mascotaRepository).save(mascota);
    }

    @Test
    void eliminarDebeLlamarDeleteById() {
        mascotaService.eliminar(1L);

        verify(mascotaRepository).deleteById(1L);
    }
}