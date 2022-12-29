import javax.swing.JFrame;
public class Frame extends JFrame{
  Frame(){
      this.add(new Panel());//add a panel to the frame
      this.setTitle("snake");
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      this.setResizable(false); //ensuring same experience across deferent devices
      this.pack();//pack gets the prefered size
      this.setVisible(true);
      this.setLocationRelativeTo(null); //make sure the frame spawns the centre of the screen
  }
}
