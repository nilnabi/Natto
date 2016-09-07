package com.nilnabi.android.natto.activity

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.nilnabi.android.natto.R
import com.nilnabi.android.natto.service.RssFetchService

/**
 * Created by nilnabi on 2016/09/03.
 */
class MainActivity: AppCompatActivity() {

    val toolbar by lazy { findViewById(R.id.toolbar) as Toolbar }

    val drawer by lazy { findViewById(R.id.drawer_layout) as DrawerLayout }

    val navigationView by lazy { findViewById(R.id.nav_view) as NavigationView }

    val fab by lazy { findViewById(R.id.fab) as FloatingActionButton }

    val onClickFabListener by lazy {
        View.OnClickListener {
            Snackbar.make(it, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("TAction", null).show()
        }
    }

    val toggle by lazy {
        ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        )
    }

    val onNavigationListener by lazy {
        NavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_camera -> {}
                R.id.nav_slideshow -> {}
                R.id.nav_manage -> {}
                R.id.nav_share -> {}
                R.id.nav_send -> {}
            }
            drawer.closeDrawer(GravityCompat.START)
            true
        }
    }

    val fetchService by lazy { RssFetchService() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener(onClickFabListener)
        drawer.addDrawerListener(toggle).apply { toggle.syncState() }
        navigationView.setNavigationItemSelectedListener(onNavigationListener)

        fetchService.execute()

    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return item?.let {
            if (it.itemId == R.id.action_settings) true else false
        } ?: super.onOptionsItemSelected(item)
    }

}