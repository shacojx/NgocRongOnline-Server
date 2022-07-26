/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cache;

public class Part {
    public Part(int type) {
        this.type = type;
        if (type == 0) {
            this.pi = new PartImage[3];
        }
        if (type == 1) {
            this.pi = new PartImage[17];
        }
        if (type == 2) {
            this.pi = new PartImage[14];
        }
        if (type == 3) {
            this.pi = new PartImage[2];
        }
    }

    public int type;
    public PartImage[] pi;
}
