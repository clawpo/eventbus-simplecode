package cn.ucai.a2eventbusintent;

public class IntentServiceResult {
    int mResult;
    String mResultValue;

    IntentServiceResult(int resultCode, String resultValue) {
        mResult = resultCode;
        mResultValue = resultValue;
    }

    public int getResult() {
        return mResult;
    }

    public String getResultValue() {
        return mResultValue;
    }
}
