package br.org.cesar.knot.beamsensor.ui.login;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import br.org.cesar.knot.beamsensor.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CloudSetupActivity extends AppCompatActivity {

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud_setup);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mIpEditText.addTextChangedListener(mCloudEmptyWatcher);

        mPortEditText.addTextChangedListener(mCloudEmptyWatcher);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO save info
            }
        });
    }




    private void checkFieldsForEmptyValues() {
    }
}
