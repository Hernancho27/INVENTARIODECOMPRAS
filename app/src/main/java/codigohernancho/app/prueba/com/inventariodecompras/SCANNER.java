package codigohernancho.app.prueba.com.inventariodecompras;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class SCANNER extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private ZXingScannerView escanerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
    }

    @Override
    public void handleResult(Result result) {

    }

}