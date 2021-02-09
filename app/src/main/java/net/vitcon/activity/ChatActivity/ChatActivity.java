package net.vitcon.activity.ChatActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import net.vitcon.R;
import net.vitcon.model.Message;

import java.util.ArrayList;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {

    private final static int RESULT_RECOGNIZE_SPEECH = 10;
    private ChatAdapter adapter;

    public ChatActivity() {
        // TODO test
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message("Hey yo", "hungdv39"));
        adapter = new ChatAdapter(messages);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().hide();
        // Get self
        ChatActivity self = this;

        // Find view
        ImageView mIvSpeechInput = findViewById(R.id.iv_speech_input);
        RecyclerView mRvChat = findViewById(R.id.rv_chat);
        Button mBtSend = findViewById(R.id.bt_send);
        EditText mEtTextInput = findViewById(R.id.et_text_input);
        // Set chat view
        mRvChat.setAdapter(adapter);
        // Set layout manager
        LinearLayoutManager loManager = new LinearLayoutManager(this);
        loManager.setReverseLayout(true);
        mRvChat.setLayoutManager(loManager);


        // Set on click listener
        mIvSpeechInput.setOnClickListener(v -> {
            // Init speech recognize
            getSpeechInput();
        });

        mBtSend.setOnClickListener(v -> {
            String message = mEtTextInput.getText().toString();
            if (message != null && !message.trim().isEmpty()) {
                adapter.addMessage(new Message(message.trim(), "hungdv39"));
                mEtTextInput.setText("");
            }
        });
    }

    private void getSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, RESULT_RECOGNIZE_SPEECH);
        } else {
            Toast.makeText(this, "Thiết bị không hỗ trợ nhận diện giọng nói", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Process with recognize speech
        if (requestCode == RESULT_RECOGNIZE_SPEECH) {
            // Get data and result
            if (resultCode == RESULT_OK && data != null) {
                // Add data
                ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                for (String result : results) {
                    adapter.addMessage(new Message(result, "hungdv39"));
                }
            }
        }
    }
}