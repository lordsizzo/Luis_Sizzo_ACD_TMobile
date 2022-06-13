package luis.sizzo.luis_sizzo_acd_t_mobile.common

import android.content.Context
import android.graphics.Color

class Utilities {

    companion object{
        fun getRandomColor(context: Context): Int {
            val allColors = context.resources.getStringArray(luis.sizzo.luis_sizzo_acd_t_mobile.R.array.colors)
            val rnds = (allColors.indices).random()
            return Color.parseColor(allColors[rnds])
        }
    }
}