package br.org.cesar.knot.beamsensor.ui.login;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.org.cesar.knot.beamsensor.R;
import br.org.cesar.knot.beamsensor.data.local.PreferencesManager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CloudSetupActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.setupRoot)
    View mRootView;
    @BindView(R.id.loginTextInputAddress)
    TextInputEditText mIpEditText;
    @BindView(R.id.loginTextInputPort)
    TextInputEditText mPortEditText;
    @BindView(R.id.saveButton)
    Button mSaveButton;


    private TextWatcher mCloudEmptyWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            checkFieldsForEmptyValues();
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    private InputFilter[] filters = new InputFilter[1];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud_setup);
        ButterKnife.bind(this);

        //toolbar
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.title_setup);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mPortEditText.addTextChangedListener(mCloudEmptyWatcher);
        mPortEditText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);

        mIpEditText.addTextChangedListener(mCloudEmptyWatcher);

        filters[0] = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (end > start) {
                    String destTxt = dest.toString();
                    String resultingTxt = destTxt.substring(0, dstart) + source.subSequence(start, end) + destTxt.substring(dend);
                    if (!resultingTxt.matches ("^\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3})?)?)?)?)?)?")) {
                        return "";
                    } else {
                        String[] splits = resultingTxt.split("\\.");
                        for (String split : splits) {
                            if (Integer.valueOf(split) > 255) {
                                return "";
                            }
                        }
                    }
                }
                return null;
            }
        };

        mIpEditText.setFilters(filters);

        mSaveButton.setOnClickListener(this);

        //TODO remover
        mIpEditText.setText("10.211.55.19");
        mPortEditText.setText("3000");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void checkFieldsForEmptyValues() {

        if (!mIpEditText.getText().toString().isEmpty()
                && !mPortEditText.getText().toString().isEmpty()) {
            enableSaveButton(true);
        } else {
            enableSaveButton(false);
        }
    }

    private void enableSaveButton(boolean enable) {
        mSaveButton.setEnabled(enable);
    }


    @Override
    public void onClick(View view) {

        PreferencesManager preferencesManager = PreferencesManager.getInstance();

        preferencesManager.setCloudIp(mIpEditText.getText().toString());
        preferencesManager.setCloudPort(mPortEditText.getText().toString());

        Toast.makeText(this, getString(R.string.text_cloud_info_saved), Toast.LENGTH_SHORT).show();

        finish();
    }
}
