package com.example.admediator

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.admediator.databinding.ActivityMainBinding
import com.example.admediator.utils.http.ConnectionListener
import com.example.admediator.viewmodel.AdViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mConnectionListener: ConnectionListener

    private val mAdViewModel: AdViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(
            this, R.layout.activity_main
        ).apply {
            this.lifecycleOwner = this@MainActivity
            this.viewModel = mAdViewModel
        }

        requestGetAdNetworks()
    }

    private fun requestGetAdNetworks() {
        mConnectionListener.observe(this) {
           it?.let {
               if (it.isConnected)
                   mAdViewModel.getAdNetworks()
           }
        }
    }
}