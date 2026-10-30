import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import simulador.SimuladorPage as Sim

Sim.navegarHastaSeccion(7)
Sim.verificarTitulo('Confirmación')
Sim.verificarTexto('Código de tu simulación')

List<String> resumen = Sim.valoresResumen()
KeywordUtil.logInfo("Valores del resumen: ${resumen}")
List<String> esperados = ['Quito', 'Juan Pérez Prueba', '1710034065', 'juan.prueba@example.com', '0991234567', 'Empresa Demo S.A.']
List<String> faltantes = esperados.findAll { !resumen.contains(it) }
if (faltantes) {
	KeywordUtil.markFailed("El resumen no refleja los datos ingresados: ${faltantes}")
}

WebUI.verifyElementHasAttribute(Sim.boton('Aceptar y Enviar'), 'disabled', Sim.timeout())
WebUI.check(Sim.css('#confirmInformation'))
WebUI.verifyElementNotHasAttribute(Sim.boton('Aceptar y Enviar'), 'disabled', Sim.timeout())
Sim.evidencia('Sección 7 - Confirmación antes de enviar')

WebUI.click(Sim.boton('Aceptar y Enviar'))

boolean hayError = WebUI.waitForElementVisible(Sim.xpath("//*[normalize-space(text())='Error en el Envío']"), 15, FailureHandling.OPTIONAL)
Sim.evidencia('Sección 7 - Resultado del envío')
if (hayError) {
	String detalle = WebUI.getText(Sim.xpath("//*[contains(text(),'Http failure')]"), FailureHandling.OPTIONAL)
	KeywordUtil.markFailed("El envío de la simulación falló: ${detalle}")
}

WebUI.closeBrowser()
