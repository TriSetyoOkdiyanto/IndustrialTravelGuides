package com.eternity.duakelinci.InnerFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.eternity.duakelinci.R;

public class Tab3Fragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.tab3fragment, container, false);
		return v;
	}

	@Override
	public boolean onNavigationItemSelected(MenuItem item) {

		return false;
	}
}
