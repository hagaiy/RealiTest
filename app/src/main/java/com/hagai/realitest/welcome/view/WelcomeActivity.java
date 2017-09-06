package com.hagai.realitest.welcome.view;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hagai.realitest.R;
import com.hagai.realitest.rss.view.RssActivity;
import com.hagai.realitest.welcome.presenter.WelcomeContract;
import com.hagai.realitest.welcome.presenter.WelcomePresenter;

import java.util.ArrayList;

import static android.Manifest.permission.INTERNET;

public class WelcomeActivity extends AppCompatActivity implements WelcomeContract.ViewActions {

    private final static int ALL_PERMISSIONS_RESULT = 101;
    private ArrayList<String> mPermissionsToRequest;
    private ArrayList<String> mPermissionsRejected = new ArrayList<>();
    private ArrayList<String> mPermissions = new ArrayList<>();

    private WelcomePresenter mPresenter;
    private Button mContinueButton;
    private TextView mLastRssTextView;
    private TextView mDateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mContinueButton = (Button) findViewById(R.id.continueButton);
        mLastRssTextView = (TextView) findViewById(R.id.lastRssTextView);
        mDateTextView = (TextView) findViewById(R.id.dateTextView);
        mPresenter = new WelcomePresenter(this);
        //
        mContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.buttonPressed();
            }
        });
        requestPermissions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    private void requestPermissions() {
        mPermissions.add(INTERNET);
        mPermissionsToRequest = findUnAskedPermissions(mPermissions);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mPermissionsToRequest.size() > 0) {
                requestPermissions(mPermissionsToRequest.toArray(new String[mPermissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
            }
        }
    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    private void showMessageOKExit(String message, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener exitListener) {
        new AlertDialog.Builder(WelcomeActivity.this)
                .setMessage(message)
                .setPositiveButton(R.string.ok, okListener)
                .setNegativeButton(R.string.exit, exitListener)
                .create()
                .show();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                mPermissionsRejected.clear();
                for (String perms : mPermissionsToRequest) {
                    if (!hasPermission(perms)) {
                        mPermissionsRejected.add(perms);
                    }
                }
                if (mPermissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(mPermissionsRejected.get(0))) {
                            showMessageOKExit(getApplicationContext().getResources().getString(R.string.premissions_request),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(mPermissionsRejected.toArray(new String[mPermissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    },
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            WelcomeActivity.this.finish();
                                        }
                                    }
                            );
                            return;
                        }
                    }

                }
                break;
        }
    }

    @Override
    public void publishDate(String date) {
        mDateTextView.setText(date);
    }

    @Override
    public void publishLabel(String label) {
        mLastRssTextView.setText(label);
    }

    @Override
    public void changeView() {
        startActivity(new Intent(this, RssActivity.class));
    }
}
