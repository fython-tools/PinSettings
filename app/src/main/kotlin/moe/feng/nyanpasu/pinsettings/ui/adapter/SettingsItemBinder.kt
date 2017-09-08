package moe.feng.nyanpasu.pinsettings.ui.adapter

import android.content.Intent
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import moe.feng.nyanpasu.pinsettings.R
import moe.feng.nyanpasu.pinsettings.SettingsItem
import moe.feng.nyanpasu.pinsettings.ui.adapter.ModelBindAdapter.*
import moe.feng.nyanpasu.pinsettings.util.LauncherUtils
import moe.feng.nyanpasu.pinsettings.view.CheatSheet

class SettingsItemBinder: ModelBindAdapter.ModelBinder<SettingsItem>() {

	private var ViewHolder.icon by autoBindView<ImageView>(android.R.id.icon)
	private var ViewHolder.title by autoBindView<TextView>(android.R.id.title)
	private var ViewHolder.pinAction by autoBindView<ImageButton>(R.id.action_pin)

	override fun onViewCreated(view: View, holder: ViewHolder) {
		super.onViewCreated(view, holder)
		CheatSheet.setup(holder.pinAction)
		holder.pinAction.setOnClickListener {
			val data = holder.getCurrentData<SettingsItem>()
			LauncherUtils.addShortcut(holder.context, data.titleResId, data.iconResId, data.action)
		}
		view.setOnClickListener {
			holder.context.startActivity(Intent(holder.getCurrentData<SettingsItem>().action))
		}
	}

	override fun onBind(holder: ViewHolder, data: SettingsItem) {
		holder.title.setText(data.titleResId)
		holder.icon.setImageResource(data.iconResId)
	}

}