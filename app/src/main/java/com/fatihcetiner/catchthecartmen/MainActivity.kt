package com.fatihcetiner.catchthecartmen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.fatihcetiner.catchthecartmen.databinding.ActivityMainBinding
import java.util.Random

class MainActivity : AppCompatActivity() {

    var score = 0
    private lateinit var binding: ActivityMainBinding
    var imageArray = ArrayList<ImageView>()
    var handler : Handler = Handler(Looper.getMainLooper())
    var runnable = Runnable {  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        imageArray.add(binding.imageView)
        imageArray.add(binding.imageView2)
        imageArray.add(binding.imageView3)
        imageArray.add(binding.imageView4)
        imageArray.add(binding.imageView5)
        imageArray.add(binding.imageView6)
        imageArray.add(binding.imageView7)
        imageArray.add(binding.imageView8)
        imageArray.add(binding.imageView9)
        hideImages()

        //CountDownTimer
        object : CountDownTimer(15500,1000){
            override fun onTick(millisUntilFinished: Long) {
                binding.timeText.text = "Time: " + millisUntilFinished/1000
            }

            override fun onFinish() {
                binding.timeText.text = "Time: 0"
                handler.removeCallbacks(runnable)

                for (image in imageArray){
                    image.visibility = View.INVISIBLE
                }

                //Alert
                val alertDialog = AlertDialog.Builder(this@MainActivity)
                alertDialog.setTitle("Game Over!")
                alertDialog.setMessage("Restart The Game ?")
                alertDialog.setPositiveButton("Yes"){dialog, which ->
                    val intent = intent
                    finish()
                    startActivity(intent)
                }
                alertDialog.setNegativeButton("No"){dialog, which ->
                    Toast.makeText(this@MainActivity,"Game Over Your Score $score",Toast.LENGTH_LONG).show()
                }
                alertDialog.show()
            }

        }.start()
    }

    fun hideImages(){

        runnable = object : Runnable{
            override fun run() {
                for (image in imageArray){
                    image.visibility = View.INVISIBLE
                }
                val random = Random()
                val randomIndex = random.nextInt(9)
                imageArray[randomIndex].visibility = View.VISIBLE

                handler.postDelayed(runnable,500)
            }

        }
        handler.post(runnable)

    }

    fun increaseScore(view: View){
        score = score + 1
        binding.scoreText.text = "Score: $score"
    }
}