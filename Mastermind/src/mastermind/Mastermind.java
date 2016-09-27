package mastermind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Mastermind {
	
	private static Scanner scanner;
	private static String input;
	private static String answer;
	private static boolean useCharacters;
	private static int numTries;
	private static int length;
	
	private static int numCorrect, numClose;
	
	public static void main(String[] args){
		
		//initialize fields
		scanner = new Scanner(System.in);
		numTries = 0;
		
		//settings
		length = 6;
		useCharacters = true;
		
		if(useCharacters)
			System.out.println("The word to guess is " + length + " letters long.");
		else
			System.out.println("The number to guess is " + length + " digits long.");
		
		//while loop (game loop)
		while(true){
			//generate answer
			answer = generateAnswer(useCharacters, length);
			
			while(true){
				//guess loop

				//for debugging purposes
				//System.out.println("Answer is: " + answer);
				System.out.print("Guess the answer: ");
				numTries++;
				input = scanner.nextLine();
				
				//print output
				if(answer.equals(input)) break;
				
				computeCorrectness(length, input, answer);
				//print stats
				//System.out.println("Length of Word: " + length);
				System.out.println("Correct " + (useCharacters? "letters: " : "digits: ") + numCorrect);
				System.out.println("Close " + (useCharacters? "letters: " : "digits: ") + numClose);
				System.out.println();
			}
			
			//print victory
			System.out.println("Congratulations! You got the answer \"" + answer + "\" in " + numTries + " tries!");
			
			System.out.print("Play again? y/n ");
			input = scanner.nextLine();
			System.out.println();
			
			if("n".equals(input)) break;
			
		}
	}
	
	private static String generateAnswer(boolean useChars, int length){
		int startIndex = (int)(useChars? 'a' : '0');
		int range = (int)(useChars? 26 : 10);
		
		String str = "";
		
		for(int i = 0; i < length; i++)
			str += (char)(startIndex + (int)(Math.random()*range));
		
		return str;
	}
	
	private static void computeCorrectness(int length, String guess, String answer){
		//reset values
		numCorrect = 0;
		numClose = 0;
		
		if(guess.length() != length){
			System.out.println("Your guess has a different length! The length should be " + length + "!");
			return;
		}
		
		String answerMod = answer, guessMod = guess;
		
		for(int i = answer.length() - 1; i >= 0; i--){
			if(guessMod.charAt(i) == answerMod.charAt(i)){
				answerMod = answerMod.substring(0, i) + answerMod.substring(i + 1);
				guessMod = guessMod.substring(0, i) + guessMod.substring(i + 1);
				numCorrect++;
			}
		}
		
		char[] answerCharList = answerMod.toCharArray();
		Arrays.sort(answerCharList);
		answerMod = new String(answerCharList);
		
		char[] guessCharList = guessMod.toCharArray();
		Arrays.sort(guessCharList);
		guessMod = new String(guessCharList);
		
		for(int i = answerMod.length() - 1; i >= 0; i--){
			int prevLength = guessMod.length();
			
			guessMod = guessMod.replace("" + answerMod.charAt(i), "");
			
			if(prevLength != guessMod.length()) numClose++;
		}
		
		/*
		for(int i = 0; i < guess.length(); i++){
			for(int j = 0; j < answer.length(); j++){
				if(guess.charAt(i) == answer.charAt(j)){
					numClose++;
					if(i == j) numCorrect++;
					break;
				}
			}
		}
		*/
	}
}
