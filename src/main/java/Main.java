/**
 *
 */

/**
 * @author Ahmed Metwally
 *
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;



public class Main {

	// construct AST of the .java files
	public static ASTVisitorMod parse(char[] str) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(str);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		// Check for compilationUnits problems in the provided file
		IProblem[] problems = cu.getProblems();
		for(IProblem problem : problems) {
			// Ignore some error because of the different versions.
            if (problem.getID() == 1610613332) 		 // 1610613332 = Syntax error, annotations are only available if source level is 5.0
                continue;
            else if (problem.getID() == 1610613329) // 1610613329 = Syntax error, parameterized types are only available if source level is 5.0
                continue;
            else if (problem.getID() == 1610613328) // 1610613328 = Syntax error, 'for each' statements are only available if source level is 5.0
                continue;
            else
            {
            	// quit compilation if
    	        System.out.println("CompilationUnit problem Message " + problem.getMessage() + " \t At line= "+problem.getSourceLineNumber() + "\t Problem ID="+ problem.getID());

    	        System.out.println("The program will quit now!");
    	        System.exit(1);
            }
	    }

		// visit nodes of the constructed AST
		ASTVisitorMod visitor= new ASTVisitorMod();
		cu.accept(visitor);

	    return visitor;
	}

	// parse file in char array
	public static char[] getFileContents(String filePath) throws IOException {
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
		reader.close();
		return  fileData.toString().toCharArray();
	}

	// cli test:
	// cat test_files.txt | gradle execute

	public static void main(String[] args) throws IOException {

		ASTVisitorMod astVisitor;
		BufferedReader stdin = new BufferedReader(
									new InputStreamReader(System.in));
		System.out.println("file,h1,h2,N1,N2,vocabulary,length,"
							+ "calculated_length,volume,difficulty,effort,"
							+ "time,bugs");
		String line = "";
		while ((line = stdin.readLine()) != null){
			int distinctOperators = 0;
			int distinctOperands = 0;
			int operatorCount = 0;
			int operandCount = 0;

			char[] fileContents = getFileContents(line);
			// collectStatsFromCode(fileContents)

			astVisitor = parse(fileContents);
			distinctOperators += astVisitor.oprt.size();
			distinctOperands += astVisitor.names.size();

			for (int f : astVisitor.oprt.values()) {
				operatorCount += f;
			}

			for (int f : astVisitor.names.values()) {
				operandCount += f;
			}

			HalsteadMetrics hal = new HalsteadMetrics(distinctOperators,
										 distinctOperands, operatorCount,
										 operandCount);

			String csvLine = line + "," + astVisitor.oprt.size() + ","
							 + astVisitor.names.size() + "," + operatorCount
							 + "," + operandCount + "," + hal.getVocabulary()
							 + "," + hal.getProglen() + ","
							 + hal.getCalcProgLen() + "," + hal.getVolume()
							 + "," + hal.getDifficulty() + ","
							 + hal.getEffort() + "," + hal.getTimeReqProg()
							 + "," + hal.getTimeDelBugs();
			System.out.println(csvLine);
		}
	}
}