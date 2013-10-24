/*
 *  Copyright (C) 2013 The OmniROM Project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.omni.roadrunner;

import java.util.ArrayList;

import org.omni.roadrunner.adapters.WakelocksAdapter;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class WakelocksFragment extends Fragment {
	public static final String LOG_TAG = WakelocksFragment.class.getSimpleName();
	
	final ArrayList<PackageItem> mData = new ArrayList<PackageItem>();
	WakelocksAdapter mAdapter = null;

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 * 
	 * @return A new instance of fragment WakelocksFragment.
	 */
	public static WakelocksFragment newInstance() {
		WakelocksFragment fragment = new WakelocksFragment();
		/*Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);*/
		return fragment;
	}

	public WakelocksFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}*/
	}
	


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_wakelocks, container, false);
		
		SwipeListView swipeListView = (SwipeListView) root.findViewById(R.id.wakelocks_lv);
		
		mAdapter = new WakelocksAdapter(getActivity());
		
		TextView wlCount = (TextView) root.findViewById(R.id.tv_wakelocks_count);
		wlCount.setText(Integer.toString(mAdapter.getCount()));

        swipeListView.setAdapter(mAdapter);
        swipeListView.setChoiceMode(SwipeListView.CHOICE_MODE_NONE);
		swipeListView.setSwipeListViewListener(new BaseSwipeListViewListener() {
	        @Override
	        public void onOpened(int position, boolean toRight) {
	        }

	        @Override
	        public void onClosed(int position, boolean fromRight) {
	        }

	        @Override
	        public void onListChanged() {
	        }

	        @Override
	        public void onMove(int position, float x) {
	        }

	        @Override
	        public void onStartOpen(int position, int action, boolean right) {
	        }

	        @Override
	        public void onStartClose(int position, boolean right) {
	        }

	        @Override
	        public void onClickFrontView(int position) {
	        }

	        @Override
	        public void onClickBackView(int position) {
	        }

	        @Override
	        public void onDismiss(int[] reverseSortedPositions) {
	            for (int position : reverseSortedPositions) {
	                mData.remove(position);
	            }
	            mAdapter.notifyDataSetChanged();
	        }

	    });
		return root;
	}

}
