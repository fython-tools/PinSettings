package moe.feng.nyanpasu.pinsettings.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import moe.feng.nyanpasu.pinsettings.*
import moe.feng.nyanpasu.pinsettings.ui.adapter.ModelBindAdapter
import moe.feng.nyanpasu.pinsettings.ui.adapter.SettingsItemBinder
import moe.feng.nyanpasu.pinsettings.ui.fragment.MainSettingsFragment
import moe.feng.nyanpasu.pinsettings.util.Preferences
import moe.feng.nyanpasu.pinsettings.util.ToastUtils
import moe.feng.nyanpasu.pinsettings.util.kotlinyan.getSharedPreferencesProvider

class MainActivity : Activity() {

	private val recyclerView by lazy { findViewById<RecyclerView>(android.R.id.list) }
	private val adapter = ModelBindAdapter(SettingsItemBinder())

	private val tipsContainer by lazy { findViewById<View>(R.id.tips_container) }

	private val pinSuccessReceiver by lazy { PinSuccessReceiver() }

	private val settingsInstance = getSharedPreferencesProvider<Preferences>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		setTitle(R.string.activity_main_title)

		recyclerView.layoutManager = LinearLayoutManager(this)
		recyclerView.adapter = adapter
		adapter.items.addAll(SETTINGS_ITEMS)

		if (settingsInstance.shouldShowTips) {
			tipsContainer.visibility = View.VISIBLE
		}

		val button = findViewById<Button>(R.id.button_ok)
		button.setOnClickListener {
			tipsContainer.visibility = View.GONE
			settingsInstance.shouldShowTips = false
		}
	}

	override fun onResume() {
		super.onResume()
		registerReceiver(pinSuccessReceiver, IntentFilter(ACTION_SUCCESS_ADD))
	}

	override fun onPause() {
		super.onPause()
		unregisterReceiver(pinSuccessReceiver)
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		menuInflater.inflate(R.menu.menu_main, menu)
		return super.onCreateOptionsMenu(menu)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
		R.id.action_settings -> {
			SettingsActivity.launch<MainSettingsFragment>(this)
			true
		}
		R.id.action_about -> {
			AlertDialog.Builder(this)
					.setTitle(R.string.app_name)
					.setMessage(R.string.about_message)
					.setPositiveButton(android.R.string.ok) {_, _ -> }
					.setNegativeButton(R.string.button_donate_via_alipay) {_, _ ->
						val intent = Intent.parseUri(ALIPAY_TRANSFER_URL, Intent.URI_INTENT_SCHEME)
						startActivity(intent)
					}
					.setNeutralButton(R.string.button_github) {_, _ ->
						val intent = Intent(Intent.ACTION_VIEW,
								Uri.parse("https://github.com/fython/PinSettings"))
						startActivity(intent)
					}
					.show()
			true
		}
		else -> super.onOptionsItemSelected(item)
	}

	private inner class PinSuccessReceiver: BroadcastReceiver() {

		override fun onReceive(context: Context, intent: Intent) {
			ToastUtils.makeText(
					this@MainActivity,
					getString(R.string.toast_pinned_to_home, intent.getStringExtra(EXTRA_ACTION_NAME)),
					Toast.LENGTH_LONG
			).show()
		}

	}

}