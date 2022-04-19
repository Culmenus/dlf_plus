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
import com.google.gson.Gson
import com.hbv2.dlf_plus.R
import com.hbv2.dlf_plus.data.model.MessageDTO
import com.hbv2.dlf_plus.databinding.ActivityMainBinding
import com.hbv2.dlf_plus.networks.BackendApiClient
import com.hbv2.dlf_plus.networks.misc.SessionManager
import com.hbv2.dlf_plus.networks.requestBody.LoginRequestBody
import com.hbv2.dlf_plus.networks.responses.LoginResponse
import com.hbv2.dlf_plus.networks.websocket.WSChatClient
import com.hbv2.dlf_plus.ui.forumcardlistfragment.view.ForumCardListFragment
import com.hbv2.dlf_plus.ui.userprofile.view.UserProfileActivity
import io.reactivex.Completable
import io.reactivex.CompletableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent


class MainActivity : AppCompatActivity() {

    // sama og að gera
    //      var toggle: ActionBarDrawerToggle? = null
    // nema að þetta þarf fult af null checks
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityMainBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var backendApiClient: BackendApiClient
    private lateinit var mStompClient: StompClient

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
        val url = "ws://10.0.2.2:8080/thread/websocket"
        /**
        println(url);
        val intervalMillis = 5000L
        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)
        mStompClient.connect()
        val stompConnection = mStompClient.lifecycle().subscribe {
            when (it.type) {
                LifecycleEvent.Type.OPENED -> {
                    Log.d("stompConn", "OPENED")
                }
                LifecycleEvent.Type.CLOSED ->{
                    Log.d("stompConn","CLOSED")
                }
                LifecycleEvent.Type.ERROR -> {
                    Log.d("stompConn","ERROR")
                }
                else -> {
                    Log.d("stompConn","Something went wrong")
                }
            }
        }
        try {
            val someth = mStompClient.topic("/thread/1/get")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                Log.d("Someth", it.payload.toString())
            }
        }
        catch(e: Throwable){
            Log.d("someth", e.message!!);
        }*/
        val message = MessageDTO(
            message = "Hello world",
            isEdited = false,
            userID = 1,
            username = "Danni"
        )
        /**
        mStompClient.send("/app/thread/1/send", Gson().toJson(message))
            .compose(applySchedulers())
            .subscribe() */

        val ws = WSChatClient()
        var messages = ArrayList<MessageDTO>()
        val addItem = {msg: MessageDTO -> messages.add(msg)}
        ws.subscribe(1,addItem)
        ws.sendMessage(1,message)
        ws.sendMessage(1,message)
        Log.d("TESTIN",messages.toString())



    }
    protected fun applySchedulers(): CompletableTransformer? {
        return CompletableTransformer { upstream: Completable ->
            upstream
                .unsubscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
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