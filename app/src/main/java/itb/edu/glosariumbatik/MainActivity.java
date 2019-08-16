package itb.edu.glosariumbatik;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // View Object
    private Button buttonScan;
    private TextView textViewNama, textViewTinggi;

    //qr code scanner object
    private IntentIntegrator intentIntegrator;

    private WebView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize object
        buttonScan = (Button) findViewById(R.id.buttonScan);

        // attaching onclickListener
        buttonScan.setOnClickListener(this);

        genWeb("http://batik.tf.itb.ac.id/");
    }

    // Mendapatkan hasil scan

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
            if (result.getContents() == null){
                Toast.makeText(this, "Hasil tidak ditemukan", Toast.LENGTH_SHORT).show();
            }else{
                // jika qrcode berisi data
                genWeb(result.getContents());
//                try{
//                    // converting the data json
//                    JSONObject object = new JSONObject(result.getContents());
//                    // atur nilai ke textviews
//                    textViewNama.setText(object.getString("nama"));
//                    textViewTinggi.setText(object.getString("tinggi"));
//                }catch (JSONException e){
//                    e.printStackTrace();
//                    // jika format encoded tidak sesuai maka hasil
//                    // ditampilkan ke toast
//                    Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
//                }
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View v) {
        // inisialisasi IntentIntegrator(scanQR)
        intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setPrompt("Pindai untuk detail lebih jelas");
        intentIntegrator.initiateScan();
    }

    void genWeb(String url) {
        view = (WebView) this.findViewById(R.id.webView);
        view.getSettings().setJavaScriptEnabled(true);
        view.setWebViewClient(new MyBrowser());
        view.loadUrl(url);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
