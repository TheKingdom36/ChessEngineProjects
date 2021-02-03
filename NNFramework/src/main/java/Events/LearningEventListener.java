package Events;

@FunctionalInterface
public interface LearningEventListener extends  java.util.EventListener {

    /**
     * This method gets executed when LearningRule fires LearningEvent which some class is listening to.
     * For example, if you want to print current iteration, error etc.
     * @param event holds the information about event tha occured
     */
    public void handleLearningEvent(LearningEvent event);
}