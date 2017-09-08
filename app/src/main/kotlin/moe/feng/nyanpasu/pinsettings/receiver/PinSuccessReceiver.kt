package moe.feng.nyanpasu.pinsettings.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import moe.feng.nyanpasu.pinsettings.ACTION_SUCCESS_ADD
import moe.feng.nyanpasu.pinsettings.EXTRA_ACTION_NAME

class PinSuccessReceiver : BroadcastReceiver() {

	override fun onReceive(context: Context, intent: Intent) {
		val sendIntent = Intent(ACTION_SUCCESS_ADD)
		sendIntent.putExtra(EXTRA_ACTION_NAME, intent.getStringExtra(EXTRA_ACTION_NAME))
		context.sendBroadcast(sendIntent)
	}

}