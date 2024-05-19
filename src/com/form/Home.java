package com.form;

import com.chart.Chart;
import com.chart.ModelChart;
import java.awt.Color;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.model.MySql;

public class Home extends javax.swing.JPanel {

    public Home() {
        initComponents();
        setOpaque(false);
        jPanel1.setVisible(false);
        jPanel2.setVisible(true);
        loadExpenseDetails();
        incomeExpenses();
        fualQuantity();
        loadMonth();
    }
    
    private void loadExpenseDetails() {
        try {
            ResultSet resultSet = MySql.execute("SELECT * FROM `expense_details` INNER JOIN `expense` ON `expense_details`.`expense_ex_id` = `expense`.`ex_id` ORDER BY `date` DESC LIMIT 10");

            DefaultTableModel tm = (DefaultTableModel) jTable1.getModel();
            tm.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> pv = new Vector<>();
                pv.add(resultSet.getString("exd_id"));
                pv.add(resultSet.getString("ex_name"));
                pv.add(resultSet.getString("date"));
                pv.add(resultSet.getString("amount"));

                tm.addRow(pv);
                jTable1.setModel(tm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void incomeExpenses() {
        double[] january = new double[2];
        double[] february = new double[2];
        double[] march = new double[2];
        double[] april = new double[2];
        double[] may = new double[2];
        double[] june = new double[2];
        double[] july = new double[2];
        double[] august = new double[2];
        double[] september = new double[2];
        double[] october = new double[2];
        double[] november = new double[2];
        double[] december = new double[2];

        try {
            ResultSet incomeSet = MySql.execute("SELECT * FROM `pumps_has_release_date` INNER JOIN `release_date` ON `pumps_has_release_date`.`release_date_pr_id` = `release_date`.`pr_id` WHERE YEAR(`release_date`.`pr_date`) = YEAR(CURDATE())");
            while (incomeSet.next()) {
                LocalDate date = incomeSet.getDate("pr_date").toLocalDate();
                int month = date.getMonthValue();
                switch (month) {
                    case 1:
                        january[0] += incomeSet.getDouble("sales_total");
                        break;
                    case 2:
                        february[0] += incomeSet.getDouble("sales_total");
                        break;
                    case 3:
                        march[0] += incomeSet.getDouble("sales_total");
                        break;
                    case 4:
                        april[0] += incomeSet.getDouble("sales_total");
                        break;
                    case 5:
                        may[0] += incomeSet.getDouble("sales_total");
                        break;
                    case 6:
                        june[0] += incomeSet.getDouble("sales_total");
                        break;
                    case 7:
                        july[0] += incomeSet.getDouble("sales_total");
                        break;
                    case 8:
                        august[0] += incomeSet.getDouble("sales_total");
                        break;
                    case 9:
                        september[0] += incomeSet.getDouble("sales_total");
                        break;
                    case 10:
                        october[0] += incomeSet.getDouble("sales_total");
                        break;
                    case 11:
                        november[0] += incomeSet.getDouble("sales_total");
                        break;
                    case 12:
                        december[0] += incomeSet.getDouble("sales_total");
                        break;
                    default:
                        break;
                }
            }
            ResultSet expensesSet = MySql.execute("SELECT * FROM `expense_details` WHERE YEAR(`date`) = YEAR(CURDATE())");
            while (expensesSet.next()) {
                LocalDate date = expensesSet.getDate("date").toLocalDate();
                int month = date.getMonthValue();
                switch (month) {
                    case 1:
                        january[1] += expensesSet.getDouble("amount");
                        break;
                    case 2:
                        february[1] += expensesSet.getDouble("amount");
                        break;
                    case 3:
                        march[1] += expensesSet.getDouble("amount");
                        break;
                    case 4:
                        april[1] += expensesSet.getDouble("amount");
                        break;
                    case 5:
                        may[1] += expensesSet.getDouble("amount");
                        break;
                    case 6:
                        june[1] += expensesSet.getDouble("amount");
                        break;
                    case 7:
                        july[1] += expensesSet.getDouble("amount");
                        break;
                    case 8:
                        august[1] += expensesSet.getDouble("amount");
                        break;
                    case 9:
                        september[1] += expensesSet.getDouble("amount");
                        break;
                    case 10:
                        october[1] += expensesSet.getDouble("amount");
                        break;
                    case 11:
                        november[1] += expensesSet.getDouble("amount");
                        break;
                    case 12:
                        december[1] += expensesSet.getDouble("amount");
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        chart2.seriesSize = 35;
        chart2.seriesSpace = 25;
        chart2.addLegend("Income", new Color(12, 84, 175), new Color(0, 108, 247));
        chart2.addLegend("Expense", new Color(54, 4, 143), new Color(104, 49, 200));
        chart2.addData(new ModelChart("January", january));
        chart2.addData(new ModelChart("February", february));
        chart2.addData(new ModelChart("March", march));
        chart2.addData(new ModelChart("April", april));
        chart2.addData(new ModelChart("May", may));
        chart2.addData(new ModelChart("June", june));
        chart2.addData(new ModelChart("July", july));
        chart2.addData(new ModelChart("August", august));
        chart2.addData(new ModelChart("September", september));
        chart2.addData(new ModelChart("Octomber", october));
        chart2.addData(new ModelChart("November", november));
        chart2.addData(new ModelChart("December", december));
        chart2.start();
        chart2.setVisible(true);
    }
    
    private void loadMonth(){
        LocalDate d = LocalDate.now();
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MMM");
        String date = d.format(f);

        try {
            ResultSet resultSet = MySql.execute("SELECT * FROM `sa_month` WHERE `month` = '" + date + "'");
            if (!resultSet.next()) {
                MySql.execute("INSERT INTO `sa_month` (`month`) VALUES ('" + date + "')");
                loadMonth();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fualQuantity() {

        double[] petrol95 = new double[1];
        double[] petrol92 = new double[1];
        double[] disal = new double[1];
        double[] superDisal = new double[1];

        try {
            ResultSet fualSet = MySql.execute("SELECT * FROM `fual`");
            while (fualSet.next()) {
                switch (fualSet.getInt("fu_id")) {
                    case 1:
                        petrol95[0] = fualSet.getDouble("fu_qty");
                        break;
                    case 2:
                        petrol92[0] = fualSet.getDouble("fu_qty");
                        break;
                    case 3:
                        disal[0] = fualSet.getDouble("fu_qty");
                        break;
                    case 4:
                        superDisal[0] = fualSet.getDouble("fu_qty");
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        chart.seriesSize = 35;
        chart.seriesSpace = 25;
        chart.addLegend("Quantity", new Color(12, 84, 175), new Color(0, 108, 247));
        chart.addData(new ModelChart("Petrol 95", petrol95));
        chart.addData(new ModelChart("Petrol 92", petrol92));
        chart.addData(new ModelChart("Disal", disal));
        chart.addData(new ModelChart("Super Disal", superDisal));
        chart.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundPanel1 = new com.swing.RoundPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        roundPanel2 = new com.swing.RoundPanel();
        jPanel2 = new javax.swing.JPanel();
        chart2 = new com.chart.Chart();
        jPanel1 = new javax.swing.JPanel();
        chart = new com.chart.Chart();
        roundPanel3 = new com.swing.RoundPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        roundPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jButton2.setBackground(new java.awt.Color(51, 51, 51));
        jButton2.setFont(new java.awt.Font("Quicksand", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Income And Expenses");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(51, 51, 51));
        jButton3.setFont(new java.awt.Font("Quicksand", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Fuel Quantity");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icon/chart.png"))); // NOI18N

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Quicksand", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Charts");
        jLabel6.setToolTipText("");

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(27, 27, 27))
            .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(roundPanel1Layout.createSequentialGroup()
                    .addGap(35, 35, 35)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(240, Short.MAX_VALUE)))
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addGap(114, 114, 114)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(35, 35, 35))
            .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(roundPanel1Layout.createSequentialGroup()
                    .addGap(37, 37, 37)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(224, Short.MAX_VALUE)))
        );

        roundPanel2.setBackground(new java.awt.Color(51, 51, 51));
        roundPanel2.setOpaque(false);
        roundPanel2.setLayout(new javax.swing.OverlayLayout(roundPanel2));

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chart2, javax.swing.GroupLayout.DEFAULT_SIZE, 1171, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(chart2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        roundPanel2.add(jPanel2);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, 1171, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        roundPanel2.add(jPanel1);

        roundPanel3.setBackground(new java.awt.Color(51, 51, 51));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Quicksand", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Latest Expense");
        jLabel5.setToolTipText("");

        jTable1.setBackground(new java.awt.Color(102, 102, 102));
        jTable1.setForeground(new java.awt.Color(255, 255, 255));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id", "Name", "Date", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setEnabled(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
        }

        javax.swing.GroupLayout roundPanel3Layout = new javax.swing.GroupLayout(roundPanel3);
        roundPanel3.setLayout(roundPanel3Layout);
        roundPanel3Layout.setHorizontalGroup(
            roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );
        roundPanel3Layout.setVerticalGroup(
            roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(roundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roundPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jPanel1.setVisible(false);
        jPanel2.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        jPanel1.setVisible(true);
        jPanel2.setVisible(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        
    }//GEN-LAST:event_jTable1MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.chart.Chart chart;
    private com.chart.Chart chart2;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private com.swing.RoundPanel roundPanel1;
    private com.swing.RoundPanel roundPanel2;
    private com.swing.RoundPanel roundPanel3;
    // End of variables declaration//GEN-END:variables
}
