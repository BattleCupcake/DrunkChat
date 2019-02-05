package com.example.drunkchat.Controller

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.drunkchat.R
import com.example.drunkchat.Services.AuthService
import com.example.drunkchat.Services.UserDataServices
import kotlinx.android.synthetic.main.activity_create_user.*
import java.util.*

class CreateUserActivity : AppCompatActivity() {

    var userAvatar = "profileDefult"
    var avatarColor = "[0.5, 0.5, 0.5, 1]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
    }

    fun generateUserAvatar(view: View){
        val random = Random()
        val color = random.nextInt(2)
        val avatar = random.nextInt(28)

        if (color == 0) {
            userAvatar = "light$avatar"
        } else {
            userAvatar = "dark$avatar"
        }
        val resourceId = resources.getIdentifier(userAvatar, "drawable", packageName)
        createAvatarImageView.setImageResource(resourceId)
    }

    fun generateColorClicked(view: View){
        val random = Random()
        var r = random.nextInt(255)
        var g = random.nextInt(255)
        var b = random.nextInt(255)

        createAvatarImageView.setBackgroundColor(Color.rgb(r, g, b))

        val saveR = r.toDouble() / 255
        val saveG = g.toDouble() / 255
        val saveB = b.toDouble() / 255

        avatarColor = "[$saveR, $saveG, $saveB, 1]"
    }

    fun createUserClicked(view: View){
        val userName = createUserNameTxt.text.toString()
        val email = createEmailText.text.toString()
        val password = createPasswordText.text.toString()
        AuthService.registerUser(this, email, password) {registerSuccsess ->
            if(registerSuccsess){
                AuthService.loginUser(this, email, password) {loginSuccsess ->
                    if (loginSuccsess){
                        AuthService.createUser(this, userName, email, userAvatar, avatarColor){ createSuccess ->
                            if (createSuccess){
                                println(UserDataServices.avatarColor)
                                println(UserDataServices.avatarName)
                                println(UserDataServices.name)
                                finish()
                            }
                        }
                    }
                }
            }
        }
    }
}
