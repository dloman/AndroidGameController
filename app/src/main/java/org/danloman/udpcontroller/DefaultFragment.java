package org.danloman.udpcontroller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dlom on 1/11/15.
 */
public class DefaultFragment extends Fragment
{
    @Override
    public View onCreateView(
      LayoutInflater inflater,
      ViewGroup container,
      Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.default_fragment, container, false);
    }
}
