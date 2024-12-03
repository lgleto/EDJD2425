package ipca.example.shoppinglist.repositories

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import ipca.example.shoppinglist.TAG
import ipca.example.shoppinglist.models.Item


object ItemRepository {

    private val db = Firebase.firestore

    fun addItem(listId: String, item: Item){
        db.collection("lists")
            .document(listId)
            .collection("items")
            .add(item)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun removeItem(listId: String, itemId: String){
        db.collection("lists")
            .document(listId)
            .collection("items")
            .document(itemId)
            .delete()
    }


    fun getItems(listId : String, onResult: (List<Item>, error:String?) ->Unit){
        db.collection("lists")
            .document(listId)
            .collection("items")
            .addSnapshotListener{ value, error->
                if (error!=null){
                    onResult(arrayListOf<Item>(), error.message)
                    return@addSnapshotListener
                }

                val items = arrayListOf<Item>()
                for (document in value?.documents!!) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    val item = document.toObject(Item::class.java)
                    item?.docId = document.id
                    items.add(item!!)
                }
                onResult(items, null)
            }

    }

    fun setChecked(listId: String, item: Item, isCheck:Boolean){
        item.checked = isCheck
        db.collection("lists")
            .document(listId)
            .collection("items")
            .document(item.docId!!)
            .set(item)
    }

}