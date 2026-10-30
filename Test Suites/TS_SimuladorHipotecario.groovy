import com.kms.katalon.core.annotation.TearDownTestCase
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// Cierra el navegador aunque el caso haya fallado a mitad del flujo
@TearDownTestCase(skipped = false)
def cerrarNavegador() {
	WebUI.closeBrowser(FailureHandling.OPTIONAL)
}
