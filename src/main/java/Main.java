import java.io.File;

public class Main {
    public static void main(String[] args) {

        StringContainer st = new StringContainer("\\d{2}[-]\\d{3}");


        st.add("60-444");
        st.add("61-444");
        st.add("00-000");
        st.add("62-444");
        st.add("63-444");
       //  st.insert("ala ma kota");
//
       //st.show();
//        st.deleteAt(2);
      //  System.out.println("po delete ");


      //  st.remove("00-000");

        //@persist should save all elements to file
        st.persist(new File("postalCodes.txt"));
        StringContainer fromFrile = StringContainer.fromFile(new File("postalCodes.txt")); // powinno wczytac stan listy z pliku postalCodes.txt


      //  st.show();
        //  fromst.show();

//        for(int i=0; i<st.size(); i++){
//            System.out.println(st.get(i)); //powinno wypisac dodane kody pocztowe
//        }




    }
}
