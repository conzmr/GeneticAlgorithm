import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class DNA {

	private double fitness;
	private int genes[];
	private String testName;
	//granularity, latency, swapiness and timeslice

	public DNA(int number){
		this.genes = new int[4];
		this.genes[0] = this.randInt(200000, 10000000);
		this.genes[1] = this.randInt(100000, this.genes[0]/2);
		this.genes[2] = this.randInt(0, 100000);
		this.genes[3] = this.randInt(10, 60);
		this.fitness = 0;
		this.testName = "pts"+number;
	}

	public DNA(int[] dna){
		this.genes = dna;
	}

	public void setTestName(int number){
		this.testName = "pts"+number;
	}

	public double getFitness(){
		return this.fitness;
	}

	public void calculateFitness(){
		double newFitness=0;
		try {
			this.executeScript();
			newFitness = Double.parseDouble(this.readFile("/home/conzmr/"+this.testName+".csv"));
		} catch (Exception e) {
			System.out.println("Cannot run fitness function. ");
			e.printStackTrace();
		}
		this.fitness = newFitness;
		System.out.println("Result yay"+ newFitness);
	}

	public int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}

	  public DNA crossover(DNA parent){
		  DNA child;
		  int midpoint = randInt(0, 3);
		  if(midpoint==0){
			  child = this;
		  }
		  else if(midpoint == 3){
			  child = parent;
		  }
		  else{
			  int newGenes[] = new int[4];
			  for(int i=0; i<midpoint; i++){
				  newGenes[i]=this.genes[i];
			  }
			  for(int i=midpoint; i<newGenes.length; i++){
				  newGenes[i]=parent.genes[i];
			  }
			  child = new DNA(newGenes);
		  }
		    return child;
		 }

	  public String getGenesString(){
		  StringBuilder sb = new StringBuilder();
		  for (int i=0; i<this.genes.length; i++){
			  sb.append(this.genes[i]+" ");
		  }
		  return sb.toString();
	  }

	  public String[] getGenes(){
		  String[] genesArray = new String[this.genes.length];
		  for (int i=0; i<this.genes.length; i++){
			  genesArray[i]=String.valueOf(this.genes[i]);
		  }
		  return genesArray;
	  }

	  public String toString(){
		  return "\nGenotype: "+this.getGenesString()+ "\nPhenotype: "+this.fitness;
	  }

	  public void executeScript() {
		  try {
		    ProcessBuilder pb = new ProcessBuilder("/home/conzmr/Documents/4th Semester ISC/Operating Systems/KernelOptimization/src/expect_script.sh",
		      this.testName, this.getGenes().toString());
		    pb.inheritIO();
		    Process p = pb.start();
		    int errCode = p.waitFor();
		    System.out.println("Command executed with " + (errCode == 0 ? "no errors." : "errors."));
		    System.out.println("Script executed successfully");
		  } catch (Exception e) {
		    e.printStackTrace();
		  }
		}

	  public String readFile(String fileRoute) {
	        String csvFile = fileRoute;
	        BufferedReader br = null;
	        String line = "";
	        try {

	            br = new BufferedReader(new FileReader(csvFile));
	            while ((line = br.readLine()) != null) {
	                if (line.startsWith("\"Sample Pi Program - Phoronix Test Suite v5.2.1")){
	                	System.out.println(line);
	                	return line.substring(line.length()-4);
	                }
	            }
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (br != null) {
	                try {
	                    br.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
			return "0.0";
	    }
}
