public class PercolationStats 
{
   private int Trytime;
   private double[] Threshold;
   private Percolation per;
   
   public PercolationStats(int N, int T)     // perform T independent experiments on an N-by-N grid
   {
       if (N <= 0 || T <= 0) 
       {
            throw new IllegalArgumentException("Given N <= 0 || T <= 0");
       }
       Trytime = T;
       Threshold = new double[T]; 
       for(int k = 0; k < T; k++) 
       {
           per = new Percolation(N);
           int openedSites = 0;
           while (!per.percolates()) // Keep opening sites (raw i, column j) until the array percolates. 
           {
        	   int i = StdRandom.uniform(1, N+1);
        	   int j = StdRandom.uniform(1, N+1);
        	   if (!per.isOpen(i,j))
        	   {
        		   per.open(i, j);
        		   openedSites++;
        	   }
           }
           double p = (double) openedSites/(N*N);
           Threshold[k] = p;
        }
   }
      
   public double mean()                      // sample mean of percolation threshold
   {
       return StdStats.mean(Threshold);
   }    
       
   public double stddev()                    // sample standard deviation of percolation threshold
   {
       return StdStats.stddev(Threshold);
   }
       
       
   public double confidenceLo()              // low  endpoint of 95% confidence interval
   {
       return mean() - (1.96 * stddev() / Math.sqrt(Trytime));
   }
       
       
   public double confidenceHi()              // high endpoint of 95% confidence interval
   {
       return mean() + (1.96 * stddev() / Math.sqrt(Trytime));
   }

   public static void main(String[] args)    // test client (described below)
   {
       int N = Integer.parseInt(args[0]);
       int T = Integer.parseInt(args[1]);
       
       PercolationStats Multiper = new PercolationStats(N,T);
       
       StdOut.println("mean = " + Multiper.mean());
       StdOut.println("stddev = " + Multiper.stddev());
       String confidence = Multiper.confidenceLo() + ", " + Multiper.confidenceHi();
       StdOut.println("95% confidence interval = " + confidence);
   }
}
