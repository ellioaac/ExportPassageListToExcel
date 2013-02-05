package ellioaac;

import java.io.File;

// TODO finish commenting

public class Utils {

	// TODO do I need these?
	// TODO do I need these to be public?
	
	// extensions to be used in other classes
    public final static String txt = "txt";
    public final static String xls = "xls";
    public final static String xlsx = "xlsx";

    /**
     * Gets the extension of a file.
     */  
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}
