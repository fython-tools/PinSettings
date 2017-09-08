/*
 * Materialize - Materialize all those not material
 * Copyright (C) 2015  XiNGRZ <xxx@oxo.ooo>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package moe.feng.nyanpasu.pinsettings.drawable

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
import moe.feng.nyanpasu.pinsettings.R

class CompositeDrawable(private val resources: Resources) : Drawable() {

	private val foregroundBounds = RectF()

	private val backgroundBounds = Rect()

	private val scoreBounds = Rect()

	private val paint = Paint()

	private var source: Bitmap? = null

	private var shape: Shape? = null

	private var padding = 0f

	private var background: Drawable? = null

	private var back: Bitmap? = null

	private var mask: Bitmap? = null

	private var fore: Bitmap? = null

	init {
		setShape(Shape.ROUND)
	}

	fun setSource(source: Bitmap?) {
		this.source = source
		invalidateSelf()
	}

	fun setShape(shape: Shape) {
		this.shape = shape
		invalidateForegroundBounds()
		invalidateBackgroundBounds()
		invalidateBitmaps()
		invalidateSelf()
	}

	fun setPadding(padding: Float) {
		this.padding = padding
		invalidateForegroundBounds()
		invalidateSelf()
	}

	fun setBackground(background: Drawable) {
		this.background = background
		invalidateSelf()
	}

	override fun onBoundsChange(bounds: Rect) {
		super.onBoundsChange(bounds)
		invalidateForegroundBounds()
		invalidateBackgroundBounds()
		invalidateScoreBounds()
	}

	private fun invalidateForegroundBounds() {
		foregroundBounds.set(bounds)
		applyForegroundBounds(foregroundBounds)
	}

	private fun applyForegroundBounds(foregroundBounds: RectF) {
		val finalPadding = padding + shape!!.getPadding(resources)
		foregroundBounds.inset(finalPadding, finalPadding)
	}

	private fun invalidateBackgroundBounds() {
		backgroundBounds.set(bounds)
		applyBackgroundBounds(backgroundBounds)
	}

	private fun applyBackgroundBounds(backgroundBounds: Rect) {
		val offset = resources.getDimensionPixelOffset(shape!!.padding)
		backgroundBounds.inset(offset, offset)
	}

	private fun invalidateScoreBounds() {
		scoreBounds.set(bounds)
		applyScoreBounds(scoreBounds)
	}

	private fun applyScoreBounds(scoreBounds: Rect) {
		scoreBounds.bottom = scoreBounds.centerY()
	}

	private fun invalidateBitmaps() {
		if (back != null) {
			back!!.recycle()
		}

		back = shape!!.getBackBitmap(resources)

		if (mask != null) {
			mask!!.recycle()
		}

		mask = shape!!.getMaskBitmap(resources)

		if (fore != null) {
			fore!!.recycle()
		}

		fore = shape!!.getForeBitmap(resources)
	}

	override fun draw(canvas: Canvas) {
		drawInternal(canvas, true, bounds, foregroundBounds, backgroundBounds, scoreBounds)
	}

	fun drawTo(canvas: Canvas, antiAliasing: Boolean) {
		val bounds = Rect(0, 0, canvas.width, canvas.height)

		val foregroundBounds = RectF(bounds)
		applyForegroundBounds(foregroundBounds)

		val backgroundBounds = Rect(bounds)
		applyBackgroundBounds(backgroundBounds)

		val scoreBounds = Rect(bounds)
		applyScoreBounds(scoreBounds)

		drawInternal(canvas, antiAliasing, bounds, foregroundBounds, backgroundBounds, scoreBounds)
	}

	private fun drawInternal(canvas: Canvas, antiAliasing: Boolean,
	                         bounds: Rect, foregroundBounds: RectF, backgroundBounds: Rect, scoreBounds: Rect) {
		paint.flags = if (antiAliasing) FLAG_SCALES else 0

		canvas.drawBitmap(back, null, bounds, paint)

		canvas.saveLayer(
				bounds.left.toFloat(), bounds.top.toFloat(), bounds.right.toFloat(), bounds.bottom.toFloat(), null, Canvas.ALL_SAVE_FLAG)

		if (background != null) {
			background!!.bounds = backgroundBounds
			background!!.draw(canvas)
		}

		// always anti-aliasing on source bitmap because we scaled it
		if (source != null) {
			paint.flags = FLAG_SCALES
			canvas.drawBitmap(source, null, foregroundBounds, paint)
		}

		paint.flags = if (antiAliasing) FLAG_SCALES else 0

		if (shape!!.score) {
			paint.color = SCORE_COLOR
			canvas.drawRect(scoreBounds, paint)
			paint.color = Color.WHITE
		}

		paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
		canvas.drawBitmap(mask, null, bounds, paint)
		paint.xfermode = null

		canvas.drawBitmap(fore, null, bounds, paint)

		canvas.restore()
	}

	override fun setAlpha(alpha: Int) {
		// not support
	}

	override fun setColorFilter(colorFilter: ColorFilter) {
		// not support
	}

	override fun getOpacity(): Int {
		return PixelFormat.TRANSLUCENT
	}

	enum class Shape constructor(@DimenRes val padding: Int, val back: Int,
	                             val mask: Int, val fore: Int, val score: Boolean) {

		ROUND(
				R.dimen.default_padding_round,
				R.drawable.stencil_round_back,
				R.drawable.stencil_round_mask,
				R.drawable.stencil_round_fore,
				false
		);

		fun getPadding(resources: Resources): Float {
			return resources.getDimension(padding)
		}

		fun getBitmap(resources: Resources, @DrawableRes drawable: Int): Bitmap {
			val options = BitmapFactory.Options()
			options.inScaled = false

			return BitmapFactory.decodeResource(resources, drawable, options)
		}

		fun getBackBitmap(resources: Resources): Bitmap {
			return getBitmap(resources, back)
		}

		fun getMaskBitmap(resources: Resources): Bitmap {
			return getBitmap(resources, mask)
		}

		fun getForeBitmap(resources: Resources): Bitmap {
			return getBitmap(resources, fore)
		}

	}

	companion object {

		private val FLAG_SCALES = Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG

		private val SCORE_COLOR = Color.argb(0x10, 0, 0, 0)
	}

}
