package model.contracts;

import org.json.JSONObject;


/**
 * Defines a contract for model classes that can be serialized to JSON,
 * demanding them to provide a method to convert their internal state
 * into a {@link JSONObject}, supporting data persistence between runs.
 */
public interface ISerializable
{
    /**
     * Serializes the object into a {@link JSONObject} representation.
     * @return A {@code JSONObject} containing the serialized data of the object.
     */
    JSONObject toJSON();
}
