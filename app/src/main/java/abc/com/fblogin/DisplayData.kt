package abc.com.fblogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DisplayData : AppCompatActivity() {

    lateinit var showMe: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_data)
 val intent = intent

        val facebookFirstName = intent.getStringExtra("first_name")
        val email = intent.getStringExtra("email")
//
//        //textview
        showMe = findViewById<TextView>(R.id.showMe)

        //setText
        showMe.text = "First name: "+facebookFirstName+"\nEmaail: "+email

//        val first_name = "first_name : ${intent.getStringExtra("first_name")} \n " +
//                "Email: ${intent.getStringExtra("email")}"
//        showMe.text = first_name

//        val text = "name : ${intent.getString("first_name")} \n " +
//                "Email : ${details.getString("email")}"
//        showMe.text = text





    }
}