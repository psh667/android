package org.androidtown.gcm.push;

public class BasicInfo {
    /**
     * Project Id registered to use GCM.
     * 단말 등록을 위한 필요한 ID
     */
    public static final String PROJECT_ID = "1004588246065";

    /**
     * Google API Key generated for service access
     * 서버 : 푸시 메시지 전송을 위해 필요한 KEY
     */
    public static final String GOOGLE_API_KEY = "AIzaSyCxgSpw5aHnyVE8_MhnxWji5qK8Cjl6zzQ";

    
    
    /**
     * Registration ID for this device
     * 단말 등록 후 수신한 등록 ID
     */
    public static String RegistrationId = "";
    
    /**
     * 서비스에서 액티비티로 토스트 메시지 전송하기 위한 액션 정의
     */
    public static String TOAST_MESSAGE_ACTION = "org.androidtown.gcm.push.TOAST_MESSAGE";
    
}
