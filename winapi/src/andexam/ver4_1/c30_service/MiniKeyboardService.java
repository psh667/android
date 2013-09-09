package andexam.ver4_1.c30_service;

import andexam.ver4_1.*;
import android.inputmethodservice.*;
import android.view.*;
import android.view.inputmethod.*;

public class MiniKeyboardService extends InputMethodService 
	implements KeyboardView.OnKeyboardActionListener {
	
	KeyboardView mInputView;
	MiniKeyboard mEnglish;
	MiniKeyboard mNumber;
	MiniKeyboard mSymbol;

	private int mLastDisplayWidth;

	// 키보드 생성. 폭이 바뀐 경우만 재생성한다. 
	@Override 
	public void onInitializeInterface() {
		if (mEnglish != null) {
			int displayWidth = getMaxWidth();
			if (displayWidth == mLastDisplayWidth) return;
			mLastDisplayWidth = displayWidth;
		}
		mEnglish = new MiniKeyboard(this, R.xml.english);
		mNumber = new MiniKeyboard(this, R.xml.number);
		mSymbol = new MiniKeyboard(this, R.xml.symbol);
	}

	// 입력뷰 생성하고 영문 키보드로 초기화
	@Override 
	public View onCreateInputView() {
		mInputView = (KeyboardView) getLayoutInflater().inflate(
				R.layout.minikeyboard, null);
		mInputView.setOnKeyboardActionListener(this);
		mInputView.setKeyboard(mEnglish);
		return mInputView;
	}
	
	// 입력 시작시 초기화 - 특별히 초기화할 내용이 없음
	@Override 
	public void onStartInput(EditorInfo attribute, boolean restarting) {
		super.onStartInput(attribute, restarting);
	}

	// 입력 끝 - 키보드를 닫는다.
	@Override 
	public void onFinishInput() {
		super.onFinishInput();
		if (mInputView != null) {
			mInputView.closing();
		}
	}
	
	@Override 
	public void onStartInputView(EditorInfo attribute, boolean restarting) {
		super.onStartInputView(attribute, restarting);
		mInputView.setKeyboard(mEnglish);
		mInputView.closing();
	}

	// 문자 입력을 받았을 때를 처리한다. 기능키 먼저 처리하고 문자키 처리한다.
	public void onKey(int primaryCode, int[] keyCodes) {
		if (primaryCode == Keyboard.KEYCODE_SHIFT) {
			// 영문일 때는 대소문자 토글, 숫자나 기호일 때는 두 키보드 교체
			Keyboard current = mInputView.getKeyboard();
			if (current == mEnglish) {
				mInputView.setShifted(!mInputView.isShifted());
			} else if (current == mNumber) {
				mInputView.setKeyboard(mSymbol);
				mNumber.setShifted(true);
				mSymbol.setShifted(true);
			} else if (current == mSymbol) {
				mInputView.setKeyboard(mNumber);
				mNumber.setShifted(false);
				mSymbol.setShifted(false);
			}
		} else if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE) {
			// 영문일 때 숫자로, 숫자나 기호일 때는 영문으로
			Keyboard current = mInputView.getKeyboard();
			if (current == mEnglish) {
				mInputView.setKeyboard(mNumber);
			} else {
				mInputView.setKeyboard(mEnglish);
			}
		} else if (primaryCode == Keyboard.KEYCODE_DELETE) {
			// Del키 코드를 보내 문자 삭제
			keyDownUp(KeyEvent.KEYCODE_DEL);
		} else {
			// 쉬프트 상태이면 대문자로 변환
			if (isInputViewShown()) {
				if (mInputView.isShifted()) {
					primaryCode = Character.toUpperCase(primaryCode);
				}
			}
			// 문자를 편집기로 보낸다.
			getCurrentInputConnection().commitText(
					String.valueOf((char) primaryCode), 1);
		}
	}

	// 키를 눌렀다가 떼는 동작을 하는 도우미 함수
	private void keyDownUp(int keyEventCode) {
		getCurrentInputConnection().sendKeyEvent(
				new KeyEvent(KeyEvent.ACTION_DOWN, keyEventCode));
		getCurrentInputConnection().sendKeyEvent(
				new KeyEvent(KeyEvent.ACTION_UP, keyEventCode));
	}

	public void onText(CharSequence text) {
	}

	public void onPress(int primaryCode) {
	}

	public void onRelease(int primaryCode) {
	}

	public void swipeDown() {
	}

	public void swipeLeft() {
	}

	public void swipeRight() {
	}

	public void swipeUp() {
	}
}
