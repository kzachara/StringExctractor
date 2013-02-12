package com.adacom.workshop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.List;

import com.google.code.externalsorting.ExternalSort;

/**
 * This program reads the contents of two input plain text files having one word per line
 * and writes in an output file the words that are common , ordered lexicographically.
 * 
 * The ExternalSort utility (http://code.google.com/p/externalsortinginjava/) is used to sort
 * the input files and facilitate strings extraction. 
 * @author Konstantinos Zacharakis (kzachara@gmail.com)
 * @since 0.2
 *
 */
public class StringExtractorTwoFiles extends ExternalSort {

	private static File sortFile(File input) throws IOException {

		File output = File.createTempFile(input.getName(), "out");
		output.deleteOnExit();

		Comparator<String> comparator = new Comparator<String>() {
			public int compare(String r1, String r2) {
				return r1.compareTo(r2);
			}
		};
		
		List<File> l = sortInBatch(input, comparator);
		
		mergeSortedFiles(l, output, comparator);

		return output;
	}
	
	/**
	 * Compares the content of two files and stores all common String in a third file.
	 * First the method sorts each file lexicographically and reading line by line extracts
	 * all common strings.
	 * @param 		f1 	The first input files
	 * @param 		f2 	The second input file 
	 * @param 		out	The output files containing common strings
	 * @throws IOException When program fails to write or read files
	 */
	private static void compare(File f1, File f2, File out) throws IOException {
		File s1 = sortFile(f1);
		File s2 = sortFile(f2);
		
		BufferedReader fbr1 = new BufferedReader(new InputStreamReader(new FileInputStream(s1)));
		BufferedReader fbr2 = new BufferedReader(new InputStreamReader(new FileInputStream(s2)));
		BufferedWriter w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out)));
		
		String line1 = "";
		String line2 = "";
		
		try {		
			while(line1 != null && line2 != null){
				if (line1.compareTo(line2) == 0){
					// If lines are equal store in output
					if (line1.length() > 0){
						w.write(line1);
						w.newLine();
					}
					
					line1 = fbr1.readLine();
					line2 = fbr2.readLine();
				}
				// Read next line from file2
				else if(line1.compareTo(line2) > 0){
					line2 = fbr2.readLine();
				}
				// Read next line from file1
				else if (line1.compareTo(line2) < 0){
					line1 = fbr1.readLine();
				}
			}
		}
		// Close readers/writer
		finally {
			fbr1.close();
			fbr2.close();
			w.close();			
		}		
	}
	
	/**
	 * The main method
	 * @param args
	 * @throws FileNotFoundException If any of the input files are not found.
	 * @throws IOException When program fails to write to the output file.
	 */
	public static void main(String[] args) throws IOException {
		
		if (args.length < 3){
			System.err.println("Invalid number of arguments. Please provide the paths of two input files and one output file. \r\n Usage: InputFile1 InputeFile2 OutputFile");
			System.exit(-1);
		}
		
		compare(new File(args[0]), new File(args[1]), new File(args[2]));
		
	}

}
