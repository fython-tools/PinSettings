package moe.feng.nyanpasu.pinsettings.ui.fragment

import android.os.Bundle
import moe.feng.common.material.colorutils.MaterialColors
import moe.feng.nyanpasu.pinsettings.R
import moe.feng.nyanpasu.pinsettings.preference.ColorPickerPreference
import moe.feng.nyanpasu.pinsettings.util.SettingsInstance

class MainSettingsFragment: AbsSettingsFragment() {

	override val preferenceResId: Int = R.xml.preference_main
	override val titleResId: Int = R.string.action_settings

	private val colorPickerPref by bindPreference<ColorPickerPreference>("shortcut_color")

	override fun onCreatePreferences(savedInstanceState: Bundle?) {
		colorPickerPref.setCheckedColor(MaterialColors.parseStringToColor(SettingsInstance.shortcutColor!!))
		colorPickerPref.pickerCallback = {
			SettingsInstance.shortcutColor = it.value
		}
	}

}