package rocketpotatoes.authout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void updateButtonOptions(Parent parent) {
        parent = null; //todo actual parent implementation or initiation

        List<Child> children = parent.getChildren();
        getOptionByChildren(children);

    }

    /** Checks if a single option can be fit to a list of children.
     * 
     * @param children - a list of child objects to check
     * @return a {@link DynamicButtonOption} of which button form to take
     */
    public Enum getOptionByChildren(List<Child> children) {
        String signedInText = "Signed-In";
        if (children.size() == 0) return DynamicButtonOption.NOT_COMPATIBLE;
        if (children.size() == 1) {
            if (children.get(0).getStatus().equals(signedInText)) {
                return DynamicButtonOption.SIGN_IN;
            }
            return DynamicButtonOption.SIGN_OUT;
        } else {
            for (int i = 0; i < children.size() - 1; i++) {
                if (!children.get(i).getStatus().equals(children.get(i + 1).getStatus())) {
                    return DynamicButtonOption.NOT_COMPATIBLE;
                }
            }
        }
        return children.get(0).getStatus().equals(signedInText) ?
                DynamicButtonOption.SIGN_IN : DynamicButtonOption.SIGN_OUT;
    }

}
