package com.androidside.subway.ui;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.ListActivity;
import android.os.Bundle;

import com.androidside.subway.R;
import com.androidside.subway.common.ExitInfoAdapter;
import com.androidside.subway.common.StationExitInfo;

public class Tab2_ExitInfo extends ListActivity {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	ArrayList<StationExitInfo> arrStationExit = new ArrayList<StationExitInfo>();

	ExitInfoAdapter adapter = null;
	static String strExitInfo = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.exitinfo);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub

		strExitInfo = Tab1_ArrivalInfo.strStationExit.replace("+", "//");

		// ArrayList 초기화
		arrStationExit.clear();

		// 출구번호 별로 나누기
		String[] arrExitInfo = strExitInfo.split("//");

		for (int i = 0; i < arrExitInfo.length; i++) {
			// 1번 출구 : 서울역, 남영동, 동자동, 시티투어버스정류장
			Pattern pattern = Pattern.compile("(.+) : (.+)");
			Matcher match = pattern.matcher(arrExitInfo[i]);

			if (match.find()) {
				// 출구 번호
				String strExitNumber = match.group(1);
				// 출구정보
				String strExitInfo = match.group(2).replace(", ", "\n");

				arrStationExit.add(new StationExitInfo(strExitNumber,
						strExitInfo));
			}
		}

		// 어댑터를 생성합니다.
		adapter = new ExitInfoAdapter(this, R.layout.exitinforow, arrStationExit);
		setListAdapter(adapter);

		adapter.notifyDataSetChanged();

		super.onResume();
	}
}
