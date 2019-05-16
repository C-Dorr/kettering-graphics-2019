/*
 *   Class name:        NoStampSelectedException
 *   Contributor(s):    Jeremy Maxey-Vesperman
 *   Modified:          May 16th, 2019
 *   Package:           edu.kettering.tools.stamp
 *   Purpose:           Wrapper class for Exception.
 *                      Thrown when trying to access the stamp image, but value is null.
 * */

package edu.kettering.tools.stamp;

class NoStampSelectedException extends Exception {
    NoStampSelectedException(String errorMessage) {
        super(errorMessage);
    }
}
