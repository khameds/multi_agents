import java.util.List;

/**
 *  Defines domains of values for the variables in the DCOP
 */
public class Domain
{
    private int id;
    private List<Integer> values;

    public Domain(int id, List<Integer> values)
    {
        this.id = id;
        this.values = values;
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
}
