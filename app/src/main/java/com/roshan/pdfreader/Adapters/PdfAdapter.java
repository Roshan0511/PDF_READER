package com.roshan.pdfreader.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.roshan.pdfreader.Models.PdfModel;
import com.roshan.pdfreader.OnSelectedPdf;
import com.roshan.pdfreader.R;

import java.io.File;
import java.util.List;

public class PdfAdapter extends RecyclerView.Adapter<PdfModel> {

    private final Context context;
    private final List<File> itemList;
    private OnSelectedPdf listener;

    public PdfAdapter(Context context, List<File> itemList, OnSelectedPdf listener) {
        this.context = context;
        this.itemList = itemList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PdfModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.rv_list, parent, false);
        return new PdfModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PdfModel holder, @SuppressLint("RecyclerView") int position) {
        holder.textView.setText(itemList.get(position).getName());
        holder.textView.setSelected(true);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnSelectedPdf(itemList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
