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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class AgenteEsteira extends Agent implements InterfaceAgenteForm {
    
    ArrayList<AID> _agentesMaquina = new ArrayList<>();
    ArrayList<Integer> _localAgentesMaquina = new ArrayList<>();
    ArrayList<AID> _agentesPeça = new ArrayList<>();
    ArrayList<Integer> _localAgentesPeça = new ArrayList<>();
    Boolean _esteiraLigada = false;
    FormAgenteEsteira myForm = new FormAgenteEsteira(this);
    
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
    
    public void retirarPostagem(Agent ag){
        try { DFService.deregister(ag); }
        catch (FIPAException fe) { fe.printStackTrace(); }
    }

    
    public void postarServico(ArrayList<String> habilidades){
        retirarPostagem(this);
        if(!this.GetIsLocalPosted()){//verifica se o serviço já foi postado
            DFAgentDescription dfd = new DFAgentDescription();
            dfd.setName(this.getAID());
            for(String habilidade : habilidades) {
                ServiceDescription sd = new ServiceDescription();
                sd.setType("Local");
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
        return _localAgentesPeça.get(_agentesPeça.indexOf(agente));
    }
    
    
    protected void setup() {
        System.out.println("Oi, sou agente " + getLocalName());
        _esteiraLigada = true;
        myForm.setVisible(true);
        postarServico(_esteiraServicesArray);
        iniciarProducao();
        
    }
    
    public void iniciarProducao() {
        addBehaviour(new Esteira());//executa o comportamento que realizará as negociações
    }
    protected void takeDown() {
        System.out.println("Agente " + getLocalName() + " Encerrado");
        System.exit(0);//comando para fechar o programa
    }

    @Override
    public void botaoSair() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public class Esteira extends Behaviour {
        
        public Esteira(){
        
        }
        
        public Integer GetLocalOnMessage(String message){
            final Pattern pattern = Pattern.compile("\\d+"); // the regex
            final Matcher matcher = pattern.matcher(message); // your string

            final ArrayList<Integer> ints = new ArrayList<>(); // results

            while (matcher.find()) { // for each match
                ints.add(Integer.parseInt(matcher.group())); // convert to int
            }
            return ints.get(0);
        }
        
        long TempoCiclo = new Date().getTime();
        
        int _posiçãoEsteira;
        @Override
        public void action() {
            while(true){
                
            ACLMessage mensagem = myAgent.receive();
            if(mensagem != null ){
                if((mensagem.getPerformative() == ACLMessage.CFP)){
                    if(mensagem.getConversationId().equalsIgnoreCase("Local Maquina")){
                        if(!_agentesMaquina.contains(mensagem.getSender())){
                            _agentesMaquina.add(mensagem.getSender());
                            _localAgentesMaquina.add(GetLocalOnMessage(mensagem.getContent()));
                        }
                    }
                    else if(mensagem.getConversationId().equalsIgnoreCase("Local Peça")){
                            if(!_agentesPeça.contains(mensagem.getSender())){
                                _agentesPeça.add(mensagem.getSender());
                                _localAgentesPeça.add(GetLocalOnMessage(mensagem.getContent()));
                                myForm.CreatePeça(mensagem.getSender().getLocalName());
                            }
                        }
                    }                
                if(mensagem.getPerformative() == ACLMessage.CANCEL){
                    if(mensagem.getConversationId().equalsIgnoreCase("Local Maquina")){
                        if(_agentesMaquina.contains(mensagem.getSender())){
                            int index = _agentesMaquina.indexOf(mensagem.getSender());
                            _agentesMaquina.remove(index);
                            _localAgentesMaquina.remove(index);
                        }
                    }
                    else if(mensagem.getConversationId().equalsIgnoreCase("Local Peça")){
                        if(!_agentesPeça.contains(mensagem.getSender())){
                            int index = _agentesMaquina.indexOf(mensagem.getSender());
                            _agentesMaquina.remove(index);
                            _localAgentesMaquina.remove(index);
                            myForm.RemovePeça(mensagem.getSender().getLocalName());
                        }
                    }
                }
            }             
            
            if(new Date().getTime() - TempoCiclo > 2000){
                TempoCiclo = new Date().getTime();
                if(!_agentesPeça.isEmpty()){
                    
                for (int i = 0; i < _agentesPeça.size(); i++) {
                    Integer value  = _localAgentesPeça.get(i);
                    
                    if(_localAgentesPeça.get(i) < 100){
                    value++;
                    }
                    else{
                        value=0;
                    } 
                    _localAgentesPeça.add(i, value);
                    ACLMessage sender = new ACLMessage();
                    sender.addReceiver(_agentesPeça.get(i));
                    sender.setContent(Integer.toString(_localAgentesPeça.get(i)));
                    sender.setConversationId("Local Peça");
                    myAgent.send(sender);
                    SetLocationPeça(_agentesPeça.get(i).getLocalName(),value);
                }  
                }
            }
            }    
        }

        @Override
        public boolean done() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

        public void SetLocationPeça(String Agentes, int local){
            
            int x=0;
            int y=0;
            
            if(local<25){
                y = 05;
                x = 500 - local*20;               
            }
            if(25<=local && local<50){
                x = 05;
                y = 100 + local*20;               
            }
            if(50<=local && local<75){
                y = 455;
                x = 100 + local*20;               
            }
            if(75<=local){
                x = 455;
                y = 500 - local*20;               
            }
            
            myForm.RealodLocationPeça(Agentes, x, y);
        }
        public boolean done() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
}