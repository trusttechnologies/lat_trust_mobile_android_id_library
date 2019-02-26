package com.trust.id.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trust.id.R;

public class TextInputLayout extends LinearLayout implements View.OnFocusChangeListener {
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
    private boolean mIsPassword;
    private boolean mLightMode;
    private android.support.design.widget.TextInputLayout mInputLayout;
    private boolean mIsRut = false;

    public TextInputLayout(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public TextInputLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        getAttributes(attrs);
        init();
    }

    public TextInputLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        getAttributes(attrs);
        init();
    }

    public void addTextChangeListener(TextWatcher textWatcher) {
        mInput.addTextChangedListener(textWatcher);
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
            mIsRut = mAttributes.getBoolean(R.styleable.TextInputLayout_isRut, false);*/
        } finally {
            mAttributes.recycle();
        }
    }

    private void init() {
    /*    inflate(mContext, R.layout.input_login, this);
        mInput = findViewById(R.id.input);
        mInputLayout = findViewById(R.id.input_layout);
        mUnderActive = findViewById(R.id.underline_active);
        mUnderNormal = findViewById(R.id.underline_normal);
        mUnderError = findViewById(R.id.underline_error);
        mHelp = findViewById(R.id.help_text);
*/

        mInput.setOnFocusChangeListener(this);
        updateUI();
    }

    private void setupLightMode() {
        mInput.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        mInput.setHintTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
      //  mUnderActive.setBackground(ContextCompat.getDrawable(mContext, R.drawable.register_input_gradient));
        mUnderNormal.setBackgroundColor(ContextCompat.getColor(mContext, R.color.black));
     //   mInputLayout.setHintTextAppearance(R.style.TextAppearance_App_TextInputLayout);
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
        if (mIsRut) {
            mInput.setInputType(InputType.TYPE_CLASS_TEXT);
            InputFilter[] fArray = new InputFilter[1];
            fArray[0] = new InputFilter.LengthFilter(10);
            mInput.setFilters(fArray);
        }
        mInput.setTypeface(ResourcesCompat.getFont(mContext, R.font.barlow_regular));
    }

    private void updateUI() {
        updateUnderline();
        setupInputType();
        setupError();

        //mInput.setHint(mHint);
        mInputLayout.setHint(mHint);

        if (mLightMode) {
            setupLightMode();
        }
    }

    private void setupError() {
        if (mErrorMessage != null) {
            mHelp.setText(mErrorMessage);
            mHelp.setTextColor(mContext.getResources().getColor(R.color.red));
        } else if (mHelpMessage != null) {
            mHelp.setText(mHelpMessage);
            mHelp.setTextColor(mContext.getResources().getColor(R.color.white_179));
        } else {
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
    protected void setHelp(String helpMessage) {
        this.mHelpMessage = helpMessage;
        setupError();
    }

    public String getText() {
        return mInput.getText().toString();
    }

    public void setText(String text) {
        mInput.setText(text);
    }

    public void removeTextWatcher(TextWatcher textWatcher) {
        mInput.removeTextChangedListener(textWatcher);
    }

}
