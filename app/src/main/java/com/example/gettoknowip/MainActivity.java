package com.example.gettoknowip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gettoknowip.ui.IPInfoActivity;

import org.w3c.dom.Text;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ImageView ipImage;
    private TextView userMessageTextView;
    private EditText userIpAddressEditText;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ipImage = findViewById(R.id.imageView_ip_icon);
        userMessageTextView = findViewById(R.id.textViewMessage);
        userIpAddressEditText = findViewById(R.id.editTextIp);
        searchButton = findViewById(R.id.buttonSearch);

        setUpUi();
    }

    private void setUpUi() {
        userIpAddressEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String[] res = (s.toString()).split("\\.");
                Log.d("DebugTime", Arrays.toString(res));
                if (res.length == 4) {
                    searchButton.setEnabled(true);
                    searchButton.setBackgroundColor(getResources().getColor(R.color.textColor));
                    Log.d("DebugTime", Integer.toString(res.length));
                } else {
                    searchButton.setEnabled(false);
                    searchButton.setBackgroundColor(getResources().getColor(R.color.textColor2));
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        searchButton.setOnClickListener(v -> {
            if (checkInputValidity(userIpAddressEditText.getText().toString())) {
                //if input is valid then -> result activity.
                Intent startIPInfoActivityIntent = new Intent(MainActivity.this, IPInfoActivity.class);
                startIPInfoActivityIntent.putExtra(Constants.IP_ADDRESS_ENTERED, userIpAddressEditText.getText().toString());
                startActivity(startIPInfoActivityIntent);
            } else {
                //input is invalid.
                Toast.makeText(MainActivity.this, Constants.ENTER_VALID_IP, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Boolean checkInputValidity(String input) {
        //eg: 196.255.23.45 is an IPv4 address.
        //separate the ip by its full stops to check each element individually.
        String[] res = input.split("\\.");
        //tells if the elements are valid. Even if one is invalid this will have value false.
        boolean valid = true; //assume that elements are valid initially.

        for (String item : res) {
            try {
                //throws exception if there are non numeric chars in the string.
                double number = Double.parseDouble(item);

                //each item can have values between 0 to 255 (both inclusive).
                if (number > 255) {
                    valid = false;
                    break;
                }
            } catch (NumberFormatException error) {
                valid = false;
                break;
            }
        }

        return valid;
    }
}