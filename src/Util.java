public class Util
{
    public static String cleanInputString(String line)
    {
        return line.trim().replaceAll("( )+", " ");
    }
}
