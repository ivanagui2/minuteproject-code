package net.sf.minuteProject.utils.io;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.lang.StringUtils;

public class FileUtils {

	public static String getFileFullPathFromFileInRootClassPath(String filePathInClassPath) {
		URL url = Thread.currentThread().getContextClassLoader().getResource(filePathInClassPath);
		if (url==null)
			return filePathInClassPath;//"RESOURCE NOT IN THE PATH";
		else {
			try {
				return new File(url.toURI()).getAbsolutePath();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return filePathInClassPath;
			}
		}
	}

	public static String getAbsolutePathFromPath(String inputPathInFile, String inputPathFromFile) {
		String inputAbsolutePath = getAbsolutePathFromPath(inputPathInFile);
		if (inputAbsolutePath!=null)
			return inputAbsolutePath;
		return getAbsolutePathFromPath(resolvePath(inputPathFromFile, inputPathInFile));
	}
	
	public static String getAbsolutePathFromPath(String inputPathInFile) {
		File file = new File (inputPathInFile);
//		file.getAbsolutePath();
		if (file.exists())
			try {
				return file.getCanonicalPath();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		return null;
//		try {
//			// is the inputPathInFile abs?
//			URL url = new URL ("file://"+inputPathInFile);
//			if (url==null) {
//				return null;
//			}		
//			else {
//				try {
//					URI uri = url.toURI();
//					uri.getPath();
//					File f  = new File(inputPathInFile);
//					//File f = new File(uri);
//					f.getAbsolutePath();
//					f.exists();
//					return new File(url.toURI()).getAbsolutePath();
//				} catch (URISyntaxException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					return null;
//				}				
//			}
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		}
	}
	
	public static String getRoot() {
		return getFileFullPathFromFileInRootClassPath(".");
	}

	public static String getFileFullPathFromFileInClassPath(String filePathInClassPath) {
		return getFileFullPathFromFileInRootClassPath(filePathInClassPath);
	}
	
	public static String getFileFullPath (String dir, String filename) {
		return getFileFullPath(null, dir, filename);
	}
	
	public static String getFileFullPath (
			String triggerFileInClassPath, 
			String dir, 
			String filename) {
		String rootPath;
		if (dir!=null)
			return rootPath = dir;
		else {
			if (triggerFileInClassPath==null)
				rootPath = getRoot();
			else {
				String context = getFileFullPathFromFileInClassPath(triggerFileInClassPath);
				rootPath = stripFileName(context);
			}
		}
		return resolvePath(rootPath, filename);
	}
	
	private static String getMinuteProjectHome() {
		return System.getenv("MP_HOME");
	}
	
	public static String stripFileName(String filename) {
//		return getAbsolutePathFromPath(filename);
		filename = StringUtils.replace(filename, "/", "\\");
		int i = StringUtils.lastIndexOf(filename, "\\");
		return StringUtils.substring(filename, 0, i);
	}
	

	public static String stripRelativePath(String filename) {
		int i = StringUtils.lastIndexOf(filename, "\\");
		return StringUtils.substring(filename, i);
	}
	
	public static String resolvePath (String root, String relativePath) {
		return root + "/" + relativePath;
	}

	
}
