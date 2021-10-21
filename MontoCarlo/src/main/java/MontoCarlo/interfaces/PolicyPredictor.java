package MontoCarlo.interfaces;

import MontoCarlo.NodeState;

public abstract class PolicyPredictor<PolicyInput,PolicyOut extends PolicyOutput> {


    public PolicyOut evaluate(NodeState nodeState){

        PolicyInput policyInput= ConvertToPolicyInput(nodeState);

        return evaluate(policyInput);

    }

    protected abstract PolicyInput ConvertToPolicyInput(NodeState nodeState);

    protected abstract PolicyOut evaluate(PolicyInput policyInput);

    public abstract int getNumOfOutputNodes();
}
