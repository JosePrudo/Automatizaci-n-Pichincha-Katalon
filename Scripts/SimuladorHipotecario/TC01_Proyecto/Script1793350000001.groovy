import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import simulador.SimuladorPage as Sim

Sim.abrir()
Sim.verificarSeccionActiva(1)
Sim.verificarTitulo('Tu proyecto de vivienda')
Sim.verificarSiguienteDeshabilitado()

Sim.completarProyecto('individual', 'casa', 'Quito')
Sim.verificarSiguienteHabilitado()
Sim.evidencia('Sección 1 - Proyecto diligenciada')

Sim.siguiente()
Sim.verificarSeccionActiva(2)
Sim.verificarTitulo('Tu situación financiera')

WebUI.closeBrowser()
