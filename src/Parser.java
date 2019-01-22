import java.util.ArrayList;
import java.util.List;

public class Parser
{
    /**
     * Variables in the DCOP
     */
    private List<Frequency> frequencies;

    /**
     *  Define relations between variable tuples
     */
    private List<Constraint> constraints;

    /**
     *  Defines domains of values for the variables in the DCOP
     */
    private List<Domain> domains;

    /**
     * Location of input files :
     * var.txt, dom.txt, ctr.txt, cst.txt ,
     */
    private String inputFolder;
    /**
     * Basic constructor, init lists
     */
    public Parser(String inputFolder)
    {
        frequencies = new ArrayList<>();
        constraints = new ArrayList<>();
        domains = new ArrayList<>();
    }

    public void readInputs()
    {
        System.out.println("Current input folder : " + inputFolder);
        readInputs(inputFolder + "var.txt",
                   inputFolder + "dom.txt",
                   inputFolder + "ctr.txt",
                   inputFolder + "cst.txt");
    }

    public void readInputs(String varPath, String domPath, String ctrPath, String cstPath)
    {

    }

    /*** Getters ans Setters ***/
    public List<Frequency> getFrequencies()
    {
        return frequencies;
    }

    public void setFrequencies(List<Frequency> frequencies)
    {
        this.frequencies = frequencies;
    }

    public List<Constraint> getConstraints()
    {
        return constraints;
    }

    public void setConstraints(List<Constraint> constraints)
    {
        this.constraints = constraints;
    }

    public List<Domain> getDomains()
    {
        return domains;
    }

    public void setDomains(List<Domain> domains)
    {
        this.domains = domains;
    }
}
