package Data;

/**
 * An enum for the different types of attributes.
 */
public enum AttributeType {
    /**
     * Categorical attributes in no specific order.
     */
    NOMINAL,
    /**
     * Categorical and ordered.
     */
    ORDINAL,
    /**
     * Quantitative attributes.
     */
    NUMERICAL,
    /**
     * Attributes that can take 2 states.
     */
    BINARY
}
