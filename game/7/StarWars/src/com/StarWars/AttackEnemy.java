package com.StarWars;

import java.util.Random;

import android.util.Log;

//------------------------
// 적군의  공격 - 생성자 없음
//------------------------
public class AttackEnemy {
	public int loop = 0;				// 루프 카운터
	private Random rnd = new Random();
	private int r1, r2;
	
	//------------------------
	// 적군의  공격 - 생성자 없음
	//------------------------
	public void ResetAttack() {
		loop = 0;
	}
	
	//------------------------
	//  공격 명령
	//------------------------
	public void Attack() {
		if (MyGameView.mMap.enemyCnt <= 10) {	// 적이 10마리 이하면
			AttackAll();						// 일제 총공격
			return;
		}

		loop++;
		int n = loop - (MyGameView.mMap.attackTime + 120);
		if (n < 0) return;					// 모든 캐릭터가 입장할 때 까지 대기
		
		switch (n % 600) {
		case 0:
			r1 = rnd.nextInt(10) + 1;
			AttackPath(3, 1, r1);			// 3등급, 1번기 : r1번 공격 루트로 출격
			AttackPath(3, 3, r1);
			AttackPath(2, 1, r1);
			break;
		case 50:
			r1 = rnd.nextInt(10) + 1;
			AttackPath(5, 4, r1);			// 5등급 4번기 : r1번 공격 루트로 출력
			AttackPath(5, 2, r1);
			AttackPath(4, 0, r1);
			break;
		case 100:
			r1 = rnd.nextInt(10) + 1;
			AttackPath(3, 0, r1);
			AttackPath(3, 2, r1);
			AttackPath(2, 4, r1);
			break;
		case 150:
			r1 = rnd.nextInt(10) + 1;
			AttackPath(0, 2, r1);
			AttackPath(1, 3, r1);
			AttackPath(1, 4, r1);
			break;
		case 200:
			r1 = rnd.nextInt(10) + 1;
			AttackPath(5, 3, r1);
			AttackPath(5, 5, r1);
			AttackPath(4, 6, r1);
			break;
		case 250:
			r1 = rnd.nextInt(10) + 1;
			AttackPath(3, 6, r1);
			AttackPath(3, 4, r1);
			AttackPath(2, 2, r1);
			break;
		case 300:
			r1 = rnd.nextInt(10) + 1;
			r2 = rnd.nextInt(10) + 1;
			AttackPath(2, 7, r1);
			AttackPath(2, 5, r1);
			AttackPath(0, 5, r2);
			AttackPath(1, 1, r2);
			break;
		case 350:
			r1 = rnd.nextInt(10) + 1;
			r2 = rnd.nextInt(10) + 1;
			AttackPath(4, 6, r1);
			AttackPath(4, 5, r1);
			AttackPath(3, 5, r1);
			AttackPath(3, 7, r2);
			AttackPath(4, 4, r2);
			break;
		case 400:
			r1 = rnd.nextInt(10) + 1;
			r2 = rnd.nextInt(10) + 1;
			AttackPath(5, 6, r1);
			AttackPath(5, 1, r1);
			AttackPath(2, 6, r2);
			AttackPath(2, 3, r2);
			break;
		case 450:
			r1 = rnd.nextInt(10) + 1;
			r2 = rnd.nextInt(10) + 1;
			AttackPath(1, 2, r1);
			AttackPath(1, 6, r1);
			AttackPath(2, 0, r2);
			AttackPath(4, 3, r2);
			break;
		case 500:
			r1 = rnd.nextInt(10) + 1;
			r2 = rnd.nextInt(10) + 1;
			AttackPath(4, 2, r1);
			AttackPath(4, 1, r1);
			AttackPath(1, 5, r2);
			AttackPath(5, 7, r2);
			break;
		}	
	}

	//------------------------
	//  적군의 공격
	//------------------------
	private void AttackPath(int kind, int num, int aKind) {
		// 위에서 지시한 공격 루트로 이동 - Sprite Attack 참조
		MyGameView.mEnemy[kind][num].BeginAttack(aKind);
	}
		
	//------------------------
	//  남은 적군 총공격
	//------------------------
	private void AttackAll() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 8; j++) {
				if (MyGameView.mEnemy[i][j].status == Sprite.SYNC)
					AttackPath(i, j, rnd.nextInt(10) + 1);			// 무작위 공격
			}
		}
	}
} // end Class
