package com.adacom.workshop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * This program takes a list of input plain text files having one word per line
 * and writes in an output file the words that are common in all input files, ordered
 * lexicographically.  
 * @author Konstantinos Zacharakis (kzachara@gmail.com) 
 * @since 0.1
 *
 */
public class StringExtractor {
	
	private static final String CR_LF = "\r\n";


	/**
	 * Scans the lines of an input file and loads in a java.util.List 
	 * @param file The input File
	 * @return A java.util.List<String>. If file contains no lines, an empty list is returned
	 * @throws FileNotFoundException If file does not exist
	 */
	private static List<String> scanFile(File file) throws FileNotFoundException {
		
		List<String> words = new ArrayList<String>();
		
		Scanner scanner = new Scanner(file);
		
		while(scanner.hasNext()){
			words.add(scanner.next());
		}
		
		return words;
		
		
	}
	
	/**
	 * Loads all the words of the inputFiles and constructs a TreeSet including the common tokens.
	 * @param 		inputFiles 	A List of files to be read.
	 * @param 		outputFile 	The output file that will contain the common words of the input files. 
	 * 							The collection is sorted lexicographically.
	 * @throws FileNotFoundException If input files are not found.
	 * @throws IOException When program fails to write to the output file.
	 */
	private static void loadAndExtract(List<File> inputFiles, File outputFile) throws FileNotFoundException, IOException {
		
		TreeSet<String> commonWords = new TreeSet<String>();
		Iterator<File> filesIter = inputFiles.iterator();
		
		int i = 0;
		
		while (filesIter.hasNext()) {
			File file = (File) filesIter.next();
			List<String> words = scanFile(file);			
			
			if (i == 0 ){
				commonWords.addAll(words);
			}
			else {
				commonWords.retainAll(words);	
			}
			
			i++;			
		}
		
		writeCommonWordsInFile(outputFile, commonWords);			
	}
	
	/**
	 * Writes all the String members of the input collection in a file. Each word is placed in a single line.
	 * @param 		outputFile 	The file to be written. If file already exists its contents are replaced.
	 * @param 		commonWords The common words
	 * @throws IOException When program is unable to perform write operation in file 
	 */
	private static void writeCommonWordsInFile(File outputFile, TreeSet<String> commonWords) throws IOException {
		FileWriter writer = null;
				
		try {
			writer = new FileWriter(outputFile);
			
			Iterator<String >iter = commonWords.iterator();
			
			while (iter.hasNext()){
				writer.write(iter.next());
				writer.write(CR_LF);
				writer.flush();
			}	
		}
		catch (IOException ioe){
			throw ioe;
		}		
		finally {
			if (writer != null){				
				writer.close();		
			}
		}	
		
	}
	
	
	/**
	 * The main method
	 * @param args
	 * @throws FileNotFoundException If any of the input files are not found.
	 * @throws IOException When program fails to write to the output file.
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		if (args.length < 3){
			System.err.println("Invalid number of arguments. Please provide at least the paths of two input files and one output file. \r\n Usage: [InputFile1 InputeFile2...] OutputFile");
			System.exit(-1);
		}
		
		List<File> inputFiles = new ArrayList<File>();
		
		for (int i = 0; i < args.length - 1; i++) {
			inputFiles.add(new File(args[i]));
		}
		
		File outputFile = new File(args[args.length - 1]);

		loadAndExtract(inputFiles, outputFile);		
		
	}

}
