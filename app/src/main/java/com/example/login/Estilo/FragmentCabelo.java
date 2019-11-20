package com.example.login.Estilo;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
public class FragmentCabelo extends Fragment {
    private RecyclerView recyclerView;
    private List<ModeloEstilo> modeloEstilos;
    private EstiloAdapter adapter;
    ModeloEstilo modeloEstilo;
    private Button excluir;


    public FragmentCabelo() {
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
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter = new EstiloAdapter(getContext(), modeloEstilos, onClickModeloEstilo()));

        FirebaseDatabase.getInstance().getReference("Estilos").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ModeloEstilo> lista = new ArrayList<>();

                for(DataSnapshot d : dataSnapshot.getChildren()) {
                    ModeloEstilo m = d.getValue(ModeloEstilo.class);
                    if(m.getBarbeiro().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                        lista.add(m);
                }

                lista = (List<ModeloEstilo>) lista.stream().filter(e->e.getCategoria().equals("Cabelo - R$ 25,00")).collect(Collectors.toList());
                System.out.println(lista);
                modeloEstilos = lista;
                carregarRecyclerView(lista);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

    protected EstiloAdapter.ModeloEstiloOnClickListener onClickModeloEstilo() {
        final Intent intent = new Intent(getContext(), ModeloEstilo.class);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        return new EstiloAdapter.ModeloEstiloOnClickListener() {
            @Override
            public void onClickModeloEstilo(EstiloAdapter.ModeloEstilosViewHolder holder, int idx) {
//                ModeloEstilo p = modeloEstilos.get(idx);
//                intent.putExtra("Objeto", p); //putextraserializable


                System.out.println("entrou pora");
                ModeloEstilo p = modeloEstilos.get(idx);

                FragmentTransaction ft = fm.beginTransaction();
                FragmentAddEstilo fragmentAddEstilo = new FragmentAddEstilo();

                Bundle bundle = new Bundle();
                bundle.putSerializable("objeto", p);
                fragmentAddEstilo.setArguments(bundle);

                ft.replace(R.id.conteiner, fragmentAddEstilo);
                ft.commit();

            }
        };

    }



    public void carregarRecyclerView(List<ModeloEstilo> modeloEstilos) {
        recyclerView.setAdapter(adapter = new EstiloAdapter(getContext(), modeloEstilos, onClickModeloEstilo()));

    }



}


/*Romance 30 -> 2° Geração modernista (prosa)               Ambiente/espaço -> Degradante

Neo-regionalista ou neo-realista -> Política de denúncia -> Realidade das minorias ->
                                                            (Exploradores - Burguesia) - (Exploradores - Proletários)
Personagens -> Representam um grupo -> O norderstino retirante, os moradorts de rua, os trabalhadores de fábrica

                Personagens são
                representados pelas
                suas características

                Física, sociais

Contexto histórico:

* Crise de 1929 -> Quebra da bolsa
* 1930 -> Getulio Vargas
* 1937 -> Estado novo -> Industrialização
* Revolução russa 18 de outubro de 17 -> Socialismo Soviético
*/

