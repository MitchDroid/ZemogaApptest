package co.com.mjbarrerab.zemogaapptest.ui.base.activity

import android.os.Bundle
import android.view.View

import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import co.com.mjbarrerab.zemogaapptest.R
import co.com.mjbarrerab.zemogaapptest.di.components.ActivityComponent
import co.com.mjbarrerab.zemogaapptest.di.components.DaggerActivityComponent
import co.com.mjbarrerab.zemogaapptest.di.modules.ActivityModule
import co.com.mjbarrerab.zemogaapptest.di.modules.NetworkModule
import co.com.mjbarrerab.zemogaapptest.ui.base.MvpView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_posts.*

/**
 * Created by miller.barrera on 08/08/2020.
 */
abstract class BaseMVPActivity :
    AppCompatActivity(), MvpView.BaseView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(this.getLayout())
        manageStatusBarColor()
    }

    @LayoutRes
    protected abstract fun getLayout(): Int

    private fun manageStatusBarColor() {
        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.status_bar_color)
    }

    fun getActivityComponent(): ActivityComponent {
        return DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .networkModule(NetworkModule())
            .build()
    }

    fun setupToolbar() {
        val mToolbar = toolbar
         setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        mToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    fun showErrorMessage(view: View, errorMessage: String?){
        Snackbar.make(view, errorMessage.toString(), Snackbar.LENGTH_LONG).show()
    }

}