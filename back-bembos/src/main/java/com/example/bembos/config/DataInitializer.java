package com.example.bembos.config;

import com.example.bembos.model.*;
import com.example.bembos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private TipoIngredienteRepository tipoIngredienteRepository;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private HamburguesaRepository hamburguesaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {
            System.out.println("===========================================");
            System.out.println("Verificando inicialización de datos...");
            
            long rolesCount = rolRepository.count();
            System.out.println("Roles encontrados: " + rolesCount);

            if (rolesCount == 0) {
                System.out.println("Inicializando datos de la base de datos (roles e ingredientes)...");
                // 1. Roles
                Rol rolCliente = new Rol();
                rolCliente.setNombre("ROLE_CLIENTE");
                rolRepository.save(rolCliente);

                Rol rolBuilder = new Rol();
                rolBuilder.setNombre("ROLE_BURGERBUILDER");
                rolRepository.save(rolBuilder);

                Rol rolAdmin = new Rol();
                rolAdmin.setNombre("ROLE_ADMINISTRADOR");
                rolRepository.save(rolAdmin);
                System.out.println("✓ Roles creados");

                // 2. Tipos ingrediente
                TipoIngrediente tipoPan = crearTipoIngrediente("Pan", "Diferentes tipos de pan");
                TipoIngrediente tipoCarne = crearTipoIngrediente("Carne", "Variedades de carne");
                TipoIngrediente tipoQueso = crearTipoIngrediente("Queso", "Diferentes quesos");
                TipoIngrediente tipoVegetales = crearTipoIngrediente("Vegetales", "Verduras y vegetales frescos");
                TipoIngrediente tipoSalsas = crearTipoIngrediente("Salsas", "Salsas y aderezos");
                TipoIngrediente tipoExtras = crearTipoIngrediente("Extras", "Ingredientes adicionales");
                System.out.println("✓ Tipos de ingredientes creados");

                // 3. Ingredientes (solo primera vez)
                crearIngrediente("Pan Clásico", 1.5, "Pan de hamburguesa tradicional", tipoPan);
                crearIngrediente("Pan Integral", 2.0, "Pan integral saludable", tipoPan);
                crearIngrediente("Pan Brioche", 2.5, "Pan brioche premium", tipoPan);
                crearIngrediente("Carne de Res", 6.0, "Carne 100% res", tipoCarne);
                crearIngrediente("Carne de Pollo", 5.0, "Pechuga de pollo jugosa", tipoCarne);
                crearIngrediente("Carne Vegetariana", 4.5, "Hamburguesa vegetal", tipoCarne);
                crearIngrediente("Queso Cheddar", 2.0, "Queso cheddar americano", tipoQueso);
                crearIngrediente("Queso Suizo", 2.5, "Queso suizo", tipoQueso);
                crearIngrediente("Queso Azul", 3.0, "Queso azul gourmet", tipoQueso);
                crearIngrediente("Lechuga", 0.5, "Lechuga fresca", tipoVegetales);
                crearIngrediente("Tomate", 0.5, "Tomate fresco en rodajas", tipoVegetales);
                crearIngrediente("Cebolla", 0.5, "Cebolla morada", tipoVegetales);
                crearIngrediente("Pepinillos", 0.8, "Pepinillos encurtidos", tipoVegetales);
                crearIngrediente("Aguacate", 2.0, "Aguacate fresco", tipoVegetales);
                crearIngrediente("Mayonesa", 0.5, "Mayonesa clásica", tipoSalsas);
                crearIngrediente("Ketchup", 0.5, "Salsa de tomate", tipoSalsas);
                crearIngrediente("Mostaza", 0.5, "Mostaza amarilla", tipoSalsas);
                crearIngrediente("Salsa BBQ", 1.0, "Salsa barbacoa", tipoSalsas);
                crearIngrediente("Salsa Especial", 1.5, "Salsa de la casa", tipoSalsas);
                crearIngrediente("Tocino", 2.5, "Tocino crocante", tipoExtras);
                crearIngrediente("Huevo Frito", 1.5, "Huevo frito", tipoExtras);
                crearIngrediente("Jalapeños", 1.0, "Chiles jalapeños", tipoExtras);
                crearIngrediente("Aros de Cebolla", 2.0, "Aros de cebolla fritos", tipoExtras);
                System.out.println("✓ Ingredientes creados");
            } else {
                System.out.println("BD con roles existentes: verificando ingredientes...");
                long ingredientesCount = ingredienteRepository.count();
                if (ingredientesCount == 0) {
                    System.out.println("Creando ingredientes...");
                    // 2. Tipos ingrediente
                    TipoIngrediente tipoPan = crearTipoIngrediente("Pan", "Diferentes tipos de pan");
                    TipoIngrediente tipoCarne = crearTipoIngrediente("Carne", "Variedades de carne");
                    TipoIngrediente tipoQueso = crearTipoIngrediente("Queso", "Diferentes quesos");
                    TipoIngrediente tipoVegetales = crearTipoIngrediente("Vegetales", "Verduras y vegetales frescos");
                    TipoIngrediente tipoSalsas = crearTipoIngrediente("Salsas", "Salsas y aderezos");
                    TipoIngrediente tipoExtras = crearTipoIngrediente("Extras", "Ingredientes adicionales");
                    System.out.println("✓ Tipos de ingredientes creados");

                    // 3. Ingredientes
                    crearIngrediente("Pan Clásico", 1.5, "Pan de hamburguesa tradicional", tipoPan);
                    crearIngrediente("Pan Integral", 2.0, "Pan integral saludable", tipoPan);
                    crearIngrediente("Pan Brioche", 2.5, "Pan brioche premium", tipoPan);
                    crearIngrediente("Carne de Res", 6.0, "Carne 100% res", tipoCarne);
                    crearIngrediente("Carne de Pollo", 5.0, "Pechuga de pollo jugosa", tipoCarne);
                    crearIngrediente("Carne Vegetariana", 4.5, "Hamburguesa vegetal", tipoCarne);
                    crearIngrediente("Queso Cheddar", 2.0, "Queso cheddar americano", tipoQueso);
                    crearIngrediente("Queso Suizo", 2.5, "Queso suizo", tipoQueso);
                    crearIngrediente("Queso Azul", 3.0, "Queso azul gourmet", tipoQueso);
                    crearIngrediente("Lechuga", 0.5, "Lechuga fresca", tipoVegetales);
                    crearIngrediente("Tomate", 0.5, "Tomate fresco en rodajas", tipoVegetales);
                    crearIngrediente("Cebolla", 0.5, "Cebolla morada", tipoVegetales);
                    crearIngrediente("Pepinillos", 0.8, "Pepinillos encurtidos", tipoVegetales);
                    crearIngrediente("Aguacate", 2.0, "Aguacate fresco", tipoVegetales);
                    crearIngrediente("Mayonesa", 0.5, "Mayonesa clásica", tipoSalsas);
                    crearIngrediente("Ketchup", 0.5, "Salsa de tomate", tipoSalsas);
                    crearIngrediente("Mostaza", 0.5, "Mostaza amarilla", tipoSalsas);
                    crearIngrediente("Salsa BBQ", 1.0, "Salsa barbacoa", tipoSalsas);
                    crearIngrediente("Salsa Especial", 1.5, "Salsa de la casa", tipoSalsas);
                    crearIngrediente("Tocino", 2.5, "Tocino crocante", tipoExtras);
                    crearIngrediente("Huevo Frito", 1.5, "Huevo frito", tipoExtras);
                    crearIngrediente("Jalapeños", 1.0, "Chiles jalapeños", tipoExtras);
                    crearIngrediente("Aros de Cebolla", 2.0, "Aros de cebolla fritos", tipoExtras);
                    System.out.println("✓ Ingredientes creados");
                }
            }

            // 4. Usuarios siempre verificados
            crearUsuarioSiNoExiste("demo@bembos.com", "Demo", "User", "Demo1234!", "ROLE_CLIENTE");
            crearUsuarioSiNoExiste("test@bembos.com", "Test", "User", "Test1234!", "ROLE_CLIENTE");
            crearUsuarioSiNoExiste("admin@bembos.com", "Admin", "Bembos", "Admin1234!", "ROLE_ADMINISTRADOR");

            System.out.println("✓ Usuarios de prueba verificados/creados");

            // 5. Hamburguesas de prueba
            System.out.println("Verificando hamburguesas...");
            if (ingredienteRepository.count() >= 23) {
                System.out.println("Ingredientes disponibles, creando hamburguesas de ejemplo...");
                try {
                    // Borrar hamburguesas existentes
                    hamburguesaRepository.deleteAll();
                    System.out.println("✓ Tabla limpiada");
                    
                    crearHamburguesaClasica();
                    crearHamburguesaBBQ();
                    crearHamburguesaVegetariana();
                    System.out.println("✓ 3 Hamburguesas de prueba creadas");
                } catch (Exception e) {
                    System.err.println("Error creando hamburguesas: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.out.println("⚠ Faltan ingredientes para crear hamburguesas");
            }

            System.out.println("===========================================");
            System.out.println("✅ Inicialización de datos completada");
            System.out.println("===========================================");
        };
    }

    private TipoIngrediente crearTipoIngrediente(String nombre, String descripcion) {
        TipoIngrediente tipo = new TipoIngrediente();
        tipo.setNombre(nombre);
        tipo.setDescripcion(descripcion);
        return tipoIngredienteRepository.save(tipo);
    }

    private void crearIngrediente(String nombre, double precio, String descripcion, TipoIngrediente tipo) {
        Ingrediente ing = new Ingrediente();
        ing.setNombre(nombre);
        ing.setPrecio(precio);
        ing.setDescripcion(descripcion);
        ing.setTipoIngrediente(tipo);
        ingredienteRepository.save(ing);
    }

    private void crearUsuarioSiNoExiste(String email, String nombre, String apellido, String passwordPlano, String nombreRol) {
        Rol rol = rolRepository.findByNombre(nombreRol).orElse(null);
        usuarioRepository.findByEmail(email).ifPresentOrElse(existing -> {
            if (existing.getRoles() == null || existing.getRoles().isEmpty()) {
                if (rol != null) {
                    existing.getRoles().add(rol);
                    usuarioRepository.save(existing);
                    System.out.println("  - Rol asignado a usuario existente: " + email);
                } else {
                    System.out.println("  - Usuario existente sin rol porque " + nombreRol + " no se encontró: " + email);
                }
            } else {
                System.out.println("  - Usuario ya existe con roles: " + email);
            }
        }, () -> {
            Usuario u = new Usuario();
            u.setNombre(nombre);
            u.setApellido(apellido);
            u.setEmail(email);
            u.setPassword(passwordEncoder.encode(passwordPlano));
            if (rol != null) {
                u.getRoles().add(rol);
            }
            usuarioRepository.save(u);
            System.out.println("  - Usuario creado: " + email + (rol != null ? " con rol " + nombreRol : " (sin rol por no existir " + nombreRol + ")"));
        });
    }

    private void crearHamburguesaClasica() {
        Hamburguesa h = new Hamburguesa();
        h.setNombre("Clásica Bembos");
        h.setPrecioTotal(15.90);
        h.setImagenUrl("/Multimedia/hamburguesa-clasica.jpg");
        
        // Ingredientes
        Ingrediente panClasico = ingredienteRepository.findById(1L).orElse(null);
        Ingrediente carneRes = ingredienteRepository.findById(4L).orElse(null);
        Ingrediente queso = ingredienteRepository.findById(7L).orElse(null);
        Ingrediente lechuga = ingredienteRepository.findById(10L).orElse(null);
        Ingrediente tomate = ingredienteRepository.findById(11L).orElse(null);
        Ingrediente mayo = ingredienteRepository.findById(15L).orElse(null);
        
        if (panClasico != null) h.getIngredientes().add(panClasico);
        if (carneRes != null) h.getIngredientes().add(carneRes);
        if (queso != null) h.getIngredientes().add(queso);
        if (lechuga != null) h.getIngredientes().add(lechuga);
        if (tomate != null) h.getIngredientes().add(tomate);
        if (mayo != null) h.getIngredientes().add(mayo);
        
        hamburguesaRepository.save(h);
    }

    private void crearHamburguesaBBQ() {
        Hamburguesa h = new Hamburguesa();
        h.setNombre("BBQ Bacon");
        h.setPrecioTotal(19.90);
        h.setImagenUrl("/Multimedia/hamburguesa-bbq.jpg");
        
        Ingrediente panBrioche = ingredienteRepository.findById(3L).orElse(null);
        Ingrediente carneRes = ingredienteRepository.findById(4L).orElse(null);
        Ingrediente queso = ingredienteRepository.findById(7L).orElse(null);
        Ingrediente cebolla = ingredienteRepository.findById(12L).orElse(null);
        Ingrediente salsaBBQ = ingredienteRepository.findById(18L).orElse(null);
        Ingrediente tocino = ingredienteRepository.findById(20L).orElse(null);
        
        if (panBrioche != null) h.getIngredientes().add(panBrioche);
        if (carneRes != null) h.getIngredientes().add(carneRes);
        if (queso != null) h.getIngredientes().add(queso);
        if (cebolla != null) h.getIngredientes().add(cebolla);
        if (salsaBBQ != null) h.getIngredientes().add(salsaBBQ);
        if (tocino != null) h.getIngredientes().add(tocino);
        
        hamburguesaRepository.save(h);
    }

    private void crearHamburguesaVegetariana() {
        Hamburguesa h = new Hamburguesa();
        h.setNombre("Veggie Deluxe");
        h.setPrecioTotal(14.90);
        h.setImagenUrl("/Multimedia/hamburguesa-veggie.jpg");
        
        Ingrediente panIntegral = ingredienteRepository.findById(2L).orElse(null);
        Ingrediente carneVegetariana = ingredienteRepository.findById(6L).orElse(null);
        Ingrediente lechuga = ingredienteRepository.findById(10L).orElse(null);
        Ingrediente tomate = ingredienteRepository.findById(11L).orElse(null);
        Ingrediente aguacate = ingredienteRepository.findById(14L).orElse(null);
        Ingrediente mayo = ingredienteRepository.findById(15L).orElse(null);
        
        if (panIntegral != null) h.getIngredientes().add(panIntegral);
        if (carneVegetariana != null) h.getIngredientes().add(carneVegetariana);
        if (lechuga != null) h.getIngredientes().add(lechuga);
        if (tomate != null) h.getIngredientes().add(tomate);
        if (aguacate != null) h.getIngredientes().add(aguacate);
        if (mayo != null) h.getIngredientes().add(mayo);
        
        hamburguesaRepository.save(h);
    }
}
