package moe.feng.nyanpasu.pinsettings.ui.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.preference.PreferenceFragment

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

}