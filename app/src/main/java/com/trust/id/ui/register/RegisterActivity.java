package com.trust.id.ui.register;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.trust.id.R;
import com.trust.id.utils.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View,DatePickerDialog.OnDateSetListener {

    @BindView(R.id.etNombres)
    EditText etNombres;

    @BindView(R.id.etApellidoM)
    EditText etApellidoM;

    @BindView(R.id.etAPellidoP)
    EditText etApellidoP;

    @BindView(R.id.etNacionalidad)
    EditText etNacionalidad;

    @BindView(R.id.etFechaNacimiento)
    EditText etFechaNacimiento;

    @BindView(R.id.radio_group_gender)
    RadioGroup radioGroup;

    @BindView(R.id.btnRegistrar)
    Button btnRegistrar;

    private String selectedDate = "";
    private RegisterContract.Presenter mPresenter;
    private Map<String, String> nat = Util.getNationalities();
    private String selectedNat = null;
    private MaterialDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mPresenter = new RegisterPresenter(this);
        mPresenter.onViewCreated();

    }

    @OnClick(R.id.etFechaNacimiento)
    void onBirthdayPressed() {
        mPresenter.birthdayButtonWasPressed();
    }

    @OnClick(R.id.btnRegistrar)
    void onNextButtonPressed() {
        mPresenter.onNextButtonPressed(etNombres.getText().toString(),
                etApellidoP.getText().toString(),
                etApellidoM.getText().toString(),
                selectedDate,
                radioGroup.getCheckedRadioButtonId(),
                selectedNat);
    }
    @Override
    public void displayProfileData(String name, String family, String middle, String birthday, int gender, String birthdayServer, String nationality) {
        etApellidoM.setText(middle);
        etNombres.setText(name);
        etApellidoP.setText(family);
        etFechaNacimiento.setText(birthdayServer);
        radioGroup.check(gender);
        selectedNat =  nationality;

        this.selectedDate = birthdayServer;
        if (nationality != null && !nationality.isEmpty()) {
            List<String> values = new ArrayList<>(nat.values());
            int index = values.indexOf(selectedNat.toLowerCase());
            List<String> keys = new ArrayList<>(nat.keySet());
            etNacionalidad.setText(keys.get(index));
        }

    }
    @OnClick(R.id.etNacionalidad)
    void onNacionalityPressed() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title("Seleccione su nacionalidad")
                .typeface(ResourcesCompat.getFont(this, R.font.barlow_medium), ResourcesCompat.getFont(this, R.font.barlow_regular))
                .negativeText("Cancelar")
                .items(nat.keySet());
        int index = -1;
        if (selectedNat != null) {
            List<String> values = new ArrayList<>(nat.values());
            index = values.indexOf(selectedNat);
        }
        builder.itemsCallbackSingleChoice(index, (dialog, itemView, which, text) -> {
            etNacionalidad.setText(text);
            selectedNat = nat.get(text);
            return true;
        });
        builder.show();
    }
    @Override
    public void showDatePickerDialog(int year, int month, int day) {
        DatePickerDialog dialog = new DatePickerDialog(this, this, year, month, day);
        dialog.show();
    }

    @Override
    public void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = createLoadingDialog(this, "Cargando...");
        }
        loadingDialog.show();
    }

    @Override
    public void dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) loadingDialog.dismiss();

    }

    @Override
    public void showNamesError(boolean isError) {
        etNombres.setError(!isError ? null :"Ingrese su nombre.");
    }

    @Override
    public void showFamilyNameError(boolean isError) {
        etApellidoP.setError(!isError ? null :"ingrese su apellido paterno.");
    }

    @Override
    public void showMiddleNameError(boolean isError) {
        etApellidoM.setError(!isError ? null :"ingrese su apellido materno.");
    }

    @Override
    public void showBirthDayError(boolean isError) {
        etFechaNacimiento.setError(!isError ? null :"ingrese su fecha de nacimiento.");
    }

    @Override
    public void showNatError(boolean isError) {
        etNacionalidad.setError(!isError ? null :"ingrese su nacionalidad.");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        Calendar newDate = Calendar.getInstance();
        newDate.set(year, monthOfYear, dayOfMonth);
        selectedDate = Util.formatBirthDateServer(newDate.getTimeInMillis());
        etFechaNacimiento.setText(Util.formatBirthDate(newDate.getTimeInMillis()));
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
