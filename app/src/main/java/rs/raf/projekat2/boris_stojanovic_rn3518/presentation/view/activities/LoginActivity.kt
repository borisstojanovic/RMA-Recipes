package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import rs.raf.projekat2.boris_stojanovic_rn3518.R
import rs.raf.projekat2.boris_stojanovic_rn3518.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        initView();
        initListeners();
    }

    private fun doLogin(userName :String, PIN :String){

        if (PIN.equals("12345678")) {
            val sharedPreferences = getSharedPreferences("loginPreferences", MODE_PRIVATE);

            sharedPreferences.edit().putString("userName", userName).apply()
            sharedPreferences.edit().putBoolean("loggedIn", true).apply()

            val i = Intent(this, MainActivity::class.java)
            startActivity(i);
            finish();
        } else
            binding.txtInfo.setText(getString(R.string.invalidLogin));
    }

    private fun initListeners() {
        binding.btnLogin.setOnClickListener{
            if(binding.edtUserName.text.isNullOrBlank() || binding.edtPassword.text.isNullOrBlank()){
                binding.txtInfo.text = getString(R.string.missingFields)
            }else{
                doLogin(binding.edtUserName.text.toString(), binding.edtPassword.text.toString())
            }
        }
    }

    private fun initView() {


    }
}
