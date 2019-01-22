import input.scen01.FileLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
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
     * Basic constructor, init lists
     */
    public Parser()
    {
        frequencies = new ArrayList<>();
        constraints = new ArrayList<>();
        domains = new ArrayList<>();
    }

    public void readInputs()
    {
        FileLoader fileLoader = new FileLoader();

        readInputs(fileLoader.getVarFile(),
                   fileLoader.getDomFile(),
                   fileLoader.getCtrFile(),
                   fileLoader.getCstFile());
    }

    public void readInputs(String varFile, String domFile, String ctrFile, String cstFile)
    {
        readVarFile(varFile);
    }

    private void readVarFile(String varFile)
    {
        /**
         * Read 'var.txt' File
         */
        System.out.println("Reading : " + varFile);
        try (BufferedReader br = new BufferedReader(new FileReader(varFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = Util.cleanInputString(line);
                List<String> data = Arrays.asList(line.split(" "));
                Frequency frequency = new Frequency(Integer.parseInt(data.get(0)),  // Variable Id
                                                    0, // AgentId (unknown yet)
                                                    Integer.parseInt(data.get(1))); // Domain Id
                frequencies.add(frequency);
            }
            System.out.println(frequencies.size() + " frequencies added.");
            br.close(); // Free everything
        }
        catch (FileNotFoundException fn)
        {
            System.out.println("File not found  : " + varFile);
            System.out.println(fn.getMessage());
        }
        catch (IOException io)
        {
            System.out.println(io.getMessage());
        }
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
