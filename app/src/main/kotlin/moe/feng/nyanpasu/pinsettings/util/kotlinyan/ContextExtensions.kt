package moe.feng.nyanpasu.pinsettings.util.kotlinyan

import android.content.Context

interface ContextExtensions {

	fun Context.sharedPreferences(prefName: String, mode: Int = Context.MODE_PRIVATE)
			= GetSharedPreferences(this, prefName, mode)

}