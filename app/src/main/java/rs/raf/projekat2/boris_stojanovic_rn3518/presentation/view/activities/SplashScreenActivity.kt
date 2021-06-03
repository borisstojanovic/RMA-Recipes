package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import rs.raf.projekat2.boris_stojanovic_rn3518.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.checkLogin()
    }

    private fun checkLogin(){
        val sharedPreferences = getSharedPreferences("loginPreferences", MODE_PRIVATE);
        val loggedIn = sharedPreferences.getBoolean("loggedIn", false);
        if(loggedIn){
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }else{
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i);
        }
        finish();
    }
}
