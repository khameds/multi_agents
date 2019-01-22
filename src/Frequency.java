/**
 * Variables in the DCOP
 */
public class Frequency
{
    private int id;
    private int agentId;
    private int domain;

    public Frequency(int id, int agentId, int domain)
    {
        this.id = id;
        this.agentId = agentId;
        this.domain = domain;
    }

    /*** Getters and Setters ***/
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getAgentId()
    {
        return agentId;
    }

    public void setAgentId(int agentId)
    {
        this.agentId = agentId;
    }

    public int getDomain()
    {
        return domain;
    }

    public void setDomain(int domain)
    {
        this.domain = domain;
    }


}
