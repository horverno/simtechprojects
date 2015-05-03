/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.puff.diffsolver.output;


import hu.puff.diffsolver.gui.LineChart;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jfree.chart.ChartPanel;




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
    	
    	
    	
    	JButton saveButton = new JButton("Save");

		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				// Saving generated JSON
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"JSON file", "json");

				JFileChooser saveFileChooser = new JFileChooser();
				saveFileChooser.addChoosableFileFilter(filter);
				saveFileChooser.setFileFilter(filter);
				saveFileChooser.setSelectedFile(new File(".json"));
				
				// Demonstrate "Save" dialog:
				int rVal = saveFileChooser.showSaveDialog(NewJFrame.this);
				if (rVal == JFileChooser.APPROVE_OPTION) {
				
					JSONParser.getInstance().dataToJSON(
							MeasurementHandler.getInstance().getMathResult(),   //getMathResult
							saveFileChooser.getSelectedFile());
				}
				if (rVal == JFileChooser.CANCEL_OPTION) {
					System.out.println("Cancel");
				}

			}
		});

		JButton loadButton = new JButton("Load");

		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				// Loading JSON
				List<MeasurementItem> list = null;

				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"JSON file", "json");

				JFileChooser loadFileChooser = new JFileChooser();
				loadFileChooser.addChoosableFileFilter(filter);
				loadFileChooser.setFileFilter(filter);
				loadFileChooser.setSelectedFile(new File(".json"));

				int rVal = loadFileChooser.showOpenDialog(NewJFrame.this);
				if (rVal == JFileChooser.APPROVE_OPTION) {
					 list = JSONParser.getInstance()
							.getDataFromJSON(loadFileChooser.getSelectedFile());
					System.out.println(list.toString());
				}
				if (rVal == JFileChooser.CANCEL_OPTION) {
					System.out.println("Cancel");
				}
				
				 jPanel1.setLayout(new java.awt.BorderLayout());
	             demo = new LineChart("Line Chart Demo 6",list);
	             jPanel1.add(new ChartPanel(demo.getChart()));
	             jPanel1.validate();

			}
		});
    	
    	
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
                .addComponent(loadButton)
                .addGap(24, 24, 24)
                .addComponent(saveButton)
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
                .addComponent(saveButton)
                .addGap(100, 128, 128))
             .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(149, Short.MAX_VALUE)
                .addComponent(loadButton)
                .addGap(100, 128, 128))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        
        /**Kirajzolja a diagramot, null hely�re (2.sor!) az adatok kell be�rni (getMathResult!!!).... **/
        jPanel1.setLayout(new java.awt.BorderLayout());
        demo = new LineChart("Line Chart Demo 6",null);
        jPanel1.add(new ChartPanel(demo.getChart()));
        jPanel1.validate();
        /*****/
        
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed      
    	
           jPanel1.setLayout(new java.awt.BorderLayout());
           demo = new LineChart("Line Chart Demo 6",null);
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
    
    
    
   
}
