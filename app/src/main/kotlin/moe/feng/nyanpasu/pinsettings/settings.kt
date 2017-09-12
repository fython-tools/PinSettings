package moe.feng.nyanpasu.pinsettings

import android.os.Build
import android.provider.Settings
import moe.feng.nyanpasu.pinsettings.ui.adapter.ModelBindAdapter

val SETTINGS_ITEMS = mutableListOf(
		Settings.ACTION_WIFI_SETTINGS.createItem(R.string.wifi_settings, R.drawable.ic_signal_wifi_4_bar_black_24dp),
		Settings.ACTION_WIFI_IP_SETTINGS.createItem(R.string.wifi_ip_settings, R.drawable.ic_perm_scan_wifi_black_24dp),
		Settings.ACTION_BLUETOOTH_SETTINGS.createItem(R.string.bluetooth_settings, R.drawable.ic_bluetooth_black_24dp),
		Settings.ACTION_WIRELESS_SETTINGS.createItem(R.string.wireless_settings, R.drawable.ic_network_cell_black_24dp),
		Settings.ACTION_NFC_SETTINGS.createItem(R.string.nfc_settings, R.drawable.ic_nfc_black_24dp),
		Settings.ACTION_NFC_PAYMENT_SETTINGS.createItem(R.string.nfc_payment_settings, R.drawable.ic_payment_black_24dp),
		Settings.ACTION_CAST_SETTINGS.createItem(R.string.cast_settings, R.drawable.ic_cast_black_24dp),
		Settings.ACTION_DISPLAY_SETTINGS.createItem(R.string.display_settings, R.drawable.ic_brightness_medium_black_24dp),
		Settings.ACTION_NIGHT_DISPLAY_SETTINGS.createItem(R.string.night_mode_settings, R.drawable.ic_brightness_2_black_24dp),
		Settings.ACTION_HOME_SETTINGS.createItem(R.string.home_settings, R.drawable.ic_home_black_24dp),
		Settings.ACTION_SOUND_SETTINGS.createItem(R.string.sound_settings, R.drawable.ic_volume_up_black_24dp),
		Settings.ACTION_ZEN_MODE_PRIORITY_SETTINGS.createItem(R.string.zen_mode_settings, R.drawable.ic_notifications_off_black_24dp),
		Settings.ACTION_INTERNAL_STORAGE_SETTINGS.createItem(R.string.internal_storage_settings, R.drawable.ic_storage_black_24dp),
		Settings.ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS.createItem(R.string.all_applications_settings, R.drawable.ic_apps_black_24dp),
		Settings.ACTION_SECURITY_SETTINGS.createItem(R.string.security_settings, R.drawable.ic_lock_black_24dp),
		Settings.ACTION_SYNC_SETTINGS.createItem(R.string.sync_settings, R.drawable.ic_sync_black_24dp),
		Settings.ACTION_LOCATION_SOURCE_SETTINGS.createItem(R.string.location_settings, R.drawable.ic_location_on_black_24dp),
		Settings.ACTION_LOCALE_SETTINGS.createItem(R.string.locale_settings, R.drawable.ic_language_black_24dp),
		Settings.ACTION_INPUT_METHOD_SETTINGS.createItem(R.string.input_method_settings, R.drawable.ic_keyboard_black_24dp),
		Settings.ACTION_ACCESSIBILITY_SETTINGS.createItem(R.string.accessibility_settings, R.drawable.ic_accessibility_black_24dp),
		Settings.ACTION_PRINT_SETTINGS.createItem(R.string.print_settings, R.drawable.ic_print_black_24dp),
		Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS.createItem(R.string.application_development_settings, R.drawable.ic_developer_mode_black_24dp),
		Settings.ACTION_DEVICE_INFO_SETTINGS.createItem(R.string.device_info_settings, R.drawable.ic_perm_device_information_black_24dp)
).apply {
	if (true || Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
		add(indexOf(Settings.ACTION_ACCESSIBILITY_SETTINGS),
				Settings.ACTION_BATTERY_SAVER_SETTINGS.createItem(R.string.battery_saver_settings, R.drawable.ic_battery_charging_full_black_24dp))
	}
	if (true || Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
		add(indexOf(Settings.ACTION_NFC_SETTINGS),
				Settings.ACTION_VPN_SETTINGS.createItem(R.string.vpn_settings, R.drawable.ic_vpn_key_black_24dp))
	}
}

data class SettingsItem(val action: String, val titleResId: Int, val iconResId: Int)

private fun String.createItem(titleResId: Int, iconResId: Int) = SettingsItem(this, titleResId, iconResId)

private fun MutableList<SettingsItem>.indexOf(action: String): Int = this.indexOfFirst { it.action == action }