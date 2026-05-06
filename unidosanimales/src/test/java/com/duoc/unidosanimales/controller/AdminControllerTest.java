package com.duoc.unidosanimales.controller;

import com.duoc.unidosanimales.service.MascotaService;
import com.duoc.unidosanimales.service.SolicitudAdopcionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private MascotaService mascotaService;

    @Mock
    private SolicitudAdopcionService solicitudAdopcionService;

    @Mock
    private Model model;

    @InjectMocks
    private AdminController adminController;

    @Test
    void panelAdminDebeRetornarVistaAdminYAgregarDatos() {
        when(mascotaService.listarTodas()).thenReturn(Collections.emptyList());
        when(solicitudAdopcionService.listarTodas()).thenReturn(Collections.emptyList());

        String vista = adminController.panelAdmin(model);

        assertEquals("admin", vista);
        verify(model).addAttribute("mascotas", Collections.emptyList());
        verify(model).addAttribute("solicitudes", Collections.emptyList());
    }
}