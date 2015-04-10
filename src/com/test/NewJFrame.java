/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileReader;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.jfree.chart.ChartPanel;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



/**
 *
 * @author rpc-37
 */
public class NewJFrame extends javax.swing.JFrame {

	
	private Color color=Color.GREEN;
	private javax.swing.JButton jDiagram;
	private javax.swing.JButton jRead;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JButton jColor;
	private String[] options;
	private LineChart demo;
	private JTextField jText;
	

	/**
     * Creates new form NewJFrame
     */
    public NewJFrame() {
        initComponents();
        setResizable(false);
    }

    
    @SuppressWarnings("unchecked")
    private void initComponents() {

    	jDiagram = new JButton();
    	jRead = new JButton();
    	jColor = new JButton();
    	jPanel1 = new javax.swing.JPanel();
    	      
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jDiagram.setText("Diagram");
        jDiagram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        
        jRead.setText("Read");
        jRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            
                jPanel1.setLayout(new java.awt.BorderLayout());
                demo = new LineChart("Line Chart Demo 6",3,jText.getText()+".txt",getDataFromJSON());
                jPanel1.add(new ChartPanel(demo.getChart()));
                jPanel1.validate();
            }
        });
        
        
        jColor.setText("Color");
        jColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            
            	Color initialColor = Color.GREEN;
                color = JColorChooser.showDialog(null, "JColorChooser Sample", initialColor);
                
                jPanel1.setLayout(new java.awt.BorderLayout());
                
                demo.setColor(color);
                jPanel1.add(new ChartPanel(demo.getChart()));
                jPanel1.validate();
               
            }
        });
        
       
        
        
        jText = new JTextField("Elso",20);


        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 264, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 278, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(39, 39, 39)
                .addComponent(jDiagram)
                .addGap(24, 24, 24)
                .addComponent(jText)
                .addGap(24, 24, 24)
                .addComponent(jRead)
                .addGap(24, 24, 24)
                .addComponent(jColor)
                .addGap(24, 24, 24))
                
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                .addContainerGap(149, Short.MAX_VALUE)
                .addComponent(jDiagram)
                .addGap(128, 128, 128))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(149, Short.MAX_VALUE)
                .addComponent(jColor)
                .addGap(128, 128, 128))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(149, Short.MAX_VALUE)
                .addComponent(jText)
                .addGap(128, 128, 128))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(149, Short.MAX_VALUE)
                .addComponent(jRead)
                .addGap(100, 128, 128))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed      
    	
           jPanel1.setLayout(new java.awt.BorderLayout());
           demo = new LineChart("Line Chart Demo 6",3,jText.getText()+".txt",null);
           jPanel1.add(new ChartPanel(demo.getChart()));
           jPanel1.validate();
           
          
    }
    
    
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
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }
    
    
    private List<XYDataItem> getDataFromJSON(){
    	
    	 JSONParser parser = new JSONParser();
    	 List<XYDataItem> adat = null;
         try {
             Object obj = parser.parse(new FileReader(
                     jText.getText()+".txt"));
  
             JSONObject jsonObject = (JSONObject) obj;
  
             String name = (String) jsonObject.get("Name");
             String numbers = (String) jsonObject.get("DataList");
             
             Gson gson = new Gson();
             adat = gson.fromJson(numbers,new TypeToken<List<XYDataItem>>(){}.getType());
             
             System.out.println("Read");
             System.out.println("Name: " + name);
             System.out.println("\nCompany List:"+adat.toString());

  
         } catch (Exception e) {
             e.printStackTrace();
         }
		return adat;
    }
    
   
}
