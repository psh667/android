package com.StarWars;

import android.util.Log;

//-----------------------------------
//진입 Delay 시간
//-----------------------------------
public class DelayTime {
	private int Delay[][] = new int[6][8];
	
	//-----------------------------------
	//  배열에 넣기
	//-----------------------------------
	public DelayTime(String str) {
		
		String tmp[] = str.split("\n");
		String s;
		for (int i = 1; i < tmp.length; i++) {
			for (int j = 0; j < 8; j++) {
				s = tmp[i].substring(j * 4, (j + 1) * 4).trim();
				if (s.equals("---")) 
					Delay[i - 1][j] = -1;
				else 
					Delay[i - 1][j] = Integer.parseInt(s);
			} // j
		} // i
		Log.v("Delay", "success");
	}
	
	//-----------------------------------
	//  Delay 번호 구하기
	//-----------------------------------
	public int GetDelay(int kind, int num) {
		return Delay[kind][num];
	}
}

