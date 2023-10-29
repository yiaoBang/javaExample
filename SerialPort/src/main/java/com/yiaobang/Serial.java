package com.yiaobang;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Y
 * @version 1.0
 * @date 2023/10/14 11:12
 */
public class Serial implements SerialPortDataListener {

    public static final SimpleStringProperty SIMPLE_STRING_PROPERTY = new SimpleStringProperty();
    /**
     * 读到缓冲区
     */
    private final byte[] readBuffer = new byte[1024];
    private int pos;

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        SerialPort serialPort = event.getSerialPort();
        byte[] buffer = new byte[serialPort.bytesAvailable()];
        int numRead = serialPort.readBytes(buffer, buffer.length);
        for (int i = 0; i < numRead; i++) {
            if (buffer[i] == ' ') {
                update(new String(readBuffer, 0, pos));
                pos = 0;
            } else {
                readBuffer[pos] = buffer[i];
                pos++;
            }
        }
    }

    public void update(String str) {
        Platform.runLater(() -> SIMPLE_STRING_PROPERTY.set(str));
    }
}
