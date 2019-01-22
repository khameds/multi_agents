import java.util.List;

/**
 *  Defines domains of values for the variables in the DCOP
 */
public class Domain
{
    private int id;
    private List<Integer> values;
    private int cardinality;

    public Domain(int id, List<Integer> values, int cardinality)
    {
        this.id = id;
        this.values = values;
        this.cardinality = cardinality;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public List<Integer> getValues()
    {
        return values;
    }

    public void setValues(List<Integer> values)
    {
        this.values = values;
    }

    public int getSize()
    {
        return values.size();
    }

    public int getCardinality()
    {
        return cardinality;
    }

    public void setCardinality(int cardinality)
    {
        this.cardinality = cardinality;
    }
}
