package ipca.example.shoppinglist.ui.lists.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ipca.example.shoppinglist.ui.theme.ShoppingListTheme

@Composable
fun AddItemView(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    listId:String
){

    val viewModel : AddItemViewModel = viewModel()
    val state = viewModel.state.value

    Column(modifier = modifier.fillMaxSize()) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = {
                Text("enter item name")
            },
            value = state.name,
            onValueChange = viewModel::onNameChange
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            placeholder = {
                Text("enter quantity")
            },
            value = state.qtd.toString(),
            onValueChange = {
                viewModel.onQttChange(it.toDouble())
            }
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = {
            viewModel.addItem(listId)
                navController.popBackStack()
        }) {
            Text("add")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun AddListViewPreview(){
    ShoppingListTheme {
        AddItemView(listId = "none")
    }
}