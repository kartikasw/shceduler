package com.dicoding.courseschedule.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder
import com.dicoding.courseschedule.util.NightMode

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        //TODO 10 : Update theme based on value in ListPreference
        val listPreference = findPreference<ListPreference>(getString(R.string.pref_key_dark))
        listPreference?.setOnPreferenceChangeListener { _, newValue ->
            updateTheme(listPreference.findIndexOfValue(newValue.toString()))
        }

        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference
        val dailyReminder = DailyReminder()

        val prefNotification = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
        prefNotification?.setOnPreferenceChangeListener { _, _ ->
            if(!prefNotification.isChecked) {
                dailyReminder.setDailyReminder(requireContext())
            } else {
                dailyReminder.cancelAlarm(requireContext())
            }
            true
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        when(nightMode){
            0 ->  AppCompatDelegate.setDefaultNightMode(NightMode.AUTO.value)
            1 ->  AppCompatDelegate.setDefaultNightMode(NightMode.ON.value)
            2 ->  AppCompatDelegate.setDefaultNightMode(NightMode.OFF.value)
        }
        requireActivity().recreate()
        return true
    }
}