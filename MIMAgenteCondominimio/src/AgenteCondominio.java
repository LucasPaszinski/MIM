
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class AgenteCondominio extends Agent implements InterfaceAgenteCondominio{
    
    InterfaceAgenteCondominio myInterface;
    FormAgenteCondominio myForm = new FormAgenteCondominio();
    //atributos
    public String MensagemPeca;
    public String MensagemMaquina;
    
    
    
    protected void Setup(){
        System.out.println("Oi, sou agente "+ getLocalName());
        myForm.setTitle("Agente " + getAID().getLocalName());
        myForm.setVisible(true);//para ativar o form
    }
    protected void takeDown() {
        System.out.println("Agente "+ getLocalName()+" Encerrado");      
        //System.exit(0);//comando para fechar o programa- retira para evitar
        //que ao fechar um programa feche os demais
    }
    @Override
    public void botaoSair(){  
        myForm.dispose();//para fechar o form
        doDelete();//chama o método takedown
    }
    
    public AgenteCondominio() { 
        //METODO CONSTRUTOR
    }
    
    public String getMensangemPeca() {
        return MensagemPeca;
    }
    
    public void setMensangemPeca(String MP) {
        this.MensagemPeca = MP;
    }
    
    public String getMensagemMaquina() {
        return MensagemMaquina;
    }
    
    public void setMensagemMaquina(String MensagemMaquina) {
        this.MensagemMaquina = MensagemMaquina;
    }
    
    
    public void ReceberPeca(){
        //RECEBE AS MENSAGENS DO AGENTE PEÇA
        DFAgentDescription dfd = new DFAgentDescription();
        ACLMessage mensagem = this.receive();//lê a mensage
        MensagemPeca = "";
        
    }
    public void ReceberMaquina(){
        //RECEBE AS MENSAGENS DO AGENTE MAQUINA
        DFAgentDescription dfd = new DFAgentDescription();
        ACLMessage mensagem = this.receive();//lê a mensage
         MensagemMaquina = "";
         if ("".equals(this.MensagemMaquina)){
             this.MensagemMaquina = "";
             
         }
    }
    
    public class Mensageiro extends CyclicBehaviour {
        
        public Mensageiro(){
        //CONTRUTOR pois é unico jeito de acessar os metodos do behaviour (java wtf ta no mesmo projeto)
        }
        @Override
        public void action() {
            ACLMessage msg = myAgent.receive();
            if(msg!=null){
                if(msg.getPerformative() == ACLMessage.INFORM){
                    if(msg.getConversationId()=="Mensagem Maquina"){
                        //envia as mensagens para o form
                    }
                    if(msg.getConversationId()=="Mensagem Peça"){
                        //envia as mensagens para o form 
                    }
                }
            }
                
                
                
        
                
                
                
                
        }
        
    }
    
}
