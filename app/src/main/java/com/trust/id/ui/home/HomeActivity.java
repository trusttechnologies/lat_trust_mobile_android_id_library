package com.trust.id.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.trust.id.R;
import com.trust.id2.model.Profile;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity implements HomeContract.View {
    @BindView(R.id.txtGender)
    TextView txtGender;
    @BindView(R.id.txtLastName)
    TextView txtLastName;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtNationaloty)
    TextView txtNationality;
    @BindView(R.id.txtSurName)
    TextView txtSurname;
    @BindView(R.id.btnLogout)
    Button btnLogout;
    private HomeContract.Presenter mPresenter;
    private MaterialDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mPresenter = new HomePresenter(this);
        mPresenter.onViewCreated();
    }

    @OnClick(R.id.btnLogout)
    void onLogoutPressed() {
        mPresenter.logoutButtonWasPressed();
    }

    @Override
    public void displayProfileData(Profile profile) {
        if (profile != null) {
            txtGender.setText("Genero: " + profile.getGender());
            txtName.setText("Nombre completo: " + profile.getGivenName());
            txtLastName.setText("Apellido Paterno: " + profile.getFamilyName());
            txtNationality.setText("Nacionalidad: " + profile.getAddress().getCountry());
            txtSurname.setText("Apellido Materno: " + profile.getMiddleName());
        }
    }

    @Override
    public void showLoading(String message) {
        if (loadingDialog == null) {
            loadingDialog = createLoadingDialog(this, message);
        }
        loadingDialog.show();
    }

    @Override
    public void dismissLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) loadingDialog.dismiss();
    }

    public static MaterialDialog createLoadingDialog(Context ctx, String message) {
        return new MaterialDialog.Builder(ctx)
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .title(message)
                .widgetColor(ContextCompat.getColor(ctx, R.color.colorAccent))
                .typeface(ResourcesCompat.getFont(ctx, R.font.baijamjuree_regular), ResourcesCompat.getFont(ctx, R.font.baijamjuree_regular))
                .titleColor(ContextCompat.getColor(ctx, R.color.black))
                .progress(true, 100)
                .progressIndeterminateStyle(true)
                .build();
    }

}
