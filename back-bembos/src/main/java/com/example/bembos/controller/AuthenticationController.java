package com.example.bembos.controller;

import com.example.bembos.model.Usuario;
import com.example.bembos.service.UsuarioService;
import com.example.bembos.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/api/usuario/authenticate")
    public ResponseEntity<?> createAuthenticationToken(
            @RequestParam String email,
            @RequestParam String password) {
        try {
            // Log de depuración
            System.out.println("Intentando autenticar usuario: " + email);

            // Intento de autenticación
            var auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));

            if (!auth.isAuthenticated()) {
                System.out.println("Autenticación fallida para: " + email);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Credenciales inválidas");
            }

            final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            Usuario usuario = usuarioService.getUsuarioByEmail(email);

            // Obtener roles y verificar
            final List<String> roles = usuario.getRoles().stream()
                    .map(r -> r.getNombre())
                    .collect(Collectors.toList());

            if (roles.isEmpty()) {
                System.out.println("No se encontraron roles para: " + email);
                throw new RuntimeException("Usuario sin roles asignados");
            }

            // Generar token
            final String jwt = jwtUtil.generateToken(
                    usuario.getEmail(),
                    roles,
                    usuario.getNombre(),
                    usuario.getApellido()
            );

            // Log de éxito
            System.out.println("Autenticación exitosa para: " + email);
            System.out.println("Roles asignados: " + roles);

            var response = new HashMap<String, Object>();
            response.put("token", jwt);
            response.put("roles", roles);
            response.put("email", email);
            response.put("nombre", usuario.getNombre());
            response.put("apellido", usuario.getApellido());

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            System.out.println("Credenciales incorrectas para: " + email);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales inválidas");
        } catch (Exception e) {
            System.err.println("Error de autenticación para: " + email);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error de autenticación: " + e.getMessage());
        }
    }
}