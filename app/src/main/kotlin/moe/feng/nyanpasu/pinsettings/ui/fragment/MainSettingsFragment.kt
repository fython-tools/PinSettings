package moe.feng.nyanpasu.pinsettings.ui.fragment

import android.os.Bundle
import moe.feng.nyanpasu.pinsettings.R

class MainSettingsFragment: AbsSettingsFragment() {

	override val preferenceResId: Int = R.xml.preference_main
	override val titleResId: Int = R.string.action_settings

	override fun onCreatePreferences(savedInstanceState: Bundle?) {

	}
	
}