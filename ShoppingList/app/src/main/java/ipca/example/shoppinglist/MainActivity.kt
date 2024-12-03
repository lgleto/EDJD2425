package ipca.example.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
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

const val TAG = "ShoppingList"

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingListTheme {
                var title by remember{ mutableStateOf("Shopping Lists") }
                var isTopBarHidden by remember { mutableStateOf(false) }
                var showProfile by remember { mutableStateOf(false) }
                var showShareList by remember { mutableStateOf(false) }
                var navController = rememberNavController()
                var actualList by remember{ mutableStateOf("") }
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
                    if (currentUser != null){
                        navController.navigate(Screen.Home.route)
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

