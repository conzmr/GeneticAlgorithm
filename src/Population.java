import java.util.Random;


public class Population {
	private DNA population[];
	private int generation; 
	private double target;
	private DNA maxFitness;
	private double defaultFitnessValue;

	public Population(double target, double defaultFitnessValue, int size){
		this.generation=1;
		this.maxFitness=null;
		this.target = target;
		this.defaultFitnessValue=defaultFitnessValue;
		this.population = new DNA[size];
		for (int i = 0; i < size; i++) {
		    this.population[i] = new DNA();
		}
		this.calculateFitness();
	}
	
	public int getGeneration(){
		return this.generation;
	}
	
	public DNA getMaxFitness(){
		return this.maxFitness;
	}
	
	public void calculateFitness(){
		for (int i=0; i<this.population.length;i++){
			this.population[i].getFitness();
			if(this.maxFitness!=null){
				if(this.population[i].fitness<this.maxFitness.fitness){
					this.maxFitness=this.population[i];
				}	
			}
			else{
				this.maxFitness=this.population[i];
			}
		}
	}
	
	public boolean finished(){
		return this.maxFitness.fitness<=target;
	}
	
	public int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
	public double relativeFitness(double fitness){
		return((this.defaultFitnessValue-fitness)/Math.abs(this.target-this.defaultFitnessValue));
	}
	
	public DNA acceptReject(){
		int randomIndex = this.randInt(0, this.population.length-1);
		double dnaFitness = this.relativeFitness(this.population[randomIndex].fitness);
		if(randInt(0, 10) < dnaFitness*10){
			return this.population[randomIndex];
		}
		else{
			return this.acceptReject();
		}
	}
	
	public DNA getBestOfGeneration(){
		return this.maxFitness;
	}
	
	public void newGeneration(){
		DNA newPopulation[] = new DNA[this.population.length];
		for(int i=0; i<this.population.length; i++){
			DNA parent1 = this.acceptReject();
			DNA parent2 = this.acceptReject();
			DNA child = parent1.crossover(parent2);
			newPopulation[i]=child;
		}
		this.generation++;
		this.population = newPopulation;
		this.calculateFitness();
	}
	
}