package moe.feng.nyanpasu.pinsettings.ui.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

abstract class AbsSettingsFragment: PreferenceFragment() {

	abstract protected val preferenceResId: Int

	abstract protected val titleResId: Int

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		addPreferencesFromResource(preferenceResId)
		onCreatePreferences(savedInstanceState)
	}

	abstract fun onCreatePreferences(savedInstanceState: Bundle?)

	override fun onAttach(context: Context?) {
		super.onAttach(context)
		if (context is Activity) {
			context.setTitle(titleResId)
		}
	}

	protected fun <T: Preference> bindPreference(key: String) = PreferenceProperty<T>(key)

	protected class PreferenceProperty<out T: Preference>(private val key: String)
		: ReadOnlyProperty<AbsSettingsFragment, T> {

		override fun getValue(thisRef: AbsSettingsFragment, property: KProperty<*>): T {
			return thisRef.findPreference(key) as T
		}

	}

}