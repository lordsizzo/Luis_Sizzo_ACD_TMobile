package luis.sizzo.luis_sizzo_acd_t_mobile.common

import android.content.Context
import android.text.*
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.*
import com.google.android.material.snackbar.Snackbar
import luis.sizzo.luis_sizzo_acd_t_mobile.view.adapters.*

fun RecyclerView.settingsGrid(adapter: UserSearchAdapter){
    this.layoutManager = GridLayoutManager(this.context, 2)
    this.adapter = adapter
}

fun RecyclerView.settingsLinearVertical(adapter: UserSearchAdapter){
    this.layoutManager = LinearLayoutManager(this.context)
    this.adapter = adapter
}

fun RecyclerView.settingsLinearVerticalRepo(adapter: UserRepoAdapter){
    this.layoutManager = LinearLayoutManager(this.context)
    this.adapter = adapter
}

fun Context.toast(message: String, lenght: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, message, lenght).show()
}

fun View.snack(message: String, lenght: Int = Snackbar.LENGTH_SHORT){
    Snackbar.make(this, message, lenght).show()
}

fun View.click(listener: (View) -> Unit){
    this.setOnClickListener{
        listener(it)
    }
}

fun AppCompatEditText.textWatcher(listener: (CharSequence) -> Unit){
    this.addTextChangedListener(object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            listener(s)
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun afterTextChanged(s: Editable) {

        }
    })
}
