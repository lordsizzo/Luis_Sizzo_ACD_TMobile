package luis.sizzo.luis_sizzo_acd_t_mobile.common

import android.content.Context
import androidx.appcompat.app.AlertDialog

class Dialogs {

    fun showError(context: Context, message: String) {
        AlertDialog.Builder(context)
            .setTitle("Error occurred")
            .setMessage(message)
            .setNegativeButton("CLOSE") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}