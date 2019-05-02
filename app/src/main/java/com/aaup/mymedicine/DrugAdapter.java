package com.aaup.mymedicine;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DrugAdapter extends RecyclerView.Adapter<DrugAdapter.MyViewHolder> implements Filterable {
    private ArrayList<Medicine> mDataset;
    private ArrayList<Medicine> mFilteredData;
    private View.OnClickListener mOnItemClickListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // You provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // Each data item is just a string in this case
        public TextView textView;

        public MyViewHolder(LinearLayout v) {
            super(v);
            textView = v.findViewById(R.id.medicine_name);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DrugAdapter(ArrayList<Medicine> myDataset) {
        mDataset = myDataset;
        this.mFilteredData = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DrugAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout parentLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        TextView v = (TextView) parentLayout.findViewById(R.id.medicine_name);

        final MyViewHolder vh = new MyViewHolder(parentLayout);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(mFilteredData.get(position).getName());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mFilteredData.size();
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilteredData = mDataset;
                } else {
                    ArrayList<Medicine> filteredList = new ArrayList<>();
                    for (Medicine row : mDataset) {
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    mFilteredData = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredData;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredData = (ArrayList<Medicine>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }
}