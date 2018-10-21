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


import java.io.Serializable;

public class Child implements Serializable {
    private final String firstName;
    private final String surname;
    private final String status;
    private final int id;

    /** Constructor for child object
     *
     * @param firstName - child's first name
     * @param surname   - child's surname
     * @param status    - signed-in/signed-out status of the child
     */
    public Child(String firstName, String surname, String status, int id) {
        this.firstName = firstName;
        this.surname = surname;
        this.status = status;
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }
}
