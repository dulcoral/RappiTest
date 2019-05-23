package com.coral.movietest.ui

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.coral.movietest.util.ActivityUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.graphics.drawable.AnimationDrawable
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.coral.movietest.R


class HomeActivity : AppCompatActivity() {
    var animationDrawable: AnimationDrawable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.coral.movietest.R.layout.activity_home)

        if (savedInstanceState == null) {
            setupViewFragment()
        }
        setupToolbar()
        setupBottomNavigation()
        animationStart()

    }

    private fun setupViewFragment() {
        // show discover movies fragment by default
        val discoverMoviesFragment = MoviesFragment.newInstance()
        ActivityUtils.replaceFragmentInActivity(
            supportFragmentManager, discoverMoviesFragment, R.id.fragment_container
        )
    }

    override fun onResume() {
        super.onResume()
        animationDrawable?.start()
    }

    private fun animationStart() {
        val coordinatorLayout: CoordinatorLayout? = findViewById(R.id.coordinator_home_layout)
        var animationDrawable = coordinatorLayout?.background as AnimationDrawable
        animationDrawable?.setEnterFadeDuration(3000)
        animationDrawable?.setExitFadeDuration(1000)
        animationDrawable.start()
    }

    private fun setupBottomNavigation() {
        val bottomNav: BottomNavigationView = this.findViewById(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->

            ActivityUtils.replaceFragmentInActivity(
                supportFragmentManager, MoviesFragment.newInstance(),
                R.id.fragment_container
            )
            return@OnNavigationItemSelectedListener true
        })

    }

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }
}
