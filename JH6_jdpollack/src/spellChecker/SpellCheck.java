package spellChecker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class SpellCheck {

	private HashSet<String> dictionary = new HashSet<String>();

	private TreeSet<String> miss_spelled_words = new TreeSet<String>();

	public SpellCheck() throws FileNotFoundException
	{
		try (Scanner sc = new Scanner(new File("dictionary.txt"))){
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				dictionary.add(line);
			}
			sc.close();
		}
	}

	public void checkSpelling(String fileName) throws FileNotFoundException
	{
		System.out.println("======== Spell checking " + fileName + " =========");

		miss_spelled_words.clear();

		String line = "";
		int lineNumber = 1;
		Scanner input = new Scanner(new File(fileName));
		Scanner userInput = new Scanner(System.in);

		while (input.hasNextLine()){
			line = input.nextLine();
			StringTokenizer st = new StringTokenizer(line, " ()\t,.;:-%'\"");
			while (st.hasMoreTokens())
			{
				String word = st.nextToken();
				word = word.toLowerCase();
				if(Character.isLetter(word.charAt(0)))
				{
					if (dictionary.contains(word)==false && miss_spelled_words.contains(word)==false)
					{
						if (word.charAt(word.length() - 1) == 's')
						{
							word = word.substring(0, word.length() -1);						
						}						
						System.out.println(lineNumber + " :  " + line);
						lineNumber += 1;
						System.out.println(word + " not in dictionary.  Add to dictionary? (y/n)");
						char command = userInput.next().charAt(0);
						if (command == 'y'){
							dictionary.add(word);
							System.out.println(word + " has been added to the dictionary.");
							System.out.println();
						}
						else{
							System.out.println();
							miss_spelled_words.add(word);
						}
					}
				}
			}
		}
		input.close();
	}

	public void dump_miss_spelled_words()
	{
		Iterator iterator = miss_spelled_words.iterator();
		while (iterator.hasNext())
			System.out.println(iterator.next());
	}

	public static void main(String[] args) {

		try {
			SpellCheck spellCheck = new SpellCheck();

			for (int i=0; i < args.length; i++)
			{
				spellCheck.checkSpelling(args[i]);
				spellCheck.dump_miss_spelled_words();
			}            
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println(e);
		}
	}
}