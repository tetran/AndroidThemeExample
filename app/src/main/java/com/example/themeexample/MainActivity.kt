package com.example.themeexample

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.preference.PreferenceManager
import com.example.themeexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var currentTheme = THEME_DEFAULT

    companion object {
        private const val KEY_THEME = "Theme"
        private val THEME_DEFAULT = R.style.AppTheme
        private val THEME_PINK = R.style.AppTheme_Pink
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentTheme = PreferenceManager
            .getDefaultSharedPreferences(this)
            .getInt(KEY_THEME, THEME_DEFAULT)
        setTheme()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.themeSwitch.text = when(currentTheme) {
            THEME_PINK -> "PINK -> DEFAULT"
            else -> "DEFAULT -> PINK"
        }
        binding.themeSwitch.setOnClickListener { view ->
            switchTheme()
            recreate()
        }
    }

    private fun setTheme() {
        super.setTheme(currentTheme)
    }

    private fun switchTheme() {
        currentTheme = when(currentTheme) {
            THEME_DEFAULT -> THEME_PINK
            THEME_PINK -> THEME_DEFAULT
            else -> THEME_DEFAULT
        }

        PreferenceManager
            .getDefaultSharedPreferences(this)
            .edit()
            .putInt(KEY_THEME, currentTheme)
            .apply()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}