package com.example.lufthansa.MainFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.example.lufthansa.MainFragments.Fragments.FlightStatusFragment;
import com.example.lufthansa.MainFragments.Fragments.ArrivalSearchFragment;
import com.example.lufthansa.MainFragments.Fragments.DepartureSearchFragment;
import com.example.lufthansa.R;
import com.example.lufthansa.StartPage;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import kotlin.OptionalExpectation;

public class FragmentCollection extends Fragment {

    private final String TAG = FragmentCollection.class.getSimpleName();

    FragmentsAdapter adapter;
    ViewPager2 pager;

    int[] iconsSelected = new int[] {
            R.drawable.ic_flight_status_selected_24dp,
            R.drawable.ic_dep_selected_24dp,
            R.drawable.ic_arr_selected_24dp
    };

    int[] iconsNons = new int[] {
            R.drawable.ic_flight_status_nonselected_24dp,
            R.drawable.ic_dep_nons_24dp,
            R.drawable.ic_arr_nonselected_24dp
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle svdInstState) {
        return inflater.inflate(R.layout.main_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle svdInstState) {
        adapter = new FragmentsAdapter(this);

        adapter.addFragToList(new FlightStatusFragment());
        adapter.addFragToList(new DepartureSearchFragment());
        adapter.addFragToList(new ArrivalSearchFragment());

        pager = view.findViewById(R.id.mainPager);
        pager.setAdapter(adapter);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPos = tab.getPosition();
                tab.setIcon(iconsSelected[tabPos]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int tabPos = tab.getPosition();
                tab.setIcon(iconsNons[tabPos]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        new TabLayoutMediator(tabLayout, pager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText(getTxt(position));
                        tab.setIcon(getIcon(position));
                    }
                }).attach();
    }

    private String getTxt(int position) {
        switch(position) {
            case 0: return "INFO";
            case 1: return "DEP";
            case 2: return "ARR";
        }
        return "DEFAULT";
    }

    private int getIcon(int position) throws ArrayIndexOutOfBoundsException {
        if(position == 0)
            return iconsSelected[position];
        else
            return iconsNons[position];
    }

    // do transaction to flight info center
    public void navigateToFlightSearchResults(View view, String searchedDate) {
        Log.d(TAG, "try switching to new fragment");
        Bundle bundle = new Bundle();
        bundle.putString("date", searchedDate);
        Navigation.findNavController(view).navigate(R.id.action_fragmentCollection_to_flightInfo, bundle);
    }

    // do transaction to flightnumber info fragment
    public void navigateToFlightInfoResult(View view) {
        Log.d(TAG, "transmitting to flight info fragment");
        Bundle bundle = new Bundle();
        bundle.putString("index", "no"); //are there more than 1 flight ?
        bundle.putString("from", "main");
        Navigation.findNavController(view).navigate(R.id.action_fragmentCollection_to_flightNumberInfoFragment, bundle);
    }

}