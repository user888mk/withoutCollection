import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringContainer {

    Container head;
    static  String  regex;

    public StringContainer(Container head) {
        this.head = head;
    }

    public String getRegex() {
        return regex;
    }

    public StringContainer(String regex) {
        regex = regex;
    }

    public StringContainer() {
    }

    public static StringContainer fromFile(File file) {
        //load data from file to StringContainer

        File path = new File("src/main/resources/" + file);


        try (Scanner scanner = new Scanner(path)) {
            StringContainer stringContainer = null;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] split = line.split(" |\\n");
                String data = split[0];
                if (data != null) {


                    Container container = new Container();
                    container.data = data;

                    stringContainer = new StringContainer();
                    stringContainer.add(data);
                    System.out.println(stringContainer);
                }
            }
            return stringContainer;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void add(String data) {

        Container container = new Container();
        container.data = data;

        Pattern pattern = Pattern.compile(this.regex);
        Matcher matcher = pattern.matcher(data);

        if (!matcher.find()) {
            throw new InvalidStringContainerValueException("badValue");
        }

        if (head == null) {
            head = container;
            container.next = null;
        } else {
            //przechodzenie po elementach i szukanie ostatniego
            Container n = head;
            while (n.next != null) {
                //podmiana referencji zeby przehsc do kolejnego kontenera
                n = n.next;
            }
            n.next = container;
        }
    }

    public void show() {
        Container container = head;
        while (container.next != null) {
            System.out.println(container.data);
            container = container.next;
        }
        System.out.println(container.data);
    }

    public void remove(int index) {

        if (index == 0) {
            head = head.next;
        } else {
            Container n = head;
            Container n1 = null;

            for (int i = 0; i < index - 1; i++) {
                n = n.next;
            }
            n1 = n.next;
            n.next = n1.next;
            n1 = null;
        }
    }

    public void remove(String inputData) {
        Container c = head;
        Container c1 = null;

        if (c.data.equals(inputData)) {
            head = head.next;
        } else {
            while (!c.data.equals(inputData)) {
                c1 = c;
                c = c.next;
            }
            c1.next = c.next;
            c = null;
        }

    }

    public int size() {
        int counter = 1;
        Container n = head;

        while (n.next != null) {
            counter++;
            n = n.next;
        }
        //   System.out.println(counter);
        return counter;
    }

    public String get(int data) {

        Container n = head;
        String result = "";

        for (int i = 0; i < size(); i++) {
            if (i == data) {
                result = n.data;
                return result;
            }
            n = n.next;
        }
        return null;
    }


    public void persist(File file) {

        File path = new File("src/main/resources/" + file);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {

            Container n = head;
            for (int i = 0; i < size(); i++) {
                bw.write(n.data);
                bw.newLine();
                n = n.next;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("StringContainer{");
        sb.append("head=").append(head);
        sb.append(", regex='").append(regex).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
