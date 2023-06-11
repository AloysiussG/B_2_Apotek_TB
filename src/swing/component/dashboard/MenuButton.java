package swing.component.dashboard;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
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

public class MenuButton extends JButton {
    //tidak ada default constructor, sehingga harus manual diletakkan menggunakan code

    private static ColorPallete cp = new ColorPallete();

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private int index;
    private Animator animator;
    private int targetSize;
    private float animatSize;
    private Point pressedPoint;
    private float alpha;
    private Color effectColor = new Color(255, 255, 255, 150);

    private Animator hoverAnimator;
    private boolean mouseEntered;
    private boolean mouseExited;
    private float alphaHover;

    //constructor menu utama (ada icon + text)
    public MenuButton(Icon icon, String text) {
        super(text);
        setIcon(icon);
        init();
        setBorder(new EmptyBorder(1, 20, 1, 1));
    }

    //constructor untuk submenu dari menu utama (hanya ada text)
    public MenuButton(String text) {
        super(text);
        init();
        setBorder(new EmptyBorder(1, 50, 1, 1));
    }

    public MenuButton(String text, boolean subMenu) {
        super(text);
        init();
    }

    private void init() {
        setContentAreaFilled(false);
        setForeground(new Color(255, 255, 255));
        setHorizontalAlignment(JButton.LEFT);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                targetSize = Math.max(getWidth(), getHeight()) * 2;
                animatSize = 0;
                pressedPoint = me.getPoint();
                alpha = 0.5f;
                if (animator.isRunning()) {
                    animator.stop();
                }
                animator.start();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mouseEntered = true;
                mouseExited = false;
                if (hoverAnimator.isRunning()) {
                    hoverAnimator.stop();
                }
                hoverAnimator.start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseExited = true;
                mouseEntered = false;
                if (hoverAnimator.isRunning()) {
                    hoverAnimator.stop();
                }
                hoverAnimator.start();
            }

        });
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                if (fraction > 0.5f) {
                    alpha = 1 - fraction;
                }
                animatSize = fraction * targetSize;
                repaint();
            }
        };
        TimingTarget hoverTarget = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                if (fraction <= 1f) {
                    if (mouseEntered) {
                        alphaHover = (int) (240f * (fraction));
                    } else if (mouseExited) {
                        alphaHover = (int) (240f * (1f - fraction));
                    }
                }
                repaint();
            }
        };
        animator = new Animator(400, target);
        animator.setResolution(0);

        hoverAnimator = new Animator(300, hoverTarget);
        hoverAnimator.setAcceleration(0.5f);
        hoverAnimator.setDeceleration(0.5f);
        hoverAnimator.setResolution(0);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (mouseEntered) {
            g2.setColor(new Color(255, 255, 255, 20));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP));
            g2.fillRect(0, 0, getWidth(), getHeight());
        } else if (mouseExited) {
        }

        if (pressedPoint != null) {
            g2.setColor(effectColor);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.fillOval((int) (pressedPoint.x - animatSize / 2), (int) (pressedPoint.y - animatSize / 2), (int) animatSize, (int) animatSize);
        }

        g2.setComposite(AlphaComposite.SrcOver);
        super.paintComponent(grphcs);
    }
}
