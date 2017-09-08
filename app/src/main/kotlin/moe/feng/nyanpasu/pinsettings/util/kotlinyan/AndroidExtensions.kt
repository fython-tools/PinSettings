package moe.feng.nyanpasu.pinsettings.util.kotlinyan

import android.content.SharedPreferences

interface AndroidExtensions {

	operator fun SharedPreferences.get(key: String): SharedPreferencesGetter?
			= if (contains(key)) SharedPreferencesGetter(this, key) else null

	class SharedPreferencesGetter internal constructor(internal val sp: SharedPreferences, val key: String) {
		fun asString(defValue: String? = null): String? = sp.getString(key, defValue)
		fun asInt(defValue: Int = 0): Int = sp.getInt(key, defValue)
		fun asBoolean(defValue: Boolean = false): Boolean = sp.getBoolean(key, defValue)
		fun asLong(defValue: Long = 0): Long = sp.getLong(key, defValue)
		fun asFloat(defValue: Float = 0F): Float = sp.getFloat(key, defValue)
	}

	fun SharedPreferencesGetter?.asString(defValue: String? = null): String?
			= this?.sp?.getString(key, defValue) ?: defValue
	fun SharedPreferencesGetter?.asInt(defValue: Int = 0): Int
			= this?.sp?.getInt(key, defValue) ?: defValue
	fun SharedPreferencesGetter?.asBoolean(defValue: Boolean = false): Boolean
			= this?.sp?.getBoolean(key, defValue) ?: defValue
	fun SharedPreferencesGetter?.asLong(defValue: Long = 0): Long
			= this?.sp?.getLong(key, defValue) ?: defValue
	fun SharedPreferencesGetter?.asFloat(defValue: Float = 0F): Float
			= this?.sp?.getFloat(key, defValue) ?: defValue


	operator fun SharedPreferences.set(key: String, value: Any?) {
		when (value) {
			is String -> edit().putString(key, value).apply()
			is Int -> edit().putInt(key, value).apply()
			is Boolean -> edit().putBoolean(key, value).apply()
			is Long -> edit().putLong(key, value).apply()
			is Float -> edit().putFloat(key, value).apply()
		}
	}

}