package com.form;

import com.model.MySql;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class GroceryStockManagement extends javax.swing.JPanel {

    public GroceryStockManagement() {
        initComponents();
        loadStock("SELECT * FROM `stock` INNER JOIN `product` ON `stock`.`p_barcode` = `product`.`p_barcode` INNER JOIN `brand` ON `brand`.`b_id` = `product`.`b_id`");
        jTable2.getTableHeader().setBackground(new Color(102, 102, 102));
        jTable2.getTableHeader().setForeground(new Color(242, 242, 242));
        jTable2.getTableHeader().setFont(new Font("Barnschrift", Font.PLAIN, 14));
        setOpaque(false);
        jButton7.setEnabled(false);
    }

    private void loadStock(String query2) {

        try {

            String query = query2;

            query += "ORDER BY ";

//            query = query.replace("AND ORDER BY ", "ORDER BY ");

            if (jComboBox2.getSelectedIndex() == 0) {
                query += "`stock`.`s_id` ASC";
            } else if (jComboBox2.getSelectedIndex() == 1) {
                query += "`stock`.`s_id` DESC";
            } else if (jComboBox2.getSelectedIndex() == 2) {
                query += "`brand`.`b_name` ASC";
            } else if (jComboBox2.getSelectedIndex() == 3) {
                query += "`brand`.`b_name` DESC";
            } else if (jComboBox2.getSelectedIndex() == 4) {
                query += "`product`.`title` ASC";
            } else if (jComboBox2.getSelectedIndex() == 5) {
                query += "`product`.`title` DESC";
            } else if (jComboBox2.getSelectedIndex() == 6) {
                query += "`selling_price` ASC";
            } else if (jComboBox2.getSelectedIndex() == 7) {
                query += "`selling_price` DESC";
            } else if (jComboBox2.getSelectedIndex() == 8) {
                query += "`s_qty` ASC";
            } else if (jComboBox2.getSelectedIndex() == 9) {
                query += "`s_qty` DESC";
            }

            ResultSet resultset = MySql.execute(query);

            DefaultTableModel dtm = (DefaultTableModel) jTable2.getModel();
            dtm.setRowCount(0);

            while (resultset.next()) {

                Vector<String> v = new Vector<>();
                v.add(resultset.getString("stock.s_id"));
                v.add(resultset.getString("p_barcode"));
                v.add(resultset.getString("b_name"));
                v.add(resultset.getString("title"));
                v.add(resultset.getString("selling_price"));
                v.add(resultset.getString("s_qty"));
                v.add(resultset.getString("mfd"));
                v.add(resultset.getString("exp"));

                dtm.addRow(v);
                jTable2.setModel(dtm);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void reset() {

        jComboBox2.setSelectedIndex(0);
        jFormattedTextField4.setText("");
        jTextField4.setText("");
        loadStock("SELECT * FROM `stock` INNER JOIN `product` ON `stock`.`p_barcode` = `product`.`p_barcode` INNER JOIN `brand` ON `brand`.`b_id` = `product`.`b_id`");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundPanel2 = new com.swing.RoundPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jFormattedTextField4 = new javax.swing.JFormattedTextField();
        jButton7 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        roundPanel2.setBackground(new java.awt.Color(71, 71, 71));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Bahnschrift", 1, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(242, 242, 242));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Stock Management");
        jLabel5.setToolTipText("");

        jLabel1.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(242, 242, 242));
        jLabel1.setText("Sort By:");

        jComboBox2.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Stock ID Ascending", "Stock ID Descending", "Brand Ascending", "Brand Descending", "Name Ascending", "Name Descending", "Selling Price Ascending", "Selling Price Descending", "Quantity Ascending", "Quantity Descending" }));
        jComboBox2.setPreferredSize(new java.awt.Dimension(76, 33));
        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(242, 242, 242));
        jLabel7.setText("Selling Price:");

        jLabel8.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(242, 242, 242));
        jLabel8.setText("Barcode:");

        jTextField4.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jTextField4.setPreferredSize(new java.awt.Dimension(68, 33));
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });

        jFormattedTextField4.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField4.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField4.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jFormattedTextField4.setMinimumSize(new java.awt.Dimension(68, 33));
        jFormattedTextField4.setPreferredSize(new java.awt.Dimension(68, 33));

        jButton7.setBackground(new java.awt.Color(247, 147, 30));
        jButton7.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(242, 242, 242));
        jButton7.setText("Edit Price");
        jButton7.setBorderPainted(false);
        jButton7.setMargin(new java.awt.Insets(5, 5, 2, 5));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(193, 39, 45));
        jButton6.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(242, 242, 242));
        jButton6.setText("Clear Fields");
        jButton6.setBorderPainted(false);
        jButton6.setMargin(new java.awt.Insets(5, 5, 2, 5));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jTable2.setBackground(new java.awt.Color(102, 102, 102));
        jTable2.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jTable2.setForeground(new java.awt.Color(242, 242, 242));
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Stock ID", "Barcode", "Brand", "Name", "Selling Price", "Quantity", "Manufactured Date", "Expiry Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable2);

        javax.swing.GroupLayout roundPanel2Layout = new javax.swing.GroupLayout(roundPanel2);
        roundPanel2.setLayout(roundPanel2Layout);
        roundPanel2Layout.setHorizontalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addGap(7, 7, 7)
                        .addComponent(jFormattedTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1210, Short.MAX_VALUE)
                    .addComponent(jLabel5))
                .addGap(20, 20, 20))
        );
        roundPanel2Layout.setVerticalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jFormattedTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 614, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged

        
        String barcode = jTextField4.getText();
        
        if(barcode.isEmpty()){
            loadStock("SELECT * FROM `stock` INNER JOIN `product` ON `stock`.`p_barcode` = `product`.`p_barcode` INNER JOIN `brand` ON `brand`.`b_id` = `product`.`b_id`");
        }else{
            loadStock("SELECT * FROM `stock` INNER JOIN `product` ON `stock`.`p_barcode` = `product`.`p_barcode` INNER JOIN `brand` ON `brand`.`b_id` = `product`.`b_id` WHERE `stock`.`p_barcode` = '" + barcode + "'");
        }
    }//GEN-LAST:event_jComboBox2ItemStateChanged

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased

        String barcode = jTextField4.getText();

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            loadStock("SELECT * FROM `stock` INNER JOIN `product` ON `stock`.`p_barcode` = `product`.`p_barcode` INNER JOIN `brand` ON `brand`.`b_id` = `product`.`b_id` WHERE `stock`.`p_barcode` = '" + barcode + "'");
        }

    }//GEN-LAST:event_jTextField4KeyReleased

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        int selectedTableRow=jTable2.getSelectedRow();    
        String stockId=String.valueOf(jTable2.getValueAt(selectedTableRow, 0)); 
        String price=jFormattedTextField4.getText();
        
        if (price.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Stock Price", "Warning", JOptionPane.ERROR_MESSAGE);
        }else{
            try {
                MySql.execute("UPDATE `stock` SET `selling_price`='" + price + "' WHERE `s_id`='"+stockId+"'");
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            
        jTable2.clearSelection();
        jButton7.setEnabled(false);
        jFormattedTextField4.setText("");
        
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        reset();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        int selectedTableRow=jTable2.getSelectedRow();
        
        if(selectedTableRow!=-1){
            jFormattedTextField4.setText(String.valueOf(jTable2.getValueAt(selectedTableRow, 4)));                           
            jButton7.setEnabled(true);
        }      
        
        
    }//GEN-LAST:event_jTable2MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JFormattedTextField jFormattedTextField4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField4;
    private com.swing.RoundPanel roundPanel2;
    // End of variables declaration//GEN-END:variables
}
