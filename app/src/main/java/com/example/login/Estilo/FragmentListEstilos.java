package com.example.login.Estilo;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.login.Lista.Clientes.FragmentManha;
import com.example.login.Lista.Clientes.FragmentNoite;
import com.example.login.Lista.Clientes.FragmentTarde;
import com.example.login.Lista.Clientes.ViewPagerAdapter;
import com.example.login.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentListEstilos extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;


    public FragmentListEstilos() {
        // Required empty public constructor
    }

    private void setupViewPager(ViewPager viewPager2) {
        ViewPagerAdapter adapter2 = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter2.addFragment(new FragmentCabelo(), "CABELO");
        adapter2.addFragment(new FragmentBarba(), "BARBA");
        adapter2.addFragment(new FragmentSobrancelha(), "SOBRANCELHA");
        viewPager2.setAdapter(adapter2);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_list_estilos, container, false);

        getActivity().setTitle("Estilos");

        viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager = (ViewPager) view.findViewById(R.id.viewpager2);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);



        return view;
    }

}
