package com.nicolas.canivete_suico;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
                .inflate(R.layout.contact, parent, false);
        return new PictureViewHolder(itemView);

    }

//    @Override
//    public void onBindViewHolder(@NonNull PictureAdapter.PictureViewHolder holder, int position) {
//
//    }


    @Override
    public void onBindViewHolder(@NonNull PictureViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.nameText.setText(contact.getName());
        holder.phoneText.setText(contact.getPhone());

        holder.deleteButton.setOnClickListener(v -> {

            Contact contactToRemove = contacts.get(position);
            databaseManager.deleteContactByName(contactToRemove.getName());

            contacts.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, contacts.size());
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class PictureViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView phoneText;
        Button deleteButton;
        DatabaseManager databaseManager;

        public PictureViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.textView);
            phoneText = itemView.findViewById(R.id.editTextPhone);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
