import java.io.PrintWriter;
import java.util.ArrayList;

public class MagicSquare 
{
	static final int[] powersOfThree = {1, 3, 9, 27, 81, 243, 729};
	int[][][] outputSquares;
	PrintWriter logOut;
	
	public MagicSquare(int maxPower)
	{
		try
		{
			logOut = new PrintWriter("magicSquare.txt");
			outputSquares = new int[maxPower][][];
			for (int i = 1; i <= maxPower; i++)
			{	
				outputSquares[i-1] = createSquare(i);
				print(powersOfThree[i], outputSquares[i-1]);
			}
			logOut.close();
		}
		catch (Exception e)
		{
			System.out.println("Exception opening log file: " + e);
		}
	}	
	private int[][] createSquare(int powerIn)
	{
		int side = powersOfThree[powerIn];
		int size = side * side;
		int power = powerIn;
		int numberOfLuoshus = size / Luoshu.SIZE;
		log("power is " + power + ", size is " + size + ", side is " + side + ", numberOfLuoshus is " + numberOfLuoshus);
		ArrayList integerList = new ArrayList(size);
		ArrayList listOfLuoshus = new ArrayList(numberOfLuoshus);
		int dsIndex = 0;
		for (int i = 0; i < numberOfLuoshus; i++)
		{
			listOfLuoshus.add(new Luoshu(power, i));
			for (int j = 0; j < Luoshu.SIZE; j++)
			{	
				integerList.add(new Integer(++dsIndex));
			}	
		}
		if (power == 1)
		{
			Luoshu l = (Luoshu)listOfLuoshus.get(0);
			l.fill(integerList);
			l.shuffle();
			return makeRows(l.getSquare(), side);
		}
		dsIndex = 0;
		int setsOf3 = (int) side / Luoshu.SIDE;
//		log("there will be " + setsOf3 + " 3-row sets");
		for (int i = 0; i < setsOf3; i++)		// each of these is a set of 3 rows
		{
			int aListBase = i * setsOf3; 
			for (int j = 0; j < Luoshu.SIDE; j++)				// each of these is a row of the masterSquare
			{
				// each square will go into an ArrayList
				for (int k = 0; k < side; k++)		// each square in the row
				{
					int aListIndex = aListBase + (int) (k / Luoshu.SIDE);
//					log("adding square #" + dsIndex + " to Luoshu #" + aListIndex);
					(((Luoshu)listOfLuoshus.get(aListIndex)).getSquare()).add(integerList.get(dsIndex++));
//					log("size of Luoshu #" + aListIndex + " is " + (((Luoshu)listOfLuoshus.get(aListIndex)).getSquare()).size()); 
				}
			}	
		}
		// this shuffles each tiny luoshu
		for (int i = 0; i < listOfLuoshus.size(); i++)
		{
			((Luoshu) listOfLuoshus.get(i)).shuffle();
		}
		ArrayList[] shuffledRows = makeRowLists(side);
		for (int row = 0; row < outputSquares[power - 2].length; row++)
		{
			for (int col = 0; col < outputSquares[power - 2][row].length; col++)
			{	
//				log("row = " + row + ", col = " + col + ", going for Luoshu " + outputSquares[power - 2][row][col]);
				ArrayList sq = ((Luoshu) listOfLuoshus.get(outputSquares[power - 2][row][col] - 1)).getSquare();
				int counter = 0;
				// each square becomes 3 columns in 3 rows
				for (int r = row * 3; r < row * 3 + 3; r++)
				{
					shuffledRows[r].add(sq.get(counter++));
					shuffledRows[r].add(sq.get(counter++));
					shuffledRows[r].add(sq.get(counter++));
//					log("size of shuffledRow " + r + " is " + shuffledRows[r].size());
				}
			}	
		}
		return makeRows(shuffledRows, side);
	}
	public ArrayList getRow(int rowNumber, ArrayList outputSquare, int side)
	{
		ArrayList row = new ArrayList();
		for (int i = 0, index = (rowNumber * side); i < side; index ++, i++)
		{
			row.add((Object) outputSquare.get(index));
		}
		return row;
	}
	public int[] getColumn(int colNumber, int[][] outputSquare, int side)
	{
		int[] col = new int[side];
		for (int i = 0; i < side; i++)
		{
			col[i] = outputSquare[i][colNumber];
		}
		return col;
	}
	private String pad(int in, int length)
	{
		String temp = "000000000" + in;
		return (temp.substring(temp.length() - length));
	}
	public void print(int side, int[][] outputSquare)
	{
		log("<table>");
		for (int i = 0; i < side; i++)
		{
			log(formatPrint(this.getColumn(i, outputSquare, side)));
		}
		log("</table>");
		log("****************************************************************************************");
	}
	private String formatPrint(int[] a)
	{
		int len = 0;
		for (len = 0; len < powersOfThree.length; len++)
		{
			if (powersOfThree[len] == a.length)
			{
				break;
			}
		}
		StringBuffer sb = new StringBuffer("<tr>");
		int sum = 0;
		for (int j = a.length - 1; j > -1; j--)
		{
			sb.append("<td>" + pad(a[j], len) + "</td>");
			sum += a[j];
		}
		return sb.toString() + "</tr>"; //+ " = " + sum;
	}
	public void log(String s)
	{
//		System.out.println(s);
		try
		{
			logOut.print(s + "\r\n");
		}
		catch (Exception e)
		{
			System.out.println("Exception writing to log file: " + e);
		}
	}
	private ArrayList[] makeRowLists(int numberOfRows)
	{
		ArrayList[] rows = new ArrayList[numberOfRows];
		for (int i = 0; i < numberOfRows; i++)
		{
			rows[i] = new ArrayList();
		}
		return rows;
	}
	private int[][] makeRows(ArrayList a, int side)
	{
		int[][] returnArray = new int[side][side];
		int dsIndex = 0;
		for (int i = 0; i < side; i++)
		{
			for (int j = 0; j < side; j++)
			{
				returnArray[i][j] = ((Integer) a.get(dsIndex++)).intValue();
			}
		}
		return returnArray;
	}	
	private int[][] makeRows(ArrayList[] a, int side)
	{
		int[][] returnArray = new int[side][side];
		for (int i = 0; i < side; i++)
		{
			returnArray[i] =  new int[a[i].size()];
			for (int j = 0; j < a[i].size(); j++)
			{	
				returnArray[i][j] =  ((Integer)(a[i].get(j))).intValue();
//				log("set square " + i + ", " + j + " to " +  ((Integer)(a[i].get(j))).intValue());
			}	
		}
		return returnArray;
	}	
	public static void main(String[] args)
	{
			new MagicSquare(5);
	}		
}
