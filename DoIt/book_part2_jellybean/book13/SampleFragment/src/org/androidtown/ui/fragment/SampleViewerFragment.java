package org.androidtown.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SampleViewerFragment extends Fragment {

	String[] imageTitles = {"Dream 01", "Dream 02", "Dream 03"};
	int[] imageLocations = {R.drawable.dream01, R.drawable.dream02, R.drawable.dream03};

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.image_viewer_fragment, container, false);
    }

    public void update(int index) {
        TextView title = (TextView) getView().findViewById(R.id.title);
        ImageView image = (ImageView) getView().findViewById(R.id.image);

        title.setText(imageTitles[index]);
        image.setImageResource(imageLocations[index]);
    }

}