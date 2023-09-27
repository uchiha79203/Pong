import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame implements ActionListener {
    JFrame frame;
    JButton start_button;
    ImageIcon ic=new ImageIcon("pong.png");
    //ping pong table is generally kept in the ratio of 5:9   , so height=  (0.5555) for 1000 width
    public static final int TABLE_WIDTH=1000;
    public static final int TABLE_HEIGHT=(int)(TABLE_WIDTH*(0.5555));

    public GameFrame(){
        start_button=new JButton("S T A R T");
        start_button.addActionListener(this);
        start_button.setBackground(Color.black);
        start_button.setForeground(Color.GREEN);
        start_button.setFocusable(false);

        frame=new JFrame();
        frame.setIconImage(ic.getImage());
        frame.setVisible(true);
        frame.setTitle("PING PONG");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0,0,TABLE_WIDTH,TABLE_HEIGHT);
        frame.add(start_button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==start_button){
            start_button.setVisible(false);
            Game_Panel panel=new Game_Panel();
            frame.add(panel);
            panel.requestFocus();
        }
    }
}
