package com.iwxyi.letsremember.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.iwxyi.letsremember.R;

public class BoxFragment extends Fragment {
    private ImageView imgold;
    private ImageView imsliver;
    private ImageView imiron;
    private TextView gold;
    private TextView sliver;
    private TextView iron;

    public static BoxFragment newInstance(String param1, String param2) {
        BoxFragment fragment = new BoxFragment();
        return fragment;
    }

    private void initView(@NonNull final View itemView) {
        imgold=(ImageView)itemView.findViewById(R.id.gold_box);
        imsliver=(ImageView)itemView.findViewById(R.id.sliver_box);
        imiron=(ImageView)itemView.findViewById(R.id.iron_box);
        gold=(TextView)itemView.findViewById(R.id.gold_boxes);
        sliver=(TextView)itemView.findViewById(R.id.sliver_boxes);
        iron=(TextView)itemView.findViewById(R.id.iron_boxes);
        refreshInformation();
    }

    public void refreshInformation() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_box, container, false);
        initView(view);
        return view;
    }
}
