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

		new Exporter();

	}
	
	/**
	 * List of references to export to Excel
	 */
	private ArrayList<String> refs = null;

	/**
	 * List of verses to export to Excel
	 */
	private ArrayList<String> verses = null;

	/**
	 * Creates an Exporter.
	 */
	public Exporter() {

		// initialize
		refs = new ArrayList<String>();
		verses = new ArrayList<String>();

		readVerses();

		// TODO handle exceptions
		try {
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

		// allows user to select input file
		JFileChooser chooser = new JFileChooser();

		// only shows .txt files
		chooser.setFileFilter(new TxtFileFilter());

		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File inFile = chooser.getSelectedFile();

			Scanner scan = null;

			try {
				scan = new Scanner(inFile);

				// read in verses
				// count is used to determine if even or odd term
				// will add item to refs if odd, add to verses if even
				int count = 0;
				System.out.println("before loop");
				while (scan.hasNextLine()) {

					String line = scan.nextLine();

					// if even (reference)
					if ((count % 2) == 0) {
						refs.add(line);
					}
					// if odd (verse)
					else if ((count % 2) == 1) {
						verses.add(line);
					}

					count++;
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

		// TODO need to put a filter on save fileChooser to limit to only xls
		// files

		// allows user to choose where to save output file
		JFileChooser save = new JFileChooser();
		
		//only allows .xls and .xlsx files
		save.setFileFilter(new ExcelFileFilter());
		
		int returnVal = save.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File outFile = save.getSelectedFile();

			WritableWorkbook workbook = null;
			WritableSheet sheet = null;

			// create workbook
			workbook = Workbook.createWorkbook(outFile);

			// create sheet
			sheet = workbook.createSheet("Verses", 0);

			// add references to column A starting at row 0
			for (int i = 0; i < refs.size(); i++) {
				Label label = new Label(0, i, refs.get(i));
				sheet.addCell(label);
			}

			// add verses to column B starting at row 0
			for (int i = 0; i < verses.size(); i++) {
				Label label = new Label(1, i, verses.get(i));
				sheet.addCell(label);
			}

			workbook.write();
			workbook.close();
		} // end if
	} // end exportVerses()

} // end Exporter
