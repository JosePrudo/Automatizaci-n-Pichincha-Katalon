package simulador

import org.openqa.selenium.Keys

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.SelectorMethod
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

/**
 * Page object del asistente "Tu Crédito de Vivienda" (7 secciones).
 */
class SimuladorPage {

	static final Map<Integer, String> SECCIONES = [
		1: 'Proyecto', 2: 'Finanzas', 3: 'Personaliza', 4: 'Resultados',
		5: 'Capacidades', 6: 'Contacto', 7: 'Confirmación'
	]

	static TestObject css(String selector) {
		TestObject to = new TestObject(selector)
		to.setSelectorValue(SelectorMethod.CSS, selector)
		to.setSelectorMethod(SelectorMethod.CSS)
		return to
	}

	static TestObject xpath(String expr) {
		TestObject to = new TestObject(expr)
		to.setSelectorValue(SelectorMethod.XPATH, expr)
		to.setSelectorMethod(SelectorMethod.XPATH)
		return to
	}

	static TestObject boton(String texto) {
		return xpath("//main//button[normalize-space()='${texto}']")
	}

	static int timeout() {
		return GlobalVariable.timeout as int
	}

	// ---------- Navegación y verificaciones comunes ----------

	static void abrir() {
		WebUI.openBrowser('')
		WebUI.setViewPortSize(1366, 900)
		WebUI.navigateToUrl(GlobalVariable.urlSimulador)
		WebUI.waitForPageLoad(timeout())
		WebUI.verifyElementPresent(xpath("//h1[normalize-space()='Tu Credito de Vivienda']"), timeout())
	}

	static void verificarSeccionActiva(int numero) {
		// El círculo del paso activo usa el color corporativo #0f265c
		TestObject activo = xpath("//app-wizard-stepper//div[contains(@class,'rounded-full') and contains(@class,'bg-[#0f265c]') and normalize-space()='${numero}']")
		WebUI.verifyElementPresent(activo, timeout())
		KeywordUtil.logInfo("Sección activa: ${numero} - ${SECCIONES[numero]}")
	}

	static void verificarTitulo(String titulo) {
		WebUI.verifyElementPresent(xpath("//main//h2[normalize-space()='${titulo}']"), timeout())
	}

	static void verificarTexto(String texto) {
		WebUI.verifyElementPresent(xpath("//main//*[contains(normalize-space(),\"${texto}\")]"), timeout())
	}

	static void verificarSiguienteDeshabilitado() {
		WebUI.verifyElementHasAttribute(boton('Siguiente'), 'disabled', timeout())
	}

	static void verificarSiguienteHabilitado() {
		WebUI.verifyElementNotHasAttribute(boton('Siguiente'), 'disabled', timeout())
	}

	static void siguiente() {
		WebUI.waitForElementClickable(boton('Siguiente'), timeout())
		WebUI.click(boton('Siguiente'))
	}

	static void evidencia(String nombre) {
		WebUI.takeScreenshot(FailureHandling.OPTIONAL)
		KeywordUtil.logInfo("Evidencia capturada: ${nombre}")
	}

	static void escribirMoneda(TestObject campo, String valor) {
		WebUI.setText(campo, valor)
		// El formato de moneda se aplica al perder el foco
		WebUI.sendKeys(campo, Keys.chord(Keys.TAB))
	}

	// ---------- Sección 1: Proyecto ----------

	static void completarProyecto(String solicitante, String vivienda, String ciudad) {
		WebUI.click(css("#${solicitante}"))
		WebUI.click(css("#${vivienda}"))
		WebUI.selectOptionByLabel(css('main select'), ciudad, false)
	}

	// ---------- Sección 2: Finanzas ----------

	static void completarFinanzas(String situacionLaboral, String ingresos, String ahorro, String fechaIso, String estadoCivil) {
		WebUI.click(css("#${situacionLaboral}"))
		escribirMoneda(css("input[placeholder='Ingresos netos']"), ingresos)
		escribirMoneda(css("input[placeholder='Total ahorrado']"), ahorro)
		// input type=date depende del locale del navegador: se asigna por JS y se notifica a Angular
		WebUI.executeJavaScript("""
			const e = document.querySelector("main input[type='date']");
			e.value = arguments[0];
			e.dispatchEvent(new Event('input', {bubbles: true}));
			e.dispatchEvent(new Event('change', {bubbles: true}));
			e.dispatchEvent(new Event('blur'));
		""", [fechaIso])
		WebUI.selectOptionByLabel(css('main select'), estadoCivil, false)
	}

	// ---------- Sección 3: Personaliza ----------

	static void completarPersonaliza(String monto, String plazo, String tipoCuota) {
		escribirMoneda(css("input[placeholder='Monto total del préstamo']"), monto)
		WebUI.selectOptionByLabel(css('main select'), plazo, false)
		WebUI.click(css("#${tipoCuota}"))
	}

	// ---------- Sección 5: Capacidades ----------

	static void completarCapacidades(String nombre, String cedula, String email, String telefono, String cargas, String empresa) {
		WebUI.setText(css("input[placeholder='Nombre completo']"), nombre)
		WebUI.setText(css("input[placeholder='Documento de identidad']"), cedula)
		WebUI.setText(css("main input[type='email']"), email)
		WebUI.setText(css("main input[type='tel']"), telefono)
		WebUI.check(css('#authorizeCreditCheck'))
		WebUI.setText(css("input[placeholder='Cargas familiares']"), cargas)
		WebUI.setText(css("input[placeholder='Compañía de tecnología S.A.S']"), empresa)
	}

	// ---------- Sección 6: Contacto ----------

	static void completarContacto(String horario, String medio) {
		WebUI.click(xpath("//main//input[@name='preferredTime' and @value='${horario}']"))
		WebUI.click(xpath("//main//input[@name='contactMethod' and @value='${medio}']"))
		WebUI.check(css('#acceptsPrivacyPolicy'))
	}

	// ---------- Datos por defecto para llegar a una sección ----------

	static void navegarHastaSeccion(int destino) {
		abrir()
		if (destino > 1) {
			completarProyecto('individual', 'casa', 'Quito')
			siguiente()
		}
		if (destino > 2) {
			verificarSeccionActiva(2)
			completarFinanzas('empleado', '3000', '40000', '1990-05-15', 'Soltero')
			siguiente()
		}
		if (destino > 3) {
			verificarSeccionActiva(3)
			completarPersonaliza('150000', '20 años', 'fija')
			siguiente()
		}
		if (destino > 4) {
			verificarSeccionActiva(4)
			siguiente()
		}
		if (destino > 5) {
			verificarSeccionActiva(5)
			completarCapacidades('Juan Pérez Prueba', '1710034065', 'juan.prueba@example.com', '0991234567', '2', 'Empresa Demo S.A.')
			siguiente()
		}
		if (destino > 6) {
			verificarSeccionActiva(6)
			completarContacto('mañana', 'correo')
			siguiente()
		}
		verificarSeccionActiva(destino)
	}

	static List<String> valoresResumen() {
		return WebUI.executeJavaScript("return [...document.querySelectorAll('main input[type=text]')].map(e => e.value);", null) as List<String>
	}
}
