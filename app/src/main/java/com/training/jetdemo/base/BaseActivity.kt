package com.training.jetdemo.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.training.jetdemo.JetApplication
import com.training.jetdemo.di.component.ActivityComponent
import com.training.jetdemo.di.component.DaggerActivityComponent
import com.training.jetdemo.di.module.ActivityModule
import com.training.jetdemo.utils.common.Toaster
import javax.inject.Inject

abstract  class BaseActivity<VM : BaseViewModel>: AppCompatActivity(){

    @Inject
    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies(buildActivityComponent())
        setContentView(provideLayoutId())
        super.onCreate(savedInstanceState)
        setupObservers()
        setupView(savedInstanceState)
        viewModel.onCreate()
    }

    private fun buildActivityComponent()=
        DaggerActivityComponent
            .builder()
            .applicationComponent((application as JetApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()

    protected open fun setupObservers() {
        viewModel.messageString.observe(this, Observer {
            it.data?.run { showMessage(this) }
        })

        viewModel.messageStringId.observe(this, Observer {
            it.data?.run { showMessage(this) }
        })
    }

    open fun goBack() = onBackPressed()

    fun showMessage(message: String) = Toaster.show(applicationContext, message)

    fun showMessage(@StringRes resId: Int) = showMessage(getString(resId))

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount>0)
            supportFragmentManager.popBackStackImmediate()
        else
            super.onBackPressed()
    }

    @LayoutRes
    protected abstract fun provideLayoutId(): Int

    protected abstract  fun injectDependencies(activityComponent: ActivityComponent)

    protected  abstract fun setupView(savedInstanceState: Bundle?)


}