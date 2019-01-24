public class Main {

    public static void main(String[] args)
    {
        int MAX_FREQUENCY_ID = 50;
        Parser parser = new Parser();
        parser.readInputs(MAX_FREQUENCY_ID);
        parser.createNaiveXML("test.xml");
    }
}


















