import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class JFrameMain extends JFrame implements ActionListener {
    JPanel jp = new JPanel();
    JTextArea jta = new JTextArea();
    JButton jbtn_change = new JButton("화면변경");
    public JFrameMain() {
        initDisplay();
    }
    public void initDisplay() {
        jbtn_change.addActionListener(this);
        jp.setLayout(new BorderLayout());
        jp.add("Center",jta);

        this.add("Center",jp);
        this.add("South",jbtn_change);
        this.setSize(500, 400);
        this.setVisible(true);
    }
    public static void main(String[] args) {
        new JFrameMain();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if(obj==jbtn_change) {
            System.out.println("변경");
            Container cont=this.getContentPane();
            if(jp!=null) {
                cont.remove(jp);
                cont.remove(jbtn_change);
            }
            JPanel1 jp1 = null;
            jp1 = new JPanel1();
            this.add("Center",jp1);
            cont.revalidate();
        }
    }

}