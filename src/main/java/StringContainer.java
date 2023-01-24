import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringContainer {

    Container head;
    String regex;

    boolean duplicatedNotAllowed = false;

    public StringContainer(String regex, boolean duplicatedNotAllowed) {
        this.regex = regex;
        this.duplicatedNotAllowed = duplicatedNotAllowed;
    }

    public StringContainer(String regex) {
        this.regex = regex;
    }

    public StringContainer() {
    }

    public static StringContainer fromFile(File file) {
        File path = new File("src/main/resources/" + file);

        try (Scanner scanner = new Scanner(path)) {
            StringContainer stringContainer = new StringContainer();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] split = line.split(" |\\n");
                String data = split[0];
                if (data != null) {

                    Container container = new Container();
                    container.data = data;
                    stringContainer.addWithoutCheck(data);
                }
            }
            return stringContainer;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private void addWithoutCheck(String data) {

        Container container = new Container();
        container.data = data;

        if (head == null) {
            head = container;
            container.next = null;
        } else {
            Container n = head;
            while (n.next != null) {
                n = n.next;
            }
            n.next = container;
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
            Container n = head;
            while (n.next != null) {
                n = n.next;
            }
            n.next = container;
        }

        if (this.duplicatedNotAllowed) {
            Container container1 = head;
            int counter = 0;
            while (container1.next != null) {
                if (data.equals(container1.data)) {
                    counter++;
                }
                container1 = container1.next;
            }
            if (counter >= 1) {
                throw new InvalidStringContainerValueException("duplicatedValue");
            }
        }
    }


    public int size() {
        int counter = 1;
        Container n = head;

        while (n.next != null) {
            counter++;
            n = n.next;
        }
        return counter;
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
    public boolean equals(Object stringContainer) {
        StringContainer p = (StringContainer) stringContainer;
        StringContainer l = this;

        if (p.size() != l.size()) {
            return false;
        }
        int size = p.size();
        Container pc = p.head;
        Container lc = l.head;

        for (int i = 0; i < size; i++) {

            if (!lc.data.equals(pc.data)) {
                return false;
            }
            pc = pc.next;
            lc = lc.next;
        }
        return true;
    }

    @Override
    public String toString() {
        return "StringContainer{" +
                "head=" + head +
                ", regex='" + regex + '\'' +
                '}';
    }
}