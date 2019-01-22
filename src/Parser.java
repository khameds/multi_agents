import input.scen09.FileLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Enum.enum_ConstraintType;
import Enum.enum_Operator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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

    private Criteria criterias;
    /**
     * Basic constructor, init lists
     */
    public Parser()
    {
        frequencies = new ArrayList<>();
        constraints = new ArrayList<>();
        domains = new ArrayList<>();
        criterias = new Criteria();
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
        //System.out.println("Reading : " + varFile);
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
        //System.out.println("Reading : " + domFile);
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
        //System.out.println("Reading : " + ctrFile);
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
        try (BufferedReader br = new BufferedReader(new FileReader(cstFile)))
        {
            String line;
            while ((line = br.readLine()) != null && !line.contains("a1 ="))
            {
                line = Util.cleanInputString(line);
            }

            List<Integer> criteria = new ArrayList<>();
            do
            {
                line = Util.cleanInputString(line);
                List<String> data = Arrays.asList(line.split(" "));

                criteria.add(Integer.parseInt(data.get(2)));
            }
            while ((line = br.readLine()) != null);

            if(criteria.size() >= 8)
            {
                criterias.setA1(criteria.get(0));
                criterias.setA2(criteria.get(1));
                criterias.setA3(criteria.get(2));
                criterias.setA4(criteria.get(3));
                criterias.setB1(criteria.get(4));
                criterias.setB2(criteria.get(5));
                criterias.setB3(criteria.get(6));
                criterias.setB4(criteria.get(7));

            }
            else
            {
                System.out.println("Failed to read optimisation criteria from :\n"
                                           + cstFile + "(" + criteria.size() + ")");
                criterias.setA1(1000);
                criterias.setA2(100);
                criterias.setA3(2);
                criterias.setA4(1);
                criterias.setB1(10000);
                criterias.setB2(1000);
                criterias.setB3(100);
                criterias.setB4(10);
            }
            br.close(); // Free everything
        }
        catch (FileNotFoundException fn)
        {
            System.out.println("File not found  : " + cstFile );
            System.out.println(fn.getMessage());
        }
        catch (IOException io)
        {
            System.out.println(io.getMessage());
        }

        System.out.println("Optimisation criterias are : "
                                   + "a1 = " + criterias.getA1()
                                   + " a2 = " + criterias.getA2()
                                   + " a3 = " + criterias.getA3()
                                   + " a4 = " + criterias.getA4()
                                   + " b1 = " + criterias.getB1()
                                   + " b2 = " + criterias.getB2()
                                   + " b3 = " + criterias.getB3()
                                   + " b4 = " + criterias.getB4());
    }

    /**
     *
     * @param fileName output xml file name
     */
    public void createNaiveXML(String fileName)
    {
        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            /*** CREATE INSTANCE ELEMENT (root) ***/
            Document doc = db.newDocument();
            Element rootElement = doc.createElement("instance");
            doc.appendChild(rootElement);

            /*** CREATE PRESENTATION ELEMENT ***/
            Element presentationElement = doc.createElement("presentation");
            presentationElement.setAttribute("name", "sampleProblem");
            presentationElement.setAttribute("maxConstraintArity", "2");
            presentationElement.setAttribute("maximize", "false");
            presentationElement.setAttribute("format", "XCSP 2.1_FRODO");
            rootElement.appendChild(presentationElement);

            /*** CREATE AGENTS ELEMENT ***/
            Element agentsElement = doc.createElement("agents");
            // In the case of naive generation we dont try to optimise the agent number (each agent handle one variable
            agentsElement.setAttribute("nbAgents", String.valueOf(frequencies.size()));
            rootElement.appendChild(agentsElement);
            for (Frequency frequency : frequencies){
                Element agent = doc.createElement("agent");
                agent.setAttribute("name", "agent"+frequency.getId());
                agentsElement.appendChild(agent);
            }

            /*** CREATE DOMAINS ELEMENT ***/
            Element domainsElement = doc.createElement("domains");
            domainsElement.setAttribute("nbDomains", String.valueOf(domains.size()));
            rootElement.appendChild(domainsElement);
            for (Domain domain : domains){
                Element domainElement = doc.createElement("domain");
                domainElement.setAttribute("name", "domain" + domain.getId());
                domainElement.setAttribute("nbValues", String.valueOf(domain.getCardinality()));
                String domainValues = "";
                for (Integer domainValue : domain.getValues()){
                    domainValues += String.valueOf(domainValue) + " ";
                }
                domainElement.appendChild(doc.createTextNode(domainValues));
                domainsElement.appendChild(domainElement);
            }

            /*** CREATE VARIABLES ELEMENT ***/
            Element variablesElement = doc.createElement("variables");
            variablesElement.setAttribute("nbVariables", String.valueOf(frequencies.size()));
            rootElement.appendChild(variablesElement);
            for (Frequency frequency : frequencies){
                Element variable = doc.createElement("variable");
                variable.setAttribute("name", "f" + frequency.getId());
                variable.setAttribute("domain", "domain" + frequency.getDomainId());
                variable.setAttribute("agent", "agent" + frequency.getId());
                variablesElement.appendChild(variable);
            }

            /*** CREATE PREDICATES ELEMENT ***/
            // Each predicate declares a whitespace delimited list of parameters, each preceded by its type
            Element predicatesElement = doc.createElement("predicates");
            predicatesElement.setAttribute("nbPredicates", "3");
            rootElement.appendChild(predicatesElement);

            // Constraints with '=' operator are defined as hard constraints (EQUAL)
            Element predicate = doc.createElement("predicate");
            predicate.setAttribute("name", "EQUAL");
            Element parameters = doc.createElement("parameters");
            parameters.appendChild(doc.createTextNode(" int F1 int F2 int VALUE "));
            predicate.appendChild(parameters);
            Element expression = doc.createElement("expression");
            Element functional = doc.createElement("functional");
            functional.appendChild(doc.createTextNode(" eq(abs(sub(F1, F2)),VALUE) "));
            expression.appendChild(functional);
            predicate.appendChild(expression);
            predicatesElement.appendChild(predicate);

            // Constraints with '>' operator are defined as hard constraints (GTHARD)
            predicate = doc.createElement("predicate");
            predicate.setAttribute("name", "GTHARD");
            parameters = doc.createElement("parameters");
            parameters.appendChild(doc.createTextNode(" int F1 int F2 int VALUE "));
            predicate.appendChild(parameters);
            expression = doc.createElement("expression");
            functional = doc.createElement("functional");
            functional.appendChild(doc.createTextNode(" gt(abs(sub(F1, F2)),VALUE) "));
            expression.appendChild(functional);
            predicate.appendChild(expression);
            predicatesElement.appendChild(predicate);

            // Constraints with non defined weight are defined as soft constraints (GTSOFT)
            predicate = doc.createElement("function");
            predicate.setAttribute("name", "GTSOFT");
            parameters = doc.createElement("parameters");
            parameters.appendChild(doc.createTextNode(" int F1 int F2 int VALUE int COST"));
            predicate.appendChild(parameters);
            expression = doc.createElement("expression");
            functional = doc.createElement("functional");
            functional.appendChild(doc.createTextNode(" if(gt(abs(sub(F1, F2)),VALUE),0, COST) "));
            expression.appendChild(functional);
            predicate.appendChild(expression);
            predicatesElement.appendChild(predicate);

            /*** CREATE CONSTRAINTS ELEMENT ***/
            Element constraintsElement = doc.createElement("constraints");
            constraintsElement.setAttribute("nbConstraints", String.valueOf(constraints.size()));
            rootElement.appendChild(constraintsElement);
            for (Constraint c : constraints){
                Element constraint = doc.createElement("constraint");
                constraint.setAttribute("name",
                                        c.getFrequenceId1() + "_" + c.getFrequenceId2() + "_" + c.getK12());
                constraint.setAttribute("arity", "2");
                constraint.setAttribute("scope",
                                        "f"+c.getFrequenceId1() + " f" + c.getFrequenceId2());
                if (c.getConstraintType() == enum_ConstraintType.Difference )
                {
                    constraint.setAttribute("reference", "EQUAL");
                }
                else if (c.getWeight() != -1)
                {// Input file didn't provided it
                    constraint.setAttribute("reference", "GTSOFT");
                }
                else
                {
                    constraint.setAttribute("reference", "GTHARD");
                }
                Element param = doc.createElement("parameters");
                int cost=-1;
                switch (c.getWeight()){
                    case 1:
                        cost = criterias.getA1();
                        break;
                    case 2:
                        cost = criterias.getA2();
                        break;
                    case 3:
                        cost = criterias.getA3();
                        break;
                    case 4:
                        cost = criterias.getA4();
                        break;
                }
                String params = "f" + c.getFrequenceId1()
                        + " f"+c.getFrequenceId2() + " "
                        + c.getK12() + (cost!=-1?" "+cost:"");
                param.appendChild(doc.createTextNode(params));
                constraint.appendChild(param);
                constraintsElement.appendChild(constraint);
            }

            /*** Generate the xml file ***/
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileName));
            transformer.transform(source, result);

            System.out.println("-> Generated xml file : " + fileName);

        }
        catch (ParserConfigurationException pce)
        {
            System.out.println(pce.getMessage());
            pce.printStackTrace();
        }
        catch (TransformerException tfe)
        {
            System.out.println(tfe.getMessage());
            tfe.printStackTrace();
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
