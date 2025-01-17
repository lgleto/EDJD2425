package ipca.example.shoppinglist

import android.content.Context

const val preferencesName = "ShoppingListPreferences"

var Context.PREF_FIREBASE_MESSAGING_TOKEN : String
    get() {
        val sharedPref = getSharedPreferences(preferencesName, Context.MODE_PRIVATE) ?:  return ""
        return sharedPref.getString("pref_firebase_messaging_token", "")?:""
    }
    set(value) {
        val sharedPref = getSharedPreferences(preferencesName, Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString("pref_firebase_messaging_token", value)
            commit()
        }
    }

var Context.PREF_DEVICE_ID : String
    get() {
        val sharedPref = getSharedPreferences(preferencesName, Context.MODE_PRIVATE)?:return ""
        return sharedPref.getString("pref_device_id", "")?:""
    }
    set(value) {
        val sharedPref = getSharedPreferences(preferencesName, Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString("pref_device_id", value)
            commit()
        }
    }