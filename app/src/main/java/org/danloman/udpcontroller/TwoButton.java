package org.danloman.udpcontroller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by dlom on 1/11/15.
 */
public class TwoButton extends Fragment
{
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.two_button, container, false);
        Button FirstButton = (Button) rootView.findViewById(R.id.button1);
        FirstButton.setOnClickListener(ButtonListener);
        Button SecondButton = (Button) rootView.findViewById(R.id.button2);
        SecondButton.setOnClickListener(ButtonListener);
        return rootView;
    }

    View.OnClickListener ButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button1:
                    MainActivity.sendData("W00t Button 1");
                    break;
                case R.id.button2:
                    MainActivity.sendData("W00t Button 2");
                    break;
            }
        }
    };
}
