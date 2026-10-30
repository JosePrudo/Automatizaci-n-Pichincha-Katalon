import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import simulador.SimuladorPage as Sim

Sim.navegarHastaSeccion(5)
Sim.verificarTitulo('Tu capacidad real')

Sim.completarCapacidades('María López Prueba', '1710034065', 'maria.prueba@example.com', '0987654321', '1', 'Tecnología Demo S.A.')
Sim.verificarSiguienteHabilitado()
Sim.evidencia('Sección 5 - Capacidades diligenciada')

Sim.siguiente()
Sim.verificarSeccionActiva(6)
Sim.verificarTexto('¿Cómo prefieres que te contactemos?')

WebUI.closeBrowser()
