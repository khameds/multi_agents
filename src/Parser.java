import input.scen09.FileLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Enum.enum_ConstraintType;
import Enum.enum_Operator;

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
        readDomFile(domFile);
        readCtrFile(ctrFile);
        readCstFile(cstFile);
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

    private void readDomFile(String domFile)
    {
        /**
         * Read 'dom.txt' File
         */
        System.out.println("Reading : " + domFile);
        try (BufferedReader br = new BufferedReader(new FileReader(domFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = Util.cleanInputString(line);
                List<String> data = Arrays.asList(line.split(" "));
                Domain domain = new Domain(Integer.parseInt(data.get(0)),  // Domain Id
                                              Integer.parseInt(data.get(1))); // Cardinality

                for (int i=2; i<data.size(); i++)
                {
                    domain.addValue( Integer.parseInt(data.get(i)));
                }
                domains.add(domain);

                if(domain.getCardinality() != data.size()-2)
                    System.out.println("Warning : Domain's cardinality and data size don't match.");
            }

            System.out.println(domains.size() + " domains added.");
            br.close(); // Free everything
        }
        catch (FileNotFoundException fn)
        {
            System.out.println("File not found  : " + domFile);
            System.out.println(fn.getMessage());
        }
        catch (IOException io)
        {
            System.out.println(io.getMessage());
        }
    }


    private void readCtrFile(String ctrFile)
    {
        /**
         * Read 'ctr.txt' File
         */
        System.out.println("Reading : " + ctrFile);
        try (BufferedReader br = new BufferedReader(new FileReader(ctrFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = Util.cleanInputString(line);
                List<String> data = Arrays.asList(line.split(" "));

                enum_ConstraintType type;
                if(data.get(3).equals("D"))
                    type = enum_ConstraintType.Difference;
                else if(data.get(3).equals("C"))
                    type = enum_ConstraintType.Cosite;
                else if(data.get(3).equals("F"))
                    type = enum_ConstraintType.Fixe;
                else if(data.get(3).equals("P"))
                    type = enum_ConstraintType.Prefixe;
                else
                    type = enum_ConstraintType.Farfield;

                enum_Operator operator;
                if(data.get(4).equals(">"))
                    operator = enum_Operator.GREATER_THAN;
                else
                    operator = enum_Operator.EQUAL;

                Constraint constraint = new Constraint(Integer.parseInt(data.get(0)),   // Frequence 1 Id
                                                       Integer.parseInt(data.get(1)),   // Frequence 2 Id
                                                       type,                            // Constraint type
                                                       operator,                        // Operator ( >, = )
                                                       Integer.parseInt(data.get(4)));
                // If constraints' weights are defined
                if(data.size() > 5)
                    constraint.setWeight(Integer.parseInt(data.get(5)));
                constraints.add(constraint);
             }

            System.out.println(constraints.size() + " constraints added.");
            br.close(); // Free everything
        }
        catch (FileNotFoundException fn)
        {
            System.out.println("File not found  : " + ctrFile);
            System.out.println(fn.getMessage());
        }
        catch (IOException io)
        {
            System.out.println(io.getMessage());
        }
    }

    private void readCstFile(String cstFile)
    {
        /**
         * Read 'cst.txt' File
         */

        // TODO : Optimisation part
        System.out.println("Cannot read yet : " + cstFile);
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
