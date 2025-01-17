package ipca.example.shoppinglist

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.messaging.FirebaseMessaging
import ipca.example.shoppinglist.ui.lists.AddListView
import ipca.example.shoppinglist.ui.lists.ListListsView
import ipca.example.shoppinglist.ui.lists.items.AddItemView
import ipca.example.shoppinglist.ui.lists.items.ListItemsView
import ipca.example.shoppinglist.ui.login.LoginView
import ipca.example.shoppinglist.ui.profile.ProfileView
import ipca.example.shoppinglist.ui.share.ShareView
import ipca.example.shoppinglist.ui.theme.Pink80
import ipca.example.shoppinglist.ui.theme.Purple40
import ipca.example.shoppinglist.ui.theme.PurpleGrey40
import ipca.example.shoppinglist.ui.theme.ShoppingListTheme
import java.util.UUID

const val TAG = "ShoppingList"

class MainActivity : ComponentActivity() {

    // Declare the launcher at the top of your Activity/Fragment:
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            Toast.makeText(this, "FCM can't post notifications without permission", Toast.LENGTH_LONG).show()
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {

            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }



    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        askNotificationPermission()
        val st = this.PREF_DEVICE_ID

        if (st.isEmpty()) {
            try {
                val id = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
                this.PREF_DEVICE_ID = id
            } catch (ex: Exception) {
                this.PREF_DEVICE_ID = UUID.randomUUID().toString()
            }
        }
        enableEdgeToEdge()
        setContent {
            ShoppingListTheme {
                var title by remember{ mutableStateOf("Shopping Lists") }
                var isTopBarHidden by remember { mutableStateOf(false) }
                var showProfile by remember { mutableStateOf(false) }
                var showShareList by remember { mutableStateOf(false) }
                var navController = rememberNavController()
                var actualList by remember{ mutableStateOf("") }

                val context = LocalContext.current

                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = title,
                                    fontSize = 16.sp
                                )
                            },
                            actions = {
                                if (showProfile) {
                                    Button(
                                        onClick = {
                                            navController.navigate(Screen.Profile.route)
                                        }
                                    ) {

                                        Text("Proflie")
                                    }
                                }
                                if (showShareList) {
                                    Button(
                                        onClick = {
                                            navController.navigate(Screen.Share.route.replace("{listId}", actualList))
                                        }
                                    ) {
                                        Text("Share")
                                    }
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Pink80
                            )

                        )
                    }
                ) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = Screen.Login.route
                    ){
                        composable(Screen.Login.route) {
                            isTopBarHidden = true
                            showProfile = false
                            showShareList = false
                            LoginView(
                                modifier = Modifier.padding(innerPadding),
                                onLoginSuccess = {
                                    navController.navigate(Screen.Home.route)
                                }
                            )
                        }
                        composable(Screen.Home.route) {
                            isTopBarHidden = false
                            showProfile = true
                            showShareList = false
                            title = "Shopping List"
                            ListListsView(
                                navController = navController
                            )
                        }
                        composable (Screen.AddList.route){
                            isTopBarHidden = false
                            showProfile = false
                            showShareList = false
                            title = "Add List"
                            AddListView(navController = navController)
                        }
                        composable (Screen.AddItem.route){
                            val listId = it.arguments?.getString("listId")
                            isTopBarHidden = false
                            showProfile = false
                            showShareList = false
                            title = "Add Item"
                            AddItemView(navController = navController,
                                listId = listId ?: "")
                        }
                        composable(Screen.ListItems.route) {
                            val listId = it.arguments?.getString("listId")
                            val listName = it.arguments?.getString("name")
                            actualList = listId!!
                            isTopBarHidden = false
                            showProfile = false
                            showShareList = true
                            title = listName?:""
                            ListItemsView(
                                navController = navController,
                                modifier = Modifier.padding(innerPadding),
                                listId = listId ?: ""
                            )
                        }
                        composable (Screen.Profile.route){
                            showProfile = false
                            showShareList = false
                            ProfileView()
                        }
                        composable (Screen.Share.route){
                            val listId = it.arguments?.getString("listId")
                            showProfile = false
                            showShareList = false
                            ShareView(listId = listId!!,
                                navController = navController
                                )
                        }
                    }
                }
                LaunchedEffect(Unit) {
                    val auth = Firebase.auth
                    val currentUser = auth.currentUser
                    if (currentUser != null) {
                        navController.navigate(Screen.Home.route)

                        if (context.PREF_FIREBASE_MESSAGING_TOKEN.isNullOrBlank()) {
                            FirebaseMessaging.getInstance().token.addOnCompleteListener(
                                OnCompleteListener { task ->
                                    if (!task.isSuccessful) {
                                        Log.w(
                                            TAG,
                                            "Fetching FCM registration token failed",
                                            task.exception
                                        )
                                        return@OnCompleteListener
                                    }
                                    val token = task.result
                                    MyFirebaseMessagingService.sendRegistrationToServer(
                                        context,
                                        token
                                    )
                                    Log.d(TAG, token)
                                })

                        }
                    }

                }
            }
        }
    }
}

sealed class Screen (val route:String){
    object Login : Screen("login")
    object Profile : Screen("profile")
    object Share : Screen("Share/{listId}")
    object Home : Screen("home")
    object AddList : Screen("add_list")
    object ListItems : Screen("list_items/{listId}")
    object AddItem : Screen("add_item/{listId}/{name}")
}

