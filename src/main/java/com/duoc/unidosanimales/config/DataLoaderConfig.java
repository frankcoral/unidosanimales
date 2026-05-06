package com.duoc.unidosanimales.config;

import com.duoc.unidosanimales.model.*;
import com.duoc.unidosanimales.repository.MascotaRepository;
import com.duoc.unidosanimales.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoaderConfig {

    @Bean
    CommandLineRunner initData(MascotaRepository mascotaRepository,
                               UsuarioRepository usuarioRepository,
                               PasswordEncoder passwordEncoder) {
        return args -> {
            if (mascotaRepository.count() == 0) {
                mascotaRepository.save(new Mascota(null, "Luna", "Perro", "Mestizo", 2,
                        "Perrita tranquila y cariñosa, ideal para familias.",
                        "https://placehold.co/600x400?text=Luna", EstadoMascota.DISPONIBLE));

                mascotaRepository.save(new Mascota(null, "Milo", "Gato", "Común europeo", 1,
                        "Gatito juguetón y sociable.",
                        "https://placehold.co/600x400?text=Milo", EstadoMascota.DISPONIBLE));

                mascotaRepository.save(new Mascota(null, "Rocky", "Perro", "Labrador", 4,
                        "Perro activo, le gusta correr y jugar.",
                        "https://placehold.co/600x400?text=Rocky", EstadoMascota.RESERVADA));
            }

            if (usuarioRepository.count() == 0) {
                usuarioRepository.save(new Usuario(null, "admin",
                        passwordEncoder.encode("admin123"),
                        "admin@duoc.cl", RolUsuario.ADMIN, true));

                usuarioRepository.save(new Usuario(null, "coordinador",
                        passwordEncoder.encode("coord123"),
                        "coordinador@duoc.cl", RolUsuario.COORDINADOR, true));

                usuarioRepository.save(new Usuario(null, "usuario",
                        passwordEncoder.encode("user123"),
                        "usuario@duoc.cl", RolUsuario.USUARIO, true));
            }
        };
    }
}