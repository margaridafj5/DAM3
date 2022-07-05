package com.example.projetodum.classes;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projetodum.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesAdapter.ExercisesViewHolder> {

    Context context;
    ArrayList<String> ExerciseID, NameList, DescriptionList, nPeopleList, CaloriesList;

    public ExercisesAdapter(Context context, ArrayList<String> ExerciseID, ArrayList<String> NameList, ArrayList<String> DescriptionList, ArrayList<String> nPeopleList, ArrayList<String> CaloriesList ) {
        this.context = context;
        this.ExerciseID = ExerciseID;
        this.NameList = NameList;
        this.DescriptionList = DescriptionList;
        this.CaloriesList = CaloriesList;
        this.nPeopleList = nPeopleList;

    }

    @NonNull
    @Override
    public ExercisesAdapter.ExercisesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item2, parent, false);
        return new ExercisesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExercisesAdapter.ExercisesViewHolder holder, int position) {

        String Name = NameList.get(position);
        String Description = DescriptionList.get(position);
        String Calories = CaloriesList.get(position);
        String nPeople = nPeopleList.get(position);
        String id = ExerciseID.get(position);


        holder.name.setText(Name);
        holder.description.setText(Description);
        holder.calories.setText(Calories);
        holder.nPeople.setText(nPeople);

        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Eid = FirebaseAuth.getInstance().getCurrentExercises().getEid();
                rootRef = FirebaseDatabase.getInstance("https://projetodum-9ff4d-default-rtdb.firebaseio.com/").getReference("Exercises");
                uidRef = rootRef.child(Eid);

                EidRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String numVotosDisponiveis = snapshot.child("numVotosDisponiveis").getValue().toString();
                        votesLeft = Integer.parseInt(numVotosDisponiveis);
                        Log.d("Votos", "numVotos = " + numVotosDisponiveis);

                        if(votesLeft > 0 && canVote) {
                            numberOfVotes[0] = numberOfVotes[0] + 1;
                            databaseReference = FirebaseDatabase.getInstance("https://projetoddam-default-rtdb.europe-west1.firebasedatabase.app/").getReference("ideias").child(id).child("numVotes");
                            databaseReference.setValue(numberOfVotes[0]);
                            uidRef.child("numVotosDisponiveis").setValue(votesLeft - 1);
                            canVote = false;
                            ((Votar)context).refreshActivity();

                        }
                        else if (votesLeft < 1){

                            Toast.makeText(v.getContext(), "Esse exercicio já foi adicionado", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });



    }

    @Override
    public int getItemCount() {
        return ExerciseID.size();
    }

    public static class ExercisesViewHolder extends RecyclerView.ViewHolder{

        TextView name,description,calories,nPeople;


        public ExercisesViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            nPeople = itemView.findViewById(R.id.nPeople);
            calories = itemView.findViewById(R.id.calories);


        }
    }



}