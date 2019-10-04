package com.example.login.Estilo;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.login.Modelo.ModeloEstilo;
import com.example.login.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBarba extends Fragment {
    private RecyclerView recyclerView;
    private List<ModeloEstilo> modeloEstilos;
    private EstiloAdapter adapter;
    ModeloEstilo modeloEstilo;


    public FragmentBarba() {
        // Required empty public constructor
    }


    @Override
    @SuppressWarnings("newApi")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activity_recycler_view, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        modeloEstilo = new ModeloEstilo();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(adapter = new EstiloAdapter(getContext(), modeloEstilos, onClickModeloEstilo()));

        FirebaseDatabase.getInstance().getReference("Estilos").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ModeloEstilo> lista = new ArrayList<>();

                for(DataSnapshot d : dataSnapshot.getChildren()) {
                    ModeloEstilo m = d.getValue(ModeloEstilo.class);
                    if(m.getBarbeiro().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                        lista.add(m);

                }

                lista = (List<ModeloEstilo>) lista.stream().filter(e->e.getCategoria().equals("Barba - R$ 10,00")).collect(Collectors.toList());
                System.out.println(lista);

                carregarRecyclerView(lista);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

    private EstiloAdapter.ModeloEstiloOnClickListener onClickModeloEstilo() {
        final Intent intent = new Intent(getContext(), ModeloEstilo.class);
        return new EstiloAdapter.ModeloEstiloOnClickListener() {
            @Override
            public void onClickModeloEstilo(EstiloAdapter.ModeloEstilosViewHolder holder, int idx) {
                ModeloEstilo p = modeloEstilos.get(idx);

            }
        };
    }

    public void carregarRecyclerView(List<ModeloEstilo> modeloEstilos) {
        recyclerView.setAdapter(adapter = new EstiloAdapter(getContext(), modeloEstilos, onClickModeloEstilo()));

    }

}
