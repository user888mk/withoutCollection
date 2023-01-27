import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringContainer {
    private Container head;
    private String regex;
    private Pattern pattern;
    boolean duplicatedNotAllowed = false;

    public StringContainer(String regex, boolean duplicatedNotAllowed) {
        if (regex == null) {
            throw new InvalidStringContainerValueException("Regex must not be null");
        }
        this.pattern = Pattern.compile(regex);
        this.duplicatedNotAllowed = duplicatedNotAllowed;
    }

    public StringContainer(String regex) {
        if (regex == null) {
            throw new InvalidStringContainerValueException("Regex must not be null");
        }
        this.regex = regex;
    }

    private StringContainer() {
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
            throw new IllegalArgumentException("File not found");
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

        Matcher matcher = this.pattern.matcher(data);

        if (!matcher.find()) {
            throw new InvalidStringContainerValueException("badValue");
        }
        if (head == null) {
            head = container;
            container.next = null;
        } else {
            Container n = head;
            while (n.next != null) {
                if ((n.data.equals(data) || (n.next.next == null && n.next.data.equals(data))) && duplicatedNotAllowed) {
                    throw new DuplicatedElementOnListException("Duplicated not allow");
                }
                n = n.next;
            }
            n.next = container;
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
                if (c == null) {
                    throw new IllegalArgumentException("Bad value ");
                }
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
        throw new IndexOutOfBoundsException("Data must have correct value ");
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
            throw new IllegalArgumentException("File not found");
        }
    }

    @Override
    public boolean equals(Object stringContainer) {
        if (stringContainer == null)
            return false;

        if (this.getClass() != stringContainer.getClass())
            return false;

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
        final StringBuilder sb = new StringBuilder("StringContainer{");
        sb.append("head=").append(head);
        sb.append(", regex='").append(regex).append('\'');
        sb.append('}');
        return sb.toString();
    }
}