package com.tdr.wisdome.update;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.tdr.wisdome.util.Constants;


public class GetVersionCodeAsynckTask extends
        AsyncTask<String, Integer, Boolean> {

    private Context mContext;
    private Handler mHandler;

    private VersionInfo versionInfo;

    public GetVersionCodeAsynckTask(Context mContext, Handler mHandler) {
        super();
        this.mContext = mContext;
        this.mHandler = mHandler;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        UpdateWebServiceRequest request = new UpdateWebServiceRequest();
        try {
            versionInfo = request.getVersionCode(params[0]);
            if (versionInfo != null) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (result) {
            Message msg = mHandler.obtainMessage();
            msg.obj = versionInfo.getVersionCode();
            msg.what = Constants.HANDLER_KEY_GETVERSION_SUCCESS;
            mHandler.sendMessage(msg);
        } else {
            mHandler.sendEmptyMessage(Constants.HANDLER_KEY_GETVERSION_FAIL);
        }
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

}
