package com.trust.id.ui.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.trust.id.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity  implements HomeContract.View{
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

    private HomeContract.Presenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mPresenter = new HomePresenter(this);
        mPresenter.onViewCreated();

    }

    @Override
    public void displayProfileData(String name, String family, String middle, String birthday, String gender, String birthdayServer, String nationality) {
        txtGender.setText(gender);
        txtLastName.setText(middle);
        txtName.setText(name);
        txtNationality.setText(nationality);
        txtSurname.setText(family);
    }

    @Override
    public void showDatePickerDialog(int year, int month, int day) {

    }

    @Override
    public void showLoadingDialog() {

    }

    @Override
    public void dismissLoadingDialog() {

    }

    @Override
    public void showNamesError(boolean isError) {

    }

    @Override
    public void showFamilyNameError(boolean isError) {

    }

    @Override
    public void showMiddleNameError(boolean isError) {

    }

    @Override
    public void showBirthDayError(boolean isError) {

    }

    @Override
    public void showNatError(boolean isError) {

    }
}
