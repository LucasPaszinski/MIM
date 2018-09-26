package MIM;

import java.util.ArrayList;

public class FormAgentePeca extends javax.swing.JFrame {
    private final InterfaceAgenteForm myInterface;
    
    public FormAgentePeca(InterfaceAgenteForm myNewInterface) {
        initComponents();
        this.myInterface = myNewInterface;
    }
    ArrayList<String> serviçosPeca = new ArrayList<String>();

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnSair = new javax.swing.JToggleButton();
        jLabel7 = new javax.swing.JLabel();
        txtTarefaDesejada = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtStatus = new javax.swing.JTextArea();
        btnIniciar = new javax.swing.JButton();
        btnAddTarefa = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtTarefas = new javax.swing.JTextArea();
        btnRemoveTarefa = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtTarefas1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        jLabel7.setText("Tarefa Desejada:");

        txtTarefaDesejada.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTarefaDesejada.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtTarefaDesejada.setText("Montagem");
        txtTarefaDesejada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTarefaDesejadaActionPerformed(evt);
            }
        });

        txtStatus.setColumns(20);
        txtStatus.setRows(5);
        jScrollPane2.setViewportView(txtStatus);

        btnIniciar.setText("Iniciar");
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });

        btnAddTarefa.setText("Add Tarefa");
        btnAddTarefa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTarefaActionPerformed(evt);
            }
        });

        txtTarefas.setColumns(20);
        txtTarefas.setRows(5);
        jScrollPane1.setViewportView(txtTarefas);

        btnRemoveTarefa.setText("Remove Tarefa");
        btnRemoveTarefa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveTarefaActionPerformed(evt);
            }
        });

        txtTarefas1.setColumns(20);
        txtTarefas1.setRows(5);
        jScrollPane3.setViewportView(txtTarefas1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(txtTarefaDesejada)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRemoveTarefa, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                    .addComponent(btnAddTarefa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(btnSair)
                    .addComponent(btnIniciar))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(txtTarefaDesejada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddTarefa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoveTarefa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2)))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public void atualizarTexto(String msg){
        txtStatus.setText(txtStatus.getText()+"\n" + msg);
    }
    public void limparTexto(){
        txtStatus.setText("");
    }
    public String getServicos(){//retorna o serviço solicitado
        return this.txtTarefaDesejada.getText();
    }
    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        this.myInterface.botaoSair();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        this.myInterface.iniciarProducao(); //inicia a produção da peça
    }//GEN-LAST:event_btnIniciarActionPerformed

    private void txtTarefaDesejadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTarefaDesejadaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTarefaDesejadaActionPerformed

    private void btnAddTarefaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTarefaActionPerformed
        // TODO add your handling code here:
        if(!this.serviçosPeca.contains(this.txtTarefaDesejada.getText()))
        {
            this.serviçosPeca.add(this.txtTarefaDesejada.getText());
            this.RealoadServiçes();            
        }
        
        
        
        
    }//GEN-LAST:event_btnAddTarefaActionPerformed
    public void RealoadServiçes(){
            this.txtTarefas.setText("");
            int i=0;
            for(String servico:this.serviçosPeca){
                i+=1;
                this.txtTarefas.append(i+": "+servico +"\n");  
            }
            
        
    }
    public void ReloadPosição(String posição){
        this.txtTarefas1.setText("Posição Atual: "+posição);
    }
    private void btnRemoveTarefaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveTarefaActionPerformed
        // TODO add your handling code here:
        if(this.serviçosPeca.contains(this.txtTarefaDesejada.getText()))
        {
            this.serviçosPeca.remove(this.txtTarefaDesejada.getText());
            this.RealoadServiçes();
        }
    }//GEN-LAST:event_btnRemoveTarefaActionPerformed
    private void formWindowClosed(java.awt.event.WindowEvent evt) {                                  
        this.myInterface.botaoSair();
    }  
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormAgentePeca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormAgentePeca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormAgentePeca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormAgentePeca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
       
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new FormAgentePeca().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddTarefa;
    private javax.swing.JButton btnIniciar;
    private javax.swing.JButton btnRemoveTarefa;
    private javax.swing.JToggleButton btnSair;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea txtStatus;
    private javax.swing.JTextField txtTarefaDesejada;
    private javax.swing.JTextArea txtTarefas;
    private javax.swing.JTextArea txtTarefas1;
    // End of variables declaration//GEN-END:variables
}
