package com.example.mysenors

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.Sensor.TYPE_ACCELEROMETER
import android.hardware.Sensor.TYPE_GRAVITY
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener {

    @RequiresApi(Build.VERSION_CODES.N)
    @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sensormanager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val listOfSensors = sensormanager.getSensorList(Sensor.TYPE_ALL)
        listOfSensors.forEach {
            Log.e("TAG", "--------------")
            Log.e("TAG", "Name : " + it.name)
            Log.e("TAG", "Type : " + it.stringType)
            Log.e("TAG", "Vendor : " + it.vendor)
            Log.e("TAG", "Max Event Count : " + it.fifoMaxEventCount.toString())
            Log.e("TAG", "Sensor ID : " + it.id.toString())
            Log.e("TAG", "Dynamic sensor : " + it.isDynamicSensor)
            Log.e("TAG", "Is Wakeup : " + it.isWakeUpSensor)
            Log.e("TAG", "Max Delay" + it.maxDelay)
            Log.e("TAG", "Min Delay : " + it.minDelay)
            Log.e("TAG", "Max Range : " + it.maximumRange)
            Log.e("TAG", "Power consumption : " + it.power)
            Log.e("TAG", "Version : " + it.version)
            Log.e("TAG", "--------------")
        }

        val gravitysensor =sensormanager.getDefaultSensor(TYPE_GRAVITY)
        val accelerometer =sensormanager.getDefaultSensor(TYPE_ACCELEROMETER)
        sensormanager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_UI)
        sensormanager.registerListener(this,gravitysensor,SensorManager.SENSOR_DELAY_UI)

    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event.let {
            when{
                    it?.sensor?.type==Sensor.TYPE_GRAVITY->{
                        val red = (Math.abs(it.values[0]) * 255 / SensorManager.GRAVITY_EARTH).toInt()
                        val green = (Math.abs(it.values[1]) * 255 / SensorManager.GRAVITY_EARTH).toInt()
                        val blue = (Math.abs(it.values[2]) * 255 / SensorManager.GRAVITY_EARTH).toInt()

                        val color = Color.rgb(red, green, blue)
                        linearLayout.setBackgroundColor(color)
                    }
                it?.sensor?.type==Sensor.TYPE_ACCELEROMETER->{

                    Toast.makeText(baseContext,"accelerometer${it.values[0]}",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



}