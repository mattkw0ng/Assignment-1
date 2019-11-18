import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TriePrefix extends TreesAbstract{
    Node root = new Node();

    //==========Node class==========\\
    class Node {
        String prefix;
        HashMap<String,Node> children;
        boolean isWord;
        boolean isLeaf;
        String word;

        Node(){
            children = new HashMap<>(); //for the root node which does not have any data/prefix
        }

        Node(String item){
            prefix = item;
            children = new HashMap<>();
        }
    }

    //==========Inserts items into the trie==========\\
    public void insert(String input){
        //item = item.toLowerCase();
        Node current = root;
        HashMap<String,Node> children = root.children;

        for(int i = 0 ; i < input.length()+1 ; i++){
            String temp = input.substring(0,i);
            if(children.containsKey(temp)){
                current = children.get(temp);
                current.isLeaf = false;
                //System.out.println("found key: "+temp);
            }else{
                current = new Node(temp);
                children.put(temp,current);
                //System.out.println("created new key: "+temp);
            }
            children = current.children;
        }
        current.isWord = true;
        current.word = input;
        //System.out.println(current.word);
        current.isLeaf = true;

    }

    //==========Prints the trie in order==========\\
    public void print(){
        print(root);
    }
    public void print(Node node){
        if(node.isLeaf){
            System.out.println(node.word);
        }else{
            if(node.isWord){
                System.out.println(node.word);
            }
            Set<String> prefixes = node.children.keySet();
            Object[] arr_kids = prefixes.toArray();
            for(int i = 0; i < arr_kids.length ; i++){
                print(node.children.get(arr_kids[i]));
            }
        }
    }

    //==========Function to see if the input is a word in the trie==========\\
    public boolean find(String input){
        Node current = root;
        HashMap<String,Node> children = root.children;
        for(int i = 0 ; i < input.length()+1 ; i++){
            String temp = input.substring(0,i);
            if(children.containsKey(temp)){
                current = children.get(temp);
                children = current.children;
            }else{
                return false;
            }
        }
        if(current.isWord){
            return true;
        }
        return false;
    }

    //==========Main function to find the suggested word==========\\
    public String findSuggestion(String input){
        if(find(input)){    //if the input is found in the trie
            return input;
        }else if(find(input.toUpperCase())){    //checks for all uppercase
            return input.toUpperCase();
        }else if(find(input.toLowerCase())){    //checks for all lowercase
            return input.toLowerCase();
        }else{
            char first = Character.toUpperCase(input.charAt(0));
            String temp_input = first+input.substring(1);   //checks for a proper name by changing the first character to uppercase
            if(find(temp_input)){
                return temp_input;
            }

            //------------else-------------
            Node current = root;
            HashMap<String,Node> children = root.children;
            String prefix;
            String suggested = "error";
            int index = 0;
            for(int i = 0 ; i < input.length() ; i++){  //finds the longest prefix that is found in the trie
                String temp = input.substring(0,i);
                if(children.containsKey(temp)){
                    current = children.get(temp);
                    children = current.children;
                    index++;
                }else{
                    break;  //once the input is not found in the prefix trie, breaks
                }
            }
            prefix = input.substring(0,index);
            suggested = findClose(prefix);  //passes prefix to findClose()
            return suggested;
        }
    }

    //==========Finds a close string to the given prefix==========\\
    public String findClose(String prefix){
        //System.out.println("finding closest string...");
        Node current = root;
        HashMap<String,Node> children = root.children;
        for(int i = 0 ; i < prefix.length() ; i++){ //iterates down to the given prefix in the trie
            String temp = prefix.substring(0,i);
            current = children.get(temp);
            children = current.children;
        }
        if(current.isWord){ //if the prefix is a word, returns that word
            return current.word;
        }else{
            while(!current.isWord){ //iterates down until it finds a word
                Set<String> prefixes = current.children.keySet();
                Object[] arr_kids = prefixes.toArray();
                current = current.children.get(arr_kids[0]);    //finds the first child of the current node
            }
            return current.word;    //returns the word
        }
    }


}