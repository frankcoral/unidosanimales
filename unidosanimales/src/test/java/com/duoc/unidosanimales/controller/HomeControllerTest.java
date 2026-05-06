package com.duoc.unidosanimales.controller;

import com.duoc.unidosanimales.model.EstadoMascota;
import com.duoc.unidosanimales.model.Mascota;
import com.duoc.unidosanimales.model.SolicitudAdopcion;
import com.duoc.unidosanimales.service.MascotaService;
import com.duoc.unidosanimales.service.SolicitudAdopcionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HomeControllerTest {

    @Mock
    private MascotaService mascotaService;

    @Mock
    private SolicitudAdopcionService solicitudAdopcionService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private HomeController homeController;

    @Test
    void inicioDebeRetornarIndexYAgregarMascotas() {
        List<Mascota> mascotas = List.of(
                new Mascota(1L, "Luna", "Perro", "Mestizo", 2, "desc", "img", EstadoMascota.DISPONIBLE)
        );

        when(mascotaService.listarDisponibles()).thenReturn(mascotas);

        String vista = homeController.inicio(model);

        assertEquals("index", vista);
        verify(model).addAttribute("mascotas", mascotas);
    }

    @Test
    void listarMascotasDebeRetornarVistaMascotas() {
        List<Mascota> mascotas = List.of(
                new Mascota(1L, "Milo", "Gato", "Comun", 1, "desc", "img", EstadoMascota.DISPONIBLE)
        );

        when(mascotaService.listarDisponibles()).thenReturn(mascotas);

        String vista = homeController.listarMascotas(model);

        assertEquals("mascotas", vista);
        verify(model).addAttribute("mascotas", mascotas);
    }

    @Test
    void detalleMascotaDebeRetornarVistaDetalleCuandoExiste() {
        Mascota mascota = new Mascota(1L, "Luna", "Perro", "Mestizo", 2, "desc", "img", EstadoMascota.DISPONIBLE);

        when(mascotaService.buscarPorId(1L)).thenReturn(Optional.of(mascota));

        String vista = homeController.detalleMascota(1L, model);

        assertEquals("detalle-mascota", vista);
        verify(model).addAttribute("mascota", mascota);
    }

    @Test
    void detalleMascotaDebeRedirigirCuandoNoExiste() {
        when(mascotaService.buscarPorId(99L)).thenReturn(Optional.empty());

        String vista = homeController.detalleMascota(99L, model);

        assertEquals("redirect:/mascotas", vista);
    }

    @Test
    void formularioAdopcionDebeRetornarVistaCuandoMascotaExiste() {
        Mascota mascota = new Mascota(1L, "Luna", "Perro", "Mestizo", 2, "desc", "img", EstadoMascota.DISPONIBLE);

        when(mascotaService.buscarPorId(1L)).thenReturn(Optional.of(mascota));

        String vista = homeController.formularioAdopcion(1L, model);

        assertEquals("formulario-adopcion", vista);
        verify(model).addAttribute("mascota", mascota);
        verify(model).addAttribute(eq("solicitud"), any(SolicitudAdopcion.class));
    }

    @Test
    void formularioAdopcionDebeRedirigirCuandoMascotaNoExiste() {
        when(mascotaService.buscarPorId(99L)).thenReturn(Optional.empty());

        String vista = homeController.formularioAdopcion(99L, model);

        assertEquals("redirect:/mascotas", vista);
    }

    @Test
    void guardarSolicitudDebeGuardarYRedirigirCuandoNoHayErrores() {
        Mascota mascota = new Mascota(1L, "Luna", "Perro", "Mestizo", 2, "desc", "img", EstadoMascota.DISPONIBLE);
        SolicitudAdopcion solicitud = new SolicitudAdopcion();
        solicitud.setMascota(mascota);

        when(mascotaService.buscarPorId(1L)).thenReturn(Optional.of(mascota));
        when(bindingResult.hasErrors()).thenReturn(false);

        String vista = homeController.guardarSolicitud(solicitud, bindingResult, model);

        assertEquals("redirect:/?solicitudOk", vista);
        verify(solicitudAdopcionService).guardar(solicitud);
    }

    @Test
    void guardarSolicitudDebeRetornarFormularioCuandoHayErrores() {
        Mascota mascota = new Mascota(1L, "Luna", "Perro", "Mestizo", 2, "desc", "img", EstadoMascota.DISPONIBLE);
        SolicitudAdopcion solicitud = new SolicitudAdopcion();
        solicitud.setMascota(mascota);

        when(mascotaService.buscarPorId(1L)).thenReturn(Optional.of(mascota));
        when(bindingResult.hasErrors()).thenReturn(true);

        String vista = homeController.guardarSolicitud(solicitud, bindingResult, model);

        assertEquals("formulario-adopcion", vista);
        verify(solicitudAdopcionService, never()).guardar(any());
    }

    @Test
    void loginDebeRetornarVistaLogin() {
        String vista = homeController.login();

        assertEquals("login", vista);
    }
}