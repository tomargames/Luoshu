import java.util.ArrayList;

public class Luoshu 
{
	/**
	 * This is a basic Luoshu, 3 x 3 
	 */
	private static final long serialVersionUID = 1L;
	public static final int SIDE = 3;
	public static final int SIZE = SIDE * SIDE;
	public static final int[] ROTATED = {5, 0, 7, 6, 4, 2, 1, 8, 3};
	ArrayList square = new ArrayList();
	int index = 0;
	int level = 0;
	
	public Luoshu()	{}
	public Luoshu(int level)
	{
		this.level = level;
	}
	public Luoshu(int level, int index)
	{
		this.level = level;
		this.index = index;
	}
	
	public void fill (ArrayList input)
	{
		square = input;
//		System.out.println("Filled luoshu, size is " + square.size());
	}
	public void shuffle()
	{
		ArrayList shuffled = new ArrayList(square.size());
		for (int i = 0; i < SIZE; i++)
		{
			shuffled.add(new Object());
		}
		for (int i = 0; i < SIZE; i++)
		{
			shuffled.set(ROTATED[i],(Object) square.get(i));
//			System.out.println("Moved square " + i + " to position " + ROTATED[i]);
		}
		square = shuffled;
	}
	public ArrayList getSquare()
	{
		return square;
	}

	public void setSquare(ArrayList square)
	{
		this.square = square;
	}

	public int getIndex()
	{
		return index;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}

	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}
	public String toString()
	{
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < SIZE; i++)
		{
			sb.append((Integer) square.get(i) + " ");
		}
		return sb.toString();
	}
}
