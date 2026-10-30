# 📋 Descripción

**[Simulador Hipotecario] El envío de la simulación falla con "Error en el Envío": el servicio backend está deshabilitado (HTTP 403 "Site Disabled")**

**Severidad:** 1 - Crítica (bloqueante) · **Prioridad:** 1 · **Caso automatizado:** `TC07_Confirmacion` · **ID interno:** DEF-01

## 📍 Ubicación

**Módulo:** Simulador de Crédito Hipotecario: sección 7 "Confirmación"
**URL:** [https://brave-mud-0605ba20f.3.azurestaticapps.net/](https://brave-mud-0605ba20f.3.azurestaticapps.net/)
**Servicio afectado:** `POST https://simulador-credito-bancopichincha-back-hef4bsapdyhvgmcf.eastus2-01.azurewebsites.net/api/Simulacion/procesar`
**Ambiente:** Azure (Static Web App + App Service, East US 2)
**Navegador:** Chrome 156.0.8078.74 · Windows
**Fecha de detección:** 30-10-2026 (reproducido en 2 ejecuciones: 10:28 y 10:43)

---

### ✅ Comportamiento Esperado

Al marcar "Selecciono a toda la información está correcta y completa" y pulsar **Aceptar y Enviar**, la simulación se registra en el backend y el cliente recibe la confirmación de que será contactado.

---

### ❌ Comportamiento Encontrado

Se muestra el modal **"Error en el Envío"** con el mensaje:

```
Http failure response for https://simulador-credito-bancopichincha-back-hef4bsapdyhvgmcf.eastus2-01.azurewebsites.net/api/Simulacion/procesar: 0 Unknown Error
Respuesta del Servidor: { "isTrusted": true }
```

Al pulsar **Aceptar** en el modal, el asistente **vuelve a la sección 1** y se pierden todos los datos ingresados.

---

### 🎯 Pasos para Reproducir

1. Abrir `https://brave-mud-0605ba20f.3.azurestaticapps.net/`.
2. **Proyecto:** Individual, Casa, Quito y **Siguiente**.
3. **Finanzas:** Empleado, ingresos `3000`, ahorro `40000`, nacimiento `15/05/1990`, Soltero y **Siguiente**.
4. **Personaliza:** monto `150000`, `20 años`, cuota Fija y **Siguiente**.
5. **Resultados:** **Siguiente**.
6. **Capacidades:** nombre `Juan Pérez Prueba`, cédula `1710034065`, correo `juan.prueba@example.com`, celular `0991234567`, autorizar consulta, cargas `2`, empresa `Empresa Demo S.A.` y **Siguiente**.
7. **Contacto:** Mañana, Correo electrónico, aceptar política de privacidad y **Siguiente**.
8. **Confirmación:** marcar la casilla de confirmación y pulsar **Aceptar y Enviar**.
9. Observar el modal de error.

**Reproducibilidad:** 100 % (3 de 3 intentos: exploración manual y 2 ejecuciones automatizadas).

---

### ⚠️ Impacto

- **Ningún cliente puede completar una simulación.** El flujo de negocio está bloqueado.
- Se pierden los datos diligenciados en las 7 secciones, lo que obliga a empezar de nuevo.
- Bloquea la validación funcional de la sección Confirmación (`TC07_Confirmacion` en estado FAILED).

---

### 🔬 Análisis Técnico

1. **Consola del navegador:**
   ```
   Access to XMLHttpRequest at '.../api/Simulacion/procesar' from origin 'https://brave-mud-0605ba20f.3.azurestaticapps.net'
   has been blocked by CORS policy: Response to preflight request doesn't pass access control check:
   No 'Access-Control-Allow-Origin' header is present on the requested resource.
   net::ERR_FAILED
   ```
2. **Verificación directa del endpoint** (preflight `OPTIONS` con `Origin` del frontend), repetida a las 10:44:
   ```
   HTTP/1.1 403 Site Disabled
   ```
   La App Service del backend está **detenida o deshabilitada** (por ejemplo, por cuota agotada del plan o por detención manual). Por eso no responde con las cabeceras CORS, y el navegador lo reporta como error CORS con estado `0`.
3. El frontend sí construye y envía la petición: la consola muestra `=== INICIO ENVÍO ===` y `Enviando datos al servicio: {currentStep: 7, project, finance, personalize, results}`.

**Corrección sugerida:**
- Reactivar o iniciar la App Service `simulador-credito-bancopichincha-back-hef4bsapdyhvgmcf` y revisar la cuota y el estado del plan.
- Confirmar que la configuración CORS permite el origen `https://brave-mud-0605ba20f.3.azurestaticapps.net`.
- En el frontend, conservar los datos del asistente cuando falla el envío y permitir reintentarlo.

---

### 📸 Evidencias

- `Informes/2026-10-30_SimuladorHipotecario_Ejecucion2/evidencias/TC07_Confirmacion_antes_envio.png`
- `Informes/2026-10-30_SimuladorHipotecario_Ejecucion2/evidencias/TC07_Confirmacion_error_envio.png`
- Log paso a paso: `Informes/2026-10-30_SimuladorHipotecario_Ejecucion2/Reporte_detallado_Katalon.html`

**\*Adjuntar las imágenes de forma manual en el Work Item. 🫳**
