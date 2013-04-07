package com.juddernaught.mouse;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
//import org.apache.commons.net.telnet.TelnetClient;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends Activity {
	private static final String TAG = "CameraDemo";
	Camera camera;
	Preview preview;
	Button buttonClick;
	Button send;
	
	public static boolean created = false;
	public static Socket socket;
	public static BufferedReader r;
	public static PrintWriter  w;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/*System.out.println ("YES");
		 ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo netInfo = cm.getActiveNetworkInfo();
		    if (netInfo != null && netInfo.isConnected()) {
		    	System.out.println ("Yes");
		    } else {
		    	System.out.println ("No");
		    }
			System.out.println ("YES");*/

		preview = new Preview(this);
		((FrameLayout) findViewById(R.id.preview)).addView(preview);

		Button send = (Button) findViewById(R.id.send);
		send.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				System.out.println ("Here");
				new RetreiveFeedTask().execute ();
				
				/*try {
					
				
					/*System.out.println ("Lets go");
					Socket socket = new Socket("160.39.143.234", 11231);
					System.out.println ("Lets go2");

					socket.setKeepAlive(true);
					System.out.println ("Lets go3");

					BufferedReader r = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					System.out.println ("Lets go4");

					PrintWriter  w = new PrintWriter(socket.getOutputStream(),true);
					System.out.println ("Let's go 5");
					for (int i = 1; i < 50; i++) {
						w.println(i+" "+i);
						System.out.println ("Sent: " + i);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println ("SHIT");
					e.printStackTrace();
				}*/
			}
		});
		buttonClick = (Button) findViewById(R.id.buttonClick);
		buttonClick.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				preview.camera.takePicture(shutterCallback, rawCallback,
						jpegCallback);
			}
		});

		Log.d(TAG, "onCreate'd");
	}

	ShutterCallback shutterCallback = new ShutterCallback() {
		public void onShutter() {
			Log.d(TAG, "onShutter'd");
		}
	};

	/** Handles data for raw picture */
	PictureCallback rawCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			Log.d(TAG, "onPictureTaken - raw");
		}
	};

	/** Handles data for jpeg picture */
	PictureCallback jpegCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			FileOutputStream outStream = null;
			try {
				// write to local sandbox file system
				// outStream =
				// CameraDemo.this.openFileOutput(String.format("%d.jpg",
				// System.currentTimeMillis()), 0);
				// Or write to sdcard
				/*outStream = new FileOutputStream(String.format(
						"/sdcard/poop%d.jpg", System.currentTimeMillis()));*/
				
			outStream = new FileOutputStream(String.format(
						"/sdcard/poop.jpg"));
				outStream.write(data);
				outStream.close();
				Log.d(TAG, "onPictureTaken - wrote bytes: " + data.length);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
			}
			Log.d(TAG, "onPictureTaken - jpeg");
		}
	};
	
	class RetreiveFeedTask extends AsyncTask<String, Void, String> {

		protected String doInBackground(String... params) {
			
			/*TelnetClient telnet = new TelnetClient();
			try {
				telnet.connect("160.39.143.234", 11231);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			InputStream in = telnet.getInputStream();
			PrintStream out = new PrintStream(telnet.getOutputStream());
			try {
				telnet.setKeepAlive(true);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int i = 1; i < 50; i++) {
				out.println(i+" "+i);	
			}*/
		try {
			if (created == false) {
				System.out.println ("Lets go");
				
				
				socket = new Socket("160.39.143.234", 11231);
				System.out.println ("Lets go2");

				socket.setKeepAlive(true);
				System.out.println ("Lets go3");

				r = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				System.out.println ("Lets go4");

				w = new PrintWriter(socket.getOutputStream(),true);
				created = true;
			}
			System.out.println ("Let's go 5");
			for (int i = 1; i < 50; i++) {
				w.println(i+" "+i);
				System.out.println ("Sent: " + i);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println ("SHIT");
			e.printStackTrace();
		}
			
			return "";
		}
	}

}