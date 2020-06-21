package com.kok.sport.utils.tasks;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Timerx {

	
	//if hto time over timerd zo ..use htod ..beir use  timerd 
	public static void main(String[] args) {
		Timer tmr = new Timer();
		tmr.schedule(new TimerTask() {

			@Override
			public void run() {
				System.out.println(new Date());
				try {//from db
					 Thread.sleep(1000);
				} catch (Exception e) {

					e.printStackTrace();
				}

			}
		}, 0, 2000);

	}

}
