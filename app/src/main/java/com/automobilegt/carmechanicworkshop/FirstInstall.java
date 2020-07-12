package com.automobilegt.carmechanicworkshop;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class FirstInstall extends AppCompatActivity {

    private CheckBox checkBox;

    public void getStarted(View view) {
        if (checkBox.isChecked()) {
            Intent intent = new Intent(FirstInstall.this, MainActivity.class);
            intent.putExtra("installed", true);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Check the box to get Started", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_install);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        checkBox = findViewById(R.id.agree_checkBox);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            checkBox.setText(Html.fromHtml(getString(R.string.sample_aula_and_privacy_policy), HtmlCompat.FROM_HTML_MODE_LEGACY));
            checkBox.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            checkBox.setText(Html.fromHtml(getString(R.string.sample_aula_and_privacy_policy)));
            checkBox.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }
}