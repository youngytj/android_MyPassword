package young.home.com.mypassword.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;

import young.home.com.mypassword.R;
import young.home.com.mypassword.application.BaseActivity;
import young.home.com.mypassword.application.MD5;
import young.home.com.mypassword.model.SettingKey;

public class StartActivity extends BaseActivity {

    EditText inputPwd;
    String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

       pwd =  super.getSetting(SettingKey.LOCK_PWD, "");
        if(pwd.isEmpty()){
            Intent intent = new Intent(this, SetPasswordActivity.class);
            startActivity(intent);
            finish();
        }

        inputPwd = (EditText)findViewById(R.id.InputPwd);
    }

    public void EnterClick(View v) {
        if(pwd.equals(MD5.getMD5(inputPwd.getText().toString()))) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(this, "密码不正确", Toast.LENGTH_SHORT).show();
        }
    }
}