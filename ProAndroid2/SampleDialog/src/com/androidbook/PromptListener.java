package com.androidbook;

import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

public class PromptListener implements android.content.DialogInterface.OnClickListener 
{
	// 프롬프트 응답 값을 반환하는 지역 변수
	private String promptReply = null; 
	
	// 프롬프트 값을 알아내기 위해 뷰에 변수를 보관
	View promptDialogView = null; 
	
	// 뷰를 생성자에 가져옴
	public PromptListener(View inDialogView) {
		promptDialogView = inDialogView; 
	}

	// 대화창들 상의 콜백 메서드
	public void onClick(DialogInterface v, int buttonId) {
		if (buttonId == DialogInterface.BUTTON1) {
			// OK 버튼
			promptReply = getPromptText(); 
		}
		else {
			// Cancel 버튼
			//promptValue = null; 
		}
	}
	
	// 에디트 박스에 들어있는 내용을 알아내는 접근 메서드
	private String getPromptText() {
		EditText et = (EditText) 
						promptDialogView.findViewById(R.id.promptmessage); 
		return et.getText().toString(); 
	}
	public String getPromptReply() { return promptReply; }
}
