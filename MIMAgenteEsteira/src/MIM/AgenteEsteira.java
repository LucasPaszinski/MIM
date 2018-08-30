/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
//import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

/**
 *
 * @author Lucas
 */
public class AgenteEsteira extends Agent {
    
    ArrayList<AID> _agentesMaquina = new ArrayList<>();
    ArrayList<String> _localAgentesMaquina = new ArrayList<>();
    ArrayList<AID> _agentesPeça = new ArrayList<>();
    ArrayList<String> _localAgentesPeça = new ArrayList<>();
    
    Boolean _esteiraLigada = false;
    
    protected void setup() {
        System.out.println("Oi, sou agente " + getLocalName());
        _esteiraLigada = true;
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
            ACLMessage mensagem = myAgent.receive();
            if(mensagem != null){
                if(mensagem.getConversationId().equalsIgnoreCase("Local Maquina")){
                    if(!_agentesMaquina.contains(mensagem.getSender())){
                        _agentesMaquina.add(mensagem.getSender());
                        _localAgentesMaquina.add(String.format("{}",Integer.parseInt(mensagem.getContent())));
                    }
                }
                if(mensagem.getConversationId().equalsIgnoreCase("Local Peça")){
                    if(!_agentesPeça.contains(mensagem.getSender())){
                        _agentesPeça.add(mensagem.getSender());
                        _localAgentesPeça.add(String.format("{}",Integer.parseInt(mensagem.getContent())));
                    }
                }
            }
            if(new Date().getTime() - TempoCiclo > 2000){
                TempoCiclo = new Date().getTime();
                for (int i = 0; i < _agentesPeça.size(); i++) {
                    String value  = String.format("{0}",Integer.parseInt(_localAgentesPeça.get(i))+1);
                    _localAgentesPeça.add(i, value);
                }   
            }
            
        }

        @Override
        public boolean done() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    
}
//
    
}
