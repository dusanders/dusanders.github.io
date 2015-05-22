/*  Author: Dustin Anderson (Zena)
    Title:  SensorController.java
    Desc:   This file is intended to handle the accelerometer sensor provided by the Android OS.
                This file will handle all sensor events provided by Android.
 */


package com.zena.btcomm.app;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Zena on 10/11/2014.
 */
public class SensorController implements SensorEventListener {

    //class to hold values to be sent to rc car. used for storing the
    //commands within a dynamic array
	private class Command{
		public byte[] value;
		public BluetoothGattCharacteristic characteristic;
		public Command(byte[] inValue, BluetoothGattCharacteristic inCharacteristic){
			value = inValue;
			characteristic = inCharacteristic;
		}
	}
    private class TimeoutTimer extends Thread{
        private Timer timer;
        private long delay;
        protected TimeoutTimer(long inDelay)
        {
            delay = inDelay;
        }
        @Override
        public void run(){
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    timeout = true;
                }
            }, delay);
        }
    }

    //class variables
	private BluetoothGattServerController gattServerController;
	private Activity activity;
	private ArrayList<BluetoothGattCharacteristic> characteristics;
	private BluetoothDevice device;
	private ArrayList<Command> commandBuffer;
    private boolean stopX = false;
    private boolean stopY = false;
    public static boolean timeout;

	public SensorController(Activity inActivity, BluetoothGattServerController inGattServerController, ArrayList<BluetoothGattCharacteristic> inCharacteristic, BluetoothDevice inDevice) {

		activity = inActivity;
		gattServerController = inGattServerController;
		characteristics = inCharacteristic;
		device = inDevice;
		commandBuffer = new ArrayList<Command>();
        //timeout if BTLE does not respond, started and stopped by receiving accel. data
        //no need to set repeating
        timeout = false;
	}
	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {
        //get the values from the sensor
		float[] values = sensorEvent.values;
        //parse the proper coordinates from the returned array.
		Integer x = (int)values[0]; // x plane always 0 index
		Integer y = (int)values[1]; // y plane always 1 index
		int z = (int)values[2]; // z plane always 2 index
        MainActivity.StartTimeout();
        TimeoutTimer timeoutTimer = new TimeoutTimer(5000);
        timeoutTimer.start();
		if(x == 0 && !stopX)  {
            //wait for ACK signal, or timeout
            while(!gattServerController.clearToSend && !timeout){
               stopX = true;
            }
            //set the values and stopX, as we got a 0 value from sensor,
            //stopX will be sent to true so don't continually send '0' to car. only need to send
            //once.
			if(gattServerController.clearToSend) {
				characteristics.get(0).setValue(new byte[]{x.byteValue()});
				gattServerController.notifyCharacteristic(characteristics.get(0));
                stopX = true;
			}
		}
		else if (x > 0.8) {
            //create Command object to hold update value
			 Command command = new Command(new byte[]{x.byteValue()}, characteristics.get(1));
            //wait for ACK signal, or timeout
            //we got an acceptable command to move, so switch our stopX to allow
            // sending a new '0' value when we are requested to stop.
            while(!gattServerController.clearToSend && !timeout){
                stopX = false;
            }
			 if(gattServerController.clearToSend) {
                 //update Command object's characteristic value & notify of change
				 command.characteristic.setValue(command.value);
				 //gattServerController.notifyCharacteristic(characteristics.get(1));
                 gattServerController.notifyCharacteristic(command.characteristic);
                 stopX = false; //reset or stop, we are moving now so the next '0' will need to
                                    //be sent.
			 }
		}
        else if(x < -0.8) {
            //phone axis returns negative, we need absolute value for car.
            x = Math.abs(x);
            //create the Command object
            Command command = new Command(new byte[] {x.byteValue()}, characteristics.get(0));
            //wait for ACK or time out
            while(!gattServerController.clearToSend && !timeout){
                stopX = false;
            }
            if(gattServerController.clearToSend) {
                //update characteristic value and notify of change.
                command.characteristic.setValue(command.value);
                gattServerController.notifyCharacteristic(command.characteristic);
                stopX = false;
            }
        }

        //got a '0' y value, if we are already stopped, skip.
		if(y == 0 && !stopY) {
            //wait for ACK or timeout
            while(!gattServerController.clearToSend && !timeout){
                stopY = true;
            }
			if(gattServerController.clearToSend) {
                //update the characteristic and notify of change
				characteristics.get(2).setValue(new byte[]{y.byteValue()});
				gattServerController.notifyCharacteristic(characteristics.get(2));
                //no need to send this continually, will skip next '0' value
                stopY = true;
			}
		}
        //got acceptable move signal
		else if (y > 0.8) {
			 Command command = new Command(new byte[]{y.byteValue()}, characteristics.get(3));
            while(!gattServerController.clearToSend && !timeout){
                stopY = false;
            }
			 if(gattServerController.clearToSend) {
				 command.characteristic.setValue(command.value);
				 gattServerController.notifyCharacteristic(characteristics.get(3));
                 stopY = false; // reset our stopY as we are moving, next '0' will need to be sent.
			 }
		}
        else if(y < -0.8 ) {
            y = Math.abs(y);
            Command command = new Command(new byte[]{y.byteValue()}, characteristics.get(2));
            while(!gattServerController.clearToSend && !timeout){
                stopY = false;
            }
            if(gattServerController.clearToSend) {
                command.characteristic.setValue(command.value);
                gattServerController.notifyCharacteristic(characteristics.get(2));
                stopY = false;
            }
        }
        //shutdown the timer
        MainActivity.StopTimeout();
        //reset the timeout
        timeout = false;
        //update the GUI with new accel. data
		TextView textView = (TextView)activity.findViewById(R.id.textView1);
		textView.setText("");
		textView.append("x: "+ (int)values[0] + " " + "y: " + (int)values[1] + " " + "z: " + z);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int i) {
	}

    //method will be called be the BluetoothGattServerController class once an 'ACK' is received
    // from the rc car. If we have Commands within the buffer awaiting sending, send them.
    //          --currently we do not 'buffer' these commands as once the car does recover from
    //              a 'frozen' state, it is bombarded with move commands and not this is not desirable.
	public void notifyClearToSend(){
		if(commandBuffer.size() != 0){
			commandBuffer.get(0).characteristic.setValue(commandBuffer.get(0).value);
			gattServerController.notifyCharacteristic(commandBuffer.get(0).characteristic);
			commandBuffer.remove(0);
		}
	}

}
