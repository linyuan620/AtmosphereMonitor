package com.skyray.atmospheremonitor.controller;

import android.util.Log;

import com.serotonin.io.serial.SerialParameters;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.msg.ReadCoilsRequest;
import com.serotonin.modbus4j.msg.ReadCoilsResponse;
import com.serotonin.modbus4j.msg.ReadDiscreteInputsRequest;
import com.serotonin.modbus4j.msg.ReadDiscreteInputsResponse;
import com.serotonin.modbus4j.msg.ReadExceptionStatusRequest;
import com.serotonin.modbus4j.msg.ReadExceptionStatusResponse;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersRequest;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersResponse;
import com.serotonin.modbus4j.msg.ReadInputRegistersRequest;
import com.serotonin.modbus4j.msg.ReadInputRegistersResponse;
import com.serotonin.modbus4j.msg.ReportSlaveIdRequest;
import com.serotonin.modbus4j.msg.ReportSlaveIdResponse;
import com.serotonin.modbus4j.msg.WriteCoilRequest;
import com.serotonin.modbus4j.msg.WriteCoilResponse;
import com.serotonin.modbus4j.msg.WriteCoilsRequest;
import com.serotonin.modbus4j.msg.WriteCoilsResponse;
import com.serotonin.modbus4j.msg.WriteMaskRegisterRequest;
import com.serotonin.modbus4j.msg.WriteMaskRegisterResponse;
import com.serotonin.modbus4j.msg.WriteRegisterRequest;
import com.serotonin.modbus4j.msg.WriteRegisterResponse;
import com.serotonin.modbus4j.msg.WriteRegistersRequest;
import com.serotonin.modbus4j.msg.WriteRegistersResponse;

import java.util.Arrays;

/**
 * Created by linyuan on 2017/11/16 0016.
 */

public class SerialMaster {

    private static  String TAG = "SerialMaster";

    private static ModbusMaster master = null;

    /**
     * 初始化连接,读取和写入前必须先调用本方法
     */
    //Device /dev/ttyAMA0
    //微嵌平台 /dev/ttyAMA0 对应COM1

    ///dev/ttyMT2
    //temolin 平台串口号 对应COM3
    public static void InitConnection(){
        SerialParameters serialParameters = new SerialParameters();
        serialParameters.setCommPortId("COM1");
        serialParameters.setPortOwnerName("Numb nuts");
        serialParameters.setBaudRate(9600);

        ModbusFactory modbusFactory = new ModbusFactory();

        master = modbusFactory.createRtuMaster(serialParameters);

        try{
            master.init();
        }catch (Exception e){
            Log.e(TAG, "InitConnection: " + e.getMessage());
        }
    }

    public static void readCoil(int slaveId,int start ,int len){
        try{
            ReadCoilsRequest request = new ReadCoilsRequest(slaveId,start,len);
            ReadCoilsResponse response = (ReadCoilsResponse)master.send(request);

            if(response.isException())
                Log.e(TAG, "readCoil: " +  "Exception response: message=" + response.getExceptionMessage());
            else
                Log.e(TAG, "readCoil: " +   Arrays.toString(response.getBooleanData()));
        }
        catch (ModbusTransportException e){
            e.printStackTrace();
        }
    }

    public static void readDiscreteInput(int slaveId,int start ,int len){

        try{
            ReadDiscreteInputsRequest request = new ReadDiscreteInputsRequest(slaveId,start,len);
            ReadDiscreteInputsResponse response = (ReadDiscreteInputsResponse) master.send(request);

            if(response.isException())
                Log.e(TAG, "readDiscreteInput: " + "Exception response: message=" + response.getExceptionMessage() );
            else
                Log.e(TAG, "readDiscreteInput: " + Arrays.toString(response.getBooleanData()) );
        }
        catch(ModbusTransportException e){
            e.printStackTrace();
        }

    }

    public static short[] readHoldingRegisters(int slaveId , int start , int len){

        short[] result = null;
        try{
            ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(slaveId , start ,len);
            ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse) master.send(request);

            if(response.isException())
                Log.e(TAG, "readHoldingRegisters: " + "读取保持寄存器错误" + response.getExceptionMessage() );
            else
                result =  response.getShortData();
        }
        catch (ModbusTransportException e){
            e.printStackTrace();
        }

        return result;
    }

    public static short[] readInputRegisters (int slaveId , int start , int len){
        short[] result = null;

        try{
            ReadInputRegistersRequest request = new ReadInputRegistersRequest(slaveId , start , len);
            ReadInputRegistersResponse response = (ReadInputRegistersResponse) master.send(request);

            if(response.isException())
                Log.e(TAG, "readInputRegisters: " + "读取只读寄存器错误" + response.getExceptionMessage());
            else
                result = response.getShortData();
        }
        catch (ModbusTransportException e){
            e.printStackTrace();
        }
        return result;
    }

    public static void writeCoil (int slaveId,int offset ,boolean value){
        try{
            WriteCoilRequest request = new WriteCoilRequest(slaveId ,offset ,value);
            WriteCoilResponse response = (WriteCoilResponse) master.send(request);

            if(response.isException())
                Log.e(TAG, "writeCoil: " + "Exceptelseion response: message=" + response.getExceptionMessage() );
            else
                Log.e(TAG, "writeCoil: " + "Success");
        }
        catch (ModbusTransportException e){
            e.printStackTrace();
        }
    }

    public static void writeRegister(int slaveId ,int offset ,int value){
        try{

            WriteRegisterRequest request = new WriteRegisterRequest(slaveId,offset,value);
            WriteRegisterResponse response = (WriteRegisterResponse) master.send(request);

            if(response.isException())
                Log.e(TAG, "writeRegister: " + "Exception response: message=" + response.getExceptionMessage() );
             else
                Log.e(TAG, "writeRegister: " + "Success" );

        }
        catch(ModbusTransportException e){
            e.printStackTrace();
        }

    }


    public static void readExceptionStatus(int slaveId){

        try{
            ReadExceptionStatusRequest request = new ReadExceptionStatusRequest(slaveId);
            ReadExceptionStatusResponse response = (ReadExceptionStatusResponse) master.send(request);

            if(response.isException())
                Log.e(TAG, "readExceptionStatus: " + "Exception response: message=" + response.getExceptionMessage() );
            else
                Log.e(TAG, "readExceptionStatus: " + response.getExceptionStatus() );
        }
        catch(ModbusTransportException e)
        {
            e.printStackTrace();
        }
    }

    public static void reportSlaveId(int slaveId){
        try{
            ReportSlaveIdRequest request = new ReportSlaveIdRequest(slaveId);
            ReportSlaveIdResponse response = (ReportSlaveIdResponse) master.send(request);

            if(response.isException())
                Log.e(TAG, "reportSlaveId: " + "Exception response: message=" + response.getExceptionMessage() );
            else
                Log.e(TAG, "reportSlaveId: " + Arrays.toString(response.getData()) );
        }
        catch (ModbusTransportException e){
            e.printStackTrace();
        }
    }

    public static void writeCoils(int slaveId,int start ,boolean[] values){

        try{
            WriteCoilsRequest request = new WriteCoilsRequest(slaveId,start,values);
            WriteCoilsResponse response = (WriteCoilsResponse) master.send(request);

            if(response.isException())
                Log.e(TAG, "writeCoils: " + "Exception response: message=" + response.getExceptionMessage());
            else
                Log.e(TAG, "writeCoils: " + "Success" );
        }
        catch (ModbusTransportException e){
            e.printStackTrace();
        }

    }

    public static void writeRegisters(int slaveId,int start,short[] values){
        try{
            WriteRegistersRequest request = new WriteRegistersRequest(slaveId,start,values);
            WriteRegistersResponse response = (WriteRegistersResponse) master.send(request);

            if(response.isException())
                Log.e(TAG, "writeRegisters: " + "Exception response: message=" + response.getExceptionMessage() );
            else
                Log.e(TAG, "writeRegisters: " + "Success" );
        }
        catch(ModbusTransportException e){
            e.printStackTrace();
        }
    }

    public static void writeMaskRegister(int slaveId,int offset,int and,int or){

        try{
            WriteMaskRegisterRequest request = new WriteMaskRegisterRequest(slaveId,offset,and,or);
            WriteMaskRegisterResponse response = (WriteMaskRegisterResponse) master.send(request);

            if(response.isException())
                Log.e(TAG, "writeMaskRegister: " + "Exception response: message=" + response.getExceptionMessage());
            else
                Log.e(TAG, "writeMaskRegister: " + "Success" );
        }
        catch(ModbusTransportException e)
        {
            e.printStackTrace();
        }

    }

}
