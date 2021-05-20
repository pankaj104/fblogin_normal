package abc.com.fblogin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.BuildConfig
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        if (isLoggedIn()) {
//            Toast.makeText(this@MainActivity, "Hi loggen in", Toast.LENGTH_LONG).show()
////            val intent = Intent(this, DisplayData::class.java)
////            startActivity(intent)
//            // Show the Activity with the logged in user
//        }else{
//
//            // Show the Home Activity
//        }

        JavaHelper.printHashKey(this);

        callbackManager = CallbackManager.Factory.create()
        val loginButton=findViewById<LoginButton>(R.id.login_button)

       // val loginButton = findViewById<LoginButton>(com.facebook.R.id.login_button)
        loginButton.setReadPermissions(listOf("public_profile", "email"))
        // If you are using in a fragment, call loginButton.setFragment(this)

        // Callback registration
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {
                Log.d("TAG", "Success Login")
                startActivity(Intent(this@MainActivity, DisplayData::class.java))
                //getUserProfile(loginResult?.accessToken, loginResult?.accessToken?.userId)


            }

            override fun onCancel() {

                // Show the Activity with the logged in user

                Toast.makeText(this@MainActivity, "Login Cancelled", Toast.LENGTH_LONG).show()
            }

            override fun onError(exception: FacebookException) {
                Toast.makeText(this@MainActivity, exception.message, Toast.LENGTH_LONG).show()
            }


            //
            @SuppressLint("LongLogTag")
            fun getUserProfile(token: AccessToken?, userId: String?) {

                val parameters = Bundle()
                parameters.putString(
                    "fields",
                    "id, first_name, middle_name, last_name, name, picture, email"
                )


                GraphRequest(token,
                    "/$userId/",
                    parameters,
                    HttpMethod.GET,
                    GraphRequest.Callback { response ->
                        val jsonObject = response.jsonObject

                        // Facebook Access Token
                        // You can see Access Token only in Debug mode.
                        // You can't see it in Logcat using Log.d, Facebook did that to avoid leaking user's access token.
                        if (BuildConfig.DEBUG) {
                            FacebookSdk.setIsDebugEnabled(true)
                            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS)
                        }

                        // Facebook Id
                        if (jsonObject.has("id")) {
                            val facebookId = jsonObject.getString("id")
                            Log.i("Facebook Id: ", facebookId.toString())
                        } else {
                            Log.i("Facebook Id: ", "Not exists")
                        }


                        // Facebook First Name
                        if (jsonObject.has("first_name")) {
                            val facebookFirstName = jsonObject.getString("first_name")
                            Log.i("Facebook First Name: ", facebookFirstName)
                        } else {
                            Log.i("Facebook First Name: ", "Not exists")
                        }


                        // Facebook Middle Name
                        if (jsonObject.has("middle_name")) {
                            val facebookMiddleName = jsonObject.getString("middle_name")
                            Log.i("Facebook Middle Name: ", facebookMiddleName)
                        } else {
                            Log.i("Facebook Middle Name: ", "Not exists")
                        }


                        // Facebook Last Name
                        if (jsonObject.has("last_name")) {
                            val facebookLastName = jsonObject.getString("last_name")
                            Log.i("Facebook Last Name: ", facebookLastName)
                        } else {
                            Log.i("Facebook Last Name: ", "Not exists")
                        }


                        // Facebook Name
                        if (jsonObject.has("name")) {
                            val facebookName = jsonObject.getString("name")
                            Log.i("Facebook Name: ", facebookName)
                        } else {
                            Log.i("Facebook Name: ", "Not exists")
                        }


                        // Facebook Profile Pic URL
                        if (jsonObject.has("picture")) {
                            val facebookPictureObject = jsonObject.getJSONObject("picture")
                            if (facebookPictureObject.has("data")) {
                                val facebookDataObject = facebookPictureObject.getJSONObject("data")
                                if (facebookDataObject.has("url")) {
                                    val facebookProfilePicURL = facebookDataObject.getString("url")
                                    Log.i("Facebook Profile Pic URL: ", facebookProfilePicURL)
                                }
                            }
                        } else {
                            Log.i("Facebook Profile Pic URL: ", "Not exists")
                        }

                        // Facebook Email
                        if (jsonObject.has("email")) {
                            val facebookEmail = jsonObject.getString("email")
                            Log.i("Facebook Email: ", facebookEmail)
                        } else {
                            Log.i("Facebook Email: ", "Not exists")
                        }
                    }).executeAsync()

            }


        })

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }}

fun isLoggedIn(): Boolean {
    val accessToken = AccessToken.getCurrentAccessToken()
    val isLoggedIn = accessToken != null && !accessToken.isExpired
    return isLoggedIn
}
