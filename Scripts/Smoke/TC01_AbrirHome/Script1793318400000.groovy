import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable

WebUI.openBrowser('')

WebUI.maximizeWindow()

WebUI.navigateToUrl(GlobalVariable.baseUrl)

WebUI.waitForPageLoad(GlobalVariable.timeout)

WebUI.click(findTestObject('Object Repository/Home/btn_AceptarCookies'), FailureHandling.OPTIONAL)

WebUI.verifyMatch(WebUI.getWindowTitle(), '.*Pichincha.*', true)

WebUI.closeBrowser()
