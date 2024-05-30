package com.example.listadetarefas;

import static android.widget.Toast.makeText;

import android.content.Context;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayAdapter<String> devicesAdapter;
    private AudioManager audioManager;
    private AudioDeviceInfo selectedDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView devicesListView = findViewById(R.id.devicesList);
        devicesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        devicesListView.setAdapter(devicesAdapter);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        updateDevicesList();

        Button getDeviceButton = findViewById(R.id.selectDeviceButton);
        getDeviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDevice = (AudioDeviceInfo) devicesListView.getSelectedItem();
                if (selectedDevice != null) {
                    makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show(); makeText(MainActivity.this, "Dispositivo selecionado: " + selectedDevice.getProductName(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateDevicesList() {
        devicesAdapter.clear();

        List<AudioDeviceInfo> audioDevices = getAudioDevices();

        for (AudioDeviceInfo device : audioDevices) {
            devicesAdapter.add(device.getProductName().toString());
        }

        devicesAdapter.notifyDataSetChanged();
    }

    private List<AudioDeviceInfo> getAudioDevices() {

        AudioDeviceInfo[] devices = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS);

        return new ArrayList<>(Arrays.asList(devices));
    }
}
