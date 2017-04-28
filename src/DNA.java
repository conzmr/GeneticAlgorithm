import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;
import javax.swing.JOptionPane;


public class DNA {
	
	public double fitness;
	public int genes[];
	//granularity, latency, swapiness and timeslice
	
	public DNA(){
		this.genes = new int[4];
		this.genes[0] = this.randInt(200000, 10000000);
		this.genes[1] = this.randInt(100000, this.genes[0]/2);
		this.genes[2] = this.randInt(0, 100000);
		this.genes[3] = this.randInt(10, 60);
		this.fitness =0;
	}
	
	public DNA(int[] dna){
		this.genes = dna;
	}

	public void getFitness(){
		try {
			this. execShellCmd();
		} catch (Exception e) {
			System.out.println("Cannot run fitness function. ");
			e.printStackTrace();
		}
		Double result = Double.parseDouble(JOptionPane.showInputDialog(this, "What was the fitness?"));
		this.fitness = result;
	}
	
	public int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
	  public DNA crossover(DNA parent){
		  DNA child;
		  int midpoint = randInt(0, 4);
		  if(midpoint==0){
			  child = this;
		  }
		  else if(midpoint == 4){
			  child = parent;
		  }
		  else{
			  int newGenes[] = new int[5];
			  for(int i=0; i<midpoint; i++){
				  newGenes[i]=this.genes[i];
			  }
			  for(int i=midpoint; i<newGenes.length-1; i++){
				  newGenes[i]=parent.genes[i];
			  }
			  child = new DNA(newGenes);
		  }
		    return child;
		 }
	  
	  public String getGenes(){
		  StringBuilder sb = new StringBuilder();
		  for (int i=0; i<this.genes.length; i++){
			  sb.append(this.genes[i]+" ");
		  }
		  return sb.toString();
	  }
	  
	  public String toString(){
		  return "\nGenotype: "+this.getGenes()+ "\nPhenotype: "+this.fitness;
	  }
	  
	  public void executeScript() {
		  try {
		    ProcessBuilder pb = new ProcessBuilder(
		      "run_test_script.sh");
		    Process p = pb.start();     // Start the process.
		    p.waitFor();                // Wait for the process to finish.
		    System.out.println("Script executed successfully");
		  } catch (Exception e) {
		    e.printStackTrace();
		  }
		}
	  
	  public void runTest() throws Exception {
          try {
                  String target = new String("sh run_test_script.sh");
                  Runtime rt = Runtime.getRuntime();
                  Process proc = rt.exec(target+" "+this.getGenes());
                  proc.waitFor();
                  StringBuffer output = new StringBuffer();
                  BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                  String line = "";                       
                  while ((line = reader.readLine())!= null) {
                          output.append(line + "\n");
                  }
                  System.out.println("### " + output);
          } catch (Throwable t) {
                  t.printStackTrace();
          }
  }
	  
	  public void execShellCmd() {
	        try {
	        	String cmd = "sh run_test_script.sh "+this.getGenes();
	            Runtime runtime = Runtime.getRuntime();
	            Process process = runtime.exec(new String[] { "/bin/bash", "-c", cmd });
	            int exitValue = process.waitFor();
	            System.out.println("exit value: " + exitValue);
	            BufferedReader buf = new BufferedReader(new InputStreamReader(process.getInputStream()));
	            String line = "";
	            while ((line = buf.readLine()) != null) {
	                System.out.println("exec response: " + line);
	            }
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
	
	

}
