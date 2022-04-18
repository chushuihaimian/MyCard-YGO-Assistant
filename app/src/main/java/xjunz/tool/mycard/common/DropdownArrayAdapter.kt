package xjunz.tool.mycard.common

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter

class DropdownArrayAdapter(
    context: Context, val data: MutableList<String>
) : ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, data) {

    private val candidates by lazy {
        ArrayList(data)
    }

    private val containmentFilter by lazy {
        object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val results = FilterResults()
                return if (constraint != null) {
                    val res = candidates.filter { it.contains(constraint, true) }
                    results.also {
                        it.values = res
                        it.count = res.size
                    }
                } else {
                    results.also {
                        it.values = candidates
                        it.count = candidates.size
                    }
                }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults) {
                data.clear()
                data.addAll(results.values as? List<String> ?: emptyList())
                if (results.count == 0) {
                    notifyDataSetInvalidated()
                } else {
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun getFilter(): Filter {
        return containmentFilter
    }
}