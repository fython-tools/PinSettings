package moe.feng.nyanpasu.pinsettings.ui

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import moe.feng.nyanpasu.pinsettings.ACTION_SUCCESS_ADD
import moe.feng.nyanpasu.pinsettings.EXTRA_ACTION_NAME
import moe.feng.nyanpasu.pinsettings.R
import moe.feng.nyanpasu.pinsettings.SETTINGS_ITEMS
import moe.feng.nyanpasu.pinsettings.ui.adapter.ModelBindAdapter
import moe.feng.nyanpasu.pinsettings.ui.adapter.SettingsItemBinder
import moe.feng.nyanpasu.pinsettings.util.ToastUtils

class MainActivity : Activity() {

	private val recyclerView by lazy { findViewById<RecyclerView>(android.R.id.list) }
	private val adapter = ModelBindAdapter(SettingsItemBinder())

	private val pinSuccessReceiver by lazy { PinSuccessReceiver() }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		setTitle(R.string.activity_main_title)

		recyclerView.layoutManager = LinearLayoutManager(this)
		recyclerView.adapter = adapter
		adapter.items.addAll(SETTINGS_ITEMS)
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
			// TODO Settings Activity
			true
		}
		R.id.action_about -> {
			// TODO About dialog
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