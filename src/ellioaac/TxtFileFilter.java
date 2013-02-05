package ellioaac;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class TxtFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		
		boolean isAccepted = false;
		
		// TODO can I combine this into one if statement?
		
		// allows user to select directories
		if (f.isDirectory()) {
			isAccepted = true;
		} else {

			// set filter to default to only show txt files
			String extension = Utils.getExtension(f);
			if (extension != null && extension.equals(Utils.txt)) {
				isAccepted = true;
			}
		}

		return isAccepted;
	}

	@Override
	public String getDescription() {
		return "*.txt files";
	}

}
