package marecki.androidsocketclient;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    TextView textAnswer;
    Button buttonConnect;
    EditText editTextIP, editTextPort, message;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textAnswer = (TextView)findViewById(R.id.text_answer);
        buttonConnect = (Button)findViewById(R.id.connect);
        editTextIP = (EditText)findViewById(R.id.address);
        editTextPort = (EditText)findViewById(R.id.port);
        message = (EditText)findViewById(R.id.edit_message);

       progressBar = (ProgressBar)findViewById((R.id.progressBar));
       progressBar.setVisibility(ProgressBar.INVISIBLE);

        buttonConnect.setOnClickListener(buttonConnectOnClickListener);
    }

    OnClickListener buttonConnectOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {

            String Msg = message.getText().toString();

            ClientTask clientTask = new ClientTask(editTextIP.getText().toString(),Integer.parseInt(editTextPort.getText().toString()),Msg);
            clientTask.execute();
        }
    };
    public class ClientTask extends AsyncTask <Void, Void, Void> {

        String IPaddress;
        int portAddress;
        String AnswerFromServer ="";
        String msgToServer;

        ClientTask(String address, int port, String msgToServ){
            IPaddress = address;
            portAddress = port;
            msgToServer = msgToServ;
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            Socket socket = null;
            DataOutputStream dataOutputStream = null;
            DataInputStream dataInputStream = null;

            try {
                socket = new Socket(IPaddress,portAddress);

                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataInputStream = new DataInputStream(socket.getInputStream());

                if(msgToServer != null){
                    dataOutputStream.writeUTF(msgToServer);
                }

                AnswerFromServer = dataInputStream.readUTF();

            }catch (UnknownHostException e) {
                e.printStackTrace();
                AnswerFromServer = e.toString();
            }catch (IOException e) {
                e.printStackTrace();
                AnswerFromServer = e.toString();
            }finally {
                if(socket != null){
                    try{
                        socket.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }

            if(dataOutputStream != null){
                try{
                    dataOutputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            if(dataInputStream != null){
                try{
                    dataInputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(ProgressBar.INVISIBLE);
            textAnswer.setText(AnswerFromServer);
            super.onPostExecute(aVoid);
        }
    }

}
