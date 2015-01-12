package org.danloman.udpcontroller;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.io.IOException;
import android.util.Log;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;



public class MainActivity extends FragmentActivity {

    static Activity mActivity;
    private static Socket mSocket = null;
    FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Discoverer().start();
        mActivity = this;

        DefaultFragment defaultFragment = new DefaultFragment();

        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().add(
           R.id.fragment_container,
           defaultFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void switchScreens(String Data)
    {
       FragmentTransaction transaction =
         getSupportFragmentManager().beginTransaction();
       //TODO add a factory to handle this
       switch (Data)
       {
           case "OneButton":
               OneButton oneButton = new OneButton();
               transaction.replace(R.id.fragment_container, oneButton);
               break;
           case "TwoButton":
               TwoButton twoButton = new TwoButton();
               transaction.replace(R.id.fragment_container, twoButton);
               break;
       }
        //transaction.addToBackStack(null);
        transaction.commit();
    }

    //TODO break the socket code out to its own class
    public static void sendData(String Data)
    {
       if (mSocket != null)
       {
         try
         {
           PrintWriter out =
             new PrintWriter(
               new BufferedWriter(
                 new OutputStreamWriter(mSocket.getOutputStream())), true);
           out.println(Data);
         }
         catch (Exception e)
         {
           Toast.makeText(mActivity, "write to socket failed", Toast.LENGTH_SHORT).show();
         }
       }
       else
       {
         Toast.makeText(mActivity, "Socket not there", Toast.LENGTH_SHORT).show();
       }
    }

    public class Discoverer extends Thread {
        private static final String TAG = "Discovery";

        public void run()
        {
            try
            {
                DatagramSocket socket = new DatagramSocket(31337);
                WifiManager wifi =
                  (WifiManager)getSystemService(Context.WIFI_SERVICE);
                if (wifi != null)
                {
                    WifiManager.MulticastLock lock =
                      wifi.createMulticastLock("mylock");
                    lock.acquire();
                }

                listenForResponses(socket);
            }
            catch (IOException e)
            {
              Log.e(TAG, "Could not send discovery request", e);
            }
        }

        private void listenForResponses(DatagramSocket socket) throws IOException
        {
            byte[] buf = new byte[1024];
            try
            {
                while (true)
                {
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);
                    InetAddress ServerAddress = packet.getAddress();
                    String Data = new String(packet.getData(), 0, packet.getLength());

                    ClientSocketThread clientSocketThread =
                      new ClientSocketThread(ServerAddress);
                    clientSocketThread.start();
                    switchScreens(Data);
                }
            }
            catch (SocketTimeoutException e)
            {
                Log.d(TAG, "Receive timed out");
            }
        }

    }

    //TODO clean this up
    public class ClientSocketThread extends Thread
    {
        public ClientSocketThread(InetAddress ServerAddress)
        {
            mServerAddress = ServerAddress;
        }

        public void run()
        {
            try
            {
                mSocket = new Socket(mServerAddress, 42069);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        Toast.makeText(
                          mActivity,
                          "Socket Connection Failed",
                          Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }

        private static final String TAG = "ClientSocket";

        private InetAddress mServerAddress;

    }
}
