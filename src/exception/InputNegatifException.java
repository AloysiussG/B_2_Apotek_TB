/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception;

/**
 *
 * @author willi
 */
public class InputNegatifException extends Exception {

    public String getMessage() {
        return "Value kuantitas tidak boleh kurang dari 0";
    }
}
