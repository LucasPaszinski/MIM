/* AgenteMaquina que provoca a interação com máquna
 * Implementado por João Alvarez Peixoto
 */
package MIM;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

     

public class AgenteMaquina extends Agent implements InterfaceAgenteForm {
    InterfaceAgenteForm myInterface;
    FormAgenteMaquina myForm = new FormAgenteMaquina(this);
  
    ArrayList<String> _machineServicesArray = new ArrayList<String>()
            {
                {
                add("Storage4GB");
                add("Storage8GB");
                add("Storage16GB");
                add("Storage32GB");
                add("Storage64GB");
                add("Storage128GB");
                }
                
            };   
    boolean _isMachineFree = false;// indica o estado da máquina, false = ocupada, true = livre;
    boolean _isMachineDone = false;//indica se a execução da máquina chegou ao fim, com um true;
    boolean _hasServicePosted = false;// false = sem postagem, true = com postagem
    String _machineAsnwer;//indica a resposta da máquina a manutenção que executou
    ModbusRTU _modbus = new ModbusRTU();
    
    
    protected void setup() { 
        System.out.println("Oi, sou agente "+ getLocalName());
        myForm.setTitle("Agente " + getAID().getLocalName());
        myForm.setVisible(true);//para ativar o form
        addBehaviour(new NegociadorMaquina());// Comportamento que negocia com agentes 
        ModbusConfigurationSetup();
        myForm.SetServiceArray(this._machineServicesArray);
        myForm.RealoadServiçes();
    }
    protected void takeDown() {
        System.out.println("Agente "+ getLocalName()+" Encerrado");      
        //System.exit(0);//comando para fechar o programa- retira para evitar que ao fechar um programa feche os demais
    }
    public void botaoSair(){  
        _modbus.Disconnect();
        myForm.dispose();//para fechar o form
        if(getPostagemServico()){ retirarPostagem(this);}// retira o que fora postado, caso haja
        doDelete();//chama o método takedown
    }
    
    public void ModbusConfigurationSetup(){
        this._modbus.slaveId=2;
        this._modbus.ParametersArduino("COM3"); 
        this._modbus.StartUp();
        myForm.RealoadModbus(_modbus.RunCLP(),_modbus.DoneCLP(),_modbus.InformCLP(),_modbus.StatusCLP()); 
        
    }
        
    public void setPostagemServico(boolean PostSevico){
        this._hasServicePosted = PostSevico;//se true = há serviços postados, se false = não há serviços postados
        this.setEstadoMaquina(true);//indica que a máquina está livre para o trabalho
        
    }
    public boolean getPostagemServico(){
        return this._hasServicePosted;//se true = há serviços postados, se false = não há serviços postados
    }
    public void setEstadoMaquina(boolean estado){
        this._isMachineFree = estado;//se true = livre, se false = ocupada        
        myForm.setSinaleira(estado); 
    }
    public boolean getEstadoMaquina(){
        return this._isMachineFree;//se true = livre, se false = ocupada
    }
    public void setFimExecucaoMaquina(boolean execucao){//ajusta a indicação do final de execução da máquina
        this._isMachineDone = execucao;
    }
    public void setRespostaMaquina(String resposta){//ajusta a indicação de resposta ao final de execução da máquina
        this._machineAsnwer = resposta;
    }
    public boolean getFimExecucaoMaquina(){//monitora o estado da indicaçao do final de execução da máquina
        return this._isMachineDone;
    }
    public String getRespostaMaquina(){//monitora o estado da indicaçao de resposta ao final de execução da máquina
        return this._machineAsnwer;
    }
    
   
    public int obterCusto(String criterio){
        int custo=500;// um custo alto para não ganhar       
        if(criterio.equalsIgnoreCase("preco")){//0 a 100
            custo = (int) (Math.random()*1000);// tandom gera um double de 0 a 1
        }
        if(criterio.equalsIgnoreCase("distancia")){//0 a 100
            custo = (int) (Math.random()*1000);// tandom gera um double de 0 a 1
        }
        return custo;
    }

    public void postarServico(ArrayList<String> habilidades){
        if(!this.getPostagemServico()){//verifica se o serviço já foi postado
            DFAgentDescription dfd = new DFAgentDescription();
            dfd.setName(this.getAID());
            for(String habilidade : habilidades) {
                ServiceDescription sd = new ServiceDescription();
                sd.setType("manufatura");
                sd.setName(habilidade);
                sd.setOwnership(getLocalName());
                dfd.addServices(sd);                
            }
            try { DFService.register(this, dfd); }
            catch (FIPAException fe) { fe.printStackTrace(); } 
            this.setPostagemServico(true);//para indicar que um serviço foi postado
        }else{
            JOptionPane.showMessageDialog(null, "Serviço já postado", "Máquina" + getAID().getLocalName(), JOptionPane.INFORMATION_MESSAGE);                              
        }
    }
 
    public void retirarPostagem(Agent ag){
        try { DFService.deregister(ag); }
        catch (FIPAException fe) { fe.printStackTrace(); }
    }

    public class NegociadorMaquina extends Behaviour { 
        ACLMessage Termino;//para mandar o INFORM no final do processo
        
        public NegociadorMaquina(){ //construtor da classe  
        }
        @Override
        public void action(){          
            ACLMessage mensagem = myAgent.receive();//lê a mensage            
            if (mensagem != null) {                                                             
                if (mensagem.getPerformative() == ACLMessage.CFP) {                
                    myForm.atualizarTexto(""+mensagem.getSender().getLocalName()+" -> "+mensagem.getContent());                   
                    if(!getEstadoMaquina()){//Monitora o estado da máquina => TRUE=livre, FALSE=ocupada
                        ACLMessage Ocupada = mensagem.createReply();
                        Ocupada.setPerformative(ACLMessage.REFUSE);
                        Ocupada.setContent("Maquina Ocupada");
                        myForm.atualizarTexto("Não Posso"); 
                        myAgent.send(Ocupada);
                    }else{
                        StringTokenizer M = new StringTokenizer(mensagem.getContent());
                        String ServicoM = M.nextToken();// para tomar o <servico>
                        String CriterioM = M.nextToken();// para tomar o <criterio>
                        ACLMessage PropostaMaquina = mensagem.createReply();
                        PropostaMaquina.setPerformative(ACLMessage.PROPOSE);
                        PropostaMaquina.setContent(""+obterCusto(CriterioM));
                        myAgent.send(PropostaMaquina);
                          myForm.atualizarTexto("Posso com custo de "+PropostaMaquina.getContent());
                        //setEstadoMaquina(false);//para ocupar a máquina e não atender outro pedido
                    }
                }               
                if (mensagem.getPerformative() == ACLMessage.REJECT_PROPOSAL) {//proposta foi rejeitada
                    myForm.atualizarTexto("Proposta Recusada");
                    setEstadoMaquina(true);//para liberar a máquina   
                }
                if (mensagem.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {//proposta foi aceita                    
                    myForm.atualizarTexto("Proposta foi Aceita");
                    setEstadoMaquina(false);//para manter a máquina ocupada 
                }
                if (mensagem.getPerformative() == ACLMessage.REQUEST) {
                    String informacao = mensagem.getContent();
                    setEstadoMaquina(false);//para manter a máquina ocupada 
                      myForm.atualizarTexto("Requisitado por "+mensagem.getSender().getLocalName());
                      myForm.atualizarTexto("Servico = "+informacao);
                    Termino = mensagem.createReply();//para responder o INFORM no final
                    //Executa a máquina
                    Thread Maquina = new ExecutaMaquina(informacao);// executa a thread que fara a tarefa propriamente                    
                    Maquina.start();//executa a thread
                }
            }
            if(getFimExecucaoMaquina()){
                setFimExecucaoMaquina(false); //para resetar a flag de FimExecucaoMaquina               
                myForm.atualizarTexto("Terminou a atividade");
                Termino.setPerformative(ACLMessage.INFORM);
                Termino.setContent(getRespostaMaquina());//agrega a resposta da máquina
                myAgent.send(Termino); 
                long tempo1 = new Date().getTime();
                int contador = 0;
                while (contador < 3) {
                    long tempo2 = new Date().getTime();
                    if (tempo2 > tempo1+1000) {
                        tempo1 = tempo2;
                        contador++;
                        myForm.atualizarTexto("Encerrando..."+contador);
                    }   
                }
                myForm.limparTexto();//limpar a caixa de mensagens
                myForm.atualizarTexto("Máquina Disponível...");
                setEstadoMaquina(true);//maquina liberada = true; máquina ocupada = false;
            }
            block(500);//bloqueia a varredura por 500ms ou quando chegar uma nova mensagem
        }        
        @Override
        public boolean done() {
            return false;//para ficar sempre ativo//////////////////////////
        }
    }//final do comportamento
    
    public class ExecutaMaquina extends Thread {//executa a máquina
        String InformacaoMaquina;
        Boolean CicloMaquina=true;//indica que a máquina está ligada
        long TempoCiclo1=0;// para provocar o retardo de tempo
        long TempoCiclo2=0;// para provocar o retardo de tempo
        int ContadorCiclo;//conta quantos ciclos a máquina realizou
        String Resposta = "Tudo Certo";
        int Velocidade=1000;// rapida = 200 - media=500 - lenta=1000
        
        public ExecutaMaquina(String infoMaquina){//construtor da classe
             this.InformacaoMaquina = infoMaquina;                         
        }    
        
        private int Inform(int _inform)
        {
            _modbus.InformCLP(_inform);
            return _modbus.InformCLP();
        }
        @Override
        public void run() {            
            int passo = 1; //contador de passos
            int _inform = 0;
            _modbus.RunCLP(0);
            _modbus.DoneCLP(0);
            _modbus.InformCLP(0);
            _modbus.StatusCLP(0);
            myForm.RealoadModbus(_modbus.RunCLP(),_modbus.DoneCLP(),_modbus.InformCLP(),_modbus.StatusCLP());
            String _informName = InformacaoMaquina.toString();
            ContadorCiclo = 0; //para zerar a contagem
            if(_machineServicesArray.contains(InformacaoMaquina)) _inform = Inform(_machineServicesArray.indexOf(InformacaoMaquina)+2);
            else _inform = 1;
            _modbus.RunCLP(2);
            
            while(_modbus.DoneCLP()!=2){
                TempoCiclo2 = new Date().getTime();
                myForm.RealoadModbus(_modbus.RunCLP(),_modbus.DoneCLP(),_modbus.InformCLP(),_modbus.StatusCLP());
            }//fim do while
            myForm.RealoadModbus(_modbus.RunCLP(),_modbus.DoneCLP(),_modbus.InformCLP(),_modbus.StatusCLP());
            myForm.ServiceStatus(_informName, _inform, _modbus.StatusCLP());
            
            String respostaMaq = (_modbus.InformCLP() == _modbus.StatusCLP()/2)?
                    "Serviço Falhou":(_modbus.StatusCLP() == (2*_modbus.InformCLP())-1)?
                    "Serviço Concluido com Sucesso":"Nada";
            setRespostaMaquina(respostaMaq);
            
            
            setFimExecucaoMaquina(true);// indica que a execução da máquina chegou ao fim          
            //com a mensagem de ESTADO
        }           
    }//final da thread da máquina
}//final da classe AgenteMaquina

