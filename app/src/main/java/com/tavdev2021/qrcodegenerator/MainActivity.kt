package com.tavdev2021.qrcodegenerator

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.tavdev2021.qrcodegenerator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGenerateQRCode.setOnClickListener {
            val data = binding.etDatos.text.toString().trim()

            if (data.isEmpty()){

               Toast.makeText(this,"Debe ingresar algun dato",Toast.LENGTH_SHORT).show()

            }else{

                val writer = QRCodeWriter()
                try {

                    val bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE,512,512)
                    val width = bitMatrix.width
                    val height = bitMatrix.height
                    val bmp = Bitmap.createBitmap(width,height,Bitmap.Config.RGB_565)

                    for (x in 0 until width){
                        for (y in 0 until height){
                            bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                        }
                    }

                    binding.ivQRCode.setImageBitmap(bmp)

                }catch (e:WriterException){
                    e.printStackTrace()
                }

            }
        }

        binding.btnreset.setOnClickListener {
            resetValues()
        }

    }

    private fun resetValues() {
        binding.ivQRCode.setImageResource(R.drawable.codigoqr)
        binding.etDatos.text.clear()
    }
}