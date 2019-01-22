package input.scen09;

public class FileLoader
{
    public  String getCstFile()
    {
        return getClass().getResource("cst.txt").getPath();
    }

    public  String getCtrFile()
    {
        return getClass().getResource("ctr.txt").getPath();
    }

    public  String getDomFile()
    {
        return getClass().getResource("dom.txt").getPath();
    }

    public  String getVarFile()
    {
        return getClass().getResource("var.txt").getPath();
    }
}
