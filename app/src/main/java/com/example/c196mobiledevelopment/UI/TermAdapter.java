package com.example.c196mobiledevelopment.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196mobiledevelopment.R;
import com.example.c196mobiledevelopment.entities.Term;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {
    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;

    public TermAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termItemView;
        private final TextView termDateView;
        public TermViewHolder(@NonNull View itemView) {
            super(itemView);
            termItemView = itemView.findViewById(R.id.termTitleView);
            termDateView = itemView.findViewById(R.id.textDateView);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                final Term current = mTerms.get(position);
                Intent intent = new Intent(context, TermDetailsActivity.class);
                intent.putExtra("id", current.getTermId());
                intent.putExtra("title", current.getTitle());
                intent.putExtra("startDate", current.getStartDate());
                intent.putExtra("endDate", current.getEndDate());
                context.startActivity(intent);
            });
        }
    }

    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item, parent, false);
        return new TermViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if(mTerms != null) {
            Term current = mTerms.get(position);
            String title = current.getTitle();
            String date = current.getStartDate() + " to " + current.getEndDate();
            holder.termItemView.setText(title);
            holder.termDateView.setText(date);
        } else {
            holder.termDateView.setText("No Terms");
        }

    }

    @Override
    public int getItemCount() {
        if(mTerms != null) {
            return mTerms.size();
        } else {
            return 0;
        }
    }

    public void setTerms(List<Term> terms) {
        mTerms = terms;
        notifyDataSetChanged();
    }

}
