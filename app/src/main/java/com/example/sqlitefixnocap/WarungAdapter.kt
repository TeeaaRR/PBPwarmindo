import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.sqlitefixnocap.DBHelper
import com.example.sqlitefixnocap.DetailWarung
import com.example.sqlitefixnocap.R

class WarungAdapter(private val context: Context, private val warungList: List<DBHelper.Warung>) :
    RecyclerView.Adapter<WarungAdapter.WarungViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WarungViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_warung, parent, false)
        return WarungViewHolder(view)
    }

    override fun onBindViewHolder(holder: WarungViewHolder, position: Int) {
        val warung = warungList[position]
        holder.bind(warung)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailWarung::class.java)
            val bundle = Bundle()

            bundle.putString("WARUNG_ID", warung.id)
            bundle.putString("WARUNG_NAMA", warung.nama)
            bundle.putString("WARUNG_LOGO", warung.logo) // Use "WARUNG_LOGO" here
            bundle.putString("WARUNG_GAMBAR", warung.gambar)

            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return warungList.size
    }

    inner class WarungViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val idWarungTextView: TextView = itemView.findViewById(R.id.textIdWarung)
        private val namaWarungTextView: TextView = itemView.findViewById(R.id.textNamaWarung)
        private val logoImageView: ImageView = itemView.findViewById(R.id.imageLogo)
        private val gambarImageView: ImageView = itemView.findViewById(R.id.imageGambar)

        fun bind(warung: DBHelper.Warung) {
            idWarungTextView.text = warung.id.toString()
            namaWarungTextView.text = warung.nama

            // Load and display the image using Glide with caching
            Glide.with(context)
                .load(warung.gambar)
                .placeholder(R.drawable.placeholder_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(gambarImageView)

            Log.d("WarungAdapter", "Image URL: ${warung.gambar}")

            Glide.with(context)
                .load(warung.logo)
                .placeholder(R.drawable.placeholder_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(logoImageView)

            Log.d("WarungAdapter", "Image URL: ${warung.logo}")
        }
    }
}
