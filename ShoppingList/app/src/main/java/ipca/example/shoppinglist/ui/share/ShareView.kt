package ipca.example.shoppinglist.ui.share

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ipca.example.shoppinglist.R
import ipca.example.shoppinglist.Screen
import ipca.example.shoppinglist.ui.theme.ShoppingListTheme

@Composable
fun ShareView(
    modifier: Modifier = Modifier,
    listId : String,
    navController: NavController = rememberNavController()
                  ){

    val viewModel : ShareViewModel = viewModel()
    val state = viewModel.state.value


    Box(modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd) {

        LazyColumn(modifier = modifier.fillMaxSize()) {
            itemsIndexed(
                items = state.users
            ){  index, item ->

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable {
                            viewModel.shareWithUser(listId,item)
                            navController.popBackStack()
                        },
                    text = item.name?:"")

            }
        }




    }

    LaunchedEffect (key1 = true){
        viewModel.getUsers()
    }

}

@Preview(showBackground = true)
@Composable
fun ListListViewPreview(){
    ShoppingListTheme {
        ShareView(
            listId = ""
        )
    }
}