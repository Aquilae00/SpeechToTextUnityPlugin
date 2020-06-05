# SpeechToTextUnityPlugin
<p>Speech-to-Text Android plugin for Unity. Utilzies built-in Android API <a href="https://developer.android.com/reference/android/speech/package-summary">android.speech</a> by Google.
No dialog pop-ups</p>

<p>Plugin class doesn't extend Activity, so it uses your Unity activity and doesn't overlap an activity or stop underlying activity. An activity/context needs to be passed in to use the plugin.</p>

<h2>How to use:</h2>
<ol>
<li>Copy classes.jar or build yourself using this library
<li>Paste into Unity project folder under Plugins/Android
<li>Add record audio permission in Unity AndroidManifest.xml (Need to enable Unity custom AndroidManifest in settings)
<li>In Unity script, instantiate object <code>AndroidJavaClass pluginClass = new AndroidJavaClass("com.example.sttunityplugin.Plugin");</code> This object is entry point to the plugin
<li>Obtain Unity Activity <code>AndroidJavaClass unityPlayer = new AndroidJavaClass("com.unity3d.player.UnityPlayer");</code> then <code>AndroidJavaObject context = unityPlayer.GetStatic<AndroidJavaObject>("currentActivity");</code>
<li>Set context/Activity <code>pluginClass.CallStatic("setContext", context);</code>
<li>Set objectName to pass the results to <code>pluginClass.CallStatic("setObjectName", objectName);</code>
<li>Call startListening method on main UI thread. Can do like this <code>context.Call("runOnUiThread", new AndroidJavaRunnable(listen));</code> listen method is calling static <code>startListening</code>
<li>Add script to receive your result, create <code>void onActivityResult(String result);</code> The results from the plugin will be received by this method.
<li>???
<li>Profit
</ol>

<h2>Reason WHY I built this</h2>
<p>For ARCore. Needed a plugin that doesn't open shitty Google dialog pop-up or any other activity that interrupts ARCore activity</p>
