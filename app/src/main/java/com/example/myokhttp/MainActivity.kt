package com.example.myokhttp

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import java.io.IOException


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //   инициализация клиента
        val client = OkHttpClient()
        // формируем сам запрос
        val request = Request.Builder()
                .url("https://reqres.in/api/users/2")
                .build()

        //Создаем отправку запроса, мы должны имплементировать интерфейс Callback,
//когда будете его импортировать, проверьте, чтобы он был от библиотеки OkHttp, потому что есть
//Интерфейсы с таким же названием и в других библиотеках
        client.newCall(request).enqueue(object : Callback {
            //Переопределяем метод, что будет, если мы не сможем получить ответ на запрос
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
            //Переопределяем метод, что будет, если мы сможем получить ответ на запрос
            override fun onResponse(call: Call, response: Response) {
                //Здесь тоже надо обернуть в try-catch
                try {
                    val responseBody = response.body()
                    println("!!! ${responseBody?.string()}")
                } catch (e: Exception) {
                    println(response)
                    e.printStackTrace()
                }
            }
        })




    }

    // вызываем бесконечный цыкл для  симулирования ANR(приложение не отвечает)
   /*override fun onTouchEvent(event: MotionEvent?): Boolean {
        while (true) {}
    }*/
}