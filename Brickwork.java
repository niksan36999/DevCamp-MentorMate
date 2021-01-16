import java.util.Scanner;
public class Brickwork
{
    static int m,n; // wall dimensions: n rows, m columns
    static int[][] Layer1; 

    public static void main (String[] s)
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter dimensions (N M)");
        n = scan.nextInt();
        m = scan.nextInt();
        scan.nextLine();
        Layer1 = new int[n][m];
        System.out.println("Enter Layer 1 (N rows with M numbers separated by blanks)");
        for (int i=0; i<n; i++)
            for (int j=0; j<m; j++)
                Layer1[i][j] = scan.nextInt();     
        if (!valid(Layer1))
            System.out.print("Invalid input");
        else
        {
            int[][] Layer2 = new int[n][m];  
            boolean found;
            System.out.println("\nLayer 1");
            printLayer(Layer1);
            found=generateLayer(Layer2,0,0,1);
            if (found) 
            {
                System.out.println("\nLayer 2");
                printLayer(Layer2);
            }
            else
                System.out.println("No solution exists");
        }
    }
    // check if no bricks span 3 rows/columns
    public static boolean valid(int[][] layer)
    {
        for (int i=0; i<n; i++) // validate rows
            for (int j=0; j<m-2; j++)
                if (layer[i][j]==layer[i][j+1] && layer[i][j]==layer[i][j+2])
                    return false;
        for (int j=0; j<m; j++) // validate columns
            for (int i=0; i<n-2; i++)
                if (layer[i][j]==layer[i+1][j] && layer[i][j]==layer[i+2][j])
                    return false;
        return true;   
    }

    // recursively place the bricks
    public static boolean generateLayer(int[][] layer,int i,int j,int k)
    {
        boolean done=false;

        if (k>m*n/2) // all bricks are placed (base case)
            return true;
        // recursive case 
        // place the brick in a row
        if (j<m-1 && j>=0 && i>=0 && i<n &&  // check for valid area
            layer[i][j]==0 && layer[i][j+1]==0 && // check for empty spaces
            Layer1[i][j]!=Layer1[i][j+1]) // check for overlapping Layer1
        {
            layer[i][j]=k; // place the brick
            layer[i][j+1]=k;  
            done = generateLayer(layer,i,j+2,k+1); // try different places for the next brick
            if (!done)
                done = generateLayer(layer,i,j-1,k+1);
            if (!done)
                done = generateLayer(layer,i-1,j,k+1);
            if (!done)
                done = generateLayer(layer,i+1,j,k+1);
            if (!done)
                done = generateLayer(layer,i-1,j+1,k+1);
            if (!done)
                done = generateLayer(layer,i+1,j+1,k+1);
            if (!done) 
            {   // if unsuccessful remove the brick
                layer[i][j]=0; 
                layer[i][j+1]=0;
            }
        }
        // place the brick in a column
        if (!done && i<n-1 && i>=0 && j>=0 && j<m &&  // check for valid area
            layer[i][j]==0 && layer[i+1][j]==0 && // check for empty spaces
            Layer1[i][j]!=Layer1[i+1][j]) // check for overlapping Layer1
        {
            layer[i][j]=k; // place the brick
            layer[i+1][j]=k;
            done = generateLayer(layer,i-1,j,k+1); // try different places for the next brick
            if (!done)
                done = generateLayer(layer,i+2,j,k+1);
            if (!done)
                done = generateLayer(layer,i,j-1,k+1);
            if (!done)
                done = generateLayer(layer,i,j+1,k+1);
            if (!done)
                done = generateLayer(layer,i+1,j-1,k+1);
            if (!done)
                done = generateLayer(layer,i+1,j+1,k+1);
            if (!done) 
            {   // if unsuccessful remove the brick
                layer[i][j]=0; 
                layer[i+1][j]=0;
            }
        }
        return done;
    }

    // print the layer
    public static void printLayer(int[][] layer)
    {
        for (int i=0;i<n;i++)
        {
            for (int j=0;j<m;j++)
                System.out.print(layer[i][j]+" ");
            System.out.println();
        }
    }
}
