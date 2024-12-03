package ipca.example.shoppinglist.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ipca.example.shoppinglist.ui.theme.ShoppingListTheme

@Composable
fun ProfileView(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController()
){

    val viewModel : ProfileViewModel = viewModel()
    val state = viewModel.state.value

    Column(modifier = modifier.fillMaxSize()) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = {
                Text("enter name")
            },
            value = state.name?:"",
            onValueChange = viewModel::onNameChange
        )
        Text(state.user?.email?:"")
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = {
            viewModel.saveUser()
                navController.popBackStack()
        }) {
            Text("Save")
        }
    }

    LaunchedEffect (key1 = true){
        viewModel.getUser()
    }

}

@Preview(showBackground = true)
@Composable
fun ProfileViewPreview(){
    ShoppingListTheme {
        ProfileView()
    }
}