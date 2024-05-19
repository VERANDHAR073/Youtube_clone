package com.frams;

import com.event.TableUtils;
import java.awt.Color;
import static java.awt.Color.WHITE;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.model.MySql;

public class Profit extends javax.swing.JPanel {

    private static HashMap<String, Integer> dateMap = new HashMap<>();

    public Profit() {
        initComponents();
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
        loadMonth();
    }

    private void loadMonth() {
        try {
            ResultSet resultSet = MySql.execute("SELECT * FROM `sa_month` ORDER BY `id` DESC");
            Vector fv = new Vector();
            fv.add("SELECT");

            while (resultSet.next()) {
                fv.add(resultSet.getString("month"));
                dateMap.put(resultSet.getString("month"), resultSet.getInt("id"));
            }

            DefaultComboBoxModel cb = new DefaultComboBoxModel(fv);
            jComboBox1.setModel(cb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadProfit() {
        if (jComboBox1.getSelectedItem() != "SELECT") {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM", Locale.ENGLISH);
                Date date = sdf.parse(String.valueOf(jComboBox1.getSelectedItem()));
                SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
                int month = Integer.parseInt(monthFormat.format(date));
                int year = Integer.parseInt(yearFormat.format(date));

                ResultSet resultSet = MySql.execute("SELECT * FROM `pumps_has_release_date` INNER JOIN `release_date` ON `pumps_has_release_date`.`release_date_pr_id` = `release_date`.`pr_id` WHERE YEAR(`pr_date`) = '" + year + "' AND MONTH(`pr_date`) = '" + month + "' ORDER BY `pr_date` ASC");

                DefaultTableModel tm = (DefaultTableModel) jTable2.getModel();
                tm.setRowCount(0);
                double income = 0;
                double expense = 0;
                Vector<String> en = new Vector<>();
                en.add("INCOME");
                en.add("");
                tm.addRow(en);
                while (resultSet.next()) {
                    Vector<String> pv = new Vector<>();
                    pv.add("Fual Selling " + resultSet.getString("pr_date") + "");
                    pv.add(resultSet.getString("sales_total"));
                    income += Double.valueOf(resultSet.getString("sales_total"));
                    tm.addRow(pv);
                }
                Vector<String> em = new Vector<>();
                em.add("");
                em.add("");
                tm.addRow(em);
                Vector<String> tt = new Vector<>();
                tt.add("TOTAL");
                tt.add(String.valueOf(income));
                tm.addRow(tt);
                int totalRow = tm.getRowCount() - 1;
                Vector<String> fe = new Vector<>();
                fe.add("");
                fe.add("");
                tm.addRow(fe);
                Vector<String> f1 = new Vector<>();
                f1.add("EXPENSE");
                f1.add("");
                tm.addRow(f1);
                int expenseRow = tm.getRowCount() - 1;
                ResultSet resultSet2 = MySql.execute("SELECT * FROM `expense_details` INNER JOIN `expense` ON `expense_details`.`expense_ex_id` = `expense`.`ex_id` WHERE YEAR(`date`) = '" + year + "' AND MONTH(`date`) = '" + month + "' ORDER BY `date` ASC");
                while (resultSet2.next()) {
                    Vector<String> pv = new Vector<>();
                    pv.add(resultSet2.getString("ex_name") + " " + resultSet2.getString("date"));
                    pv.add(resultSet2.getString("amount"));
                    expense += Double.valueOf(resultSet2.getString("amount"));
                    tm.addRow(pv);
                }
                
                Vector<String> ft = new Vector<>();
                ft.add("");
                ft.add("");
                tm.addRow(ft);
                
                Vector<String> fu = new Vector<>();
                fu.add("TOTAL");
                fu.add(String.valueOf(expense));
                tm.addRow(fu);
                int totalRow2 = tm.getRowCount() - 1;

                DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
                headerRenderer.setHorizontalAlignment(JLabel.LEFT);
                jTable2.getColumnModel().getColumn(0).setCellRenderer(headerRenderer);

                DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
                rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
                jTable2.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);

                jTable2.getTableHeader().setUI(null);
                jTable2.setModel(tm);
                
                Vector<String> fp = new Vector<>();
                fp.add("PROFIT");
                fp.add(String.valueOf(income - expense));
                tm.addRow(fp);
                int profitRow = tm.getRowCount() - 1;
                
                
                int[] rows = {0, totalRow,expenseRow,totalRow2,profitRow};
                TableUtils.setRowSelection(jTable2, rows, WHITE);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please Select month", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundPanel1 = new com.swing.RoundPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();

        roundPanel1.setBackground(new java.awt.Color(102, 102, 102));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Quicksand", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Profit & Loss");
        jLabel5.setToolTipText("");

        jTable2.setAutoCreateRowSorter(true);
        jTable2.setBackground(new java.awt.Color(102, 102, 102));
        jTable2.setFont(new java.awt.Font("Quicksand", 0, 12)); // NOI18N
        jTable2.setForeground(new java.awt.Color(255, 255, 255));
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setAlignmentX(10.0F);
        jTable2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable2.setEnabled(false);
        jTable2.setRowHeight(30);
        jTable2.setShowGrid(false);
        jTable2.setSurrendersFocusOnKeystroke(true);
        jTable2.getTableHeader().setResizingAllowed(false);
        jTable2.getTableHeader().setReorderingAllowed(false);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setResizable(false);
            jTable2.getColumnModel().getColumn(0).setPreferredWidth(750);
            jTable2.getColumnModel().getColumn(1).setResizable(false);
        }

        jComboBox1.setBackground(new java.awt.Color(102, 102, 102));
        jComboBox1.setFont(new java.awt.Font("Quicksand", 0, 12)); // NOI18N
        jComboBox1.setForeground(new java.awt.Color(255, 255, 255));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.setPreferredSize(new java.awt.Dimension(64, 22));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jComboBox1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jComboBox1PropertyChange(evt);
            }
        });

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Quicksand", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("Select Month");
        jLabel10.setToolTipText("");

        jButton5.setBackground(new java.awt.Color(51, 51, 51));
        jButton5.setFont(new java.awt.Font("Quicksand", 1, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Load Profit");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, roundPanel1Layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5)))
                .addContainerGap(826, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel1Layout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addComponent(jScrollPane2)
                .addGap(120, 120, 120))
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 592, Short.MAX_VALUE)
                .addGap(31, 31, 31))
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

    }//GEN-LAST:event_jTable2MouseClicked

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged

    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jComboBox1PropertyChange

    }//GEN-LAST:event_jComboBox1PropertyChange

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        loadProfit();
    }//GEN-LAST:event_jButton5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private com.swing.RoundPanel roundPanel1;
    // End of variables declaration//GEN-END:variables
}
