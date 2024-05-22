package com.main;

import com.form.Home;
import com.form.ImportingAndReleasing;
import com.form.PumperDetails;
import com.form.Register;
import com.form.Reports;
import com.model.UserDetails;
import com.swing.ButtonMenu;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JOptionPane;

public class Main extends javax.swing.JFrame {

    public Main(UserDetails userDetails) {
        initComponents();
        setBackground(new Color(0, 0, 0, 0));
        roundPanel1.setOpaque(true);
        user.loadUserDetails(userDetails.getName());
        showForm(new Home());
        setSelected(buttonMenu2);
    }

    private void showForm(Component com) {
        body.removeAll();
        body.add(com);
        body.revalidate();
        body.repaint();
    }
    
    private void setSelected(ButtonMenu menu) {
        for (Component com : roundPanel3.getComponents()) {
            if (com instanceof ButtonMenu) {
                ButtonMenu b = (ButtonMenu) com;
                b.setSelected(false);
            }
        }
        menu.setSelected(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundPanel1 = new com.swing.RoundPanel();
        body = new javax.swing.JPanel();
        user = new com.frams.User();
        roundPanel2 = new com.swing.RoundPanel();
        jLabel1 = new javax.swing.JLabel();
        roundPanel3 = new com.swing.RoundPanel();
        buttonMenu2 = new com.swing.ButtonMenu();
        buttonMenu3 = new com.swing.ButtonMenu();
        buttonMenu4 = new com.swing.ButtonMenu();
        buttonMenu5 = new com.swing.ButtonMenu();
        buttonMenu7 = new com.swing.ButtonMenu();
        buttonMenu8 = new com.swing.ButtonMenu();

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

        roundPanel3.setBackground(new java.awt.Color(51, 51, 51));

        buttonMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icon/1.png"))); // NOI18N
        buttonMenu2.setText("   Dashboard");
        buttonMenu2.setFont(new java.awt.Font("Quicksand", 0, 12)); // NOI18N
        buttonMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMenu2ActionPerformed(evt);
            }
        });

        buttonMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icon/3.png"))); // NOI18N
        buttonMenu3.setText("   Registers");
        buttonMenu3.setFont(new java.awt.Font("Quicksand", 0, 12)); // NOI18N
        buttonMenu3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMenu3ActionPerformed(evt);
            }
        });

        buttonMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icon/2.png"))); // NOI18N
        buttonMenu4.setText("   Releasing And Importings");
        buttonMenu4.setFont(new java.awt.Font("Quicksand", 0, 12)); // NOI18N
        buttonMenu4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMenu4ActionPerformed(evt);
            }
        });

        buttonMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icon/4.png"))); // NOI18N
        buttonMenu5.setText("   Attendence And Salary");
        buttonMenu5.setFont(new java.awt.Font("Quicksand", 0, 12)); // NOI18N
        buttonMenu5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMenu5ActionPerformed(evt);
            }
        });

        buttonMenu7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icon/8.png"))); // NOI18N
        buttonMenu7.setText("   Reports");
        buttonMenu7.setFont(new java.awt.Font("Quicksand", 0, 12)); // NOI18N
        buttonMenu7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMenu7ActionPerformed(evt);
            }
        });

        buttonMenu8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icon/logout.png"))); // NOI18N
        buttonMenu8.setText("   Logout");
        buttonMenu8.setFont(new java.awt.Font("Quicksand", 0, 12)); // NOI18N
        buttonMenu8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMenu8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel3Layout = new javax.swing.GroupLayout(roundPanel3);
        roundPanel3.setLayout(roundPanel3Layout);
        roundPanel3Layout.setHorizontalGroup(
            roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonMenu2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonMenu3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonMenu4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                    .addComponent(buttonMenu5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonMenu7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonMenu8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        roundPanel3Layout.setVerticalGroup(
            roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonMenu2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonMenu4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonMenu3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonMenu5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonMenu7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 226, Short.MAX_VALUE)
                .addComponent(buttonMenu8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(user, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                    .addComponent(roundPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, 942, Short.MAX_VALUE)
                .addContainerGap())
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addComponent(roundPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(roundPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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

    private void buttonMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMenu2ActionPerformed
        showForm(new Home());
        setSelected(buttonMenu2);
    }//GEN-LAST:event_buttonMenu2ActionPerformed

    private void buttonMenu4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMenu4ActionPerformed
        showForm(new ImportingAndReleasing());
        setSelected(buttonMenu4);
    }//GEN-LAST:event_buttonMenu4ActionPerformed

    private void buttonMenu3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMenu3ActionPerformed
        showForm(new Register());
        setSelected(buttonMenu3);
    }//GEN-LAST:event_buttonMenu3ActionPerformed

    private void buttonMenu5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMenu5ActionPerformed
        showForm(new PumperDetails());
        setSelected(buttonMenu5);
    }//GEN-LAST:event_buttonMenu5ActionPerformed

    private void buttonMenu7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMenu7ActionPerformed
        showForm(new Reports());
        setSelected(buttonMenu7);
    }//GEN-LAST:event_buttonMenu7ActionPerformed

    private void buttonMenu8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMenu8ActionPerformed
        int selectOption = JOptionPane.showConfirmDialog(rootPane, "Are you sure about logging out from the system?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (selectOption == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_buttonMenu8ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    private com.swing.ButtonMenu buttonMenu2;
    private com.swing.ButtonMenu buttonMenu3;
    private com.swing.ButtonMenu buttonMenu4;
    private com.swing.ButtonMenu buttonMenu5;
    private com.swing.ButtonMenu buttonMenu7;
    private com.swing.ButtonMenu buttonMenu8;
    private javax.swing.JLabel jLabel1;
    private com.swing.RoundPanel roundPanel1;
    private com.swing.RoundPanel roundPanel2;
    private com.swing.RoundPanel roundPanel3;
    private com.frams.User user;
    // End of variables declaration//GEN-END:variables
}
