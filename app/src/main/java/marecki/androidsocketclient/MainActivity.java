package marecki.androidsocketclient;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {

    TextView textMessage;
    Button buttonConnect;
    EditText editTextIP, editTextPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textMessage = (TextView)findViewById(R.id.text_message);
        buttonConnect = (Button)findViewById(R.id.connect);
        editTextIP = (EditText)findViewById(R.id.address);
        editTextPort = (EditText)findViewById(R.id.port);

        buttonConnect.setOnClickListener(buttonConnectOnClickListener);
    }

    OnClickListener buttonConnectOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            ClientTask clientTask = new ClientTask(editTextIP.getText().toString(),Integer.parseInt(editTextPort.getText().toString()));
            clientTask.execute();
        }
    };
    public class ClientTask extends AsyncTask <Void, Void, Void> {

        String IPaddress;
        int portaddress;

        ClientTask(String address, int port){
            IPaddress = address;
            portaddress = port;
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

}
