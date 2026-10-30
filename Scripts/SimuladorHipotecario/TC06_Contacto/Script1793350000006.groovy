import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import simulador.SimuladorPage as Sim

Sim.navegarHastaSeccion(6)
Sim.verificarTexto('¿Cuál es el mejor horario para llamarte?')
Sim.verificarTexto('¿Cómo prefieres que te contactemos?')

Sim.completarContacto('tarde', 'whatsapp')
Sim.verificarSiguienteHabilitado()
Sim.evidencia('Sección 6 - Contacto diligenciada')

Sim.siguiente()
Sim.verificarSeccionActiva(7)
Sim.verificarTitulo('Confirmación')

WebUI.closeBrowser()
