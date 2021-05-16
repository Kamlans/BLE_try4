package com.example.ble_try4;

import android.bluetooth.BluetoothDevice;

public class BT_device {
    private BluetoothDevice device;
    private String name , address;

    public BT_device(BluetoothDevice device, String name, String address) {
        this.device = device;
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return "BT_device{" +
                "device=" + device +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
