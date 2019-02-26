package com.trust.id.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trust.id.R;

public class TextInputLayoutLight extends LinearLayout implements View.OnFocusChangeListener, TextWatcher {
    private Context mContext;
    private TextInputEditText mInput;
    private TextView mHelp;
    private View mUnderActive;
    private View mUnderNormal;
    private View mUnderError;
    private boolean hasFocus = false;

    private String mHint = "";
    private String mErrorMessage;
    private String mHelpMessage;
    private boolean mIsPassword = false;
    private boolean mIsPhone = false;
    private boolean mLightMode = false;
    private android.support.design.widget.TextInputLayout mInputLayout;
    private TextWatcher mOnTextChange;
    private boolean mIsDate = false;
    private boolean mIsRut = false;
    private boolean mIsEmail;

    public TextInputLayoutLight(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public TextInputLayoutLight(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        getAttributes(attrs);
        init();
    }

    public TextInputLayoutLight(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        getAttributes(attrs);
        init();
    }

    public void setOnTextChange(TextWatcher onTextChange) {
        this.mOnTextChange = onTextChange;
    }

    /**
     * Get all attributed from xml
     *
     * @param attrs set of attributes
     */
    private void getAttributes(AttributeSet attrs) {
        TypedArray mAttributes = mContext.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.TextInputLayout,
                0, 0);

        try {
           /* mHint = mAttributes.getString(R.styleable.TextInputLayout_hint);
            mHelpMessage = mAttributes.getString(R.styleable.TextInputLayout_helpMessage);
            mErrorMessage = mAttributes.getString(R.styleable.TextInputLayout_errorMessage);
            mIsPassword = mAttributes.getBoolean(R.styleable.TextInputLayout_isPassword, false);
            mLightMode = mAttributes.getBoolean(R.styleable.TextInputLayout_lightMode, false);
            mIsPhone = mAttributes.getBoolean(R.styleable.TextInputLayout_isPhone, false);
            mIsRut = mAttributes.getBoolean(R.styleable.TextInputLayout_isRut, false);
            mIsDate = mAttributes.getBoolean(R.styleable.TextInputLayout_isDate, false);
            mIsEmail = mAttributes.getBoolean(R.styleable.TextInputLayout_isEmail, false);*/
        } finally {
            mAttributes.recycle();
        }
    }

    private void init() {
       /* inflate(mContext, R.layout.input_register, this);
        mInput = findViewById(R.id.input);
        mInputLayout = findViewById(R.id.input_layout);
        mUnderActive = findViewById(R.id.underline_active);
        mUnderNormal = findViewById(R.id.underline_normal);
        mUnderError = findViewById(R.id.underline_error);
        mHelp = findViewById(R.id.help_text);


        mInput.setOnFocusChangeListener(this);
        mInput.addTextChangedListener(this);
        updateUI();*/
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v instanceof TextInputEditText) {
            this.hasFocus = hasFocus;
            updateUnderline();
        }
    }

    private void updateUnderline() {
        mUnderActive.setVisibility((hasFocus && mErrorMessage == null) ? VISIBLE : GONE);
        mUnderNormal.setVisibility((!hasFocus && mErrorMessage == null) ? VISIBLE : GONE);
        mUnderError.setVisibility((mErrorMessage == null) ? GONE : VISIBLE);
    }

    private void setupInputType() {
        mInput.setInputType(InputType.TYPE_CLASS_TEXT);
        if (mIsPassword) {
            mInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        if (mIsPhone) {
            mInput.setInputType(InputType.TYPE_CLASS_PHONE);
            InputFilter[] fArray = new InputFilter[1];
            fArray[0] = new InputFilter.LengthFilter(9);
            mInput.setFilters(fArray);
        }
        if (mIsRut) {
            mInput.setInputType(InputType.TYPE_CLASS_TEXT);
            InputFilter[] fArray = new InputFilter[1];
            fArray[0] = new InputFilter.LengthFilter(10);
            mInput.setFilters(fArray);
        }
        if (mIsDate) {
            mInput.setInputType(InputType.TYPE_CLASS_DATETIME);
            InputFilter[] fArray = new InputFilter[1];
            fArray[0] = new InputFilter.LengthFilter(10);
            mInput.setFilters(fArray);
        }

        if (mIsEmail) {
            mInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        }
        mInput.setTypeface(ResourcesCompat.getFont(mContext, R.font.barlow_regular));
    }

    private void updateUI() {
        updateUnderline();
        setupInputType();
        setupError();

        //mInput.setHint(mHint);
        mInputLayout.setHint(mHint);

    }

    private void setupError() {
        mHelp.setVisibility(VISIBLE);
        if (mErrorMessage != null) {
            mHelp.setText(mErrorMessage);
            mHelp.setTextColor(mContext.getResources().getColor(R.color.red));
        } else if (mHelpMessage != null) {
            mHelp.setText(mHelpMessage);
            mHelp.setTextColor(mContext.getResources().getColor(R.color.black_179));
        } else {
            mHelp.setVisibility(GONE);
            mHelp.setText("");
        }
    }

    protected void setHint(String hint) {
        this.mHint = hint;
        updateUI();
    }

    /**
     * Set error message
     *
     * @param errorMessage if null hide error message
     */
    protected void setError(String errorMessage) {
        this.mErrorMessage = errorMessage;
        setupError();
    }

    /**
     * Set help message
     *
     * @param helpMessage if null hide help message
     */
    public void setHelp(String helpMessage) {
        this.mHelpMessage = helpMessage;
        setupError();
    }

    public boolean isEmpty() {
        return getText().isEmpty();
    }

    public String getText() {
        return mInput.getText().toString();
    }

    public boolean isPassword() {
        return mIsPassword;
    }

    public void setIsPassword(boolean mIsPassword) {
        this.mIsPassword = mIsPassword;
    }

    public void setIsDate(boolean isDate) {
        this.mIsDate = isDate;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mOnTextChange != null) mOnTextChange.onTextChanged(s, start, before, count);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void setText(String data) {
        mInput.setText(data);
    }


    public void addTextChangeListener(TextWatcher textWatcher) {
        mInput.addTextChangedListener(textWatcher);
    }

    public void removeTextWatcher(TextWatcher textWatcher) {
        mInput.removeTextChangedListener(textWatcher);
    }
}
