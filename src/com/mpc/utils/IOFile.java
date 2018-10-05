package com.mpc.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;

import org.apache.log4j.Logger;

/***
 * @author yovi.putra
 */
public class IOFile {
	private static Logger log = Logger.getLogger(IOFile.class.getClass().getSimpleName());
	/***
	 * @author yovi.putra
	 * @param dir
	 * @param filename
	 * @param content
	 * @param extention
	 * @return
	 */
	public static File WriteFile(String dir, String filename, String content, String extention, boolean isAppend) {
		log.info("Writing file " + dir);
		if (checkDirectory(dir, false)) {
			String ext = extention == null || extention.trim().isEmpty() ? "" : "." + extention;
			FileOutputStream fop = null;
			File file = null;
			dir += "/" + filename;

			try {
				file = new File(dir + ext);
				fop = new FileOutputStream(file, isAppend);

				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}

				// get the content in bytes
				byte[] contentInBytes = content.getBytes();

				fop.write(contentInBytes);
				fop.flush();
				fop.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fop != null) {
						fop.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			log.info("Write file " + dir + " done");
			return file;
		}
		log.info("Write file " + dir + " failed");
		return null;
	}

	public static String ReadFile(String pathfile){
    	BufferedReader br = null;
		FileReader fr = null;
		String content = "";
		try {
			fr = new FileReader(pathfile);
			br = new BufferedReader(fr);
			String sCurrentLine="";
			while ((sCurrentLine = br.readLine()) != null) {
				content += sCurrentLine +"\r\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return content;
	}
	/***
	 * @author yovi.putra
	 * @param path
	 * @param createIfNotAvailable
	 * @return
	 */
	public static boolean checkDirectory(String path, boolean createIfNotAvailable) {
		File folder = new File(path);
		boolean available = folder.exists();

		if (!available && createIfNotAvailable) {
			folder.mkdir();
			available = true;
		}
		return available;
	}
	
	public static File[] getFiles(File dir, FilenameFilter filenameFilter){
		return dir.listFiles(filenameFilter);
	}
}
