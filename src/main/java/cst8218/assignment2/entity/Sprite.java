package cst8218.assignment2.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is a sprite model.
 * It contains location x y, speed, and methods for moving, bouncing, updating and comparing 
 * @author Hoang Do, Minh Duc
 * @version 1.1
 */

@Entity
@XmlRootElement
public class Sprite implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer x;
    private Integer y;
    private Integer xSpeed;
    private Integer ySpeed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(Integer xSpeed) {
        this.xSpeed = xSpeed;
    }

    public Integer getySpeed() {
        return ySpeed;
    }

    public void setySpeed(Integer ySpeed) {
        this.ySpeed = ySpeed;
    }

    public void move(){
        x+=xSpeed;
        y+=ySpeed;
    }
    
    public void bounceTopWall(){
        ySpeed = - ySpeed;
    }
    
    public void bounceBottomWall(){
        ySpeed = - ySpeed;
    }
    
    public void bounceLeftWall(){
        xSpeed = - xSpeed;
    }
    
    public void bounceRightWall(){
        xSpeed = - xSpeed;
    }
    
    /**
     * Updating sprite and overwrite old data with new data, 
     * this used for Post update
     * @param oldSprite 
     */
    public void updates(Sprite oldSprite){
        //oldSprite set values with new non-null values
        if(x != null){
            oldSprite.setX(x);
        }
        if(y != null){
            oldSprite.setY(y);
        }
        if(xSpeed != null){
            oldSprite.setxSpeed(xSpeed);
        }
        if(ySpeed != null){
            oldSprite.setySpeed(ySpeed);
        }
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sprite)) {
            return false;
        }
        Sprite other = (Sprite) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cst8218.entity.Sprite[ id=" + id + " ]";
    }
    
}
