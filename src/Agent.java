import java.util.ArrayList;
import java.util.List;

public class Agent
{
    private static int agentId = 0;

    {
        agentId++;
    }

    private List<Integer> frequencyIds;

    public Agent()
    {
        frequencyIds = new ArrayList<>();
    }

    public void addFrequency(Integer frequency)
    {
        frequencyIds.add(frequency);
    }


    /*** Getters and Setters ***/

    public static int getAgentId()
    {
        return agentId;
    }

    public List<Integer> getFrequencyIds()
    {
        return frequencyIds;
    }

    public void setFrequencyIds(List<Integer> frequencyIds)
    {
        this.frequencyIds = frequencyIds;
    }
}
