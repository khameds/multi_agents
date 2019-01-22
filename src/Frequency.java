/**
 * Variables in the DCOP
 */
public class Frequency
{
    private int id;
    private int agentId;
    private int domainId;

    public Frequency(int id, int agentId, int domainId)
    {
        this.id = id;
        this.agentId = agentId;
        this.domainId = domainId;
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

    public int getDomainId()
    {
        return domainId;
    }

    public void setDomainId(int domainId)
    {
        this.domainId = domainId;
    }


}
