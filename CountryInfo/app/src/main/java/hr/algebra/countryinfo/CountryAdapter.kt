package hr.algebra.countryinfo

import android.app.AlertDialog
import android.content.ContentUris
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.algebra.countryinfo.model.Country
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import java.io.File

class CountryAdapter(private val context: Context, private val countries: MutableList<Country>)
    : RecyclerView.Adapter<CountryAdapter.ViewHolder>(){

    class ViewHolder(countryView: View) : RecyclerView.ViewHolder(countryView) {
        private val ivCountry = countryView.findViewById<ImageView>(R.id.ivCountry)
        private val tvCountry = countryView.findViewById<TextView>(R.id.tvCountry)
        fun bind(country: Country) {
            Picasso.get()
                .load(File(country.flag))
                .error(R.drawable.sad_earth)
                .transform(RoundedCornersTransformation(50,5))
                .into(ivCountry)
            tvCountry.text = country.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
        = ViewHolder(countryView = LayoutInflater.from(parent.context)
            .inflate(R.layout.country,parent,false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener{
            //start new activity
        }
        holder.itemView.setOnLongClickListener{
            AlertDialog.Builder(context).apply {
                setTitle(R.string.delete)
                setMessage(context.getString(R.string.sure) + " '${countries[position].name}'?")
                setIcon(R.drawable.delete)
                setCancelable(true)
                setNegativeButton(R.string.cancel, null)
                setPositiveButton("OK") {_, _ -> deleteItem(position)}
                show()
            }
            true
        }
        holder.bind(countries[position])
    }

    private fun deleteItem(position: Int) {
        val country = countries[position]
        context.contentResolver.delete(
            ContentUris.withAppendedId(COUNTRY_PROVIDER_URI, country._id!!),
            null,
            null)
        File(country.flag).delete()
        countries.removeAt(position)
        notifyDataSetChanged()
    }

    override fun getItemCount() = countries.size
}