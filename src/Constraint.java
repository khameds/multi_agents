import Enum.enum_ConstraintType;
import Enum.enum_Operator;

public class Constraint
{
    private int frequenceId1;                   // Field 1
    private int frequenceId2;                   // Field 2
    private enum_ConstraintType constraintType; // Field 3
    private enum_Operator operator;             // Field 4
    private int k12;                            // Field 5
    private int weight;                         // Field 6

    public int getFrequenceId1()
    {
        return frequenceId1;
    }

    public void setFrequenceId1(int frequenceId1)
    {
        this.frequenceId1 = frequenceId1;
    }

    public int getFrequenceId2()
    {
        return frequenceId2;
    }

    public void setFrequenceId2(int frequenceId2)
    {
        this.frequenceId2 = frequenceId2;
    }

    public enum_ConstraintType getConstraintType()
    {
        return constraintType;
    }

    public void setConstraintType(enum_ConstraintType constraintType)
    {
        this.constraintType = constraintType;
    }

    public enum_Operator getOperator()
    {
        return operator;
    }

    public void setOperator(enum_Operator operator)
    {
        this.operator = operator;
    }

    public int getK12()
    {
        return k12;
    }

    public void setK12(int k12)
    {
        this.k12 = k12;
    }

    public int getWeight()
    {
        return weight;
    }

    public void setWeight(int weight)
    {
        this.weight = weight;
    }

}
