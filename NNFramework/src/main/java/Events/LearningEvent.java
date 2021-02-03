package Events;

import NeuralNetwork.Block.LearningRule;

public class LearningEvent extends java.util.EventObject{
    LearningEvent.Type eventType;

    public LearningEvent(LearningRule source, LearningEvent.Type eventType) {
        super(source);
        this.eventType = eventType;
    }

    public LearningEvent.Type getEventType() {
        return eventType;
    }

    // Types of learning events to listen to
    public static enum Type {
        EPOCH_ENDED, LEARNING_STOPPED;
    }

    public static Type EPOCH_ENDED = Type.EPOCH_ENDED;
    public static Type LEARNING_STOPPED = Type.LEARNING_STOPPED;
}
