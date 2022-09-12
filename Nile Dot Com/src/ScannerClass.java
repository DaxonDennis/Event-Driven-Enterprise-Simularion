
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

import javax.swing.JOptionPane;
import java.io.*;


public class ScannerClass {
static String timeStamp = new SimpleDateFormat("MM/dd/yyyy, hh:mm:ss a z").format(Calendar.getInstance().getTime());
private static Scanner scan;

		
		public static void main( String Idinput, String Quantity ) throws IOException{
			
			
			String filepath = ("src/inventory.txt");
			String line = "";
			boolean found = false;
			String stock = "true";
			
			String ID = ""; String Description = ""; String InStock = ""; String Price = "";
			
			try {
				scan = new Scanner(new File(filepath));
				scan.useDelimiter("[,\n]");
				while(scan.hasNext() && !found) {
				
					ID = scan.next();
					Description = scan.next();
					InStock = scan.next();
					Price = scan.next();
					
					if(ID.equals(Idinput) && InStock.equals(" true")){
	
						found = true;
						
					}
				}
				if (found) {
					
					float newPrice = Float.parseFloat(Price);
					int newQuantity = Integer.parseInt(Quantity);
					DecimalFormat df = new DecimalFormat("0.00");
					
					float subtotal = (newPrice * newQuantity);
					
					double percent = 0;
					if(newQuantity > 4) {
						
						percent = 10;
						subtotal -= (subtotal *= .10);
					}
					if(newQuantity > 9) {
						percent = 15;
						subtotal -= (subtotal *= .15);
					}
					if(newQuantity >= 15) {
						
						percent = 20;
						subtotal -= subtotal *= .20;
					}
					
					
				    String UserOutput = ID + Description + " " + Quantity + " " + percent + "% " + "$" + df.format(subtotal);
				    String userOutputTransactionFile = ID + ", " + Description + ", " + Price + ", " + Quantity + ", " + percent+ "%" + ", " +  "$" + df.format(subtotal) + ", " + timeStamp;
				    GUI.UserOutput1(UserOutput, subtotal, userOutputTransactionFile);
				}
				else {
					JOptionPane.showMessageDialog(null, "Item ID: " + Idinput + " not in file", "Error", JOptionPane.ERROR_MESSAGE);
					GUI.Clear();
					scan.close();
					
				}
			}
			catch(Exception e) {
				JOptionPane.showMessageDialog(null, "Error");
				
			}
			finally {
			}
			
				scan.close();
			}
		
			
}
		
