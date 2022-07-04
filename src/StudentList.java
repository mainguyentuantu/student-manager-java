
import java.util.Hashtable;
import java.io.FileInputStream;// for reading binary file 
import java.io.FileOutputStream;// for writing binary file
import java.io.InputStreamReader;// For reading UTF-8 char. in a file 
import java.io.OutputStreamWriter; // For writing UTF-8 char. to a file 
import java.io.BufferedReader; // for reading lines in a text file 
import java.io.PrintWriter; // for writing lines in a text file
import java.util.Iterator;// for traversing the list 
import java.util.Scanner;// for inputting data
import java.util.ArrayList; // for getting student list then sort the list 
import java.util.Collections; // for sorting based on codes
// <code, student>

public class StudentList extends Hashtable<String, Student> {

    Scanner sc = new Scanner(System.in);

    public StudentList() {
        super();

    }

    public boolean loadFromFile(String filename) {
        try { // using intermediate class between text and binary streams 
            FileInputStream fi = new FileInputStream(filename);
            InputStreamReader isr = new InputStreamReader(fi, "UTF8");
            BufferedReader bf = new BufferedReader(isr); // read unicode lines 
            String line; // SE12345,Hoàng Anh Tuấn,5
            while ((line = bf.readLine()) != null) {
            line = line.trim();
            String[] ar = line.split("[,]");
            if (ar.length == 3) {      
            Student st = new Student(ar[0], ar[1], Integer.parseInt(ar[2]));
            this.put(st.code, st); // Key = student code
            }
            }
            bf.close(); // closing the file
            isr.close();
            fi.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace(); // printing called methods in stack
        }
        return false;
    }

    public boolean saveToFile(String filename) {
        try { // using intermediate class between text and binary streams 
            FileOutputStream fo = new FileOutputStream(filename);
            OutputStreamWriter osw = new OutputStreamWriter(fo, "UTF8");
            PrintWriter pw = new PrintWriter(osw); // write unicode line 
            // get keyset
            Iterator<String> it = this.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                Student st = this.get(key);
                pw.println(st); // write a student to file
            }
            pw.close(); // closing the file
            osw.close();
            fo.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace(); // printing called methods in stack
        }
        return false;
    }

    public Student search(String code) {
        return this.get(code);
    }

    public void addStudent() {
        String code, name;
        int mark;
        System.out.println("Enter data of new student: ");
        boolean cont = true;
        do {
            System.out.println("Code: ");
            code = sc.nextLine().toUpperCase().trim();
            cont = search(code) != null;
            if (cont) {
                System.out.println("Code is duplicated!");
            }
        } while (cont);
        System.out.println("Name:");
        name = sc.nextLine().toUpperCase().trim();
        System.out.println("Mark: ");
        mark = Integer.parseInt(sc.nextLine());
        this.put(code, new Student(code, name, mark));
        System.out.println("New student " + code + " has been added.");
    }

    public void searchStudent() {
        String code;
        if (this.isEmpty()) {
            System.out.println("Empty list!");
            return;
        }
        System.out.print("Enter student code for searching:");
        code = sc.nextLine().toUpperCase();
        Student st = this.search(code);
        if (st == null) {
            System.out.println("This code doesn't exist!");
        } else {
            System.out.println(st);
        }
    }

    public void removeStudent() {
        String code;
        if (this.isEmpty()) {
            System.out.println("Empty list!");
            return;
        }
        System.out.print("Enter student code for removing:");
        code = sc.nextLine().toUpperCase();
        Student st = this.search(code);
        if (st == null) {
            System.out.println("This code doesn't exist!");
        } else {
            remove(code);
            System.out.println("The student " + code + " has been removed.");
        }

    }

    public void updateStudent() {
        String code;
        if (this.isEmpty()) {
            System.out.println("Empty list!");
            return;
        }
        System.out.print("Enter student code for updating:");
        code = sc.nextLine().toUpperCase();
        Student st = this.search(code);
        if (st == null) {
            System.out.println("This code doesn't exist!");
        } else {
            st.update();
        }
    }

    public void print() {
        if (this.isEmpty()) {
            System.out.println("Empty list!");
            return;
        }
        ArrayList<Student> list = new ArrayList();
        list.addAll(this.values());
        Collections.sort(list);
        for (Student st : list) {
            System.out.println(st);
        }
    }
}
