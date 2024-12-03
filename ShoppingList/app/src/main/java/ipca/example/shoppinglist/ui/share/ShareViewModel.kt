package ipca.example.shoppinglist.ui.share

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import ipca.example.shoppinglist.models.ListItems
import ipca.example.shoppinglist.models.User
import ipca.example.shoppinglist.repositories.ListItemsRepository
import ipca.example.shoppinglist.repositories.UserRepository

data class ShareState(
    val users : List<User> = arrayListOf(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ShareViewModel : ViewModel(){

    var state = mutableStateOf(ShareState())
        private set

    fun getUsers(){
       UserRepository.getUsers{ users ->
           state.value = state.value.copy(
               users = users
           )
       }
    }

    fun shareWithUser(listId :String , user : User){
        ListItemsRepository.addUserToList(listId,user.docId!!)
    }



}