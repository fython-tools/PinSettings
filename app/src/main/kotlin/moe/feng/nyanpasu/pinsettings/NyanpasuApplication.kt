package moe.feng.nyanpasu.pinsettings

import android.app.Application
import moe.feng.common.material.colorutils.MaterialColors
import moe.feng.nyanpasu.pinsettings.util.SettingsInstance
import moe.feng.nyanpasu.pinsettings.util.kotlinyan.getSharedPreferencesProvider

class NyanpasuApplication: Application() {

	override fun onCreate() {
		super.onCreate()
		MaterialColors.init(this)
		SettingsInstance = getSharedPreferencesProvider()
	}

}