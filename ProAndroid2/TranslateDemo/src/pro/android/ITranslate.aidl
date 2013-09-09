package pro.android;
// 이 파일은 /src 디렉터리 안의 ITranslate.aidl이다.
interface ITranslate {
	String translate(in String text, in String from,  in String to);
}
