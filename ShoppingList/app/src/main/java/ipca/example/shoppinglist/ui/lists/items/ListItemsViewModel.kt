package ipca.example.shoppinglist.ui.lists.items

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import ipca.example.shoppinglist.TAG
import ipca.example.shoppinglist.models.Item
import ipca.example.shoppinglist.repositories.ItemRepository

data class ListItemsState(
    val items : List<Item> = arrayListOf(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ListItemsViewModel : ViewModel(){

    var state = mutableStateOf(ListItemsState())
        private set


    fun getItems(listId : String){
        ItemRepository.getItems(listId){ items, error->
            state.value = state.value.copy(
                error = error,
                items = items
            )
        }
    }

    fun toggleItemChecked(listId: String, item: Item){
        ItemRepository.setChecked(listId,item, !item.checked)
    }



}