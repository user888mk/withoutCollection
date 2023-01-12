import java.io.File;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {


        //tworzymy klaske String container ktora bedzie mogla przyjmowac tylko Stringi z okreslonym Patternem podanym jako argument.
//      podczas tworzenia obiektu nalezy zdefinowac poprawnosc patternu i jesli pattern bedzie "zly- czyli taki ktory sie nie kompiluje" to ma zostac rzucony wyjatek InvalidStringContainerPatternException(badPattern)
//      wszystkie wyjatki w programie maja dziedziczyc RuntimeException.
//      tu w przykladzie dodajemy kody pocztowe

        Pattern pattern = Pattern.compile("\\d{2}[-]\\d{3}");
        StringContainer st = new StringContainer(pattern, true);

        st.add("01-123");//git
        st.add("02-495");//git
        st.add("03-120");//git
        st.add("04-123");//git
        st.add("00-000");//git

        //st.add("ala ma kota"); //powinno sie wywalic wyjatkiem InvalidStringContainerValueException(badValue)

//        for(int i=0; i<st.size(); i++){
//            System.out.println(st.get(i)); //powinno wypisac dodane kody pocztowe
//        }
//        System.out.println();
//        st.remove(1, st);  //usuwa "02-495"
//        st.remove("00-000", st); // usuwa "00-000"
//        System.out.println("po usunieciu");
//        for(int i=0; i<st.size(); i++){
//            System.out.println(st.get(i)); //powinno wypisac dodane kody pocztowe
//        }
//
//        st.persist(new File("postalCodes.txt"));
//        st.fromFile(new File("postalCodes.txt")); // powinno wczytac postac listy z pliku postalCodes.txt
//        System.out.println(st.fromFile());
    }
}
