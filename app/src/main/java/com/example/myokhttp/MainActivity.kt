package com.example.myokhttp

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.example.myokhttp.ОbjectMovie.Movie
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private val api_key = API.KEY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println(api_key) // вывод ключа

        val gson = Gson()
        //   инициализация клиента
        val client = OkHttpClient()
        // формируем сам запрос
        val request = Request.Builder()
                .url("https://api.themoviedb.org/3/movie/550?api_key=$api_key")
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
                    //println("!!! ${responseBody?.string()}")

                    // получаем из формата JSON данные
                    val output = gson.fromJson(responseBody?.string(), Movie::class.java)
                    // получение данных объекта
                    println(">>> ${output.homepage}")
                    println(">>> ${output.genres}")
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