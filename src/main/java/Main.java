import java.io.File;

public class Main {
    public static void main(String[] args) {

        StringContainer st = new StringContainer("\\d{2}[-]\\d{3}", true);

        st.add("02-495");//git
        st.add("01-120");//git
        st.add("05-123");//git
        st.add("00-000");//git
        //      st.add("ala ma kota"); //powinno sie wywalic wyjatkiem InvalidStringContainerValueException(badValue)
        for (int i = 0; i < st.size(); i++) {
            System.out.println(st.get(i)); //powinno wypisac dodane kody pocztowe
        }

        st.remove(0);            //usuwa "02-495"
        st.remove("00-000"); // usuwa "00-000"

        System.out.println("po usunieciu");
        for (int i = 0; i < st.size(); i++) {
            System.out.println(st.get(i)); //powinno wypisac dodane kody pocztowe
        }

        st.persist(new File("postalCodes.txt")); // powinno zapisac stan listy do pliku postalCodes.txt

        StringContainer fromFile = StringContainer.fromFile(new File("postalCodes.txt")); // powinno wczytac stan listy z pliku postalCodes.txt

        System.out.println(fromFile.equals(st)); // powinno dac true, te same dane w srodku

        //       nasza liste mozna tez parametryzowac tak aby nie dalo sie wrzucac powtorzen np:
        //      StringContainer st = new StringContainer("\\d{2}[-]\\d{3}", true); //jakis parametr np: duplicatedNotAllowed - domyslnie false
        //       wtedy np:
        st.add("02-495");//git
        //    st.add("02-495");//powinno rzucic wyjatkiem DuplicatedElementOnListException
    }
}