package uk.co.jamesswright.reactanimationframenativeexample;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.react.common.LifecycleState;

public class MainActivity extends Activity {
    private ReactInstanceManager reactInstanceManager;

    private final int OVERLAY_PERMISSION_REQ_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestOverlayPermission();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (this.reactInstanceManager != null) {
            this.reactInstanceManager.onHostPause(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (this.reactInstanceManager != null) {
            this.reactInstanceManager.onHostResume(this, null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (this.reactInstanceManager != null) {
            this.reactInstanceManager.onHostDestroy(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            this.handleOverlayPermissionResponse();
        }
    }

    private void requestOverlayPermission() {
        if (Settings.canDrawOverlays(this)) {
            this.startApp();
            return;
        }

        Intent intent = new Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:" + getPackageName())
        );

        startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
    }

    private void handleOverlayPermissionResponse() {
        if (!Settings.canDrawOverlays(this)) {
            Log.e("MainActivity", "Request for overlay permission denied by user");
            finish();
        }

        this.startApp();
    }

    private void startApp() {
        ReactRootView reactRootView = new ReactRootView(this);

        this.reactInstanceManager = ReactInstanceManager.builder()
            .setApplication((getApplication()))
            .setBundleAssetName("index.android.bundle")
            .setJSMainModuleName("app/src/js/index")
            .addPackage(new MainReactPackage())
            .setUseDeveloperSupport(BuildConfig.DEBUG)
            .setInitialLifecycleState(LifecycleState.RESUMED)
            .build();

        reactRootView.startReactApplication(this.reactInstanceManager, "App");
        setContentView(reactRootView);
    }
}
