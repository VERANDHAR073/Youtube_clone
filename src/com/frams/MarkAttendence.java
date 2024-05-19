package com.frams;

import java.awt.Color;
import java.time.LocalDate;
import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.MySql;

public class MarkAttendence extends javax.swing.JPanel {

    private static HashMap<String, Integer> dateMap = new HashMap<>();
    private static HashMap<String, Integer> pumpersMap = new HashMap<>();
    private static HashMap<String, Integer> attendenceMap = new HashMap<>();

    private String pumperId;
    private String markId;

    public MarkAttendence() {
        initComponents();
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
        loadDate();
        loadEmployee();
        loadStatus();
        loadAttendenceDetails();
        jButton3.setVisible(false);
    }

    private void loadDate() {
        try {
            ResultSet resultSet = MySql.execute("SELECT * FROM `attendence_date` ORDER BY `date` DESC");
            Vector fv = new Vector();
            fv.add("SELECT");

            while (resultSet.next()) {
                fv.add(resultSet.getString("date"));
                dateMap.put(resultSet.getString("date"), resultSet.getInt("d_id"));
            }

            DefaultComboBoxModel cb = new DefaultComboBoxModel(fv);
            jComboBox1.setModel(cb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDate(String seDate) {
        try {
            ResultSet resultSet = MySql.execute("SELECT * FROM `attendence_date` ORDER BY `date` DESC");
            Vector fv = new Vector();
            fv.add("SELECT");

            while (resultSet.next()) {
                fv.add(resultSet.getString("date"));
                dateMap.put(resultSet.getString("date"), resultSet.getInt("d_id"));
            }

            DefaultComboBoxModel cb = new DefaultComboBoxModel(fv);
            jComboBox1.setModel(cb);
            jComboBox1.setSelectedItem(seDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadEmployee() {
        try {
            ResultSet resultSet = MySql.execute("SELECT * FROM `pumpers`");
            Vector fv = new Vector();
            fv.add("SELECT");

            while (resultSet.next()) {
                fv.add(resultSet.getString("per_name"));
                pumpersMap.put(resultSet.getString("per_name"), resultSet.getInt("per_id"));
            }

            DefaultComboBoxModel cb = new DefaultComboBoxModel(fv);
            jComboBox3.setModel(cb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadStatus() {
        try {
            ResultSet resultSet = MySql.execute("SELECT * FROM `attendence`");
            Vector fv = new Vector();
            fv.add("SELECT");

            while (resultSet.next()) {
                fv.add(resultSet.getString("att_status"));
                attendenceMap.put(resultSet.getString("att_status"), resultSet.getInt("att_id"));
            }

            DefaultComboBoxModel cb = new DefaultComboBoxModel(fv);
            jComboBox4.setModel(cb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clear() {
        jComboBox3.setSelectedIndex(0);
        jComboBox4.setSelectedIndex(0);
        jButton2.setVisible(true);
        jButton3.setVisible(false);
    }

    private void loadAttendenceDetails() {
        try {
            LocalDate d = LocalDate.now();
            DateTimeFormatter f = DateTimeFormatter.ofPattern("yyy-MM-dd");

            String date = d.format(f);
            ResultSet resultSet = MySql.execute("SELECT * FROM `mark_attendence` "
                    + "INNER JOIN `pumpers` ON `mark_attendence`.`pumpers_per_id` = `pumpers`.`per_id` "
                    + "INNER JOIN `attendence` ON `mark_attendence`.`attendence_id` = `attendence`.`att_id` "
                    + "INNER JOIN `attendence_date` ON `mark_attendence`.`attendence_date_d_id` = `attendence_date`.`d_id` "
                    + "WHERE `date`= '" + date + "'");

            DefaultTableModel tm = (DefaultTableModel) jTable2.getModel();
            tm.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> pv = new Vector<>();
                pv.add(resultSet.getString("id"));
                pv.add(resultSet.getString("per_name"));
                pv.add(resultSet.getString("contact"));
                pv.add(resultSet.getString("att_status"));

                tm.addRow(pv);
                jTable2.setModel(tm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAttendenceEdit() {
        try {
            ResultSet resultSet = MySql.execute("SELECT * FROM `mark_attendence` "
                    + "INNER JOIN `pumpers` ON `mark_attendence`.`pumpers_per_id` = `pumpers`.`per_id` "
                    + "INNER JOIN `attendence` ON `mark_attendence`.`attendence_id` = `attendence`.`att_id` "
                    + "INNER JOIN `attendence_date` ON `mark_attendence`.`attendence_date_d_id` = `attendence_date`.`d_id` "
                    + "WHERE `id`= '" + markId + "'");
            resultSet.next();
            jComboBox1.setSelectedItem(resultSet.getString("date"));
            jComboBox3.setSelectedItem(resultSet.getString("per_name"));
            jComboBox4.setSelectedItem(resultSet.getString("att_status"));
            jButton2.setVisible(false);
            jButton3.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundPanel1 = new com.swing.RoundPanel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();

        roundPanel1.setBackground(new java.awt.Color(102, 102, 102));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Quicksand", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Mark Attendence");
        jLabel5.setToolTipText("");

        jButton1.setBackground(new java.awt.Color(51, 51, 51));
        jButton1.setFont(new java.awt.Font("Quicksand", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Cancel");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(51, 51, 51));
        jButton2.setFont(new java.awt.Font("Quicksand", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Mark");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTable2.setBackground(new java.awt.Color(102, 102, 102));
        jTable2.setFont(new java.awt.Font("Quicksand", 0, 12)); // NOI18N
        jTable2.setForeground(new java.awt.Color(255, 255, 255));
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id", "Name", "Contact", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setAlignmentX(3.0F);
        jTable2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setResizable(false);
            jTable2.getColumnModel().getColumn(1).setResizable(false);
            jTable2.getColumnModel().getColumn(2).setResizable(false);
            jTable2.getColumnModel().getColumn(3).setResizable(false);
        }

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Quicksand", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Date");
        jLabel7.setToolTipText("");

        jButton3.setBackground(new java.awt.Color(51, 51, 51));
        jButton3.setFont(new java.awt.Font("Quicksand", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Mark Edit");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jComboBox1.setBackground(new java.awt.Color(102, 102, 102));
        jComboBox1.setFont(new java.awt.Font("Quicksand", 0, 12)); // NOI18N
        jComboBox1.setForeground(new java.awt.Color(255, 255, 255));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.setPreferredSize(new java.awt.Dimension(64, 22));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Quicksand", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Today Attendence");
        jLabel9.setToolTipText("");

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Quicksand", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("Employee");
        jLabel10.setToolTipText("");

        jComboBox3.setBackground(new java.awt.Color(102, 102, 102));
        jComboBox3.setFont(new java.awt.Font("Quicksand", 0, 12)); // NOI18N
        jComboBox3.setForeground(new java.awt.Color(255, 255, 255));
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox3.setPreferredSize(new java.awt.Dimension(64, 22));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Quicksand", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("status");
        jLabel11.setToolTipText("");

        jComboBox4.setBackground(new java.awt.Color(102, 102, 102));
        jComboBox4.setFont(new java.awt.Font("Quicksand", 0, 12)); // NOI18N
        jComboBox4.setForeground(new java.awt.Color(255, 255, 255));
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox4.setPreferredSize(new java.awt.Dimension(64, 22));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(51, 51, 51));
        jButton4.setFont(new java.awt.Font("Quicksand", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Start Marking");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1199, Short.MAX_VALUE))
                .addGap(27, 27, 27))
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(roundPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(301, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        if (evt.getClickCount() == 2) {
            int selectedRow = jTable2.getSelectedRow();
            String mattenId = String.valueOf(jTable2.getValueAt(selectedRow, 0));
            markId = mattenId;
            loadAttendenceEdit();
        }
    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String date = String.valueOf(jComboBox1.getSelectedItem());
        String employee = String.valueOf(jComboBox3.getSelectedItem());
        String status = String.valueOf(jComboBox4.getSelectedItem());

        if (date == "SELECT") {
            JOptionPane.showMessageDialog(this, "Please Select Date", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (employee == "SELECT") {
            JOptionPane.showMessageDialog(this, "Please Select Employee", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (status == "SELECT") {
            JOptionPane.showMessageDialog(this, "Please Select Status", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            int dateId = dateMap.get(date);
            int employeeId = pumpersMap.get(employee);
            int statusId = attendenceMap.get(status);

            try {
                ResultSet resultSet = MySql.execute("SELECT * FROM `mark_attendence` WHERE `pumpers_per_id` = '" + employeeId + "' AND `attendence_date_d_id` = '" + dateId + "'");
                if (!resultSet.next()) {
                    MySql.execute("INSERT INTO `mark_attendence` (`pumpers_per_id`,`attendence_id`,`attendence_date_d_id`) VALUES('" + employeeId + "','" + statusId + "','" + dateId + "')");
                    clear();
                    loadAttendenceDetails();
                } else {
                    JOptionPane.showMessageDialog(this, "Already Marked Attendence", "Warning", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        clear();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String date = String.valueOf(jComboBox1.getSelectedItem());
        String employee = String.valueOf(jComboBox3.getSelectedItem());
        String status = String.valueOf(jComboBox4.getSelectedItem());

        if (date == "SELECT") {
            JOptionPane.showMessageDialog(this, "Please Select Date", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (employee == "SELECT") {
            JOptionPane.showMessageDialog(this, "Please Select Employee", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (status == "SELECT") {
            JOptionPane.showMessageDialog(this, "Please Select Status", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            int dateId = dateMap.get(date);
            int employeeId = pumpersMap.get(employee);
            int statusId = attendenceMap.get(status);

            try {
                MySql.execute("UPDATE `mark_attendence` SET `pumpers_per_id` = '"+employeeId+"',`attendence_id` = '"+statusId+"',`attendence_date_d_id` = '"+dateId+"' WHERE `id` = '"+markId+"'");
                clear();
                loadAttendenceDetails();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        LocalDate d = LocalDate.now();
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String date = d.format(f);
        try {
            ResultSet resultSet = MySql.execute("SELECT * FROM `attendence_date` WHERE `date` = '" + date + "'");
            if (!resultSet.next()) {
                MySql.execute("INSERT INTO `attendence_date` (`date`) VALUES ('" + date + "')");
                loadDate(date);
            } else {
                loadDate(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private com.swing.RoundPanel roundPanel1;
    // End of variables declaration//GEN-END:variables
}
