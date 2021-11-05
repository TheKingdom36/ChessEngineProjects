package MontoCarlo;

public abstract class PolicyPredictor<PolicyInput,GState extends GameState> {

    public PolicyOutput evaluate(GState gameState){

        PolicyInput policyInput= ConvertToPolicyInput(gameState);

        return evaluate(policyInput);

    }

    protected abstract PolicyInput ConvertToPolicyInput(GState gameState);

    protected abstract PolicyOutput evaluate(PolicyInput policyInput);

    public abstract int getNumOfOutputStates();
}
