package com.roshan.pdfreader.Models;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.roshan.pdfreader.R;

public class PdfModel extends RecyclerView.ViewHolder {

    public TextView textView;
    public CardView cardView;

    public PdfModel(@NonNull View itemView) {
        super(itemView);

        textView = itemView.findViewById(R.id.pdf_txtView);
        cardView = itemView.findViewById(R.id.card_view);
    }
}
