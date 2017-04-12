package br.org.cesar.knot.beamsensor.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.org.cesar.knot.beamsensor.R;
import br.org.cesar.knot.beamsensor.controller.BeamController;
import br.org.cesar.knot.beamsensor.data.local.PreferencesManager;
import br.org.cesar.knot.beamsensor.data.networking.callback.AuthenticateRequestCallback;
import br.org.cesar.knot.beamsensor.ui.list.DeviceListActivity;
import br.org.cesar.knot.beamsensor.util.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements AuthenticateRequestCallback {
    private static final String TAG = LoginActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.loginRoot)
    View mRootView;
    @BindView(R.id.loginTextInputUsername)
    TextInputEditText mUsernameEditText;
    @BindView(R.id.loginTextInputPassword)
    TextInputEditText mPasswordEditText;
    @BindView(R.id.loginButton)
    Button mLoginButton;

    private PreferencesManager mPreferencesManager;

    private BeamController mBeamController;

    private TextWatcher mCredentialsEmptyWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            checkCredentialsFieldForEmptyValues();
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.title_login);

        mPreferencesManager = PreferencesManager.getInstance();

        mUsernameEditText.addTextChangedListener(mCredentialsEmptyWatcher);
        mPasswordEditText.addTextChangedListener(mCredentialsEmptyWatcher);

        this.mBeamController = BeamController.getInstance();

        if (mPreferencesManager.getUsername() != null &&
                !mPreferencesManager.getUsername().isEmpty()) {
            mUsernameEditText.setText(mPreferencesManager.getUsername());
        }

        //TODO remover
        mUsernameEditText.setText("user");
        mPasswordEditText.setText("1234");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        showCloudSetupScreen();
        return true;
    }

    private void enableLoginButton(boolean enable) {
        mLoginButton.setEnabled(enable);
    }


    private void checkCredentialsFieldForEmptyValues() {
        if (!mUsernameEditText.getText().toString().isEmpty()
                && !mPasswordEditText.getText().toString().isEmpty()) {
            enableLoginButton(true);
        } else {
            enableLoginButton(false);
        }
    }

    private boolean validateCloudInfo() {

        boolean cloudIpOk = mPreferencesManager.getCloudIp() != null && !mPreferencesManager.getCloudIp().trim().isEmpty();

        boolean cloudPortOk = mPreferencesManager.getCloudPort() != Constants.INVALID_CLOUD_PORT;

        return cloudIpOk && cloudPortOk;
    }

    @OnClick(R.id.loginButton)
    void performLogin() {

        if (validateCloudInfo()) {

            String mUsername = mUsernameEditText.getText().toString();
            String mPassword = mPasswordEditText.getText().toString();

            this.mBeamController.authenticate(mPreferencesManager.getCloudIp(),
                    mPreferencesManager.getCloudPort(), mUsername, mPassword, this);

        } else {

            Snackbar snackbar = Snackbar.make(mRootView, "Setup cloud info", Snackbar.LENGTH_LONG);
            snackbar.setAction("Settings", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showCloudSetupScreen();
                }
            });
            snackbar.show();
        }

    }

    @Override
    public void onAuthenticateSuccess() {

        mPreferencesManager.setUsername(mUsernameEditText.getText().toString());

        startActivity(new Intent(this, DeviceListActivity.class));
    }

    @Override
    public void onAuthenticateFailed() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, R.string.text_authentication_failed, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showCloudSetupScreen() {

        startActivity(new Intent(this, CloudSetupActivity.class));
    }
}
