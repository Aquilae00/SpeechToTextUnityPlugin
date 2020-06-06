package com.example.sttunityplugin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import com.unity3d.player.UnityPlayer;

import java.util.ArrayList;

/**
 * Plugin Class to be called statically from Unity.
 * This class has no instance.
 */
public class Plugin {
    private static String objectName;
    private static Context context;
    private static final String TAG = "Plugin";

    /**
     * Set the Object name in Unity that has onActivityResult method attached to it.
     * @param string name of the object
     */
    public static void setObjectName(String string) {
        objectName = string;
    }

    /**
     * Call setContext from Unity to set Plugin.Context to UnityPlayerActivity.
     * unityPlayer.GetStatic<AndroidJavaObject>("currentActivity");
     * This method will be used to obtain UnityActivity and later attached to SpeechRecognizer.
     * @param c the context to be user for this class.
     */
    public static void setContext(Context c) {
        context = c;
    }

    /**
     * This method locally instantiates the important components of Speech Recognition capability.
     * onResults method in listener passes the results of SpeechRecognition to Unity's objectName.
     */
    public static void startListening() {
        if(context==null){
            throw new NullPointerException("null context");
        }
        SpeechRecognizer speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
        RecognitionListener recognitionListener = new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle results) {
                String str = new String();
                Log.d(TAG, "onResults " + results);
                ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                for (int i = 0; i < data.size(); i++) {
                    Log.d(TAG, "result " + data.get(i));
                    str += (data.get(i) + "~");
                }
                String result = str.split("~")[0];
                if (objectName != null) {
                    UnityPlayer.UnitySendMessage(objectName, "onActivityResult", result);
                    Log.d(TAG, "Unity objectName method called");
                } else {
                    throw new NullPointerException("null objectName");
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        };
        speechRecognizer.setRecognitionListener(recognitionListener);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        speechRecognizer.cancel();
        speechRecognizer.startListening(intent);

    }

}
