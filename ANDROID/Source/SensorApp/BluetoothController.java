package com.zena.sensorapp2.app;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by Zena on 9/22/2014.
 */
public class BluetoothController {
	private Activity activity;
	private BluetoothServerSocket serverSocket;
	private BluetoothSocket connectedSocket;
	private BluetoothAdapter adapter;
	private InputStream inputStream;
	private OutputStream outputStream;
	private byte[] buffer = new byte[1024];
	private UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

	public BluetoothController(Activity mainActivity, BluetoothAdapter inAdapter) {
		adapter = inAdapter;
		activity = mainActivity;
	}
	public void disconnect() {
		try {
			connectedSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public byte[] read() {
		try {
			inputStream.read(buffer);
		} catch (IOException e) {
			try {
				connectedSocket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return buffer;
	}
	public void write(byte[] byteString) {
		try {
			outputStream.write(byteString);
		} catch (IOException e) {
			try {
				connectedSocket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	public void write(int inInt) {
		try {
			outputStream.write(inInt);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void discover() {
		try {
			//start listening
			serverSocket = adapter.listenUsingInsecureRfcommWithServiceRecord("BluetoothRC", uuid);
			//this blocks, so get a new thread
			Thread connect = new Thread() {
				@Override
				public void run() {
					//just keep listening until we get exception or are connected
					while(true) {
						try {
							//get our socket from the ServerSocket, wait for it....
							connectedSocket = serverSocket.accept();
						} catch (IOException e) {
							e.printStackTrace();
							activity.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									Toast toast = Toast.makeText(activity, "Timeout!", Toast.LENGTH_SHORT);
									toast.show();
								}
							});
							break;
						}
						if (connectedSocket != null) {
							//close the server socket (it returned a BluetoothSocket, so it is no longer needed
                            //the ServerSocket is resource heavy so dispose once we get a Socket!
							try {
								serverSocket.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
							//get our streams from socket
							try {
								inputStream = connectedSocket.getInputStream();
								outputStream = connectedSocket.getOutputStream();
							} catch (IOException e) {
								e.printStackTrace();
							}
                            //update our 'connected' device list, must be run on MAIN thread because of the ListView
							activity.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									MainActivity.addBluetoothDevice(connectedSocket.getRemoteDevice(), connectedSocket);
								}
							});
							//toast the user success
							activity.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									Toast toast = Toast.makeText(activity, "Connected!", Toast.LENGTH_LONG);
									toast.show();
								}
							});
							//get out of the loop
							break;
						}//end if(connectedSocket != null)
					}//end while(true)
				}//end run()
			};//end Thread connect = new Thread {
            //start the thread
			connect.start();
		}
		catch(IOException e) {
			e.printStackTrace();
		}//end catch
	}//end discover()
}//end BluetoothController



