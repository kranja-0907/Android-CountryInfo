package hr.algebra.countryinfo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import hr.algebra.countryinfo.databinding.ActivitySplashScreenBinding
import hr.algebra.countryinfo.framework.*


private const val DELAY = 6500L
const val DATA_IMPORTED = "hr.algebra.countryinfo.data_imported"

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startAnimations()
        redirect()

    }

    private fun startAnimations() {
        binding.tvSplash.startAnimation(R.anim.translatetext)
        binding.ivSplash.startAnimation(R.anim.translateimage)
    }

    private fun redirect() {
        //imported, redirect
        if (getBooleanPreference(DATA_IMPORTED)){
            callDelayed(DELAY) {startActivity<HostActivity>()}
        } else {
            if(isOnline()){
                // 1. if online start download service
                Intent(this, CountryInfoService::class.java).apply{
                    CountryInfoService.equeue(
                        this@SplashScreenActivity,
                        this
                    )
                }
            } else {
                // 2. else write message and leave
                binding.tvSplash.text = getString(R.string.no_internet)
                callDelayed(DELAY) {finish()}
            }
        }
    }



}