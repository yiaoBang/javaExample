package com.yiaobang;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static com.fazecast.jSerialComm.SerialPort.FLOW_CONTROL_XONXOFF_IN_ENABLED;
import static com.fazecast.jSerialComm.SerialPort.FLOW_CONTROL_XONXOFF_OUT_ENABLED;

/**
 * @author Y
 * @version 1.0
 * @date 2023/10/31 19:58
 */
public class ADR {
    static Scanner scanner = new Scanner(System.in);
    static SerialPort com6 = SerialPort.getCommPort("COM6");

    public static void main(String[] args) {
        com6.setComPortParameters(9600, 8, 1, 0);
        com6.setFlowControl(FLOW_CONTROL_XONXOFF_IN_ENABLED | FLOW_CONTROL_XONXOFF_OUT_ENABLED);
        com6.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING | SerialPort.TIMEOUT_WRITE_BLOCKING, 200, 200);
        com6.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                byte[] buffer = new byte[com6.bytesAvailable()];
                System.out.println(new String(buffer));
            }
        });
        com6.openPort();
        write("ADR 06");
    }

    public static void write(String s) {
        write((s + "\r").getBytes(StandardCharsets.UTF_8));
    }

    public static void write(byte[] b) {
        com6.writeBytes(b, b.length);
    }
}
