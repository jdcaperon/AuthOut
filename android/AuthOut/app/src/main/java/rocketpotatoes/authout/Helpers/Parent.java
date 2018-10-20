/*
 * MIT License

 Copyright (c) 2018 Ryan Kurz

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
package rocketpotatoes.authout.Helpers;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rocketpotatoes.authout.Helpers.Child;

public class Parent implements Parcelable{
    private final String firstName;
    private final String surname;
    private final List<Child> children;
    private final List<Child> trustedChildren;
    private int id;

    /** A parent object in order to
     *
     * @param firstName - first name of the parent
     * @param surname   - surname of the parent
     * @param children - list of child objects
     * @param trustedChildren - list of child objects
     */
    public Parent(String firstName, String surname, List<Child> children, List<Child> trustedChildren, int id) {
        this.children = children;
        this.firstName = firstName;
        this.surname = surname;
        this.trustedChildren = trustedChildren;
        this.id = id;
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

    public List<Child> getTrustedChildren() {
        return trustedChildren;
    }

    public int getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(firstName);
        out.writeString(surname);
        out.writeList(children);
        out.writeList(trustedChildren);
    }

    // this is used to regenerate your object.
    // All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Parent> CREATOR = new Parcelable.Creator<Parent>() {
        public Parent createFromParcel(Parcel in) {
            return new Parent(in);
        }

        public Parent[] newArray(int size) {
            return new Parent[size];
        }
    };

    /** Constructor for a parent built from a {@link Parcel}
     *
     * @param in - {@link Parcel} to build the parent from
     */
    private Parent(Parcel in) {
        this.children = new ArrayList<Child>();
        this.trustedChildren = new ArrayList<Child>();
        this.firstName = in.readString();
        this.surname = in.readString();
        in.readList(children, null);
        in.readList(trustedChildren, null);
    }
}
