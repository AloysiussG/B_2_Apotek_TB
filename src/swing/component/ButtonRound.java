package swing.component;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import swing.ColorPallete;

public class ButtonRound extends JButton {

    private ColorPallete cp = new ColorPallete();

    private Animator animator;
    private int alpha;
    private Color effectColor = cp.getWhite();
    private boolean mouseEntered;
    private boolean mouseExited;
    private Icon icon = null;
    private int colorR, colorG, colorB;

    public int getColorR() {
        return colorR;
    }

    public void setColorR(int colorR) {
        this.colorR = colorR;
    }

    public int getColorG() {
        return colorG;
    }

    public void setColorG(int colorG) {
        this.colorG = colorG;
    }

    public int getColorB() {
        return colorB;
    }

    public void setColorB(int colorB) {
        this.colorB = colorB;
    }

    public void setColorEffectRGB(int r, int g, int b) {
        setColorR(r);
        setColorG(g);
        setColorB(b);
    }

    public void setBtnIcon(Icon icon) {
        this.icon = icon;
    }

    public Color getEffectColor() {
        return effectColor;
    }

    public void setEffectColor(Color effectColor) {
        this.effectColor = effectColor;
    }

    public ButtonRound() {
        setColorEffectRGB(44, 110, 73);
        setBtnIcon(icon);
        setContentAreaFilled(false);
        setBorder(new EmptyBorder(5, 0, 5, 0));
        setBackground(cp.getColor(1));
        setForeground(cp.getWhite());
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                mouseEntered = false;
                mouseExited = true;
                if (animator.isRunning()) {
                    animator.stop();
                }
                animator.start();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mouseExited = false;
                mouseEntered = true;
                if (animator.isRunning()) {
                    animator.stop();
                }
                animator.start();
            }

        });
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                if (fraction <= 1f) {
                    if (mouseEntered) {
                        alpha = (int) (240f * (fraction));
                    } else if (mouseExited) {
                        alpha = (int) (240f * (1f - fraction));
                    }
                }
                repaint();
            }
        };
        animator = new Animator(300, target);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.setResolution(0);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        int width = getWidth();
        int height = getHeight();

        Graphics2D g2 = (Graphics2D) grphcs.create(0, 0, width, height);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, width, height, height, height);
        if (mouseEntered) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP));
            g2.setColor(new Color(colorR, colorG, colorB, alpha));
        } else if (mouseExited) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP));
            g2.setColor(new Color(colorR, colorG, colorB, alpha));
        }

        g2.fillRoundRect(0, 0, width, height, height, height);

        super.paintComponent(grphcs);
    }
}
