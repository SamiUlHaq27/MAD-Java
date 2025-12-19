package com.example.madjava;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;

public class MediaService extends Service {

    public static final String MEDIA_STATUS = "media_status";
    public static final String STATUS_PLAYING = "PLAYING";
    public static final String STATUS_PAUSED = "PAUSED";
    public static final String STATUS_STOPPED = "STOPPED";
    public static final String ACTION_PLAY = "com.example.madjava.ACTION_PLAY";
    public static final String ACTION_PAUSE = "com.example.madjava.ACTION_PAUSE";
    public static final String ACTION_STOP = "com.example.madjava.ACTION_STOP";
    public static final String BROADCAST_ACTION = "com.example.madjava.MEDIA_STATUS_BROADCAST";

    private MediaPlayer mediaPlayer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            switch (intent.getAction()) {
                case ACTION_PLAY:
                    play();
                    break;
                case ACTION_PAUSE:
                    pause();
                    break;
                case ACTION_STOP:
                    stopPlayer();
                    break;
            }
        }
        return START_STICKY;
    }

    public void play() {
        try {
            if (mediaPlayer == null) {
                Uri ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                mediaPlayer = MediaPlayer.create(this, ringtoneUri);
                mediaPlayer.setLooping(true);
            }

            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                sendMediaStatusBroadcast(STATUS_PLAYING);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            sendMediaStatusBroadcast(STATUS_PAUSED);
        }
    }

    public void stopPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            sendMediaStatusBroadcast(STATUS_STOPPED);
        }
    }

    private void sendMediaStatusBroadcast(String status) {
        Intent intent = new Intent(BROADCAST_ACTION);
        intent.putExtra(MEDIA_STATUS, status);
        sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        stopPlayer();
        super.onDestroy();
    }
}