package com.yash.tongaonkar.multiselectspinnerexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.yash.tongaonkar.multiselectspinner.MultiSelectSpinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    List<Object> loginResponses = new ArrayList<>();

    private Button buttonClicked;
    private MultiSelectSpinner multiSelectSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createData();
        this.buttonClicked = (Button) findViewById(R.id.buttonClicked);
        this.buttonClicked.setOnClickListener(this);
        multiSelectSpinner = (MultiSelectSpinner) findViewById(R.id.optionSpinner);
        multiSelectSpinner.setItems(loginResponses, "username");
        multiSelectSpinner.setSelection(new int[]{0});
    }

    private void createData() {
        this.loginResponses.add(new LoginResponse("1", "test", "test9694"));
        this.loginResponses.add(new LoginResponse("2", "test1", "test0987"));
        this.loginResponses.add(new LoginResponse("3", "test12", "test1234"));
        this.loginResponses.add(new LoginResponse("4", "test123", "test"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonClicked:
                onButtonClicked();
                break;
        }
    }

    private void onButtonClicked() {
        List<Object> objects = multiSelectSpinner.getSelectedObjects();
        for (Object obj : objects) {
            LoginResponse loginResponse = (LoginResponse) obj;
            Log.d("SelectedObject", loginResponse.toString());
        }
    }
}
