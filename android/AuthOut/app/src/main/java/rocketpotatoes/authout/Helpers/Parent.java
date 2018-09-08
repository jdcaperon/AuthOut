package rocketpotatoes.authout.Helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rocketpotatoes.authout.Helpers.Child;

public class Parent {
    private final String firstName;
    private final String surname;
    private final List<Child> children;

    /** A parent object in order to
     *
     * @param firstName - first name of the parent
     * @param surname   - surname of the parent
     * @param children - Array of Maps of child objects with keys
     *                   'firstName', 'surname', and 'status'
     */
    public Parent(String firstName, String surname, Map<String, Object>[] children) {
        this.children = new ArrayList<>();
        this.firstName = firstName;
        this.surname = surname;
        for (Map<String, Object> child : children) {
            if (!child.containsKey("firstName") ||
                    !child.containsKey("surname") || !child.containsKey("status")) {
                throw new IllegalArgumentException("First name, surname and status required.");
            }
            this.children.add(new Child(child.get("firstName").toString(),
                                        child.get("surname").toString(),
                                        child.get("status").toString()));
        }
    }

    //Todo this is for testing and is not required for actual implementation.Remove this.
    public Parent(String firstName, String surname, List<Child> children) {
        this.children = children;
        this.firstName = firstName;
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public List<Child> getChildren() {
        return children;
    }
}