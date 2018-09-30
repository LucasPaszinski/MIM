package MIM;


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
    FormAgenteCondominio myForm = new FormAgenteCondominio(this);
   
    //atributos
    String MensagemPeca;
    String MensagemMaquina;
    Mensageiro Mensageiro = new Mensageiro();
    
    
    protected void setup(){
        System.out.println("Oi, sou agente "+ getLocalName());
        myForm.setVisible(true);
        myForm.setTitle("Agente " + getAID().getLocalName());
        myForm.AddMessagePeça("Estou Vivo, teste inicializado", "Agente Peça");
        myForm.AddMessageMaquina("Estou Vivo teste sucesso", "Agente Maquina");
        postarServico(_MensageiroServicesArray); // posta que relealiza o serviço do tipo mensageiro e as atividade mensagem maquina e peça
        addBehaviour(Mensageiro); // inicia o comportamento Mensageiro
        //para ativar o form
    }
    @Override
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
    
   
        
    ArrayList<String> _MensageiroServicesArray = new ArrayList<String>()
    {
        {
            add("Mensagem Maquina");
            add("Mensagem Peça");
        }
    }; 
    private Boolean _isMensageiroPosted = false;
        
    public void SetIsLocalPosted(Boolean state){
        _isMensageiroPosted = state;
    }
    
    public Boolean GetIsLocalPosted(){
            return _isMensageiroPosted;
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
                sd.setType("Mensageiro");
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
    //endregion 
    public AgenteCondominio() { 
        //METODO CONSTRUTOR
    }
    
    public String getMensangemPeca() {
        return MensagemPeca;
    }
    
    public void setMensangemPeca(String MensagemPeça) {
        this.MensagemPeca = MensagemPeça;
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
                    if(msg.getConversationId().equalsIgnoreCase("Mensagem Maquina")){
                        myForm.AddMessageMaquina(msg.getContent(), msg.getSender().getLocalName());
                    }
                    if(msg.getConversationId().equalsIgnoreCase("Mensagem Peça")){
                        myForm.AddMessagePeça(msg.getContent(), msg.getSender().getLocalName());
                    }
                }
            }             
        }
        
    }
    
}
