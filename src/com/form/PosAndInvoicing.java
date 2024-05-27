package com.form;

import com.model.InvoiceItem;
import com.model.MySql;
import com.model.UserDetails;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class PosAndInvoicing extends javax.swing.JPanel {
    
    HashMap<String, InvoiceItem> invoiceItemMap = new HashMap<>();
    private String selectedStockId;    
    private UserDetails userBean;


    public PosAndInvoicing(UserDetails userDetails) {
        initComponents();
        setOpaque(false);
        jTable1.getTableHeader().setBackground(new Color(102,102,102));
        jTable1.getTableHeader().setForeground(new Color(242,242,242));
        jTable1.getTableHeader().setFont(new Font("Barnschrift",Font.PLAIN,14));
        jLabel34.setText(userDetails.getUsername());
        jLabel36.setText(userDetails.getName());
        jTextField4.grabFocus();
        this.userBean = userDetails;
        loadPaymentMethods();
        countDailyInvoices();
    }
    
    //  mobile
    public JTextField getjTextField2() {
        return jTextField2;
    }
    
    //    points
    public JFormattedTextField getjFormattedTextField4() {
        return jFormattedTextField4;
    }

//  selected product stock id
    public String getSelectedStockId() {
        return selectedStockId;
    }

    public void setSelectedStockId(String selectedStockId) {
        this.selectedStockId = selectedStockId;
    }

//    brand
    public JTextField getjLabel11() {
        return jLabel11;
    }
    
    //    barcode
    public JTextField getjTextField4() {
        return jTextField4;
    }

//    name
    public JTextField getjLabel13() {
        return jLabel13;
    }

//    mfd
    public JTextField getjLabel18() {
        return jLabel18;
    }

//    exp
    public JTextField getjLabel22() {
        return jLabel22;
    }

//    selling price
    public JFormattedTextField getjLabel23() {
        return jLabel23;
    }

    //    available qty
    public JFormattedTextField getjLabel25() {
        return jLabel25;
    }

    
    public void countDailyInvoices() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {

            ResultSet resultset = MySql.execute("SELECT COUNT(`in_id`) FROM `invoice` WHERE `e_nic`='" + userBean.getNic()+ "' AND `date_time` LIKE '" + format.format(new Date()) + "%'");

            if (resultset.next()) {
                String count = resultset.getString("COUNT(`in_id`)");
                jLabel38.setText(count);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    HashMap<String, String> paymentMethodMap = new HashMap<>();
    public void loadPaymentMethods() {
        try {
            ResultSet resultSet = MySql.execute("SELECT * FROM `payment_methods` ORDER BY `pm_name` ASC");

            Vector v = new Vector();
            v.add("Select Method");

            while (resultSet.next()) {
                v.add(resultSet.getString("pm_name"));
                paymentMethodMap.put(resultSet.getString("pm_name"), resultSet.getString("pm_id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(v);
            jComboBox1.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private double total = 0;
    private double payment = 0;
    private boolean withdrawPoints = false;
    private double balance = 0;
    private String paymentMethod = "Select Method";
    private double newPoints = 0;

    private void calculate() {

        if (jFormattedTextField1.getText().isEmpty()) {
            payment = 0;
        } else {
            payment = Double.parseDouble(jFormattedTextField1.getText());
        }

        total = Double.parseDouble(jLabel29.getText());

        if (jCheckBox1.isSelected()) {
            withdrawPoints = true;
        } else {
            withdrawPoints = false;
        }

        paymentMethod = String.valueOf(jComboBox1.getSelectedItem());

        if (total < 0) {
//           discount error
        } else {
//            discount success

            if (withdrawPoints) {

                if (Double.parseDouble(jFormattedTextField4.getText()) == total) {
                    newPoints = 0;
                    total = 0;
//                    no payment needed

                } else if (Double.parseDouble(jFormattedTextField4.getText()) < total) {
                    newPoints = 0;
                    total -= Double.parseDouble(jFormattedTextField4.getText());
//                    payment needed

                } else {
                    newPoints = Double.parseDouble(jFormattedTextField4.getText()) - total;
                    total = 0;
//                    nopayment needed
                }
            }
        }

        if (paymentMethod.equals("Cash")) {
//                cash
            balance = payment - total;
            jFormattedTextField1.setEditable(true);

            if (balance < 0) {
                jButton2.setEnabled(false);
            } else {
                jButton2.setEnabled(true);
            }

        } else {
//                card
            payment = total;
            balance = 0;
            jFormattedTextField1.setText(String.valueOf(payment));
            jFormattedTextField1.setEditable(false);
        }

        jLabel28.setText(String.valueOf(total));
        jLabel32.setText(String.valueOf(balance));

    }
    
    private void clearInvoice(){
                reset();
                jTextField4.grabFocus();
                jTextField2.setText("");
                jComboBox1.setSelectedIndex(0);
                jLabel29.setText("");             
                jLabel28.setText("");             
                jLabel32.setText("");                
                jFormattedTextField4.setText("");   
                jFormattedTextField1.setText("");
                jCheckBox1.setSelected(false);
                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                dtm.setRowCount(0);           
    }
    
    private void loadInvoiceItems() {

        double billTotal = 0;

        DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
        dtm.setRowCount(0);
        billTotal = 0;

        for (InvoiceItem invoiceItem : invoiceItemMap.values()) {

            Vector<String> v = new Vector<>();
            v.add(invoiceItem.getStockId());
            v.add(invoiceItem.getBrand());
            v.add(invoiceItem.getName());
            v.add(invoiceItem.getQty());
            v.add(invoiceItem.getSellingPrice());
            v.add(invoiceItem.getMfg());
            v.add(invoiceItem.getExp());

            double singleItemTotal = Double.parseDouble(invoiceItem.getQty()) * Double.parseDouble(invoiceItem.getSellingPrice());
            billTotal += singleItemTotal;
            v.add(String.valueOf(singleItemTotal));

            dtm.addRow(v);
            jTable1.setModel(dtm);
        }

        jLabel29.setText(String.valueOf(billTotal));
        calculate();
    }
    
    private void reset() {
        jTextField4.setText("");
        jLabel25.setText("");
        jLabel11.setText("");
        jLabel13.setText("");
        jLabel23.setText("");
        jFormattedTextField2.setText("1");
        jLabel18.setText("");
        jLabel22.setText("");
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundPanel2 = new com.swing.RoundPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JTextField();
        jLabel112 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel182 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel25 = new javax.swing.JFormattedTextField();
        jLabel23 = new javax.swing.JFormattedTextField();
        jFormattedTextField4 = new javax.swing.JFormattedTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JFormattedTextField();
        jLabel20 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JFormattedTextField();
        jButton2 = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JFormattedTextField();
        jButton3 = new javax.swing.JButton();

        roundPanel2.setBackground(new java.awt.Color(71, 71, 71));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Bahnschrift", 1, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(242, 242, 242));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("POS and Invoicing");
        jLabel5.setToolTipText("");

        jLabel1.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(242, 242, 242));
        jLabel1.setText("Operator Username:");

        jLabel2.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(242, 242, 242));
        jLabel2.setText("Operator Name:");

        jLabel3.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(242, 242, 242));
        jLabel3.setText("Daily Invoice Count:");

        jLabel34.setEditable(false);
        jLabel34.setBackground(new java.awt.Color(102, 102, 102));
        jLabel34.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(242, 242, 242));
        jLabel34.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jLabel34.setBorder(null);
        jLabel34.setFocusable(false);
        jLabel34.setPreferredSize(new java.awt.Dimension(68, 33));
        jLabel34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLabel34ActionPerformed(evt);
            }
        });

        jLabel36.setEditable(false);
        jLabel36.setBackground(new java.awt.Color(102, 102, 102));
        jLabel36.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(242, 242, 242));
        jLabel36.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jLabel36.setBorder(null);
        jLabel36.setFocusable(false);
        jLabel36.setPreferredSize(new java.awt.Dimension(68, 33));
        jLabel36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLabel36ActionPerformed(evt);
            }
        });

        jLabel38.setEditable(false);
        jLabel38.setBackground(new java.awt.Color(102, 102, 102));
        jLabel38.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(242, 242, 242));
        jLabel38.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jLabel38.setBorder(null);
        jLabel38.setFocusable(false);
        jLabel38.setPreferredSize(new java.awt.Dimension(68, 33));
        jLabel38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLabel38ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(242, 242, 242));
        jLabel4.setText("Customer Mobile:");

        jTextField2.setEditable(false);
        jTextField2.setBackground(new java.awt.Color(102, 102, 102));
        jTextField2.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(242, 242, 242));
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField2.setBorder(null);
        jTextField2.setFocusable(false);
        jTextField2.setPreferredSize(new java.awt.Dimension(68, 33));
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(51, 51, 51));
        jButton1.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jButton1.setForeground(new java.awt.Color(242, 242, 242));
        jButton1.setText("Select");
        jButton1.setBorderPainted(false);
        jButton1.setMargin(new java.awt.Insets(5, 14, 2, 14));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(242, 242, 242));
        jLabel6.setText("Item Barcode:");

        jTextField4.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
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

        jLabel7.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(242, 242, 242));
        jLabel7.setText("Availability in Stock:");

        jLabel9.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(242, 242, 242));
        jLabel9.setText("Product Brand:");

        jLabel11.setEditable(false);
        jLabel11.setBackground(new java.awt.Color(102, 102, 102));
        jLabel11.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(242, 242, 242));
        jLabel11.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jLabel11.setBorder(null);
        jLabel11.setFocusable(false);
        jLabel11.setPreferredSize(new java.awt.Dimension(68, 33));
        jLabel11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLabel11ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(242, 242, 242));
        jLabel10.setText("Product Title:");

        jLabel13.setEditable(false);
        jLabel13.setBackground(new java.awt.Color(102, 102, 102));
        jLabel13.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(242, 242, 242));
        jLabel13.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jLabel13.setBorder(null);
        jLabel13.setFocusable(false);
        jLabel13.setPreferredSize(new java.awt.Dimension(68, 33));
        jLabel13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLabel13ActionPerformed(evt);
            }
        });

        jLabel112.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jLabel112.setForeground(new java.awt.Color(242, 242, 242));
        jLabel112.setText("Product Unit Price:");

        jLabel8.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(242, 242, 242));
        jLabel8.setText("Buying Quantity:");

        jButton4.setBackground(new java.awt.Color(247, 147, 30));
        jButton4.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jButton4.setForeground(new java.awt.Color(242, 242, 242));
        jButton4.setText("Add to Invoice");
        jButton4.setBorderPainted(false);
        jButton4.setMargin(new java.awt.Insets(5, 5, 2, 5));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(193, 39, 45));
        jButton5.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jButton5.setForeground(new java.awt.Color(242, 242, 242));
        jButton5.setText("Clear Fields");
        jButton5.setBorderPainted(false);
        jButton5.setMargin(new java.awt.Insets(5, 5, 2, 5));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jFormattedTextField2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField2.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jFormattedTextField2.setMinimumSize(new java.awt.Dimension(68, 33));
        jFormattedTextField2.setPreferredSize(new java.awt.Dimension(68, 33));

        jLabel12.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(242, 242, 242));
        jLabel12.setText("Manufac. Date:");

        jLabel18.setEditable(false);
        jLabel18.setBackground(new java.awt.Color(102, 102, 102));
        jLabel18.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(242, 242, 242));
        jLabel18.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jLabel18.setBorder(null);
        jLabel18.setFocusable(false);
        jLabel18.setPreferredSize(new java.awt.Dimension(68, 33));
        jLabel18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLabel18ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(242, 242, 242));
        jLabel15.setText("Expiry Date:");

        jLabel22.setEditable(false);
        jLabel22.setBackground(new java.awt.Color(102, 102, 102));
        jLabel22.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(242, 242, 242));
        jLabel22.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jLabel22.setBorder(null);
        jLabel22.setFocusable(false);
        jLabel22.setPreferredSize(new java.awt.Dimension(68, 33));
        jLabel22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLabel22ActionPerformed(evt);
            }
        });

        jTable1.setBackground(new java.awt.Color(102, 102, 102));
        jTable1.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jTable1.setForeground(new java.awt.Color(242, 242, 242));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Barcode", "Brand", "Product Name", "Quantity", "Selling Price", "MFD", "EXP", "Total Item Price", "stockId"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setFocusable(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(200);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(20);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(20);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(20);
            jTable1.getColumnModel().getColumn(8).setMinWidth(0);
            jTable1.getColumnModel().getColumn(8).setPreferredWidth(0);
            jTable1.getColumnModel().getColumn(8).setMaxWidth(0);
        }

        jLabel17.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(242, 242, 242));
        jLabel17.setText("Payment Method:");

        jComboBox1.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jComboBox1.setPreferredSize(new java.awt.Dimension(76, 33));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jLabel182.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jLabel182.setForeground(new java.awt.Color(242, 242, 242));
        jLabel182.setText("Redeem Points:");

        jCheckBox1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCheckBox1.setPreferredSize(new java.awt.Dimension(33, 33));
        jCheckBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox1ItemStateChanged(evt);
            }
        });
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jLabel25.setEditable(false);
        jLabel25.setBackground(new java.awt.Color(102, 102, 102));
        jLabel25.setBorder(null);
        jLabel25.setForeground(new java.awt.Color(242, 242, 242));
        jLabel25.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jLabel25.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jLabel25.setFocusable(false);
        jLabel25.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jLabel25.setMinimumSize(new java.awt.Dimension(68, 33));
        jLabel25.setPreferredSize(new java.awt.Dimension(68, 33));

        jLabel23.setEditable(false);
        jLabel23.setBackground(new java.awt.Color(102, 102, 102));
        jLabel23.setBorder(null);
        jLabel23.setForeground(new java.awt.Color(242, 242, 242));
        jLabel23.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jLabel23.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jLabel23.setFocusable(false);
        jLabel23.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jLabel23.setMinimumSize(new java.awt.Dimension(68, 33));
        jLabel23.setPreferredSize(new java.awt.Dimension(68, 33));

        jFormattedTextField4.setEditable(false);
        jFormattedTextField4.setBackground(new java.awt.Color(102, 102, 102));
        jFormattedTextField4.setBorder(null);
        jFormattedTextField4.setForeground(new java.awt.Color(242, 242, 242));
        jFormattedTextField4.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField4.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField4.setFocusable(false);
        jFormattedTextField4.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jFormattedTextField4.setMinimumSize(new java.awt.Dimension(68, 33));
        jFormattedTextField4.setPreferredSize(new java.awt.Dimension(68, 33));

        jLabel19.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(242, 242, 242));
        jLabel19.setText("Grand Bill Total:");

        jLabel28.setEditable(false);
        jLabel28.setBackground(new java.awt.Color(102, 102, 102));
        jLabel28.setBorder(null);
        jLabel28.setForeground(new java.awt.Color(242, 242, 242));
        jLabel28.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jLabel28.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jLabel28.setFocusable(false);
        jLabel28.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jLabel28.setMinimumSize(new java.awt.Dimension(68, 33));
        jLabel28.setPreferredSize(new java.awt.Dimension(68, 33));

        jLabel20.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(242, 242, 242));
        jLabel20.setText("Paid Amount:");

        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField1.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jFormattedTextField1.setMinimumSize(new java.awt.Dimension(68, 33));
        jFormattedTextField1.setPreferredSize(new java.awt.Dimension(68, 33));
        jFormattedTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jFormattedTextField1KeyReleased(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(242, 242, 242));
        jLabel21.setText("Balance:");

        jLabel32.setEditable(false);
        jLabel32.setBackground(new java.awt.Color(102, 102, 102));
        jLabel32.setBorder(null);
        jLabel32.setForeground(new java.awt.Color(242, 242, 242));
        jLabel32.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jLabel32.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jLabel32.setFocusable(false);
        jLabel32.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jLabel32.setMinimumSize(new java.awt.Dimension(68, 33));
        jLabel32.setPreferredSize(new java.awt.Dimension(68, 33));

        jButton2.setBackground(new java.awt.Color(242, 242, 242));
        jButton2.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 0, 0));
        jButton2.setText("Print the Invoice ");
        jButton2.setBorderPainted(false);
        jButton2.setMargin(new java.awt.Insets(5, 14, 2, 14));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(242, 242, 242));
        jLabel24.setText("Invoice Total:");

        jLabel29.setEditable(false);
        jLabel29.setBackground(new java.awt.Color(102, 102, 102));
        jLabel29.setBorder(null);
        jLabel29.setForeground(new java.awt.Color(242, 242, 242));
        jLabel29.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jLabel29.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jLabel29.setFocusable(false);
        jLabel29.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jLabel29.setMinimumSize(new java.awt.Dimension(68, 33));
        jLabel29.setPreferredSize(new java.awt.Dimension(68, 33));

        jButton3.setBackground(new java.awt.Color(193, 39, 45));
        jButton3.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jButton3.setForeground(new java.awt.Color(242, 242, 242));
        jButton3.setText("Clear the Invoice");
        jButton3.setBorderPainted(false);
        jButton3.setMargin(new java.awt.Insets(5, 14, 2, 14));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel2Layout = new javax.swing.GroupLayout(roundPanel2);
        roundPanel2.setLayout(roundPanel2Layout);
        roundPanel2Layout.setHorizontalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(roundPanel2Layout.createSequentialGroup()
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                            .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jFormattedTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(54, 54, 54)
                        .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel6)
                            .addComponent(jLabel10)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(52, 52, 52)
                        .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel2Layout.createSequentialGroup()
                                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel112))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel2Layout.createSequentialGroup()
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(roundPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel2Layout.createSequentialGroup()
                .addContainerGap(691, Short.MAX_VALUE)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(roundPanel2Layout.createSequentialGroup()
                        .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel182))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel2Layout.createSequentialGroup()
                                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jFormattedTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(28, 28, 28)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(roundPanel2Layout.createSequentialGroup()
                        .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21)
                            .addComponent(jLabel19)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jFormattedTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        roundPanel2Layout.setVerticalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel112, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel182, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jFormattedTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jCheckBox1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
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

    private void jLabel34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLabel34ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel34ActionPerformed

    private void jLabel36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLabel36ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel36ActionPerformed

    private void jLabel38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLabel38ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel38ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Select Customer
        PanelCustomerManagement customer = new PanelCustomerManagement();
        customer.setVisible(true);
        customer.setInvoice(this);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased

        String barcode = jTextField4.getText();
        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            
            PanelStockManagement stock = new PanelStockManagement();
            stock.setVisible(true);
            stock.setInvoice(this);
            stock.setBarcode(barcode);
        }
    }//GEN-LAST:event_jTextField4KeyReleased

    private void jLabel11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLabel11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel11ActionPerformed

    private void jLabel13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLabel13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel13ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        String stockId = getSelectedStockId();
        String brand = jLabel11.getText();
        String barcode = jTextField4.getText();
        String name = jLabel13.getText();
        String mfg = jLabel18.getText();
        String exp = jLabel22.getText();        
        String avai = jLabel25.getText();
        String sellingPrice = jLabel23.getText();
        String qty = jFormattedTextField2.getText();
        
        if(!qty.isEmpty()){
            InvoiceItem invoiceItem = new InvoiceItem();
            invoiceItem.setBarcode(barcode);
            invoiceItem.setStockId(stockId);
            invoiceItem.setBrand(brand);
            invoiceItem.setName(name);
            invoiceItem.setMfg(mfg);
            invoiceItem.setExp(exp);
            invoiceItem.setSellingPrice(sellingPrice);
            invoiceItem.setQty(qty);
            
            if(Double.parseDouble(qty)<Double.parseDouble(avai)){
                if (invoiceItemMap.get(stockId) == null) {
                    invoiceItemMap.put(stockId, invoiceItem);
                } else {

                    InvoiceItem found = invoiceItemMap.get(stockId);

                    int option = JOptionPane.showConfirmDialog(this, "Do you want to update the quantity of the product : " + name, "Message",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                      
            
                    if (option == JOptionPane.YES_OPTION) {
                        double updatedQuantity = Double.parseDouble(found.getQty()) + Double.parseDouble(qty);
                        if(updatedQuantity>Double.parseDouble(avai)){
                            JOptionPane.showMessageDialog(this, "Stock doesn't have an enough item quantity to update!", "Warning", JOptionPane.WARNING_MESSAGE);                    
                        }else{
                            found.setQty(String.valueOf(updatedQuantity));
                        }                
                    }
                    
                }
                reset();
                jTextField4.grabFocus();
            }else{
                JOptionPane.showMessageDialog(this, "Stock doesn't have enough quantity of these items to add into the invoice!", "Warning", JOptionPane.WARNING_MESSAGE); 
            }

            loadInvoiceItems();
            
        }else{
            JOptionPane.showMessageDialog(this, "Please set the buying quantity!", "Warning", JOptionPane.WARNING_MESSAGE);
        }

        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
reset();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jLabel18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLabel18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel18ActionPerformed

    private void jLabel22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLabel22ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel22ActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
       // payment method
        calculate();
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jCheckBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox1ItemStateChanged
        // withdraw points
        calculate();
    }//GEN-LAST:event_jCheckBox1ItemStateChanged

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       // Insert

        try {
            String id = String.valueOf(System.currentTimeMillis());
            String customerMobile = jTextField2.getText();
            String employeeName = jLabel36.getText();
            String employeeNic = userBean.getNic();
            String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String billAmount = jLabel28.getText();
            String paymentMethodId = paymentMethodMap.get(String.valueOf(jComboBox1.getSelectedItem()));

            //add to invoice            
            String query = "INSERT INTO `invoice`(`in_id`,`date_time`,`paid_amount`,`pm_id`,`e_nic`,`cu_mobile`) VALUES('" + id + "','" + dateTime + "','" + billAmount + "','" + paymentMethodId + "','" + employeeNic + "','" + customerMobile + "')";
            MySql.execute(query);

            //add to invoice item '" + item.getStockId() + "'
            for (InvoiceItem item : invoiceItemMap.values()) {

                MySql.execute("INSERT INTO `invoice_item`(`s_id`,`ii_qty`,`in_id`) VALUES('1','" + item.getQty() + "','" + id + "')");

                //update stock
                MySql.execute("UPDATE `stock` SET `s_qty` = `s_qty`-'" + item.getQty() + "' WHERE `s_id`='1'");
            }

            //customer points
            double points = Double.parseDouble(jLabel29.getText()) / 100;

            if (withdrawPoints) {

                newPoints += points;
                MySql.execute("UPDATE `customer` SET `points` = '" + newPoints + "' WHERE `cu_mobile`='" + customerMobile + "'");
            } else {
                MySql.execute("UPDATE `customer` SET `points` = `points`+'" + points + "' WHERE `cu_mobile`='" + customerMobile + "'");
            }

            //jasper report generation
            String path = "src//com//Reports//invoiceReport.jasper";
            
            HashMap<String, Object> parameters = new HashMap<>();
            
            parameters.put("Parameter1", jLabel29.getText());
            parameters.put("Parameter3", jLabel28.getText());
            parameters.put("Parameter4", String.valueOf(jComboBox1.getSelectedItem()));
            parameters.put("Parameter5", jFormattedTextField1.getText());
            parameters.put("Parameter6", jLabel32.getText());            
            parameters.put("Parameter7", id);
            parameters.put("Parameter8", customerMobile);
            parameters.put("Parameter9", employeeName);
            parameters.put("Parameter10", dateTime);
            
            JRTableModelDataSource dataSource = new JRTableModelDataSource(jTable1.getModel());
            JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameters, dataSource);
            JasperViewer.viewReport(jasperPrint, false);
            
            clearInvoice();
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jFormattedTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField1KeyReleased
       // payment

        calculate();
    }//GEN-LAST:event_jFormattedTextField1KeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        clearInvoice();
        
    }//GEN-LAST:event_jButton3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JFormattedTextField jFormattedTextField4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JTextField jLabel11;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JTextField jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JTextField jLabel18;
    private javax.swing.JLabel jLabel182;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JTextField jLabel22;
    private javax.swing.JFormattedTextField jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JFormattedTextField jLabel25;
    private javax.swing.JFormattedTextField jLabel28;
    private javax.swing.JFormattedTextField jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JFormattedTextField jLabel32;
    private javax.swing.JTextField jLabel34;
    private javax.swing.JTextField jLabel36;
    private javax.swing.JTextField jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField4;
    public com.swing.RoundPanel roundPanel2;
    // End of variables declaration//GEN-END:variables
}
