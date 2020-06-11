package com.attilax.coreLuni.util;

import java.util.Timer;
import java.util.TimerTask;

public class timerTest {
	
	public static void main(String[] args) {
		Timer tmr = new Timer("time2");
		tmr.schedule(new TimerTask() {

			@Override
			public void run() {

			 
				System.out.println("getconnStatChkTmr:run");

			}
		}, 50, 5000);
		 
	}

}
