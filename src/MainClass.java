public class MainClass {

	public static void main(String[] args){
		Population population = new Population(4.5, 5, 10); 
		while(!population.finished()){
			population.newGeneration();
		}
	}
}
