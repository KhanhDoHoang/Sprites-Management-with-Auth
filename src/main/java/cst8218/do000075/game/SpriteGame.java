/**
 *
 * @author Khanh Do
 * Purpose: This works likely as an ejb to gameSession
 */
package cst8218.do000075.game;

import cst8218.do000075.entity.Sprite;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;


@Startup
@Singleton
public class SpriteGame {
    
    private Integer x_size = 500;
    private Integer y_size = 500;
    
    private List<Sprite> spriteList;
    
    @Inject
    private GameSession session;
    
    private void doBounce(Sprite s){
        if(s.getX() < 0 && s.getxSpeed() < 0){
            s.bounceLeftWall();
        }
        if(s.getY() < 0 && s.getySpeed() < 0){
            s.bounceTopWall();
        }
        if(s.getX() > x_size && s.getxSpeed() > 0){
            s.bounceRightWall();
        }
        if(s.getY() > y_size && s.getySpeed() > 0){
            s.bounceBottomWall();
        }
    }
    
    //run a method after constructor finishes
    //setup a thread and a infinite loops of retrieving all sprites
    //move sprite every 1/10 seconds                    }
    @PostConstruct
    public void go(){
        new Thread(new Runnable(){
            public void run(){
                while(true){
                    spriteList = session.findAll();
                    //move sprite
                    for(Sprite s: spriteList){
                            s.move();
                            doBounce(s);
                            session.edit(s);
                        }
                    //tell the code to determine which sprite should bounce
                    //ask them to bounce
                    try {
                        Thread.sleep(100);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
