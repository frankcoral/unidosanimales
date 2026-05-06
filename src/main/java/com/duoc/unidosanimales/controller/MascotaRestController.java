package com.duoc.unidosanimales.controller;

import com.duoc.unidosanimales.model.Mascota;
import com.duoc.unidosanimales.service.MascotaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaRestController {

    private final MascotaService mascotaService;

    public MascotaRestController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @GetMapping
    public List<Mascota> listar() {
        return mascotaService.listarTodas();
    }
}