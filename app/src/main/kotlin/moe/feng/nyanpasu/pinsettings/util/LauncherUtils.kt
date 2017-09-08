package moe.feng.nyanpasu.pinsettings.util

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.support.v4.content.pm.ShortcutInfoCompat
import android.support.v4.content.pm.ShortcutManagerCompat
import android.support.v4.graphics.drawable.IconCompat
import moe.feng.nyanpasu.pinsettings.EXTRA_ACTION_NAME
import moe.feng.nyanpasu.pinsettings.drawable.CompositeDrawable
import moe.feng.nyanpasu.pinsettings.receiver.PinSuccessReceiver
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.os.Build
import moe.feng.nyanpasu.pinsettings.R

object LauncherUtils {

	fun addShortcut(context: Context, titleRes: Int, iconRes: Int, action: String) {
		val title = context.getString(titleRes)
		val icon = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
			IconCompat.createWithBitmap(makeShortcutIcon(context, iconRes))
		} else {
			IconCompat.createWithAdaptiveBitmap(makeAdaptiveIcon(context, iconRes))
		}
		ShortcutManagerCompat.requestPinShortcut(
				context,
				ShortcutInfoCompat.Builder(context, action)
						.setShortLabel(title)
						.setIntent(Intent(action))
						.setIcon(icon)
						.build(),
				PendingIntent.getBroadcast(
						context,
						0,
						Intent(context, PinSuccessReceiver::class.java).apply { putExtra(EXTRA_ACTION_NAME, title) },
						PendingIntent.FLAG_CANCEL_CURRENT
				).intentSender
		)
	}

	private fun makeShortcutIcon(context: Context, iconRes: Int): Bitmap {
		val `48dp` = ScreenUtils.convertDpToPixel(48f).toInt()
		val `24dp` = `48dp` / 2
		val `12dp` = `24dp` / 2

		val drawable = CompositeDrawable(context.resources)

		drawable.setBackground(ColorDrawable(context.resources.getColor(R.color.material_grey_100)))
		drawable.setPadding(ScreenUtils.convertDpToPixel(2f))

		// Create bitmap canvas
		val bitmap = Bitmap.createBitmap(`48dp`, `48dp`, Bitmap.Config.ARGB_8888)
		val canvas = Canvas(bitmap)

		// Draw background
		drawable.setBounds(0, 0, canvas.width, canvas.height)
		drawable.drawTo(canvas, true)

		// Draw foreground
		context.resources.getDrawable(iconRes).mutate().apply {
			setTint(context.resources.getColor(R.color.material_teal_500))
			setBounds(`12dp`, `12dp`, `12dp` + `24dp`, `12dp` + `24dp`)
		}.draw(canvas)

		return bitmap
	}

	private fun makeAdaptiveIcon(context: Context, iconRes: Int): Bitmap {
		val `108dp` = ScreenUtils.convertDpToPixel(108f).toInt()
		val `36dp` = ScreenUtils.convertDpToPixel(36f).toInt()
		val `18dp` = `36dp` / 2

		// Create bitmap canvas
		val bitmap = Bitmap.createBitmap(`108dp`, `108dp`, Bitmap.Config.ARGB_8888)
		val canvas = Canvas(bitmap)

		// Draw background
		canvas.drawColor(context.resources.getColor(R.color.material_grey_100))

		// Draw foreground
		context.resources.getDrawable(iconRes).mutate().apply {
			setTint(context.resources.getColor(R.color.material_teal_500))
			setBounds(
					`108dp` / 2 - `18dp`,
					`108dp` / 2 - `18dp`,
					`108dp` / 2 + `18dp`,
					`108dp` / 2 + `18dp`)
		}.draw(canvas)

		return bitmap
	}

}