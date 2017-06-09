package com.bridgefy.react.sdk;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.ReactContext;

import java.util.List;

/**
 * @author kekoyde on 6/8/17.
 */

public class BridgefyReactActivity extends ReactActivity implements ReactInstanceManager.ReactInstanceEventListener {
    @Override
    protected String getMainComponentName() {
        return null;
    }

    @Override
    protected boolean getUseDeveloperSupport() {
        return false;
    }

    @Override
    protected List<ReactPackage> getPackages() {
        return null;
    }

    @Override
    public void onReactContextInitialized(ReactContext context) {

    }
}
