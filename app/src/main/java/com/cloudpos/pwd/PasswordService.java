package com.cloudpos.pwd;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import com.cloudpos.pwd.aidl.control.IAIDLListener;
import com.cloudpos.pwd.aidl.control.ModifyAdminPwdController;
import com.wizarpos.wizarviewagentassistant.aidl.IModifyAdminPwdService;

public class PasswordService extends Service implements IAIDLListener {

    private final static String TAG = "PasswordService";
    private static final String DEFAULT_PWD = "123456";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Run modify admin password...");
        modifyPassword();
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void serviceConnected(Object objService, ServiceConnection connection, int cmd) {
        try {
            if (objService instanceof IModifyAdminPwdService) {
                IModifyAdminPwdService modifyService = (IModifyAdminPwdService) objService;
                if (cmd == 1) {
                    boolean isAdminPwd = modifyService.isAdminPwd(DEFAULT_PWD);
                    Log.i(TAG, "veryfy admin pwd " + (isAdminPwd ? "Success" : "failed"));
                } else if (cmd == 2) {
                    boolean isModifySuccess = modifyService.modifyAdminPwd(DEFAULT_PWD, "123");
                    Log.i(TAG, "modify admin pwd " + (isModifySuccess ? "Success" : "failed"));
                } else if (cmd == 3) {
                    boolean isModifySuccess = modifyService.forceModifyAdminPwd(DEFAULT_PWD);
                    Log.i(TAG, "force modify admin pwd " + (isModifySuccess ? "Success" : "failed"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                this.unbindService(connection);
            }
        }
    }

    public void modifyPassword() {
        ModifyAdminPwdController.getInstance().startConnectService(this, this, 3);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "PasswordService destroyed");
    }
}
