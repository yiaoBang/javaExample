package com.yiaobang;


import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.procimg.SimpleRegister;
import com.ghgande.j2mod.modbus.util.SerialParameters;

/**
 * 继电器
 *
 * @author Y
 * @version 1.0
 * @date 2023/9/11 14:12
 */

public class Modbus {
    /**
     * 寄存器数字写为0
     */
    public static final SimpleRegister ONE = new SimpleRegister(1);
    /**
     * 寄存器数字写为1
     */
    public static final SimpleRegister ZERO = new SimpleRegister(0);
    /**
     * 绿灯亮
     */
    public static final SimpleRegister[] GREEN = new SimpleRegister[]{ONE, ZERO, ZERO};
    /**
     * 黄灯亮
     */
    public static final SimpleRegister[] YELLOW = new SimpleRegister[]{ZERO, ONE, ZERO};
    /**
     * 红灯亮
     */
    public static final SimpleRegister[] RED = new SimpleRegister[]{ZERO, ZERO, ONE};
    /**
     * 灯全灭
     */
    public static final SimpleRegister[] BLACK = new SimpleRegister[]{ZERO, ZERO, ZERO};

    public static void main(String[] args) throws Exception {
        SerialParameters serialParameters = new SerialParameters();
        serialParameters.setPortName("");
        serialParameters.setBaudRate(0);
        serialParameters.setBaudRate("");
        serialParameters.setFlowControlIn(0);
        serialParameters.setFlowControlIn("");
        serialParameters.setFlowControlOut(0);
        serialParameters.setFlowControlOut("");
        serialParameters.setDatabits(0);
        serialParameters.setDatabits("");
        serialParameters.setStopbits(0);
        serialParameters.setStopbits("");
        serialParameters.setParity(0);
        serialParameters.setParity("");
        serialParameters.setEncoding("");
        serialParameters.setEcho(false);
        serialParameters.setOpenDelay(0);
        serialParameters.setOpenDelay("");
        serialParameters.setRs485Mode(false);
        serialParameters.setRs485TxEnableActiveHigh(false);
        serialParameters.setRs485EnableTermination(false);
        serialParameters.setRs485RxDuringTx(false);
        serialParameters.setRs485DelayBeforeTxMicroseconds(0);
        serialParameters.setRs485DelayBeforeTxMicroseconds("");
        serialParameters.setRs485DelayAfterTxMicroseconds(0);
        serialParameters.setRs485DelayAfterTxMicroseconds("");

        ModbusSerialMaster master = new ModbusSerialMaster(serialParameters);
        master.connect();
    }

}
