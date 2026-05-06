package com.duoc.unidosanimales.service;

import com.duoc.unidosanimales.model.RolUsuario;
import com.duoc.unidosanimales.model.Usuario;
import com.duoc.unidosanimales.repository.UsuarioRepository;
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
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void listarTodosDebeRetornarLista() {
        List<Usuario> usuarios = List.of(
                new Usuario(1L, "admin", "123", "admin@duoc.cl", RolUsuario.ADMIN, true)
        );

        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<Usuario> resultado = usuarioService.listarTodos();

        assertEquals(1, resultado.size());
        verify(usuarioRepository).findAll();
    }

    @Test
    void buscarPorIdDebeRetornarUsuario() {
        Usuario usuario = new Usuario(1L, "admin", "123", "admin@duoc.cl", RolUsuario.ADMIN, true);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.buscarPorId(1L);

        assertEquals(true, resultado.isPresent());
        verify(usuarioRepository).findById(1L);
    }

    @Test
    void buscarPorUsernameDebeRetornarUsuario() {
        Usuario usuario = new Usuario(1L, "admin", "123", "admin@duoc.cl", RolUsuario.ADMIN, true);

        when(usuarioRepository.findByUsername("admin")).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.buscarPorUsername("admin");

        assertEquals(true, resultado.isPresent());
        verify(usuarioRepository).findByUsername("admin");
    }

    @Test
    void guardarDebePersistirUsuario() {
        Usuario usuario = new Usuario(1L, "admin", "123", "admin@duoc.cl", RolUsuario.ADMIN, true);

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioService.guardar(usuario);

        assertEquals("admin", resultado.getUsername());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void eliminarDebeLlamarDeleteById() {
        usuarioService.eliminar(1L);

        verify(usuarioRepository).deleteById(1L);
    }
}