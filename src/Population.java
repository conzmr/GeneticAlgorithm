import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

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
		    this.population[i] = new DNA((i+1)+(this.generation*size));
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
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			File file = new File("results.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);
			bw.write("Generation: "+this.generation);
			bw.newLine();
		for (int i=0; i<this.population.length;i++){
			System.out.println("Generation: "+this.generation+" DNA number: "+(i+1));
			this.population[i].calculateFitness();
			bw.write(this.population[i].toString());
			bw.newLine();
			if(this.maxFitness!=null){
				if(this.population[i].getFitness()<this.maxFitness.getFitness()){
					this.maxFitness=this.population[i];
				}
			}
			else{
				this.maxFitness=this.population[i];
			}
		}
		bw.write("At generation "+this.getGeneration()+"the best is "+this.getMaxFitness()+"");
		bw.newLine();
		System.out.println("\nAt generation "+this.getGeneration()+"the best is "+this.getMaxFitness()+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();

			}

		}
	}

	public boolean finished(){
		return this.maxFitness.getFitness()<=target;
	}

	public int randInt(int min, int max) {
	    int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
	    return randomNum;
	}

	public double relativeFitness(double fitness){
		return((this.defaultFitnessValue-fitness)/Math.abs(this.target-this.defaultFitnessValue));
	}

	public DNA acceptReject(){
		int randomIndex = this.randInt(0, this.population.length-1);
		double dnaFitness = this.relativeFitness(this.population[randomIndex].getFitness());
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
		this.generation++;
		for(int i=0; i<this.population.length; i++){
			DNA parent1 = this.acceptReject();
			DNA parent2 = this.acceptReject();
			DNA child = parent1.crossover(parent2);
			child.setTestName((i+1)+(this.generation*this.population.length));
			newPopulation[i]=child;
		}
		this.population = newPopulation;
		this.calculateFitness();
	}
}
