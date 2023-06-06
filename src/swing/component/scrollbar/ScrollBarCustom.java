package swing.component.scrollbar;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JScrollBar;
import swing.ColorPallete;

public class ScrollBarCustom extends JScrollBar {

    private static ColorPallete cp = new ColorPallete();

    public ScrollBarCustom() {
        setUI(new ModernScrollBarUI());
        setPreferredSize(new Dimension(5, 5));
        setForeground(cp.getWhite());
        setUnitIncrement(20);
        setOpaque(false);
    }
}
