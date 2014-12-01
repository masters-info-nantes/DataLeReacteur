//~ package ;

import java.io.*;
import java.nio.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.regex.*;

public class Pma2xml {
	public static void main(String[] args) {
		for(int i=0;i<args.length;i++)
			System.out.println("args["+i+"]="+args[i]);
		if(args.length < 2)
			System.out.println("Usage : java Pma2xml /path/to/source/directory/pma.xml /path/to/target/diretory/target.xml");

		writeFile(pma2xml(readFile(args[0])),args[1]);;
	}
	
	public static String readFile(String filename) {
		Charset charset = Charset.forName("UTF-8");
		File inputFile = new File(filename);
		Path input = inputFile.toPath();
		
		String fileContent = "";
		
		try (BufferedReader reader = Files.newBufferedReader(input, charset)) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				if(line.length() > 0 && line.charAt(line.length()-1) == '>')
					fileContent += line+"\n";
				else
					fileContent += line+" ";
			}
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
		
		return fileContent;
	}
	
	public static void writeFile(String fileContent, String filename) {
		Charset charset = Charset.forName("UTF-8");
		File outputFile = new File(filename);
		Path output = outputFile.toPath();
		
		try (BufferedWriter writer = Files.newBufferedWriter(output, charset)) {
			writer.write(fileContent, 0, fileContent.length());
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
	}
	
	public static String pma2xml(String fileContent) {		
		Pattern pattern;
		
		pattern = Pattern.compile("\n\\s*<!--(.*)-->");
		fileContent = pattern.matcher(fileContent).replaceAll("");
		
		pattern = Pattern.compile("<(/)?pma_xml_export(.*)?>(\n)?");
		fileContent = pattern.matcher(fileContent).replaceAll("<$1document>$2");
		
		pattern = Pattern.compile("<(/)?database(.*)?>(\n)?");
		fileContent = pattern.matcher(fileContent).replaceAll("<$1data>$2");
		
		pattern = Pattern.compile("<(/)?table(.*)?>(\n)?");
		fileContent = pattern.matcher(fileContent).replaceAll("<$1element>$2");
		
		pattern = Pattern.compile("<column name=\"(.*)\">(\n)?(.*)</column>");
		fileContent = pattern.matcher(fileContent).replaceAll("<$1>$3</$1>");
		
		pattern = Pattern.compile(">(\n*\\s*)*<");
		fileContent = pattern.matcher(fileContent).replaceAll("><");
		
		pattern = Pattern.compile("\\s+");
		fileContent = pattern.matcher(fileContent).replaceAll(" ");
		
		//~ pattern = Pattern.compile("\n");
		//~ fileContent = pattern.matcher(fileContent).replaceAll("");
		
		return fileContent;
	}
	
	
	
}
