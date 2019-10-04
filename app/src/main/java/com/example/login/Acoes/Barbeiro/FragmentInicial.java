package com.example.login.Acoes.Barbeiro;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.login.R;

import pl.droidsonroids.gif.GifImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentInicial extends Fragment {


    //ImageView aliasPerfil, aliasHorarios;
    //ImageView aliasLista;
    GifImageView aliasPerfil;

    public FragmentInicial() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_inicial, container, false);


        getActivity().setTitle("Home");

//        aliasPerfil = (GifImageView) view.findViewById(R.id.gifPerfil);
        //aliasHorarios = (ImageView) view.findViewById(R.id.imageView6);
       //aliasLista = (ImageView) view.findViewById(R.id.imageView7);




        /*aliasPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                FragmentPerfilBarbeiro fragmentPerfil = new FragmentPerfilBarbeiro();
                ft.replace(R.id.conteiner, fragmentPerfil);
                ft.commit();

            }
        });*/

        /*aliasHorarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                FragmentListClienteBarbeiro fragmentListClienteBarbeiro = new FragmentListClienteBarbeiro();
                ft.replace(R.id.conteiner, fragmentListClienteBarbeiro);
                ft.commit();

            }
        });*/

        // Inflate the layout for this fragment
        return view;
    }

}
