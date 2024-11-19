package ipca.example.shoppinglist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

data class AddListState(
    val name : String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

class AddListViewModel : ViewModel(){

    var state = mutableStateOf(AddListState())
        private set

    fun onNameChange(name: String) {
        state.value = state.value.copy(name = name)
    }

    fun addList(){

    }

}