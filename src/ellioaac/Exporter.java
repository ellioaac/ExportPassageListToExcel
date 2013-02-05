package ellioaac;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * This class will read in verses and references from a text file and then
 * export them into Excel.
 * 
 * @author elliotta
 * 
 */
public class Exporter {

	/**
	 * Runs the program.
	 * 
	 * @param args
	 *            Ignored
	 */
	public static void main(String[] args) {

		Exporter exporter = new Exporter();
		exporter.run();  // runs the program

	}
	
	/**
	 * List of references to export to Excel
	 */
	private ArrayList<String> refs = null;

	/**
	 * List of verses to export to Excel
	 */
	private ArrayList<String> verses = null;

	// TODO is this constructor needed?
	
	/**
	 * Creates an Exporter by initializing some internals
	 */
	public Exporter() {

		// initialize variables
		refs = new ArrayList<String>();
		verses = new ArrayList<String>();
		
	}
	
	/**
	 * Starts the program and handles remaining exceptions.
	 */
	public void run() {
		
		// read verses in from a text file
		readVerses();

		// TODO handle exceptions
		try {
			// export verses into Excel
			
			// TODO currently the Excel document is created but no text 
			// has been added to the worksheet except for the title.
			exportVerses();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads in verses from a text file. Assumes the only text in the file are
	 * references and verses, no extra content is allowed.
	 */
	public void readVerses() {

		// allows user to select input file in a window
		JFileChooser chooser = new JFileChooser();

		// only shows .txt files
		chooser.setFileFilter(new TxtFileFilter());

		// setup FileChooser
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File inFile = chooser.getSelectedFile();

			Scanner scan = null;

			try {
				scan = new Scanner(inFile); // instantiate Scanner
				
				// following code reads in the file and stores the verses 
				// and references.
				
				/*
				 * Logos exports the passage list so that the first line contains
				 * a reference and the following line contains the related verse.
				 * The format continues with pairs of lines in that the first
				 * line of the pair is always a reference and the second line is 
				 * always the verse associated with that reference.
				 */
				int lineNumber = 0;
				while (scan.hasNextLine()) {

					String line = scan.nextLine();

					// if line number is even then add to refs
					if ((lineNumber % 2) == 0) {
						refs.add(line);
					}
					// if line number is odd than add to verses
					else if ((lineNumber % 2) == 1) {
						verses.add(line);
					}

					lineNumber++;
				}

				// TODO handle exceptions
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				scan.close();
			}
		} // end if

	} // end readVerses()

	/**
	 * Exports verses into an Excel workbook.
	 * 
	 * @throws IOException
	 * @throws WriteException
	 */
	public void exportVerses() throws IOException, WriteException {

		// TODO allow for headers on columns if user wants

		// allows user to choose where to save output file
		JFileChooser save = new JFileChooser();
		
		//only allows .xls and .xlsx files
		save.setFileFilter(new ExcelFileFilter());
		
		// setup file chooser
		int returnVal = save.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File outFile = save.getSelectedFile();

			// This is pulling from the Java Excel API
			// the JEAPI library is packaged with this software.
			WritableWorkbook workbook = null;
			WritableSheet sheet = null;

			// create workbook
			workbook = Workbook.createWorkbook(outFile);

			// create sheet with a specified title
			sheet = workbook.createSheet("Verses", 0);

			// TODO shoudn't I add starting at row = 1 or 2 
			// to allow for a header?
			
			// add the references to column A starting at row 0
			for (int i = 0; i < refs.size(); i++) {
				Label label = new Label(0, i, refs.get(i));
				sheet.addCell(label);
			}

			// add verses to column B starting at row 0
			for (int i = 0; i < verses.size(); i++) {
				Label label = new Label(1, i, verses.get(i));
				sheet.addCell(label);
			}

			// finalize the workbook
			workbook.write();
			workbook.close();
		} // end if
	} // end exportVerses()

} // end Exporter
