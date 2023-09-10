import java.io.*;
import java.lang.IllegalArgumentException;
import java.util.*;
public class Assembler {
    
    
    
    
//new plan: 
    //for each line, pull out any data and store it somewhere with line number
    //for every other "word" in INST, covert to binary code 
        //when parsing together, if there are 3 "codes", combine
        //else, if two codes, then add 00 before regB

//Methods: 
    //scanFile
        //returns a list of instruction objects with assembly language inputs
    public ArrayList<Instruction> scanFile(String filename) {
        ArrayList<Instruction> a_list = new ArrayList<Instruction>();
        
        try{
            File input = new File(filename);
            Scanner reader = new Scanner(input);
            while(reader.hasNext()) {    
                String line = reader.nextLine();
                //System.out.println(line);
                String[] words = line.split(",", -1);
                //System.out.println(words.length);
                for(String word:words){
                //    System.out.println(word);
                }
                String k = words[0]; String rA = words[1]; String rB = words[2]; String d = words[3];
                Instruction inst = new Instruction(k, rA, rB, d);
                a_list.add(inst);
            }

            
    
        } catch(Exception e) {
            e.getStackTrace();
        }
        
            
        //System.out.println(a_list.get(0));
        return a_list;
                
    }

    //convert
        //converts list of inst obj with binary inputs 
    public ArrayList<Instruction> convert(ArrayList<Instruction> a_list, String filename) {
        HashMap<String, String> map = new HashMap<String, String>();
            map.put("ADD", "1000");
            map.put("SHR", "1001");
            map.put("SHL", "1010");
            map.put("NOT", "1011");
            map.put("AND", "1100");
            map.put("OR", "1101");
            map.put("XOR", "1110");
            map.put("CMP", "1111");
            map.put("LD", "0000");
            map.put("ST", "0001");
            map.put("DATA", "0010");
            map.put("JMPR", "0011");
            map.put("JMP", "0100");
            map.put("JZ", "01010001");
            map.put("JA", "01010010");
            map.put("JE", "01010100");
            map.put("JC", "01011000");
            map.put("JCA", "01011100");
            map.put("JCE", "01011010");
            map.put("JCZ", "01011001");
            map.put("JAE", "01010110");
            map.put("JAZ", "01010101");
            map.put("JEZ", "01010011");
            map.put("JCAE", "01011110");
            map.put("JCAZ", "01011101");
            map.put("JCEZ", "01011011");
            map.put("JAEZ", "01010111");
            map.put("JCAEZ", "01011111");
            map.put("CLF", "01100000");
            map.put("END", "11001111");
            map.put("R0", "00");
            map.put("R1", "01");
            map.put("R2", "10");
            map.put("R3", "11");
            map.put("", "00");

       
            for(Instruction inst : a_list) {

                inst.keyword = map.get(inst.keyword);
                if(inst.keyword.length()<8) {
                    if (inst.regA==null) {
                        inst.regA = "00";
                    } else {
                        inst.regA = map.get(inst.regA);
                    }
    
                    if (inst.regB==null) {
                        inst.regB = "00";
                    } else {
                        inst.regB = map.get(inst.regB);
                    }
                }

                if(inst.data == ("")) {
                    inst.data = "\n";
                }else{
                    inst.data = "\n"+inst.data+"\n";
                }
            }


       return a_list;
    }
    
    //assemble
        //writes binary script
    public void assemble(ArrayList<Instruction> list, String filename) {
        try {
                File input = new File(filename);
                FileWriter writer = new FileWriter(input);
                for(Instruction inst:list){
                    writer.write(inst.keyword+inst.regA+inst.regB+inst.data);
                }
                writer.close();

                
            } catch(Exception e) {
                e.getStackTrace();
            }
            
        }

    public String hexadecimal(String[] bits) {
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
    public void logisimOutput(String inFile, String outFile) {
        ArrayList<String> lines = new ArrayList<String>();
        try{
            File in = new File(inFile);
            Scanner s = new Scanner(in);
            while(s.hasNext()){
                String raw = s.nextLine();
                System.out.println(raw);
                lines.add(raw);
            }
            

        } catch(Exception e) {
            e.getStackTrace();
        }

        try{
            File output = new File(outFile);
            FileWriter writer = new FileWriter(output);
            writer.write("v2.0 raw\n");
            for(String line:lines) {
                String[] bits = line.split("");
                String[] bits0 = Arrays.copyOfRange(bits, 0, bits.length/2);
                String[] bits1 = Arrays.copyOfRange(bits, bits.length/2, bits.length);
                String hd0 = hexadecimal(bits0).toUpperCase();
                String hd1 = hexadecimal(bits1).toUpperCase();
                System.out.println(hd0+hd1);
                writer.write(hd0+hd1+"\n");
                
            }
            writer.close();
        } catch(Exception e) {
            e.getStackTrace();
        }
        



    }
    

    public static void main(String[] args) {
        Assembler a = new Assembler();

        ArrayList<Instruction> a_list = a.scanFile("add.csv");
       
        
        ArrayList<Instruction> b_list = a.convert(a_list, "test.out");
        

        a.assemble(b_list, "test.out");

        a.logisimOutput("test.out", "test.log");


        

        
        
    }



        
        
}
