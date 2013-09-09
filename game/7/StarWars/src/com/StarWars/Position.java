package com.StarWars;

//-----------------------------------
// Position 배열
//-----------------------------------
public class Position {
	private int posX[][] = new int[6][8];
	private int posY[][] = new int[6][8];
	private int enemy[][] = new int[6][8];
	
	//-----------------------------------
	//  캐릭터 번호와 Position 배열에 넣기
	//-----------------------------------
	public Position(String str) {
		String tmp[] = str.split("\n");
		String s;
		char ch;
		
		for (int i = 1; i < tmp.length; i++) {		// 캐릭터 번호
			s = tmp[i];
			for (int j = 0; j < 8; j++) {
				ch = s.charAt(j);
				if (ch == '-')
					enemy[i - 1][j] = -1;
				else if (ch <= '9') 
					enemy[i - 1][j] = ch - 48;  // 0~9
				else
					enemy[i - 1][j] = ch - 87;	// 'a'~'z'
			} // j
		} // i
		
		int top = 100;
		int left = 72;
		int wid = 48;
		int x;
		
		for (int i = 0; i < 6; i++) {				// 캐릭터 좌표
			if (i <= 1) {
				for (int j = 0; j < 8; j++) {
					posX[i][j] = j * wid + left;
					posY[i][j] = i * wid + top;
				}
			} else {
				for (int j = 0; j < 8; j++) {
					if (j % 2 == 0)
						x = 3 - j / 2;
					else
						x = j / 2 + 4;
					posX[i][j] = x * wid + left;
					posY[i][j] = i * wid + top;
				}
			} // if
		} // i

	}
	
	//-----------------------------------
	//  캐릭터 번호 구하기
	//-----------------------------------
	public int GetEnemyNum(int kind, int num) {
		return enemy[kind][num];
	}
	
	//-----------------------------------
	//  Position - X
	//-----------------------------------
	public int GetPosX(int kind, int num) {
		return posX[kind][num];
	}
	
	//-----------------------------------
	//  Position - Y
	//-----------------------------------
	public int GetPosY(int kind, int num) {
		return posY[kind][num];
	}
}
