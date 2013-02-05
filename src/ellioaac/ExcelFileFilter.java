package ellioaac;

import java.io.File;

import javax.swing.filechooser.FileFilter;

// TODO can I use .xlsx?  Read EJAPI to see if compatible.

/**
 * Filters out all files except files used for Excel (*.xls and *.xlsx).
 */
public class ExcelFileFilter extends FileFilter {

	@Override
	public boolean accept(File file) {
		
		// TODO can I combine this into one if statement?
		
		// true if file should be accepted by filter
		boolean isAccepted = false;
		
		// allows user to select directories
		if (file.isDirectory()) {
			isAccepted = true;
		} else {

			// get extension of file and ensure is Excel compatible
			String extension = Utils.getExtension(file);
			if (extension != null &&
					(extension.equals(Utils.xls) || extension.equals(Utils.xlsx))) {
				isAccepted = true;
			}
		}

		return isAccepted;
	}

	@Override
	public String getDescription() {
		return "All Excel files (.xls and .xlsx)";
	}

}
