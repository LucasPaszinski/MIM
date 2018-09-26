
import jade.core.Agent;
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
    public String MensangemPeca;
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
        return MensangemPeca;
    }
    
    public void setMensangemPeca(String MP) {
        this.MensangemPeca = MP;
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
        MensangemPeca = "";
        
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
}
