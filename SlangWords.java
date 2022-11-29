import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class SlangWords {
    public static String fileName = "slang.txt";
    public static Scanner sc = new Scanner(System.in);
    public static HashMap<String, List<String>> m = new HashMap<String, List<String>>();
    public static ArrayList<String> history = new ArrayList<String>();

    public static void readFile(String fileName) {
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            
            String a;
            while ((a = br.readLine()) != null) {
                List<String> defi = new ArrayList<String>();

                String[] s = a.split("`");
                if (s[1].contains("|")) {
                    String[] s_tmp = s[1].split("\\|");
                    for (int i = 0; i < s_tmp.length; i++) {
                        s_tmp[i] = s_tmp[i].trim();
                    }
                    defi = Arrays.asList(s_tmp);
                }
                else {
                        defi.add(s[1]);
                }
                m.put(s[0], defi);
            }

            fr.close();
            br.close();
        }
        catch (Exception ex) {
            System.out.println("Error ");
        }
    }
    
    public static void writeFile(String fileName) {
        try {
            FileWriter fw = new FileWriter(fileName);
            
            for (String key : m.keySet()) {
                fw.write(key + "`");
                List<String> tmp = m.get(key);
                int i = 0;
                for (i = 0; i < tmp.size() - 1; i++) {
                    fw.write(tmp.get(i) + "| ");
                }
                fw.write(tmp.get(i) + "\n");
            }

            fw.close();
        }
        catch (Exception ex) {
            System.out.println("Error");
        }
    }
    
    public static void writeHistory(String fileName) {
        try {
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);
            
            for (String i : history) {
                fw.write(i + "\n");
            }

            fw.close();
            bw.close();
        }
        catch (Exception ex) {
            System.out.println("Error: ");
        }
    }
    
    public static ArrayList<String> loadHistory (String fileName) {
        ArrayList<String> his = new ArrayList<String>();
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            
            String a;
            while ((a = br.readLine()) != null) {
                his.add(a);
            }

            fr.close();
            br.close();
        }
        catch (Exception ex) {
            System.out.println("Error: ");
        }

        return his;
    }

    public static void showDefinition(String i) {
        List<String> list = m.get(i);
        for (String s: list) {
            System.out.print(s + ", ");
        }
        System.out.print("\b\b     \n");
    }
    
    public static void addSlang(String slang, String means) {
            ArrayList<String> tmp = new ArrayList<String>();
            tmp.add(means);
            m.put(slang, tmp);
            System.out.println("Add successfully!!!");
    }

    public static void duplicate(String slang, String means) {
        List<String> tmp = new ArrayList<String>();
        tmp = m.get(slang);
        tmp.add(means);
        m.put(slang, tmp);
        System.out.println("Add successfully!!!");
    }

    public static String randomKey() {
        Object[] Keys = m.keySet().toArray();
        return (String)Keys[new Random().nextInt(Keys.length)];
    }
        
    public static void Function_1() {
        
        System.out.println("\n");
        System.out.print("Enter a Slang word: ");
        String key = sc.nextLine();
        history.add(key);
        
        if (!m.containsKey(key)) {
            System.out.println("Not Found!");
        }
        else {
            List<String> list = m.get(key);
            System.out.println("Definiton:");
            for (String s: list) {
                System.out.println("- " + s);
            }
        }
        
        Menu();
    }

    public static void Function_2() {
        
        System.out.println("\n");
        ArrayList<String> means = new ArrayList<String>();
        System.out.print("Enter any word to find a Slang word: ");
        String word = sc.nextLine();

        history.add(word);
        for (String i : m.keySet()) {
            for (String s: m.get(i)) {
                if (s.contains(word)) {
                    means.add(i);
                }
            }
        }

        if (!means.isEmpty()) {
            System.out.println("Slang words found: ");
            for (String i : means) {
                System.out.print("- " + i + ": ");
                showDefinition(i);
            }
        }
        else {
            System.out.println("Not Found!");
        }
        
        Menu();
    }

    public static void Function_3() {
        
        System.out.println("\n");
        System.out.println("History:");
        for (String s : history) {
            System.out.println("- " + s);
        }
        
        Menu();
    }
    
    public static void Function_4() {
        
        System.out.println("\n");
        System.out.print("Enter Slang word: ");
        String slang = sc.nextLine();

        System.out.print("Enter meanings: ");
        String means = sc.nextLine();
 
        if (m.containsKey(slang)) {
            System.out.println("This Slang word was existed, you want to choose: ");
            System.out.println("1. Overwrite");
            System.out.println("2. Dupicate");
            int c = sc.nextInt();
            if (c == 1) {
                addSlang(slang, means);
                writeFile(fileName);
            }
            else if (c == 2) {
                duplicate(slang, means);
                writeFile(fileName);
            }
        }
        else {
            addSlang(slang, means);
            writeFile(fileName);
        }
        
        Menu();
    }

    public static void Function_5() {
        System.out.println("\n");
        String slang;       

        do {
            System.out.print("Enter Slang word you want to edit: ");
            slang = sc.nextLine();
            
            if(m.containsKey(slang))
                break;
            else
                System.out.println("This Slang word does not exist!");
        }
        while(!m.containsKey(slang));
        
        System.out.print("Enter new Meaning: ");
        String means = sc.nextLine();
            
        List<String> tmp = new ArrayList<String>();
        tmp.add(means);
        m.put(slang, tmp);
        writeFile(fileName);
        System.out.println("Edit successfully!!");
        
        Menu();
    }

    public static void Function_6() {
        System.out.println("\n");
        String slang;
        
        do {
            System.out.print("Enter Slang word you want to delete: ");
            slang = sc.nextLine();
            
            if(m.containsKey(slang))
                break;
            else
                System.out.print("This Slang word does not exist!");
        }
        while(!m.containsKey(slang));
        
        System.out.println("Are you sure you want to delete (yes/no)? ");
        String choice = sc.next();
        if (choice.equals("yes")) {
            m.remove(slang);
            writeFile(fileName);
            System.out.println("Delete successfully!!");
        }
        else {
            System.out.println("Not delete!");
        }
        
        Menu();
    }

    public static void Function_7() {
        System.out.println("\n");

        m.clear();
        if (m.isEmpty()) {
            readFile("slang.txt");
            System.out.println("Reset successfully!!");
        }
        else {
            System.out.println("Reset Fail!");
        }
        
        Menu();
    }

    public static void Function_8() {
        System.out.println("\n");
        String random = randomKey();
        
        System.out.println("Ramdom Slang word:");
        System.out.print("- " + random + ": ");
        showDefinition(random);
        
        Menu();
    }
    

    public static void Menu() {
        
        System.out.println("\n==========================================");
        System.out.println("MENU");
        System.out.println("1. Search by Slang word");
        System.out.println("2. Search by Definition");
        System.out.println("3. History");
        System.out.println("4. Add a Slang word");
        System.out.println("5. Edit a Slang word");
        System.out.println("6. Deleta a Slang word");
        System.out.println("7. Reset");
        System.out.println("8. Random 1 Slang word");
        System.out.println("9. Fun Game (Find Definition by Slang word)");
        System.out.println("10. Fun Game (Find Slang word by Definition)");
        System.out.println("11. Exit");
        
        System.out.print(">>> Your choice: ");
        int choice = sc.nextInt();
        String s = sc.nextLine();
        
        if (choice == 1) {
            Function_1();
        }
        else if (choice == 2) {
            Function_2();
        }
        else if (choice == 3) {
            Function_3();
        }
        else if (choice == 4) {
            Function_4();
        }
        else if (choice == 5) {
            Function_5();
        }
        else if (choice == 6) {
            Function_6();
        }
        else if (choice == 7) {
            Function_7();
        }
        else if (choice == 8) {
            Function_8();
        }
        else if (choice == 9) {
            Function_9();
        }
        else if (choice == 10) {
            Function_10();
        }
        else {
            writeHistory("history.txt");
            System.exit(0);
        }
    }
    
    public static void main(String[] args) {
        
        readFile("slang.txt");
        
        history = loadHistory("history.txt");
        Menu();
    }
    
}