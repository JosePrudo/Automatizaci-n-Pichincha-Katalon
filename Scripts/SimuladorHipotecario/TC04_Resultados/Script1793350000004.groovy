import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import simulador.SimuladorPage as Sim

Sim.navegarHastaSeccion(4)
Sim.verificarTitulo('Tus resultados')
Sim.verificarTexto('Aquí ve su cuota y los seguros')
WebUI.verifyElementPresent(Sim.boton('¿Quieres conocer tu capacidad real?'), Sim.timeout())
WebUI.verifyElementPresent(Sim.boton('Volver'), Sim.timeout())
Sim.evidencia('Sección 4 - Resultados')

// Defecto conocido DEF-02: se registra como advertencia para no bloquear el flujo básico
boolean muestraMonto = WebUI.verifyElementPresent(Sim.xpath('//main//*[contains(text(),"$")]'), 5, FailureHandling.OPTIONAL)
if (!muestraMonto) {
	KeywordUtil.markWarning('DEF-02: la sección Resultados no muestra la cuota mensual ni el costo de los seguros')
}

Sim.verificarSiguienteHabilitado()
Sim.siguiente()
Sim.verificarSeccionActiva(5)
Sim.verificarTitulo('Tu capacidad real')

WebUI.closeBrowser()
