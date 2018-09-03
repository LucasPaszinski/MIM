package MIM;

import jade.core.AID;
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

public class AgenteEsteira extends Agent {
    
    ArrayList<AID> _agentesMaquina = new ArrayList<>();
    ArrayList<String> _localAgentesMaquina = new ArrayList<>();
    ArrayList<AID> _agentesPeça = new ArrayList<>();
    ArrayList<String> _localAgentesPeça = new ArrayList<>();
    Boolean _esteiraLigada = false;
    
    ArrayList<String> _esteiraServicesArray = new ArrayList<String>()
            {
                {
                add("Local Maquina");
                add("Local Peça");

                }
                
            };   
    private Boolean _isLocalPosted = false;
        
    public void SetIsLocalPosted(Boolean state){
        _isLocalPosted = state;
    }
    
    public Boolean GetIsLocalPosted(){
            return _isLocalPosted;
        }
     public void postarServico(ArrayList<String> habilidades){
        if(!this.GetIsLocalPosted()){//verifica se o serviço já foi postado
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
            this.SetIsLocalPosted(true);//para indicar que um serviço foi postado
        }else{
            JOptionPane.showMessageDialog(null, "Serviço já postado", "Máquina" + getAID().getLocalName(), JOptionPane.INFORMATION_MESSAGE);                              
        }
    }
    
    public int GetLocationPeça(AID agente)
    {
        return Integer.parseInt(_localAgentesPeça.get(_agentesPeça.indexOf(agente)));
    }
    
    protected void setup() {
        System.out.println("Oi, sou agente " + getLocalName());
        _esteiraLigada = true;
        iniciarProducao();
        
    }
    
    public void iniciarProducao() {
        addBehaviour(new Esteira());//executa o comportamento que realizará as negociações
    }
    protected void takeDown() {
        System.out.println("Agente " + getLocalName() + " Encerrado");
        System.exit(0);//comando para fechar o programa
    }

    public class Esteira extends Behaviour {
        
        long TempoCiclo = new Date().getTime();
        
        int _posiçãoEsteira;
        @Override
        public void action() {
            while(true){
                
            ACLMessage mensagem = myAgent.receive();
            if(mensagem != null){
                if(mensagem.getConversationId().equalsIgnoreCase("Local Maquina")){
                    if(!_agentesMaquina.contains(mensagem.getSender())){
                        _agentesMaquina.add(mensagem.getSender());
                        _localAgentesMaquina.add(String.format("{}",Integer.parseInt(mensagem.getContent())));
                    }
                }
                else if(mensagem.getConversationId().equalsIgnoreCase("Local Peça")){
                    if(!_agentesPeça.contains(mensagem.getSender())){
                        _agentesPeça.add(mensagem.getSender());
                        _localAgentesPeça.add(String.format("{}",Integer.parseInt(mensagem.getContent())));
                    }
                }
                else{
                    myAgent.putBack(mensagem);
                }
                
            }
            if(new Date().getTime() - TempoCiclo > 2000){
                TempoCiclo = new Date().getTime();
                for (int i = 0; i < _agentesPeça.size(); i++) {
                    String value  = String.format("{0}",Integer.parseInt(_localAgentesPeça.get(i))+1);
                    _localAgentesPeça.add(i, value);
                    ACLMessage sender = new ACLMessage(ACLMessage.INFORM);
                    sender.addReceiver(_agentesPeça.get(i));
                    sender.setContent(_localAgentesPeça.get(i));
                    sender.setConversationId("Local");
                    myAgent.send(sender);
                }   
            }
            }    
        }
       
        @Override
        public boolean done() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
