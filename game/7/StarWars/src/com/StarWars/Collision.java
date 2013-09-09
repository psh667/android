package com.StarWars;

import java.util.Random;

//----------------------------
//  충돌 판정 - 생성자 없음
//----------------------------
public class Collision {
	private Random rnd = new Random();

	//----------------------------
	//  충돌 판정
	//----------------------------
	public void CheckCollision() {
		Check_1();					// 아군 미사일과 적과의 충돌
		Check_2();					// 적 미사일과 아군과의 충돌
		Check_3();					// 적군과 아군과의 충돌
		Check_4();					// 아군과 보너스와의 충돌
		if (MyGameView.isBoss) {	// Boss Stage
			Check_5();				// Boss 미사일과 아군과의 충돌
			Check_6();				// 아군 미사일과 보스와의 충돌
		}
	}
	
	//----------------------------
	//  아군 미사일과 적 충돌
	//----------------------------
	private void Check_1() {
		int x, y, x1, y1, w, h; 
		int r = rnd.nextInt(100) - 93;			// 0~6 - 보너스 나올 확률

		NEXT:
		for (int p = MyGameView.mGun.size() - 1; p >= 0; p--) { 
			x = MyGameView.mGun.get(p).x;		//	미사일 좌표	
			y = MyGameView.mGun.get(p).y;
			
			for (int i = 0; i < 6; i++) {		// 모든 적군에 대해 조사		 
				for (int j = 0; j < 8; j++) {			 
					if (MyGameView.mEnemy[i][j].isDead) continue;	// 사망자 무시

					x1 = MyGameView.mEnemy[i][j].x;			// 적군의 좌표
					y1 = MyGameView.mEnemy[i][j].y;
					w = MyGameView.mEnemy[i][j].w;				// 충돌을 조사할 범위
					h = MyGameView.mEnemy[i][j].h;
					
					if (Math.abs(x - x1) > w || Math.abs(y - y1) > h)	// 충돌 없음 
						continue;

					if (MyGameView.isPower)							// 강화 미사일은
						MyGameView.mEnemy[i][j].shield -= 4;		// 보호막 4씩 감소
					else
						MyGameView.mEnemy[i][j].shield--;			// 일반 미사일
						
					if (MyGameView.mEnemy[i][j].shield > 0) {		// 보호막이 있으면
						// 작은 폭발은 미사일 위치에
						MyGameView.mExp.add(new Explosion(x, y, Explosion.SMALL)); 
						MyGameView.score += (6 - i) * 100;			// 득점은 절반
					} else {				 
						MyGameView.mEnemy[i][j].isDead = true;	 	// 적군 사망
						MyGameView.mMap.enemyCnt--;					// 남은 적군 수
						// 큰 폭발은 적군의 중심부에 
						MyGameView.mExp.add(new Explosion(x1, y1, Explosion.BIG)); 
						MyGameView.score += (6 - i) * 200;			// 득점

						if (r > 0)									// 보너스가 있나?
							MyGameView.mBonus.add(new Bonus(x1, y1, r));	// 보너스
					}
					MyGameView.mGun.remove(p);						// 아군 미사일 제거
					continue NEXT;
				} // for j
			} // for i
		} // for p
	}

	//----------------------------
	//  적군 미사일과 아군기 충돌
	//----------------------------
	private void Check_2() {
		if (MyGameView.mShip.undead || MyGameView.mShip.isDead) return;
		
		int x, y, x1, y1, w, h;
		x = MyGameView.mShip.x;		// 아군기 좌표
		y = MyGameView.mShip.y;
		w = MyGameView.mShip.w;
		h = MyGameView.mShip.h;
		
		// 모든 적 미사일에 대해 조사
		for (int i = MyGameView.mMissile.size() - 1; i >= 0; i--) {	
			x1 = MyGameView.mMissile.get(i).x;					// 미사일 좌표
			y1 = MyGameView.mMissile.get(i).y;

			if (Math.abs(x1 - x) > w || Math.abs(y1 - y) > h)	// 충돌 없음 
				continue; 
			MyGameView.mMissile.remove(i);						// 적 미사일 제거
			MyGameView.mShip.shield--;							// 아군 보호막 감소
			if (MyGameView.mShip.shield >= 0) {					// 보호막 남아 있으면 
				MyGameView.mExp.add(new Explosion(x1, y1, Explosion.SMALL));	// 작은 불꽃 
			} else {											// 보호막 없으면 
				MyGameView.mShip.isDead = true;					// 아군기를 잃음
				MyGameView.shipCnt--;							// 아군기를 잃음
				MyGameView.mExp.add(new Explosion(x, y, Explosion.MYSHIP));	// 큰 불꽃
			} // if
			break;		 			
		} // for
	}

	//----------------------------
	//  아군기와 적기와의 충돌
	//----------------------------
	private void Check_3() {
		if (MyGameView.mShip.isDead) return;

		int x, y, x1, y1, w, h;
		
		x = MyGameView.mShip.x;			// 아군기 좌표
		y = MyGameView.mShip.y;
		w = MyGameView.mShip.w;
		h = MyGameView.mShip.h;
		
		for (int i = 0; i < 6; i++) {				 
			for (int j = 0; j < 8; j++) {			 
				if (MyGameView.mEnemy[i][j].isDead) continue;		// 사망자 무시

				x1 = MyGameView.mEnemy[i][j].x;
				y1 = MyGameView.mEnemy[i][j].y;
				
				if (Math.abs(x1 - x) > w ||	Math.abs(y1 - y) > h)	// 충돌 없음 
					continue;
				MyGameView.mEnemy[i][j].isDead = true;	 		// 충돌이면 적군도 사망	
				MyGameView.mMap.enemyCnt--;						// 남은 적군 수
				MyGameView.score += (6 - i) * 200;				// 자폭도 점수 인정
				if (MyGameView.mShip.undead) {
					MyGameView.mExp.add(new Explosion(x1, y1, Explosion.BIG));	// 적군만 사망
				} else {
					MyGameView.mShip.isDead = true;					// 아군기를 잃음
					MyGameView.shipCnt--;							// 아군기도 함께 잃음
					MyGameView.mExp.add(new Explosion(x, y, Explosion.MYSHIP));	// 아군 파괴 불꽃
				}	
				return;
			} // for j
		} // for i
	}

	//----------------------------
	//  아군기와 보너스와  충돌
	//----------------------------
	private void Check_4() {
		int x, y, x1, y1, w, h, bonus = 0;
		
		x = MyGameView.mShip.x;						// 아군기 좌표
		y = MyGameView.mShip.y;
		w = MyGameView.mShip.w;
		h = MyGameView.mShip.h;
		
		for (int i = MyGameView.mBonus.size() - 1; i >= 0; i--) {
			x1 = MyGameView.mBonus.get(i).x;	// 보너스 좌표
			y1 = MyGameView.mBonus.get(i).y;
			if (Math.abs(x - x1) > w * 2 || Math.abs(y - y1) > h * 2)	// 충돌 없음 
				continue;

			bonus = MyGameView.mBonus.get(i).kind;		// 보너스 종류
			MyGameView.mBonus.remove(i);				// 보너스 제거

			switch (bonus) {
			case 1: 
				MyGameView.isDouble = true;		// Double Fire 모드
				break;
			case 2: 	
				MyGameView.isPower = true;		// 강화 미사일
				break;
			case 3: 	
				if (MyGameView.gunDelay > 6)
					MyGameView.gunDelay -= 2;	// 미사일  발사 속도
				break;
			case 4:
				MyGameView.mShip.shield = 6;	// 보호막 충전
				break;
			case 5:
				MyGameView.mShip.undeadCnt = 100;	// 무적 모드로 변신		 
				MyGameView.mShip.undead = true;				 
				break;
			case 6:
				if (MyGameView.shipCnt < 4)		// 우주선 1대 늘려줌
					MyGameView.shipCnt++;
			}
		} // for
	}

	//----------------------------
	//  Boss 미사일과 아군과와  충돌
	//----------------------------
	private void Check_5() {
		if (MyGameView.mShip.undead) return;
		
		int x, y, x1, y1;
		int w, h;

		x = MyGameView.mShip.x;
		y = MyGameView.mShip.y;
		w = MyGameView.mShip.w;
		h = MyGameView.mShip.h;
		
		for (int i = MyGameView.mBsMissile.size() - 1; i >= 0; i--) {	
			x1 = MyGameView.mBsMissile.get(i).x;					// 미사일 좌표
			y1 = MyGameView.mBsMissile.get(i).y;

			if (Math.abs(x1 - x) <= w && Math.abs(y1 - y) <= h)	{ 
				MyGameView.mBsMissile.remove(i);				// 적 미사일 제거
				MyGameView.mShip.isDead = true;					// 아군기를 잃음
				MyGameView.mExp.add(new Explosion(x, y, Explosion.MYSHIP));	
				MyGameView.shipCnt--;						
			}	
		}
	}
	
	//----------------------------
	//  아군 미사일과 Boss와  충돌
	//----------------------------
	private void Check_6() {
		int x1, x2, x3, y1, w, h;			// Boss의 Center, Left, Right, 폭
		int x, y, damage = 1;				// 미사일 좌표, power
		if (MyGameView.isPower) damage = 4;
		
		// Boss 3부분 좌표와 폭
		w = MyGameView.mBoss.w / 2;
		h = MyGameView.mBoss.h;
		x1 = MyGameView.mBoss.x;
		x2 = x1 - w;
		x3 = x1 + w;
		y1 = MyGameView.mBoss.y;

		for (int i = MyGameView.mGun.size() - 1; i >= 0; i--) { 
			x = MyGameView.mGun.get(i).x;		//	미사일 좌표	
			y = MyGameView.mGun.get(i).y;
		
			// Boss Center
			if (Math.abs(x - x1) < w && Math.abs(y - y1) < h ) {
				MyGameView.mBoss.shield[EnemyBoss.CENTER] -= damage;
				MyGameView.mGun.remove(i);

				// 방어막이 (-)가 될때까지 처리 - Explosion에서 CENTER값 참조함
				if (MyGameView.mBoss.shield[EnemyBoss.CENTER] >= 0) {
					MyGameView.mExp.add(new Explosion(x, y, Explosion.SMALL));
					MyGameView.score += 50;
					continue;
				}
				ClearAllEnemies();
				return;
			} // if
			
			// 보스의 왼쪽
			if (Math.abs(x - x2) < w && Math.abs(y - y1) < h && 
					MyGameView.mBoss.shield[EnemyBoss.LEFT] > 0) {
				MyGameView.mBoss.shield[1] -= damage;
				MyGameView.mGun.remove(i);

				if (MyGameView.mBoss.shield[EnemyBoss.LEFT] > 0) {
					MyGameView.mExp.add(new Explosion(x, y, Explosion.SMALL));	
					MyGameView.score += 50;
					continue;
				}	
				
				// Boss 왼쪽 파괴
				MyGameView.mExp.add(new Explosion(x2, y1, Explosion.BIG));
				MyGameView.score += 1000;		
				MyGameView.mGun.remove(i);
					
				if (MyGameView.mBoss.shield[EnemyBoss.RIGHT] > 0)
					MyGameView.mBoss.imgBoss = MyGameView.mBoss.imgSpt[EnemyBoss.RIGHT];
				else	
					MyGameView.mBoss.imgBoss = MyGameView.mBoss.imgSpt[EnemyBoss.CENTER];
				continue;
			} // if
			
			// 보스의 오른쪽
			if (Math.abs(x - x3) < w && Math.abs(y - y1) < h && 
					MyGameView.mBoss.shield[EnemyBoss.RIGHT] > 0) {
				MyGameView.mBoss.shield[EnemyBoss.RIGHT] -= damage;
				MyGameView.mGun.remove(i);

				if (MyGameView.mBoss.shield[EnemyBoss.RIGHT] > 0) {
					MyGameView.mExp.add(new Explosion(x, y, Explosion.SMALL));	
					MyGameView.score += 50;
					continue;
				}	
				
				// Boss 오른쪽 파괴
				MyGameView.mExp.add(new Explosion(x3, y1, Explosion.BIG));	
				MyGameView.score += 1000;		
				MyGameView.mGun.remove(i);
					
				if (MyGameView.mBoss.shield[EnemyBoss.LEFT] > 0)
					MyGameView.mBoss.imgBoss = MyGameView.mBoss.imgSpt[EnemyBoss.LEFT];
				else	
					MyGameView.mBoss.imgBoss = MyGameView.mBoss.imgSpt[EnemyBoss.CENTER];
			} // if
		} // for
	}	
	
	//----------------------------
	//  모든 적 파괴
	//----------------------------
	private void ClearAllEnemies() {
		int x1, x2, x3, y1, w;			// Boss의 Center, Left, Right, 폭

		w = MyGameView.mBoss.w / 2;
		x1 = MyGameView.mBoss.x;
		x2 = x1 - w;
		x3 = x1 + w;
		y1 = MyGameView.mBoss.y;
		
		// Boss 파괴
		MyGameView.mExp.add(new Explosion(x1, y1, Explosion.BOSS));
		MyGameView.score += 5000;
		
		// 왼쪽 파괴
		if (MyGameView.mBoss.shield[EnemyBoss.LEFT] > 0) {
			MyGameView.mBoss.shield[EnemyBoss.LEFT] = 0;
			MyGameView.mExp.add(new Explosion(x2, y1, Explosion.BOSS));
		}
		
		// 오른쪽 파괴
		if (MyGameView.mBoss.shield[EnemyBoss.RIGHT] > 0) {
			MyGameView.mBoss.shield[EnemyBoss.RIGHT] = 0;
			MyGameView.mExp.add(new Explosion(x3, y1, Explosion.BOSS));
		}

		// Boss Missile 모두 제거
		for (BossMissile tmp : MyGameView.mBsMissile) {
			MyGameView.mExp.add(new Explosion(tmp.x, tmp.y, Explosion.BIG));
		}
		
		MyGameView.mBsMissile.clear();
		
		// 화면에 남은 적 모두 제거
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 8; j++) {
				if (MyGameView.mEnemy[i][j].shield > 0) {
					x1 = MyGameView.mEnemy[i][j].x;
					y1 = MyGameView.mEnemy[i][j].y;
					MyGameView.mExp.add(new Explosion(x1, y1, Explosion.BIG));
					MyGameView.mEnemy[i][j].shield = 0;
					MyGameView.mMap.enemyCnt--;					// 남은 적군 수
				}
			}
		} // for
		
		//Stage Clear는 Explosion에서 처리함
	}
}
