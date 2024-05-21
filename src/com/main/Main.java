package com.main;

import com.event.EventMenu;
import com.form.Home;
import com.form.Other;
import com.form.PumpRelese;
import com.form.PumperDetails;
import com.form.Register;
import com.form.Reports;
import com.model.UserDetails;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JOptionPane;


public class Main extends javax.swing.JFrame {
    
    public Main(UserDetails userDetails) {
        initComponents();
        setBackground(new Color(0, 0, 0, 0));
        EventMenu event = new EventMenu() {
            @Override
            public void selected(int index) {
                if (index == 0) {
                    showForm(new Home());
                } else if (index == 1) {
                    showForm(new PumpRelese());
                } else if (index == 2) {
                    showForm(new Register());
                } else if (index == 3) {
                    showForm(new PumperDetails());
                } else if (index == 4) {
                    showForm(new Other());
                }else if (index == 5) {
                    showForm(new Reports());
                } else if (index == 8) {
                    int selectOption=JOptionPane.showConfirmDialog(rootPane,"Are you sure about logging out from the system?","Confirm",JOptionPane.YES_NO_OPTION);
                    if(selectOption == JOptionPane.YES_OPTION){
                        System.exit(0);;
                    }   
                }
            }
        };
        menu2.initMenu(event);
        showForm(new Home());
        roundPanel1.setOpaque(true);
        user.loadUserDetails(userDetails.getName());
    }
    
    private void showForm(Component com) {
        body.removeAll();
        body.add(com);
        body.revalidate();
        body.repaint();
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundPanel1 = new com.swing.RoundPanel();
        body = new javax.swing.JPanel();
        menu2 = new com.component.Menu();
        user = new com.component.User();
        roundPanel2 = new com.swing.RoundPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        roundPanel1.setBackground(new java.awt.Color(25, 25, 25));

        body.setOpaque(false);
        body.setLayout(new java.awt.BorderLayout());

        user.setName("Charuka");

        roundPanel2.setBackground(new java.awt.Color(51, 51, 51));
        roundPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Quicksand", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Fuel Station");
        jLabel1.setAlignmentX(0.5F);
        roundPanel2.add(jLabel1, new java.awt.GridBagConstraints());

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(menu2, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                    .addComponent(user, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, 942, Short.MAX_VALUE)
                .addContainerGap())
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addComponent(roundPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(menu2, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    private javax.swing.JLabel jLabel1;
    private com.component.Menu menu2;
    private com.swing.RoundPanel roundPanel1;
    private com.swing.RoundPanel roundPanel2;
    private com.component.User user;
    // End of variables declaration//GEN-END:variables
}
