package org.example.Creature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CreatureManager {
    CreatureNode head = null;
    CreatureNode tail = null; 

    public CreatureNode getPointer(){
        return head;
    }
    public void movePointer(){
        head = head.child;
    }

    public void insertCreatedNode(String name, CreatureNode node){
        CreatureNode owner = new CreatureNode(name, node);
        this.insertAtEnd(owner);
    }

    public void insertStringNode(String name, String value, boolean printValueAsString){
        CreatureNode node = new CreatureNode(name, value, printValueAsString);
        this.insertAtEnd(node);
    }

    public void insertNodeList(String name, ArrayList<CreatureNode> values){
        CreatureNode node = new CreatureNode(name, values, false);
        this.insertAtEnd(node);
    }

    public void insertFromHashMap(String name, HashMap<String,String> values, boolean printValueAsString){
        CreatureNode current = null;
        CreatureNode head = current;
        for (String key : values.keySet()) {
            CreatureNode newNode = new CreatureNode(key, values.get(key), printValueAsString);
            if (head == null) {
                head = newNode;
                current = head;
            } else {
                current.child = newNode;
                current = newNode;
            }
        }
        CreatureNode node = new CreatureNode(name, head);
        this.insertAtEnd(node);
    }

    public void insertFromMapListOfMaps(HashMap<String, ArrayList<HashMap<String, String>>> traitMap){
        for (String key : traitMap.keySet()) {
            ArrayList<CreatureNode> traitsForKey = new ArrayList<>();
            int i = 0;
            for (HashMap<String, String> trait : traitMap.get(key)) {
                CreatureNode name = new CreatureNode("name", trait.get("name"), true);
                CreatureNode entries = this.createLiteralList("entries", new ArrayList<>(Arrays.asList(trait.get("description"))), true);
                CreatureNode sort = new CreatureNode("sort", String.valueOf(i), false);
                
                entries.setChild(sort);
                name.setChild(entries);

                CreatureNode parent = new CreatureNode(null, name);

                traitsForKey.add(parent);
                i++;
            }   

            this.insertNodeList(key, traitsForKey);
        }
    }

    public void insertLiteralList(String name, ArrayList<String> values, boolean printValueAsString){
        CreatureNode node = this.createLiteralList(name, values, printValueAsString);
        this.insertAtEnd(node);
    }
    public CreatureNode createLiteralList(String name, ArrayList<String> values, boolean printValueAsString){
        ArrayList<CreatureNode> finalList = new ArrayList<>(); 

        for (String value : values) {
            CreatureNode tmpNode = new CreatureNode(null, value, printValueAsString);
            finalList.add(tmpNode);
        }
        CreatureNode node = new CreatureNode(name, finalList, printValueAsString);
        return node;
    }

    private void insertAtEnd(CreatureNode node){
        if (head == null){
            this.head = node;
        }
        if (tail != null) {
            this.tail.setChild(node);
        }
        this.tail = node;
    }

    public void print(CreatureNode pointer, int depth){
        if (pointer == null){
            pointer = this.head;
        }
        if (depth == 0){
            System.out.println("-------");
        }
        while (pointer != null ) {
            for (int i = 0; i < depth; i++) {
                System.out.print("  ");
            }
            System.out.println(pointer);
            if (pointer.getType().equals("object")) {
                this.print(pointer.getObjectValue(), depth+1);
            }
            pointer = pointer.child;
        }   
    }
}
