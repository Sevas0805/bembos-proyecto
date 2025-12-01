# Bembos — Guía de Branding (guardada)

Este archivo guarda la información de branding proporcionada por el usuario. No se han aplicado cambios al código aún; esperar instrucciones adicionales antes de modificar archivos del proyecto.

---

Fecha: 2025-11-12

Origen del logo:
- Ruta local proporcionada por el usuario: `C:\Users\rodri\OneDrive\Escritorio\WEB INTEGRADOR\Front-0\bembos-front\Multimedia`

Paleta principal (valores aproximados proporcionados):
- Azul royal (color principal de fondo): #0014B8 / #0026FF (aprox.)
- Amarillo intenso (botones, acentos): #FFD200 / #FFC800 (aprox.)
- Rojo (logo y textos destacados): #E30613 / #FF1D25 (aprox.)
- Blanco: #FFFFFF (textos y equilibrio visual)
- Tonos crema/marrón: para elementos de alimentos (panes, salsas), mantener sensación cálida y apetitosa

Tipografía:
- Estilo: Sans serif gruesa y condensada, negrita, geométrica y ligeramente inclinada. Transmite dinamismo y fuerza.
- Nota: Si tienes el archivo de fuente (woff/woff2/ttf) súbelo a `Multimedia` o indícame la fuente exacta para incluirla en `assets` y en `styles`.

Requisitos de marca:
- Mantener la marca registrada: "Bembos®" donde corresponda.
- Usar el logo oficial ubicado en la ruta indicada (no sustituir sin confirmación).

Alcance acordado por ahora:
- El usuario desea también cambios de contenido (por ejemplo textos tipo "COMPRAR AQUÍ", promociones "HASTA 50% DTO.", rutas/páginas) además del rebranding visual.
- El usuario tiene una base de datos existente; espera dar más contexto antes de aplicar cambios funcionales o integraciones.

Próximos pasos (pendientes de confirmación del usuario):
1. Subir o confirmar los archivos del logo (SVG preferido) y de la tipografía (si aplica). Si no hay archivo, usar placeholder temporal.
2. Confirmar si quieres que cambie `package.json` name a `bembos-front` y el `<title>` en `src/index.html` a "Bembos®".
3. Permitir aplicar cambios: actualizar `src/styles.sass` con variables de color, añadir assets a `public/` y modificar `src/app` para textos/promociones.
4. Construir y probar localmente (instalación de dependencias y `npm start`).

Notas técnicas/consideraciones:
- Usaremos variables SASS para la paleta y clases utilitarias para botones (ej.: `.btn--primary` amarillo) y badges (rojo) para promociones.
- Para SSR no cambiaremos la configuración de `angular.json` hasta confirmar estructura de rutas y prioridad de SEO.

Si quieres que proceda ahora con cambios mínimos (placeholder + variables SASS + ejemplos de botones/textos), dilo y los aplico; si prefieres, espero a que subas assets y me des el detalle de cambios de contenido.
