package com.duoc.unidosanimales.controller;

import com.duoc.unidosanimales.service.MascotaService;
import com.duoc.unidosanimales.service.SolicitudAdopcionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    private final MascotaService mascotaService;
    private final SolicitudAdopcionService solicitudAdopcionService;

    public AdminController(MascotaService mascotaService, SolicitudAdopcionService solicitudAdopcionService) {
        this.mascotaService = mascotaService;
        this.solicitudAdopcionService = solicitudAdopcionService;
    }

    @GetMapping("/admin")
    public String panelAdmin(Model model) {
        model.addAttribute("mascotas", mascotaService.listarTodas());
        model.addAttribute("solicitudes", solicitudAdopcionService.listarTodas());
        return "admin";
    }
}