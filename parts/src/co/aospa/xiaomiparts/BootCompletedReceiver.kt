/*
 * SPDX-FileCopyrightText: 2018 The LineageOS Project
 * SPDX-FileCopyrightText: 2025 Paranoid Android
 * SPDX-License-Identifier: Apache-2.0
 */

package co.aospa.xiaomiparts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import co.aospa.xiaomiparts.camera.NfcCameraService
import co.aospa.xiaomiparts.display.ColorService
import co.aospa.xiaomiparts.display.DcDimmingService
import co.aospa.xiaomiparts.doze.PocketService
import co.aospa.xiaomiparts.gestures.GestureUtils
import co.aospa.xiaomiparts.thermal.ThermalUtils
import co.aospa.xiaomiparts.touch.TouchNonUiService
import co.aospa.xiaomiparts.touch.TouchOrientationService

/** Everything begins at boot. */
class BootCompletedReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "Received intent: ${intent.action}")
        if (intent.action != Intent.ACTION_LOCKED_BOOT_COMPLETED) return

        Log.i(TAG, "Boot completed, starting services")
        ColorService.startService(context)
        DcDimmingService.startService(context)
        PocketService.startService(context)
        NfcCameraService.startService(context)
        TouchOrientationService.startService(context)
        TouchNonUiService.startService(context)
        ThermalUtils.getInstance(context).startService()
        GestureUtils.onBootCompleted(context)
    }

    companion object {
        private const val TAG = "XiaomiParts-BCR"
    }
}
