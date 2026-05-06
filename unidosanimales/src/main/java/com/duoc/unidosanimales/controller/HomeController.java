package com.duoc.unidosanimales.controller;

import com.duoc.unidosanimales.model.Mascota;
import com.duoc.unidosanimales.model.SolicitudAdopcion;
import com.duoc.unidosanimales.service.MascotaService;
import com.duoc.unidosanimales.service.SolicitudAdopcionService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class HomeController {

    private final MascotaService mascotaService;
    private final SolicitudAdopcionService solicitudAdopcionService;

    public HomeController(MascotaService mascotaService, SolicitudAdopcionService solicitudAdopcionService) {
        this.mascotaService = mascotaService;
        this.solicitudAdopcionService = solicitudAdopcionService;
    }

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("mascotas", mascotaService.listarDisponibles());
        return "index";
    }

    @GetMapping("/mascotas")
    public String listarMascotas(Model model) {
        model.addAttribute("mascotas", mascotaService.listarDisponibles());
        return "mascotas";
    }

    @GetMapping("/mascotas/{id}")
    public String detalleMascota(@PathVariable Long id, Model model) {
        Optional<Mascota> mascota = mascotaService.buscarPorId(id);
        if (mascota.isEmpty()) {
            return "redirect:/mascotas";
        }
        model.addAttribute("mascota", mascota.get());
        return "detalle-mascota";
    }

    @GetMapping("/adopcion/{id}")
    public String formularioAdopcion(@PathVariable Long id, Model model) {
        Optional<Mascota> mascota = mascotaService.buscarPorId(id);
        if (mascota.isEmpty()) {
            return "redirect:/mascotas";
        }

        SolicitudAdopcion solicitud = new SolicitudAdopcion();
        solicitud.setMascota(mascota.get());

        model.addAttribute("mascota", mascota.get());
        model.addAttribute("solicitud", solicitud);
        return "formulario-adopcion";
    }

    @PostMapping("/adopcion")
    public String guardarSolicitud(@Valid @ModelAttribute("solicitud") SolicitudAdopcion solicitud,
                                   BindingResult result,
                                   Model model) {
        if (solicitud.getMascota() != null && solicitud.getMascota().getId() != null) {
            Optional<Mascota> mascota = mascotaService.buscarPorId(solicitud.getMascota().getId());
            mascota.ifPresent(solicitud::setMascota);
            model.addAttribute("mascota", mascota.orElse(null));
        }

        if (result.hasErrors()) {
            return "formulario-adopcion";
        }

        solicitudAdopcionService.guardar(solicitud);
        return "redirect:/?solicitudOk";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}