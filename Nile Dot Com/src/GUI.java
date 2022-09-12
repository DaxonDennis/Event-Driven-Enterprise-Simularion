/*  Name: Daxon Dennis
     Course: CNT 4714 – Fall 2022 
     Assignment title: Project 1 – Event-driven Enterprise Simulation 
     Date: Sunday September 15, 2022 
*/ 


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI implements ActionListener{
	
	private static JLabel IdFieldLb,QuantityFieldLb,DetailsFieldLb,SubtotalFieldLb;
	private static JFrame frame;
	private static JButton ProcessItemBtn,ConfirmItemBtn,ViewOrderBtn,FinishOrderBtn,NewOrderBtn,ExitBtn;
	private static JTextField IdField,QuantityField,DetailsField,SubtotalField;
	private static JPanel panel;
	ScannerClass s = new ScannerClass();
	static ArrayList<String> list = new ArrayList<String>();
	static ArrayList<String> transactionList = new ArrayList<String>();
	static ArrayList<Float> subtotalList = new ArrayList<Float>();
	static StringBuilder builder = new StringBuilder();
	static StringBuilder transactionBuilder = new StringBuilder();
	static int field1 = 1;
	static int field0 = 0;
	static DecimalFormat df = new DecimalFormat("0.00");
	static String timeStamp = new SimpleDateFormat("MM/dd/yyyy, hh:mm:ss a z").format(Calendar.getInstance().getTime());
	static String transactionTimeStamp = new SimpleDateFormat("ddMMYYYYHHmm").format(Calendar.getInstance().getTime());
	
	public static void main(String[] args) {
	
	   frame = new JFrame();
	   
	   IdFieldLb = new JLabel();
	   IdFieldLb.setText("Enter item ID for Item #" +  field1 + ":");
	   IdField = new JTextField(20);
	   IdField.setBounds(50,100,200,30);
	   
	   QuantityFieldLb = new JLabel();
	   QuantityFieldLb.setText("Enter quantity for Item # "+ field1 + ":");
	   QuantityField = new JTextField(20);
	   QuantityField.setBounds(50,100,200,30);
	   
	   DetailsFieldLb = new JLabel();
	   DetailsFieldLb.setText("Details for Item #"+ field0 + ":");
	   DetailsField = new JTextField(20);
	   DetailsField.setEditable(false);
	   DetailsField.setBounds(50,100,200,30);

	   
	   SubtotalFieldLb = new JLabel();
	   SubtotalFieldLb.setText("Order subtotal for " + list.size() + " item(s):");
	   SubtotalField = new JTextField(20); 
	   SubtotalField.setEditable(false);
	   SubtotalField.setBounds(50,100,200,30);
	   
	   
	   ProcessItemBtn = new JButton("Process Item # " + field1 );
	   ProcessItemBtn.setBounds(50,100,200,30);
	   ProcessItemBtn.addActionListener(new GUI());
	   
		
		
	    ConfirmItemBtn = new JButton("Confirm Item # "+ field1);
		ConfirmItemBtn.setBounds(50,100,200,30);
		ConfirmItemBtn.setEnabled(false);
	    ConfirmItemBtn.addActionListener(e -> {

	    	
			SubtotalField.setText("$" + String.valueOf(df.format(sum())));
	    	field1++;
	    	
			ProcessItemBtn.setEnabled(true);
			ConfirmItemBtn.setEnabled(false);
			ViewOrderBtn.setEnabled(true);
			FinishOrderBtn.setEnabled(true);
			
			JOptionPane.showMessageDialog(null, "Item added to cart", "Item Confirmed", JOptionPane.INFORMATION_MESSAGE);
			IdField.setText(null);
			QuantityField.setText(null);
			Titles();
		});
		
		
	    ViewOrderBtn = new JButton("View Order");
		ViewOrderBtn.setBounds(50,100,200,30);
		ViewOrderBtn.setEnabled(false);
		ViewOrderBtn.addActionListener(e-> {
			
		
		    for (int i = 0, position = 1; i < list.size(); i++, position++) {
		    	builder.append(position + ": " + list.get(i) + "\n");
		    }
		    
		    JOptionPane.showMessageDialog(null, builder, "Your Cart:", JOptionPane.INFORMATION_MESSAGE); 
		    builder.setLength(0);
		    
		});
		
		FinishOrderBtn = new JButton("Finish Order");
		FinishOrderBtn.setBounds(50,100,200,30);
		FinishOrderBtn.setEnabled(false);
		FinishOrderBtn.addActionListener(e -> {
			
			for (int i = 0, position = 1; i < list.size(); i++, position++) {
		    	builder.append(position + ": " + list.get(i) + "\n");
		    }

			double taxAmount = (sum() * 0.06) ;
			String finalInvoice = "Date:" +  timeStamp +"\n\n" 
		                          + "Number of line items: " + list.size() + "\n\n" 
					              + "Item# / ID / Title / Price / Qty / Disc % / Subtotal: \n\n" 
		                          + builder + "\n\n"
		                          + "Order subtotal: " + df.format(sum()) + "\n\n" 
		                          + "Tax rate:   6% \n\n"  
		                          + "Tax amount:    " + "$" +df.format(taxAmount) + "\n\n"
		                          + "ORDER TOTAL:    " + "$" + (df.format(sum()+taxAmount)) + "\n\n"
		                          + "Thanks for shopping at Nile Dot Com!";
			JOptionPane.showMessageDialog(null, finalInvoice, "Final Invoice", JOptionPane.INFORMATION_MESSAGE);
			for (int i = 0, position = 1; i < transactionList.size(); i++, position++) {
		    	transactionBuilder.append(transactionTimeStamp + ", " + transactionList.get(i) + "\n");
		    }
			try {
				
				FileWriter myWriter = new FileWriter("src/transaction.txt",true);
				myWriter.write("" + transactionBuilder);
				myWriter.close();
			} catch (IOException e1) {

				e1.printStackTrace();
			}
			transactionBuilder.setLength(0);

			
			IdField.setEditable(false);
			QuantityField.setEditable(false);
		});
		
		NewOrderBtn = new JButton("New Order");
		NewOrderBtn.setBounds(50,100,200,30);
		NewOrderBtn.addActionListener(e  -> {
			
			ProcessItemBtn.setEnabled(true);
			ConfirmItemBtn.setEnabled(false);
			IdField.setEditable(true);
			QuantityField.setEditable(true);
			IdField.setText(null);
			QuantityField.setText(null);
			DetailsField.setText(null);
			SubtotalField.setText(null);
			ViewOrderBtn.setEnabled(false);
			FinishOrderBtn.setEnabled(false);
			list.clear();
			subtotalList.clear();
		    builder.setLength(0);
		    field1 = 1;
		    field0 = 0;
		    Titles();
		});
		
		
		ExitBtn = new JButton("Exit");
		ExitBtn.setBounds(50,100,200,30);
		ExitBtn.addActionListener(new ExitBtnListener());
		
	    panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		panel.setLayout(new GridLayout(0,1));
		
		panel.add(IdFieldLb);
		panel.add(IdField);
		panel.add(QuantityFieldLb);
		panel.add(QuantityField);
		panel.add(DetailsFieldLb);
		panel.add(DetailsField);
		panel.add(SubtotalFieldLb);
		panel.add(SubtotalField);
		   
		panel.add(ProcessItemBtn);
		panel.add(ConfirmItemBtn);
		panel.add(ViewOrderBtn);
		panel.add(FinishOrderBtn);
		panel.add(NewOrderBtn);
		panel.add(ExitBtn);
		
			
		frame.add(panel, BorderLayout.CENTER);
		frame.setSize(800,1200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Nile Dot Com");
		frame.setVisible(true); 
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		field0++;
		DetailsFieldLb.setText("Details for Item #"+ field0 + ":");
		ProcessItemBtn.setEnabled(false);
		ConfirmItemBtn.setEnabled(true);
		
		String Idinput = IdField.getText();
	    String Quantity = QuantityField.getText();
	    
		try {
			ScannerClass.main(Idinput, Quantity);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	public static void UserOutput1(String UserOutput, Float subtotal, String userOutputTransactionFile) {
		DetailsField.setText(null);
		DetailsField.setText(UserOutput);
		list.add(UserOutput);
		transactionList.add(userOutputTransactionFile);
		subtotalList.add(subtotal);
	return;
	}
	
	public static double sum() {
		double sum = 0;
		for(int i = 0; i < subtotalList.size(); i++)
		{
			sum = sum + subtotalList.get(i);
		}
	return sum;
	}
	
	public static String Titles() {
		SubtotalFieldLb.setText("Order subtotal for " + list.size() + " item(s):");
		IdFieldLb.setText("Enter item ID for Item #" +  field1 + ":");
		QuantityFieldLb.setText("Enter quantity for Item # "+ field1 + ":");
		DetailsFieldLb.setText("Details for Item #"+ field0 + ":");
	return null;
	}
	
	 public static String Clear() {
		ProcessItemBtn.setEnabled(true);
		ConfirmItemBtn.setEnabled(false);
		IdField.setText(null);
		QuantityField.setText(null);
	return null;
	 }
	}
	

