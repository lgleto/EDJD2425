package ipca.example.shoppinglist

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

data class ListListsState(
    val listItemsList : List<ListItems> = arrayListOf(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ListListsViewModel : ViewModel(){

    var state = mutableStateOf(ListListsState())
        private set


    fun getLists(){

        val db = Firebase.firestore
        val auth = Firebase.auth
        val currentUser = auth.currentUser
        val userId = currentUser?.uid

        db.collection("lists")
            //.whereEqualTo("capital", true)
            .get()
            .addOnSuccessListener { documents ->
                val listItemsList = arrayListOf<ListItems>()
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    val listItem = document.toObject(ListItems::class.java)
                    listItemsList.add(listItem)
                }
                state.value = state.value.copy(
                    listItemsList = listItemsList
                )
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }


    }

}