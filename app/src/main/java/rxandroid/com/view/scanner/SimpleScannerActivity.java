package rxandroid.com.view.scanner;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.zxing.Result;

import io.vov.vitamio.utils.StringUtils;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import rxandroid.com.view.scanner.interfaces.ScannerCallBack;

import static io.vov.vitamio.utils.Log.TAG;

/**
 * Created on 2017/7/3.
 * Author by Aaron.Wang
 */

public class SimpleScannerActivity extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    public ScannerCallBack scannerCallBack ;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
         Log.v(TAG, rawResult.getText()); // Prints scan results
        Log.v(TAG, rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
        String data = rawResult.getText().toString() ;
        if (data!=null||!data.equals("")){
            scannerCallBack.getData(rawResult.getText());
        }else{
            scannerCallBack.getData("扫码格式不对，请重新扫码！");
        }
        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);
    }
}
