package moe.feng.nyanpasu.pinsettings.preference

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.preference.Preference
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import de.hdodenhof.circleimageview.CircleImageView
import moe.feng.common.material.colorutils.ColorLevel
import moe.feng.common.material.colorutils.ColorName
import moe.feng.common.material.colorutils.MaterialColors
import moe.feng.nyanpasu.pinsettings.R
import moe.feng.nyanpasu.pinsettings.ui.adapter.ModelBindAdapter
import kotlin.properties.Delegates

class ColorPickerPreference: Preference {

	constructor(context: Context): super(context)

	constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet)

	constructor(context: Context, attributeSet: AttributeSet, defAttrs: Int): super(context, attributeSet, defAttrs)

	private var dialogContentView: View by Delegates.notNull()
	private var dialogList: RecyclerView by Delegates.notNull()
	private var pickerAdapter: ModelBindAdapter<Pair<ColorName, Int>>? = null

	private var previewView: CircleImageView? = null

	private val colors = ColorName.values().map { it to MaterialColors[it][ColorLevel.`500`] }.toMutableList()
	private var checkedPos: Int = 0

	var pickerCallback: ((ColorName) -> Unit)? = null

	init {
		widgetLayoutResource = R.layout.preference_material_color_preview
	}

	override fun onClick() {
		showDialog()
	}

	private fun getCurrentColor(): Int = colors[checkedPos].second

	override fun onBindView(view: View?) {
		super.onBindView(view)
		previewView = view?.findViewById(R.id.image_view)
		previewView?.setImageDrawable(ColorDrawable(getCurrentColor()))
	}

	fun setCheckedColor(color: ColorName) {
		checkedPos = colors.indexOfFirst { it.first == color }
		pickerAdapter?.notifyDataSetChanged()
		summary = color.name
		previewView?.setImageDrawable(ColorDrawable(getCurrentColor()))
	}

	private fun showDialog() {
		val builder = AlertDialog.Builder(context)

		dialogContentView = LayoutInflater.from(context).inflate(R.layout.dialog_color_picker, null)
		dialogList = dialogContentView.findViewById(android.R.id.list)
		dialogList.layoutManager = GridLayoutManager(context, 5)
		pickerAdapter = ModelBindAdapter(ColorItemBinder())
		pickerAdapter?.items = colors
		dialogList.adapter = pickerAdapter

		builder.setView(dialogContentView)
		builder.setPositiveButton(android.R.string.ok) {_, _ ->
			summary = colors[checkedPos].first.name
			pickerCallback?.invoke(colors[checkedPos].first)
			previewView?.setImageDrawable(ColorDrawable(getCurrentColor()))
		}
		builder.setNegativeButton(android.R.string.cancel) {_, _ -> }
		builder.show()
	}

	private inner class ColorItemBinder: ModelBindAdapter.ModelBinder<Pair<ColorName, Int>>() {

		override val layoutResId: Int = R.layout.item_color_picker

		private var ModelBindAdapter.ViewHolder.imageView by autoBindView<CircleImageView>(R.id.item_image_view)
		private var ModelBindAdapter.ViewHolder.checkIcon by autoBindView<View>(R.id.item_check_icon)

		override fun onViewCreated(view: View, holder: ModelBindAdapter.ViewHolder) {
			super.onViewCreated(view, holder)
			holder.imageView.setOnClickListener {
				checkedPos = holder.adapterPosition
				pickerAdapter?.notifyDataSetChanged()
			}
		}

		override fun onBind(holder: ModelBindAdapter.ViewHolder, data: Pair<ColorName, Int>) {
			holder.imageView.setImageDrawable(ColorDrawable(data.second))
			holder.imageView.borderColor = context.resources.getColor(
					if (checkedPos == holder.adapterPosition) R.color.material_grey_500 else R.color.material_grey_50
			)
			holder.checkIcon.visibility = if (checkedPos == holder.adapterPosition) View.VISIBLE else View.INVISIBLE
		}

	}

}