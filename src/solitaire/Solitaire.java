package solitaire;
import java.io.IOException;
import java.util.Scanner;
import java.util.Random;
public class Solitaire
{
	CardNode deckRear;
	public void makeDeck()
	{
		int[] cardValues = new int[28];
		for(int i = 0; i < cardValues.length; i++)
		{
			cardValues[i] = i + 1;
		}
		Random randgen = new Random();
		for(int i = 0; i < cardValues.length; i++)
		{
			int other = randgen.nextInt(28);
			int temp = cardValues[i];
			cardValues[i] = cardValues[other];
			cardValues[other] = temp;
		}
		CardNode cn = new CardNode();
		cn.cardValue = cardValues[0];
		cn.next = cn;
		deckRear = cn;
		for(int i = 1; i < cardValues.length; i++)
		{
			cn = new CardNode();
			cn.cardValue = cardValues[i];
			cn.next = deckRear.next;
			deckRear.next = cn;
			deckRear = cn;
		}
	}
	public void makeDeck(Scanner scanner) throws IOException
	{
		CardNode cn = null;
		if(scanner.hasNextInt())
		{
			cn = new CardNode();
			cn.cardValue = scanner.nextInt();
			cn.next = cn;
			deckRear = cn;
		}
		while (scanner.hasNextInt())
		{
			cn = new CardNode();
			cn.cardValue = scanner.nextInt();
			cn.next = deckRear.next;
			deckRear.next = cn;
			deckRear = cn;
		}
	}
	void jokerA()
	{
		CardNode prev = deckRear;
		for(CardNode cur = deckRear.next; cur != deckRear; cur = cur.next)
		{
			if(cur.next == deckRear && cur.cardValue == 27)
			{
				CardNode head = deckRear.next;
				CardNode rear = deckRear;
				prev.next = cur.next;
				rear.next = cur;
				deckRear = cur;
				cur.next = head;
				break;
			}
			else if(cur.cardValue == 27)
			{
				CardNode tmp = cur.next.next;
				prev.next = cur.next;
				prev.next.next = cur;
				cur.next = tmp;
				break;
			}
			else if(cur.next == deckRear && cur.next.cardValue == 27)
			{
				CardNode tmp = deckRear;
				deckRear = deckRear.next;
				cur.next = deckRear;
				CardNode tmp2 = deckRear.next;
				deckRear.next = tmp;
				deckRear.next.next = tmp2;
				break;
			}
			prev = prev.next;
		}
	}
	void jokerB()
	{
		CardNode prev = deckRear;
		for(CardNode cur = deckRear.next; cur != deckRear; cur = cur.next)
		{
			if(cur.next.cardValue == 28 && cur.next == deckRear)
			{
				CardNode back = cur.next;
				deckRear = deckRear.next;
				cur.next = deckRear;
				CardNode tmp = deckRear.next.next;
				deckRear.next.next = back;
				deckRear.next.next.next = tmp;
				return;
			}
			else if(cur.cardValue == 28 && cur.next.next == deckRear)
			{
				prev.next = cur.next;
				cur.next = deckRear.next;
				deckRear.next = cur;
				deckRear = cur;
				return;
			}
			else if(cur.cardValue == 28 && cur.next == deckRear)
			{
				prev.next = deckRear;
				deckRear = deckRear.next;
				cur.next = deckRear.next;
				deckRear.next = cur;
				return;
			}
			else if(cur.cardValue == 28)
			{
				prev.next = cur.next;
				cur.next = cur.next.next.next;
				prev.next.next.next = cur;
				return;
			}
			else
				prev = prev.next;
		}
	}
	void tripleCut()
	{
		if(deckRear.next.cardValue == 27 || deckRear.next.cardValue == 28)
		{
			if(deckRear.cardValue == 27 || deckRear.cardValue == 28)
				return;
			CardNode prev = deckRear;
			for(CardNode cur = deckRear.next.next; cur != deckRear; cur = cur.next)
			{
				if(cur.cardValue == 28 || cur.cardValue == 27)
				{
					deckRear = cur;
					deckRear.next = cur.next;
					return;
				}
				prev = prev.next;
			}
		}
		else if(deckRear.cardValue == 28 || deckRear.cardValue == 27)
		{
			if(deckRear.next.cardValue == 27 || deckRear.next.cardValue == 28)
				return;
			CardNode prev = deckRear;
			for(CardNode cur = deckRear.next; cur != deckRear; cur = cur.next)
			{
				if(cur.cardValue == 27 || cur.cardValue == 28)
				{
					deckRear = prev;
					return;
				}
				prev = prev.next;
			}
		}
		else
		{
			CardNode joker1 = null;
			CardNode joker2 = null;
			for(CardNode cur = deckRear.next; cur != deckRear; cur = cur.next)
			{
				if(cur.next.cardValue == 27 || cur.next.cardValue == 28)
				{
					joker1 = cur;
					for(CardNode cur2 = cur.next; cur2 != deckRear; cur2 = cur2.next)
					{
						if(cur2.next.cardValue == 27 || cur2.next.cardValue == 28)
						{
							joker2 = cur2;
							CardNode tmp = joker2.next.next;
							joker2.next.next = deckRear.next;
							deckRear.next = joker1.next;
							joker1.next = joker2.next.next;
							deckRear = joker1;
							deckRear.next = tmp;
							return;
						}
					}
				}
			}
		}
	}
	void countCut()
	{
		CardNode cur = deckRear;
		CardNode last = null;
		CardNode secondToLast = deckRear;
		int countDown = deckRear.cardValue;
		if(countDown == 28)
			countDown = 27;
		while (countDown > -1)
		{
			last = cur;
			cur = cur.next;
			countDown--;
		}
		for(CardNode c = deckRear.next; c != deckRear; c = c.next)
		{
			secondToLast = secondToLast.next;
		}
		secondToLast.next = deckRear.next;
		deckRear.next = last.next;
		last.next = deckRear;
	}
	int getKey()
	{
		int countTo = 1;
		int frontVal = deckRear.next.cardValue;
		if(frontVal == 28)
			frontVal = 27;
		for(CardNode curCard = deckRear.next; curCard != deckRear; curCard = curCard.next)
		{
			if(countTo == frontVal)
			{
				if(curCard.next.cardValue == 27 || curCard.next.cardValue == 28)
				{
					jokerA();
					jokerB();
					tripleCut();
					countCut();
					curCard = deckRear;
					countTo = 0;
					frontVal = deckRear.next.cardValue;
					if(frontVal == 28)
						frontVal = 27;
				}
				else
					System.out.println(curCard.next.cardValue);
					return curCard.next.cardValue;
			}
			countTo++;
		}
		return 0;
	}
	private static void printList(CardNode rear)
	{
		if(rear == null)
		{
			return;
		}
		System.out.print(rear.next.cardValue);
		CardNode ptr = rear.next;
		do
		{
			ptr = ptr.next;
			System.out.print("," + ptr.cardValue);
		}
		while (ptr != rear);
		System.out.println("\n");
	}
	public String encrypt(String message)
	{
		int key = -1;
		String encrypted = "";
		String lettersOnly = "";
		message = message.toUpperCase();
		for(int cur = 0; cur < message.length(); cur++)
		{
			if(message.charAt(cur) >= 65 && message.charAt(cur) <= 90)
				lettersOnly += message.charAt(cur);
		}
		for(int i = 0; i < lettersOnly.length(); i++)
		{
			char curChar = lettersOnly.charAt(i);
			int charAsNum;
			int newCharAsNum;
			char newChar;
			jokerA();
			jokerB();
			tripleCut();
			countCut();
			key = getKey();
			charAsNum = curChar - 'A' + 1;
			newCharAsNum = charAsNum + key;
			if(newCharAsNum > 26)
				newCharAsNum -= 26;
			newChar = (char) (newCharAsNum - 1 + 'A');
			encrypted += newChar;
		}
		return encrypted;
	}
	public String decrypt(String message)
	{
		int key = -1;
		String decrypted = "";
		String lettersOnly = "";
		message = message.toUpperCase();
		for(int cur = 0; cur < message.length(); cur++)
		{
			if(message.charAt(cur) >= 65 && message.charAt(cur) <= 90)
				lettersOnly += message.charAt(cur);
		}
		for(int i = 0; i < lettersOnly.length(); i++)
		{
			char curChar = lettersOnly.charAt(i);
			int charAsNum;
			int newCharAsNum;
			char newChar;
			jokerA();
			jokerB();
			tripleCut();
			countCut();
			key = getKey();
			charAsNum = curChar - 'A' + 1;
			newCharAsNum = charAsNum - key;
			if(newCharAsNum <= 0)
				newCharAsNum += 26;
			newChar = (char) (newCharAsNum - 1 + 'A');
			decrypted += newChar;
		}
		return decrypted;
	}
}