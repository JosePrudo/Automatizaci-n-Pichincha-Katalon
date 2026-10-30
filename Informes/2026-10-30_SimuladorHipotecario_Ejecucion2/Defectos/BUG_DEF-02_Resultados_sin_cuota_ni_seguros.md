# 📋 Descripción

**[Simulador Hipotecario] La sección "Resultados" no muestra la cuota mensual ni el costo de los seguros (valores calculados en 0)**

**Severidad:** 2 - Alta · **Prioridad:** 1 · **Caso automatizado:** `TC04_Resultados` (registrado como advertencia) · **ID interno:** DEF-02

## 📍 Ubicación

**Módulo:** Simulador de Crédito Hipotecario: sección 4 "Resultados"
**URL:** [https://brave-mud-0605ba20f.3.azurestaticapps.net/](https://brave-mud-0605ba20f.3.azurestaticapps.net/)
**Ambiente:** Azure Static Web App
**Navegador:** Chrome 156.0.8078.74 · Windows
**Fecha de detección:** 30-10-2026 (reproducido en 2 ejecuciones: 10:26 y 10:41)

---

### ✅ Comportamiento Esperado

Tras completar Proyecto, Finanzas y Personaliza, la sección **"Tus resultados"** muestra, según su propio texto ("Aquí ve su cuota y los seguros"):
- la cuota mensual calculada,
- el costo de los seguros,
- los totales del crédito (monto total, intereses y relación préstamo/valor).

---

### ❌ Comportamiento Encontrado

La sección solo muestra el título **"Tus resultados"**, el texto **"Aquí ve su cuota y los seguros"** y los botones. **No aparece ningún valor monetario.**

---

### 🎯 Pasos para Reproducir

1. Abrir `https://brave-mud-0605ba20f.3.azurestaticapps.net/`.
2. **Proyecto:** Individual, Casa, Quito y **Siguiente**.
3. **Finanzas:** Empleado, ingresos `3000`, ahorro `40000`, nacimiento `15/05/1990`, Soltero y **Siguiente**.
4. **Personaliza:** monto `150000`, `20 años`, cuota Fija y **Siguiente**.
5. Observar la sección **Resultados**.

**Reproducibilidad:** 100 % (3 de 3 intentos).

---

### ⚠️ Impacto

El cliente no obtiene la información principal del simulador (cuánto pagará al mes y el costo de los seguros), por lo que no puede evaluar el crédito antes de dejar sus datos de contacto.

---

### 🔬 Análisis Técnico

La consola del navegador muestra que el componente avanza con todos los resultados en cero:

```
Resultados completados: {monthlyPayment: 0, totalAmount: 0, totalInterest: 0, loanToValue: 0, insuranceCost: 0}
```

Los datos de entrada sí llegan correctamente a los pasos anteriores:

```
Datos de personalización: {loanAmount: 150000, loanTermYears: 20, paymentType: fija}
```

**Hipótesis:** el cálculo no se ejecuta en el frontend o depende de una respuesta del backend que no llega (ver DEF-01: la App Service está deshabilitada). Además, la vista no tiene enlaces de datos para mostrar los valores.

**Corrección sugerida:**
- Revisar el servicio o método que calcula `monthlyPayment` e `insuranceCost` y su invocación al entrar a la sección 4.
- Si depende del backend, mostrar un mensaje de error o de carga en lugar de una sección vacía.
- Verificar el binding de la plantilla de la sección Resultados.

---

### 📸 Evidencias

- `Informes/2026-10-30_SimuladorHipotecario_Ejecucion2/evidencias/TC04_Resultados.png`
- Log paso a paso: `Informes/2026-10-30_SimuladorHipotecario_Ejecucion2/Reporte_detallado_Katalon.html`

**\*Adjuntar las imágenes de forma manual en el Work Item. 🫳**
