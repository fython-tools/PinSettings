package moe.feng.nyanpasu.pinsettings.ui

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import moe.feng.nyanpasu.pinsettings.R

class SettingsActivity: Activity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_settings)

		actionBar.setDisplayHomeAsUpEnabled(true)
		actionBar.setHomeAsUpIndicator(resources.getDrawable(R.drawable.ic_arrow_back_black_24dp).mutate().apply {
			setTint(resources.getColor(R.color.material_teal_700))
		})

		fragmentManager.beginTransaction().replace(
				R.id.frame_container,
				(intent.getSerializableExtra(EXTRA_FRAGMENT_CLASS) as Class<Fragment>).newInstance()
		).commit()
	}

	override fun onOptionsItemSelected(item: MenuItem?): Boolean {
		if (item?.itemId == android.R.id.home) {
			onBackPressed()
			return true
		}
		return super.onOptionsItemSelected(item)
	}

	companion object {

		private const val EXTRA_FRAGMENT_CLASS = "fragment_class"

		fun <T: Fragment> launch(context: Context, clazz: Class<T>) {
			val intent = Intent(context, SettingsActivity::class.java)
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
			intent.putExtra(EXTRA_FRAGMENT_CLASS, clazz)
			context.startActivity(intent)
		}

		inline fun <reified T: Fragment> launch(context: Context) {
			launch(context, T::class.java)
		}

	}

}