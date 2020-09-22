package com.training.jetdemo.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.training.jetdemo.R
import com.training.jetdemo.base.BaseActivity
import com.training.jetdemo.di.component.ActivityComponent
import com.training.jetdemo.repo.RepoFragment

class MainActivity : BaseActivity<MainViewModel>() {
    companion object {
        const val TAG = "MainActivity"
    }

    private var activeFragment: Fragment? = null

    override fun provideLayoutId(): Int = R.layout.activity_main

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.homeNavigation.observe(this, Observer {
            it.getIfNotHandled()?.run { showHome() }
        })

    }

    private fun showHome() {
        if (activeFragment is RepoFragment) return

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        var fragment = supportFragmentManager.findFragmentByTag(RepoFragment.TAG) as RepoFragment?

        if (fragment == null) {
            fragment = RepoFragment.newInstance()
            fragmentTransaction.add(R.id.containerFragment, fragment, RepoFragment.TAG)
        } else {
            fragmentTransaction.show(fragment)
        }

        if (activeFragment != null) fragmentTransaction.hide(activeFragment as Fragment)

        fragmentTransaction.commit()

        activeFragment = fragment
    }

}
