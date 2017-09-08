package moe.feng.nyanpasu.pinsettings.util

import android.content.Context
import moe.feng.nyanpasu.pinsettings.util.kotlinyan.SharedPreferencesProvider

class Preferences private constructor(context: Context): SharedPreferencesProvider(context, "pref") {

	var shouldShowTips by booleanValue(defValue = true)

}