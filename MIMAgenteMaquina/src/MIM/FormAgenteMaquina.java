package MIM;

import java.util.ArrayList;

public class FormAgenteMaquina extends javax.swing.JFrame {
    
    private InterfaceAgenteForm myInterface;
    private ModbusRTU _modbus = new ModbusRTU();
    
    public FormAgenteMaquina(InterfaceAgenteForm myNewInterface) {
        initComponents();
        this.myInterface = myNewInterface;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnSair = new javax.swing.JToggleButton();
        jLabel11 = new javax.swing.JLabel();
        txtServicoMaquina1 = new javax.swing.JTextField();
        btnPostagem = new javax.swing.JButton();
        lblSinaleira = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtStatusM = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtServicos = new javax.swing.JTextArea();
        btnAddServico = new javax.swing.JToggleButton();
        btnDelServico = new javax.swing.JToggleButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtModbus = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        jLabel11.setText("Serviços da Máquina:");

        txtServicoMaquina1.setText("Montagem");

        btnPostagem.setText("Postagem");
        btnPostagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPostagemActionPerformed(evt);
            }
        });

        lblSinaleira.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/parado.jpg"))); // NOI18N

        txtStatusM.setColumns(20);
        txtStatusM.setRows(5);
        txtStatusM.setPreferredSize(new java.awt.Dimension(94, 90));
        txtStatusM.setRequestFocusEnabled(false);
        jScrollPane1.setViewportView(txtStatusM);

        txtServicos.setColumns(20);
        txtServicos.setRows(5);
        jScrollPane2.setViewportView(txtServicos);

        btnAddServico.setText("Adicionar Serviço");
        btnAddServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddServicoActionPerformed(evt);
            }
        });

        btnDelServico.setText("Deletar Serviço");
        btnDelServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelServicoActionPerformed(evt);
            }
        });

        txtModbus.setColumns(20);
        txtModbus.setRows(5);
        jScrollPane3.setViewportView(txtModbus);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblSinaleira)
                        .addGap(18, 18, 18)
                        .addComponent(btnPostagem))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnAddServico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDelServico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtServicoMaquina1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(btnSair))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtServicoMaquina1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddServico)
                        .addGap(3, 3, 3)
                        .addComponent(btnDelServico)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnPostagem, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblSinaleira, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public void atualizarTexto(String ac){
        txtStatusM.setText(txtStatusM.getText()+"\n"+ac);
    }
    public void limparTexto(){
        txtStatusM.setText("");
    }
    ArrayList<String> servicoMaquina = new ArrayList<String>();
    public void setSinaleira(boolean sinal){//true=livre (Vr) , false = ocupada (Vm)
        if(sinal){
            lblSinaleira.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/vrSinaleira.jpg")));
        }else{
            lblSinaleira.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/vmSinaleira.jpg")));
        }
    } 
        public void RealoadServiçes(){
            this.txtServicos.setText("");
            int i=0;
            for(String servico:this.servicoMaquina){
                i+=1;
                this.txtServicos.append(i+": "+servico +"\n");  
            }
        }
        public void RealoadModbus(int _run, int _done, int _inform, int _status){
            this.txtModbus.setText("");
            this.txtModbus.append("RunCLP(): "+_run+"\n");
            this.txtModbus.append("DoneCLP(): "+ _done+"\n");
            this.txtModbus.append("InformCLP(): "+ _inform+"\n");
            this.txtModbus.append("StatusCLP(): "+ _status+"\n");
        }
                public void ServiceStatus(String _informName,int _inform, int _status){
            String informStatus = (_status==2*_inform)?" falhou.":" concluiu";
            this.txtStatusM.append("\n\nO Serviço "+_informName+informStatus+"\n\n");

        }
        public void SetServiceArray(ArrayList<String> machineServices)
        {
            servicoMaquina = machineServices;
        }
    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        this.myInterface.botaoSair();
    }//GEN-LAST:event_btnSairActionPerformed
    private void btnPostagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPostagemActionPerformed
        this.myInterface.postarServico(servicoMaquina);
    }//GEN-LAST:event_btnPostagemActionPerformed

    private void btnDelServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelServicoActionPerformed
        // TODO add your handling code here:
        if(this.servicoMaquina.contains(this.txtServicoMaquina1.getText()))this.servicoMaquina.remove(this.txtServicoMaquina1.getText());
        RealoadServiçes();
    }//GEN-LAST:event_btnDelServicoActionPerformed

    private void btnAddServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddServicoActionPerformed
        // TODO add your handling code here:
        if(!this.servicoMaquina.contains(this.txtServicoMaquina1.getText()))this.servicoMaquina.add(this.txtServicoMaquina1.getText());
        RealoadServiçes();
    }//GEN-LAST:event_btnAddServicoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormAgenteMaquina.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormAgenteMaquina.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormAgenteMaquina.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormAgenteMaquina.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            //new FormAgenteMaquina().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnAddServico;
    private javax.swing.JToggleButton btnDelServico;
    private javax.swing.JButton btnPostagem;
    private javax.swing.JToggleButton btnSair;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblSinaleira;
    private javax.swing.JTextArea txtModbus;
    private javax.swing.JTextField txtServicoMaquina1;
    private javax.swing.JTextArea txtServicos;
    private javax.swing.JTextArea txtStatusM;
    // End of variables declaration//GEN-END:variables
}
