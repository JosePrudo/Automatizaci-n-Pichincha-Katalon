import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import simulador.SimuladorPage as Sim

Sim.navegarHastaSeccion(2)
Sim.verificarTitulo('Tu situación financiera')
Sim.verificarSiguienteDeshabilitado()

Sim.completarFinanzas('independiente', '2500', '30000', '1985-08-20', 'Casado')
Sim.verificarSiguienteHabilitado()
Sim.evidencia('Sección 2 - Finanzas diligenciada')

Sim.siguiente()
Sim.verificarSeccionActiva(3)
Sim.verificarTitulo('Personaliza tu crédito de vivienda')

WebUI.closeBrowser()
