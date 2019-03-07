package com.trust.id.ui.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trust.id.R;
import com.trust.id.ui.result.ResultActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etUsername)
    EditText etUsername;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnLoginId)
    Button btnLoginId;
    @BindView(R.id.btnRegisterId)
    Button btnRegisterId;
    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter = new LoginPresenter(this, new ResultActivity());
        mPresenter.onCreate();

    }

    @OnClick(R.id.btnLoginId)
    void onLoginIdPressed() {
        mPresenter.authorizationWasRequested(LoginActivity.this);
    }

    @OnClick(R.id.btnRegisterId)
    void onRegisterPressed() { mPresenter.authorizationWasRequested(LoginActivity.this); }

    @Override
    public void showWelcome() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void showMessage(String title, String message, boolean isError) { Toast.makeText(this, message, Toast.LENGTH_SHORT).show(); }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }


}
