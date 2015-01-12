package org.danloman.udpcontroller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by dlom on 1/11/15.
 */
public class OneButton extends Fragment
{
    @Override
    public View onCreateView(
      LayoutInflater inflater,
      ViewGroup container,
      Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.one_button, container, false);
        Button BigButton = (Button) rootView.findViewById(R.id.BigButton);
        BigButton.setOnClickListener(BigButtonListener);
        return rootView;
    }

    View.OnClickListener BigButtonListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            MainActivity.sendData("W00t");
        }
    };
}
