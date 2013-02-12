package com.adacom.workshop;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import junit.framework.Assert;
import junit.framework.TestCase;


public class StringExtractorTest extends TestCase {
	
	
	public void testStringExtractor() throws Exception {
		String output = "samples/output.txt";
		StringExtractor.main(new String[] {
				"samples/input1.txt",
				"samples/input2.txt",
				output
		});
		
		File outputFile = new File(output);
		Assert.assertTrue(outputFile.exists());		
		
		Assert.assertEquals(
				readFileToString(new File("samples/output-expected.txt")), 
				readFileToString(outputFile));		
	}
	

	public void testStringExtractorTwoFiles() throws Exception {
		String output = "samples/outputnew.txt";
		
		StringExtractorTwoFiles.main(new String[] {
				"samples/input1.txt",
				"samples/input2.txt",
				output
		});
		
		File outputFile = new File(output);
		Assert.assertTrue(outputFile.exists());		
		
		Assert.assertEquals(
				readFileToString(new File("samples/output-expected.txt")), 
				readFileToString(outputFile));		
	}
	
	
	private String readFileToString(File file) throws FileNotFoundException {
		StringBuffer sb = new StringBuffer();
		
		Scanner scanner = new Scanner(file);
		
		while(scanner.hasNext()){
			sb.append(scanner.next());
			sb.append("\r\n");
		}
		return sb.toString();
	}
	
	

}
