import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringContainer {

    private String postCode;
    private Pattern regex;

    private StringContainer nextElement;

    private int index = 0;
    private boolean duplicationAllowed = false;


    public StringContainer(Pattern regex, boolean duplicationAllowed) {
        this.regex = regex;
        this.duplicationAllowed = duplicationAllowed;
        this.index = 0;
    }

    public StringContainer(String postCode, Pattern regex, int index, boolean duplicationAllowed) {
        this.postCode = postCode;
        this.regex = regex;
        this.index = index;
        this.duplicationAllowed = duplicationAllowed;
    }

    public void add(String postCode) {
        StringContainer pointerCopy = this;

        Matcher matcher = this.regex.matcher(postCode);
        if (this.postCode == null && this.index == 0) {
            this.postCode = postCode;
        }

        boolean postCodeExist;

        while (true) {
            postCodeExist = this.postCode != null;

            if (!pointerCopy.duplicationAllowed) {
                postCodeExist = false;

                if (postCode.equals(pointerCopy.postCode)) {
                    postCodeExist = true;
                    if (postCodeExist) {
                        throw new DuplicatedElementOnListException("Duplicatted not allowed");
                    }
                }
            }

            if (pointerCopy.nextElement != null) {
                pointerCopy = pointerCopy.nextElement;
            } else break;

        }
        if (!postCodeExist || !this.postCode.equals(postCode)) {
            if (matcher.find()) {
                StringContainer stringContainer = new StringContainer(postCode, regex, index + 1, duplicationAllowed);
                if (nextElement == null) {
                    nextElement = stringContainer;
                } else {
                    nextElement.add(postCode);
                }
            } else {
                throw new InvalidStringContainerPatternException("BadValue");
            }
        }
    }

    public void print() {
        if (nextElement != null) {
            System.out.println(postCode + "," + index);
            nextElement.print();
        } else {
            System.out.println(postCode + "," + index);
        }
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getPostCode() {
        return postCode;
    }

    public StringContainer getNextElement() {
        return nextElement;
    }

    public int getIndex() {
        return index;
    }

    public void setNextElement(StringContainer nextElement) {
        this.nextElement = nextElement;
    }

    private StringContainer nextInChain = null;
    private Boolean targetReached = false;

    public void remove(String data, StringContainer currentNode) {

        if (currentNode.getPostCode().equals(data)) {
            nextInChain = currentNode.getNextElement();
            targetReached = true;
            return;
        } else {

            remove(data, currentNode.getNextElement());
        }
        if (targetReached) {
            currentNode.setNextElement(nextInChain);
            targetReached = false;
        }
        fixIndex(0);
    }

    public void remove(int index, StringContainer currentNode) {

        if (index == 0) {
            currentNode = currentNode.getNextElement();
            nextInChain = currentNode.getNextElement();
            targetReached = true;
            return;
        }
        if (currentNode.getIndex() == index) {
            nextInChain = currentNode.getNextElement();
            targetReached = true;
            return;
        } else {
            remove(index, currentNode.getNextElement());
        }
        if (targetReached) {
            currentNode.setNextElement(nextInChain);
            targetReached = false;
        }
        fixIndex(0);
    }

    public void persist(File file) {

        File path = new File("src/main/resources/" + file);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            StringContainer container = this;
            while (true) {
                bw.write(container.postCode + " " + container.index + "\n");
                container = container.nextElement;
                if (container == null) break;
            }
            bw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void fromFile(File file) {
        String path = "src/main/resources/";
        File pathFile = new File(path + file);

        try {
            Scanner scanner = new Scanner(pathFile);

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] split = line.split(" ");
                String postCode = split[0];
                int index = Integer.parseInt(split[1]);

                System.out.println("file post code " + postCode + " file index " + index);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean fromFile() {
        File file = new File("src/main/resources/postalCodes.txt");
        StringContainer stringContainer = this;
        boolean result = true;
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] split = line.split(" ");
                String postCode = split[0];
                int index = Integer.parseInt(split[1]);

                if (!postCode.equals(stringContainer.postCode) || index != stringContainer.getIndex()) {
                    result = false;
                    System.out.println("Brak zgodności danych ");
                    System.out.println("File postcode " + postCode + "\t\t file index " + index);
                    System.out.println("Container postcode " + stringContainer.postCode + "\t container index " + stringContainer.index);
                    break;
                }
                stringContainer = stringContainer.nextElement;
            }
            scanner.close();
            return result;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public int size() {
        StringContainer pointerCopy = this;
        int counter = 1;

        while (true) {
            if (pointerCopy.nextElement != null) {
                pointerCopy = pointerCopy.nextElement;
                counter++;
            } else break;
        }
        return counter;
    }

    public String get(int i) {

        String postCode1 = "";
        StringContainer pointerCopy = this;
        int size = pointerCopy.size();

        for (int j = 0; j < size; j++) {

            if (i == pointerCopy.getIndex()) {
                postCode1 = pointerCopy.getPostCode();
                return postCode1;
            }
            pointerCopy = pointerCopy.nextElement;
        }
        return null;
    }

    public void fixIndex(int firstIndex) {

        StringContainer pointerCopy = this;
        int counter = firstIndex;

        while (true) {
            if (pointerCopy.nextElement != null) {
                pointerCopy = pointerCopy.nextElement;
                counter++;
                pointerCopy.setIndex(counter);
            } else break;
        }
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("StringContainer{");
        sb.append("postCode='").append(postCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

