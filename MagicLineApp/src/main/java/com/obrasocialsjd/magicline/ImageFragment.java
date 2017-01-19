package com.obrasocialsjd.magicline;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Usuari on 16/01/2017.
 */

public class ImageFragment extends Fragment {

    private static ImageView imageView;
    private int image;

    public ImageFragment() {}

    public static ImageFragment newInstance(int image) {
        Bundle args = new Bundle();
        args.putInt("image", image);
        ImageFragment fragment = new ImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.image_layout, container, false);

        Bundle args = getArguments();
        image = args.getInt("image");
        imageView = (ImageView) rootView.findViewById(R.id.image);
        imageView.setImageResource(image);

        return rootView;
    }
}
