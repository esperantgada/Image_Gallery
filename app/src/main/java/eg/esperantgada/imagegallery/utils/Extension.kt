package eg.esperantgada.imagegallery.utils

import androidx.appcompat.widget.SearchView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


fun SearchView.getQueryTextChangedStateFlow() : StateFlow<String>{

    var searchQuery = MutableStateFlow("cat")

    setOnQueryTextListener(object : SearchView.OnQueryTextListener{

        //Takes a query from the user and submits it to the adapter by calling setQuery method from the ViewModel
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newQuery: String?): Boolean {

            if (newQuery != null) {
                searchQuery.value = newQuery
            }
            return true
        }

    })

    return searchQuery
}