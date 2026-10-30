import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import simulador.SimuladorPage as Sim

Sim.navegarHastaSeccion(3)
Sim.verificarTitulo('Personaliza tu crédito de vivienda')
Sim.verificarSiguienteDeshabilitado()

Sim.completarPersonaliza('120000', '15 años', 'variable')
Sim.verificarSiguienteHabilitado()
Sim.evidencia('Sección 3 - Personaliza diligenciada')

Sim.siguiente()
Sim.verificarSeccionActiva(4)
Sim.verificarTitulo('Tus resultados')

WebUI.closeBrowser()
