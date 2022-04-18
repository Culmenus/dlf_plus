package com.hbv2.dlf_plus.ui

//import com.hbv2.dlf_plus.networks.SessionManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.gmail.bishoybasily.stomp.lib.Event
import com.gmail.bishoybasily.stomp.lib.StompClient
import com.hbv2.dlf_plus.R
import com.hbv2.dlf_plus.databinding.ActivityMainBinding
import com.hbv2.dlf_plus.networks.BackendApiClient
import com.hbv2.dlf_plus.networks.misc.SessionManager
import com.hbv2.dlf_plus.networks.requestBody.LoginRequestBody
import com.hbv2.dlf_plus.networks.responses.LoginResponse
import com.hbv2.dlf_plus.ui.forumcardlistfragment.view.ForumCardListFragment
import com.hbv2.dlf_plus.ui.userprofile.view.UserProfileActivity
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    // sama og að gera
    //      var toggle: ActionBarDrawerToggle? = null
    // nema að þetta þarf fult af null checks
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityMainBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var backendApiClient: BackendApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initDrawer()
        Log.d("MainActivity","Hello world!!")
        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_forum_cards)

        if (currentFragment == null) {
            val fragment = ForumCardListFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container_forum_cards, fragment)
                .commit()
        }
        var threadId = 1
        backendApiClient = BackendApiClient()
        sessionManager = SessionManager(applicationContext)
        backendApiClient.getApi().login(LoginRequestBody("user@user.is","pword"))
            .enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val loginResponse = response.body()
                    if(response.isSuccessful && loginResponse != null){
                        Log.d("MainActivity",loginResponse.toString())
                        sessionManager.saveAuthedUser(loginResponse)
                    }else{
                        //Error login
                    }
                }
            })
        val token = sessionManager.fetchAuthedUserDetails()?.token
        var stompConnection: Disposable
        var topic: Disposable
        val url = "http://127.0.0.1:8080/thread/"
        val intervalMillis = 5000L
        val client = OkHttpClient.Builder()
            .addInterceptor { it.proceed(it.request().newBuilder().header("Authorization", "Bearer "+token!!).build()) }
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()

        val stomp = StompClient(client, intervalMillis).apply { this@apply.url = url }

        val listen = stomp.join("thread/$threadId/get").subscribe {
            object : DisposableObserver<String?>() {
                override fun onStart() {
                    println("Start!")
                }

                override fun onNext(t: String) {
                    println(t)
                }

                override fun onError(t: Throwable) {
                    t.printStackTrace()
                }

                override fun onComplete() {
                    println("Done!")
                }
            }
        }


        listen.dispose()


// disconnect

    }


    // Drawer
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)

    }

    fun initDrawer() {
        toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout,
            R.string.open,
            R.string.close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.miItem1 -> {
                    val intent = Intent(this@MainActivity, MainActivity::class.java)
                    startActivity(intent)
                }
                R.id.miItem2 -> {
                    val intent = Intent(this@MainActivity, MyForumsActivity::class.java)
                    startActivity(intent)
                }
                R.id.miItem3 -> {
                    val intent = Intent(this@MainActivity, UserProfileActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.nav_drawer_menu, menu)
        return true
    }

}