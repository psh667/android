package andexam.ver4_1;

import java.util.*;

import andexam.ver4_1.c04_view.*;
import andexam.ver4_1.c05_layout.*;
import andexam.ver4_1.c06_layoutman.*;
import andexam.ver4_1.c07_output.*;
import andexam.ver4_1.c08_input.*;
import andexam.ver4_1.c09_menu.*;
import andexam.ver4_1.c10_tool.*;
import andexam.ver4_1.c11_widget.*;
import andexam.ver4_1.c12_adapterview.*;
import andexam.ver4_1.c13_advwidget.*;
import andexam.ver4_1.c14_cuswidget.*;
import andexam.ver4_1.c15_resource.*;
import andexam.ver4_1.c16_dialog.*;
import andexam.ver4_1.c17_activity.*;
import andexam.ver4_1.c18_process.*;
import andexam.ver4_1.c19_thread.*;
import andexam.ver4_1.c20_fragment.*;
import andexam.ver4_1.c21_actionbar.*;
import andexam.ver4_1.c22_graphic.*;
import andexam.ver4_1.c23_animation.*;
import andexam.ver4_1.c24_propani.*;
import andexam.ver4_1.c25_file.*;
import andexam.ver4_1.c26_cp.*;
import andexam.ver4_1.c27_clipboard.*;
import andexam.ver4_1.c28_network.*;
import andexam.ver4_1.c29_br.*;
import andexam.ver4_1.c30_service.*;
import andexam.ver4_1.c31_gesture.*;
import andexam.ver4_1.c32_map.*;
import andexam.ver4_1.c33_multimedia.*;
import andexam.ver4_1.c34_sensor.*;
import andexam.ver4_1.c35_setting.*;
import andexam.ver4_1.c36_tel.*;
import andexam.ver4_1.c37_appwidget.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class AndExam extends Activity {
	static final int SETTING_ACTIVITY = 1;
	// 예제가 있는 시작 챕터. 스피너의 첨자에 이 값을 더해야 장 번호가 된다.
	static final int START_CHAPTER = 4;
	class Example {
		Example(Class<?> acls, String aDesc) {
			cls = acls;
			Name = cls.getSimpleName();
			Desc = aDesc;
		}
		Class<?> cls;
		String Name;
		String Desc;
	}
	
	ArrayList<Example> arExample = new ArrayList<Example>();

	// 요청한 장의 예제들을 배열에 채운다.
	void FillExample(int chapter) {
		arExample.clear();
		
		switch(chapter) {
		case 4: // 뷰
			arExample.add(new Example(TextViewTest.class, "텍스트 뷰 위젯 소개. 3개의 문자열 출력"));
			arExample.add(new Example(ImageViewTest.class, "이미지 뷰 위젯 소개. 세 개의 이미지 출력"));
			arExample.add(new Example(ButtonEdit.class, "버튼과 에디트를 배치하고 버튼 클릭 이벤트 처리"));
			break;
		case 5: // 레이아웃
			arExample.add(new Example(Horizontal1.class, "버튼 에디트 수평 배치"));
			arExample.add(new Example(Horizontal2.class, "텍스트 수평 배치의 잘못된 예"));
			arExample.add(new Example(Horizontal3.class, "텍스트 수평 배치의 예"));
			arExample.add(new Example(Gravity1.class, "디폴트 좌상단 정렬"));
			arExample.add(new Example(Gravity2.class, "화면 중앙에 텍스트 뷰 정렬"));
			arExample.add(new Example(Gravity3.class, "수직으로만 중앙에 정렬"));
			arExample.add(new Example(Gravity4.class, "수직 중앙,수평 오른쪽 정렬"));
			arExample.add(new Example(Gravity5.class, "텍스트 뷰 화면 중앙에 정렬 시도(실패)"));
			arExample.add(new Example(lGravity1.class, "수평중앙에 텍스트 뷰 배치"));
			arExample.add(new Example(lGravity2.class, "수평중앙 위쪽에 텍스트 뷰 배치"));
			arExample.add(new Example(lGravity3.class, "화면중앙에 텍스트 뷰 배치"));
			arExample.add(new Example(lGravity4.class, "두 위젯을 화면 중앙에 배치"));
			arExample.add(new Example(lGravity5.class, "두 정렬 속성의 차이 연구"));
			arExample.add(new Example(Base1.class, "높이가 다른 위젯 베이스 정렬 예"));
			arExample.add(new Example(Base2.class, "베이스 정렬을 하지 않은 예"));
			arExample.add(new Example(Weight1.class, "1:3:1 비율로 리니어 영역 분할"));
			arExample.add(new Example(Weight2.class, "1:2:3 비율로 리니어 영역 분할"));
			arExample.add(new Example(Weight3.class, "수직 리니어 삼단 분할"));
			arExample.add(new Example(Padding1.class, "여백이 전혀 없는 배치"));
			arExample.add(new Example(Padding2.class, "마진 30을 적용한 배치"));
			arExample.add(new Example(Padding3.class, "마진 30, 패딩 30을 적용한 배치"));
			arExample.add(new Example(Relative.class, "렐러티브 레이아웃의 상대적 배치"));
			arExample.add(new Example(NameCard.class, "렐러티브를 이용한 명함철"));
			arExample.add(new Example(NameCard2.class, "정렬을 잘못한 명함철"));
			arExample.add(new Example(IfMissing.class, "layout_alignWithParentIfMissing 속성이 있을 때"));
			arExample.add(new Example(IfMissing2.class, "layout_alignWithParentIfMissing 속성이 없을 때"));
			arExample.add(new Example(Absolute.class, "앨슬루트 레이아웃의 절대좌표에 배치"));
			arExample.add(new Example(NoAbsolute.class, "렐러티브를 이용한 절대 배치"));
			arExample.add(new Example(Frame.class, "프레임으로 뷰를 겹쳐서 배치하기"));
			arExample.add(new Example(Table.class, "표형태의 테이블로 배치"));
			arExample.add(new Example(Table2.class, "중간의 캡션을 길게 수정한 표"));
			break;
		case 6: // 레이아웃 관리
			arExample.add(new Example(NestLayout.class, "레이아웃끼리 중첩"));
			arExample.add(new Example(MultiPage.class, "프레임을 이용한 여러 페이지로 구성된 액티비티"));
			arExample.add(new Example(LinearTable.class, "리니어를 이용한 테이블 배치"));
			arExample.add(new Example(CodeLayout.class, "XML만으로 레이아웃 전개하기"));
			arExample.add(new Example(CodeLayout2.class, "코드로 레이아웃 생성하여 배치"));
			arExample.add(new Example(Inflation.class, "XML로 레이아웃 전개"));
			arExample.add(new Example(Inflation2.class, "코드로 레이아웃 생성"));
			arExample.add(new Example(Inflation3.class, "XML문서를 전개하여 배치"));
			arExample.add(new Example(Inflation4.class, "텍스트 뷰만 전개하여 리니어에 추가하기"));
			arExample.add(new Example(Inflation5.class, "실행중에 차일드 뷰 전개하여 추가하기"));
			arExample.add(new Example(LayoutParameter.class, "레이아웃 전개"));
			arExample.add(new Example(LayoutParameter2.class, "코드로 배치하면서 레이아웃 파라미터 생략"));
			arExample.add(new Example(LayoutParameter3.class, "코드로 배치하면서 레이아웃 파라미터 지정"));
			arExample.add(new Example(MarginParameter.class, "XML로 마진 주기"));
			arExample.add(new Example(MarginParameter2.class, "코드로 마진 주기"));
			arExample.add(new Example(SetParameter.class, "실행중에 레이아웃 파라미터 변경"));
			arExample.add(new Example(GridHorz.class, "수평 그리드 레이아웃"));
			arExample.add(new Example(GridVert.class, "수직 그리드 레이아웃"));
			arExample.add(new Example(GridLinear.class, "일렬 그리드 레이아웃"));
			arExample.add(new Example(CellSize.class, "셀 크기 지정"));
			arExample.add(new Example(CellPosition.class, "셀 위치 지정"));
			arExample.add(new Example(CellSpan.class, "셀 병합"));
			arExample.add(new Example(TwoLine.class, "2열의 텍스트 뷰 배치"));
			arExample.add(new Example(InputForm.class, "그리드를 이용한 입력 폼"));
			break;
		case 7: // 출력
			arExample.add(new Example(CustomView.class, "커스텀 뷰에 직접 그리기"));
			arExample.add(new Example(Primitive1.class, "기본 도형-점,선,원,사각형,문자열"));
			arExample.add(new Example(Primitive2.class, "기본 도형-둥근 사각형,타원,반원,다각선"));
			arExample.add(new Example(AntiAlias.class, "안티 알리아싱 옵션"));
			arExample.add(new Example(Stroke.class, "굵기, 끝장식, 조인 등 선의 속성"));
			arExample.add(new Example(PaintStyle.class, "채움 및 외곽선 그리기 스타일"));
			arExample.add(new Example(NewPaint.class, "Paint 객체 매번 생성하기"));
			arExample.add(new Example(PrePaint.class, "Paint 객체를 미리 생성해 놓기"));
			arExample.add(new Example(DrawBitmap.class, "비트맵 이미지 출력. 확대 및 일부 영역 출력"));
			arExample.add(new Example(OffScreen.class, "메모리에서 비트맵 생성하기"));
			arExample.add(new Example(DrawText.class, "텍스트 출력"));
			arExample.add(new Example(TypeFace.class, "텍스트의 타입 페이스"));
			arExample.add(new Example(CustomFont.class, "커스텀 폰트 사용하기"));
			arExample.add(new Example(DrawPath.class, "패스 출력"));
			arExample.add(new Example(LinearGrad.class, "직선 그래디언트"));
			arExample.add(new Example(RadialGrad.class, "원형 그래디언트"));
			arExample.add(new Example(SweepGrad.class, "원주 그래디언트"));
			arExample.add(new Example(BitmapSdr.class, "비트맵 셰이더"));
			arExample.add(new Example(ComposeSdr.class, "조합 셰이더"));
			arExample.add(new Example(MirrorImage.class, "조합 셰이더를 이용한 반사 이미지"));
			arExample.add(new Example(Shape.class, "셰이프 드로블 출력"));
			arExample.add(new Example(ToastTest.class, "토스트 출력"));
			arExample.add(new Example(Beep.class, "간단한 비프음"));
			arExample.add(new Example(Vibrate.class, "폰 진동시키기"));
			break;
		case 8: // 입력
			arExample.add(new Example(HandleEvent.class, "여러 가지 이벤트 처리 방식 연구"));
			arExample.add(new Example(HandlerOrder.class, "핸들러의 우선 순위 연구"));
			arExample.add(new Example(HandlerOrder2.class, "모든 핸들러 순차적 호출"));
			arExample.add(new Example(HandlerAccess.class, "핸들러에서 외부 변수 액세스"));
			arExample.add(new Example(FreeLine.class, "터치 이벤트로 자유 곡선 그리기"));
			arExample.add(new Example(MoveCircle.class, "키보드로 원 움직이기"));
			arExample.add(new Example(Fruit.class, "위젯의 이벤트 처리 및 핸들러 통합"));
			arExample.add(new Example(Fruit2.class, "onClick 속성으로 클릭 이벤트 처리"));
			arExample.add(new Example(LongClick.class, "위젯의 롱클릭 처리"));
			arExample.add(new Example(FocusTest.class, "포커스 이동(디폴트)"));
			arExample.add(new Example(FocusTest2.class, "포커스 이동(순환)"));
			arExample.add(new Example(TimerTest.class, "핸들러를 이용한 타이머"));
			break;
		case 9: // 메뉴
			arExample.add(new Example(OptionMenu.class, "옵션 메뉴, 서브 메뉴로 토스트 열어 보기"));
			arExample.add(new Example(OptionMenuXml.class, "XML로 메뉴 정의하여 전개"));
			arExample.add(new Example(MenuOnClick.class, "onClick 속성으로 메뉴 선택하기"));
			arExample.add(new Example(ContextMenuTest.class, "위젯에 컨텍스트 메뉴 부착"));
			arExample.add(new Example(PopupMenuTest.class, "위젯에 팝업 메뉴 부착"));
			arExample.add(new Example(MenuHistory.class, "버전에 따른 메뉴의 모양과 기능 살펴 보기"));
			arExample.add(new Example(MenuCheck.class, "메뉴로 체크 및 라디오 옵션 입력 및 출력"));
			arExample.add(new Example(ChangeMenu.class, "실행중에 메뉴 교체하기"));
			break;
		case 10: // 개발툴
			arExample.add(new Example(MemoryPower.class, "기억력 게임(도형)"));
			arExample.add(new Example(MemoryPowerBaby.class, "기억력 게임(이미지)"));
			arExample.add(new Example(NumPang.class, "숫자 3연속 이상 맞추기 게임"));
			arExample.add(new Example(BabyPang.class, "아기 사진 3연속 이상 맞추기 게임"));
			arExample.add(new Example(MemoryPower2.class, "디버깅 실습을 위한 오류 프로젝트"));			
			arExample.add(new Example(LogTest.class, "로그 기록 남기기"));
			break;
		case 11: // 기본위젯
			arExample.add(new Example(ReadResource.class, "리소스에서 문자열, 색상, 크기 읽기"));
			arExample.add(new Example(ReadResource2.class, "레이아웃에서 리소스 읽기"));
			arExample.add(new Example(StyleTest.class, "스타일 계층 정의 및 사용"));
			arExample.add(new Example(StyleTest2.class, "부모 스타일 변경"));
			arExample.add(new Example(ThemeTest.class, "액티비티에 타이틀바 없음 테마 적용"));
			arExample.add(new Example(SystemTheme.class, "액티비티에 대화상자 테마 적용"));
			arExample.add(new Example(TextViewAttr.class, "텍스트 뷰의 속성 연구"));
			arExample.add(new Example(SpannableTest.class, "Spannable 버퍼 타입. 서식 미지정"));
			arExample.add(new Example(SpannableTest2.class, "Spannable 버퍼 타입으로 서식 문자열 출력"));
			arExample.add(new Example(TextViewHtml.class, "HTML 태그로 서식 문자열 출력"));
			arExample.add(new Example(EditableTest.class, "Editable 버퍼 타입으로 문자열 직접 수정"));
			arExample.add(new Example(TextChange.class, "텍스트 변경 이벤트"));
			arExample.add(new Example(GramPrice.class, "그램당 상품 가격 계산"));
			arExample.add(new Example(EditLimit.class, "텍스트 입력 길이 제한"));
			arExample.add(new Example(EditSelect.class, "선택 영역 관리"));
			arExample.add(new Example(InputTypeTest.class, "inputType 속성으로 키보드의 종류 선택"));
			arExample.add(new Example(ShowHideKey.class, "코드에서 키보드 보이기/숨기기"));
			arExample.add(new Example(AdjustKey1.class, "키보드 Panning 모드"));
			arExample.add(new Example(AdjustKey2.class, "키보드 Resize 모드"));
			arExample.add(new Example(NoNinePatch.class, "보통 이미지 배경 사용"));
			arExample.add(new Example(YesNinePatch.class, "나인패치 이미지 배경 사용"));
			arExample.add(new Example(ArrowButton.class, "상태에 따라 색상이 변하는 화살표 버튼"));
			arExample.add(new Example(RadioCheck.class, "라디오 버튼, 체크 박스"));
			arExample.add(new Example(FilterTouch.class, "버튼의 터치 필터링 속성"));
			arExample.add(new Example(ScaleTypeTest.class, "이미지 뷰의 확대 모드"));
			arExample.add(new Example(ImageViewAttr.class, "이미지 뷰의 속성 연구"));
			arExample.add(new Example(ImageButtonTest.class, "이미지 버튼 테스트"));
			arExample.add(new Example(ButtonDrawable.class, "버튼에 드로블 같이 표시하기"));
			arExample.add(new Example(ButtonSelector.class, "버튼에 셀렉터 적용하여 상태 표시하기"));
			break;
		case 12: // 어댑터 뷰
			arExample.add(new Example(ListViewTest.class, "리스트뷰에 문자열 항목 표시"));
			arExample.add(new Example(ListFromArray.class, "리스트뷰에 XML 배열 표시"));
			arExample.add(new Example(ListAttr.class, "리스트뷰의 구분선 속성"));
			arExample.add(new Example(ListItemSelect.class, "리스트뷰의 항목 선택"));
			arExample.add(new Example(ListAddDel.class, "실행중에 항목 삽입 및 삭제"));
			arExample.add(new Example(ListAddDelMulti.class, "여러 개의 항목 한꺼번에 삭제하기"));
			arExample.add(new Example(ListAddDelMulti2.class, "라디오 버튼으로 여러 항목 삭제"));
			arExample.add(new Example(ListAddDelMulti3.class, "체크 박스로 여러 항목 삭제"));
			arExample.add(new Example(ListIconText.class, "아이콘과 텍스트로 항목 뷰 구성하기"));
			arExample.add(new Example(ListOfViews.class, "여러 종류의 항목 뷰 섞어서 표시"));
			arExample.add(new Example(ManyItem.class, "대용량 항목 뷰 표시와 리스트 뷰의 동작 연구"));
			arExample.add(new Example(Expandable.class, "2단계의 확장 리스트 뷰"));
			arExample.add(new Example(ListOnly.class, "ListActivity 사용"));
			arExample.add(new Example(OverScroll.class, "리스트뷰의 오버 스크롤 "));
			arExample.add(new Example(SpinnerTest.class, "스피너로 과일 이름 선택하기"));
			arExample.add(new Example(GridViewTest.class, "그리드뷰로 이미지 선택하기"));
			arExample.add(new Example(GalleryTest.class, "갤러리로 이미지 선택하기"));
			break;
		case 13: // 고급 위젯
			arExample.add(new Example(ProgressBarTest.class, "프로그래스 바"));
			arExample.add(new Example(ProgressTitle.class, "타이틀바의 프로그래스 바"));
			arExample.add(new Example(ProgressTitle2.class, "타이틀바의 원형 프로그래스"));
			arExample.add(new Example(SeekBarTest.class, "시크 바"));
			arExample.add(new Example(RatingBarTest.class, "래이팅 바"));
			arExample.add(new Example(DateTimeTest.class, "날짜 및 시간 조사 방법"));
			arExample.add(new Example(SpeedTest.class, "덧셈 5억번 수행 시간 측정"));
			arExample.add(new Example(ClockTest.class, "디지털, 아날로그 시계 위젯"));
			arExample.add(new Example(DatePickerTest.class, "날짜 선택 위젯"));
			arExample.add(new Example(TimePickerTest.class, "시간 선택 위젯"));
			arExample.add(new Example(PickerDialogTest.class, "날짜, 시간 선택 대화상자"));
			arExample.add(new Example(ChronometerTest.class, "스톱워치 위젯"));
			arExample.add(new Example(StopWatch.class, "핸들러로 구현한 스톱워치"));
			arExample.add(new Example(AutoComplete.class, "자동 완성 에디트"));
			arExample.add(new Example(MultiAuto.class, "자동 완성 멀티 에디트"));
			arExample.add(new Example(SlidingDrawerTest.class, "화면 아래쪽에 숨겨진 서랍"));
			arExample.add(new Example(ScrollViewTest.class, "스크롤 뷰(색상 뷰 수직 스크롤)"));
			arExample.add(new Example(HScrollView.class, "수평 스크롤 뷰"));
			arExample.add(new Example(WebViewTest.class, "웹 뷰-웹 페이지 및 리소스의 HTML 보기"));			
			arExample.add(new Example(SportsScore.class, "스포츠 경기의 점수 매기기"));			
			arExample.add(new Example(SwitchTest.class, "스위치 위젯"));
			arExample.add(new Example(SpaceTest.class, "Space위젯으로 여백 띄우기"));
			arExample.add(new Example(NumberPickerTest.class, "숫자 선택기(Holo 테마)"));
			arExample.add(new Example(NumberPickerTest2.class, "숫자 선택기(Theme 테마)"));
			arExample.add(new Example(CalendarViewTest.class, "달력 위젯"));
			arExample.add(new Example(ListPopupWindowTest.class, "팝업 목록"));
			break;
		case 14: // 커스텀 위젯
			arExample.add(new Example(SoundEdit.class, "문자 입력시마다 소리나는 커스텀 에디트 뷰"));
			arExample.add(new Example(NumEdit.class, "입력문자수를 표시하는 조합 위젯"));
			arExample.add(new Example(AttributeTest.class, "생성자로 전달되는 속성 집합 덤프"));
			arExample.add(new Example(SoundEdit2.class, "커스텀 속성을 적용한 소리나는 에디트"));
			arExample.add(new Example(Measuring.class, "onMeasure-wrap, wrap"));
			arExample.add(new Example(Measuring2.class, "onMeasure-100, 50"));
			arExample.add(new Example(Measuring3.class, "onMeasure-match, match"));
			arExample.add(new Example(Measuring4.class, "onMeasure-버튼있는 match, match"));
			arExample.add(new Example(Measuring5.class, "onMeasure-종횡비 유지"));
			arExample.add(new Example(Measuring6.class, "onMeasure-weight로 삼분할"));
			arExample.add(new Example(Rainbow.class, "무지개 프로그래스 바 위젯"));
			break;
		case 15: // 리소스 관리
			arExample.add(new Example(LandPort.class, "화면 방향별 레이아웃 정의"));
			arExample.add(new Example(MultiLang.class, "한글, 영어 다국어 지원"));
			arExample.add(new Example(DisplayMetricsTest.class, "디스플레이 정보 조사"));
			arExample.add(new Example(LogicUnit.class, "논리 단위를 사용해야 하는 이유 연구"));
			arExample.add(new Example(ConvertDpi.class, "DPI 변환 유틸리티"));
			arExample.add(new Example(ResDensity.class, "밀도별 리소스의 프리 스케일링"));
			arExample.add(new Example(SameSize.class, "밀도에 무관하게 같은 크기로 이미지 출력"));
			arExample.add(new Example(FillWidth.class, "레이아웃 폭 가득 채우기"));
			arExample.add(new Example(DrawUnit.class, "실행중에 논리 단위를 픽셀로 변환하기"));
			arExample.add(new Example(RulerTest.class, "밀리, 인치 단위의 자"));
			arExample.add(new Example(ScreenSize.class, "화면 크기에 따른 레이아웃 정의"));
			arExample.add(new Example(SmallestWidth.class, "짧은폭에 대한 리소스 선택 연구"));
			arExample.add(new Example(SwLayout.class, "짧은폭 기준 레이아웃"));
			break;
		case 16: // 대화상자
			arExample.add(new Example(DialogTest.class, "Dialog 클래스를 이용한 대화상자"));
			arExample.add(new Example(AlertDialogTest.class, "AlertDialog 대화상자"));
			arExample.add(new Example(DialogButton.class, "대화상자에 닫기 버튼 배치"));
			arExample.add(new Example(OkCancel.class, "확인, 취소 2개의 버튼 배치"));
			arExample.add(new Example(Cancelable.class, "Back 버튼 금지. 버튼을 눌러야 닫힘"));
			arExample.add(new Example(TouchOutside.class, "대화상자 바깥을 눌러서 닫기"));
			arExample.add(new Example(ShowDialog.class, "대화상자 미리 생성해 놓기"));
			arExample.add(new Example(ErrorMessage1.class, "에러 메시지 출력-안보임"));
			arExample.add(new Example(ErrorMessage2.class, "에러 메시지 출력-보임"));
			arExample.add(new Example(Question1.class, "질문하기의 잘못된 예"));
			arExample.add(new Example(Question2.class, "질문후 응답 결과에 따라 연산하기"));
			arExample.add(new Example(Question3.class, "3단계 질문 통합하기"));
			arExample.add(new Example(SelectDialog1.class, "목록 선택. 항목 클릭 즉시 닫힘"));
			arExample.add(new Example(SelectDialog2.class, "단일 선택. 항목 선택 후 확인 버튼"));
			arExample.add(new Example(SelectDialog3.class, "복수 선택. 여러 항목 선택 후 확인 버튼"));
			arExample.add(new Example(OrderDialog.class, "상품 주문 커스텀 대화상자"));
			arExample.add(new Example(Popup.class, "팝업 윈도우 열기"));			
			break;
		case 17: // 액티비티
			arExample.add(new Example(CallActivity.class, "내부 액티비티 호출"));
			arExample.add(new Example(CommActivity.class, "인텐트를 통한 액티비티간의 통신"));
			arExample.add(new Example(CallOther.class, "여러 가지 외부 액티비티 호출"));
			arExample.add(new Example(Add.class, "정수 덧셈 액티비티"));
			arExample.add(new Example(CallAdd.class, "암시적 인텐트로 호출"));
			arExample.add(new Example(ActParent.class, "생명주기 메서드 호출 순서 연구"));
			arExample.add(new Example(SaveState.class, "상태 저장 안함. 화면 회전시 리셋"));
			arExample.add(new Example(SaveState2.class, "x값만 임시적으로 저장"));
			arExample.add(new Example(SaveState3.class, "x는 임시저장 y는 영구 저장"));
			arExample.add(new Example(SaveCurve.class, "시리얼라이즈로 곡선 객체 저장"));
			arExample.add(new Example(SaveCurve2.class, "Parcel 객체로 곡선 정보 저장"));
			arExample.add(new Example(SaveCurve3.class, "액티비티 파괴 금지"));
			arExample.add(new Example(TabTest.class, "탭-뷰의 id 지정"));
			arExample.add(new Example(TabTest2.class, "탭-팩토리로 생성"));
			arExample.add(new Example(TabTest3.class, "탭안에 액티비티 포함"));
			arExample.add(new Example(CustomTab.class, "프레임을 이용한 커스텀 탭"));			
			break;
		case 18: // 프로세스
			arExample.add(new Example(ApplicationTest.class, "Application 객체 테스트"));
			arExample.add(new Example(NoTitle.class, "타이틀 바 없음"));
			arExample.add(new Example(FullScreen.class, "전체 화면 사용"));
			arExample.add(new Example(Overlay.class, "레이아웃 겹치기"));
			arExample.add(new Example(Center.class, "화면 중앙에 윈도우 열기"));
			arExample.add(new Example(WindowManagerTest.class, "윈도우 관리자"));
			arExample.add(new Example(DragReorder.class, "드래그해서 항목 순서 변경하기"));
			break;
		case 19: // 스레드
			arExample.add(new Example(ThreadTest.class, "스레드로 숫자 증가시키기"));
			arExample.add(new Example(HandlerTest.class, "핸들러로 스레드에서 UI 갱신하기"));
			arExample.add(new Example(LooperTest.class, "루퍼로 양방향 통신하기"));
			arExample.add(new Example(Upload.class, "대화상자가 사라지지 않음"));
			arExample.add(new Example(Post.class, "작업 등록 후 즉시 리턴"));
			arExample.add(new Example(ANR.class, "ANR 문제 확인"));
			arExample.add(new Example(ANR2.class, "스레드로 ANR 문제 해결"));
			arExample.add(new Example(StrictModeTest.class, "스트릭트 모드"));
			arExample.add(new Example(LongTime.class, "긴 작업(블로킹)"));
			arExample.add(new Example(LongTime2.class, "핸들러로 작업 경과 표시"));
			arExample.add(new Example(LongTime3.class, "프로그래스로 경과 표시 및 취소"));
			arExample.add(new Example(LongTime4.class, "스레드 사용"));
			arExample.add(new Example(LongTime5.class, "AsyncTask 사용"));
			arExample.add(new Example(BackWork.class, "백그라운드 작업"));
			arExample.add(new Example(BackWork2.class, "백그라운드 작업-스레드"));
			arExample.add(new Example(BackWork3.class, "백그라운드 작업-대기 스레드"));			
			break;
		case 20: // 프래그먼트 
			arExample.add(new Example(FragmentTest.class, "프래그먼트"));
			arExample.add(new Example(ReuseFragment.class, "프래그먼트 재사용"));
			arExample.add(new Example(TwoFragment1.class, "두 개의 프래그먼트 배치(정상 동작)"));
			arExample.add(new Example(TwoFragment2.class, "두 개의 프래그먼트 배치(왼쪽만 동작)"));
			arExample.add(new Example(SaveFragmentState.class, "프래그먼트 상태의 저장 및 복구"));
			arExample.add(new Example(FragmentManagerTest.class, "프래그먼트 관리자"));
			arExample.add(new Example(FragmentArgument.class, "프로그래먼트로 인수 전달"));
			arExample.add(new Example(BackStack.class, "프로그먼트의 백스택"));
			arExample.add(new Example(ListFragmentTest.class, "프래그먼트를 이용한 단어 목록"));
			arExample.add(new Example(WordList.class, "단어의 뜻을 보여 주는 단어장"));
			arExample.add(new Example(DialogFragmentTest.class, "프래그먼트 대화상자"));
			arExample.add(new Example(DialogFragmentEmbed.class, "액티비티에 프래그먼트 대화상자 내장하기"));
			arExample.add(new Example(DialogStyleTheme.class, "프래그먼트 대화상자의 스타일과 테마"));
			arExample.add(new Example(MultiPaneWidth.class, "크기에 따른 2단 배치"));
			arExample.add(new Example(MultiPaneOrient.class, "방향에 따른 2단 배치"));
			break;
		case 21: // 액션바 
			arExample.add(new Example(ActionBarTest.class, "액션 바 테스트"));
			arExample.add(new Example(NoActionBar.class, "액션바 숨기기"));
			arExample.add(new Example(ShowHideActionBar.class, "액션바 보이기/숨기기"));
			arExample.add(new Example(OverlayActionBar.class, "액션바 오버레이"));
			arExample.add(new Example(SplitActionBar.class, "액션바 분리"));
			arExample.add(new Example(AppIcon.class, "액션바에 앱 아이콘 배치"));
			arExample.add(new Example(ActionView.class, "검색 액션 뷰"));
			arExample.add(new Example(ActionSwitch.class, "액션 뷰의 스위치 위젯"));
			arExample.add(new Example(ActionProviderTest.class, "액션 프로바이더로 숫자 증감"));
			arExample.add(new Example(ShareAction.class, "공유 액션 프로바이더"));
			arExample.add(new Example(ActionTab.class, "3개의 페이지로 구성된 액션 탭"));
			arExample.add(new Example(DisplayOption.class, "액션 바 출력 옵션"));
			arExample.add(new Example(ActionModeTest.class, "액션 바에 임시적으로 열리는 액션 모드"));
			break;
		case 22: // 그리기
			arExample.add(new Example(BlurFlt.class, "블러 필터"));
			arExample.add(new Example(EmbossFlt.class, "임보싱 필터"));
			arExample.add(new Example(ColorFlt.class, "색상 필터"));
			arExample.add(new Example(ColorM.class, "이미지 반전"));
			arExample.add(new Example(GrayScale.class, "그레이 스케일"));
			arExample.add(new Example(Porter.class, "Porter, Duff의 색상 변환 규칙"));
			arExample.add(new Example(DashPathEft.class, "선 모양 변경"));
			arExample.add(new Example(CornerPathEft.class, "모서리 변경"));
			arExample.add(new Example(PathDashEft.class, "화살표 모양 대시"));
			arExample.add(new Example(DashAnim.class, "대시 애니메이션"));
			arExample.add(new Example(Xfer.class, "Xfermode"));
			arExample.add(new Example(Dither.class, "디더링"));
			arExample.add(new Example(AccelTest1.class, "하드웨어 가속 기능 사용"));
			arExample.add(new Example(AccelTest2.class, "하드웨어 가속 사용 금지"));
			arExample.add(new Example(BlurFlt2.class, "블러 필터(하드웨어 가속)"));
			arExample.add(new Example(DashPathEft2.class, "선 모양 변경(하드웨어 가속)"));
			arExample.add(new Example(Translate.class, "이동 변환"));
			arExample.add(new Example(Translate2.class, "문자열 연속 출력"));
			arExample.add(new Example(SaveCanvas.class, "캔버스 상태 저장"));
			arExample.add(new Example(Skew.class, "기울이기"));
			arExample.add(new Example(Scale.class, "확대"));
			arExample.add(new Example(TransOrder.class, "변환의 순서에 따른 출력 결과 연구"));
			arExample.add(new Example(Rotate.class, "회전 변환"));
			arExample.add(new Example(Reflection.class, "직접 그리기"));
			arExample.add(new Example(Reflection2.class, "SurfaceView로 그리기"));
			arExample.add(new Example(ReDraw1.class, "느린 그리기"));
			arExample.add(new Example(ReDraw2.class, "객체 미리 생성"));
			arExample.add(new Example(ReDraw3.class, "클리핑 최소화"));
			arExample.add(new Example(ReDraw4.class, "지연된 그리기"));
			arExample.add(new Example(ReDraw5.class, "비트맵 배경 사용"));			
			arExample.add(new Example(OpenGLTest.class, "OpenGL 3D 그래픽 테스트"));
			break;
		case 23: // 애니메이션
			arExample.add(new Example(FrameAni.class, "애기가 북을 두드리는 프레임 애니메이션"));
			arExample.add(new Example(AnimationTest.class, "무궁화 이미지에 대한 트윈 애니메이션"));
			arExample.add(new Example(AnimAttr.class, "애니메이션의 속성 관찰 예제"));
			arExample.add(new Example(AnimSet.class, "이동 및 알파 애니메이션 집합"));
			arExample.add(new Example(AnimCustom.class, "XML 문서로 애니메이션 정의하기.기울이기, 카메라"));
			arExample.add(new Example(Tween.class, "버튼 집합에 대한 여러 가지 트윈 애니메이션"));
			arExample.add(new Example(TweenListener.class, "회전-알파-확대 연속 애니메이션"));
			arExample.add(new Example(ListAnim.class, "리스트 애니메이션, 위에서부터 순서대로"));
			arExample.add(new Example(ListAnim2.class, "리스트 애니메이션, 아래에서부터 순서대로"));
			arExample.add(new Example(GridAnim.class, "그리드 애니메이션, 수평으로 항목 전개"));
			arExample.add(new Example(GridAnim2.class, "그리드 애니메이션, 수직으로 항목 전개"));
			arExample.add(new Example(ActAnim.class, "액티비티의 애니메이션. 아래로 사라짐"));
			arExample.add(new Example(ViewFlipperTest.class, "애니메이션 뷰 교체"));
			arExample.add(new Example(TextSwitcherTest.class, "애니메이션 텍스트 교체"));
			break;
		case 24: // 속성 애니메이션
			arExample.add(new Example(ValueAnimatorTest.class, "값 애니메이터로 버튼 폭 애니메이션"));
			arExample.add(new Example(ValueAnimatorTest2.class, "값 애니메이터의 속성"));
			arExample.add(new Example(ObjectAnimatorTest.class, "객체 애니메이터로 버튼 폭 애니메이션"));
			arExample.add(new Example(ObjectAnimatorTest2.class, "객체 애니메이터의 속성"));
			arExample.add(new Example(BallAnim.class, "공을 왼쪽에서 오른쪽으로 이동"));
			arExample.add(new Example(ColorAnim.class, "색상 애니메이션"));
			arExample.add(new Example(AnimInterpolator.class, "여러 가지 인터폴레이터 테스트"));
			arExample.add(new Example(AnimatorSetTest.class, "애니메이터 그룹으로 연속 애니메이션"));
			arExample.add(new Example(XmlAnim.class, "XML 리소스로 애니메이션 정의"));
			arExample.add(new Example(LayoutAnim.class, "레이아웃 애니메이션"));
			break;
		case 25: // 파일
			arExample.add(new Example(FileIO.class, "파일에 문자열 입출력 및 파일 삭제"));
			arExample.add(new Example(andexam.ver4_1.external.ShareFile.class, "패키지간 파일 공유"));
			arExample.add(new Example(SDCard.class, "SD Card에 문자열 입출력"));
			arExample.add(new Example(TextLogTest.class, "디버깅을 위한 텍스트 로그 클래스"));
			arExample.add(new Example(FileExplorer.class, "파일 탐색기"));
			arExample.add(new Example(ReadZip.class, "ZIP 압축 파일 읽기"));
			arExample.add(new Example(PrefTest.class, "프레프런스에 정수와 문자열 저장"));
			arExample.add(new Example(PrefActivity.class, "프레프런스 액티비티"));
			arExample.add(new Example(TextPrefTest.class, "텍스트 프레퍼런스 테스트"));
			break;
		case 26: // CP
			arExample.add(new Example(EnglishWord.class, "DB를 이용한 영어 단어장"));
			arExample.add(new Example(ProductList.class, "커서와 연결된 리스트 뷰"));
			arExample.add(new Example(CallWordCP.class, "영어 단어장 CP 호출"));			
			break;
		case 27: // 클립보드 
			arExample.add(new Example(CopyText.class, "텍스트 복사"));
			arExample.add(new Example(CopyUri.class, "URI 복사"));
			arExample.add(new Example(CopyIntent.class, "Intent 복사"));
			arExample.add(new Example(DragButton.class, "버튼 드래그 & 드롭"));
			arExample.add(new Example(DragShadow.class, "드래그 섀도우 작성"));
			arExample.add(new Example(DragCoin.class, "동전 드래그"));
			break;
		case 28: // 네트워크
			arExample.add(new Example(ConMgr.class, "연결 관리자로 연결 방법 덤프"));
			arExample.add(new Example(DownHtml.class, "HTML로 웹 문서 다운로드(예외 발생)"));
			arExample.add(new Example(DownHtml2.class, "HTML로 웹 문서 다운로드(정책 수정)"));
			arExample.add(new Example(AsyncDownHtml.class, "HTML 비동기 다운로드"));
			arExample.add(new Example(ApacheDownHtml.class, "아파치 라이브러리 HTML 다운로드"));
			arExample.add(new Example(DownImage.class, "이미지 다운로드받아서 그리기"));
			arExample.add(new Example(DownImage2.class, "이미지 미리 다운로드 받아두기"));
			arExample.add(new Example(DownloadManagerTest.class, "다운로드 관리자로 다운로드 예약하기"));
			arExample.add(new Example(SearchRank.class, "네이버 검색어 순위 조사"));
			arExample.add(new Example(DomParser.class, "DOM 파서로 문자열 값 조사"));
			arExample.add(new Example(DomParser2.class, "DOM 파서로 복수 엘리먼트와 속성 읽기"));
			arExample.add(new Example(SaxParser.class, "SAX 파서로 XML 문서 읽기"));
			arExample.add(new Example(PullParser.class, "PULL 파서로 XML 문서 읽기"));
			arExample.add(new Example(JSONArrayTest.class, "JSON 정수 배열 읽어서 합산"));
			arExample.add(new Example(JSONObjectTest.class, "JSON 객체 읽어서 출력"));			
			break;
		case 29: // BR
			arExample.add(new Example(NapAlarm.class, "낮잠 시간을 알려 주는 알람 시계"));
			arExample.add(new Example(CustomNotiView.class, "확장 상태란의 커스텀 통지 뷰"));
			arExample.add(new Example(DetectFree.class, "공짜 네트워크 발견 방송 발송"));
			arExample.add(new Example(DetectSaveZone.class, "할인 지역 발견 방송 발송"));
			arExample.add(new Example(OnSaveZone.class, "할인 지역 발견 방송 청취 "));
			arExample.add(new Example(WatchBattery.class, "배터리 감시하여 메시지 출력"));
			arExample.add(new Example(WatchSdcard.class, "SdCard 감시하여 변화 출력"));
			arExample.add(new Example(AlarmTest.class, "일회성 알람, 주기적 알람"));
			break;
		case 30: // 서비스
			arExample.add(new Example(NewsController.class, "뉴스 보기 서비스 관리"));
			arExample.add(new Example(andexam.ver4_1.external.NewsController.class, "외부 패키지에서 뉴스 보기"));
			arExample.add(new Example(CalcClient.class, "서비스로 최소 공배수, 소수 여부 연산(AIDL)"));			
			arExample.add(new Example(andexam.ver4_1.external.CalcClient.class, "외부 패키지에서 서비스로 연산"));			
			arExample.add(new Example(CalcClient2.class, "서비스로 최소 공배수, 소수 여부 연산(로컬)"));			
			break;
		case 31: // 제스처
			arExample.add(new Example(GestureDump.class, "제스처 이벤트 덤프"));
			arExample.add(new Example(GestureNavi.class, "제스처로 좌우 이동하기"));
			arExample.add(new Example(CustomGesture.class, "커스텀 제스처 인식"));
			arExample.add(new Example(GestureOverlay.class, "제스처 오버레이"));
			arExample.add(new Example(TouchDump.class, "터치 덤프"));
			arExample.add(new Example(PinchZoom.class, "멀티 터치로 확대 및 축소"));
			arExample.add(new Example(ImageZoom.class, "멀티 터치로 이미지 확대 및 축소"));
			break;
		case 32: // 맵서비스
			arExample.add(new Example(GetProvider.class, "위치 제공자 조사."));
			arExample.add(new Example(ReadLocation.class, "현재 위치 읽기"));
			arExample.add(new Example(LastKnown.class, "최근 위치 조사"));
			arExample.add(new Example(LocationConvert.class, "포맷 변환"));
			arExample.add(new Example(LocationAlert.class, "도착 알림"));
			arExample.add(new Example(ViewLocation.class, "63빌딩 위치 보기"));
			arExample.add(new Example(MapViewTest.class, "맵뷰 테스트"));
			arExample.add(new Example(GeoCoding.class, "지오 코딩"));
			arExample.add(new Example(OverlayWidget.class, "오버레이 위젯"));
			arExample.add(new Example(OverlayView.class, "오버레이 뷰"));
			arExample.add(new Example(OverlayMulti.class, "오버레이 항목"));
			arExample.add(new Example(OverlayLocation.class, "현재 위치 및 나침반"));
			break;
		case 33: // 멀티미디어
			arExample.add(new Example(MPTest.class, "MediaPlayer의 간단한 사용법 연구"));
			arExample.add(new Example(PlayAudio.class, "MP3 음악 재생기"));
			arExample.add(new Example(RecAudio.class, "레코더를 이용한 음성 녹음"));
			arExample.add(new Example(SoundPoolTest.class, "SoundPool 클래스 테스트"));
			arExample.add(new Example(LoadComplete.class, "사운드 로드 완료 리스너"));
			arExample.add(new Example(ChangeVolume.class, "볼륨 조절"));
			arExample.add(new Example(PlayVideo.class, "테스트 동영상 재생"));
			arExample.add(new Example(VideoViewer.class, "VideoView를 활용한 동영상 재생"));
			arExample.add(new Example(RecVideo.class, "레코더를 이용한 영상 및 음성 녹화"));
			arExample.add(new Example(DumpMedia.class, "미디어 DB의 구조와 목록을 조사한다."));
			arExample.add(new Example(DumpMedia2.class, "미디어 목록의 변화에 대해 반응한다."));
			arExample.add(new Example(ImageViewer.class, "미디어 DB의 이미지 보기"));
			arExample.add(new Example(ImageGrid.class, "그리드 뷰로 이미지 보기"));
			arExample.add(new Example(CameraTest.class, "촬영만 가능한 카메라 예제"));
			arExample.add(new Example(SHCamera.class, "포커싱,해상도,접사,리뷰 지원 카메라"));
			arExample.add(new Example(AttachImage.class, "카메라, 갤러리를 호출하여 첨부 이미지 얻기"));
			break;
		case 34: // 센서
			arExample.add(new Example(SensorManagerTest.class, "센서 관리자로 지원되는 센서 정보 출력"));
			arExample.add(new Example(SensorDump.class, "모든 센서값을 출력해 본다."));
			arExample.add(new Example(Compass.class, "방향 센서를 이용한 나침반 및 수평계"));
			arExample.add(new Example(Accelerator.class, "가속 센서의 값 변화를 그래픽으로 보기"));
			arExample.add(new Example(MotionCounter.class, "가속 센서를 이용한 카운터"));
			arExample.add(new Example(ShakeCounter.class, "흔들기를 이용한 카운터"));
			arExample.add(new Example(ScreenFlash.class, "뒤집으면 밝아지는 후레쉬"));
			arExample.add(new Example(GetOrientation.class, "회전 행렬을 이용한 방향 조사"));
			arExample.add(new Example(Ottogi.class, "항상 위쪽으로 서는 오뚜기"));
			break;
		case 35: // 시스템 설정
			arExample.add(new Example(WakeAlways.class, "항상 켜 있기"));
			arExample.add(new Example(ReadingCounter.class, "독서 도우미"));
			arExample.add(new Example(UserInteraction.class, "사용자 입력 감시"));
			arExample.add(new Example(GetSetting.class, "설정값 조사"));
			arExample.add(new Example(SetSetting.class, "설정값 변경"));
			arExample.add(new Example(MakeWallPaper.class, "벽지 변경"));
			break;
		case 36: // 전화
			arExample.add(new Example(TelState.class, "전화 상태 조사"));
			arExample.add(new Example(TelCall.class, "전화 걸기"));
			arExample.add(new Example(YieldCall.class, "통화중 대기"));
			arExample.add(new Example(YieldCall2.class, "게임중 통화시 잠시 멈춤"));
			arExample.add(new Example(FormatNumber.class, "전화 번호 관리"));
			arExample.add(new Example(CallSms.class, "문자 메시지 프로그램 호출"));
			arExample.add(new Example(SendSms.class, "문자 메시지 보내기"));
			arExample.add(new Example(ReceiveSms.class, "문자 메시지 받기"));
			arExample.add(new Example(ReadContactOld.class, "1.6 이전의 주소록 덤프"));
			arExample.add(new Example(ReadContact.class, "주소록 덤프"));
			arExample.add(new Example(CallLogTest.class, "통화 기록 덤프"));
			arExample.add(new Example(SmsLog.class, "문자 메시지 덤프"));
			break;
		case 37: // 앱위젯
			arExample.add(new Example(AppWidgetManagerTest.class, "앱 위젯 관리자로 위젯 목록 덤프"));
			break;
		}
	}
	
	// 예제는 4장부터 제공된다. 4(START_CHAPTER)장이 첨자 0번이다.
	String[] arChapter = {
			"4장 뷰",
			"5장 레이아웃",
			"6장 레이아웃 관리",
			"7장 출력",
			"8장 입력",
			"9장 메뉴",
			"10장 개발툴",
			"11장 기본 위젯",
			"12장 어댑터 뷰",
			"13장 고급 위젯",
			"14장 커스텀 위젯",
			"15장 리소스 관리",
			"16장 대화상자",
			"17장 액티비티",
			"18장 프로세스",
			"19장 스레드",
			"20장 프래그먼트",
			"21장 액션바",
			"22장 그리기",
			"23장 애니메이션",
			"24장 속성 애니메이션",
			"25장 파일",
			"26장 CP",
			"27장 클립보드",
			"28장 네트워크",
			"29장 BR",
			"30장 서비스",
			"31장 제스처",
			"32장 맵 서비스",
			"33장 멀티미디어",
			"34장 센서",
			"35장 시스템 설정",
			"36장 전화",
			"37장 앱위젯",
		};
	
	ArrayAdapter<CharSequence> mAdapter;
	ListView mExamList;
	Spinner mSpinner;
	boolean mInitSelection = true;
	int mFontSize;
	int mBackType;
	boolean mDescSide;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.andexam);

		mExamList = (ListView)findViewById(R.id.examlist);
		mSpinner = (Spinner)findViewById(R.id.spinnerchapter);
		mSpinner.setPrompt("장을 선택하세요.");

		mAdapter = new ArrayAdapter<CharSequence>(this, 
				android.R.layout.simple_spinner_item, arChapter);
		mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinner.setAdapter(mAdapter);

		mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// 최초 전개시에도 Selected가 호출되는데 이때는 프레퍼런스에서 최후 장을 찾아 로드한다.
				// 이후부터는 사용자가 선택한 장을 로드한다.
				if (mInitSelection) {
					mInitSelection = false;
					SharedPreferences pref = getSharedPreferences("AndExam", 0);
					int lastchapter = pref.getInt("LastChapter", START_CHAPTER);
					mSpinner.setSelection(lastchapter - START_CHAPTER);
					ChangeChapter(lastchapter);
				} else {
					// 장을 변경할 때마다 프레퍼런스에 기록한다.
					int chapter = position + START_CHAPTER;
					ChangeChapter(chapter);
					SharedPreferences pref = getSharedPreferences("AndExam", 0);
					SharedPreferences.Editor edit = pref.edit();
					edit.putInt("LastChapter", chapter);
					edit.commit();
				}
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		ReadOption();

		// 자동실행 옵션의 디폴트는 false로 설정한다. 한 예제만 반복적으로 테스트할 때 이 옵션을
		// 사용하되 예외 처리가 어려우므로 왠만하면 사용하지 않는 것이 좋다.
		boolean bRunLast = false;
		if (bRunLast) {
			SharedPreferences pref = getSharedPreferences("AndExam", 0);
			int pkg = pref.getInt("LastChapter", START_CHAPTER);
			int pos = pref.getInt("LastPosition", 0);
			ChangeChapter(pkg);
			Intent intent = new Intent(this, arExample.get(pos).cls);
			startActivity(intent);
		}
	}
	
	public void ReadOption() {
		SharedPreferences pref = getSharedPreferences("AndExam", 0);
		mFontSize = pref.getInt("mFontSize", 1);
		mBackType = pref.getInt("mBackType", 0);
		mDescSide = pref.getBoolean("mDescSide", false);
		
		if (mBackType != 0) {
			mExamList.setBackgroundColor(0xff101010);
			mExamList.setDivider(new ColorDrawable(0xff606060));
			mExamList.setDividerHeight(1);
		} else {
			mExamList.setBackgroundColor(0xffe0e0e0);
			mExamList.setDivider(new ColorDrawable(0xff404040));
			mExamList.setDividerHeight(1);
		}
	}
	
	// 장을 변경한다. 인수로 전달되는 chapter는 첨자가 아니라 장 번호이다. 
	public void ChangeChapter(int chapter) {
		FillExample(chapter);
		ExamListAdapter Adapter = new ExamListAdapter(this);
		mExamList.setAdapter(Adapter);

		final Context ctx = this;
		mExamList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				SharedPreferences pref = getSharedPreferences("AndExam", 0);
				SharedPreferences.Editor edit = pref.edit();
				edit.putInt("LastPosition", position);
				edit.commit();
				Intent intent = new Intent(ctx, arExample.get(position).cls);
				startActivity(intent);
			}
		});
	}
	
	public void mOnClick(View v) {
		SharedPreferences pref = getSharedPreferences("AndExam", 0);
		int lastchapter = pref.getInt("LastChapter", START_CHAPTER);
		switch (v.getId()) {
		case R.id.btnprev:
			if (lastchapter !=  START_CHAPTER) {
				lastchapter--;
				mSpinner.setSelection(lastchapter - START_CHAPTER);
			}
			break;
		case R.id.btnnext:
			if (lastchapter != arChapter.length - 1 + START_CHAPTER) {
				lastchapter++;
				mSpinner.setSelection(lastchapter - START_CHAPTER);
			}
			break;
		}
	}
	
	//어댑터 클래스
	class ExamListAdapter extends BaseAdapter {
		Context maincon;
		LayoutInflater Inflater;

		public ExamListAdapter(Context context) {
			maincon = context;
			Inflater = (LayoutInflater)context.getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
		}

		public int getCount() {
			return arExample.size();
		}

		public String getItem(int position) {
			return arExample.get(position).Name;
		}

		public long getItemId(int position) {
			return position;
		}

		// 각 항목의 뷰 생성
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = Inflater.inflate(R.layout.andexamlist, parent, false);
			}
			
			LinearLayout examlayout = (LinearLayout)convertView.findViewById(R.id.examlayout);
			TextView txt1 = (TextView)convertView.findViewById(R.id.text1);
			TextView txt2 = (TextView)convertView.findViewById(R.id.text2);

			if (mDescSide) {
				examlayout.setOrientation(LinearLayout.HORIZONTAL);
			}
			
			if (mBackType != 0) {
				examlayout.setBackgroundResource(R.drawable.exambackdark);
				txt1.setTextColor(Color.WHITE);
				txt2.setTextColor(Color.LTGRAY);
			}

			switch (mFontSize) {
			case 0:
				txt1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
				txt2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
				break;
			case 1:
				txt1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
				txt2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
				break;
			case 2:
				txt1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
				txt2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
				break;
			}
			
			txt1.setText(arExample.get(position).Name);
			txt2.setText(arExample.get(position).Desc);

			return convertView;
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
	
		menu.add(0,1,0,"소개");
		menu.add(0,2,0,"옵션");
		menu.add(0,3,0,"종료");

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			new AlertDialog.Builder(this)
			.setTitle("프로그램 소개")
			.setMessage("안드로이드 프로그래밍 정복(한빛미디어)의 예제 모음 프로그램입니다.\n" +
					"상단의 스피너에서 패키지를 선택하고 목록에서 예제를 선택하십시오.")
			.setIcon(R.drawable.andexam)
			.setPositiveButton("닫기", null)
			.show();
			return true;
		case 2:
			startActivityForResult(new Intent(this, AndExamSetting.class), SETTING_ACTIVITY);
			return true;
		case 3:
			finish();
			System.exit(0);
			return true;
		}
		return false;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case SETTING_ACTIVITY:
			if (resultCode != RESULT_OK) return;
			ReadOption();
			SharedPreferences pref = getSharedPreferences("AndExam", 0);
			int lastchapter = pref.getInt("LastChapter", START_CHAPTER);
			ChangeChapter(lastchapter);
			break;
		}
	}
}

