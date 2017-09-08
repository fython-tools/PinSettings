package moe.feng.nyanpasu.pinsettings.util

import android.content.res.Resources

object ScreenUtils {

	fun convertDpToPixel(dpValue: Float): Float {
		val scale = Resources.getSystem().displayMetrics.density
		return dpValue * scale + 0.5f
	}

}