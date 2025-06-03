/*
 * SPDX-FileCopyrightText: 2020 The LineageOS Project
 * SPDX-FileCopyrightText: 2025 Paranoid Android
 * SPDX-License-Identifier: Apache-2.0
 */

package co.aospa.xiaomiparts.thermal

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import co.aospa.xiaomiparts.R
import co.aospa.xiaomiparts.utils.dlog
import com.android.settingslib.collapsingtoolbar.CollapsingToolbarBaseActivity

/** Thermal profile settings activity. */
class ThermalSettingsActivity : CollapsingToolbarBaseActivity() {

    private lateinit var thermalUtils: ThermalUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager
            .beginTransaction()
            .replace(
                com.android.settingslib.collapsingtoolbar.R.id.content_frame,
                ThermalSettingsFragment(),
                TAG
            )
            .commit()
        thermalUtils = ThermalUtils.getInstance(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        dlog(TAG, "onBackPressed()")
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu
            .add(Menu.NONE, MENU_RESET, Menu.NONE, R.string.thermal_reset)
            .setIcon(R.drawable.reset_wrench_24px)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            MENU_RESET -> {
                confirmReset()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun confirmReset() {
        AlertDialog.Builder(this)
            .setTitle(R.string.thermal_reset)
            .setMessage(R.string.thermal_reset_message)
            .setPositiveButton(android.R.string.yes) { _, _ ->
                thermalUtils.resetProfiles()
                recreate()
            }
            .setNegativeButton(android.R.string.no, null)
            .show()
    }

    companion object {
        private const val TAG = "thermal"
        private const val MENU_RESET = 1001
    }
}
