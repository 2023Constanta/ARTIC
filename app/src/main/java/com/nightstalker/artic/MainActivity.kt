package com.nightstalker.artic

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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
//    private val artworksListViewModel by viewModel<ArtworksListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }
        val splashScreen = installSplashScreen()

        checkForPermission()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val botNavView: BottomNavigationView = binding.bottomNavigation

        val navController = findNavController(R.id.navHostFragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.artworksListFragment,
                R.id.exhibitionsListFragment,
                R.id.qrScanner,
                R.id.ticketsListFragment,
                R.id.audioLookupFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            Handler(Looper.getMainLooper()).post {
                when (destination.id) {
                    R.id.artworksListFragment,
                    R.id.exhibitionsListFragment,
                    R.id.qrScanner,
                    R.id.ticketsListFragment,
                    R.id.audioLookupFragment
                    -> {
                        botNavView.visibility = View.VISIBLE
                    }
                    else -> {
                        botNavView.visibility = View.GONE
                    }
                }
            }
        }
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

    private fun checkForPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return
//            gotToMainActivity()

        } else {
            requestPermission()
        }
    }


    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )
    }

    private fun gotToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                gotToMainActivity()
            } else if (isPermanentlyDenied()) {
                showGoToAppSettingsDialog()
            } else
                requestPermission()
        }
    }

    private fun isPermanentlyDenied(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA).not()
        } else {
            return false
        }
    }


    private fun showGoToAppSettingsDialog() {
        AlertDialog.Builder(this, R.style.CustomAlertDialog)
            .setTitle(getString(R.string.grant_permissions))
            .setMessage(getString(R.string.we_need_permission))
            .setPositiveButton(getString(R.string.grant)) { _, _ ->
                goToAppSettings()
            }
            .setNegativeButton(getString(R.string.cancel)) { _, _ ->
                run {
                    finish()
                }
            }.show()
    }

    private fun goToAppSettings() {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        )
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }


    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 102
    }

}