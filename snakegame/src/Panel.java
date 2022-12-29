import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;

//inheriting panel class from Jpanel class and adding actionlistener
public class Panel extends JPanel implements ActionListener{

    //dimenssion of the panel
    static int width=1200;
    static int height=600;
    //size of each unit
    static int unit=50;
    //for checking the state of the game at regular intervals
    Timer timer;
    static int delay=160;

    //for food spawns
    Random random;
    int fx,fy;


    int body=3;  //initialise snake body with one head and two body parts
    char dir='R';
    int score=0;

    boolean flag=false;
    //total number of units
    static int size=(width*height)/(unit*unit);
    //x and y blocks of the snake in these arrays
    int xsnake[]=new int[size];
    int ysnake[]=new int[size];


    Panel(){
        //setting the dimension of the panel to width*height
         this.setPreferredSize(new Dimension(width,height));
         this.setBackground(Color.BLACK);
         //making sure that the panel is in focus and the keyboard input get read
         this.setFocusable(true);
         random=new Random();
         //adding key listener to the panel
         this.addKeyListener(new Key());
         game_start();
    }
    public void game_start(){
        //spawning the food
         spawnfood();
         //setting the game runing flag to true
         flag=true;
         //starting the timer with delay
         timer=new Timer(delay,this);
         timer.start();
    }
    public void spawnfood(){
        //setting random coordinates for the food
      fx=random.nextInt((int)(width/unit))*unit;
        fy=random.nextInt((int)(height/unit))*unit;
    }
    public void checkHit(){
  //checking the snakes head collision with its own body or the walls

        //checking with body parts and the for loops we check with the walls
        for(int i=body;i>0;i--){
            if((xsnake[0]==xsnake[i]) && (ysnake[0]==ysnake[i])){
                flag = false;
            }
        }
        //check hit with the walls
        if(xsnake[0]<0){
            flag=false;
        }
        if(xsnake[0]>width){
            flag=false;
        }
        if(ysnake[0]<0){
            flag=false;
        }
        if(ysnake[0]>height){
            flag=false;
        }


        if(flag==false){
            timer.stop();
        }
    }

    //intermediate function to call the draw function
    public void paintComponent(Graphics graphic){
        super.paintComponent(graphic);
        draw(graphic);
    }
    public void draw(Graphics graphic){
    if(flag==true){
        //setting parameter for the food block
        graphic.setColor(Color.RED);
                graphic.fillOval(fx,fy,unit,unit);

        //setting parameters for the snake
        for(int i=0;i<body;i++){
            //for the head
            if(i==0){
               graphic.setColor(Color.YELLOW);
               graphic.fillRect(xsnake[i],ysnake[i],unit,unit);
            }
            //for other parts
            else{
                graphic.setColor(Color.GREEN);
                graphic.fillRect(xsnake[i],ysnake[i],unit,unit);
            }
        }
        //drawing the score
        graphic.setColor(Color.BLUE);
        graphic.setFont(new Font("Comic Sans",Font.BOLD,40));
        FontMetrics f=getFontMetrics(graphic.getFont());
        //drawstring take the string to draw,starting position in x and starting position in y
        graphic.drawString("SCORE:"+score,(width-f.stringWidth("SCORE:"+score))/2,graphic.getFont().getSize());
    }
    else{
        gameOver(graphic);
    }
    }

    public void gameOver(Graphics graphic){
        //drawing the score
        graphic.setColor(Color.RED);
        graphic.setFont(new Font("Comic Sans",Font.BOLD,40));
        FontMetrics f=getFontMetrics(graphic.getFont());
        //drawstring take the string to draw,starting position in x and starting position in y
        graphic.drawString("SCORE:"+score,(width-f.stringWidth("SCORE:"+score))/2,graphic.getFont().getSize());

        //graphic for the game over text
        graphic.setColor(Color.RED);
        graphic.setFont(new Font("Comic Sans",Font.BOLD,80));
        FontMetrics f2=getFontMetrics(graphic.getFont());
        //drawstring take the string to draw,starting position in x and starting position in y
        graphic.drawString("GAME OVER!",(width-f2.stringWidth("GAME OVER!"))/2,height/2);

        //graphic for the replay prompt
        graphic.setColor(Color.WHITE);
        graphic.setFont(new Font("Comic Sans",Font.BOLD,40));
        FontMetrics f3=getFontMetrics(graphic.getFont());
        //drawstring take the string to draw,starting position in x and starting position in y
        graphic.drawString("Press R to replay",(width-f3.stringWidth("Press R to replay"))/2,height/2-180);
    }

    public void move(){
        //loop for updating the body parts except the head
        for(int i=body;i>0;i--){
            xsnake[i]=xsnake[i-1];
            ysnake[i]=ysnake[i-1];
        }

        //for the updation  head coordinates
        switch(dir){
            case 'U':
                ysnake[0]=ysnake[0]-unit;
                break;
            case 'D':
                ysnake[0]=ysnake[0]+unit;
                break;
            case 'L':
                xsnake[0]=xsnake[0]-unit;
                break;
            case 'R':
                xsnake[0]=xsnake[0]+unit;
                break;
        }
    }
    public void checkScore(){
        //checking if the head and the food block coincide
        if((fx==xsnake[0]) && (fy==ysnake[0])){
            body++;
            score++;
            spawnfood();
        }
    }


    public class Key extends KeyAdapter{
      @Override
        public void keyPressed(KeyEvent e){
          switch(e.getKeyCode()){
              case KeyEvent.VK_LEFT:
                  if(dir!='R'){
                      dir='L';
                  }
                  break;
              case KeyEvent.VK_RIGHT:
                  if(dir!='L'){
                      dir='R';
                  }
                  break;
              case KeyEvent.VK_UP:
                  if(dir!='D'){
                      dir='U';
                  }
                  break;
              case KeyEvent.VK_DOWN:
                  if(dir!='U'){
                      dir='D';
                  }
                  break;
              case KeyEvent.VK_R:
                  if(!flag){
                      //changing everything to the initial value and starting the game
                      score=0;
                      body=3;
                      dir='R';
                      Arrays.fill(xsnake ,0 );
                      Arrays.fill(ysnake , 0);
                      game_start();
                  }
                  break;
          }
      }
    }
    @Override
    public void actionPerformed(ActionEvent arg0){
         if(flag==true){
             move();
             checkScore();
             checkHit();
         }
         repaint();
    }
}
