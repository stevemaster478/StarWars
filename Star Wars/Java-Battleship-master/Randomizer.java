import java.util.*;

public class Randomizer
{

	public static Random istanza = null;

	public Randomizer(){

	}

	public static Random getIstanza(){
		if(istanza == null){
			istanza = new Random();
		}
		return istanza;
	}

	public static boolean nextBoolean(){
		return Randomizer.getIstanza().nextBoolean();
	}

	public static boolean nextBoolean(double probability){
		return Randomizer.nextDouble() < probability;
	}

	public static int nextInt(){
		return Randomizer.getIstanza().nextInt();
	}

	public static int nextInt(int n){
		return Randomizer.getIstanza().nextInt(n);
	}

	public static int nextInt(int min, int max){
		return min + Randomizer.nextInt(max - min + 1);
	}

	public static double nextDouble(){
		return Randomizer.getIstanza().nextDouble();
	}

	public static double nextDouble(double min, double max){
		return min + (max - min) * Randomizer.nextDouble();
	}


}