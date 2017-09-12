package moe.feng.nyanpasu.pinsettings.util

import android.content.Context
import moe.feng.common.material.colorutils.ColorName
import moe.feng.nyanpasu.pinsettings.util.kotlinyan.SharedPreferencesProvider
import kotlin.properties.Delegates

class Preferences private constructor(context: Context): SharedPreferencesProvider(context, "pref") {

	var shouldShowTips by booleanValue(defValue = true)
	var shortcutColor by stringValue(defValue = ColorName.Teal.value)

}

var SettingsInstance: Preferences by Delegates.notNull()