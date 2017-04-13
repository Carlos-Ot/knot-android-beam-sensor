package br.org.cesar.knot.beamsensor.ui.login;

import android.support.v4.content.ContextCompat;
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
import android.widget.EditText;
import android.widget.Toast;

import br.org.cesar.knot.beamsensor.R;
import br.org.cesar.knot.beamsensor.data.local.PreferencesManager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CloudSetupActivity extends AppCompatActivity implements View.OnClickListener{


    @BindView(R.id.ip_et)
    EditText edtIp;

    @BindView(R.id.port_et)
    EditText edtPort;

    @BindView(R.id.btn_save)
    Button btnSave;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

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
        toolbar.setTitle(R.string.activity_cloud_setup_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        edtIp.addTextChangedListener(mCloudEmptyWatcher);
        edtPort.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);

        edtIp.addTextChangedListener(mCloudEmptyWatcher);

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

        edtIp.setFilters(filters);

        btnSave.setOnClickListener(this);

        //TODO remover
        edtIp.setText("172.24.4.231");
        edtPort.setText("3000");
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

        if (!edtIp.getText().toString().isEmpty()
                && !edtPort.getText().toString().isEmpty()) {
            enableSaveButton(true);
        } else {
            enableSaveButton(false);
        }
    }

    private void enableSaveButton(boolean enable) {
        btnSave.setEnabled(enable);
        if(enable) {
            btnSave.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
            btnSave.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        } else {
            btnSave.setBackgroundColor(ContextCompat.getColor(this, R.color.material_blue_grey_400));
            btnSave.setTextColor(ContextCompat.getColor(this, R.color.material_blue_grey_500));
        }

    }


    @Override
    public void onClick(View view) {

        PreferencesManager preferencesManager = PreferencesManager.getInstance();

        preferencesManager.setCloudIp(edtIp.getText().toString());
        preferencesManager.setCloudPort(edtPort.getText().toString());

        Toast.makeText(CloudSetupActivity.this, getString(R.string.text_cloud_info_saved), Toast.LENGTH_SHORT).show();
        finish();
    }
}
