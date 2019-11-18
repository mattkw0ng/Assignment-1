import java.util.Scanner;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class CS245A1{
    public static void main(String[] args) throws IOException{
        //-----------Initializing things-----------
        String input_file = args[0];
        String output_file = args[1];
        TreesAbstract tree = typeOfTree();  //calls function to initialize the tree by reading the a1properties file
        FileReader fr = new FileReader("english.0");
        BufferedReader br = new BufferedReader(fr);
        String s;
        ArrayList<String> list = new ArrayList<>(); //stores dictionary in list first to optimize runtime

        //-----------Copying to list-----------
        while((s=br.readLine())!=null){
            list.add(s);
            s = br.readLine();  //accounts for the spaces inbetween the lines
        }
        br.close();

        //-----------Copying to tree-----------
        long startTime = System.currentTimeMillis();    //recording time
        int size = list.size();
        System.out.println("Size of tree/trie: "+size);
        String[] arr = list.toArray(new String[size]);  //converts list to array
        addItems(tree ,arr,0,(size-1)); //calls addItems() to optimally add items to the tree/trie
        long endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;
        System.out.println("Execution time for insert: " + timeElapsed);

        //-----------Reading and writing to files-----------
        startTime = System.currentTimeMillis();
        WriteToFile(input_file,tree,output_file);   //calls WriteToFile() to find and write suggestions
        endTime = System.currentTimeMillis();
        timeElapsed = endTime - startTime;
        System.out.println("Execution time for suggesting: " + timeElapsed);


    }

    //==========adds the items to the trie/tree in a way that creates a balanced tree==========\\
    static public void addItems(TreesAbstract tree,String[] arr,int left, int right){
        int middle = ((right-left)/2)+left;
        tree.insert(arr[middle]);
        if(right<=left){
            return;
        }else{
            addItems(tree,arr,left,middle-1);
            addItems(tree,arr,middle+1,right);
        }
    }
    //==========Returns a tree/trie based on the properties file==========\\
    static public TreesAbstract typeOfTree(){
        try{
            FileReader fr = new FileReader("a1properties.txt");
            BufferedReader br = new BufferedReader(fr);
            String type = br.readLine();
            br.close();
            if(type.contains("tree")){
                System.out.println("Storage type: tree");
                return new Tree();
            }else{
                System.out.println("Storage type: trie");
                return new TriePrefix();
            }
        }catch(Exception e){
            System.out.println("No properties file -- default: trie");
            return new TriePrefix();
        }

    }
    //==========Reads input file and creates output file==========\\
    public static void WriteToFile(String input_file,TreesAbstract tree,String output_file){
         try {
             //intitializing the file readers and writers
             FileReader file = new FileReader(input_file);
             BufferedReader br = new BufferedReader(file);
             String s;
             File output = new File(output_file);
             FileWriter fw = new FileWriter(output);
             BufferedWriter bw = new BufferedWriter(fw);

             while((s=br.readLine())!=null){
                 String suggested = tree.findSuggestion(s);
                 bw.write(suggested+"\n");  //writes the suggested word to the file and appends a '\n' to create a new line
             }
             br.close();
             bw.close();

         }catch(Exception e){
             System.out.println("Error: Could not find files");
         }
    }
}