package com.appstudio.android.sample.ch_33_3;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.appstudio.android.sample.R;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;

public class FacebookTestActivity extends Activity {

	// 요청할 퍼미션을 기술한다.
    private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");

    // 어떤 액션이 실행되고 있는지 정보를 찾기 위한 키이다. 
    private final String PENDING_ACTION_BUNDLE_KEY = "com.appstudio.android.sample:PendingAction";

    // 로그인 한 페이스 북 사용자의 프로파일 사진을 담기 위한 뷰이다. 
    private ProfilePictureView profilePictureView;
    
    // 로그인 한 페이스 북 사용자의 상태에 메시지를 추가하기 위한 버튼이다.
    // 안드로이드의 일반 버튼이다.
    private Button postStatusUpdateButton;

    // 로그인을 위해 제공하는 페이스북 SDK제공 버튼이다. 
    private LoginButton loginButton;
    
    // 로그인 시 인사말을 출력한다. 
    private TextView greeting;
    
    // 초기에는 아무 액션도 수행하지 않고 있다고 초기화 한다. 
    private PendingAction pendingAction = PendingAction.NONE;
    private ViewGroup controlsContainer;
    
    // 페이스 북의 Graph API를 위한 사용자 정의이다. 
    private GraphUser user;

    // 어떤 액션을 수행하고 있는지를 나타내기 위해 액션의 종류를 기술했다. 
    private enum PendingAction {
        NONE,
        POST_STATUS_UPDATE
    }
    
    // 페이스 북 SDK제공 도움 클래스 
    private UiLifecycleHelper uiHelper;

    // 페이스 북 사용자 세션이 변경되었을 때 호출되는 콜백 메소드이다. 
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            String name = savedInstanceState.getString(PENDING_ACTION_BUNDLE_KEY);
            pendingAction = PendingAction.valueOf(name);
        }

        setContentView(R.layout.facebook_test_activity);

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
            	FacebookTestActivity.this.user = user;
                updateUI();
                handlePendingAction();
            }
        });

        profilePictureView = (ProfilePictureView) findViewById(R.id.profilePicture);
        greeting = (TextView) findViewById(R.id.greeting);

        postStatusUpdateButton = (Button) findViewById(R.id.postStatusUpdateButton);
        postStatusUpdateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onClickPostStatusUpdate();
            }
        });

        controlsContainer = (ViewGroup) findViewById(R.id.main_ui_container);
    }

    @Override
    protected void onResume() {
        super.onResume();
        uiHelper.onResume();

        updateUI();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);

        outState.putString(PENDING_ACTION_BUNDLE_KEY, pendingAction.name());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    // 페이스 북 사용자 세션이 변경되었을 때 호출되는 콜백 메소드에서 호출되는 메소드이다.  
    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
    	// 액션이 수행중이고 예외가 발생되었다면 경고창을 띄운다. 
        if (pendingAction != PendingAction.NONE &&
                (exception instanceof FacebookOperationCanceledException ||
                exception instanceof FacebookAuthorizationException)) {
                new AlertDialog.Builder(FacebookTestActivity.this)
                    .setTitle("Canceled")
                    .setMessage("Not Permission")
                    .setPositiveButton("OK", null)
                    .show();
            pendingAction = PendingAction.NONE;
        } else if (state == SessionState.OPENED_TOKEN_UPDATED) {
            handlePendingAction();
        }
        updateUI();
    }

    private void updateUI() {
        Session session = Session.getActiveSession();
        boolean enableButtons = (session != null && session.isOpened());

        postStatusUpdateButton.setEnabled(enableButtons);

        if (enableButtons && user != null) {
            profilePictureView.setProfileId(user.getId());
            greeting.setText("Hi "+user.getFirstName());
        } else {
            profilePictureView.setProfileId(null);
            greeting.setText(null);
        }
    }

    @SuppressWarnings("incomplete-switch")
    private void handlePendingAction() {
        PendingAction previouslyPendingAction = pendingAction;

        pendingAction = PendingAction.NONE;

        switch (previouslyPendingAction) {
            case POST_STATUS_UPDATE:
                postStatusUpdate();
                break;
        }
    }

    private interface GraphObjectWithId extends GraphObject {
        String getId();
    }

     
    private void showPublishResult(String message, GraphObject result, FacebookRequestError error) {
        String title = null;
        String alertMessage = null;
        if (error == null) {
            title = "Success";
            String id = result.cast(GraphObjectWithId.class).getId();
            alertMessage = "Message Pot Success "+id;
        } else {
            title = "Error";
            alertMessage = error.getErrorMessage();
        }

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(alertMessage)
                .setPositiveButton("OK", null)
                .show();
    }

    private void onClickPostStatusUpdate() {
        performPublish(PendingAction.POST_STATUS_UPDATE);
    }

    
    private void postStatusUpdate() {
        if (user != null && hasPublishPermission()) {
            final String message = "메시지를 전송합니다.";
            Request request = Request
                    .newStatusUpdateRequest(Session.getActiveSession(), message, new Request.Callback() {
                        @Override
                        public void onCompleted(Response response) {
                            showPublishResult(message, response.getGraphObject(), response.getError());
                        }
                    });
            request.executeAsync();
        } else {
            pendingAction = PendingAction.POST_STATUS_UPDATE;
        }
    }


    private boolean hasPublishPermission() {
        Session session = Session.getActiveSession();
        return session != null && session.getPermissions().contains("publish_actions");
    }

    // 로그인한 상태에서 사용자의 상태 게시판에 메시지를 전송하기 위한 메인 메소드이다. 
    private void performPublish(PendingAction action) {
        Session session = Session.getActiveSession();
        if (session != null) {
            pendingAction = action;
            // 만약 퍼미션을 가진다면 나머지 처리를 수행한다. 
            // 로그인 한 상태에서도 퍼미션은 없을 수 있다. 
            if (hasPublishPermission()) {
                handlePendingAction();
            // 퍼미션을 가지지 못한다면 퍼미션을 요청한다.     
            } else {
                session.requestNewPublishPermissions(new Session.NewPermissionsRequest(this, PERMISSIONS));
            }
        }
    }
}
