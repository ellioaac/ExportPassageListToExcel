package ellioaac;

import java.io.File;

import javax.swing.filechooser.FileFilter;

// TODO add better comments

/**
 * Filters out all files except files used for Excel (*.xls and *.xlsx).
 */
public class ExcelFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		String extension = Utils.getExtension(f);
		if (extension != null) {
			if (extension.equals(Utils.xls) || extension.equals(Utils.xlsx)) {
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

	@Override
	public String getDescription() {
		return "All Excel files (.xls and .xlsx)";
	}

}
