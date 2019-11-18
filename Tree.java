//==========Binary Tree==========\\
public class Tree extends TreesAbstract{
    Node root;

    //==========Node Class==========\\
    class Node {
        String data;
        Node right;
        Node left;

        Node(String item){
            data = item;
        }

        void setRight(Node right) {
            this.right = right;
        }
        void setLeft(Node left) {
            this.left = left;
        }

        String getData(){
            return data;
        }
    }

    //==========Function to see if some string is in the tree==========\\
    public boolean find(String input){
        return find(input,root);
    }
    public boolean find(String input, Node node){
        if(node==null){
            return false;
        }else if(node.getData().compareTo(input) == 0){
            return true;
        }else if(node.getData().compareTo(input) < 0){
            return find(input, node.right);
        }else{
            return find(input, node.left);
        }
    }

    //==========Function to insert items into the tree==========\\
    public void insert(String input){
        root = insert(input,root);
    }
    public Node insert(String input, Node node){
        if(node==null){
            return new Node(input);
        }else if(node.getData().compareTo(input) < 0){
            node.setRight(insert(input, node.right));
        }else{
            node.setLeft(insert(input, node.left));
        }
        return node;
    }

    //==========function to print the tree==========\\
    public void print(){
        print(root);
    }
    public void print(Node node){

        if(node==null){
            return;
        }else{
            print(node.left);
            System.out.println(node.getData());
            print(node.right);
        }
    }

    //==========Main fuction to find the suggested word==========\\
    public String findSuggestion(String input){

        if(find(input)){
            return input;
        }else if(find(input.toUpperCase())){    //checks for all uppercase
            return input.toUpperCase();
        }else if(find(input.toLowerCase())){    //checks for all lowercase
            return input.toLowerCase();
        }else{
            char first = Character.toUpperCase(input.charAt(0));
            String temp_item = first+input.substring(1);   //checks for a proper name by changing the first character to uppercase
            if(find(temp_item)){
                return temp_item;
            }

            String prefix;
            String suggested = "";
            for(int i = input.length()-2 ; i>=0 ; i--){  //starts by removing the last character of the string and passes to findClose
                prefix = input.substring(0,i);           //finds a word that starts with the largest prefix of the given string
                suggested = findClose(prefix);
                if(suggested!=null){
                    break;
                }
            }
            return suggested;
        }
    }

    //==========Function to find a word close to the input==========\\
    public String findClose(String input){
        return findClose(input,root);
    }
    public String findClose(String item, Node node){
        if(node==null){
            return null;
        }else if(node.getData().startsWith(item)){
            return node.getData();
        }else if(node.getData().toLowerCase().compareTo(item) < 0){
            return findClose(item, node.right);
        }else{
            return findClose(item, node.left);
        }
    }

}