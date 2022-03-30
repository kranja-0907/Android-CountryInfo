package hr.algebra.countryinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import hr.algebra.countryinfo.databinding.FragmentCountriesBinding
import hr.algebra.countryinfo.framework.fetchCountries
import hr.algebra.countryinfo.model.Country

class CountriesFragment : Fragment() {

    private lateinit var binding: FragmentCountriesBinding
    private lateinit var countries: MutableList<Country>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountriesBinding.inflate(inflater, container, false)
        countries = requireContext().fetchCountries()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvCountries.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = CountryAdapter(context, countries)
        }
    }

}