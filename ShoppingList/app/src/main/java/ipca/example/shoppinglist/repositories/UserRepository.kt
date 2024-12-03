package ipca.example.shoppinglist.repositories

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import ipca.example.shoppinglist.TAG
import ipca.example.shoppinglist.models.Item
import ipca.example.shoppinglist.models.ListItems
import ipca.example.shoppinglist.models.User


object UserRepository {

    val db = Firebase.firestore
    val auth = Firebase.auth

    fun get(onResult:(User)->Unit){
        val currentUser =  auth.currentUser
        val userId = currentUser?.uid
        if (userId == null)
            return



        db.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                val user = document.toObject(User::class.java)
                user?.let {
                    onResult(it)
                }
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun getUsers(onResult:(List<User>)->Unit){
        val currentUser =  auth.currentUser
        val userId = currentUser?.uid
        if (userId == null)
            return

        db.collection("users")
            .get()
            .addOnSuccessListener { documents ->
                val users = arrayListOf<User>()
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    val user = document.toObject(User::class.java)
                    user.docId = document.id
                    users.add(user)
                }
                onResult(users)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }




    fun save(user : User){

        val db = Firebase.firestore

        val currentUser = auth.currentUser
        val userId = currentUser?.uid
        if (userId == null)
            return

        user.email = currentUser.email

        db.collection("users")
            .document(userId)
            .set(
                user
            )
    }



}