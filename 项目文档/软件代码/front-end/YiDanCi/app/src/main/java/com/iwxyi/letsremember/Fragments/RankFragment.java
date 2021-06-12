package com.iwxyi.letsremember.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iwxyi.letsremember.R;

public class RankFragment extends Fragment {
    private TextView words;
    private TextView poems;
    private TextView records;
    private TextView others;

    public static RankFragment newInstance(String param1, String param2) {
        RankFragment fragment = new RankFragment();
        return fragment;
    }

    private void initView(@NonNull final View itemView) {
        words=(TextView) itemView.findViewById(R.id.word);
        poems=(TextView) itemView.findViewById(R.id.poem);
        records=(TextView) itemView.findViewById(R.id.record);
        others=(TextView) itemView.findViewById(R.id.other);
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
        View view= inflater.inflate(R.layout.fragment_rank, container, false);
        initView(view);
        return view;
    }
}
