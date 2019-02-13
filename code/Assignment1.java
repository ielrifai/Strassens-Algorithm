import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Assignment1 {

  public int[][] denseMatrixMult(int[][] A, int[][] B, int size)
  {
    int n = size;
    int[][] R = new int[n][n];

    /**Base Case**/
    if (n == 1)
      R[0][0] = A[0][0] * B[0][0];
    else
    {
      int[][] A11 = new int[n/2][n/2];
      int[][] A12 = new int[n/2][n/2];
      int[][] A21 = new int[n/2][n/2];
      int[][] A22 = new int[n/2][n/2];
      int[][] B11 = new int[n/2][n/2];
      int[][] B12 = new int[n/2][n/2];
      int[][] B21 = new int[n/2][n/2];
      int[][] B22 = new int[n/2][n/2];

      /** Dividing matrix A into 4 halves **/
      split(A, A11, 0 , 0);
      split(A, A12, 0 , n/2);
      split(A, A21, n/2, 0);
      split(A, A22, n/2, n/2);

      /** Dividing matrix B into 4 halves **/
      split(B, B11, 0 , 0);
      split(B, B12, 0 , n/2);
      split(B, B21, n/2, 0);
      split(B, B22, n/2, n/2);


       /**M1 = (A11 + A22)(B11 + B22)
       M2 = (A21 + A22) B11
       M3 = A11 (B12 - B22)
       M4 = A22 (B21 - B11)
       M5 = (A11 + A12) B22
       M6 = (A21 - A11) (B11 + B12)
       M7 = (A12 - A22) (B21 + B22)**/


      int [][] M1 = denseMatrixMult(add(A11, A22), add(B11, B22),n/2);
      int [][] M2 = denseMatrixMult(add(A21, A22), B11,n/2);
      int [][] M3 = denseMatrixMult(A11, sub(B12, B22),n/2);
      int [][] M4 = denseMatrixMult(A22, sub(B21, B11),n/2);
      int [][] M5 = denseMatrixMult(add(A11, A12), B22,n/2);
      int [][] M6 = denseMatrixMult(sub(A21, A11), add(B11, B12),n/2);
      int [][] M7 = denseMatrixMult(sub(A12, A22), add(B21, B22),n/2);


       /**C11 = M1 + M4 - M5 + M7;
       C12 = M3 + M5;
       C21 = M2 + M4;
       C22 = M1 - M2 + M3 + M6**/

      int [][] C11 = add(sub(add(M1, M4), M5), M7);
      int [][] C12 = add(M3, M5);
      int [][] C21 = add(M2, M4);
      int [][] C22 = add(sub(add(M1, M3), M2), M6);


      /** join 4 halves into one result matrix **/
      join(C11, R, 0 , 0);
      join(C12, R, 0 , n/2);
      join(C21, R, n/2, 0);
      join(C22, R, n/2, n/2);
    }
    /** return result **/
    return R;

  }
  /** Function to sub two matrices **/
  public int[][] sub(int[][] A, int[][] B)
  {
    int n = A.length;
    int[][] C = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        C[i][j] = A[i][j] - B[i][j];
    return C;
  }
  /** Function to add two matrices **/
  public int[][] add(int[][] A, int[][] B)
  {
    int n = A.length;
    int[][] C = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        C[i][j] = A[i][j] + B[i][j];
    return C;
  }
  /** Function to split parent matrix into child matrices **/
  public void split(int[][] P, int[][] C, int iB, int jB)
  {
    for(int i1 = 0, i2 = iB; i1 < C.length; i1++, i2++)
      for(int j1 = 0, j2 = jB; j1 < C.length; j1++, j2++)
        C[i1][j1] = P[i2][j2];
  }
  /** Function to join child matrices intp parent matrix **/
  public void join(int[][] C, int[][] P, int iB, int jB)
  {
    for(int i1 = 0, i2 = iB; i1 < C.length; i1++, i2++)
      for(int j1 = 0, j2 = jB; j1 < C.length; j1++, j2++)
        P[i2][j2] = C[i1][j1];
  }

  /** Function to add two matrices **/
  public int[][] sum(int[][] A, int[][] B, int x1, int y1, int x2, int y2, int n)
  {
    int[][] C = new int[n][n]; //Create a new array of size nxn
    for (int i = 0; i < n; i++) { //Go through each row
      for (int j = 0; j < n; j++) {   //Go through each column

        C[i][j] = A[x1+i][y1+j] + B[x2+i][y2+j]; //Add each of them
      }
    }
    return C;


  }
  /** Function to sub two matrices **/
  public int[][] sub(int[][] A, int[][] B, int x1, int y1, int x2, int y2, int n)
  {
    int[][] C = new int[n][n]; //Create a new array of size nxn
    for (int i = 0; i < n; i++) { //Go through each row
      for (int j = 0; j < n; j++) {   //Go through each column

        C[i][j] = A[x1+i][y1+j] - B[x2+i][y2+j]; //Subtract each of them
      }
    }
    return C;

  }

  public int[][] initMatrix(int n) {

    int[][] initialMatrix = new int[n][n];

    return initialMatrix;
  }

  public void printMatrix(int n, int[][] A) {

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        System.out.print(A[i][j] + " ");
      }
      System.out.println();
    }

  }

  public int[][] readMatrix(String filename, int n) throws Exception {

    int[][] matrix;

    File file = new File(filename);
    Scanner scanner = new Scanner(file);

    scanner.close();

    matrix = new int[n][n];
    scanner = new Scanner(file);

    int lineCount = 0;
    while (scanner.hasNextLine()) {
      String[] currentLine = scanner.nextLine().trim().split("\\s+");

      for (int i = 0; i < n; i++) {
        matrix[lineCount][i] = Integer.parseInt(currentLine[i]);
      }
      lineCount++;
    }
    return matrix;
  }

}
