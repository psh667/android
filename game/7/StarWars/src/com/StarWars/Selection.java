package com.StarWars;

import android.util.Log;

//-----------------------------------
//Path 종류 선택
//-----------------------------------
public class Selection {
	private int pathNum[][] = new int[6][8];
	private int enemyCnt;

	//-----------------------------------
	//  배열에 넣기
	//-----------------------------------
	public Selection(String str) {
		String tmp[] = str.split("\n");
		String s;
		enemyCnt = 0;
		char ch;
		for (int i = 1; i < tmp.length; i++) {
			s = tmp[i];
			for (int j = 0; j < 8; j++) {
				ch = s.charAt(j);
				switch (ch) {
				case '-' :
					pathNum[i - 1][j] = -1;
					break;
				default :
					enemyCnt++;						  // 전체 적의 수	
					if (ch <= '9') 
						pathNum[i - 1][j] = ch - 48;   // 0~9
					else
						pathNum[i - 1][j] = ch - 87;
				}
			} // j
		} // i
	
		Log.v("Selection", "Make Selection success");
	}

	//-----------------------------------
	//  Path 번호 구하기
	//-----------------------------------
	public int GetSelection(int kind, int num) {
		return pathNum[kind][num];
	}

	//-----------------------------------
	//  전체 적군 수  구하기
	//-----------------------------------
	public int GetEnemyCount() {
		return enemyCnt;
	}
}
