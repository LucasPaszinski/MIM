package MIM;

import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.serial.SerialParameters;
import com.intelligt.modbus.jlibmodbus.serial.SerialPort;
import com.intelligt.modbus.jlibmodbus.serial.*;
import com.intelligt.modbus.jlibmodbus.serial.SerialUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPortList;

public class ModbusRTU { 
    
    /** Endereço do Slave ModBus*/
    public int slaveId = 1;
 
    private SerialParameters sp = new SerialParameters();
    
    private ModbusMaster m ;
    
    /**Metodo que retorna os Nomes das portas identificadas, pode ser usado nos
     * demais parametros (Ex:.coms=COM();coms[1];)
     * 
     * @return Array de Strings com as portas ativas (COM's)
     */
    public String[] COM(){
        return SerialPortList.getPortNames();
    }

    private SerialPort.BaudRate DefBaudRate(int BaudRate){
        if(BaudRate==115200) return SerialPort.BaudRate.BAUD_RATE_115200;
        if(BaudRate==14400) return SerialPort.BaudRate.BAUD_RATE_14400;
        if(BaudRate==19200) return SerialPort.BaudRate.BAUD_RATE_19200;
        if(BaudRate==38400) return SerialPort.BaudRate.BAUD_RATE_38400;
        if(BaudRate==4800) return SerialPort.BaudRate.BAUD_RATE_4800;
        if(BaudRate==57600) return SerialPort.BaudRate.BAUD_RATE_57600;
        if(BaudRate==9600) return SerialPort.BaudRate.BAUD_RATE_9600;
        return SerialPort.BaudRate.BAUD_RATE_115200;
    }
    
    private int DataBits(int dataBits){
        if(dataBits==7 || dataBits==8) return dataBits;
        return 0;
    }
    
    private SerialPort.Parity Parity(String parity){
        if("None".equalsIgnoreCase(parity)) return SerialPort.Parity.NONE;
        if("Even".equalsIgnoreCase(parity)) return SerialPort.Parity.EVEN;
        if("Mark".equalsIgnoreCase(parity)) return SerialPort.Parity.MARK;
        if("Odd".equalsIgnoreCase(parity)) return SerialPort.Parity.ODD;
        if("Space".equalsIgnoreCase(parity)) return SerialPort.Parity.SPACE;
        return SerialPort.Parity.NONE;
    }
    
    private int StopBits(int stopBits)
    {
        if(stopBits==1 || stopBits==2) return stopBits;
        return 1;
    }
    
    
 /**Metodo que realiza o Setup dos parametros da máquina
 * @param COM contendo o nome da Serial (Ex. "COM1")
 * @param baudRate com o valor de BaudRate, que deve ser um dos seguintes (115200, 14400, 19200, 38400, 4800, 57600, 9600)
 * @param dataBits valor dos Data Bits que devem ser "7" ou "8" 
 * @param stopBits valor dos Stop Bits que devem ser "2" ou "1" 
 * @param parity contendo a paridade desejada (NONE, EVEN, MARK, ODD, SPACE)
 */
    public void ModbusSetupParameters(String COM, int baudRate, int dataBits, int stopBits, String parity){
                sp.setDevice(COM);
                sp.setBaudRate(DefBaudRate(baudRate));//(CLP Altus)//9600p/Arduino SerialPort.BaudRate.BAUD_RATE_115200
                sp.setDataBits(DataBits(dataBits));
                sp.setParity(Parity(parity));
                sp.setStopBits(StopBits(stopBits));
    }

/** Metodo que inicia a conexão com o CLP, deve ser o primeiro a ser executado*/    
    public void StartUp(){
        Modbus.setLogLevel(Modbus.LogLevel.LEVEL_DEBUG);
        try {
            m = ModbusMasterFactory.createModbusMasterRTU(sp);
            String[] devices = SerialPortList.getPortNames();
            if (devices.length > 0) {                
                //  JSSC is Java Simple Serial Connector
                SerialUtils.setSerialPortFactory(new SerialPortFactoryJSSC());
                m = ModbusMasterFactory.createModbusMasterRTU(sp);                
                m.connect();                
                Thread.sleep(1000);
            }
        }
        catch (RuntimeException e) {
            throw e;
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private int ReadHoldingRegisters(int slaveId,int address)
    {
        int[] registerValues;
        address=address-40001;
        try {
                    
                    registerValues = m.readHoldingRegisters(slaveId, address, 1);
                    // print values
                    int offsetmais = address;
                    for (int value : registerValues) {
                        System.out.println("\n\n\n\n\nAddress: " + offsetmais + ", Value: " + value+"\n\n\n\n\n");
                    }
                    return registerValues[0];
                } catch (RuntimeException e) {
                    throw e;
                } catch (Exception e) {
                    e.printStackTrace();
                } 
        int badway=0;
        return badway;
        
   }
    
    private void WriteHoldingRegisters(int slaveId,int address,int registervalue)
    {
        address=address-40001;
        try {
             m.writeSingleRegister(slaveId, address, registervalue);

        } catch (RuntimeException e) {
                throw e;
        } catch (Exception e) {
                e.printStackTrace();
        } 

   }
  
 /**Metodo que aplica os parametros para a comunicação com Arduino
 * @param COM contendo o nome da Serial (Ex. "COM1")
 */
    public void ParametersCLPAltus(String COM){
        sp.setDevice(COM); // gets COM8 //SerialPortList.getPortNames();
        // these parameters are set by default
        sp.setBaudRate(SerialPort.BaudRate.BAUD_RATE_115200);//(CLP Altus)//9600p/Arduino
        sp.setDataBits(8);
        sp.setParity(SerialPort.Parity.NONE);
        sp.setStopBits(1);
    }
/**Metodo que aplica os parametros para a comunicação com Arduino
 * @param COM contendo o nome da Serial (Ex. "COM1")
 */
        public void ParametersArduino(String COM){
        sp.setDevice(COM); // gets COM8 //SerialPortList.getPortNames();
        // these parameters are set by default
        sp.setBaudRate(SerialPort.BaudRate.BAUD_RATE_9600);//(CLP Altus)//9600p/Arduino
        sp.setDataBits(8);
        sp.setParity(SerialPort.Parity.NONE);
        sp.setStopBits(1);
    }
        
    /** Metodo RunCLP do Agente, escreve o sinal de inicio no Holding Register 40001 (MW0)
    *@param  registerValue - O valor o qual voce quer escrever no endereço
    */
    public void RunCLP(int registerValue){
       WriteHoldingRegisters(slaveId, 40001, registerValue);        
    }
    /** Metodo RunCLP do Agente, lê o sinal de inicio no Holding Register 40001 (MW0)
    *@return  A int que foi lida no endereço 40001, caso seja diferente da enviada houve erro na hora da escrita 
    */
    public int RunCLP(){
       return ReadHoldingRegisters(slaveId,40001);        
    }
    
    /** Metodo DoneCLP do Agente, Lê o Holding Register 40002 (MW1) para descorbir se o serviço ja terminou
    *Set o valor que está escrito no endereço 40002 
    * @param registerValue the value to be set on the address
    */
    public  void DoneCLP(int registerValue){
         WriteHoldingRegisters(slaveId, 40002, registerValue); 
    }
    
    /** Metodo DoneCLP do Agente, Lê o Holding Register 40002 (MW1) para descorbir se o serviço ja terminou
    *@return Lê o valor que está escrito no endereço 40002 
    */
    public  int DoneCLP(){
        return ReadHoldingRegisters(slaveId, 40002);
    }
    /** Metodo InformCLP do Agente, escreve o sinal de inicio no Holding Register 40003 (MW2)
    *@param service Um valor que indique qual o serviço a ser realizado pela máquina
    */
    public void InformCLP(int service){
    int address = 40003;
    WriteHoldingRegisters(slaveId, address, service); 
    }
    
    /** Metodo InformCLP do Agente, lê o sinal de inicio no Holding Register 40003 (MW2)
    * @return  Retorna o valor lido no endereço o qual escreveu, para confirmar se o valor enviado é o mesmo escrito.
    */
    public  int InformCLP(){
    int address = 40003;
    return ReadHoldingRegisters(slaveId,address); 
    }
    /** Metodo DoneCLP do Agente, Lê o Holding Register 40004 (MW3) para descorbir se o serviço ja terminou
    *Set o valor que está escrito no endereço 40004 
    * @param registerValue the value to be set on the address
    */
    public void StatusCLP(int registerValue){
         WriteHoldingRegisters(slaveId, 40004, registerValue); 
    }
    /** Metodo InformCLP do Agente, escreve o sinal de inicio no Holding Register 40004 (MW3)
    * @return  Retorna o valor lido no endereço 40004 indicando o estado atual da máquina.
    */
    public  int StatusCLP(){
       return ReadHoldingRegisters(slaveId, 40004);       
    }

    void Disconnect() {
        try {
            m.disconnect();
        } catch (ModbusIOException ex) {
            Logger.getLogger(ModbusRTU.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
                             
 

