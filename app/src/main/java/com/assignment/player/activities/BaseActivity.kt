package com.assignment.player.activities

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.assignment.player.R
import com.assignment.player.fragments.photos.PhotosFragment
import com.assignment.player.utils.Communicator
import com.assignment.player.utils.ConnectionType
import com.assignment.player.utils.NetworkMonitorUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_base.*

class BaseActivity : AppCompatActivity() , Communicator {

    lateinit var navController: NavController
    private val networkMonitor = NetworkMonitorUtil(this)
    lateinit var context : Context
    private val NETWORK_TAG = "NETWORK_MONITOR_STATUS"
    private var backPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        setUpViews()

        networkMonitor.result = { isAvailable, type ->
            runOnUiThread {
                when (isAvailable) {
                    true -> {
                        when (type) {
                            ConnectionType.Wifi -> {
                                Snackbar.make(window.decorView.rootView,getString(R.string.Wifi_Connection),Snackbar.LENGTH_SHORT).show()
                                Log.i(NETWORK_TAG, getString(R.string.Wifi_Connection))
                            }
                            ConnectionType.Cellular -> {
                                Snackbar.make(window.decorView.rootView,getString(R.string.Cellular_Connection),Snackbar.LENGTH_SHORT).show()
                                Log.i(NETWORK_TAG, getString(R.string.Cellular_Connection))
                            }
                            else -> {
                            }
                        }
                    }
                    false -> {
                        Snackbar.make(window.decorView.rootView,getString(R.string.No_Connection),
                            Snackbar.LENGTH_SHORT).show()
                        Log.i(NETWORK_TAG, getString(R.string.No_Connection))
                    }
                }
            }
        }
    }

    fun setUpViews(){
        //Finding The Navigation Controller
        navController = findNavController(R.id.fragNavHost)
        // Setting Navigation Controller with the BottomNavigationView
        bottomNavView.setupWithNavController(navController)
    }

    override fun passDataCom(editTextInput: String) {
        val bundle = Bundle()
        bundle.putString("message", editTextInput)
        val transaction = this.supportFragmentManager.beginTransaction()
        val photoFragment  = PhotosFragment()
        photoFragment.arguments = bundle
        transaction.replace(R.id.fragNavHost, photoFragment)
        transaction.commit()
    }

    override fun onResume() {
        super.onResume()
        networkMonitor.register()
    }

    override fun onStop() {
        super.onStop()
        networkMonitor.unregister()
    }

    override fun onBackPressed() {
        if (navController.graph.startDestination == navController.currentDestination?.id)
        {
            if (backPressedOnce)
            {
                super.onBackPressed()
                return
            }
            backPressedOnce = true
            Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show()

            Handler().postDelayed(2000) {
                backPressedOnce = false
            }
        }
        else {
            super.onBackPressed()
        }
    }

}