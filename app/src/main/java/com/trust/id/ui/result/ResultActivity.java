package com.trust.id.ui.result;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.trust.id.R;

public class ResultActivity extends AppCompatActivity implements ResultContract.View  {
    private ResultContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mPresenter = new ResultPresenter(this);
        mPresenter.onViewCreated(getIntent());
    }
    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        mPresenter = null;
        super.onDestroy();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("ResultActivity",data.getDataString());
        if (requestCode == ResultRouter.REGISTER_REQUEST) {
            switch (resultCode) {
                case RESULT_OK:
                    mPresenter.profileWasUpdated();
                    break;
                case ResultRouter.RESULT_LOGOUT:
                    mPresenter.logoutWasRequested();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void showUser(String givenName, boolean isMale) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void onRequestFirebaseToken() {
       /* FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(ResultActivity.this, instanceIdResult -> {
            String newToken = instanceIdResult.getToken();
            mPresenter.onFirebaseTokenObtained(newToken);
        });*/
    }

    @Override
    public void showLogoutNeeded() {

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

    }
}
