package message;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import glass.Glass;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class MessageCliente extends javax.swing.JDialog {

    private final JFrame fram;
    private Animator animator;
    private Glass glass;
    private boolean show;
    private boolean timerOn;
    private final Color TIMER_COLORON_1 = new Color(204, 204, 204);
    private final Color TIMER_COLORON_2 = new Color(255, 255, 255);

    public MessageCliente(JFrame fram, String message) {
        super(fram, true);
        this.fram = fram;
        initComponents();
        cmdCancel.setText(message);
        timer1.start();
        init();
    }

    private void init() {
        setBackground(new Color(0, 0, 0, 0));
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeMessage();
            }
        });
        animator = new Animator(350, new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                float f = show ? fraction : 1f - fraction;
                glass.setAlpha(f - f * 0.3f);
                try {
                    setOpacity(f);
                } catch (Exception e) {

                };
            }

            @Override
            public void end() {
                if (show == false) {
                    dispose();
                    glass.setVisible(false);
                }
            }
        });
        animator.setResolution(0);
        animator.setAcceleration(.5f);
        animator.setDeceleration(.5f);
        try {
            setOpacity(0f);
        } catch (Exception e) {

        }
        glass = new Glass();
    }

    private void startAnimator(boolean show) {
        if (animator.isRunning()) {
            float f = animator.getTimingFraction();
            animator.stop();
            animator.setStartFraction(1f - f);
        } else {
            animator.setStartFraction(0f);
        }
        this.show = show;
        animator.start();
    }

    public void showMessage() {
        fram.setGlassPane(glass);
        glass.setVisible(true);
        setLocationRelativeTo(fram);
        startAnimator(true);
        setVisible(true);
    }

    public void closeMessage() {
        startAnimator(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        timer1 = new org.netbeans.examples.lib.timerbean.Timer();
        jPanel1 = new javax.swing.JPanel();
        cmdCancel = new glass.ButtonCustom();
        cmdCancel1 = new glass.ButtonCustom();

        timer1.addTimerListener(new org.netbeans.examples.lib.timerbean.TimerListener() {
            public void onTime(java.awt.event.ActionEvent evt) {
                timer1OnTime(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(642, 336));

        jPanel1.setLayout(new java.awt.BorderLayout());

        cmdCancel.setBackground(new java.awt.Color(245, 71, 71));
        cmdCancel.setText("Cancelar");
        cmdCancel.setColorHover(new java.awt.Color(245, 71, 71));
        cmdCancel.setColorPressed(new java.awt.Color(245, 71, 71));
        cmdCancel.setFont(new java.awt.Font("sansserif", 0, 36)); // NOI18N
        jPanel1.add(cmdCancel, java.awt.BorderLayout.CENTER);

        cmdCancel1.setBackground(new java.awt.Color(102, 255, 102));
        cmdCancel1.setForeground(new java.awt.Color(0, 0, 0));
        cmdCancel1.setText("Cancelar");
        cmdCancel1.setColorHover(new java.awt.Color(160, 246, 160));
        cmdCancel1.setColorPressed(new java.awt.Color(105, 230, 105));
        cmdCancel1.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        cmdCancel1.setPreferredSize(new java.awt.Dimension(107, 43));
        cmdCancel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCancel1ActionPerformed(evt);
            }
        });
        jPanel1.add(cmdCancel1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void timer1OnTime(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timer1OnTime
        if (timerOn) {
            timerOn = false;
            cmdCancel.setForeground(TIMER_COLORON_1);
        } else {
            timerOn = true;
            cmdCancel.setForeground(TIMER_COLORON_2);
        }
    }//GEN-LAST:event_timer1OnTime

    private void cmdCancel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCancel1ActionPerformed
        closeMessage();
    }//GEN-LAST:event_cmdCancel1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private glass.ButtonCustom cmdCancel;
    private glass.ButtonCustom cmdCancel1;
    private javax.swing.JPanel jPanel1;
    private org.netbeans.examples.lib.timerbean.Timer timer1;
    // End of variables declaration//GEN-END:variables
}
