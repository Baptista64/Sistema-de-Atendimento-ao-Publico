package message;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import glass.Glass;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class MessageDialog extends javax.swing.JDialog {

    private final JFrame fram;
    private Animator animator;
    private Glass glass;
    private boolean show;
    private MessageType messageType = MessageType.CANCEL;

    public MessageDialog(JFrame fram, Type type) {
        super(fram, true);
        this.fram = fram;
        initComponents();
        init();
        switch (type) {
            case INFO:
                lbIcon.setIcon(new ImageIcon(".//src//message//info.png"));
                background1.remove(cmdCancel);
                cmdOK.setBounds(cmdOK.getX(), cmdOK.getY(), background1.getWidth(), cmdOK.getHeight());
                cmdOK.setText("Ok");
                break;
            case QUESTION:
                cmdCancel.setText("Não");
                cmdOK.setText("Sim");
                lbIcon.setIcon(new ImageIcon(".//src//message//icon.png"));
                break;
            case MULTICHOISES:
                lbIcon.setIcon(new ImageIcon(".//src//message//icon.png"));
                glass.ButtonCustom button = new glass.ButtonCustom();

                button.setBackground(new java.awt.Color(25, 182, 247));
                button.setColorHover(new java.awt.Color(102, 203, 245));
                button.setColorPressed(new java.awt.Color(42, 170, 224));
                button.setFont(new java.awt.Font("sansserif", 0, 14));
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        notActionPerformed(arg0);
                    }
                });

                message.Background panel = new message.Background();
                panel.setBounds(cmdCancel.getX(), cmdCancel.getY(), background1.getWidth(), background1.getHeight());
                panel.setLayout(new GridLayout(0, 3, 5, 0));

                background1.remove(cmdOK);
                background1.remove(cmdCancel);

                cmdCancel.setText("Cancelar");
                cmdOK.setText("Sim");
                button.setText("Não");

                background1.add(button);
                button.setBounds(3, 209, 120, 50);
                
                background1.add(cmdOK);
                cmdOK.setBounds(278, 209, 120, 50);
                
                background1.add(cmdCancel);
                cmdCancel.setBounds(125, 209, 150, 50);

                break;
            default:
                cmdOK.setText("Ok");
                lbIcon.setIcon(new ImageIcon(".//src//message//warning.png"));
                background1.remove(cmdCancel);
                cmdOK.setBounds(cmdOK.getX(), cmdOK.getY(), background1.getWidth(), cmdOK.getHeight());
                break;
        }
    }

    private void init() {
        setBackground(new Color(0, 0, 0, 0));
        StyledDocument doc = txt.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        txt.setOpaque(false);
        txt.setBackground(new Color(0, 0, 0, 0));
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

    public void showMessage(String title, String message) {
        fram.setGlassPane(glass);
        glass.setVisible(true);
        lbTitle.setText(title);
        txt.setText(message);
        setLocationRelativeTo(fram);
        startAnimator(true);
        setVisible(true);
    }

    public void closeMessage() {
        startAnimator(false);
    }

    public MessageType getMessageType() {
        return messageType;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background1 = new message.Background();
        cmdCancel = new glass.ButtonCustom();
        cmdOK = new glass.ButtonCustom();
        lbIcon = new javax.swing.JLabel();
        lbTitle = new javax.swing.JLabel();
        txt = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        background1.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        cmdCancel.setBackground(new java.awt.Color(245, 71, 71));
        cmdCancel.setText("Cancelar");
        cmdCancel.setColorHover(new java.awt.Color(255, 74, 74));
        cmdCancel.setColorPressed(new java.awt.Color(235, 61, 61));
        cmdCancel.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        cmdCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCancelActionPerformed(evt);
            }
        });

        cmdOK.setText("Ok");
        cmdOK.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        cmdOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdOKActionPerformed(evt);
            }
        });

        lbIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lbTitle.setForeground(new java.awt.Color(245, 71, 71));
        lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle.setText("Message Title");

        txt.setEditable(false);
        txt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        txt.setForeground(new java.awt.Color(76, 76, 76));
        txt.setText("Message Text\nSimple");
        txt.setFocusable(false);

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addComponent(cmdCancel, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                .addGap(3, 3, 3)
                .addComponent(cmdOK, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE))
            .addComponent(lbIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(txt)
        );
        background1Layout.setVerticalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, background1Layout.createSequentialGroup()
                .addComponent(lbIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(lbTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdOK, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmdOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdOKActionPerformed
        messageType = MessageType.OK;
        closeMessage();
    }//GEN-LAST:event_cmdOKActionPerformed

    private void notActionPerformed(ActionEvent e) {
        messageType = MessageType.NO;
        closeMessage();
    }

    private void cmdCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCancelActionPerformed
        messageType = MessageType.CANCEL;
        closeMessage();
    }//GEN-LAST:event_cmdCancelActionPerformed

    public static enum MessageType {
        CANCEL, OK, NO
    }

    public static enum Type {
        INFO, WARNING, QUESTION, MULTICHOISES
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private message.Background background1;
    private glass.ButtonCustom cmdCancel;
    private glass.ButtonCustom cmdOK;
    private javax.swing.JLabel lbIcon;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JTextPane txt;
    // End of variables declaration//GEN-END:variables
}
