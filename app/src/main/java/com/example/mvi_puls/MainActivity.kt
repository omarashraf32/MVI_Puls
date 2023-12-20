package com.example.mvi_puls

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var numberTV: TextView
    lateinit var addNumberBtn: Button
    private val viewModel: AddNumberViewModel by lazy {
        ViewModelProvider(this)[AddNumberViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberTV = findViewById(R.id.number_textView)
        addNumberBtn = findViewById(R.id.add_number_button)
        render()
        addNumberBtn.setOnClickListener {
            //send to viewModel
            lifecycleScope.launch {
                viewModel.intentChannel.send(MainIntent.Addnumber) //(send) suspend fun
                //hna hwa hyb3t el (Intent) el fe (Activity) le (ViewModel) 3n trek (intentChannel)
            }
        }
    }

    private fun render() { //render el data el gya man (ViewModel)
        lifecycleScope.launch {
            viewModel.state.collectLatest {
                withStarted { //3shan n7al moshklat el (lifeCycle aware)
                    when (it) {
                        is MainViewState.Idle -> numberTV.text = "Idle"
                        is MainViewState.Number -> numberTV.text = it.number.toString()
                        is MainViewState.Error -> numberTV.text = it.error
                    }
                }


            }
        }


    }
}