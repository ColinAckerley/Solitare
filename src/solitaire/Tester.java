package solitaire;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
public class Tester
{
	public static void main(String[] args) throws IOException
	{
		Solitaire ss = new Solitaire();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//System.out.print("Enter deck file name => ");
		//Scanner sc = new Scanner(new File(br.readLine()));
		ss.makeDeck();
		System.out.print("Encrypt or decrypt? (e/d), press return to quit => ");
		String inp = br.readLine();
		if(inp.length() == 0)
		{
			System.exit(0);
		}
		char ed = inp.charAt(0);
		System.out.print("Enter message => ");
		String message = br.readLine();
		String ec;
		if(ed == 'e')
		{
			ec = ss.encrypt(message);
			System.out.println("Encrypted message: " + ec);
			System.out.println("Decrypted message: " + ss.decrypt(message));
		}
	}
}
