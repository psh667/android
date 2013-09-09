package com.StarWars;

import java.io.IOException;
import java.io.InputStream;

//--------------------------------------
// Map Table
//--------------------------------------
public class MapTable {
	
	// 16 방향에 따른 이동 거리 삼각함수표
	public float sx[] = {0f, 0.39f, 0.75f, 0.93f, 1, 0.93f, 0.75f, 0.39f, 0f, -0.39f, -0.75f, -0.93f, -1f, -0.93f, -0.75f, -0.39f};
	public float sy[] = {-1f, -0.93f, -0.75f, -0.39f, 0f, 0.39f, 0.75f, 0.93f, 1f, 0.93f, 0.75f, 0.39f, 0f, -0.39f, -0.75f, -0.93f}; 

	public int syncCnt = 0;		// 싱크 계산용
	public int dirLen = 52;		// 전투 대형 유지 후 처음으로 이동할 거리 
	public int dir = 4;	  		// 위의 방향
	public int dirCnt = 0;		// 현재 부대가 이동한 거리	
	
	private Path mPath;			// Path
	private Selection mSelect;	// Selection
	private DelayTime mDelay;	// Delay
	private Position mPos;		// Pisition
	private Shield mShield;		// Shield
	
	public int enemyCnt;		// 현스테이지에서 적군의 생존자 수
	public int attackTime;		// 공격 시작 시간
	
	//--------------------------------------
	// Constructor
	//--------------------------------------
	public MapTable() {
		// 생성자 내용 없음
	}
	
	//--------------------------------------
	//  Read File
	//--------------------------------------
	public void ReadMap(int num) {
		num--;
		// 스테이지에 해당하는 맵 파일 읽기
		InputStream fi = MyGameView.mContext.getResources().openRawResource(R.raw.stage01 + num);
		try {
			byte[] data = new byte[fi.available()];
			fi.read(data);
			fi.close();
			String s = new String(data, "EUC-KR");	// 파일 인코등
			MakeMap(s);								// 인코딩한 문자열을 분석하러 보낸다
		} catch (IOException e) {
			// 
		}	
	}
	
	//--------------------------------------
	//  Make Map
	//--------------------------------------
	public void MakeMap(String str) {
		int n1 = str.indexOf("selection");					// selection 섹션의 시작 위치  
		mPath = new Path(str.substring(0, n1));				// path
		
		int n2 = str.indexOf("delay");
		mSelect = new Selection(str.substring(n1, n2));		// Selection
		enemyCnt = mSelect.GetEnemyCount();					// 적군의 수
		
		n1 = str.indexOf("position");  
		mDelay = new DelayTime(str.substring(n2, n1));		// Delay
		attackTime = mDelay.GetDelay(0, 5);					// 마지막 캐릭터 
		
		n2 = str.indexOf("shield");  
		mPos = new Position(str.substring(n1, n2));			// Position

		mShield = new Shield(str.substring(n2));			// Shield
	}
	
	//--------------------------------------
	//  Get Path
	//--------------------------------------
	public SinglePath GetPath(int num) {
		return mPath.GetPath(num);
	}
	
	//--------------------------------------
	//  Get Delay
	//--------------------------------------
	public int GetDelay(int kind, int num) {
		return mDelay.GetDelay(kind, num);
	}
	
	//--------------------------------------
	//  Get Selection
	//--------------------------------------
	public int GetSelection(int kind, int num) {
		return mSelect.GetSelection(kind, num);
	}
	
	//--------------------------------------
	//  Get X position
	//--------------------------------------
	public int GetPosX(int kind, int num) {
		return mPos.GetPosX(kind, num);
	}
	
	//--------------------------------------
	//  Get Y position
	//--------------------------------------
	public int GetPosY(int kind, int num) {
		return mPos.GetPosY(kind, num);
	}
	
	//--------------------------------------
	//  Get Enemy Num
	//--------------------------------------
	public int GetEnemyNum(int kind, int num) {
		return mPos.GetEnemyNum(kind, num);
	}
	
	//--------------------------------------
	//  Get Shield
	//--------------------------------------
	public int GetShield(int kind, int num) {
		return mShield.GetShield(kind, num);
	}
}
