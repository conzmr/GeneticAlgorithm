
public class MainClass {

	public static void main(String[] args) {
		Population population = new Population(3, 5, 10);
		System.out.println("At 1st generation the best is "+population.getMaxFitness());
		while(!population.finished()){
			population.newGeneration();
			System.out.println("At generation "+population.getGeneration()+"the best is "+population.getMaxFitness());
		}	
	}
}
