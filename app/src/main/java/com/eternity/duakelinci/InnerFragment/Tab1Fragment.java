package com.eternity.duakelinci.InnerFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.eternity.duakelinci.Kios;
import com.eternity.duakelinci.R;
import com.eternity.duakelinci.WisataIndustri;
import com.eternity.duakelinci.prosesproduksi;
import com.eternity.duakelinci.tentangkami;
import com.eternity.duakelinci.ui.MainActivity;
import com.eternity.duakelinci.waroengpati;


public class Tab1Fragment extends Fragment implements View.OnClickListener {
Context context;



	LinearLayout btnwi;
	@Override
	public View onCreateView(LayoutInflater inflater,
							 @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.tab1fragment, container, false);
		context = v.getContext();
		NavigationView navigationView = ((MainActivity)getActivity()).navigationView;
		LinearLayout btnwi = (LinearLayout) v.findViewById(R.id.btnwi);
		LinearLayout btn2kelinci = (LinearLayout) v.findViewById(R.id.btn2kelinci);
		LinearLayout btnwp = (LinearLayout) v.findViewById(R.id.btnwp);
		LinearLayout btnfo = (LinearLayout) v.findViewById(R.id.btnfo);
		LinearLayout btnproses = (LinearLayout) v.findViewById(R.id.btnproses);

		btnproses.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, prosesproduksi.class);
				startActivity(intent);
			}
		});
		btnfo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, Kios.class);
				startActivity(intent);
			}
		});
		btnwp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, waroengpati.class);
				startActivity(intent);
			}
		});
		btn2kelinci.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, tentangkami.class);
				startActivity(intent);
			}
		});
		btnwi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(context, WisataIndustri.class);
				startActivity(intent);
			}
		});

		return v;
	}

	@Override
	public void onClick(View v) {

	}

}
