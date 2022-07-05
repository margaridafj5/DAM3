package com.example.projetodum.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetodum.R;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context context;
    private ArrayList<User> list;
    private OnNoteListener mOnNoteListener;


    public UserAdapter(Context context, ArrayList<User> list, OnNoteListener onNoteListener) {
        this.context = context;
        this.list = list;
        this.mOnNoteListener = onNoteListener;
    }

    public void setFilteredList(ArrayList<User> filteredList) {
        this.list = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new UserViewHolder(v, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {
        User user = list.get(position);
        holder.name.setText(user.getfName() + " " + user.getlName());
        holder.email.setText(user.getEmail());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, email;
        OnNoteListener onNoteListener;
        public UserViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            this.onNoteListener = onNoteListener;
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.Description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());

        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
