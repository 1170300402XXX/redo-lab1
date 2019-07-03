package P1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;





public class MagicSquare {
    
    
  
    
    /**
     *  Determine whether a matrix input by a file is a magic square?
     * 
     * @param fileName
     * @return true of false ,if the matrix is a magic square or not
     
     * @throws IOException 
     */
    public boolean isLegalMagicSquare(String fileName) throws IOException{
        
        File file = new File(fileName);
        int rowNumber = 0;
        int columnNumber = 0;
        
        
        try {
            @SuppressWarnings("resource") 
            BufferedReader br = new BufferedReader(new FileReader(file));
            String firstLine = new String(br.readLine());
            String[] firstLineArray = firstLine.split("\t");//读取第一行，获取列数目
            columnNumber = firstLineArray.length;
            int[][] matrix = new int[columnNumber][columnNumber];//获得行数，获取矩阵大小
            
            /*构建第一行*/
            for (int i = 0; i < columnNumber; i++) {     
                matrix[0][i] = isInteger(firstLineArray[i]);
                if ( matrix[0][i] == -1 ) {      //如果是不符合要求的数，转化出来直接返回false
                    return false;                   
                }
            }
            rowNumber++;
            
            
            
            
            System.out.println(1);
            
            
            
            
            
            /*构建接下来的每一行*/
            String oneLine;            
            while ( (oneLine = br.readLine()) !=null ) {                
                String[] oneLineArray = oneLine.split("\t");
                if (columnNumber != oneLineArray.length) {  //判断每行的元素个数是否相等
                    return false;
                }
                for (int i = 0; i < columnNumber; i++) {  //矩阵初始化每一行
                    matrix[rowNumber][i] = isInteger( oneLineArray[i] );  //每一行只要有一个不合格，整个矩阵就不可能
                    if ( matrix[rowNumber][i] == -1 ) {
                        return false;
                    }
                }
                rowNumber++;  //记录行数                                             
            }
            
            
            /*bug出错位置
             * 
             * 因为第一行是单独挑出来加的，所以在后续的rownumber++时，会少加一行。
             * 
             * 解决方法：在构架第一行时，让rownumber+1
             * 
             * 
             * 
             * */
            
            System.out.println(rowNumber);
            System.out.println(columnNumber);
            System.out.println(2);
            
            
            
            
            /*构建完毕，判断行列是否相等*/
            if (rowNumber != columnNumber) {  
                return false;               
            }
            
            
            System.out.println(3);
            
            
            
            
            /*每行之和是否相等*/
            int rowAddFirst = 0;
            for(int j = 0; j < columnNumber; j++) {         //计算第一行之和，在之后的列和对角线中都能用得到
                rowAddFirst = rowAddFirst + matrix[0][j];
            }
            
           
            for(int i = 1; i < rowNumber ; i++) {     //计算每一行之和
                int rowAdd = 0;
                for(int j = 0; j < columnNumber; j++) {
                    rowAdd = rowAdd + matrix[i][j];
                }
                if(rowAdd != rowAddFirst) {
                    return false;
                }   
            }
            
            
            System.out.println(4);
            
            
            
            
            /*计算每一列之和*/
            for(int j = 0 ; j < columnNumber; j++) {   //计算每一列之和
                int columnAdd = 0;
                for(int i = 0; i < rowNumber; i++) {
                    columnAdd = columnAdd + matrix[i][j];
                }
                if(columnAdd != rowAddFirst ) {   //Attention:每一列之和和第一行之和比较
                    return false;
                }
            }
            
            
            
            System.out.println(5);
            
            
            
            /*计算对角线之和*/
            int backslashAdd = 0;  // 反斜杠\之和
            int slashAdd = 0;      //斜杠/之和
            for(int i = 0 ; i < rowNumber; i++) {
                backslashAdd = backslashAdd + matrix[i][i];
            }
            for (int i = 0; i < rowNumber; i++) {
                slashAdd = slashAdd + matrix[i][rowNumber-1-i];
            }
            if(backslashAdd != rowAddFirst || slashAdd != rowAddFirst) {
                return false;
            }
            
            
            System.out.println(6);
            
            br.close();
            
        } catch (IOException e) {         
            e.printStackTrace();
        }
        return true;   //没有任何异常情况，说明是魔方
        
    }

  
    /**
     * give a number represented by a string s.
     *  if it is number 0 or a positive integer,return itself in type of INT,
     *  else return -1
     *  
     * @param s a number in type of String
     * @return  a number in type of Int,if the requirements are met.Else return -1
     */
    public int isInteger(String s) {  //疑问：这个部分是如何处理不是int类型的数的转换的?
        
        int a;
        
        try {
            a = Integer.parseInt(s);         
            
        } catch (NumberFormatException nfe) {          
            nfe.printStackTrace();
            return -1;   
        }
        if(a<0) {
            return -1;
        }
        return a;         
    }
    
    
  
    
    
  
    /**
     * @param n
     * @return  Boolean type variable，TRUE if the input is positive odd, else return FALSE
     */
    public static boolean generateMagicSquare(int n) {
        
        if(n < 0 || (n/2)*2 == n) {   //为什么/2再*2，因为int有截断,所以奇数不相等
            System.out.println("输入不合法，不能有负数和奇数");
            return false;           
        }

        int magic[][] = new int[n][n];
        int row = 0, col = n / 2, i, j, square = n * n;

        for (i = 1; i <= square; i++) {
            magic[row][col] = i;
            if (i % n == 0)
                row++;
            else {
                if (row == 0)
                    row = n - 1;
                else
                    row--;
                if (col == (n - 1))
                    col = 0;
                else
                    col++;
            }
        }
                
        try {
            File file = new File("src/P1/txt/6.txt");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            
            for (i = 0; i < n; i++) {
                for (j = 0; j < n; j++) {
                    bw.write(magic[i][j]+"\t");
                    //System.out.print(magic[i][j] + "\t");
                }
                bw.write("\n"); 
                bw.flush();
            }           
            fw.close();
            
        } catch (IOException e) {     
            e.printStackTrace();
        }             
/*        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++)
                System.out.print(magic[i][j] + "\t");
           
        }
        
        */
       return true;
}

    
    

    
    


    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        MagicSquare m1 = new MagicSquare();
        MagicSquare m2 = new MagicSquare();
        MagicSquare m3 = new MagicSquare();
        MagicSquare m4 = new MagicSquare();
        MagicSquare m5 = new MagicSquare();
        MagicSquare m6 = new MagicSquare();
            
        try {
            System.out.println("1:" + m1.isLegalMagicSquare("src/P1/txt/1.txt"));
            System.out.println("2:" + m2.isLegalMagicSquare("src/P1/txt/2.txt"));
            System.out.println("3:" + m3.isLegalMagicSquare("src/P1/txt/3.txt"));
            System.out.println("4:" + m4.isLegalMagicSquare("src/P1/txt/4.txt"));
            System.out.println("5:" + m5.isLegalMagicSquare("src/P1/txt/5.txt"));      
            
        } catch (IOException e) {            
            e.printStackTrace();
        }
        
        System.out.print("请输入一个数字，用来构建幻方：");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.close();       
        boolean b = generateMagicSquare(n);
        if(!b) {
            return;
        }       
        try {
            System.out.println("6:" + m6.isLegalMagicSquare("src/P1/txt/6.txt"));      
            
        } catch (IOException e) {            
            e.printStackTrace();
        }
    }

}
