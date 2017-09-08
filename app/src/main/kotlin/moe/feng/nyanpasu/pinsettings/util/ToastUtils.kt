package moe.feng.nyanpasu.pinsettings.util

import android.content.Context
import android.widget.TextView
import android.widget.Toast
import moe.feng.nyanpasu.pinsettings.R

object ToastUtils {

	fun makeText(context: Context, text: CharSequence, duration: Int): Toast {
		val toast = Toast.makeText(context, text, duration)

		// Apply Material Toast (Oreo)
		toast.view.setBackgroundResource(R.drawable.tooltip_frame_dark)
		val textView = toast.view.findViewById<TextView>(android.R.id.message)
		textView.setTextAppearance(context, android.R.style.TextAppearance_Material_Body1)
		textView.setTextColor(context.resources.getColor(android.R.color.white))
		val `16dp` = ScreenUtils.convertDpToPixel(16f).toInt()
		textView.setPaddingRelative(`16dp`, 0, `16dp`, 0)

		return toast
	}

	fun makeText(context: Context, stringResId: Int, duration: Int): Toast
			= makeText(context, context.getString(stringResId), duration)

}