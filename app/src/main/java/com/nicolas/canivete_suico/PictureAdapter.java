package com.nicolas.canivete_suico;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureViewHolder> {

    private List<Picture> pictures;
    private DatabaseManager databaseManager;

    public PictureAdapter(List<Picture> pictures, DatabaseManager databaseManager) {
        this.pictures = pictures;
        this.databaseManager = databaseManager;
    }

    @NonNull
    @Override
    public PictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_picture, parent, false);
        return new PictureViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull PictureViewHolder holder, int position) {
        Picture picture = pictures.get(position);
        holder.dateText.setText(picture.getDate());

        Bitmap imageBitmap = decodeBase64ToBitmap(picture.getBase64());
        holder.imageView.setImageBitmap(imageBitmap);
    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }

    private Bitmap decodeBase64ToBitmap(String base64) {
        byte[] decodedBytes = Base64.decode(base64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static class PictureViewHolder extends RecyclerView.ViewHolder {
        TextView dateText;
        ImageView imageView;
        DatabaseManager databaseManager;

        public PictureViewHolder(@NonNull View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.picture_date_text_view);
            imageView = itemView.findViewById(R.id.picture_image_view);
        }
    }
}
