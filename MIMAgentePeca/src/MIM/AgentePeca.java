/* AgentePeca que provoca o gerenciamento da manufatura pela Peça
 * Implementado por João Alvarez Peixoto
 */
package MIM;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class AgentePeca extends Agent implements InterfaceAgenteForm {

    Boolean TreadAlive = false;
    InterfaceAgenteForm myInterface;
    FormAgentePeca myForm = new FormAgentePeca(this);
    int _local = 5;
    ArrayList<String> _servicesNeeded = new ArrayList<String>() {
        {
                add("Storage4GB");
                add("Storage8GB");
                add("Storage16GB");
                add("Storage32GB");
                add("Storage64GB");
                add("Storage128GB");
        }
    };

    protected void setup() {
        System.out.println("Oi, sou agente " + getLocalName());
        myForm.setTitle("Agente " + getAID().getLocalName());
        myForm.setVisible(true);
        myForm.serviçosPeca = _servicesNeeded;
        myForm.RealoadServiçes();
    }

    protected void takeDown() {
        System.out.println("Agente " + getLocalName() + " Encerrado");
        
        System.exit(0);//comando para fechar o programa
    }

    public void botaoSair() {
        TreadAlive =false;
        this.doWait(10000);
        myForm.dispose();
        doDelete();//chama o método takedown
    }

    public void iniciarProducao() {
        myForm.limparTexto();//para limpar o quadro de texto
        solicitadorServicos solicitador = new solicitadorServicos();
        addBehaviour(new solicitadorServicos());//executa o comportamento que realizará as negociações
    }  

public ArrayList<AID> buscarAgentes(Agent agentePedindo, String nomeServico, String tipoServico) {
        ArrayList<AID> aids = new ArrayList<>();
        DFAgentDescription agentDescription = new DFAgentDescription();
        ServiceDescription serviceDescription = new ServiceDescription();

        serviceDescription.setType(tipoServico);
        serviceDescription.setName(nomeServico);
        agentDescription.addServices(serviceDescription);

        try {
            for (DFAgentDescription agent : DFService.search(agentePedindo, agentDescription)) {
                aids.add(agent.getName());
            }
        } catch (FIPAException ex) {
            aids = null;
            System.out.println("Erro de busca");
        }
        return aids;
        
    }
    
    public class solicitadorServicos extends Behaviour {

        int passo = 1;//indica os passos da máquina de estados
        boolean PecaPronta = false; // com TRUE indica que a manufatura acabou
        
        ArrayList<AID> AgentesFazManufatura = new ArrayList<>();// Vetor com os agentes que fazem o serviço desejado
        ArrayList<AID> AgentesPropoeManufatura = new ArrayList<>();
        ArrayList<String> AgentesPropoeManufaturaValor = new ArrayList<>();
        AID AgenteVencedorManufatura = null;//agente que vence manufatura por apresentar o menor custo
        ArrayList<AID> AgentesPerdedoresManufatura = new ArrayList<>();//agentes que perderam a negociação para Manufatura
        int TentativaManufatura = 0; // indica as tentativas para negociar manufatura
        private MessageTemplate _messageTemplate; // Template que recebe os agentes com suas propostas
        String ServicoPeca = _servicesNeeded.get(0);
        int _locationVencedor;
        
        
       public void SendMessageToFormAndMensageiro(String msg){
            this.SendMessageToForm(msg);
            this.SendMessageToMensageiro(msg);
        }
        public void SendMessageToForm(String msg){
         myForm.atualizarTexto(msg);
        }
        
        public void SendMessageToMensageiro(String msg){
            ArrayList<AID> Mensageiros = buscarAgentes(myAgent, "Mensagem Peça", "Mensageiro");
            SendInform(Mensageiros, "Mensageiro", "Mensagem Peça", msg);
        }
        
        public void SendInform(ArrayList<AID> AgentsThatDo,String serviço,String ConversationID, String Content){
            SendMessage(AgentsThatDo, serviço, ConversationID,  Content, ACLMessage.INFORM);
        }
        
        public void SetLocation(int local){
            ArrayList<AID> AgentesEsteiras = buscarAgentes(myAgent, "Local Peça", "Local");
            SendCFP(AgentesEsteiras, "Local", "Local Peça", Integer.toString(local));
            ReloadLocal();        
        } 
    
        public void RemoveLocation(){
            ArrayList<AID> AgentesEsteiras = buscarAgentes(myAgent, "Local Peça", "Local");
            SendCancel(AgentesEsteiras, "Local", "Local Peça");        
        }
        
        public int SendCFP(ArrayList<AID> AgentsThatDo,String serviço,String ConversationID, String Content ){
               return SendMessage(AgentsThatDo, serviço, ConversationID,  Content, ACLMessage.CFP );            
        }
        
        public int SendCancel (ArrayList<AID> AgentsThatDo,String serviço,String ConversationID ){
               return SendMessage(AgentsThatDo, serviço, ConversationID,  "", ACLMessage.CANCEL );            
        }
        public int SendMessage(ArrayList<AID> AgentsThatDo,String serviço,String ConversationID, String Content, int messageType ){
            if (!AgentsThatDo.isEmpty()) {
                    ACLMessage cfpManufatura = new ACLMessage(messageType);
                    for (AID AgenteFazManufatura : AgentsThatDo) {
                        SendMessageToForm("Agente " + AgenteFazManufatura.getLocalName() + " faz");
                        cfpManufatura.addReceiver(AgenteFazManufatura);// carrega o Agentes que receberão o CFP
                    }
                    cfpManufatura.setContent(serviço + (serviço.equals("Local")? "":" ") + Content);//colocar a tarefa e o critério (preco / distancia)
                    cfpManufatura.setConversationId(ConversationID);//carrega o tipo de manufatura
                    cfpManufatura.setReplyWith("Local Peça");//habilida o recebimento de propostas
                    cfpManufatura.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);//carrega o protocolo ContractNet
                    myAgent.send(cfpManufatura);// envia os CFP aos agentes
                            // Prepara para receber a resposta do participante [PROPOSE ou REFUSE]
                    SendMessageToForm("Enviando CFP...");
                    return 3;       
                } 
                else {
                    TentativaManufatura++; //incrementa a tentativa de busca para agente de manufatura
                    SendMessageToFormAndMensageiro("Tentativa " + TentativaManufatura);
                    //quando for implemena a troca de serviços deve ser colocado aqui
                    return 2;
                }
            }
            public int GetLocalOnMessage(String message){
                final Pattern pattern = Pattern.compile("\\d+"); // the regex
                final Matcher matcher = pattern.matcher(message); // your string

                final ArrayList<Integer> ints = new ArrayList<>(); // results

                while (matcher.find()) { // for each match
                    ints.add(Integer.parseInt(matcher.group())); // convert to int
                }
                return ints.get(0);
        }
            
        @Override
        public void action() {
            ACLMessage mensagem = null;//lê a mensagem de retorno
            switch (passo) {
                case 1: //verifica o serviço necessário
                    SetLocation(_local);
                    ServicoPeca = _servicesNeeded.get(0);
                    SendMessageToFormAndMensageiro("Necessito serviço " + ServicoPeca);
                    TentativaManufatura = 0;//para zerar as tentativas de busca de agentes no DF
                    passo = 2;
                case 2: //busca no DF os agentes que podem fazer a tarefa
                    SendMessageToFormAndMensageiro("Buscando Agentes que Fazem...");
                    AgentesFazManufatura = buscarAgentes(myAgent, ServicoPeca, "Manufatura");//faz uma nova busca por novo agente
                    passo = SendCFP(AgentesFazManufatura, ServicoPeca,"Manufatura","preço");
                    break;
                case 3: // envia o CFP aos agentes máquina que podem fazer o serviço
                    AgentesPropoeManufatura = new ArrayList<>();
                    AgentesPropoeManufaturaValor = new ArrayList<>();
                    AgenteVencedorManufatura = null;//para zerar o agente;
                    passo = 4;
                    break;
                case 4: //Recebe todas as propostas dos agentes de manufatura [PROPOSE ou REFUSE] e se não forem respondidas em 5 segundos quebra loop
                    int custoVencedor = 0;
                    Date datestart = new Date();
                    long start = datestart.getTime();
                    int respostasRestantes = AgentesFazManufatura.size();
                    
                    while(respostasRestantes > 0){
                        Date datenow = new Date();
                        long now = datenow.getTime();
                        mensagem = myAgent.receive(MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.PROPOSE),MessageTemplate.MatchPerformative(ACLMessage.REFUSE)));
                        if(now-start > 5000){
                           break;
                        }
                        if(mensagem != null)
                            
                        {                            
                            if(mensagem.getPerformative()==ACLMessage.PROPOSE){
                                int custoManufatura = Integer.parseInt(mensagem.getContent());
                                AgentesPropoeManufatura.add(mensagem.getSender());                                
                                AgentesPropoeManufaturaValor.add(mensagem.getContent());
                                SendMessageToFormAndMensageiro("Agente: " + mensagem.getSender().getLocalName() + (" propos " + mensagem.getContent()));
                                if( AgenteVencedorManufatura == null || custoManufatura < custoVencedor ){
                                    AgenteVencedorManufatura = mensagem.getSender();
                                    custoVencedor = custoManufatura;
                                    _locationVencedor = GetLocalOnMessage(mensagem.getUserDefinedParameter("Location"));    //assume o custoVencedorM como sendo o deste agente que propos
                                }  
                                --respostasRestantes;
                            }
                            else if(mensagem.getPerformative() == ACLMessage.REFUSE){
                                SendMessageToFormAndMensageiro("Agente: " + mensagem.getSender().getLocalName() + (" propos " + mensagem.getContent()));
                                --respostasRestantes;
                            }
                        }   
                    }
                    if(AgenteVencedorManufatura == null){
                        passo = 1;
                        _servicesNeeded.add(ServicoPeca);
                        _servicesNeeded.remove(0);
                    }
                    else if(AgenteVencedorManufatura != null){
                        int index = AgentesPropoeManufatura.indexOf(AgenteVencedorManufatura);
                        AgentesPropoeManufatura.remove(index);
                        AgentesPropoeManufaturaValor.remove(index);
                        passo = 5;    
                    }
                    break;
                case 5: //Envia aos agentes maquina o ACCEPT_PROPOSAL e REJECT_PROPOSAL                    
                    ACLMessage VencedorManufatura = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                    VencedorManufatura.addReceiver(AgenteVencedorManufatura);
                    VencedorManufatura.setContent("Aceito proposta");
                    VencedorManufatura.setConversationId("Manufatura");//tipo de serviço
                    myAgent.send(VencedorManufatura);
                    SendMessageToFormAndMensageiro("Agente Vencedor " + AgenteVencedorManufatura.getLocalName());
                    ACLMessage PerdedoresManufatura = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
                    PerdedoresManufatura.setPerformative(ACLMessage.REJECT_PROPOSAL);
                    PerdedoresManufatura.setContent("Rejeitado");
                    PerdedoresManufatura.setConversationId("Manufatura");//tipo de serviço
                    AgentesPerdedoresManufatura = AgentesPropoeManufatura;
                    AgentesPerdedoresManufatura.remove(AgenteVencedorManufatura);
                    for (int i = 0; i < AgentesPerdedoresManufatura.size(); i++) {
                        SendMessageToFormAndMensageiro("Agente Perdedor " + AgentesPerdedoresManufatura.get(i).getLocalName());
                        PerdedoresManufatura.addReceiver(AgentesPerdedoresManufatura.get(i));
                    }
                    myAgent.send(PerdedoresManufatura);
                    passo = 6;
                    break;
                case 6: //Envia um REQUEST ao agente vencedor
                    if(_locationVencedor==_local){
                        SendMessageToFormAndMensageiro("Peça Chegou a Maquina, avisando máquina " + AgenteVencedorManufatura.getLocalName());
                        RemoveLocation();
                        ACLMessage msgReqManufatura = new ACLMessage(ACLMessage.REQUEST);// configura uma mensagem de REQUEST
                        msgReqManufatura.addReceiver(AgenteVencedorManufatura);// configura o destinatário
                        msgReqManufatura.setContent(ServicoPeca);//indica o serviço a ser executado
                        myAgent.send(msgReqManufatura);//envio da mensagem de REQUEST 
                        SendMessageToFormAndMensageiro("Serviço para  " + AgenteVencedorManufatura.getLocalName());
                        passo = 7;
                    }
                    break;
                case 7: //Receber o sinal de final de processo INFORM(Estado) da máquina ou um FAILURE
                    mensagem = myAgent.receive();
                    if (mensagem != null) {
                        if (mensagem.getPerformative() == ACLMessage.INFORM) {
                            SendMessageToFormAndMensageiro("Agente " + mensagem.getSender().getLocalName() + " disse " + mensagem.getContent());
                            //JOptionPane.showMessageDialog(null, "Manufatura "+ mensagem.getContent(), "Peca" + getAID().getLocalName(), JOptionPane.INFORMATION_MESSAGE);                   
                            SetLocation(_local);
                            passo = 8;//fim da tarefa
                        }
                        if (mensagem.getPerformative() == ACLMessage.FAILURE) {
                            SendMessageToFormAndMensageiro("Agente " + mensagem.getSender().getLocalName() + " disse " + mensagem.getContent());
                            //JOptionPane.showMessageDialog(null, "Manufatura "+ mensagem.getContent(), "Peca" + getAID().getLocalName(), JOptionPane.INFORMATION_MESSAGE);            
                           SetLocation(_local); 
                            passo = 8;//fim da tarefa, verificar se há mais tarefa na lista
                        }
                    }
                    break;
                case 8: //último passo, tarefa concluída.                  
                    SendMessageToFormAndMensageiro("Manufatura Peça encerrada");
                    _servicesNeeded.remove(0);
                    myForm.RealoadServiçes();
                    PecaPronta = true;//com true = indica que a peça acabou sua manufatura                   
                    break;
            }
            //bloqueia a varredura por 500ms ou quando chegar uma nova mensagem
            if (!_servicesNeeded.isEmpty() && done()) {
                iniciarProducao();
            }
        }//fim do Action
        
        public void ReloadLocal(){
            TreadAlive = true;
            Thread reload = new ReloadLocal();
            reload.start();
        }
        private class ReloadLocal extends Thread{
        
            public ReloadLocal(){}
            

            @Override
            public void run(){
                while(TreadAlive){
                    ACLMessage msg  = myAgent.receive(MessageTemplate.MatchConversationId("Local Peça"));
                    if(msg!=null){
                        if("Local Peça".equals(msg.getConversationId())){
                            _local = GetLocalOnMessage(msg.getContent()); 
                            myForm.ReloadPosição(msg.getContent());
                        }
                    }                    
                }
                RemoveLocation();
            }   
    }
        @Override
        public boolean done() {
            return PecaPronta; //com fim da peça em true encerra o comportamento
        }//fim do done
    }//fim do comportamento 
}//final da classe AgentePeca

