package com.nightstalker.artic

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nightstalker.artic.databinding.ActivityMainBinding
import com.nightstalker.artic.features.ApiConstants.CalendarEventConstants.EVENT_BEGIN
import com.nightstalker.artic.features.ApiConstants.CalendarEventConstants.EVENT_CALENDAR_TYPE
import com.nightstalker.artic.features.ApiConstants.CalendarEventConstants.EVENT_END
import com.nightstalker.artic.features.ApiConstants.CalendarEventConstants.EVENT_RULE
import com.nightstalker.artic.features.artwork.presentation.ui.detail.ArtworkDetailsViewModel
import com.nightstalker.artic.features.artwork.presentation.ui.filter.FilterArtworksViewModel
import com.nightstalker.artic.features.audio.presentation.viewmodel.AudioViewModel
import com.nightstalker.artic.features.exhibition.presentation.ui.detail.ExhibitionDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val audioViewModel by viewModel<AudioViewModel>()
    private val artworkDetailsViewModel by viewModel<ArtworkDetailsViewModel>()
    private val exhibitionsViewModel by viewModel<ExhibitionDetailsViewModel>()
    private val filterArtworksViewModel by viewModel<FilterArtworksViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val botNavView: BottomNavigationView = binding.bottomNavigation

        val navController = findNavController(R.id.navHostFragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.artworksListFragment,
                R.id.exhibitionsListFragment,
                R.id.ticketsListFragment,
                R.id.audioLookupFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        botNavView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean =
        findNavController(R.id.navHostFragment).navigateUp() || super.onSupportNavigateUp()

// Регистрация события в календаре Google
    fun addCalendarEvent(params: Map<String, String>) {
        val intent = Intent(Intent.ACTION_EDIT)
        intent.type = EVENT_CALENDAR_TYPE

        params.forEach {
            when (it.key) {
                // Период работы выставки, Long параметры
                EVENT_BEGIN, EVENT_END -> intent.putExtra(it.key, it.value.toLong())
                // Boolean параметры - "Событие длится весь день"
                EVENT_RULE -> intent.putExtra(it.key, it.value == "true")
                // String параметры
                else -> intent.putExtra(it.key, it.value)
            }
        }
        startActivity(intent)
    }

}