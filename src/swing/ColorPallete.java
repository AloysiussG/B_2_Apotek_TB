/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

//kelas ini digunakan untuk menyimpan color pallete UI aplikasi kelompok 2
public class ColorPallete {

    private List<String> colorList = new ArrayList<>();

    public ColorPallete() {
        //dark spring green
        colorList.add("#2C6E49");
        //sea green
        colorList.add("#4C956C");
        //light yellow
        colorList.add("#FEFEE3");
        //melon
        colorList.add("#FFC9B9");
        //persian orange
        colorList.add("#D68C45");
    }

    public List<String> getColorListAsString() {
        return colorList;
    }

    public List<Color> getColorListAsColor() {
        List<Color> colorListAsColor = new ArrayList<Color>();
        for (String color : colorList) {
            colorListAsColor.add(Color.decode(color));
        }
        return colorListAsColor;
    }

    public Color getColor(int number) {
        if (number < 0) {
            return Color.decode(colorList.get(0));
        } else if (number > colorList.size() - 1) {
            return Color.decode(colorList.get(colorList.size() - 1));
        } else {
            return Color.decode(colorList.get(number));
        }
    }

}
