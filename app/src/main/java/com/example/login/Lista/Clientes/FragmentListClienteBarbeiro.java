package com.example.login.Lista.Clientes;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.login.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentListClienteBarbeiro extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;


    public FragmentListClienteBarbeiro() {



    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new FragmentManha(), "MANHA");
        adapter.addFragment(new FragmentTarde(), "TARDE");
        adapter.addFragment(new FragmentNoite(), "NOITE");
        viewPager.setAdapter(adapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_fragment_list_cliente_barbeiro, container, false);

        getActivity().setTitle("Horários");

        viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);



        return view;


    }


}