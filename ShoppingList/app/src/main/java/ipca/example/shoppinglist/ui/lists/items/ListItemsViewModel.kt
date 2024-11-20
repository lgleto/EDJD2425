package ipca.example.shoppinglist.ui.lists.items

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import ipca.example.shoppinglist.TAG
import ipca.example.shoppinglist.models.Item

data class ListItemsState(
    val items : List<Item> = arrayListOf(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ListItemsViewModel : ViewModel(){

    var state = mutableStateOf(ListItemsState())
        private set


    fun getItems(listId : String){

        val db = Firebase.firestore

        db.collection("lists")
            .document(listId)
            .collection("items")
            .addSnapshotListener{ value, error->
                if (error!=null){
                    state.value = state.value.copy(
                        error = error.message
                    )
                    return@addSnapshotListener
                }

                val items = arrayListOf<Item>()
                for (document in value?.documents!!) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    val item = document.toObject(Item::class.java)
                    item?.docId = document.id
                    items.add(item!!)
                }
                state.value = state.value.copy(
                    items = items
                )
            }

    }

    fun toggleItemChecked(listId: String, item: Item){
        val db = Firebase.firestore
        
        item.checked = !item.checked

        db.collection("lists")
            .document(listId)
            .collection("items")
            .document(item.docId!!)
            .set(item)

    }


}