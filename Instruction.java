import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
public class Instruction {
    String keyword;
    String regA;
    String regB;
    String data;

    public Instruction(String k, String rA, String rB, String d) {
        this.keyword = k;
        this.regA = rA;
        this.regB = rB;
        this.data = d;
    }

    public static String hexadecimal(String[] bits) {
        if(bits.length != 4) {
            throw new IllegalArgumentException("String of wrong length");
        }
        ArrayList<Integer> numBits = new ArrayList<Integer>();
        for(String bit:bits) {
            numBits.add(Integer.parseInt(bit));
        }
        int decimal = numBits.get(0)*8 + numBits.get(1)*4 + numBits.get(2)*2 + numBits.get(3);

        return Integer.toString(decimal, 16);

    }
    @Override
    public String toString() {
        return this.keyword+","+this.regA+","+this.regB+","+this.data;
    }

    public static void main(String[] args) {
        ArrayList<String> lines = new ArrayList<String>();
        try{
            String input = "out";
            File in = new File(input);
            Scanner s = new Scanner(in);
            while(s.hasNext()){
                String raw = s.nextLine();
                System.out.println(raw);
                lines.add(raw);

                
            }
            

        } catch(Exception e) {
            e.getStackTrace();
        }

        for(String line:lines) {
            String[] bits = line.split("");
            String[] bits0 = Arrays.copyOfRange(bits, 0, bits.length/2);
            String[] bits1 = Arrays.copyOfRange(bits, bits.length/2, bits.length);
            String hd0 = hexadecimal(bits0).toUpperCase();
            String hd1 = hexadecimal(bits1).toUpperCase();
            System.out.println(hd0+hd1);
            
        }
        

        
                
        
        
    }
}