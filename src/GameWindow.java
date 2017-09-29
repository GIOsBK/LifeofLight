
import bases.GameObject;
import bases.events.EventManager;
import bases.inputs.CommandListener;
import bases.inputs.InputManager;
import bases.uis.InputText;
import bases.uis.StatScreen;
import bases.uis.TextScreen;
import settings.Settings;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import static java.lang.System.nanoTime;
import static java.lang.System.setOut;

/**
 * Created by huynq on 7/28/17.
 */
public class GameWindow extends JFrame {

    private BufferedImage backBufferImage;
    private Graphics2D backBufferGraphics;

    private long lastTimeUpdate = -1;
    int height = 5;// cao
    int width =5;// rong
    int playerx=2;
    int playery=3;

    public void in_map(){
        EventManager.pushClearUI();
        String line;
        for (int y = 0; y <= height + 1; y++) {
           line = "";
            for (int x = 0; x <= width + 1; x++) {
                if (x == 0 || x == width + 1) {
                    line += " * ";
                } else if (y == 0 || y == height + 1) {
                    line += " * ";
                } else if (x == playerx && y == playery) {
                    line += " @ ";
                } else {
                    line += "   ";
                }
            }
            EventManager.pushUIMessage(line);
        }
    }




    /////////////////////////////////////////////////////////////////////
    public GameWindow() {
        setupFont();
        setupPanels();
        setupWindow();
        EventManager.pushClearUI();
        InputManager.instance.addCommandListener(new CommandListener() {
            @Override
            public void onCommandFinished(String command) {
                if (command.equals("map")){
                    String line;
                    for (int y = 0; y <= height + 1; y++) {
                        line = "";
                        for (int x = 0; x <=width + 1; x++) {
                            if (x == 0 || x == width + 1) {
                                line += " * ";
                            } else if (y == 0 || y == height + 1) {
                                line += " * ";
                            } else if (x == playerx && y == playery) {
                                line += " @ ";
                            } else {
                                line += "   ";
                            }
                        }
                        EventManager.pushUIMessage(line);
                    }
                }
            }

            @Override
            public void commandChanged(String command) {

            }
        });
        int i = 0;
        while (i <100){
        InputManager.instance.addCommandListener(new CommandListener() {
            @Override
            public void onCommandFinished(String command) {
                switch (command)
                {
                    case "l":
                        if (playerx>1 && playerx <=width)
                        {
                            playerx --;
                        }
                        else {
                           EventManager.pushUIMessage("die");
                        }
                        in_map();
                        break;
                    case "r":
                        if (playerx<0 && playerx <width)
                        {
                            playerx ++;
                        }
                        else {
                            EventManager.pushUIMessage("die");
                        }
                        in_map();
                        break;
                    case "t":
                        if(playery>1 && playery <= height){
                            playery--;
                        }
                        else {
                            EventManager.pushUIMessage("die");
                        }
                        in_map();
                        break;
                    case "d":
                        if(playery >0 && playery< height){
                            playery++;
                        }
                        else {
                            EventManager.pushUIMessage("die");
                        }
                        in_map();
                        break;
                    default:
                        EventManager.pushUIMessage("lenh sai. nhap lai");
                        in_map();
                }

            }

            @Override
            public void commandChanged(String command) {

            }
        });
            i++;


        }

    }



    private void setupFont() {

    }

    private void setupPanels() {
        TextScreen textScreenPanel = new TextScreen();
        textScreenPanel.setColor(Color.BLACK);
        textScreenPanel.getSize().set(
                Settings.TEXT_SCREEN_SCREEN_WIDTH,
                Settings.TEXT_SCREEN_SCREEN_HEIGHT);
        pack();
        textScreenPanel.getOffsetText().set(getInsets().left + 20, getInsets().top + 20);
        GameObject.add(textScreenPanel);


        InputText commandPanel = new InputText();
        commandPanel.getPosition().set(
                0,
                Settings.SCREEN_HEIGHT
        );
        commandPanel.getOffsetText().set(20, 20);
        commandPanel.getSize().set(
                Settings.CMD_SCREEN_WIDTH,
                Settings.CMD_SCREEN_HEIGHT
        );
        commandPanel.getAnchor().set(0, 1);
        commandPanel.setColor(Color.BLACK);
        GameObject.add(commandPanel);


        StatScreen statsPanel = new StatScreen();
        statsPanel.getPosition().set(
                Settings.SCREEN_WIDTH,
                0
        );

        statsPanel.getAnchor().set(1, 0);
        statsPanel.setColor(Color.BLACK);
        statsPanel.getSize().set(
                Settings.STATS_SCREEN_WIDTH,
                Settings.STATS_SCREEN_HEIGHT
        );
        GameObject.add(statsPanel);

        InputManager.instance.addCommandListener(textScreenPanel);
    }


    private void setupWindow() {
        this.setSize(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
        this.setVisible(true);
        this.setTitle(Settings.GAME_TITLE);
        this.addKeyListener(InputManager.instance);
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        backBufferImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        backBufferGraphics = (Graphics2D) backBufferImage.getGraphics();
    }

    public void gameLoop() {
        while (true) {
            if (-1 == lastTimeUpdate) lastTimeUpdate = nanoTime();

            long currentTime = nanoTime();

            if (currentTime - lastTimeUpdate > 17000000) {
                lastTimeUpdate = currentTime;
                GameObject.runAll();
                InputManager.instance.run();
                render(backBufferGraphics);
                repaint();
                /*InputManager.instance.addCommandListener(new CommandListener() {
                    @Override
                    public void onCommandFinished(String command) {
                        switch (command)
                        {
                            case "l":
                                if (playerx>1 && playerx <=width)
                                {
                                    playerx --;
                                }
                                in_map();
                                break;
                            case "r":
                                if (playerx<0 && playerx <width)
                                {
                                    playerx ++;
                                }
                                in_map();
                                break;
                            case "t":
                                if(playery>1 && playery <= height){
                                    playery--;
                                }
                                in_map();
                                break;
                            case "d":
                                if(playery >0 && playery< height){
                                    playery++;
                                }
                                in_map();
                                break;
                            default:
                                EventManager.pushUIMessage("die.hehe");
                        }
                    }

                    @Override
                    public void commandChanged(String command) {

                    }
                });*/

            }
        }
    }

    private void render(Graphics2D g2d) {
        g2d.setFont(Settings.DEFAULT_FONT);
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);

        GameObject.renderAll(g2d);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(backBufferImage, 0, 0, null);
    }
}
