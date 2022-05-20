package photo_renamer;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;

public class Log{
	 
	 // singleton class
	 private static final Log instance = new Log();
	 private static TagManager tagManager = TagManager.getInstance();
	 private static ImageManager imageManager = ImageManager.getInstance();
	 private static Calendar cal = Calendar.getInstance();
	 private static DateFormat dateFormat = new SimpleDateFormat("yyy/MM/dd HH:mm:ss");
	 
	 
	 private static ObjectOutputStream tagOs;
	 private static ObjectOutputStream logOs;
	 private static ObjectInputStream tagIs;
	 private static ObjectInputStream logIs;
	 
	 private static FileWriter fw;
	 private static BufferedWriter writer;
	 private static final String tagFileName = "tags.bin";
	 private static final String logFileName = "log.bin";
	 private static final String history = "history.txt";
	 
	 private Log() { }
	 
	 public static Log getInstance(){
		 return instance;
	 }
	 
	 public void writeHistory(Image i, String newName) {
		 try {
			 fw = new FileWriter(history, true);
			 writer = new BufferedWriter(fw);
			 PrintWriter out = new PrintWriter(writer);
			 out.println(dateFormat.format(cal.getTime()) + " | FILE: " + i.getOriginalName() 
			 				+ " | OLD: " + i.getName() + " | NEW: " + newName);
			 out.close();
			 System.out.println("Done Writing to history");
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		 
	 }
	 
	 public void updateImageLog() {
		 try {
			 logOs = new ObjectOutputStream(new FileOutputStream(logFileName));
			 logOs.writeObject(imageManager);
			 logOs.close();
			 System.out.println("Done Writing to image log");
		 } catch (FileNotFoundException e) {
			 e.printStackTrace();
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
		 
	 }
	 
	 public void updateConfig() {
		 try {
			 tagOs = new ObjectOutputStream(new FileOutputStream(tagFileName));
			 tagOs.writeObject(tagManager);
			 tagOs.close();
			 System.out.println("Done Writing to config");
		 } catch (FileNotFoundException e) {
			 System.out.println("tags.bin File Not Found");
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
		 
	 }
	 
	 //TODO 
	 public void readImageLog() {
		 try {
			 logIs = new ObjectInputStream(new FileInputStream(logFileName));
			 imageManager = (ImageManager) logIs.readObject();
			 System.out.println(imageManager.toString());
			 logIs.close();
			 System.out.println("Done reading from logs.bin");
		 } catch (FileNotFoundException e) {
			 System.out.println("logs.bin File Not Found");
		 } catch (IOException e) {
			 e.printStackTrace();
		 } catch (ClassNotFoundException e) {
			 e.printStackTrace();
		 }
	 }
	 
	 public HashMap<String, ArrayList<Image>> readConfig() {
		 try {
			 tagIs = new ObjectInputStream(new FileInputStream(tagFileName));
			 tagManager = (TagManager) tagIs.readObject();
			 tagIs.close();
			 StringBuilder out = new StringBuilder();
			 for (Entry<String, ArrayList<Image>> entry : tagManager.Tags.entrySet()) {
				 out.append(entry.getKey()+ " ");
				 for (Image i : entry.getValue()) {
					 out.append(i.getOriginalName());
				 }
				 System.out.println(out);
				 out = new StringBuilder();
			 }
			 System.out.println("Done Reading from tags.bin");
		 } catch (FileNotFoundException e) {
			 System.out.println("tags.bin File Not Found");
		 } catch (IOException e) {
			 e.printStackTrace();
		 } catch (ClassNotFoundException e) {
			 e.printStackTrace();
		 }
		 return tagManager.Tags;
	 }
}
